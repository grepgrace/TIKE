package edu.wakehealth.dr.ddi.utils;

import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub.ItemType;

public class Tools {

	public static ItemType get(String title, ItemType[] itemTypes) {
		for (int k = 0; k < itemTypes.length; k++) {
			if(title.equals(itemTypes[k].getName()))
				return itemTypes[k];
		}
		return null;
	}

	/*
	 * toString(new String[] { "a", "b", "c" }, ";") = a;b;c
	 */
	public static String toString(String[] strs, String str) {
		if (strs != null) {
			String v = "";
			for (String string : strs) {v += string + str;}
			return v.substring(0, v.length() - 1);
		} else return null;
	}
}