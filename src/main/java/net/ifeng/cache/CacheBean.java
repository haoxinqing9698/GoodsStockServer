package net.ifeng.cache;

import java.io.Serializable;
import java.util.Date;

import net.itfeng.util.HostUtil;



/**
 * 
 * 需要缓存的对象的父类，所有需要缓存的对象均需要继承本接口
 * 两个字段主要记录缓存对象是在什么时间哪台服务器上创建的
 * @author itfeng
 * at 2017年3月27日 下午8:21:11
 * 
 */
public abstract class CacheBean implements Serializable {

	private static final long serialVersionUID = 3901876737640579179L;
	// 缓存节点名
	private String cache_host;
	// 写缓存时间
	private String cache_timeStr;
	
	public CacheBean(){
		this.cache_host = HostUtil.getHostIP();
		this.cache_timeStr = new Date().toString();
	}
	
	/**
	 * @return 缓存创建时间
	 */
	public String getCache_timeStr() {
		return cache_timeStr;
	}
	/**
	 * @param cache_timeStr the cache_timeStr to set
	 */
	public void setCache_timeStr(String cache_timeStr) {
		this.cache_timeStr = cache_timeStr;
	}
	/**
	 * @return 创建缓存的节点IP
	 */
	public String getCache_host() {
		return cache_host;
	}
	/**
	 * @param 
	 */
	public void setCache_host(String cache_host) {
		this.cache_host = cache_host;
	}
	
	
}
