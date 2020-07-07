/**
 * 
 */
package org.yelong.commons.beans;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * bean 工具类
 * 
 * @author PengFei
 * @deprecated 冲突的命名
 * @see BeanUtilsE
 */
@Deprecated
public class BeanUtils {

	/**
	 * 获取bean属性propertyName的值。
	 * 通过标准的get/is方法获取
	 * 
	 * @param bean bean
	 * @param propertyName fieldName
	 * @return bean->fieldName value
	 * @throws NoSuchMethodException 没有对应的get/is方法
	 * @see BeanUtilsE#getProperty(Object, String)
	 */
	@Deprecated
	public static Object getProperty(Object bean , String propertyName) throws NoSuchMethodException {
		try {
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, bean.getClass());
			Method readMethod = propertyDescriptor.getReadMethod();
			return readMethod.invoke(bean);
		} catch (Exception e) {
			throw new NoSuchMethodException(bean.getClass() + " not found get or is " + propertyName + " method ");
		}
	}

	/**
	 * 设置bean属性propertyName的值。
	 * 通过标准的set方法设置值
	 * 
	 * @param bean bean
	 * @param propertyName fieldName
	 * @param value fieldName -> value
	 * @throws NoSuchMethodException 没有对应的set方法
	 * @see BeanUtilsE#setProperty(Object, String, Object)
	 */
	@Deprecated
	public static void setProperty(Object bean , String propertyName , Object value) throws NoSuchMethodException {
		try {
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, bean.getClass());
			Method writeMethod = propertyDescriptor.getWriteMethod();
			writeMethod.invoke(bean,value);
		} catch (Exception e) {
			throw new NoSuchMethodException(bean.getClass() + " not found set " + propertyName + " method ");
		}
	}

}
