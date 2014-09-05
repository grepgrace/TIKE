package test;

import java.io.*;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;

import edu.wakehealth.dr.ddi.utils.Tools;
import gov.nih.nlm.nls.metamap.AcronymsAbbrevs;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;

public class MetaMapTest {

	public static void main(String[] args) {
		try {
			String dir = "C:\\Users\\kelu\\Downloads\\public_mm\\";
			String cmd = "cmd /c " + dir + "bin\\metamap14.bat -AN " + dir + "sample.txt";
			System.out.println(cmd);
			// Runtime.getRuntime().exec(cmd);
			// Execute command
			Process child = Runtime.getRuntime().exec(cmd);

			InputStream in = child.getInputStream();
			System.out.println(Tools.getString(in));
	} catch (IOException e) {
		}
		
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
