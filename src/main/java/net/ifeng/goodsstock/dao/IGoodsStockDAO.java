package net.ifeng.goodsstock.dao;

import net.ifeng.base.dao.IBaseDAO;
import net.ifeng.goodsstock.bean.GoodsStock;
import net.ifeng.goodsstock.bean.GoodsStockLog;




/**
 * 
 *
 * @author itfeng
 * at 2017年3月30日 下午7:28:20
 * 
 */
public interface IGoodsStockDAO extends IBaseDAO {
	
	/**
	 * 根据商品ID和库房ID查询库存
	 * 
	 * @author itfeng at 2017年3月30日 下午7:31:27
	 * @param goodsId
	 * @param warehouseId
	 * @return
	 */
	public GoodsStock findGoodsStockByGoodsIdAndwarehouseId(int goodsId,int warehouseId);
	/**
	 * 将库存变更日志写入数据库
	 * 
	 * @author itfeng at 2017年4月2日 下午6:01:04
	 * @param log
	 * @return
	 */
	public boolean addGoodsStockLog(GoodsStockLog log);
	/**
	 * 更新库存对象的可卖数和实际库存值，其他字段不会更新，如果更新整个对象请使用update方法
	 * 
	 * @author itfeng at 2017年4月2日 下午6:02:57
	 * @param id
	 * @param amount
	 * @param readAmount
	 * @return
	 */
	public boolean updateGoodsStockAmount(int goodsId,int warehouseId,int amount,int readAmount);
}
