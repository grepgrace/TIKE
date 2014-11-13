/*
===========================================================================
*
*                            PUBLIC DOMAIN NOTICE                          
*               National Center for Biotechnology Information
*         Lister Hill National Center for Biomedical Communications
*                                                                          
*  This software is a "United States Government Work" under the terms of the
*  United States Copyright Act.  It was written as part of the authors' official
*  duties as a United States Government contractor and thus cannot be
*  copyrighted.  This software is freely available to the public for use. The
*  National Library of Medicine and the U.S. Government have not placed any
*  restriction on its use or reproduction.  
*                                                                          
*  Although all reasonable efforts have been taken to ensure the accuracy  
*  and reliability of the software and data, the NLM and the U.S.          
*  Government do not and cannot warrant the performance or results that    
*  may be obtained by using this software or data. The NLM and the U.S.    
*  Government disclaim all warranties, express or implied, including       
*  warranties of performance, merchantability or fitness for any particular
*  purpose.                                                                
*                                                                          
*  Please cite the authors in any work or product based on this material.   
*
===========================================================================
*/

/**
 * Example program for submitting an Interactive SemRep request.
 *
 * This example shows how to setup a basic Interactive SemRep request.
 * This runs the latest version of SemRep with Full Fielded Output (-D).
 * 
 * @author	Jim Mork
 * @version	1.0, June 16, 2011
**/
package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.tike.utils.Tools;

public class ToolsTest {

	public static void main(String a[]) throws IOException {
//		Mirror<GEO_Data> mirror = Mirror.me(GEO_Data.class);
//		GEO_Data geo = new GEO_Data();
//		mirror.setValue(geo, "setAccession", "Accession");
//		System.out.println(geo.getAccession());
//
//		double num = 10 / (double) 3.0;
//		System.out.println(num);
//		System.out.println(Math.ceil(10 / (double) 3));
//		System.out.println(Math.ceil(num));
//		System.out.println(Math.ceil(3.33333333));
//		
//		System.out.println(Tools.toString(new String[] { "a", "b", "c" }, ";"));

		System.out.println(Math.floor(7 / 4));
		System.out.println(Math.floor(3.60));
		System.out.println(7 / 4);
		System.out.println(Math.ceil(7 / 4));
		System.out.println(Math.ceil(3.60));
}

	public static void removeAllnonASCIIChars() {
		// String dir = "C:\\Users\\kelu\\Downloads\\public_mm\\";
		// Tools.removeAllnonASCIIChars(dir + "allsummarys.txt");

		String str = "Bj��rk����oacute�";
		Tools.removeAllnonASCIIChars(str);
	}

}