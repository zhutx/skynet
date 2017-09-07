package com.moredian.fishnet.device.convertor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ObjectUtil {
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) {
		if (map == null)
			return null;
		try {
			Object obj = beanClass.newInstance();
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				int mod = field.getModifiers();
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
					continue;
				}
				field.setAccessible(true);
				field.set(obj, map.get(field.getName()));
			}
			return obj;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> objectToMap(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Field[] declaredFields = obj.getClass().getDeclaredFields();
			for (Field field : declaredFields) {
				field.setAccessible(true);
				map.put(field.getName(), field.get(obj));
			}
			return map;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return Collections.emptyMap();
	}
}
