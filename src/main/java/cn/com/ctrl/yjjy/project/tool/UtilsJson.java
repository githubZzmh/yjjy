package cn.com.ctrl.yjjy.project.tool;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings("all")
public class UtilsJson {

	/**
	 * 
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description: 从一个JSON 对象字符格式中得到一个java对象
	 * </p>
	 * @param <T>
	 * 
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static <T> T toObject(String jsonString, Class<T> pojoCalss) {
		if (StringUtils.isEmpty(jsonString))
			return null;
		T pojo;
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		pojo = JSONObject.toJavaObject(jsonObject, pojoCalss);
		return pojo;
	}

	/**
	 * 把Java 对象序列化成Json字符串
	 * 
	 * @param javaObj
	 * @return
	 */
	public static String toJson(Object javaObj) {
		String json = JSONObject.toJSONString(javaObj);
		return json;
	}

	/**
	 * 把Json字符串转换成Map对象
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> toMap(String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		Set<String> keys = jsonObject.keySet();
		String key;
		Object value;
		Map<String, Object> valueMap = new HashMap<String, Object>();
		for (String e : keys) {
			key = e;
			value = jsonObject.get(e);
			valueMap.put(key, value);
		}
		return valueMap;
	}

	/**
	 * 把List对象中的某两个字段弄成Json字符串
	 * 
	 * @param data
	 * @param pojoCalss
	 * @param idField
	 * @param nameField
	 * @return
	 */
	public static String toJson(List<?> data, Class<?> pojoCalss, String idField, String nameField) {
		StringBuffer result = new StringBuffer();
		result.append("[");
		if (data != null && data.size() > 0) {
			for (int i = 0; i < data.size(); i++) {
				try {
					result.append("[");

					Class<?> c = Class.forName(pojoCalss.getName());
					Object o = c.newInstance();
					o = data.get(i);
					Method[] methods = c.getDeclaredMethods();

					String idMethod = "get" + idField.toUpperCase().substring(0, 1) + idField.substring(1);

					String nameMethod = "get" + nameField.toUpperCase().substring(0, 1) + nameField.substring(1);

					String idValue = "";
					String nameValue = "";

					for (int t = 0; t < methods.length; t++) {
						if (methods[t].getName().equals(idMethod)) {
							Object invResult = methods[t].invoke(o, null);
							if (null != invResult) {
								idValue = invResult.toString();
							}
						}
						if (methods[t].getName().equals(nameMethod)) {
							Object invResult = methods[t].invoke(o, null);
							if (null != invResult) {
								nameValue = "\"" + invResult.toString() + "\"";
							}
						}
					}

					result.append(idValue);
					result.append(",");
					result.append(nameValue);
					result.append("]");

					if (i < data.size() - 1)
						result.append(",");

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

			}
		}
		result.append("] ");
		return result.toString();
	}

	public static String toJson(List data, Class pojoCalss) {
		StringBuffer result = new StringBuffer();
		result.append("[");
		if (data != null && data.size() > 0) {
			for (int i = 0; i < data.size(); i++) {
				try {
					result.append("{");

					Class c = Class.forName(pojoCalss.getName());
					Object o = c.newInstance();
					o = data.get(i);
					Field[] fields = c.getDeclaredFields();

					Method[] methods = c.getDeclaredMethods();
					if (fields != null && fields.length > 0) {

						for (int j = 0; j < fields.length; j++) {
							result.append(fields[j].getName() + ":");
							for (int t = 0; t < methods.length; t++) {
								String temp = "get" + fields[j].getName().toUpperCase().substring(0, 1)
										+ fields[j].getName().substring(1);
								if (methods[t].getName().equals(temp)) {
									result.append("\"");
									Object invResult = methods[t].invoke(o, null);
									if (null != invResult) {
										result.append(invResult.toString());
									}
									result.append("\"");
									if (j < fields.length - 1)
										result.append(",");
								}
							}
						}

					}
					result.append("} ");
					if (i < data.size() - 1)
						result.append(",");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

			}
		}
		result.append("] ");
		return result.toString();
	}

	/**
	 * 
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description: 从json数组中得到相应java数组
	 * </p>
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Object[] toObjectArray(String jsonString) {
		JSONArray jsonArray = JSONArray.parseArray(jsonString);
		return jsonArray.toArray();
	}

	public static List toList(String jsonString, Class pojoCalss) {
		List result = new ArrayList();
		Object[] dtoArray = UtilsJson.toObjectArray(jsonString);
		if (dtoArray != null && dtoArray.length > 0) {
			for (int i = 0; i < dtoArray.length; i++) {
				try {
					Class c = Class.forName(pojoCalss.getName());
					Object o = c.newInstance();
					o = UtilsJson.toObject(dtoArray[i].toString(), pojoCalss);
					result.add(o);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

			}
		}
		return result;
	}

	public static List<String> toStrList(String jsonString){
		List<String> result = new ArrayList<String>();
		Object[] dtoArray = UtilsJson.toObjectArray(jsonString);
		if (dtoArray != null && dtoArray.length > 0) {
			for (int i = 0; i < dtoArray.length; i++) {
				result.add(dtoArray[i].toString());
			}
		}
		return result;
	}

	
	public static void main(String[] args) {
//		Map<String, Object> map = UtilsJson
//				.toMap("{    \"pivotNamedList\": [        {            \"value\": [                {                    \"value\": \"内蒙古\",                    \"field\": \"produceProvince\",                    \"count\": 1,                    \"pivot\": [                        {                            \"value\": \"呼和浩特市\",                            \"field\": \"produceCity\",                            \"count\": 1,                            \"pivot\": [                                {                                    \"value\": \"清水河县\",                                    \"field\": \"produceArea\",                                    \"count\": 1                                }                            ]                        }                    ]                },                {                    \"value\": \"山东省\",                    \"field\": \"produceProvince\",                    \"count\": 1,                    \"pivot\": [                        {                            \"value\": \"济南市\",                            \"field\": \"produceCity\",                            \"count\": 1,                            \"pivot\": [                                {                                    \"value\": \"历城区\",                                    \"field\": \"produceArea\",                                    \"count\": 1                                }                            ]                        }                    ]                }            ],            \"key\": \"produceProvince,produceCity,produceArea\"        },        {            \"value\": [                {                    \"value\": \"\",                    \"field\": \"ex_carCat\",                    \"count\": 1,                    \"pivot\": [                        {                            \"value\": \"\",                            \"field\": \"ex_carBrand\",                            \"count\": 1,                            \"pivot\": [                                {                                    \"value\": \"\",                                    \"field\": \"ex_carSeries\",                                    \"count\": 1,                                    \"pivot\": [                                        {                                            \"value\": \"\",                                            \"field\": \"ex_carType\",                                            \"count\": 1                                        }                                    ]                                }                            ]                        }                    ]                },                {                    \"value\": \"重卡\",                    \"field\": \"ex_carCat\",                    \"count\": 1,                    \"pivot\": [                        {                            \"value\": \"中国重汽\",                            \"field\": \"ex_carBrand\",                            \"count\": 1,                            \"pivot\": [                                {                                    \"value\": \"豪沃\",                                    \"field\": \"ex_carSeries\",                                    \"count\": 1,                                    \"pivot\": [                                        {                                            \"value\": \"豪沃2010\",                                            \"field\": \"ex_carType\",                                            \"count\": 1                                        }                                    ]                                }                            ]                        }                    ]                }            ],            \"key\": \"ex_carCat,ex_carBrand,ex_carSeries,ex_carType\"        }    ]}");
//		String json = UtilsJson.toJson(map);
//		map = (Map<String, Object>) UtilsJson.toObject(json, Map.class);
//		System.out.println(map);
		String json = "[\"ns2.nic.xn--vuQ861B\",\"ns3.nic.xn--vuQ861B\"]";
		List<String> strList = toStrList(json);
		System.out.println(strList);
		
	}
}
