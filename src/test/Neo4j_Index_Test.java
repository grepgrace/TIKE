package test;

import java.util.*;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.Index;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;

import edu.wakehealth.dr.ddi.dao.Neo4jDao;
import edu.wakehealth.dr.ddi.model.geo.*;
import edu.wakehealth.dr.ddi.utils.Tools;

public class Neo4j_Index_Test extends NutZTest {
	protected static Neo4jDao neo4jDap = new Neo4jDao();

	private final static Label labelGeo = DynamicLabel.label("geo");
	private final static Label labelMetaMap = DynamicLabel.label("metamap");

	private static enum RelTypes implements RelationshipType {KNOWS, Has, Is}

	public static void main(String[] args) {
		try {

			Index<Node> nodeIndex = Neo4jDao.db.index().forNodes("nodes");

			// String statement = "MATCH (n) Where n.Id is not null RETURN n ";
			// Iterable<Node> list = neo4jDap.query(Node.class, statement,
			// null);
			// for (Node node : list) {
			// if (node.hasProperty("title"))
			// nodeIndex.add(node, "title", node.getProperty("title"));
			// }

			Node geo1 = nodeIndex
					.get("title",
							"Estrogen positive breast cancer recurrence during tamoxifen therapy: microdissected tumor")
					.getSingle();
			System.out.println(geo1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDao();
		}

	}



}