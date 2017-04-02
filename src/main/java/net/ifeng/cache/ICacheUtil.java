package net.ifeng.cache;

import org.springframework.stereotype.Service;



/**
 * 
 *
 * @author itfeng
 * at 2017年4月2日 上午8:53:40
 * 
 */
@Service
public interface ICacheUtil {

	/**
	 * 写缓存操作
	 * 
	 * @author itfeng at 2017年4月2日 上午8:55:06
	 * @param key
	 * @param cacheBean
	 */
	public void set(String key,CacheBean cacheBean);
	/**
	 * 
	 * 
	 * @author itfeng at 2017年4月2日 上午9:00:00
	 * @param key
	 * @param cacheBean
	 * @param expSeconds 过期时长，以秒为单位
	 */
	public void set(String key,CacheBean cacheBean,int expSeconds);
	/**
	 * 从缓存获取一个对象
	 * 
	 * @author itfeng at 2017年4月2日 上午8:57:25
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T get(String key,Class<T> clazz);
	/**
	 * 清除缓存
	 * 
	 * @author itfeng at 2017年4月2日 上午9:16:17
	 * @param key
	 */
	public void remove(String key);

}
