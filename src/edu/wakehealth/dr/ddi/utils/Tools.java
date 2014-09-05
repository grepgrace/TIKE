package edu.wakehealth.dr.ddi.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	
	// convert InputStream to String
	public static String getString(InputStream is) {
		BufferedReader br = null;String line;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\r\n");
			}
		} catch (IOException e) {e.printStackTrace();} finally {
			if (br != null) {
				try {br.close();} 
				catch (IOException e) {e.printStackTrace();}
			}
		}
		return sb.toString();
	}

}