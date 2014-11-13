package com.tike.model.geo;

import org.nutz.dao.entity.annotation.*;



@Table("MimeMapType")
public class MimeMapType {
	@Id
	private int ID;
	private String Type;
	private String TypeKey;
	private String UMLSConceptPrefer;
	private String UMLSConceptUniqu;

	public int getID() {
		return ID;
	}

	public MimeMapType setID(int iD) {
		ID = iD;
		return this;
	}

	public String getUMLSConceptPrefer() {
		return UMLSConceptPrefer;
	}

	public MimeMapType setUMLSConceptPrefer(String uMLSConceptPrefer) {
		UMLSConceptPrefer = uMLSConceptPrefer;
		return this;
	}

	public String getUMLSConceptUniqu() {
		return UMLSConceptUniqu;
	}

	public MimeMapType setUMLSConceptUniqu(String uMLSConceptUniqu) {
		UMLSConceptUniqu = uMLSConceptUniqu;
		return this;
	}

	public String getType() {
		return Type;
	}

	public MimeMapType setType(String type) {
		Type = type;
		return this;
	}

	public String getTypeKey() {
		return TypeKey;
	}

	public MimeMapType setTypeKey(String typeKey) {
		TypeKey = typeKey;
		return this;
	}


}