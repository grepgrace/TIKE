package edu.wakehealth.dr.ddi.controller;

import edu.wakehealth.dr.ddi.dao.GEODao;
import edu.wakehealth.dr.ddi.model.Base_breast_cs;
import edu.wakehealth.dr.ddi.model.geo.GEO_Data;
import edu.wakehealth.dr.ddi.model.geo.GEO_Link_GDS2Key;
import edu.wakehealth.dr.ddi.utils.Tools;
import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub;
import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub.*;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.nutz.mvc.view.UTF8JsonView;


@IocBean
@InjectName
@At()
public class GEOeutils extends BaseController {

	EUtilsServiceStub service;
	String db = "gds";
	// test by esearchTest(883)
	int maxIdsNum = 800; // ids[] => 1,2 length < 8105(looks like 8*1024)
	String log_idsStr = "";
	String keyword = "breast cancer ";
	String retMaxDefault = "100";

	@At()
	public View esearch(Integer perPage, Integer page, Integer offset, HttpServletRequest req) {
		UTF8JsonView jsonView = new UTF8JsonView(null);
		
		try {
			service = new EUtilsServiceStub();
			String[] idsList = new String[0];

			// get all gds id about keys
			List<Base_breast_cs> csList = basicDao.search(Base_breast_cs.class);
			for (Base_breast_cs cs : csList) {
				String keys = cs.getKeywords();
				if (keys == null || keys.trim() == "") continue;
				for (String key : keys.split(",")) {
					if (key == null || key.trim() == "")continue;
					String[] ids = getGDSAllIds(keyword + key, retMaxDefault);
					if(ids==null)continue;
					idsList = ArrayUtils.addAll(idsList, ids);
					saveGeoIdAndLink(cs, key, ids);
				}
			}

			saveGeoData(idsList);
			jsonView.setData("success");
		} catch (RemoteException e) {
			System.out.println(e.toString());
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
			System.out.println(e.toString());
			jsonView.setData(e.getMessage());
		}

		return jsonView;
	}

	@At()
	public View TestgetGDSAllIds() {
		UTF8JsonView jsonView = new UTF8JsonView(null);
		try {
			service = new EUtilsServiceStub();
			String[] ids = getGDSAllIds(keyword, retMaxDefault);
			String idsStr = Arrays.toString(ids);
			jsonView.setData("length:" + idsStr.length() + " size:" + ids.length + " " + idsStr);
		} catch (RemoteException e) {
			System.out.println(e.toString());
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

			String[] ids = getGDSAllIds(keyword + key , retMaxDefault);
			saveGeoIdAndLink(cs, key, ids);
			String idsStr = Arrays.toString(ids);
			jsonView.setData("length:" + idsStr.length() + " size:" + ids.length + " " + idsStr);
		} catch (RemoteException e) {
			System.out.println(e.toString());
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

			if(ids==null||ids.length==0)ids = getGDSAllIds(keyword + key, retMaxDefault);
			saveGeoIdAndLink(cs, key, ids);
			saveGeoData(ids);
			String idsStr = Arrays.toString(ids);
			jsonView.setData("length:" + idsStr.length() + " size:" + ids.length + " " + idsStr);
		} catch (RemoteException e) {
			System.out.println(e.toString());
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
		System.out.println(keyword);
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
		String[] ids = eResult.getIdList().getId();
		if (Integer.valueOf(retMax) < Integer.valueOf(eResult.getCount())) {
			// get (0 - 100) + (100 - count)
			eResult = getESearchResult(keyword, retMax, eResult.getCount());
			ids = ArrayUtils.addAll(ids, eResult.getIdList().getId());
		}
		return ids;
	}

	DocSumType[] getAllGEOs(String[] ids) throws RemoteException {
		DocSumType[] result = new DocSumType[0];
		int step = (int) Math.ceil(ids.length / (double) maxIdsNum);
		for (int i = 0; i < step; i++) {
			int len = i * maxIdsNum + maxIdsNum<ids.length?i * maxIdsNum + maxIdsNum:ids.length;
			String[] _ids = Arrays.copyOfRange(ids, i * maxIdsNum, len);
			result = ArrayUtils.addAll(result, getGEOs(_ids));
		}
		return result;
	}

	DocSumType[] getGEOs(String[] ids) throws RemoteException {
		String idsStr = Arrays.toString(ids);
		idsStr = idsStr.substring(1, idsStr.length() - 1);
		idsStr = idsStr.toString().replace(" ", "").replace(",null", "");
		System.out.println(idsStr);
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
		DocSumType[] geos = getAllGEOs(ids);
		for (DocSumType doc : geos) {
			int id = Integer.valueOf(doc.getId());
			GEO_Data geo = basicDao.find(id, GEO_Data.class);
			if (geo == null) {
				geo = new GEO_Data().setId(id);
				basicDao.insert(geo);
			}

			ItemType[] items = doc.getItem();
			if (items != null) {
				Mirror<GEO_Data> mirror = Mirror.me(GEO_Data.class);
				for (ItemType item : items) {
					try {mirror.setValue(geo, item.getName(), item.getItemContent());} 
					catch (FailToSetValueException e) {System.err.println(e.getMessage());}
				}
				geo.setGSM(Tools.toString(getIds(Tools.get("Samples", items).getItem(),"Accession"), ";"));
				geo.setRelations(Tools.toString(getIds(Tools.get("Relations", items).getItem(),"TargetObject"), ";"));
				basicDao.update(geo);
			}
		}
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
			cd = Cnd.where("geoid", "=", gds.getId()).and("Keyid", "=", cs.getId())
					.and("KeyName", "=", key);
			basicDao.delete("GEO_Link_GDS2Key", cd);
			basicDao.insert(link);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
}
	


