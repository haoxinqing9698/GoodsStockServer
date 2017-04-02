package net.ifeng.base.util;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;



/**
 * 
 *
 * @author itfeng
 * at 2017年4月2日 下午4:30:30
 * 
 */
public class JSONUtil {
	private static final Logger log = LogManager.getLogger(JSONUtil.class);
	

	private ObjectMapper objectMapper;
	private static final JSONUtil JSONUtilInstance = new JSONUtil();;

	public static JSONUtil getInstance() {
		return JSONUtilInstance;
	}

	private JSONUtil() {
		objectMapper = new ObjectMapper();
		objectMapper
				.configure(
						DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
	}

	public <T> T json2Obj(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			log.error(json + ":"+clazz,e);
		} catch (JsonMappingException e) {
			log.error(json + ":"+clazz,e);
		} catch (IOException e) {
			log.error(json + ":"+clazz,e);
		}
		return null;
	}

	public String obj2Json(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			log.error(obj,e);
		} catch (JsonMappingException e) {
			log.error(obj,e);
		} catch (IOException e) {
			log.error(obj,e);
		}
		return null;
	}

}
