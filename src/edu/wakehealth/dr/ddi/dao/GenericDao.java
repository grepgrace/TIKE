package edu.wakehealth.dr.ddi.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.nutz.dao.Chain;
import org.nutz.dao.Condition;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.IocBean;
/**
 * 基本数据库操作类
 * @author Administrator
 *
 */
@IocBean
public class GenericDao<T> extends BasicDao {
	
	Class<T> c;
	
	@SuppressWarnings("unchecked")
	public GenericDao() {
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
			c = (Class<T>) parameterizedType[0];
		}
	}

	public boolean delById(int id) {
		return super.delById(id, c);
	}

	public T find(int id) {
		return super.find(id, c);
	}

	public List<T> search(String orderby) {
		return super.search(c, orderby);
	}

	public List<T> search(Condition condition) {
		return super.search(c, condition);
	}

	public List<T> search() {
		return super.search(c);
	}

	public List<T> searchByPage(int currentPage, int pageSize, String orderby) {
		return super.searchByPage(c, currentPage, pageSize, orderby);
	}

	public List<T> searchByPage(Condition condition, int currentPage, int pageSize) {
		return super.searchByPage(c, condition, currentPage, pageSize);
	}

	public boolean update(Chain chain, Condition condition) {
		return super.update(c, chain, condition);
	}

	public int searchCount() {
		return super.searchCount(c);
	}

	public int searchCount(Condition condition) {
		return super.searchCount(c, condition);
	}

	public List<T> searchByIds(String ids, String orderby) {
		return super.searchByIds(c, ids, orderby);
	}

	public List<T> searchByIds(int[] ids, String orderby) {
		return super.searchByIds(c, ids, orderby);
	}

	public void deleteByIds(String ids) {
		super.deleteByIds(c, ids);
	}

	public T findByCondition(Condition condition) {
		return super.findByCondition(c, condition);
	}

	public List<T> searchPageByLike(String value, String orderby, int currentPage,
			int pageSize) {
		return super.searchPageByLike(c, value, orderby, currentPage, pageSize);
	}

	public int searchPageByLike(String value) {
		return super.searchPageByLike(c, value);
	}

	public List<T> searchByPageLike(String fieldName, String value,
			int currentPage, int pageSize) {
		return super.searchByPageLike(c, fieldName, value, currentPage, pageSize);
	}

	public int searchByPageLike(String fieldName, String value) {
		return super.searchByPageLike(c, fieldName, value);
	}

	public List<T> searchByPage(String fieldName, String value, int currentPage,
			int pageSize) {
		return super.searchByPage(c, fieldName, value, currentPage, pageSize);
	}

	public T findByCondition(String fileName, String value) {
		return super.findByCondition(c, fileName, value);
	}

	public boolean updateRelation(String fieldName, Chain chain, Condition condition) {
		return super.updateRelation(c, fieldName, chain, condition);
	}

	public List<T> searchByRelation(String joinTabel, String cloumnName,
			Condition condition, SqlExpressionGroup group, String orderby, int currentPage,
			int pageSize) {
		return super.searchByRelation(c, joinTabel, cloumnName, condition, group, orderby,
				currentPage, pageSize);
	}

	public int searchCount(String joinTabel, String cloumnName,
			Condition condition, SqlExpressionGroup group, String orderby) {
		return super.searchCount(c, joinTabel, cloumnName, condition, group, orderby);
	}
	
	
}
