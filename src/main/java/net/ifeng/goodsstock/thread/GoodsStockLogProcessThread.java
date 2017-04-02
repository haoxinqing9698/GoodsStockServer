package net.ifeng.goodsstock.thread;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.ifeng.goodsstock.bean.GoodsStockLog;
import net.ifeng.goodsstock.dao.IGoodsStockDAO;
import net.ifeng.queue.IQueueUtil;
import net.ifeng.queue.Message;

import org.apache.log4j.Category;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * 
 * 用于处理库存日志队列的线程，系统启动时自动启动
 * 主要功能为将日志写入数据库并更新数据库中的库存对象里的realAmount和amount
 * @author itfeng
 * at 2017年4月2日 下午5:20:33
 * 
 */
@Component
public class GoodsStockLogProcessThread extends Thread {
	private static final Logger logger = LogManager.getLogger(GoodsStockLogProcessThread.class);
	
	@Autowired
	private IQueueUtil queueUtil;
	
	@Autowired
	private IGoodsStockDAO goodsStockDAO;
	
	public static boolean flag = true;

	@Override
	public void run(){
		while(flag){
			GoodsStockLog log = null;
			try {
				Message<GoodsStockLog> msg = queueUtil.bpop(GoodsStockLog.QUEUENAME, 10L);
				log = msg.getMsg();
				process(log);
			} catch (Exception e) {
				if(log != null ){
					logger.error(log, e);
				}else{
					logger.error("null", e);
				}
			}
		}
	}
	/**
	 * 目前这里没有使用事务
	 * 
	 * @author itfeng at 2017年4月2日 下午6:21:31
	 * @param log
	 * @return
	 */
	private boolean process(GoodsStockLog log){
		boolean result1 = goodsStockDAO.addGoodsStockLog(log);
		boolean result2 = goodsStockDAO.updateGoodsStockAmount(log.getGoodsId(),
				log.getWarehouseId(), log.getAmount_after(), log.getRealAmount_after());
		
		return result1 && result2;
	}
	/**
	 * 实例化之后启动本线程
	 * 
	 * @author itfeng at 2017年4月2日 下午5:56:15
	 */
	@PostConstruct
	private void init(){
		this.start();
	}
	
	/**
	 * 销毁被对象时执行
	 */
	@PreDestroy
	public void destroy(){
		flag = false;
		logger.error("GoodsStockLogProcessThread对象准备销毁……");
	}

}
