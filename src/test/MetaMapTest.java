package test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.neo4j.graphdb.Node;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.lang.Dumps;

import edu.wakehealth.dr.ddi.model.geo.GEO_Data;
import edu.wakehealth.dr.ddi.model.geo.MetaMap;
import edu.wakehealth.dr.ddi.utils.Tools;
import gov.nih.nlm.nls.metamap.AcronymsAbbrevs;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;

public class MetaMapTest extends NutZTest {

	public static void main(String[] args) throws IOException {
		setDao();

		Condition cd = Cnd.where("1", "=", "1");
		// cd = Cnd.wrap(" Id in (88, 360, 483, 806, 807, 817, 820)");
		List<GEO_Data> list = basicDao.search(GEO_Data.class, cd);
		// System.out.println("list.size:" + list.size());
		for (int i = 0; i < list.size(); i++) {
			GEO_Data geo = list.get(i);
			System.out.println("\r\n" + (i + 1) + "/" + list.size() + " " + geo.getId());
			String ids = ""; // like 'C1513882','C0007600'
			if (geo.getSummary() == null)
				return;
			String text = geo.getSummary().toString();
			String filepath = Tools.dir + "tmp\\geo" + geo.getId() + ".txt";
			if (!text.endsWith("\r\n"))
				text = text + "\r\n";
			Tools.writeFile(filepath, text);
			List<MetaMap> metamaps = Tools.getMimeMaps(new File(filepath));
			for (int j = 0; j < metamaps.size(); j++) {
				MetaMap map = metamaps.get(j);
				map.setGEOId(geo.getId());
				basicDao.insert(map);
			}
		}

		closeDao();

		// getMimeMapLocal("This SuperSeries is composed of the SubSeries listed below");
		
	}

	public static String getMimeMapLocal(String terms) {
		MetaMapApi api = new MetaMapApiImpl();
		api.setHost("127.0.0.1");
		api.setPort(8066);
		api.setOptions("-AN -V USAbase");
		List<gov.nih.nlm.nls.metamap.Result> resultList = api.processCitationsFromString(terms);
		for (int i = 0; i < resultList.size(); i++) {
			gov.nih.nlm.nls.metamap.Result result = resultList.get(i);
			System.out.println(result.toString());
			List<AcronymsAbbrevs> aaList;
			try {
				aaList = result.getAcronymsAbbrevs();
				if (aaList.size() > 0) {
					System.out.println("Acronyms and Abbreviations:");
					for (AcronymsAbbrevs e : aaList) {
						System.out.println("Acronym: " + e.getAcronym());
						System.out.println("Expansion: " + e.getExpansion());
						System.out.println("Count list: " + e.getCountList());
						System.out.println("CUI list: " + e.getCUIList());
					}
				} else {
					System.out.println(" None. aaList.size() == 0");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return "";
	}


}
