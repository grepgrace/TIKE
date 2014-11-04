package edu.wakehealth.dr.ddi.controller;

import edu.wakehealth.dr.ddi.dao.GEODao;
import edu.wakehealth.dr.ddi.model.Base_breast_cs;
import edu.wakehealth.dr.ddi.model.geo.GEO_Data;
import edu.wakehealth.dr.ddi.model.geo.GEO_Link_GDS2Key;
import edu.wakehealth.dr.ddi.model.geo.MetaMap;
import edu.wakehealth.dr.ddi.utils.Tools;
import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub;
import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub.*;
import gov.nih.nlm.nls.metamap.AcronymsAbbrevs;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.skr.GenericObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.axis2.AxisFault;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.FailToSetValueException;
import org.nutz.lang.Mirror;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.view.UTF8JsonView;

@IocBean
@InjectName
@At()
public class GEOeutils extends BaseController {

	@Inject
	protected GEODao gEODao;

	EUtilsServiceStub service;
	String db = "gds";
	// test by esearchTest(883)
	int maxIdsNum = 800; // ids[] => 1,2 length < 8105(looks like 8*1024)
	String log_idsStr = "";
	String keyword = "breast cancer ";
	String retMaxDefault = "100";

	@At()
	public View getAllGEOByKey(Integer perPage, Integer page, Integer offset, HttpServletRequest req) {
		UTF8JsonView jsonView = new UTF8JsonView(null);
		
		try {
			service = new EUtilsServiceStub();
			String[] idsList = new String[0];

			// get all gds id about keys
			List<Base_breast_cs> csList = basicDao.search(Base_breast_cs.class);
			// add key here when getGDSAllIds finish, avoid getGDSAllIds twice
			String[] key_gotData = new String[0]; 
			for (Base_breast_cs cs : csList) {
				String keys = cs.getKeywords();
				// if (cs.isGeiAllGEOData())continue;
				if (keys == null || keys.trim() == "") continue;
				for (String key : keys.split(",")) {
					if (key == null || key.trim() == "")continue;
					if (Arrays.binarySearch(key_gotData, key)>-1)continue;
					String[] ids = getGDSAllIds(keyword + key, retMaxDefault);
					key_gotData = ArrayUtils.add(key_gotData, key);
					if(ids==null)continue;
					saveGeoIdAndLink(cs, key, ids);
					idsList = ArrayUtils.addAll(idsList, ids);
				}
				basicDao.update(cs.setGeiAllGEOData(true));
			}

			saveGeoData(idsList);
			jsonView.setData("success");
		} catch (RemoteException e) {
			println(e.toString());
			jsonView.setData(e.getMessage());
		}

		return jsonView;
	}

	// GDS4388		000004388
	// GPL96		100000096
	// GSE25065		200025065
	// GSM615828	300615828
	private String getGEOCode(Integer id) {
		if (id > 400000000) return "";
		else if (id > 300000000) return "GSM"+(id-300000000);
		else if (id > 200000000) return "GSE"+(id-200000000);
		else if (id > 100000000) return "GPL"+(id-100000000);
		else return "GDS"+id;
	}

	ESearchResult getESearchResult(String keyword, String retStart, String retMax) throws RemoteException {
		println(keyword);
		ESearchRequest eSearchReq = new ESearchRequest();
		eSearchReq.setTerm(keyword);
		eSearchReq.setDb(db);
		if (retStart != null && !"".equals(retStart.trim())) {eSearchReq.setRetStart(retStart);}
		eSearchReq.setRetMax(retMax);
		return service.run_eSearch(eSearchReq);
	}
	String[] getGDSAllIds(String keyword, String retMax) throws RemoteException {
		// get 0 - 100
		ESearchResult eResult = getESearchResult(keyword, null, retMax);
		String[] ids = new String[Integer.valueOf(eResult.getCount())];
		if (ids.length > 0)
			ids = eResult.getIdList().getId();
		if (Integer.valueOf(retMax) < Integer.valueOf(eResult.getCount())) {
			// get (0 - 100) + (100 - count)
			eResult = getESearchResult(keyword, retMax, eResult.getCount());
			ids = ArrayUtils.addAll(ids, eResult.getIdList().getId());
		}
		println("getGDSAllIds: key:'" + keyword + "' ids.length: " + ids.length);
		return ids;
	}

