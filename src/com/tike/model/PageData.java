package com.tike.model;

import java.util.List;

//"records": [
//{
//  "someAttribute": "I am record one",
//  "someOtherAttribute": "Fetched by AJAX"
//},
//{
//  "someAttribute": "I am record two",
//  "someOtherAttribute": "Cuz it's awesome"
//},
//{
//  "someAttribute": "I am record three",
//  "someOtherAttribute": "Yup, still AJAX"
//}
//],
//"queryRecordCount": 3,
//"totalRecordCount": 3
public class PageData<E> {
	
	private List<E> records;
	private int queryRecordCount;
	private int totalRecordCount;

	public List<E> getRecords() {
		return records;
	}

	public PageData<E> setRecords(List<E> records) {
		this.records = records;
		return this;
	}

	public int getQueryRecordCount() {
		return queryRecordCount;
	}

	public PageData<E> setQueryRecordCount(int queryRecordCount) {
		this.queryRecordCount = queryRecordCount;
		return this;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	public PageData<E> setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
		return this;
	}
	
	
}
