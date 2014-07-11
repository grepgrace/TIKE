package edu.wakehealth.dr.ddi;

import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

@Fail("json")
@IocBy(type=ComboIocProvider.class,args={
	"*org.nutz.ioc.loader.json.JsonLoader","conf/datasource.js",
	"*org.nutz.ioc.loader.annotation.AnnotationIocLoader","edu.wakehealth.dr.ddi"})
@Modules(scanPackage=true) 
// auto scan submodules that has @At Annotation and in the package (include subpackage) of MainModule
//@Encoding(input="UTF-8",output="UTF-8") // page encode (default UTF-8)
public class MainModule {

}