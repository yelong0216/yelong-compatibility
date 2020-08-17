/**
 * 
 */
package org.yelong.core.model.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.yelong.core.annotation.Nullable;
import org.yelong.core.jdbc.sql.condition.ConditionSqlFragment;
import org.yelong.core.jdbc.sql.sort.SortSqlFragment;
import org.yelong.core.model.ModelNullProperty;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.collector.ModelCollectors;
import org.yelong.core.model.manage.exception.PrimaryKeyException;
import org.yelong.core.model.sql.SqlModel;

/**
 * @since 2.0
 */
@Transactional
public interface SqlModelServiceAdapter extends SqlModelService {

	// ==================================================modelService==================================================

	// ==================================================remove==================================================

	/**
	 * 删除model所有的记录
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @return 删除的记录数
	 */
	default Integer removeAll(Class<? extends Modelable> modelClass) {
		return collect(ModelCollectors.removeAll(modelClass));
	}

	/**
	 * 根据主键删除记录
	 * 
	 * 推荐使用 {@link #removeByOnlyPrimaryKey(Class, Object)}，此方法不是一个标准命名的方法。
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param id         主键值
	 * @return <tt>true</tt> 删除记录数>0
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default boolean removeById(Class<? extends Modelable> modelClass, Object id) {
		return collect(ModelCollectors.removeByOnlyPrimaryKeyEQ(modelClass, id)) > 0;
	}

	/**
	 * 根据唯一主键删除记录
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param primaryKeyValue 主键值
	 * @return <tt>true</tt> 删除记录数>0
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default boolean removeByOnlyPrimaryKey(Class<? extends Modelable> modelClass, Object primaryKeyValue) {
		return collect(ModelCollectors.removeByOnlyPrimaryKeyEQ(modelClass, primaryKeyValue)) > 0;
	}

	/**
	 * 根据主键删除多条记录
	 * 
	 * 推荐使用 {@link #removeByOnlyPrimaryKey(Class, Object[])}，此方法不是一个标准命名的方法。
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param ids        主键值数组
	 * @return 删除的记录数
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default Integer removeByIds(Class<? extends Modelable> modelClass, Object[] ids) {
		return collect(ModelCollectors.removeByOnlyPrimaryKeyContains(modelClass, ids));
	}

	/**
	 * 根据主键删除多条记录
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param primaryKeyValues 主键值数组
	 * @return 删除的记录数
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default Integer removeByOnlyPrimaryKey(Class<? extends Modelable> modelClass, Object[] primaryKeyValues) {
		return collect(ModelCollectors.removeByOnlyPrimaryKeyContains(modelClass, primaryKeyValues));
	}

	/**
	 * 根据条件删除记录
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return 删除的记录数
	 */
	default Integer removeByCondition(Class<? extends Modelable> modelClass,
			ConditionSqlFragment conditionSqlFragment) {
		return removeBySqlFragment(modelClass, conditionSqlFragment);
	}

	// ==================================================modify==================================================

