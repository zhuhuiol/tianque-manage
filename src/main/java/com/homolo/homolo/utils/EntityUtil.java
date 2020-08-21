package com.homolo.homolo.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONArray;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * entityUtil.
 * zh.
 */
public class EntityUtil {

    /**
     * map to entity.
     * @param params map 或者 jsonObject 转entity
     * @param t entity
     * @param <T>
     * @return entity.
     */
    public static <T> T objToEntity(Object params, Class<?> t) {
        if (params instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) params;
            try {
                Class<?> clazz = Class.forName(t.getName());
                Field[] fields = clazz.getDeclaredFields();
                Object o = clazz.newInstance();
                for (Field field : fields) {
                    Object val = map.get(field.getName());
                    if (val == null || StringUtils.isBlank(val.toString())) {
                        continue;
                    }
                    String type = field.getType().toString();
                    if (type.endsWith("String")) {
                        val = val.toString();
                    } else if (type.endsWith("Date")) {
                        val = DateUtil.parseDate(val.toString(), "yyyy-MM-dd hh:mm:ss");
                    } else if (type.endsWith("int")) {
                        val = Integer.parseInt(val.toString());
                    }
                    String setMethodName = "set"
                            + field.getName().substring(0, 1).toUpperCase()
                            + field.getName().substring(1);

                    clazz.getMethod(setMethodName, field.getType()).invoke(o, val);
                }
                return (T) o;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    /**
     * entity to map.
     * @param object entity
     * @return
     */
    public static Map entityToMap(Object object) {
        HashMap<String, Object> map = new HashMap<>();
        if (object == null) {
            return map;
        }
        Class t = object.getClass();
        Field[] declaredFields = t.getDeclaredFields();
        for (Field field : declaredFields) {
            try {
                field.setAccessible(true);
                String name = field.getName();
                Object val = field.get(object);
                if (val == null) {
                    continue;
                }
                if (field.getType().toString().endsWith("Date")) {
                    String format = DateFormatUtils.format((Date) val, "yyyy-MM-dd hh:mm:ss");
                    map.put(name, format);
                } else {
                    map.put(name, val);
                }
                String displayMethod = "getDisplay"+ field.getName().substring(0, 1).toUpperCase()
                        + field.getName().substring(1);
                Method[] declaredMethods = t.getDeclaredMethods();
                for (Method method : declaredMethods) {
                    if (displayMethod.equals(method.getName())) {
                        Object invoke = method.invoke(object);
                        map.put("display" + name, invoke);
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * תint.
     * @param str
     * @param defaultVal
     * @return
     */
    public static Integer strToInt(String str, Integer defaultVal) {
        if (StringUtils.isBlank(str)) {
            return defaultVal;
        }
        return Integer.parseInt(str);
    }

    /**
     * map 键 转大写.
     * @param orgMap map
     * @param delKeys 需要去除的key
     * @return map
     */
    public static Map<String, Object> transformUpperCase(Map<String, Object> orgMap, List<String> delKeys) {
        Map<String, Object> resultMap = new HashMap<>();
        if (orgMap == null || orgMap.isEmpty()) {
            return resultMap;
        }
        Set<Map.Entry<String, Object>> entries = orgMap.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            if (delKeys != null && delKeys.contains(next.getKey().toLowerCase())) {
                continue;
            }
            String newKey = next.getKey().toLowerCase();
            newKey = newKey.replace("_", "");
            resultMap.put(newKey, next.getValue());
        }

        return resultMap;
    }


    /**
     * 去掉map的空值.
     * @param orgMap
     * @return
     */
    public static Map<String, Object> filterNullValue(Map<String, Object> orgMap) {
        Map<String, Object> resultMap = new HashMap<>();
        Set<Map.Entry<String, Object>> entries = orgMap.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            if (next.getValue() == null) {
                continue;
            }
            if (next.getValue() instanceof Date) {
                resultMap.put(next.getKey(), next.getValue());
            } else if (next.getValue() instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) next.getValue();
                if (jsonArray.isEmpty()) {
                    continue;
                }
                List list = filterListNullValue(jsonArray.toList());
                if (list.isEmpty()) {
                    continue;
                }
                resultMap.put(next.getKey(), list);
            } else {
                if (next.getValue() == null || "undefined".equals(next.getValue()) || StringUtils.isBlank((String) next.getValue())) {
                    continue;
                }
                resultMap.put(next.getKey(), next.getValue());
            }
        }
        return resultMap;
    }


    /**
     * 去掉list的空值.
     * @param list
     * @return
     */
    public static List filterListNullValue(List list) {
        List resultList = new ArrayList();
        for (Object o : list) {
            if (o == null || StringUtils.isBlank(o.toString())) {
                continue;
            }
            resultList.add(o);
        }
        return resultList;
    }



}
