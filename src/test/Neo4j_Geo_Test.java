package test;

import java.io.File;
import java.util.*;

import org.junit.Test;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphalgo.*;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.impl.util.FileUtils;
import org.neo4j.kernel.impl.util.StringLogger;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;

import edu.wakehealth.dr.ddi.dao.GEODao;
import edu.wakehealth.dr.ddi.model.geo.GEO_Data;
import edu.wakehealth.dr.ddi.model.geo.MetaMap;
import edu.wakehealth.dr.ddi.model.geo.MetaMapGroup;
import edu.wakehealth.dr.ddi.utils.Tools;

public class Neo4j_Geo_Test extends NutZTest {
	static String DB_PATH = "C:/Users/kelu/Documents/Neo4j/music.db";
	private static GraphDatabaseService db;
	static RestGraphDatabase gd = new RestGraphDatabase(DB_PATH);

	private final static Label labelGeo = DynamicLabel.label("geo");
	private final static Label labelMetaMap = DynamicLabel.label("metamap");

	private static enum RelTypes implements RelationshipType {KNOWS, Has, Is}

	public static void main(String[] args) throws Exception {
		try {
			db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		} catch (Exception e) {
			DB_PATH = "http://localhost:7474/db/data";
			db = new RestGraphDatabase(DB_PATH);
		}

		setDao();
		// createMetaMaps();
		createGeoDatas();
		// createRelationshipOfGeoWithKey();

		// test();

		closeDao();
	}

	private static void test() {
		// select Nodes and Relationship
		List<Node> list = getNodes(labelGeo, "Id", 88);
		for (Node node : list) {
			System.out.println(node.getLabels().iterator().next().name() + ":" + node);

			List<Node> relalist = getRelaNodes(node);
			if (relalist.size() == 0)
				node.delete();
			for (Node node1 : relalist) {
				System.out.println(node1.getLabels().iterator().next().name() + ":" + node1);
			}
			System.out.println();
		}
		System.out.println();

//		Node startNode = list.get(0);
//		Node endNode = getNodes(labelMetaMap, "UMLSConceptUniqu", "C0220908").get(0);
//		PathFinder<Path> finder = GraphAlgoFactory.shortestPath(PathExpanders.forTypeAndDirection(RelTypes.Has, Direction.BOTH), 15);
//		Iterator<Path> paths = finder.findAllPaths(startNode, endNode).iterator();
//		while (paths.hasNext()) {
//			System.out.println(paths.next());
//		}
//
//		List<Node> relalist = getRelatedNodes(startNode, RelTypes.Has, "C2003903");
//		for (Node node1 : relalist) {
//			System.out.println(node1.getLabels().iterator().next().name() + ":" + node1);
//		}

	}

	private static void createGeoDatas() {
		try (Transaction tx = db.beginTx();) {
			Condition cd = Cnd.where("1", "=", "1");
			// cd = Cnd.wrap(" Id in (88, 360, 483, 806, 807, 817, 820)");
			List<GEO_Data> list = basicDao.search(GEO_Data.class, cd);
			// System.out.println("list.size:" + list.size());
			for (int i = 0; i < list.size(); i++) {
				GEO_Data geo = list.get(i);
				System.out.println("\r\n" + (i + 1) + "/" + list.size() + " " + geo.getId());
	
				List<Node> listNodes = getNodes(labelGeo, "Id", geo.getId());
				if (listNodes.size() > 1) {
					for (int j = 1; j < listNodes.size(); j++) {
						listNodes.get(j).delete();
					}
				}
	
				Node geoNode = getNode(labelGeo, "Id", geo.getId());
				if(geoNode==null)geoNode = db.createNode(labelGeo);
				Tools.setProperty(geoNode, geo);
	
				createMetaMaps(geoNode);
			}
			tx.success();
		}
	}

