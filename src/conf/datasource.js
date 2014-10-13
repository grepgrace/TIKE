var ioc = {
	dataSource:{
		type:"org.apache.commons.dbcp.BasicDataSource",
		events:{
			depose:"close"
		},
		fields:{
			driverClassName:"com.mysql.jdbc.Driver",
			url:"jdbc:mysql://localhost:3311/ddi?characterEncoding=utf8",
			username:"root",
			password:"root"
		}
	},
	dao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [{refer:'dataSource'}]
	}
	
}