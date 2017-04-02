package net.ifeng.base.util;

import java.util.HashMap;
import java.util.Map;




/**
 * 利用ThreadLocal技术实现线程共享
 * 最好使用Filter技术，在filter的开始引入ThreadLocal共享对象，在filter结束时调用remove方法清除本线程的共享数据，避免内存溢出
 *
 * @author itfeng
 * at 2017年4月2日 上午10:24:09
 * 
 */
public class ThreadLocalUtil {

	private static ThreadLocal<Map<Object,Object>> context = new ThreadLocal<Map<Object,Object>>();
	/**
	 * 将线程共享数据通过ThreadLocal保存到Map中
	 * 调用本方法后务必通过调用remove()方法清除ThreadLocal中的线程数据
	 * @author itfeng at 2017年4月2日 上午10:36:07
	 * @param key
	 * @param value
	 */
	public static void setParam(Object key,Object value){
		Map<Object,Object> params = context.get();
		if(params == null){
			params = new HashMap<Object,Object>();
			context.set(params);
		}
		params.put(key, value);
	}
	/**
	 * 获取通过ThreadLocal保存的线程共享数据
	 * 
	 * @author itfeng at 2017年4月2日 上午10:38:33
	 * @param key
	 * @return
	 */
	public static Object getParam(Object key){
		Map<Object,Object> params = context.get();
		if(params!=null){
			return params.get(key);
		}
		return null;
	}
	/**
	 * 清除本线程的共享数据
	 * 
	 * @author itfeng at 2017年4月2日 上午10:39:37
	 */
	public void remove(){
		context.remove();
	}
	

}
