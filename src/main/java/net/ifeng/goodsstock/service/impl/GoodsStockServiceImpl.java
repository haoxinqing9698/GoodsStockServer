package net.ifeng.goodsstock.service.impl;

import net.ifeng.cache.ICacheUtil;
import net.ifeng.goodsstock.bean.GoodsStock;
import net.ifeng.goodsstock.bean.GoodsStockLog;
import net.ifeng.goodsstock.dao.IGoodsStockDAO;
import net.ifeng.goodsstock.service.IGoodsStockService;
import net.ifeng.queue.IQueueUtil;
import net.ifeng.queue.Message;
import net.itfeng.lock.ClusterLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * 
 *
 * @author itfeng
 * at 2017年3月30日 下午7:16:16
 * 
 */
@Service
public class GoodsStockServiceImpl implements IGoodsStockService {

	
	@Autowired
	private ICacheUtil cacheUtil;
	
	@Autowired
	private ClusterLock clusterLock;
	
	@Autowired
	private IGoodsStockDAO goodsStockDAO;
	
	@Autowired
	private IQueueUtil queueUtil;

	/* (non-Javadoc)
	 * @see net.ifeng.goodsstock.service.IGoodsStockService#decrGoodsStock(int, int, int, int, int, java.lang.String)
	 * @author itfeng at 2017年3月30日 下午7:16:16
	 * @param goodsId
	 * @param warehouseId
	 * @param changeAmount
	 * @param reasonObjId
	 * @param reasonObjType
	 * @param reason
	 * @return
	 */
	public GoodsStockLog decrGoodsStock(int goodsId, int warehouseId,
			int changeAmount, int reasonObjId, int reasonObjType, String reason,
			GoodsStockLog.OprationType oprationType) {
		//获取本商品可卖数的分布式锁
		String key = makeGoodsStockKey(goodsId, warehouseId);
		try{
			//获取锁，100ms以内获取到锁，超时则返回null
			boolean lockResult = clusterLock.lock(key, 100L, 10);	
			if(lockResult){
				//查询可卖数对象
				GoodsStock stock = findGoodsStockByGoodsIdAndwarehouseId(goodsId, warehouseId);
				if(stock != null){
					int amount = stock.getAmount();
					int securityAmount  = stock.getSecurityAmount();
					int remainAmount = amount - changeAmount;
					//可卖数充足则执行扣减操作
					if(remainAmount >= securityAmount){
						//生成日志对象
						GoodsStockLog log = changeGoodsStock(stock,-changeAmount,reasonObjId,
								reasonObjType,reason,oprationType);
						//只更新到缓存即可认为更新成功
						cacheUtil.set(key, stock);
						queueUtil.push(GoodsStockLog.QUEUENAME, new Message<GoodsStockLog>(log));
						return log;
					}else{
						//可卖数不足返回null
						return null;
					}
				}else{
					//库存对象不存在返回null
					return null;
				}
			}else{
				//未获取到锁返回null
				return null;
			}
			
		}finally{
			clusterLock.unlock(key);
		}
	}

	/* (non-Javadoc)
	 * @see net.ifeng.goodsstock.service.IGoodsStockService#incrGoodsStock(int, int, int, int, int, java.lang.String)
	 * @author itfeng at 2017年3月30日 下午7:16:16
	 * @param goodsId
	 * @param warehouseId
	 * @param changeAmount
	 * @param reasonObjId
	 * @param reasonObjType
	 * @param reason
	 * @return
	 */
	public GoodsStockLog incrGoodsStock(int goodsId, int warehouseId,
			int changeAmount, int reasonObjId, int reasonObjType, String reason,GoodsStockLog.OprationType oprationType) {
		//获取锁，100ms以内获取到锁，超时则返回null
		String key = makeGoodsStockKey(goodsId, warehouseId);
		boolean lockResult = false;
		try{
			//获取锁
			lockResult = clusterLock.lock(key, 100L, 10);	
			if(lockResult){
				//查询可卖数对象
				GoodsStock stock = findGoodsStockByGoodsIdAndwarehouseId(goodsId, warehouseId);
				if(stock != null){
					//生成日志对象
					GoodsStockLog log = changeGoodsStock(stock,-changeAmount,reasonObjId,
							reasonObjType,reason,oprationType);
					//只更新到缓存即可认为更新成功
					cacheUtil.set(key, stock);
					return log;
				}else{
					//未获取到库存对象返回null
					return null;
				}	
			}else{
				//未获取到锁返回null
				return null;
			}
			
		}finally{
			if(lockResult){
				clusterLock.unlock(key);
			}
		}
	}

