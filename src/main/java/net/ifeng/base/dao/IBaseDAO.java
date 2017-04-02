package net.ifeng.base.dao;




/**
 * 
 *
 * @author itfeng
 * at 2017年4月2日 上午9:26:46
 * 
 */
public interface IBaseDAO<T> {
	/**
	 * 将对象添加到数据库
	 * 
	 * @author itfeng at 2017年4月2日 上午9:23:41
	 * @param object
	 * @return
	 */
	public boolean add(T object);
	/**
	 * 更新数据库中的对象
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
	public  T findById(long id);
}
