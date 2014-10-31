package edu.wakehealth.dr.ddi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.view.JspView;


@IocBean
@InjectName
public class Test extends BaseController {

	@At("/test")
	@GET
	public View index1Get(HttpServletRequest req, String checkbox, String name, Integer step, String submit) {
		if (step == null)step = 1;
		if ("previous".equals(submit))step = step > 4 ? 4 : step - 1;
		else if ("next".equals(submit))step = step + 1;

		index(req, checkbox, name, step);

		return setView(req, "test/index");
	}

	@At("/test")
	@POST
	public View index1Post(HttpServletRequest req, String checkbox, String name, Integer step, String submit) {
		if(step==null)step=0;
		if("previous".equals(submit)) step = step>4?4:step-1;
		else if("next".equals(submit)) step = step+1;
	
		index(req, checkbox, name, step);
		if (step > 4) {
			index(req, checkbox, name, step);
			List<String> list = new ArrayList<String>();
			list.add("a");
			list.add("b");
			list.add("c");
			req.setAttribute("results", list);
			return setView(req, "test/result");
		}
		return setView(req, "test/index");
	}
	
	void index(HttpServletRequest req, String checkbox, String name, Integer step) {
		req.setAttribute("step", step);
		req.setAttribute("name", name);
		req.setAttribute("checkbox", checkbox);
	}



}
