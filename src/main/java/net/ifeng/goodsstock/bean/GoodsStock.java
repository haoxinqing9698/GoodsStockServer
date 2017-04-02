package net.ifeng.goodsstock.bean;

import net.ifeng.cache.CacheBean;




/**
 * 
 *
 * @author itfeng
 * at 2017年3月27日 下午8:18:02
 * 
 */
public class GoodsStock extends CacheBean {

	private static final long serialVersionUID = 7943355431275750567L;
	
	private long id = -1;
	
	//商品ID
	private int goodsId;
	//库房ID
	private int warehouseId;
	//抽象的库存概念，可以为负数（比如有订单，但没有货的时候用负数表示）
	//该值由库房系统传给商城系统更新，商城由于下单，取消，售后等操作更新可卖数时需要同步更新该值
	private int realAmount;
	//商品可以销售的数量，当realAmount大于等于0时与realAmount相等，可卖数不能为负
	//当取消订单释放可卖数时，先增加realAmount的值，如果realAmount变更后小于0则amount仍然为0保持不变
	private int amount;
	//安全库存，预留库存，当amount小于此值时前台展示不可卖，但实际可以下单成功
	private int securityAmount;
	
	
	
	/**
	 * 商品ID
	 * 
	 * @author itfeng at 2017年3月27日 下午8:44:17
	 * @return
	 */
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	/**
	 * 商品所在库房ID
	 * 
	 * @author itfeng at 2017年3月27日 下午8:44:31
	 * @return
	 */
	public int getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}
	/**
	 * 抽象的库存概念，可以为负数（比如有订单，但没有货的时候用负数表示）
	 * 
	 * @author itfeng at 2017年3月27日 下午8:44:48
	 * @return
	 */
	public int getRealAmount() {
		return realAmount;
	}
	public void setRealAmount(int realAmount) {
		this.realAmount = realAmount;
	}
	/**
	 * 商品可卖数，当realAmount大于等于0时与realAmount相等，可卖数不能为负
	 * 
	 * @author itfeng at 2017年3月27日 下午8:45:23
	 * @return
	 */
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	/**
	 * 
	 * 安全库存，预留库存，当amount小于此值时前台展示不可卖，但实际可以下单成功
	 * @author itfeng at 2017年3月27日 下午8:45:53
	 * @return
	 */
	public int getSecurityAmount() {
		return securityAmount;
	}
	public void setSecurityAmount(int securityAmount) {
		this.securityAmount = securityAmount;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	

}
