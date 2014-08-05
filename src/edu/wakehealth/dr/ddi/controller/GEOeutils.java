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
		
		String keyword = "breast cancer";
		try {
			EUtilsServiceStub service = new EUtilsServiceStub();

			// call NCBI EInfo utility
//			EInfoRequest req1 = new EInfoRequest();
//			EInfoResult res = service.run_eInfo(req1);
//			for (int i = 0; i < res.getDbList().getDbName().length; i++) {
//				// System.out.println(res.getDbList().getDbName()[i]);
//			}

			ESearchRequest eSearchReq = new ESearchRequest();
			eSearchReq.setTerm(keyword);
			eSearchReq.setDb("gds");
			eSearchReq.setRetMax("10");
			ESearchResult eSearchRet = service.run_eSearch(eSearchReq);
			// for (int i = 0; i < eSearchRet.getIdList().getId().length; i++) {
			// // System.out.println(eSearchRet.getIdList().getId()[i]);
			// }

			String idsStr = Arrays.toString(eSearchRet.getIdList().getId());
			idsStr = idsStr.toString().substring(1, idsStr.length() - 1);

			ESummaryRequest eSummaryRequest = new ESummaryRequest();
			eSummaryRequest.setId(idsStr);
			eSummaryRequest.setDb("gds");
			ESummaryResult eSummaryResult = service.run_eSummary(eSummaryRequest);
			for (DocSumType doc : eSummaryResult.getDocSum()) {
				Integer id = Integer.valueOf(doc.getId());
				Geo_gds geo_gds = new Geo_gds().setId(id).setCode("GDS" + id);
				Condition cd = Cnd.where("id", "=", geo_gds.getId()).and("code", "=",
						geo_gds.getCode());
				if(basicDao.searchCount(Geo_gds.class, cd)==0){basicDao.save(geo_gds);} 
				else {basicDao.update(geo_gds);}
				// delete all item
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
	


