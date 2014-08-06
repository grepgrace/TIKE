package edu.wakehealth.dr.ddi.controller;

import edu.wakehealth.dr.ddi.model.geo.Geo_gds;
import edu.wakehealth.dr.ddi.model.geo.Geo_gds_item;
import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub;
import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub.*;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.view.ForwardView;


@IocBean
@InjectName
@At()
public class GEOeutils extends BaseController {

	@At()
	public View esearch(Integer perPage, Integer page, Integer offset, HttpServletRequest req) {
		// GDS4388		100004388
		// GPL96		100000096
		// GSE25065		200025065
		// GSM615828	300615828
		
		String keyword = "breast cancer";
		String db = "gds";
		String retMax = "10";
		try {
			EUtilsServiceStub service = new EUtilsServiceStub();

			ESearchRequest eSearchReq = new ESearchRequest();
			eSearchReq.setTerm(keyword);
			eSearchReq.setDb(db);
			eSearchReq.setRetMax(retMax);
			ESearchResult eSearchRet = service.run_eSearch(eSearchReq);

			String idsStr = Arrays.toString(eSearchRet.getIdList().getId());
			idsStr = idsStr.toString().substring(1, idsStr.length() - 1);

			ESummaryRequest eSummaryRequest = new ESummaryRequest();
			eSummaryRequest.setId(idsStr);
			eSummaryRequest.setDb(db);
			ESummaryResult eSummaryResult = service.run_eSummary(eSummaryRequest);
			for (DocSumType doc : eSummaryResult.getDocSum()) {
				Integer id = Integer.valueOf(doc.getId());
				Geo_gds geo_gds = new Geo_gds().setId(id).setCode("GDS" + id);
				Condition cd = Cnd.where("id", "=", geo_gds.getId()).and("code", "=",
						geo_gds.getCode());
				if(basicDao.searchCount(Geo_gds.class, cd)==0){basicDao.save(geo_gds);} 
				else {basicDao.update(geo_gds);}
				// delete all gds item
				cd = Cnd.where("Gdsid", "=", geo_gds.getId());
				basicDao.delete(Geo_gds_item.class.getSimpleName(), cd);
				saveGeo_gds_item(doc.getItem(),null,geo_gds);
			}

		} catch (RemoteException e) {
			System.out.println(e.toString());
		}

		//return setView(req, "GEOeutils/esearch");
		return new ForwardView("/DBManager/Geo_gds");
	}

	private void saveGeo_gds_item(ItemType[] items, Geo_gds_item parentItem,Geo_gds geo_gds) {
		if (items != null) {
			for (ItemType subitem : items) {
				Geo_gds_item geo_gds_subitem = new Geo_gds_item().setGdsid(geo_gds.getId())
						.setName(subitem.getName())
						.setType(subitem.getType().getValue().toString())
						.setItemcontent(subitem.getItemContent());
				if (parentItem != null) {
					geo_gds_subitem.setParentId(parentItem.getId());
					geo_gds_subitem.setParentname(parentItem.getName());
				}
				try {
					basicDao.save(geo_gds_subitem);
				} catch (Exception e) {
					basicDao.update(geo_gds_subitem);
				}
				saveGeo_gds_item(subitem.getItem(), geo_gds_subitem, geo_gds);
			}
		}
	}
	
}
	