	private void println(String string) {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
		String date = dt.format(new Date());
		System.out.println(date + " " + string);
	}

	DocSumType[] getAllGEOs(String[] ids) throws RemoteException {
		DocSumType[] result = new DocSumType[0];
		int step = (int) Math.ceil(ids.length / (double) maxIdsNum);
		for (int i = 0; i < step; i++) {
			int len = i * maxIdsNum + maxIdsNum<ids.length?i * maxIdsNum + maxIdsNum:ids.length;
			String[] _ids = Arrays.copyOfRange(ids, i * maxIdsNum, len);
			result = ArrayUtils.addAll(result, getGEOs(_ids));
			println("getAllGEOs: " + i + "/" + step
					+ " " + i * maxIdsNum + "/"+ ids.length
					+ " ids.length: " + ids.length);
		}
		return result;
	}

	DocSumType[] getGEOs(String[] ids) throws RemoteException {
		String idsStr = Arrays.toString(ids);
		idsStr = idsStr.substring(1, idsStr.length() - 1);
		idsStr = idsStr.toString().replace(" ", "").replace(",null", "");
		println(idsStr);
		log_idsStr = log_idsStr + "," + idsStr;
		// max id size is 1000
		ESummaryRequest eSummaryRequest = new ESummaryRequest();
		eSummaryRequest.setId(idsStr);
		eSummaryRequest.setDb(db);
		ESummaryResult eSummaryResult = service.run_eSummary(eSummaryRequest);
		return eSummaryResult.getDocSum();
	}

	/*
	 * save GEO detail data
	 */
	private void saveGeoData(String[] ids) throws RemoteException {
		// remove all geo id like geo.isUpdate()==true
		Condition cd = Cnd.where("isUpdated", "=", "1");
		List<GEO_Data> existGeoList = basicDao.search(GEO_Data.class, cd);
		String[] existIds = new String[existGeoList.size()];
		for (int i = 0; i < existIds.length; i++) {
			existIds[i] = existGeoList.get(i).getId() + "";
		}
		List<String> list = new ArrayList<String>(Arrays.asList(ids));
		list.removeAll(Arrays.asList(existIds));
		ids = list.toArray(existIds);
		println("saveGeoData: " + " ignore alreadly updated geo count" + existIds.length);

		DocSumType[] geos = getAllGEOs(ids);
		for (DocSumType doc : geos) {
			int id = Integer.valueOf(doc.getId());
			GEO_Data geo = basicDao.find(id, GEO_Data.class);
			if (geo == null) {
				geo = new GEO_Data().setId(id);
				basicDao.insert(geo);
			}
			if (geo.isUpdated())continue;

			ItemType[] items = doc.getItem();
			if (items != null) {
				Mirror<GEO_Data> mirror = Mirror.me(GEO_Data.class);
				for (ItemType item : items) {
					try {mirror.setValue(geo, item.getName(), item.getItemContent());} 
					catch (FailToSetValueException e) {
						//System.err.println(e.getMessage());
					}
				}
				geo.setGSM(Tools.toString(getIds(Tools.get("Samples", items).getItem(),"Accession"), ";"));
				geo.setRelations(Tools.toString(getIds(Tools.get("Relations", items).getItem(),"TargetObject"), ";"));
				geo.setUpdated(true);
				basicDao.update(geo);
			}
		}
		println("saveGeoData: " + "ids.length: " + ids.length);
	}

	String[] getIds(ItemType[] list,String name) {
		if(list!=null){
			String[] ids = new String[list.length];
			for (int i = 0; i < list.length; i++) {
				ids[i] = Tools.get(name, list[i].getItem()).getItemContent();
				ids[i] = ids[i].replace(ids[i].replaceAll("\\d+", ""), "");
			}
			return ids;
		}
		return null;
	}
	/*
	 * save GEO ID and link about key
	 */
	private void saveGeoIdAndLink(Base_breast_cs cs, String key, String[] ids) {
		if(ids==null)return;
		for (String id : ids) {
			try {
			Integer _id = Integer.valueOf(id);
			GEO_Data gds = new GEO_Data().setId(_id);
			gds.setAccession(getGEOCode(_id));
			Condition cd = Cnd.where("id", "=", gds.getId());
			if(basicDao.searchCount(GEO_Data.class, cd)==0){basicDao.insert(gds);} 
			else {basicDao.update(gds);}

			// save GEO_Link_GDS2Key
			GEO_Link_GDS2Key link = new GEO_Link_GDS2Key().setGeoid(_id).setKeyName(key)
					.setKeyid(cs.getId());
			cd = Cnd.where("geoid", "=", gds.getId())
					//.and("Keyid", "=", cs.getId())
					.and("KeyName", "=", key);
			basicDao.delete("GEO_Link_GDS2Key", cd);
			basicDao.insert(link);
			} catch (Exception e) {
				println(e.toString());
			}
		}
	}
	
