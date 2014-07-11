package edu.wakehealth.dr.ddi.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.View;
import org.nutz.mvc.view.JspView;

import edu.wakehealth.dr.ddi.dao.BasicDao;


public class BaseController {

	@Inject
	protected BasicDao basicDao;
	
	public void setBasicDao(BasicDao basicDao) {
		this.basicDao = basicDao;
	}
	
	public void log(String string) {
		if(string!=null&&!string.trim().equals("")){
			StringBuilder sbBuilder = new StringBuilder();
			sbBuilder.append(append(new Date().toString()))
					.append(append(this.getClass().getSimpleName()))
					.append(append(string));
			System.out.println(sbBuilder.toString());
		}
	}

	private String append(String string) {
		return string + " ";
	}

	protected View setView(HttpServletRequest req, String page) {
		req.setAttribute("view", page);
		return new JspView("jsp._layout");
	}
}
