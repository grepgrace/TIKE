var ioc = {
	dataSource:{
		type:"org.apache.commons.dbcp.BasicDataSource",
		events:{
			depose:"close"
		},
		fields:{
			driverClassName:"com.mysql.jdbc.Driver",
			url:"jdbc:mysql://152.11.105.62:3311/ddi?characterEncoding=utf8",
			username:"root",
			password:"root"
		}
	},
	dao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [{refer:'dataSource'}]
	}
	
}