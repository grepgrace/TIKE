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

import com.tike.dao.*;
import com.tike.model.geo.*;
import com.tike.utils.*;

import abner.Tagger;

public class NutZTest {

	public static BasicDao basicDao = null;
	static Ioc ioc = new NutIoc(new JsonLoader("conf/datasource.js"));

	public static void setDao() {
		if (basicDao == null) {
			basicDao = new BasicDao();
			DataSource ds = ioc.get(DataSource.class);
			// Dao dao = new NutDao(ds); // 如果已经定义了dao,那么改成dao = ioc.get(Dao.class);
			Dao dao = new NutDao(ds);
			basicDao.setDao(dao);
		}
		// ioc.depose(); // 关闭Ioc容器
	}

	public static void closeDao() {
		if (basicDao != null)
			ioc.depose(); // 关闭Ioc容器
	}

	public static void main(String[] args) throws Exception {
		// BatchUpdateMimeMapType();
		setDao();
		batchUpdatemateMapLocText();

	}


	private static void batchUpdatemateMapLocText() {
		Condition cd = Cnd.where("UMLSConceptPrefer", "=", "Abdominal Cavity");
		// test Condition when next line commented
		// cd = Cnd.where("1", "=", "1");
		List<MetaMap> list = basicDao.search(MetaMap.class, cd);
		System.out.println(list.size());

		for (int i = 0; i < list.size(); i++) {
			MetaMap map = list.get(i);
			String ti = map.getTriggerInformation();
			if (ti == null || ti.trim() == ""
					||ti.indexOf("[")==-1||ti.indexOf("]")==-1||ti.indexOf("-")==-1)
				continue;
			ti = ti.substring(1, ti.length() - 1);
			String[] ts = ti.split(",");
			for (int j = 0; j < ts.length; j++) {
				String text = ti.split("-")[0];
				System.out.println(text);
				ts[0] = text.substring(1, text.length() - 1);
			}
			ti = Arrays.toString(ts);
			ti = ti.substring(1, ti.length() - 1);
			map.setLocText(ti);
			System.out.println(map.getLocText());
			// basicDao.update(map);
		}
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
