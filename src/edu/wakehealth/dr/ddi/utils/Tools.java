package edu.wakehealth.dr.ddi.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.neo4j.graphdb.Node;
import org.nutz.lang.Mirror;

import edu.wakehealth.dr.ddi.model.geo.GEO_Data;
import edu.wakehealth.dr.ddi.model.geo.MetaMapGroup;
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

	public static void writeFile(String filepath, String text) throws IOException {
		// create a temporary file
		File logFile = new File(filepath);
		if (!logFile.getParentFile().exists())
			logFile.getParentFile().mkdirs();
		BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
		writer.write(text);

		// Close writer
		writer.close();
	}

	public static String removeAllnonASCIIChars(String str) {
		// String str = "Bj��rk����oacute�";
		str = str.replaceAll("[^\\p{ASCII}]", "");
		System.out.println("removeAllnonASCIIChars: " + str);
		return str;
	}

	public static void removeAllnonASCIIChars(File filepath) throws IOException {
		Path path = Paths.get(filepath.getAbsolutePath());
		String str = new String(Files.readAllBytes(path));
		// String str = "Bj��rk����oacute�";
		str = removeAllnonASCIIChars(str);
		Tools.writeFile(filepath.getAbsolutePath(), str);
		System.out.println("Remove all non-ASCII chars: " + filepath);
	}

	public static <T> void setProperty(Node node1, T t) {
		Mirror<T> mirror = Mirror.me(t);
		Field[] fields = mirror.getFields();
		for (Field field : fields) {
			node1.setProperty(field.getName(), mirror.getValue(t, field.getName()));
			//System.out.println(field.getName() + ":" + mirror.getValue(t, field.getName()));
		}
	}

}