package com.bigdata.store.utils;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

public class MyBeanUtils {


	/**
	    *    将数据封装给JavaBean, 支持时间类型伟换 
	 * @param obj
	 * @param map
	 */
	public static void populate(Object obj, Map<String, String[]> map) {
		try {
			
			// 1_创建BeanUtils拉提供时间转换器
			DateConverter dateConverter = new DateConverter();
			// 2_设置需要转换的格式 
			dateConverter.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dateConverter, java.util.Date.class);
			// 4_封装数据 
			BeanUtils.populate(obj, map);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	    *   高级封装， 不需要new javabean 
	 * @param <T>
	 * @param clazz
	 * @param map
	 * @return
	 */
	public static <T> T populate(Class<T> clazz, Map<String, String[]> map) {
		try {

			//使用反射创建实例
			T bean = clazz.newInstance();
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置需要转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);

			BeanUtils.populate(bean, map);

			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
