package net.ifeng.queue;

import java.io.Serializable;
import java.util.Date;

import net.itfeng.util.HostUtil;




/**
 * 
 * 消息对象，msg为具体的消息内容对象
 * @author itfeng
 * at 2017年4月2日 下午4:57:42
 * 
 */
public class Message<T> implements Serializable{

	private static final long serialVersionUID = 5545834523623808193L;

	private String className;
	
	private T msg;
	
	// 生成消息的节点名
	private String message_host;
	// 生成消息的时间
	private String message_timeStr;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public T getMsg() {
		return msg;
	}
	public void setMsg(T msg) {
		this.msg = msg;
	}
	public String getMessage_host() {
		return message_host;
	}
	public void setMessage_host(String message_host) {
		this.message_host = message_host;
	}
	public String getMessage_timeStr() {
		return message_timeStr;
	}
	public void setMessage_timeStr(String message_timeStr) {
		this.message_timeStr = message_timeStr;
	}
	
	public Message(){}
	
	public Message(T msg){
		this.className=msg.getClass().getName();
		this.msg = msg;
		this.message_timeStr =new Date().toString();
		this.message_host = HostUtil.getHostIP();
	}
	

}