	/* (non-Javadoc)
	 * @see net.ifeng.base.service.IBaseService#add(java.lang.Object)
	 * @author itfeng at 2017年4月2日 上午9:30:33
	 * @param object
	 * @return
	 */
	public boolean add(GoodsStock object) {
		if (object == null || !(object instanceof GoodsStock)){
			return false;
		}
		GoodsStock stock = (GoodsStock)object;
		boolean result = goodsStockDAO.add(object);
		if(result){
			cacheUtil.set(makeGoodsStockKey(stock.getGoodsId(), stock.getWarehouseId()), stock);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see net.ifeng.base.service.IBaseService#update(java.lang.Object)
	 * @author itfeng at 2017年4月2日 上午9:30:33
	 * @param object
	 * @return
	 */
	public boolean update(GoodsStock object) {
		if (object == null || !(object instanceof GoodsStock)){
			return false;
		}
		GoodsStock stock = (GoodsStock)object;
		String key = makeGoodsStockKey(stock.getGoodsId(), stock.getWarehouseId());
		try{
			clusterLock.lock(key, 10L, 10);
			boolean result = goodsStockDAO.update(stock);
			return result;
			
		}finally{
			clusterLock.unlock(key);
		}	
	}

	/* (non-Javadoc)
	 * @see net.ifeng.base.service.IBaseService#findById(long)
	 * @author itfeng at 2017年4月2日 上午9:30:33
	 * @param id
	 * @return
	 */
	public GoodsStock findById(long id) {
		GoodsStock stock = (GoodsStock)goodsStockDAO.findById(id);
		return stock;
	}

	/* (non-Javadoc)
	 * @see net.ifeng.goodsstock.service.IGoodsStockService#findGoodsStockByGoodsIdAndwarehouseId(int, int)
	 * @author itfeng at 2017年4月2日 上午9:35:02
	 * @param goodsId
	 * @param warehouseId
	 * @return
	 */
	public GoodsStock findGoodsStockByGoodsIdAndwarehouseId(int goodsId,
			int warehouseId) {
		String key = makeGoodsStockKey(goodsId, warehouseId);
		GoodsStock stock = cacheUtil.get(key, GoodsStock.class);
		//无效的库存对象则返回null
		if(stock!=null && stock.getId()<=0){
			return null;
		}
		
		if(stock!=null){
			return stock;
		}
		stock = goodsStockDAO.findGoodsStockByGoodsIdAndwarehouseId(goodsId, warehouseId);
		if(stock!=null){
			cacheUtil.set(key, stock);
		}else{
			//创建一个空对象，避免无效数据导致重复查库操作
			stock = new GoodsStock();
			stock.setGoodsId(goodsId);
			stock.setWarehouseId(warehouseId);
			cacheUtil.set(key, stock);
		}
		return stock;
	}
	
	/**
	 * 获取可卖数缓存的key
	 * 
	 * @author itfeng at 2017年3月30日 下午7:27:03
	 * @param goodsId
	 * @param warehouseId
	 * @return
	 */
	private String makeGoodsStockKey(int goodsId,int warehouseId){
		
		return new StringBuilder("GoodsStock_").append(goodsId).append("_").append(warehouseId).toString();
	}

	/* (non-Javadoc)
	 * @see net.ifeng.goodsstock.service.IGoodsStockService#getGoodsAmount(int, int)
	 * @author itfeng at 2017年4月2日 上午9:47:14
	 * @param goodsId
	 * @param warehouseId
	 * @return
	 */
	public int getGoodsAmount(int goodsId, int warehouseId) {
		GoodsStock stock = findGoodsStockByGoodsIdAndwarehouseId(goodsId,warehouseId);
		if(stock == null){
			return 0;
		}
		int amount = stock.getAmount()-stock.getSecurityAmount();
		return amount;
	}
	/**
	 * 库存变更并且生成变更日志
	 * 
	 * @author itfeng at 2017年4月2日 下午4:20:12
	 * @param stock
	 * @param changeAmount
	 * @param reasonObjId
	 * @param reasonObjType
	 * @param reason
	 * @param oprationType
	 * @return
	 */
	public GoodsStockLog changeGoodsStock(GoodsStock stock, int changeAmount, int reasonObjId,
			int reasonObjType, String reason,GoodsStockLog.OprationType oprationType) {
		//首先检查GoodsStock是否合法  id>0；默认生成的GoodsStock id=-1
		if(stock.getId()<=0){
			return null;
		}
		GoodsStockLog log = new GoodsStockLog();
		log.setGoodsId(stock.getGoodsId());
		log.setWarehouseId(stock.getWarehouseId());
		log.setChangeAmount(changeAmount);
		log.setAmount_before(stock.getAmount());
		
		int realAmount = stock.getRealAmount();
		int remainAmount = realAmount + changeAmount;
		log.setRealAmount_before(stock.getRealAmount());
		log.setRealAmount_after(remainAmount);
		//库存变更后余量大于0则设置可卖数为,剩余数量，否则设置可卖数为0
		if(remainAmount>0){
			log.setAmount_after(remainAmount);
		}else{
			log.setAmount_after(0);
		}
		
		log.setSecurityAmount(stock.getSecurityAmount());
		log.setReasonObjId(reasonObjId);
		log.setReasonObjType(reasonObjType);
		log.setReason(reason);
		log.setOprationType(oprationType);
		
		//变更缓存中的库存对象
		stock.setAmount(log.getAmount_after());
		stock.setRealAmount(log.getRealAmount_after());
		
		return log;
	}

}
