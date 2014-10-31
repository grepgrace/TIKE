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
public class Index extends BaseController {

	@At("/")
	@GET
	public View index(HttpServletRequest req) {
		return new JspView("jsp.index");
	}

	



}
