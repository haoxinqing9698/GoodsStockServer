package net.ifeng.queue;




/**
 * 
 *
 * @author itfeng
 * at 2017年4月2日 下午4:48:20
 * 
 */
public interface IQueueUtil {
	/**
	 * 向消息队列中写入一条消息，从队尾写入
	 * 
	 * @author itfeng at 2017年4月2日 下午5:05:55
	 * @param queueName
	 * @param message
	 * @return 是否写入成功
	 */
	public boolean push(String queueName,Message message);
	/**
	 * 从消息队列中弹出一条消息，从队头弹出,如果队列中没有消息则返回空
	 * 
	 * @author itfeng at 2017年4月2日 下午5:07:04
	 * @param queueName 队列名
	 * @return Message
	 */
	public Message pop(String queueName);
	
	/**
	 * 从消息队列中弹出一条消息，如果没有消息则等待消息如队列
	 * 如果到超时时间还没有获取大消息则返回空
	 * 
	 * @author itfeng at 2017年4月2日 下午5:22:26
	 * @param queueName 队列名
	 * @param timeout 单位：毫秒
	 * @return Message
	 */
	public Message bpop(String queueName,long timeout);
}
