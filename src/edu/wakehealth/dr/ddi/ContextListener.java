package edu.wakehealth.dr.ddi;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import java.util.Timer;
import java.util.TimerTask;

// http://blog.sina.com.cn/s/blog_408ee9b3010005qs.html
public class ContextListener extends HttpServlet implements ServletContextListener {

	private Timer timer;

	public void contextDestroyed(ServletContextEvent event) {
		// 服务停止时调用此函数
		System.out.println("contextDestroyed");
		timer.cancel();
	}

	public void contextInitialized(ServletContextEvent event) {
		System.out.println("contextInitialized");
		// 服务初始时调用此函数
		timer = new Timer(true);
		timer.schedule(new MyTask(), 0, 1000 * 60);
	}

	public void destroy() {
		super.destroy();
		System.out.println("destroy");
	}

	public void init() throws ServletException {
		System.out.println("init");
	}

	public class MyTask extends TimerTask {

		private int i = 0;

		@Override
		public void run() {
			// System.out.println("第" + ++i + "次执行计划任务！");
		}
	}
}