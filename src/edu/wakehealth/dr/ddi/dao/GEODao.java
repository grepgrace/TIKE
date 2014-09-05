package edu.wakehealth.dr.ddi.dao;

import java.util.List;

import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import edu.wakehealth.dr.ddi.model.geo.GEO_Data;

public class GEODao extends BasicDao {

	public List<GEO_Data> getRand() {
		Sql sql = Sqls.create("SELECT  a.* FROM geo_data as a where a.entryType = 'GSE' order by RAND() limit 10000");
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(GEO_Data.class));
		dao.execute(sql);
		return sql.getList(GEO_Data.class);
	}
}
