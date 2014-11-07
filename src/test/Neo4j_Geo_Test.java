package test;

import java.util.*;

import org.neo4j.graphdb.*;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;

import edu.wakehealth.dr.ddi.dao.Neo4jDao;
import edu.wakehealth.dr.ddi.model.geo.*;
import edu.wakehealth.dr.ddi.utils.Tools;

public class Neo4j_Geo_Test extends NutZTest {
	protected static Neo4jDao neo4jDap = new Neo4jDao();

	private final static Label labelGeo = DynamicLabel.label("geo");
	private final static Label labelMetaMap = DynamicLabel.label("metamap");

	private static enum RelTypes implements RelationshipType {KNOWS, Has, Is}

	public static void main(String[] args) {
		try {
			setDao();
			// createMetaMaps();
			createGeoDatas();
			// createRelationshipOfGeoWithKey();

			// test();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDao();
		}

	}


	private static void test() {
		// select Nodes and Relationship
		List<Node> list = neo4jDap.getNodes(labelGeo, "Id", 88);
		for (Node node : list) {
			Tools.println(node.getLabels().iterator().next().name() + ":" + node);

			List<Node> relalist = neo4jDap.getRelaNodes(node);
			if (relalist.size() == 0)
				node.delete();
			for (Node node1 : relalist) {
				Tools.println(node1.getLabels().iterator().next().name() + ":" + node1);
			}
			System.out.println();
		}
		System.out.println();

//		Node startNode = list.get(0);
//		Node endNode = getNodes(labelMetaMap, "UMLSConceptUniqu", "C0220908").get(0);
//		PathFinder<Path> finder = GraphAlgoFactory.shortestPath(PathExpanders.forTypeAndDirection(RelTypes.Has, Direction.BOTH), 15);
//		Iterator<Path> paths = finder.findAllPaths(startNode, endNode).iterator();
//		while (paths.hasNext()) {
//			Tools.println(paths.next());
//		}
//
//		List<Node> relalist = getRelatedNodes(startNode, RelTypes.Has, "C2003903");
//		for (Node node1 : relalist) {
//			Tools.println(node1.getLabels().iterator().next().name() + ":" + node1);
//		}

	}

	private static void createGeoDatas() {
		Condition cd = Cnd.wrap("1=1");
		// cd = Cnd.wrap(" Id in (88, 360, 483, 806, 807, 817, 820)");
		List<GEO_Data> list = basicDao.search(GEO_Data.class, cd);
		// Tools.println("list.size:" + list.size());
		for (int i = 0; i < list.size(); i++) {
			try (Transaction tx = Neo4jDao.db.beginTx();) {
				GEO_Data geo = list.get(i);
				Tools.println((i + 1) + "/" + list.size() + " geoId:" + geo.getId());
	
				List<Node> listNodes = neo4jDap.getNodes(labelGeo, "Id", geo.getId());
				if (listNodes.size() > 1) {
					for (int j = 1; j < listNodes.size(); j++) {
						listNodes.get(j).delete();
					}
				}
	
				Node geoNode = neo4jDap.getNode(labelGeo, "Id", geo.getId());
				if(geoNode==null)geoNode = Neo4jDao.db.createNode(labelGeo);
				Tools.setProperty(geoNode, geo);

				createMetaMaps(geoNode);

				tx.success();
			}
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
			// Tools.println("list<MetaMapGroup>.size:" + list.size());
	
			for (int i = 0; i < list.size(); i++) {
				MetaMapGroup map = list.get(i);
				Tools.println("\t" + (i + 1) + "/" + list.size() + " "
						+ map.getUMLSConceptUniqu());
	
				Node mapNode = neo4jDap.getNode(labelMetaMap, "UMLSConceptUniqu",
						map.getUMLSConceptUniqu());
				if (mapNode == null)
					mapNode = Neo4jDao.db.createNode(labelMetaMap);
				Tools.setProperty(mapNode, map);

				List<Relationship> relalist = neo4jDap.getRelatedNodes(Relationship.class, geoNode,
						mapNode,
						"Id", "UMLSConceptUniqu");
				if (relalist.size() == 0) {
					mapNode.createRelationshipTo(geoNode, RelTypes.Has);
					Tools.println("createRelationshipTo: GEO:" + geoNode.getProperty("Id")
							+ " " + RelTypes.Has + " MetaMap:" + map.getUMLSConceptUniqu());
				} else
					for (Relationship r : relalist) {
						Tools.println("exits rela: node:" + geoNode.getId() + " rela:"
								+ r.getId() + " node2:" + mapNode.getId());
					}
			}
		}
	}



}