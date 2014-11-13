package com.tike.model;

import org.nutz.dao.entity.annotation.Table;

@Table("base_breast_cs")
public class Base_breast_cs {

	@org.nutz.dao.entity.annotation.Id
	private int Id;
	private String Name;
	private String Label;
	private String Description;
	private String Keywords;
	private boolean isGeiAllGEOData;

	public int getId() {
		return Id;
	}

	public Base_breast_cs setId(int id) {
		Id = id;
		return this;
	}

	public String getName() {
		return Name;
	}

	public Base_breast_cs setName(String name) {
		Name = name;
		return this;
	}

	public String getLabel() {
		return Label;
	}

	public Base_breast_cs setLabel(String label) {
		Label = label;
		return this;
	}

	public String getDescription() {
		return Description;
	}

	public Base_breast_cs setDescription(String description) {
		Description = description;
		return this;
	}

	public String getKeywords() {
		return Keywords;
	}

	public Base_breast_cs setKeywords(String keywords) {
		Keywords = keywords;
		return this;
	}

	public boolean isGeiAllGEOData() {
		return isGeiAllGEOData;
	}

	public Base_breast_cs setGeiAllGEOData(boolean isGeiAllGEOData) {
		this.isGeiAllGEOData = isGeiAllGEOData;
		return this;
	}



}
