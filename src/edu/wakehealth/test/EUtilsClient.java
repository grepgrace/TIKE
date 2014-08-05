package edu.wakehealth.test;

import java.util.Map;

import org.nutz.castor.Castors;

import edu.wakehealth.dr.ddi.utils.Tools;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchTaxonServiceStub.*;
import gov.nih.nlm.ncbi.www.soap.eutils.*;
import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub.*;

public class EUtilsClient {
	public static void main(String[] args) throws Exception {
		// eInfo utility returns a list of available databases
		try {
			String keyword = "breast cancer";
			EUtilsServiceStub service = new EUtilsServiceStub();

//			// call NCBI EInfo utility
//			EUtilsServiceStub.EInfoRequest req = new EUtilsServiceStub.EInfoRequest();
//			EUtilsServiceStub.EInfoResult res = service.run_eInfo(req);
//			// results output
//			for (int i = 0; i < res.getDbList().getDbName().length; i++) {
//				System.out.println(res.getDbList().getDbName()[i]);
//			}
		
			ESearchRequest eSearchReq = new ESearchRequest();
			eSearchReq.setTerm(keyword);
			eSearchReq.setDb("gds");
			eSearchReq.setRetMax("10");
			ESearchResult eSearchRet = service.run_eSearch(eSearchReq);
			for (int i = 0; i < eSearchRet.getIdList().getId().length; i++) {
				// System.out.println(eSearchRet.getIdList().getId()[i]);
			}

			ESummaryRequest eSummaryRequest = new ESummaryRequest();
			eSummaryRequest.setId("4388,4359");
			eSummaryRequest.setDb("gds");
			ESummaryResult eSummaryResult = service.run_eSummary(eSummaryRequest);
			for (int i = 0; i < eSummaryResult.getDocSum().length; i++) {
				System.out.println(eSummaryResult.getDocSum()[i].getId());

				for (int k = 0; k < eSummaryResult.getDocSum()[i].getItem().length; k++) {
					System.out.println("    " + eSummaryResult.getDocSum()[i].getItem()[k].getName() + ": "
							+ eSummaryResult.getDocSum()[i].getItem()[k].getItemContent());
				}
				System.out.println(Tools.getItemType("SSInfo", eSummaryResult.getDocSum()[i].getItem()).getItemContent());
			}

//			EFetchTaxonServiceStub eFetchTaxonServiceStub = new EFetchTaxonServiceStub();
//			EFetchRequest eFetchRequest = new EFetchRequest();
//			eFetchRequest.setId("4388");
//			eFetchRequest.setDb("gds");
//			EFetchResult eFetchResult = eFetchTaxonServiceStub.run_eFetch(eFetchRequest);
//			for (int i = 0; i < eFetchResult.getIdList().getId().length; i++) {
//				System.out.println(eFetchResult.getGBSet());
//			}

			EFetchTaxonServiceStub eFetchTaxonService = new EFetchTaxonServiceStub();
			// call NCBI EFetch utility
			EFetchTaxonServiceStub.EFetchRequest req = new EFetchTaxonServiceStub.EFetchRequest();
			req.setId("4388,4359");
			EFetchTaxonServiceStub.EFetchResult res = eFetchTaxonService.run_eFetch(req);
			// results output
			for (int i = 0; i < res.getTaxaSet().getTaxon().length; i++) {
				System.out.println(res.getTaxaSet().getTaxon()[i].getScientificName() + ": "
						+ res.getTaxaSet().getTaxon()[i].getDivision() + " ("
						+ res.getTaxaSet().getTaxon()[i].getRank() + ")");
			}


		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}