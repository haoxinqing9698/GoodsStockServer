package net.ifeng.cache.impl;

import net.ifeng.base.util.JSONUtil;
import net.ifeng.cache.CacheBean;
import net.ifeng.cache.ICacheUtil;
import net.itfeng.util.RedisUtil;

import org.springframework.stereotype.Service;



/**
 * 
 * 基于redis的缓存操作
 * @author itfeng
 * at 2017年4月2日 下午4:29:21
 * 
 */
@Service
public class RedisCacheUtil implements ICacheUtil {

	private RedisUtil redisUtil;
	/* (non-Javadoc)
	 * @see net.ifeng.cache.ICacheUtil#set(java.lang.String, net.ifeng.cache.CacheBean)
	 * @author itfeng at 2017年4月2日 下午4:29:21
	 * @param key
	 * @param cacheBean
	 */
	public void set(String key, CacheBean cacheBean) {
		redisUtil.set(key, JSONUtil.getInstance().obj2Json(cacheBean));
	}

	/* (non-Javadoc)
	 * @see net.ifeng.cache.ICacheUtil#set(java.lang.String, net.ifeng.cache.CacheBean, int)
	 * @author itfeng at 2017年4月2日 下午4:29:21
	 * @param key
	 * @param cacheBean
	 * @param expSeconds
	 */
	public void set(String key, CacheBean cacheBean, int expSeconds) {
		redisUtil.setex(key, JSONUtil.getInstance().obj2Json(cacheBean),expSeconds);
	}

	/* (non-Javadoc)
	 * @see net.ifeng.cache.ICacheUtil#get(java.lang.String, java.lang.Class)
	 * @author itfeng at 2017年4月2日 下午4:29:21
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T get(String key, Class<T> clazz) {
		String jsonString = redisUtil.get(key);
		if(jsonString!=null){
			return JSONUtil.getInstance().json2Obj(jsonString, clazz);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see net.ifeng.cache.ICacheUtil#remove(java.lang.String)
	 * @author itfeng at 2017年4月2日 下午4:29:21
	 * @param key
	 */
	public void remove(String key) {
		redisUtil.del(key);
	}

}
