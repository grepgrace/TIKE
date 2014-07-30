package edu.wakehealth.dr.ddi.controller;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Mirror;
import org.nutz.lang.Strings;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;

import edu.wakehealth.dr.ddi.dao.DbManagerDao;
import edu.wakehealth.dr.ddi.model.Article;
import edu.wakehealth.dr.ddi.model.DBTable;


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
		try {
			name = Strings.upperFirst(name);
			Class<?> class1 = Class.forName(Article.class.getPackage().getName() + "." + name);
			Field[] fields = Mirror.me(class1).getFields();
			if(perPage==null){perPage=1;}
			List<?> list = dbManagerDao.searchByPage(class1, null, 1, perPage);
			req.setAttribute("name", name);
			req.setAttribute("fields", fields);
			req.setAttribute("listJson", Json.toJson(list));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return setView(req, "dbmanager/index");
	}


}