	/**
	 * 根据id进行修改数据
	 * 
	 * 推荐使用 {@link #modifyByOnlyPrimaryKey(Modelable)}，此方法不是一个标准命名的方法。<br/>
	 * 
	 * 注：该model所有映射的字段均会进行修改<br/>
	 * 
	 * @param <M>   model type
	 * @param model model
	 * @return <tt>true</tt>修改记录数大于0
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default boolean modifyById(Modelable model) {
		ModelCollectors.setModifySelective(false);
		return collect(ModelCollectors.modifyModelByOnlyPrimaryKeyEQ(model)) > 0;
	}

	/**
	 * 根据唯一的主键值修改数据。这个主键值从model的实例的属性中取。<br/>
	 * 
	 * 注：该model所有映射的字段均会进行修改<br/>
	 * 
	 * @param <M>   model type
	 * @param model model
	 * @return <tt>true</tt>修改记录数大于0
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default <M extends Modelable> boolean modifyByOnlyPrimaryKey(M model) {
		ModelCollectors.setModifySelective(false);
		return collect(ModelCollectors.modifyModelByOnlyPrimaryKeyEQ(model)) > 0;
	}

	/**
	 * 根据id进行修改数据<br/>
	 * 
	 * 选择性：<br/>
	 * 1、属性为null的不会进行添加<br/>
	 * 2、需要设置为null的属性应设置为{@link ModelNullProperty}中对应的属性值<br/>
	 * 
	 * 推荐使用 {@link #modifySelectiveByOnlyPrimaryKey(Modelable)}，此方法不是一个标准命名的方法。
	 * 
	 * @param <M>   model type
	 * @param model 修改的模型对象
	 * @return <tt>true</tt>修改记录数大于0
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default boolean modifySelectiveById(Modelable model) {
		return collect(ModelCollectors.modifyModelByOnlyPrimaryKeyEQ(model)) > 0;
	}

	/**
	 * 根据唯一的主键值修改数据。这个主键值从model的实例的属性中取。<br/>
	 * 
	 * 选择性：<br/>
	 * 1、属性为null的不会进行添加<br/>
	 * 2、需要设置为null的属性应设置为{@link ModelNullProperty}中对应的属性值<br/>
	 * 
	 * @param <M>   model type
	 * @param model 修改的模型对象
	 * @return <tt>true</tt>修改记录数大于0
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default boolean modifySelectiveByOnlyPrimaryKey(Modelable model) {
		return collect(ModelCollectors.modifyModelByOnlyPrimaryKeyEQ(model)) > 0;
	}

	/**
	 * 根据条件修改数据<br/>
	 * 
	 * 这不会修改id<br/>
	 * 
	 * 注：该model所有映射的字段均会进行修改<br/>
	 * 
	 * @param <M>       model type
	 * @param model     model
	 * @param condition 条件
	 * @return 修改的记录数
	 */
	default Integer modifyByCondition(Modelable model, ConditionSqlFragment conditionSqlFragment) {
		return modifyBySqlFragment(model, conditionSqlFragment);
	}

	/**
	 * 根据条件修改数据<br/>
	 * 
	 * 这不会修改主键<br/>
	 * 
	 * 选择性：<br/>
	 * 1、属性为null的不会进行添加<br/>
	 * 2、需要设置为null的属性应设置为{@link ModelNullProperty}中对应的属性值<br/>
	 * 
	 * @param <M>       model type
	 * @param model     model
	 * @param condition 条件
	 * @return 修改的记录数
	 */
	default Integer modifySelectiveByCondition(Modelable model, ConditionSqlFragment conditionSqlFragment) {
		return modifySelectiveBySqlFragment(model, conditionSqlFragment);
	}

	// ==================================================count==================================================

	/**
	 * 根据id查询记录数
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @return 所有的记录数
	 */
	default Long countAll(Class<? extends Modelable> modelClass) {
		return collect(ModelCollectors.countAll(modelClass));
	}

	/**
	 * 根据id查询记录数<br/>
	 * 推荐使用 {@link #countByOnlyPrimaryKey(Class, Object)}，此方法不是一个标准命名的方法。<br/>
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param id         主键值
	 * @return 符合条件的记录数
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default Long countById(Class<? extends Modelable> modelClass, Object id) {
		return collect(ModelCollectors.countByOnlyPrimaryKeyEQ(modelClass, id));
	}

	/**
	 * 根据唯一主键查询记录数
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param primaryKeyValue 主键值
	 * @return 符合条件的记录数
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default Long countByOnlyPrimaryKey(Class<? extends Modelable> modelClass, Object primaryKeyValue) {
		return collect(ModelCollectors.countByOnlyPrimaryKeyEQ(modelClass, primaryKeyValue));
	}

	/**
	 * 根据多个id查询记录数<br/>
	 * 推荐使用 {@link #countByOnlyPrimaryKey(Class, Object[])}，此方法不是一个标准命名的方法。<br/>
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param ids        主键值数组
	 * @return 符合条件的记录数
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default Long countByIds(Class<? extends Modelable> modelClass, Object[] ids) {
		return collect(ModelCollectors.countByOnlyPrimaryKeyContains(modelClass, ids));
	}

	/**
	 * 根据多个主键值查询记录数
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param primaryKeyValues 主键值数组
	 * @return 符合条件的记录数
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default Long countByOnlyPrimaryKey(Class<? extends Modelable> modelClass, Object[] primaryKeyValues) {
		return collect(ModelCollectors.countByOnlyPrimaryKeyContains(modelClass, primaryKeyValues));
	}

	/**
	 * 根据条件查询记录数
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return 符合条件的记录数
	 */
	default Long countByCondition(Class<? extends Modelable> modelClass, ConditionSqlFragment conditionSqlFragment) {
		return countBySqlFragment(modelClass, conditionSqlFragment);
	}

