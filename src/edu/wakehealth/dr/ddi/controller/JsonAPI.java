package edu.wakehealth.dr.ddi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.view.UTF8JsonView;

import edu.wakehealth.dr.ddi.model.Article;
import edu.wakehealth.dr.ddi.model.Base_breast_cs;
import edu.wakehealth.dr.ddi.model.PageData;


@IocBean
@InjectName
@At()
public class JsonAPI extends BaseController {

	@At()
	public View GetArticle(Integer perPage, Integer page, Integer offset, HttpServletRequest req) {
		Condition cd = Cnd.where("1", "=", 1);
		String queryString = "queries";// queries[search]=name
		String queryName = getSquareBracketsName(req, queryString);
		if ("search".equals(queryName)){queryName = getSquareBracketsValue(req, queryString,queryName);}
		
		if (queryName != null) {((Cnd) cd).and("article_title", "LIKE", "%" + queryName + "%");}

		return GetTable(Article.class, cd, perPage, page, offset, req);
	}
	
	@At()
	public View Getbase_breast_cs(Integer perPage, Integer page, Integer offset, HttpServletRequest req) {
		Condition cd = Cnd.where("1", "=", 1);
		String queryString = "queries";// queries[search]=name
		String queryName = getSquareBracketsName(req, queryString);
		if ("search".equals(queryName)){queryName = getSquareBracketsValue(req, queryString,queryName);}
		
		if (queryName != null) {((Cnd) cd).and("name", "LIKE", "%"+queryName+"%");}

		return GetTable(Base_breast_cs.class, cd, perPage, page, offset, req);
	}
	
	private <T> View GetTable(Class<T> class1, Condition cd, Integer perPage,
			Integer page, Integer offset, HttpServletRequest req) {

		String orderString = "sorts";// sorts[id]=1
		String orderby = getSquareBracketsName(req, orderString);
		Boolean isAsc = null;
		if (orderby != null)isAsc = "1".equals(getSquareBracketsValue(req, orderString,orderby));

		if (orderby != null) {
			if (isAsc != null && isAsc.booleanValue()){cd = ((Cnd) cd).asc(orderby);}
			else ((Cnd) cd).desc(orderby);
		}

		int totalRecordCount = basicDao.searchCount(class1, cd);
		List<T> list = basicDao.searchByPage(class1, cd, page, perPage);
		PageData<T> data = new PageData<T>().setRecords(list)
				.setQueryRecordCount(totalRecordCount)
				.setTotalRecordCount(list.size());
		UTF8JsonView jsonView = new UTF8JsonView(null);
		jsonView.setData(data);
		return jsonView;
	}

}
