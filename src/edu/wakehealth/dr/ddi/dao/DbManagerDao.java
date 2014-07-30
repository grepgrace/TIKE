package edu.wakehealth.dr.ddi.dao;

import java.util.List;

import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import edu.wakehealth.dr.ddi.model.Article;
import edu.wakehealth.dr.ddi.model.DBTable;
import edu.wakehealth.dr.ddi.model.DBTableStatus;


public class DbManagerDao extends BasicDao {

	public List<DBTableStatus> getDBTableStatus() {
		Sql sql = Sqls.create("SHOW TABLE STATUS FROM `nutz_cms`");
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(DBTableStatus.class));
		dao.execute(sql);
		return sql.getList(DBTableStatus.class);
	}

	public List<Article> getArticles() {
		Sql sql = Sqls.create("SELECT * FROM t_article ORDER BY id DESC LIMIT 0, 4");
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(Article.class));
		dao.execute(sql);
		return sql.getList(Article.class);
	}

	public List<DBTable> getAllTables() {
		Sql sql = Sqls.create("SHOW TABLE STATUS");
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(DBTable.class));
		dao.execute(sql);
		return sql.getList(DBTable.class);
	}
}
