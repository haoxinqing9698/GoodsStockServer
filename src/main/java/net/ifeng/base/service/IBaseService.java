package net.ifeng.base.service;




/**
 * 
 *
 * @author itfeng
 * at 2017年4月2日 上午9:20:33
 * 
 */
public interface IBaseService<T> {
	/**
	 * 添加对象
	 * 
	 * @author itfeng at 2017年4月2日 上午9:23:41
	 * @param object
	 * @return
	 */
	public boolean add(T object);
	/**
	 * 更新对象
	 * 
	 * @author itfeng at 2017年4月2日 上午9:24:56
	 * @param object
	 * @return
	 */
	public boolean update(T object);
	/**
	 * 通过ID查询对象
	 * 
	 * @author itfeng at 2017年4月2日 上午9:25:28
	 * @param id
	 * @return
	 */
	public T findById(long id);
	
}