	// ==================================================exist==================================================

	/**
	 * 根据主键查询该记录是否存在<br/>
	 * 
	 * 推荐使用 {@link #existByOnlyPrimaryKey(Class, Object)}，此方法不是一个标准命名的方法。
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param id         主键值
	 * @return <tt>true</tt> 符合条件的记录存在
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default boolean existById(Class<? extends Modelable> modelClass, Object id) {
		return countById(modelClass, id) > 0;
	}

	/**
	 * 根据id查询该记录是否存在
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param primaryKeyValue 主键值
	 * @return <tt>true</tt> 符合条件的记录存在
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default boolean existByOnlyPrimaryKey(Class<? extends Modelable> modelClass, Object primaryKeyValue) {
		return existById(modelClass, primaryKeyValue);
	}

	/**
	 * 根据主键数组查询这些记录是否都存在<br/>
	 * 
	 * 推荐使用 {@link #existByOnlyPrimaryKey(Class, Object[])}，此方法不是一个标准命名的方法。<br/>
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param id         主键值
	 * @return <tt>true</tt> 符合条件的记录都存在
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default boolean existByIds(Class<? extends Modelable> modelClass, Object[] ids) {
		return countByIds(modelClass, ids) == ids.length;
	}

	/**
	 * 根据主键数组查询这些记录是否都存在
	 * 
	 * @param <M>              model type
	 * @param modelClass       model class
	 * @param primaryKeyValues 主键值
	 * @return <tt>true</tt> 符合条件的记录都存在
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	default boolean existByOnlyPrimaryKey(Class<? extends Modelable> modelClass, Object[] primaryKeyValues) {
		return existByIds(modelClass, primaryKeyValues);
	}

	/**
	 * 根据条件查询记录是否存在
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return <tt>true</tt> 符合条件的记录存在
	 */
	default boolean existByCondition(Class<? extends Modelable> modelClass, ConditionSqlFragment condition) {
		return countByCondition(modelClass, condition) > 0;
	}

	// ==================================================find==================================================
	/**
	 * 查询所有记录
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @return 模型对象集合
	 */
	default <M extends Modelable> List<M> findAll(Class<M> modelClass) {
		return collect(ModelCollectors.findAll(modelClass));
	}

	/**
	 * 根据主键查询模型对象<br/>
	 * 
	 * 推荐使用 {@link #findByOnlyPrimaryKey(Class, Object)}，此方法不是一个标准命名的方法。<br/>
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param id         主键值
	 * @return 模型对象
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	@Nullable
	default <M extends Modelable> M findById(Class<M> modelClass, Object id) {
		return collect(ModelCollectors.getModelByOnlyPrimaryKeyEQ(modelClass, id));
	}

	/**
	 * 根据主键查询模型对象
	 * 
	 * @param <M>             model type
	 * @param modelClass      model class
	 * @param primaryKeyValue 主键值
	 * @return 模型对象
	 * @throws PrimaryKeyException modelClass存在的主键数量不等于1
	 */
	@Nullable
	default <M extends Modelable> M findByOnlyPrimaryKey(Class<M> modelClass, Object primaryKeyValue) {
		return collect(ModelCollectors.getModelByOnlyPrimaryKeyEQ(modelClass, primaryKeyValue));
	}

	/**
	 * 根据条件查询模型对象
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return 模型对象集合
	 */
	default <M extends Modelable> List<M> findByCondition(Class<M> modelClass,
			ConditionSqlFragment conditionSqlFragment) {
		return findBySqlFragment(modelClass, conditionSqlFragment, null);
	}

	/**
	 * 根据条件查询第一个模型对象
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @return 模型对象
	 */
	@Nullable
	default <M extends Modelable> M findFirstByCondition(Class<M> modelClass,
			ConditionSqlFragment conditionSqlFragment) {
		return findFirstBySqlFragment(modelClass, conditionSqlFragment, null);
	}

