package net.ifeng.goodsstock.bean;

import net.ifeng.cache.CacheBean;




/**
 * 
 * 商品可卖数日志
 * @author itfeng
 * at 2017年3月27日 下午8:19:32
 * 
 */
public class GoodsStockLog extends CacheBean {

	private static final long serialVersionUID = -4602488925766377102L;
	/**
	 * 队列名：变更库存时将变更日志写入到队列。
	 */
	public static final String QUEUENAME = "GoodsStockLog_Queue";

	private long id;
	
	//商品ID
	private int goodsId;
	//库房ID
	private int warehouseId;
	
	//变更的数量
	private int changeAmount;
	
	//变更前的amount
	private int amount_before;
	//变更后的amount
	private int amount_after;
	
	//变更前的realAmount
	private int realAmount_before;
	//变更后的realAmount
	private int realAmount_after;
	
	//引起数量变更的原因对象ID
	private long reasonObjId;
	//引起数量变更的原因对象类型
	private int reasonObjType;
	//数量变更原因描述
	private String reason;
	//操作类型
	private OprationType oprationType;
	
	//库存变更时的安全库存值，预留库存，当amount小于此值时前台展示不可卖，但实际可以下单成功
	private int securityAmount;
	//备忘录信息
	private String memo;

	/**
	 *
	 * @author itfeng at 2017年4月2日 上午10:54:52
	 * @param stock
	 * @param changeAmount
	 * @param reasonObjId2
	 * @param reasonObjType2
	 * @param reason2
	 */
	public GoodsStockLog(){}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public int getRealAmount_before() {
		return realAmount_before;
	}

	public void setRealAmount_before(int realAmount_before) {
		this.realAmount_before = realAmount_before;
	}

	public int getRealAmount_after() {
		return realAmount_after;
	}

	public void setRealAmount_after(int realAmount_after) {
		this.realAmount_after = realAmount_after;
	}

	public int getAmount_before() {
		return amount_before;
	}

	public void setAmount_before(int amount_before) {
		this.amount_before = amount_before;
	}

	public int getAmount_after() {
		return amount_after;
	}

	public void setAmount_after(int amount_after) {
		this.amount_after = amount_after;
	}

	public int getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(int changeAmount) {
		this.changeAmount = changeAmount;
	}

	public int getSecurityAmount() {
		return securityAmount;
	}

	public void setSecurityAmount(int securityAmount) {
		this.securityAmount = securityAmount;
	}

	public long getReasonObjId() {
		return reasonObjId;
	}

	public void setReasonObjId(long reasonObjId) {
		this.reasonObjId = reasonObjId;
	}

	public int getReasonObjType() {
		return reasonObjType;
	}

	public void setReasonObjType(int reasonObjType) {
		this.reasonObjType = reasonObjType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the oprationType
	 */
	public OprationType getOprationType() {
		return oprationType;
	}

	/**
	 * @param oprationType the oprationType to set
	 */
	public void setOprationType(OprationType oprationType) {
		this.oprationType = oprationType;
	}	
	
	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}


	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public static enum OprationType {
		提交订单,取消订单,ERP同步;
	}
	
}
