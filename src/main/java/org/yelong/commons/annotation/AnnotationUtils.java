/**
 * 
 */
package org.yelong.commons.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.yelong.commons.lang.annotation.AnnotationUtilsE;

/**
 * @see AnnotationUtilsE
 * @deprecated 冲突的类命名
 * @since 2.0
 */
public class AnnotationUtils {

	/**
	 * <pre>
	 * 获取 class 标注的指定类型的注解
	 * 递归：如果 class 不存在该注解，将在 class 的父类中进行查找直至Object类
	 * 注意：如果注解不存在将返回 <code>null</code>
	 * </pre>
	 * 
	 * @param <A>        annotation type
	 * @param c          class
	 * @param annotation 获取的注解类型
	 * @param recursive  是否递归
	 * @see Class#getAnnotation(Class)
	 */
	public static <A extends Annotation> A getAnnotation(Class<?> c, Class<A> annotation, boolean recursive) {
		return AnnotationUtilsE.getAnnotation(c, annotation, recursive);
	}

	/**
	 * <p>
	 * 获取字段上面的注解
	 * </p>
	 * 
	 * 如果字段未标注该类型的注解则返回 <code>null</code>
	 * 
	 * @param <A>        annotation type
	 * @param field      字段
	 * @param annotation 注解类型
	 * @return 字段存在annotation类型的注解则返回 annotation ，否则返回 <code>null</code>
	 */
	public static <A extends Annotation> A getAnnotation(Field field, Class<A> annotation) {
		return AnnotationUtilsE.getAnnotation(field, annotation);
	}

}
