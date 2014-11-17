package test;

import java.io.*;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;

import com.tike.model.geo.GEO_Data;
import com.tike.model.geo.MetaMap;
import com.tike.utils.Tools;

public class MetaMapTest extends NutZTest {

	public static void main(String[] args) throws IOException {

		// get output from MetaMap tool
		String input = "Analysis of the response of the basal epithelium derived breast cancer line ME16C to either doxorubicin or 5-fluorouracil. Cells examined 12, 24, and 36 hours following treatment.";
		String output = Tools.getMimeMap(input);
		System.out.println(output);

		// connect database
		setDao();

		// convert output to MetaMap object
		List<MetaMap> metamaps = Tools.getMimeMaps(output);
		for (int j = 0; j < metamaps.size(); j++) {
			MetaMap map = metamaps.get(j);
			// insert Metamap object to database
			basicDao.insert(map);
		}
	}

}
