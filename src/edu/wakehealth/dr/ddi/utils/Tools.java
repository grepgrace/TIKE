package edu.wakehealth.dr.ddi.utils;

import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub.ItemType;

public class Tools {

	public static ItemType getItemType(String title, ItemType[] itemTypes) {
		for (int k = 0; k < itemTypes.length; k++) {
			if(title.equals(itemTypes[k].getName()))
				return itemTypes[k];
		}
		return null;
	}

}