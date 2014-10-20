package test;

import java.util.*;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.*;
import org.nutz.ioc.impl.*;
import org.nutz.ioc.loader.json.JsonLoader;
import org.nutz.lang.Mirror;

import abner.Tagger;
import edu.wakehealth.dr.ddi.dao.*;
import edu.wakehealth.dr.ddi.model.geo.*;
import edu.wakehealth.dr.ddi.utils.*;

public class NutZTest {

	static BasicDao basicDao = new BasicDao();

	public static void main(String[] args) {

		Mirror<GEO_Data> mirror = Mirror.me(GEO_Data.class);
		GEO_Data geo = new GEO_Data();
		mirror.setValue(geo, "setAccession", "Accession");
		System.out.println(geo.getAccession());

		double num = 10 / (double) 3.0;
		System.out.println(num);
		System.out.println(Math.ceil(10 / (double) 3));
		System.out.println(Math.ceil(num));
		System.out.println(Math.ceil(3.33333333));
		
		System.out.println(Tools.toString(new String[] { "a", "b", "c" }, ";"));


		Ioc ioc = new NutIoc(new JsonLoader("conf/datasource.js"));
		DataSource ds = ioc.get(DataSource.class);
		// Dao dao = new NutDao(ds); // 如果已经定义了dao,那么改成dao = ioc.get(Dao.class);
		Dao dao = new NutDao(ds);
		basicDao.setDao(dao);

		BatchUpdateMimeMapType();

		ioc.depose(); // 关闭Ioc容器
	}


	private static void BatchUpdateMimeMapType() {
		Condition cd = Cnd.where("UMLSConceptPrefer", "=", "Tumor cells, uncertain whether benign or malignant");
		// test Condition when next line commented
		cd = Cnd.where("1", "=", "1");
		List<MetaMapGroup> list = basicDao.search(MetaMapGroup.class, cd);
		System.out.println(list.size());

		basicDao.create(MimeMapType.class, false);

		Tagger tagger = new Tagger();

		String[] tags = { "protein", "dna", "rna", "cell_line", "cell_type" };
		for (int i = 0; i < list.size(); i++) {
			String log = "";
			MetaMapGroup map = list.get(i);
			String html = tagger.tagSGML(map.getUMLSConceptPrefer());
			Document doc = Jsoup.parse(html);
			log += i + "," + (map.getUMLSConceptPrefer()) + "," + html.replace("\n", "") + ",";

			for (int ii = 0; ii < tags.length; ii++) {
				Elements links = doc.select(tags[ii]);
				for (int iii = 0; iii < links.size(); iii++) {
					MimeMapType mType = new MimeMapType();
					mType.setType(tags[ii]).setTypeKey(links.get(iii).html())
							.setUMLSConceptPrefer(map.getUMLSConceptPrefer())
							.setUMLSConceptUniqu(map.getUMLSConceptUniqu());
					basicDao.insert(mType);
					log += (mType.getType() + "," + mType.getTypeKey());
				}
			}
			System.out.println(log);
		}
	}
}
