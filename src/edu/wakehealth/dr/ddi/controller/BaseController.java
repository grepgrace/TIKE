package edu.wakehealth.dr.ddi.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.View;
import org.nutz.mvc.view.JspView;

import edu.wakehealth.dr.ddi.dao.BasicDao;


@IocBean
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
	
	/*
	 * get "search" from url like ?queries[search]=name
	 */
	protected String getSquareBracketsName(HttpServletRequest req,String startStr) {
		String v = null;
		Enumeration<String> names = req.getParameterNames();
		while (names.hasMoreElements()) {
			String paramName = (String) names.nextElement();
			Pattern p = Pattern.compile(startStr + "\\[(.*?)\\]");
			Matcher m = p.matcher(paramName);
			while (m.find()) {
				v = m.group(1);
				System.out.println(v);
			}
		}
		return v;
	}

	/*
	 * get "name" from url like ?queries[search]=name
	 */
	protected String getSquareBracketsValue(HttpServletRequest req,String startStr,String value) {
		String v = null;
		String[] vs = req.getParameterValues(startStr + "[" + value + "]");
		if (vs.length == 1)
			v = vs[0];
		try {
			v = URLDecoder.decode(new String(req.getParameter(startStr + "[" + value + "]").getBytes("iso-8859-1")), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return v;
	}
}