	@At()
	public View TestgetSummaryKeys(HttpServletRequest request) {
		// get random GSE, make sum(length(summary))<500,000
		// upload sun(summary) to MetaMap to get key frequency
		// save the result to db
		// loop 10 times
		// Analysis the result to find key
		UTF8JsonView jsonView = new UTF8JsonView(null);

		ServletContext context = request.getServletContext();
		File dir = new File(context.getRealPath("/temp"));
		try {
			if (dir.exists())FileUtils.cleanDirectory(dir);
			for (int i = 0; i < 30; i++) {
				List<GEO_Data> listGeo = gEODao.getRand();
				StringBuilder sBuilder = new StringBuilder();
				int wi = 0;
				while ((sBuilder.toString() + (listGeo.get(wi).getSummary())).length() * 1.001 < 500000
						&& wi < listGeo.size()) {
					sBuilder.append(listGeo.get(wi).getSummary());
					sBuilder.append("\r\n");
					wi++;
				}
				// System.out.println("sBuilder: " + sBuilder.toString());
				// System.out.println("sBuilder.length: " +
				// sBuilder.toString().length());

				String filePath = dir + "/" + new Date().getTime() + ".txt";
				String str = Tools.removeAllnonASCIIChars(sBuilder.toString());
				Tools.writeFile(filePath, str);

				Tools.removeAllnonASCIIChars(filePath);

//				getMimeMap(str);
//				str = new String(Files.readAllBytes(Paths.get(str + ".out")));
//
//				List<MimeMap> listMM = MimeMap.getList(str);
//				for (MimeMap mimeMap : listMM) {
//					basicDao.insert(mimeMap);
//				}

			}
		} catch (IOException e) {
			println(e.toString());
			jsonView.setData(e.getMessage());
		}

		return jsonView;
	}

	@At()
	public View TestGetMaxGeoData(String retMax) {
		UTF8JsonView jsonView = new UTF8JsonView(null);

		maxIdsNum = Integer.valueOf(retMax);
		try {
			service = new EUtilsServiceStub();
			saveGeoData(getESearchResult(keyword, null, retMax).getIdList().getId());
			jsonView.setData(log_idsStr + "    " + log_idsStr.length());
		} catch (RemoteException e) {
			println(e.toString());
			jsonView.setData(e.getMessage());
		}

		return jsonView;
	}

	@At()
	public View TestgetGDSAllIds(String key) {
		UTF8JsonView jsonView = new UTF8JsonView(null);
		if(key==null||"".equals(key.trim()))key = keyword;
		try {
			service = new EUtilsServiceStub();
			String[] ids = getGDSAllIds(key, retMaxDefault);
			String idsStr = Arrays.toString(ids);
			jsonView.setData("length:" + idsStr.length() + " size:" + ids.length + " " + idsStr);
		} catch (Exception e) {
			println(e.toString());
			jsonView.setData(e.getMessage());
		}
		return jsonView;
	}

	@At()
	public View TestsaveGeoDataIdAndLink() {
		UTF8JsonView jsonView = new UTF8JsonView(null);
		try {
			service = new EUtilsServiceStub();
			Base_breast_cs cs = basicDao.find(1, Base_breast_cs.class);
			String key = "Tumor Size";

			String[] ids = getGDSAllIds(keyword + key, retMaxDefault);
			saveGeoIdAndLink(cs, key, ids);
			String idsStr = Arrays.toString(ids);
			jsonView.setData("length:" + idsStr.length() + " size:" + ids.length + " " + idsStr);
		} catch (RemoteException e) {
			println(e.toString());
			jsonView.setData(e.getMessage());
		}
		return jsonView;
	}

