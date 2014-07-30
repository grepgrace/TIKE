package edu.wakehealth.dr.ddi.model;

import java.util.Date;

public class DBTable {
	private String Name;
	private int Rows;
	private int Data_length;
	private Date Create_time;
	private Date Update_time;
	private String Comment;

	public String getName() {
		return Name;
	}

	public DBTable setName(String name) {
		Name = name;
		return this;
	}

	public int getRows() {
		return Rows;
	}

	public DBTable setRows(int rows) {
		Rows = rows;
		return this;
	}

	public int getData_length() {
		return Data_length;
	}

	public DBTable setData_length(int data_length) {
		Data_length = data_length;
		return this;
	}

	public Date getCreate_time() {
		return Create_time;
	}

	public DBTable setCreate_time(Date create_time) {
		Create_time = create_time;
		return this;
	}

	public Date getUpdate_time() {
		return Update_time;
	}

	public DBTable setUpdate_time(Date update_time) {
		Update_time = update_time;
		return this;
	}

	public String getComment() {
		return Comment;
	}

	public DBTable setComment(String comment) {
		Comment = comment;
		return this;
	}


}

