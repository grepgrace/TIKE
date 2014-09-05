package test;

import org.nutz.mvc.View;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;
import org.nutz.mvc.view.JspView;

@Fail("json")
@IocBy(type=ComboIocProvider.class,args={
	"*org.nutz.ioc.loader.json.JsonLoader","conf/datasource.js",
	"*org.nutz.ioc.loader.annotation.AnnotationIocLoader","edu.wakehealth.dr.ddi"})
@Modules(scanPackage=true) 
// auto scan submodules that has @At Annotation and in the package (include subpackage) of MainModule
//@Encoding(input="UTF-8",output="UTF-8") // page encode (default UTF-8)
//@At("test")
public class TestModule {

	@At("/hello")
	// url entry
	@Ok("jsp:jsp.hello")
	// view when success
	public View Hello() {
		return new JspView("hello");
	}

	@At({ "/hello/?" })
	// url entry
	@Ok("jsp:jsp.hello")
	// view when success
	// @Fail("jsp:error.404") // view when fail
	public String doHello(String str) {
		return "Hello Nutz " + str;
	}

}