	private static void createMetaMaps(Node geoNode) {
		String ids = ""; // like 'C1513882','C0007600'
		if (!geoNode.hasProperty("summary"))
			return;
		String text = geoNode.getProperty("summary").toString();
		List<MetaMap> metamaps = Tools.getMimeMaps(text);
		if (metamaps.size() > 0) {
			for (MetaMap metaMap : metamaps) {
				ids += "'" + metaMap.getUMLSConceptUniqu() + "',";
			}
			ids = ids.substring(0, ids.length() - 1);
	
			Condition cd = Cnd.where("1", "=", "1");
			cd = Cnd.where("UMLSConceptPrefer", "=","Tumor cells, uncertain whether benign or malignant");
			cd = Cnd.wrap("sum >10 and avg > 10 and treecode !='' and treecode not like '%x.%'");
			cd = Cnd.wrap("UMLSConceptUniqu IN (" + ids + ")");
			List<MetaMapGroup> list = basicDao.search(MetaMapGroup.class, cd);
			// System.out.println("list<MetaMapGroup>.size:" + list.size());
	
			for (int i = 0; i < list.size(); i++) {
				MetaMapGroup map = list.get(i);
				System.out.println("\t" + (i + 1) + "/" + list.size() + " "
						+ map.getUMLSConceptUniqu());
	
				Node mapNode = getNode(labelMetaMap, "UMLSConceptUniqu", map.getUMLSConceptUniqu());
				if (mapNode == null)
					mapNode = db.createNode(labelMetaMap);
				Tools.setProperty(mapNode, map);

				List<Relationship> relalist = getRelatedNodes(geoNode, mapNode, "Id",
						"UMLSConceptUniqu");
				if (relalist.size() == 0) {
					mapNode.createRelationshipTo(geoNode, RelTypes.Has);
					System.out.println("createRelationshipTo: GEO:" + geoNode.getProperty("Id")
							+ " " + RelTypes.Has + " MetaMap:" + map.getUMLSConceptUniqu());
				} else
					for (Relationship r : relalist) {
						System.out.println("exits rela: node:" + geoNode.getId() + " rela:"
								+ r.getId() + " node2:" + mapNode.getId());
					}
			}
		}
	}


	static Node getNode(Label paramLabel, String paramString, Object paramObject) {
		List<Node> list = new ArrayList<>();
		Iterator<Node> nodes = db.findNodesByLabelAndProperty(paramLabel, paramString, paramObject).iterator();
		while (nodes.hasNext()) {
			Node node = nodes.next();
			list.add(node);
			//System.out.println(node.getLabels().iterator().next().name() + ":" + node);
		}
		if (list.size() > 1) {
			System.err.println("Warning: getNode() list.size() > 1 " + paramLabel + " "
					+ paramString + " " + paramObject);
			for (Node node1 : list) {
				System.err.println(node1.getLabels().iterator().next().name() + ":" + node1);
			}
			return list.get(0);
		} else if (list.size() == 1)
			return list.get(0);
		else return null;
	}
	static List<Node> getNodes(Label paramLabel, String paramString, Object paramObject) {
		List<Node> list = new ArrayList<>();
		Iterator<Node> nodes = db.findNodesByLabelAndProperty(paramLabel, paramString, paramObject).iterator();
		while (nodes.hasNext()) {
			Node node = nodes.next();
			list.add(node);
			//System.out.println(node.getLabels().iterator().next().name() + ":" + node);
		}
		return list;
	}
	static List<Node> getRelaNodes(Node node) {
		return getRelaNodes(node, new RelationshipType[] {});
	}
	static List<Node> getRelaNodes(Node node, RelationshipType[] relaTypes) {
		List<Node> list = new ArrayList<>();
		Iterator<Relationship> relas = node.getRelationships(relaTypes).iterator();
		if(relaTypes.length==0)relas = node.getRelationships().iterator();
		while (relas.hasNext()) {
			Relationship rela = relas.next();
			// System.out.println(rela.getType().name() + ":" + rela);

			Node[] nodes1 = rela.getNodes();
			for (Node node2 : nodes1) {
				if(node2.equals(node))continue;
				list.add(node2);
				//System.out.println(node2.getLabels().iterator().next().name() + ":" + node2);
			}
		}
		return list;
	}

	static <T> List<T> getRelatedNodes(Node node1, Node node2, String name1, String name2) {
		List<T> list = new ArrayList<>();

		ExecutionEngine engine = new ExecutionEngine(db, StringLogger.DEV_NULL);
		scala.collection.Iterator<T> resultIterator = null;
		String queryString = "MATCH (a)-[r]-(b) WHERE a." + name1 + "= {" + name1 + "} " + "and b."
				+ name2 + "={" + name2 + "} RETURN r";
		// System.out.println(queryString);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put(name1, node1.getProperty(name1));
		parameters.put(name2, node2.getProperty(name2));
		resultIterator = engine.execute(queryString, parameters).columnAs("r");
		while (resultIterator.hasNext()) {
			list.add(resultIterator.next());
		}
		return list;
	}

}