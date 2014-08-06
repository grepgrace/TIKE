package edu.wakehealth.dr.ddi.controller;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Mirror;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.view.HttpStatusView;
import org.nutz.mvc.view.UTF8JsonView;
import org.reflections.*;
import org.reflections.scanners.*;
import org.reflections.util.*;

import edu.wakehealth.dr.ddi.dao.*;
import edu.wakehealth.dr.ddi.model.*;


@IocBean
@InjectName
@At()
public class DBManager extends BaseController {

	@Inject
	protected DbManagerDao dbManagerDao;

	@At("/")
	@GET 
	public View index(Integer perPage, HttpServletRequest req) {
		Mirror<DBTable> mirror = Mirror.me(DBTable.class);
		Field[] fields = mirror.getFields();

		req.setAttribute("fields", fields);
		req.setAttribute("listJson", Json.toJson(dbManagerDao.getArticles()));
		return setView(req, "dbmanager/index");
	}

	@At("/?")
	@GET
	public View indexPara(String name, Integer perPage, HttpServletRequest req) {
		Class<?> class1 = getClass(name);
		if (class1 != null) {
			Field[] fields = Mirror.me(class1).getFields();
			if(perPage==null){perPage=1;}
			req.setAttribute("name", name);
			req.setAttribute("fields", fields);
			return setView(req, "dbmanager/index");
		}
		return HttpStatusView.HTTP_500;
	}

	@At()
	public View GetJson(String model, Integer perPage, Integer page, Integer offset, HttpServletRequest req) {
		Class<?> class1 = getClass(model);
		if (class1 != null) {

			Condition cd = Cnd.where("1", "=", 1);
			String queryString = "queries";// queries[search]=name
			String queryName = getSquareBracketsName(req, queryString);
			if ("search".equals(queryName)){queryName = getSquareBracketsValue(req, queryString,queryName);}
			
			if (queryName != null) {
				if (class1.equals(Article.class)) {
					((Cnd) cd).and("article_title", "LIKE", "%" + queryName + "%");
				} else if (class1.equals(Base_breast_cs.class)) {
					((Cnd) cd).and("name", "LIKE", "%" + queryName + "%");
				}
			}

			req.setAttribute("model", model);

			return GetTable(class1, cd, perPage, page, offset, req);
		}
		return HttpStatusView.HTTP_500;
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

	private Class<?> getClass(String name) {
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());
		Reflections reflections = new Reflections(new ConfigurationBuilder()
		    .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
		    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
		    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(Article.class.getPackage().getName()))));
		Set<Class<? extends Object>> classes = reflections.getSubTypesOf(Object.class);
		for (Class<?> class1 : classes) {
			if (class1.getSimpleName().toLowerCase().equals(name.toLowerCase()))
				return class1;
		}

		return null;
	}
}
