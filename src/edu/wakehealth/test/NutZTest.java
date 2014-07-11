package edu.wakehealth.test;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;
import org.nutz.lang.Dumps;

import edu.wakehealth.dr.ddi.model.Article;

public class NutZTest {

	public static void main(String[] args) {
		Ioc ioc = new NutIoc(new JsonLoader("edu/wakehealth/test/test.js"));
		Article article = ioc.get(Article.class, "article");
		System.out.printf("%s - [%s]\n", Dumps.obj(article));
	}

}