package test;

import java.io.File;
import java.util.List;

import org.neo4j.graphdb.*;
import org.neo4j.kernel.impl.util.FileUtils;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;

import edu.wakehealth.dr.ddi.dao.GEODao;
import edu.wakehealth.dr.ddi.model.geo.GEO_Data;
import edu.wakehealth.dr.ddi.model.geo.MetaMapGroup;
import edu.wakehealth.dr.ddi.utils.Tools;

public class Neo4j_Geo_Test extends NutZTest {
	private static GraphDatabaseService db;

	private final static Label labelGeo = DynamicLabel.label("geo");
	private final static Label labelMetaMap = DynamicLabel.label("metamap");

	private static enum RelTypes implements RelationshipType {
		KNOWS, Has, Is
	}

	public static void main(String[] args) throws Exception {
		String DB_PATH = "H:/Documents/Neo4j/default.graphdb";
		// FileUtils.deleteRecursively(new File(DB_PATH));
		DB_PATH = "http://localhost:7474/db/data";
		// db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		db = new RestGraphDatabase(DB_PATH);
		try (Transaction tx = db.beginTx();) {
//			Iterable<Node> nodes = db.getAllNodes();
//			for (Node node : nodes) {
//				node.delete();
//			}
			
			setDao();
			// createKeywords();
			// createGeoDatas();
			// createRelationshipOfGeoWithKey();
			Iterable<Node> nodes = db.findNodesByLabelAndProperty(labelGeo, "UMLSConceptUniqu",
					"C0001948");
			System.out.println(nodes);
//			Node firstNode = db.createNode();
//			firstNode.setProperty("message", "Hello, ");
//			Node secondNode = db.createNode();
//			secondNode.setProperty("message", "World!");
//
//			Relationship relationship = firstNode.createRelationshipTo(secondNode, RelTypes.KNOWS);
//			relationship.setProperty("message", "brave Neo4j ");

			tx.success();
		}
		// finally {
		// db.shutdown();
		// }
	}

	private static void createKeywords() {
		Condition cd = Cnd.where("UMLSConceptPrefer", "=", "Tumor cells, uncertain whether benign or malignant");
		// test Condition when next line commented
		// sum >10 and avg > 10 and treecode !='' and treecode not like '%x.%'
		cd = Cnd.wrap("sum >10 and avg > 10 and treecode !='' and treecode not like '%x.%' limit 5");
		// cd = Cnd.where("1", "=", "1");
		List<MetaMapGroup> list = basicDao.search(MetaMapGroup.class, cd);
		System.out.println("list.size:" + list.size());
		for (int i = 0; i < list.size(); i++) {
			MetaMapGroup map = list.get(i);

			Node node1 = db.createNode(labelMetaMap);
			Tools.setProperty(node1, map);
			System.out.println(map.getUMLSConceptUniqu());
		}
	}

	private static void createGeoDatas() {
		Condition cd = Cnd.where("UMLSConceptPrefer", "=", "Tumor cells, uncertain whether benign or malignant");
		// test Condition when next line commented
		cd = Cnd.wrap("sum >10 and avg > 10 and treecode !='' and treecode not like '%x.%' limit 5");
		cd = Cnd.where("1", "=", "1");
		List<GEO_Data> list = basicDao.search(GEO_Data.class, cd);
		System.out.println("list.size:" + list.size());
		for (int i = 0; i < list.size(); i++) {
			GEO_Data map = list.get(i);

			Node node1 = db.createNode(labelGeo);
			Tools.setProperty(node1, map);
			System.out.println(map.getId());
		}
	}

	private static void createRelationshipOfGeoWithKey() {
		Condition cd = Cnd.where("UMLSConceptPrefer", "=","Tumor cells, uncertain whether benign or malignant");
		// test Condition when next line commented
		// sum >10 and avg > 10 and treecode !='' and treecode not like '%x.%'
		cd = Cnd.wrap("sum >10 and avg > 10 and treecode !='' and treecode not like '%x.%'");
		// cd = Cnd.where("1", "=", "1");
		List<MetaMapGroup> list = basicDao.search(MetaMapGroup.class, cd);
		System.out.println("list.size:" + list.size());
		for (int i = 0; i < list.size(); i++) {
			MetaMapGroup map = list.get(i);

			Node node1 = db.createNode();
			Tools.setProperty(node1, map);
			System.out.println(map.getUMLSConceptUniqu());

			Node node2 = db.createNode();
			node2.setProperty("name", "专辑 1");
			node1.createRelationshipTo(node2, RelTypes.Has);
		}

	}


}