	/**
	 * 根据条件查询模型对象
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sort       排序
	 * @return 模型对象集合
	 */
	default <M extends Modelable> List<M> findBySort(Class<M> modelClass, SortSqlFragment sortSqlFragment) {
		return findBySqlFragment(modelClass, null, sortSqlFragment);
	}

	/**
	 * 根据条件查询模型对象
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @param sort       排序条件
	 * @return 模型对象集合
	 */
	default <M extends Modelable> List<M> findByConditionSort(Class<M> modelClass,
			ConditionSqlFragment conditionSqlFragment, SortSqlFragment sortSqlFragment) {
		return findBySqlFragment(modelClass, conditionSqlFragment, sortSqlFragment);
	}

	// ==================================================findSingleColumn==================================================

	/**
	 * 查询一列数据
	 * 
	 * @param <M>          model type
	 * @param <T>          result type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列名
	 * @param condition    条件
	 * @param sort         排序
	 * @return 查询的列集合
	 */
	@Deprecated
	default <T> List<T> findSingleColumn(Class<? extends Modelable> modelClass, String selectColumn,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable SortSqlFragment sortSqlFragment) {
		return findSingleColumnBySqlFragment(modelClass, selectColumn, conditionSqlFragment, sortSqlFragment);
	}

	/**
	 * 查询一列数据
	 * 
	 * @param <M>          model type
	 * @param <T>          result type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列名
	 * @param condition    条件
	 * @param sort         排序
	 * @return 查询的列集合
	 */
	@Deprecated
	default <T> List<T> findSingleColumnByOnlyPrimaryKey(Class<? extends Modelable> modelClass, String selectColumn,
			Object primaryKeyValue) {
		T value = collect(ModelCollectors.getSingleValueByOnlyPrimaryKeyEQ(modelClass, selectColumn, primaryKeyValue));
		if (null == value) {
			return Collections.emptyList();
		}
		return Arrays.asList(value);
	}

	/**
	 * 查询一列的第一条数据
	 * 
	 * @param <M>          model type
	 * @param <T>          result type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列名
	 * @param condition    条件
	 * @param sort         排序
	 * @return 查询的列的第一条数据
	 */
	@Deprecated
	default <M extends Modelable, T> T findFirstSingleColumn(Class<M> modelClass, String selectColumn,
			@Nullable ConditionSqlFragment conditionSqlFragment, @Nullable SortSqlFragment sortSqlFragment) {
		return findFirstSingleColumnBySqlFragment(modelClass, selectColumn, conditionSqlFragment, sortSqlFragment);
	}

	/**
	 * 查询一列数据
	 * 
	 * @param <M>          model type
	 * @param <T>          result type
	 * @param modelClass   model class
	 * @param selectColumn 查询的列名
	 * @param condition    条件
	 * @param sort         排序
	 * @return 查询的列集合
	 */
	@Deprecated
	default <T> T findFirstSingleColumnByOnlyPrimaryKey(Class<? extends Modelable> modelClass, String selectColumn,
			Object primaryKeyValue) {
		return collect(ModelCollectors.getSingleValueByOnlyPrimaryKeyEQ(modelClass, selectColumn, primaryKeyValue));
	}

	// ==================================================findPage==================================================

	/**
	 * 分页查询
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param pageNum    页码
	 * @param pageSize   页面大小
	 * @return 模型对象集合
	 */
	default <M extends Modelable> List<M> findPage(Class<M> modelClass, Integer pageNum, Integer pageSize) {
		return findPageBySqlFragment(modelClass, null, null, pageNum, pageSize);
	}

	/**
	 * 分页条件查询
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @param pageNum    页码
	 * @param pageSize   页面大小
	 * @return 模型对象集合
	 */
	default <M extends Modelable> List<M> findPageByCondition(Class<M> modelClass,
			ConditionSqlFragment conditionSqlFragment, Integer pageNum, Integer pageSize) {
		return findPageBySqlFragment(modelClass, conditionSqlFragment, null, pageNum, pageSize);
	}

