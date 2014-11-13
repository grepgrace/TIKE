package com.tike.model.geo;

import org.nutz.dao.entity.annotation.*;



@Table("v_MetaMap_Group")
public class MetaMapGroup {
	private double Sum;
	private double Avg;
	private String UMLSConceptPrefer;
	private String UMLSConceptUniqu;
	private String Treecode;

	public double getSum() {
		return Sum;
	}

	public MetaMapGroup setSum(double sum) {
		Sum = sum;
		return this;
	}

	public double getAvg() {
		return Avg;
	}

	public MetaMapGroup setAvg(double avg) {
		Avg = avg;
		return this;
	}
	public String getUMLSConceptPrefer() {
		return UMLSConceptPrefer;
	}
	public MetaMapGroup setUMLSConceptPrefer(String uMLSConceptPrefer) {
		UMLSConceptPrefer = uMLSConceptPrefer;
		return this;
	}
	public String getUMLSConceptUniqu() {
		return UMLSConceptUniqu;
	}
	public MetaMapGroup setUMLSConceptUniqu(String uMLSConceptUniqu) {
		UMLSConceptUniqu = uMLSConceptUniqu;
		return this;
	}
	public String getTreecode() {
		return Treecode;
	}
	public MetaMapGroup setTreecode(String treecode) {
		Treecode = treecode;
		return this;
	}



}