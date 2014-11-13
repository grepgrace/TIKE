package test;

import java.io.*;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;

import edu.wakehealth.dr.ddi.model.geo.GEO_Data;
import edu.wakehealth.dr.ddi.model.geo.MetaMap;
import edu.wakehealth.dr.ddi.utils.Tools;

public class MetaMapTest {

	public static void main(String[] args) throws IOException {
		String text = "Analysis of the response of the basal epithelium derived breast cancer line ME16C to either doxorubicin or 5-fluorouracil. Cells examined 12, 24, and 36 hours following treatment.";
		System.out.println(Tools.getMimeMap(text));

	}

}