	@At()
	public View TestsaveGeoData(String[] ids) {
		UTF8JsonView jsonView = new UTF8JsonView(null);
		try {
			service = new EUtilsServiceStub();
			Base_breast_cs cs = basicDao.find(1, Base_breast_cs.class);
			String key = "Tumor Size";

			if (ids == null || ids.length == 0 || (ids.length == 1 && "".equals(ids[0])))
				ids = getGDSAllIds(keyword + key, retMaxDefault);
			saveGeoIdAndLink(cs, key, ids);
			saveGeoData(ids);
			String idsStr = Arrays.toString(ids);
			jsonView.setData("length:" + idsStr.length() + " size:" + ids.length + " " + idsStr);
		} catch (AxisFault e) {
			println(e.toString());
			jsonView.setData(e.getMessage());
		} catch (RemoteException e) {
			println(e.toString());
			jsonView.setData(e.getMessage());
		}
		return jsonView;
	}

	@At()
	public View TestgetIsGeiAllGEOData() {
		UTF8JsonView jsonView = new UTF8JsonView(null);
		// get all gds id about keys
		List<Base_breast_cs> csList = basicDao.search(Base_breast_cs.class);
		for (Base_breast_cs cs : csList) {
			println(cs.getId() + " " + cs.getName() + " " + cs.isGeiAllGEOData() + "");
		}

		jsonView.setData("success");
		return jsonView;
	}

	@At()
	@GET
	public View TestgetMimeMap(HttpServletRequest req) {
		return setView(req, "test/MimeMap");
	}

	@At()
	@POST
	public View TestgetMimeMap(String InputText, HttpServletRequest req) {
		if (InputText != null && !"".equals(InputText.trim()))
			req.setAttribute("result", getMimeMapLocal(InputText));

		return setView(req, "test/MimeMap");
	}

	public static void getMimeMap(String inputFile) throws IOException {
		String dir = "C:\\Users\\kelu\\Downloads\\public_mm\\";
		String cmd = "cmd /c " + dir + "bin\\metamap14.bat -N " + inputFile;
		System.out.println(cmd);
		// Runtime.getRuntime().exec(cmd);
		// Execute command
		Process child = Runtime.getRuntime().exec(cmd);

		InputStream in = child.getInputStream();
		System.out.println(Tools.toString(in));
	}

	// Fielded MetaMap Indexing (MMI) Output, XML Output, and Human Readable
	// Output are not supported by MetaMap Java API.
	public String getMimeMapLocal(String terms) {
		MetaMapApi api = new MetaMapApiImpl();
		api.setHost("192.168.56.102");
		api.setPort(8066);
		api.setOptions("-N");
		List<gov.nih.nlm.nls.metamap.Result> resultList = api.processCitationsFromString(terms);
		for (int i = 0; i < resultList.size(); i++) {
			gov.nih.nlm.nls.metamap.Result result = resultList.get(i);
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
					System.out.println(" None.");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return "";
	}

	// APIText must less than 10000 characters
	// UpLoad_File must less than 500 KB size
	public String getMimeMapRemote(String terms) {

		// remove all non ASCII chars
		terms = terms.replaceAll("[^\\p{ASCII}]", "");

		String results = null;

		GenericObject myIntMMObj = new GenericObject(100,"keluwakehealthedu", "KELU@uts.nlm.nih.gov");
		myIntMMObj.setField("Email_Address", "kelu@wakehealth.edu");
		StringBuffer buffer = new StringBuffer(terms);
		String bufferStr = buffer.toString();
		myIntMMObj.setField("APIText", bufferStr);
		// myIntMMObj.setFileField("UpLoad_File", "./sample.txt");
		myIntMMObj.setField("KSOURCE", "1314");
		myIntMMObj.setField("COMMAND_ARGS", "-iDN --silent");

		try {
			results = myIntMMObj.handleSubmission();
		} catch (RuntimeException ex) {
			System.err.println("");
			System.err.print("An ERROR has occurred while processing your");
			System.err.println(" request, please review any");
			System.err.print("lines beginning with \"Error:\" above and the");
			System.err.println(" trace below for indications of");
			System.err.println("what may have gone wrong.");
			System.err.println("");
			System.err.println("Trace:");
			ex.printStackTrace();
		} // catch
		return results;
	}

	@At()
	public View TestcreateTable() {
		UTF8JsonView jsonView = new UTF8JsonView(null);
		try {
			basicDao.create(MetaMap.class, false);
		} catch (Exception e) {
			e.printStackTrace();
			jsonView.setData(e.getMessage());
		}
		return jsonView;
	}

}
	


