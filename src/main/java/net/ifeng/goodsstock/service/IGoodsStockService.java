package net.ifeng.goodsstock.service;

import net.ifeng.base.service.IBaseService;
import net.ifeng.goodsstock.bean.GoodsStock;
import net.ifeng.goodsstock.bean.GoodsStockLog;




/**
 * 
 *
 * @author itfeng
 * at 2017年3月27日 下午9:03:47
 * 
 */
public interface IGoodsStockService extends IBaseService<GoodsStock> {
	
	/**
	 * 
	 * 扣减库存操作接口
	 * @author itfeng at 2017年3月27日 下午9:15:57
	 * @param goodsId 商品ID
	 * @param warehouseId 库房ID
	 * @param changeAmount 要扣减的商品数量
	 * @param reasonObjId 引起扣减操作的原因对象ID
	 * @param reasonObjType  引起扣减库存操作对象的类型ID
	 * @param reason  原因描述
	 * @param oprationType  操作类型
	 * @return GoodsStockLog 返回变更日志，如果变更失败则返回null
	 */
	public GoodsStockLog decrGoodsStock(int goodsId,int warehouseId,int changeAmount,int reasonObjId,int reasonObjType,String reason,GoodsStockLog.OprationType oprationType);
	/**
	 * 
	 * 增加库存操作接口
	 * @author itfeng at 2017年3月27日 下午9:15:57
	 * @param goodsId 商品ID
	 * @param warehouseId 库房ID
	 * @param changeAmount 要扣减的商品数量
	 * @param reasonObjId 引起扣减操作的原因对象ID
	 * @param reasonObjType  引起扣减库存操作对象的类型ID
	 * @param reason  原因描述
	 * @param oprationType  操作类型
	 * @return GoodsStockLog 返回变更日志，如果变更失败则返回null
	 */
	public GoodsStockLog incrGoodsStock(int goodsId,int warehouseId,int changeAmount,int reasonObjId,int reasonObjType,String reason,GoodsStockLog.OprationType oprationtype);
	/**
	 * 通过商品ID和库房ID查询库存对象
	 * 
	 * @author itfeng at 2017年4月2日 上午9:34:29
	 * @param goodsId
	 * @param warehouseId
	 * @return
	 */
	public GoodsStock findGoodsStockByGoodsIdAndwarehouseId(int goodsId, int warehouseId);
	/**
	 * 返回实际可以销售的数量，大小等于可卖数-安全可卖数，大于0表示可售
	 * 
	 * @author itfeng at 2017年4月2日 上午9:45:38
	 * @param goodsId
	 * @param warehouseId
	 * @return 商品的可卖数,
	 */
	public int getGoodsAmount(int goodsId, int warehouseId);

}
