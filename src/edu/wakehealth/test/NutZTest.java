package edu.wakehealth.test;

import java.util.List;

import org.nutz.lang.Mirror;

import edu.wakehealth.dr.ddi.model.geo.GEO_Data;
import edu.wakehealth.dr.ddi.utils.Tools;

public class NutZTest {

	public static void main(String[] args) {

		System.out.println("gsm123".replaceAll("\\d+", ""));

		// www.ncbi.nlm.nih.gov/geo/query/acc.cgi?acc=GSE10893&targ=self&form=text&view=full
		String xml = "";
		System.out.println("List".equals(List.class.getSimpleName()));

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

		// original seating chart with both sets of seats combined
		// int[] schart = new int[3000];
		// for (int i = 0; i < 3000; i++)
		// schart[i] = i;
		// // populating first class seats
		// int[] fcseats = Arrays.copyOfRange(schart, 0, 100);
		// // checking array is correctly populated
		// for (int i = 0; i <= fcseats.length; i++)
		// System.out.println(fcseats[i]);

//		// xml 2 class
		xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
//				+ "<!DOCTYPE eSearchResult PUBLIC \"-//NLM//DTD esearch 20060628//EN\" \"http://eutils.ncbi.nlm.nih.gov/eutils/dtd/20060628/esearch.dtd\">"
//				+ "<eSearchResult>   <retMax>20</retMax>   <count>49053</count>      <retStart>10</retStart>    <IdList>        <Id>4083</Id>          <Id>4083</Id>          <Id>4083</Id>        <Id>4082</Id>    </IdList>"
//				+ "<TranslationSet>        <Translation>            <From>breast cancer</From>            <To>breast neoplasms[MeSH Terms] OR breast cancer[All Fields]</To>        </Translation>    </TranslationSet>    <TranslationStack>        <TermSet>            <Term>breast neoplasms[MeSH Terms]</Term>            <Field>MeSH Terms</Field>            <Count>49053</Count>            <Explode>Y</Explode>        </TermSet>        <TermSet>            <Term>breast cancer[All Fields]</Term>            <Field>All Fields</Field>            <Count>32719</Count>            <Explode>N</Explode>        </TermSet>        <OP>OR</OP>        <OP>GROUP</OP>    </TranslationStack>    <QueryTranslation>breast neoplasms[MeSH Terms] OR breast cancer[All Fields]</QueryTranslation>"
//				+ "</eSearchResult>";
//		xml = xml.toLowerCase().replaceAll("(?i)<!DOCTYPE((.|\n|\r)*?)\">", "");
//		System.out.println(xml);
//		// xml = xml.replace("  ", "");
//
//		eSearchResult t = JAXB.unmarshal(new StringReader(xml),eSearchResult.class);
//		System.out.println(Dumps.obj(t));


//		xml = "<Item Name=\"Accession\" Type=\"String\">GDS4388</Item>";
//		//xml = xml.toLowerCase().replaceAll("(?i)<!DOCTYPE((.|\n|\r)*?)\">", "");
//		System.out.println(xml);
//		Item t1 = JAXB.unmarshal(new StringReader(xml), Item.class);
//		System.out.println(Dumps.obj(t1));
//
//
//		xml = "" + "<DocSum><Id>4388</Id><Item Name=\"Accession\" Type=\"String\">GDS4388</Item>"
//		// + "<Item Name=\"GDS\" Type=\"String\">4388</Item><Item Name=\"Samples\" Type=\"List\"><Item Name=\"Sample\" Type=\"Structure\"><Item Name=\"Accession\" Type=\"String\">GSM873559</Item><Item Name=\"Title\" Type=\"String\">SIN3A_siRNA_LIF1h_rep1</Item></Item><Item Name=\"Sample\" Type=\"Structure\"><Item Name=\"Accession\" Type=\"String\">GSM873563</Item><Item Name=\"Title\" Type=\"String\">SIN3A_siRNA_LIF1h_rep2</Item></Item></Item>"
//				+ "</DocSum>" ;
//		xml = xml.toLowerCase().replaceAll("(?i)<!DOCTYPE((.|\n|\r)*?)\">", "");
//		System.out.println(xml);
//
//		DocSum t11 = JAXB.unmarshal(new StringReader(xml), DocSum.class);
//		System.out.println(Dumps.obj(t11));
//
//
//		xml = "" + "<eSummaryResult>" + xml + "</eSummaryResult>";
//		xml = xml.toLowerCase().replaceAll("(?i)<!DOCTYPE((.|\n|\r)*?)\">", "");
//		System.out.println(xml);
//
//		eSummaryResult t111 = JAXB.unmarshal(new StringReader(xml), eSummaryResult.class);
//		System.out.println(Dumps.obj(t111.getdocsum()));
//		System.out.println(Dumps.obj(t111.getdocsum().getItem()));
//
//		// Quartz Test
//		try {
//			// Grab the Scheduler instance from the Factory
//			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//
//			// and start it off
//			scheduler.start();
//
//			scheduler.shutdown();
//
//		} catch (SchedulerException se) {
//			se.printStackTrace();
//		}

		// eSearch(null);
	}
}
