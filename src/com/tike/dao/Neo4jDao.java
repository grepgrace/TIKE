package com.tike.dao;

import java.util.*;

import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.StringLogger;
import org.neo4j.rest.graphdb.RestAPI;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.ConvertedResult;

public class Neo4jDao {
	static String DB_PATH = "C:/Users/kelu/Documents/Neo4j/tike.db";
	public static GraphDatabaseService db;
	static RestGraphDatabase gd = new RestGraphDatabase(DB_PATH);

	public final static Label labelGeo = DynamicLabel.label("geo");
	public final static Label labelMetaMap = DynamicLabel.label("metamap");

	public static enum RelTypes implements RelationshipType {
		KNOWS, Has, Is
	}

	public Neo4jDao() {
		try {
			db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		} catch (Exception e) {
			DB_PATH = "http://localhost:7474/db/data";
			db = new RestGraphDatabase(DB_PATH);
		}
	}

	public Node getNode(Label paramLabel, String paramString, Object paramObject) {
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

	public List<Node> getNodes(Label paramLabel, String paramString, Object paramObject) {
		List<Node> list = new ArrayList<>();
		Iterator<Node> nodes = db.findNodesByLabelAndProperty(paramLabel, paramString, paramObject).iterator();
		while (nodes.hasNext()) {
			Node node = nodes.next();
			list.add(node);
			//System.out.println(node.getLabels().iterator().next().name() + ":" + node);
		}
		return list;
	}

	public List<Node> getRelaNodes(Node node) {
		return getRelaNodes(node, new RelationshipType[] {});
	}

	public List<Node> getRelaNodes(Node node, RelationshipType[] relaTypes) {
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

	public <T> List<T> getRelatedNodes(Class<T> paramClass,Node node1, Node node2, String name1, String name2) {
		List<T> list = new ArrayList<>();

//		ExecutionEngine engine = new ExecutionEngine(db, StringLogger.DEV_NULL);
//		scala.collection.Iterator<T> resultIterator = null;
		String statement = "MATCH (a)-[r]-(b) " + " WHERE a." + name1 + "= {" + name1 + "} " + ""
				+ " and b." + name2 + "={" + name2 + "} " + " RETURN r";
		// System.out.println(queryString);
		Map<String, Object> params = new HashMap<>();
		params.put(name1, node1.getProperty(name1));
		params.put(name2, node2.getProperty(name2));
//		resultIterator = engine.execute(queryString, parameters).columnAs("r");
		RestAPI api = ((RestGraphDatabase) db).getRestAPI();
		RestCypherQueryEngine engine = new RestCypherQueryEngine(api);
		ConvertedResult<T> result = engine.query(statement, params).to(paramClass);
		Iterator<T> results = result.iterator();
		while (results.hasNext()) {
			list.add(results.next());
		}
		return list;
	}

	public <T> List<T> query(Class<T> paramClass, String statement, Map<String, Object> params) {
		RestAPI api = ((RestGraphDatabase) db).getRestAPI();
		RestCypherQueryEngine engine = new RestCypherQueryEngine(api);
		ConvertedResult<T> result = engine.query(statement, params).to(paramClass);
		Iterator<T> results = result.iterator();
		List<T> list = new ArrayList<>();
		while (results.hasNext()) {
			list.add(results.next());
		}
		return list;
	}
}
