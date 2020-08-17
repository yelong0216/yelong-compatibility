/**
 * 
 */
package org.yelong.commons.beans;

/**
 * bean 工具类
 * 
 * @see BeanUtilsE
 * @deprecated 冲突的类命名
 * @since 2.0
 */
public class BeanUtils {

	/**
	 * 获取bean属性propertyName的值。 通过标准的get/is方法获取
	 * 
	 * @param bean         bean
	 * @param propertyName fieldName
	 * @return bean->fieldName value
	 * @throws NoSuchMethodException 没有对应的get/is方法
	 */
	public static Object getProperty(Object bean, String propertyName) throws NoSuchMethodException {
		return BeanUtilsE.getProperty(bean, propertyName);
	}

	/**
	 * 设置bean属性propertyName的值。 通过标准的set方法设置值
	 * 
	 * @param bean         bean
	 * @param propertyName fieldName
	 * @param value        fieldName -> value
	 * @throws NoSuchMethodException 没有对应的set方法
	 */
	public static void setProperty(Object bean, String propertyName, Object value) throws NoSuchMethodException {
		BeanUtils.setProperty(bean, propertyName, value);
	}

}
