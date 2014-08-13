package edu.wakehealth.dr.ddi.controller;

import edu.wakehealth.dr.ddi.model.Base_breast_cs;
import edu.wakehealth.dr.ddi.model.DBTable;
import edu.wakehealth.dr.ddi.model.geo.GEO_Data;
import edu.wakehealth.dr.ddi.model.geo.GEO_Link_GDS2Key;
import edu.wakehealth.dr.ddi.model.geo.Geo_gds_item;
import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub;
import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.castor.Castors;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Mirror;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.view.ForwardView;
import org.nutz.mvc.view.UTF8JsonView;


@IocBean
@InjectName
@At()
public class GEOeutils extends BaseController {

	EUtilsServiceStub service;
	String db = "gds";

	@At()
	public View esearch(Integer perPage, Integer page, Integer offset, HttpServletRequest req) {
		UTF8JsonView jsonView = new UTF8JsonView(null);
		
		String keyword = "breast cancer ";
		String retMax = "100";
		try {
			service = new EUtilsServiceStub();
			List<String> idsList = new ArrayList<String>();

			// get all gds id about keys
			List<Base_breast_cs> csList = basicDao.search(Base_breast_cs.class);
			for (Base_breast_cs cs : csList) {
				String keys = cs.getKeywords();
				if (keys == null || keys.trim() == "") continue;
				for (String key : keys.split(",")) {
					if (key == null || key.trim() == "")continue;
					String[] ids = getGDSIds(keyword + key, retMax);
					if(ids==null)continue;
					idsList.addAll(Castors.me().castTo(ids, ArrayList.class));
					for (String id : ids) {
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
					}
				}
			}

			saveGeoData(Castors.me().castTo(idsList, String[].class));
		} catch (RemoteException e) {
			System.out.println(e.toString());
			jsonView.setData(e.getMessage());
		}
		jsonView.setData("success");

		return jsonView;
	}

	@At()
	public View esearchTest() {
		UTF8JsonView jsonView = new UTF8JsonView(null);

		String keyword = "breast cancer ";
		String retMax = "10";
		try {
			service = new EUtilsServiceStub();
			saveGeoData(getGDSIds(keyword, retMax));
		} catch (RemoteException e) {
			System.out.println(e.toString());
			jsonView.setData(e.getMessage());
		}
		jsonView.setData("success");

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

	String[] getGDSIds(String keyword, String retMax) throws RemoteException {
		System.out.println(keyword);
		ESearchRequest eSearchReq = new ESearchRequest();
		eSearchReq.setTerm(keyword);
		eSearchReq.setDb(db);
		eSearchReq.setRetMax(retMax);
		ESearchResult eSearchRet = service.run_eSearch(eSearchReq);
		return eSearchRet.getIdList().getId();
	}

	DocSumType[] getGEOs(String idsStr) throws RemoteException {
		ESummaryRequest eSummaryRequest = new ESummaryRequest();
		eSummaryRequest.setId(idsStr);
		eSummaryRequest.setDb(db);
		ESummaryResult eSummaryResult = service.run_eSummary(eSummaryRequest);
		return eSummaryResult.getDocSum();
	}

	private void saveGeoData(String[] ids) throws RemoteException {
		String idsStr = Arrays.toString(ids);
		DocSumType[] geos = getGEOs(idsStr);
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
					try {
						mirror.setValue(geo, item.getName(), item.getItemContent());
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
				basicDao.update(geo);
			}
		}
	}

}
	