	/**
	 * 分页排序查询
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param sort       排序
	 * @param pageNum    页码
	 * @param pageSize   页面大小
	 * @return 模型对象集合
	 */
	default <M extends Modelable> List<M> findPageBySort(Class<M> modelClass, SortSqlFragment sortSqlFragment,
			Integer pageNum, Integer pageSize) {
		return findPageBySqlFragment(modelClass, null, sortSqlFragment, pageNum, pageSize);
	}

	/**
	 * 分页条件加排序查询
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param condition  条件
	 * @param sort       排序
	 * @param pageNum    页码
	 * @param pageSize   页面大小
	 * @return 模型对象集合
	 */
	default <M extends Modelable> List<M> findPageByConditionSort(Class<M> modelClass,
			ConditionSqlFragment conditionSqlFragment, SortSqlFragment sortSqlFragment, Integer pageNum,
			Integer pageSize) {
		return findPageBySqlFragment(modelClass, conditionSqlFragment, sortSqlFragment, pageNum, pageSize);
	}

	// ==================================================sqlModel==================================================

	// ==================================================remove==================================================

	/**
	 * 根据指定的sql和条件sql删除记录
	 * 
	 * @param <M>      model type
	 * @param <S>      sqlModel type
	 * @param sql      删除的sql语句 注意：这不应该包含where
	 * @param sqlModel sql model 这只会取 sqlModel的条件部分
	 * @return 删除的记录数
	 */
	@Deprecated
	default Integer removeBySqlModel(String sql, SqlModel<? extends Modelable> sqlModel) {
		throw new UnsupportedOperationException();
	}

	// ==================================================exist==================================================

	/**
	 * 根据条件查询是否有符合该条件的记录
	 * 
	 * @param <M>        model type
	 * @param <S>        sqlModel type
	 * @param modelClass 查询的model
	 * @param sqlModel   sql model 这只会取 sqlModel的条件部分
	 * @return <tt>true</tt> 存在
	 */
	default boolean existBySqlModel(Class<? extends Modelable> modelClass, SqlModel<? extends Modelable> sqlModel) {
		return countBySqlModel(modelClass, sqlModel) > 0;
	}

	/**
	 * 根据指定的sql和条件查询是否有符合该条件的记录
	 * 
	 * @param <M>      model type
	 * @param <S>      sqlModel type
	 * @param sql      sql语句。注意：这不应该包含where条件
	 * @param sqlModel sql model 这只会取 sqlModel的条件部分
	 * @return <tt>true</tt> 存在
	 */
	default boolean existBySqlModel(String countSql, SqlModel<? extends Modelable> sqlModel) {
		return countBySqlModel(countSql, null, sqlModel) > 0;
	}

	/**
	 * 根据查询SQL、SqlModel的条件部分查询模型对象
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param selectSql  查询SQL
	 * @param sqlModel   SqlModel取条件、排序部分
	 * @return 查询到的模型对象集合
	 */
	default <M extends Modelable> List<M> findBySqlModel(Class<M> modelClass, String selectSql,
			SqlModel<? extends Modelable> sqlModel) {
		return findBySqlModel(modelClass, selectSql, null, sqlModel);
	}

	/**
	 * 根据查询SQL、SqlModel的条件部分查询第一条模型对象
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param selectSql  查询SQL
	 * @param sqlModel   SqlModel取条件、排序部分
	 * @return 查询到的第一个模型对象。如果不存在则返回null
	 */
	@Nullable
	default <M extends Modelable> M findFirstBySqlModel(Class<M> modelClass, String selectSql,
			SqlModel<? extends Modelable> sqlModel) {
		return findFirstBySqlModel(modelClass, selectSql, null, sqlModel);
	}

	/**
	 * 根据指定的查询SQL、SqlModel的条件、排序部分进行分页查询
	 * 
	 * @param <M>        model type
	 * @param modelClass model class
	 * @param selectSql  查询SQL
	 * @param sqlModel   SqlModel取条件、排序部分
	 * @param sqlModel   SqlModel取条件、排序部分
	 * @param pageNum    页码
	 * @param pageSize   页面大小
	 * @return 模型对象集合
	 */
	default <M extends Modelable> List<M> findPageBySqlModel(Class<M> modelClass, String selectSql,
			SqlModel<? extends Modelable> sqlModel, int pageNum, int pageSize) {
		return findPageBySqlModel(modelClass, selectSql, null, sqlModel, pageNum, pageSize);
	}

}
