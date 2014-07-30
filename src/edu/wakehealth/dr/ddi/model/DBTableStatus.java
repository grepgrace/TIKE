package edu.wakehealth.dr.ddi.model;

import java.util.Date;

	public class DBTableStatus {
		private String Name;
		private String Engine;
		private Integer Version;
		private String Row_format;
		private Integer Rows;
		private Integer Avg_row_length;
		private Integer Data_length;
		private Integer Max_data_length;
		private Integer Index_length;
		private Integer Data_free;
		private Integer Auto_increment;
		private Date Create_time;
		private Date Update_time;
		private Date Check_time;
		private String Collation;
		private String Checksum;
		private String Create_options;
		private String Comment;

		public String getName() {
			return Name;
		}

		public DBTableStatus setName(String name) {
			Name = name;
			return this;
		}

		public String getEngine() {
			return Engine;
		}

		public DBTableStatus setEngine(String engine) {
			Engine = engine;
			return this;
		}

		public Integer getVersion() {
			return Version;
		}

		public DBTableStatus setVersion(Integer version) {
			Version = version;
			return this;
		}

		public String getRow_format() {
			return Row_format;
		}

		public DBTableStatus setRow_format(String row_format) {
			Row_format = row_format;
			return this;
		}

		public Integer getRows() {
			return Rows;
		}

		public DBTableStatus setRows(Integer rows) {
			Rows = rows;
			return this;
		}

		public Integer getAvg_row_length() {
			return Avg_row_length;
		}

		public DBTableStatus setAvg_row_length(Integer avg_row_length) {
			Avg_row_length = avg_row_length;
			return this;
		}

		public Integer getData_length() {
			return Data_length;
		}

		public DBTableStatus setData_length(Integer data_length) {
			Data_length = data_length;
			return this;
		}

		public Integer getMax_data_length() {
			return Max_data_length;
		}

		public DBTableStatus setMax_data_length(Integer max_data_length) {
			Max_data_length = max_data_length;
			return this;
		}

		public Integer getIndex_length() {
			return Index_length;
		}

		public DBTableStatus setIndex_length(Integer index_length) {
			Index_length = index_length;
			return this;
		}

		public Integer getData_free() {
			return Data_free;
		}

		public DBTableStatus setData_free(Integer data_free) {
			Data_free = data_free;
			return this;
		}

		public Integer getAuto_increment() {
			return Auto_increment;
		}

		public DBTableStatus setAuto_increment(Integer auto_increment) {
			Auto_increment = auto_increment;
			return this;
		}

		public Date getCreate_time() {
			return Create_time;
		}

		public DBTableStatus setCreate_time(Date create_time) {
			Create_time = create_time;
			return this;
		}

		public Date getUpdate_time() {
			return Update_time;
		}

		public DBTableStatus setUpdate_time(Date update_time) {
			Update_time = update_time;
			return this;
		}

		public Date getCheck_time() {
			return Check_time;
		}

		public DBTableStatus setCheck_time(Date check_time) {
			Check_time = check_time;
			return this;
		}

		public String getCollation() {
			return Collation;
		}

		public DBTableStatus setCollation(String collation) {
			Collation = collation;
			return this;
		}

		public String getChecksum() {
			return Checksum;
		}

		public DBTableStatus setChecksum(String checksum) {
			Checksum = checksum;
			return this;
		}

		public String getCreate_options() {
			return Create_options;
		}

		public DBTableStatus setCreate_options(String create_options) {
			Create_options = create_options;
			return this;
		}

		public String getComment() {
			return Comment;
		}

		public DBTableStatus setComment(String comment) {
			Comment = comment;
			return this;
		}

	}
