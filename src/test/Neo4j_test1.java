package test;

import java.util.Iterator;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Neo4j_test1 {

	static String DB_PATH = "C:/Users/kelu/Documents/Neo4j/music1.db";

	private static enum RelationshipTypes implements RelationshipType {
		PUBLISH, // 出版 歌手出版专辑
		CONTAIN // 专辑 专辑包含很多歌曲
	}

	/**
	 * 使用节点和关系
	 * 
	 * @author 裴东辉
	 * @since 2014-8-4 上午9:53:49
	 */
	public void useNodeAndRelationship() {
//		try {
//			FileUtils.deleteRecursively(new File(DB_PATH));
//		} catch (IOException e) {
//			e.printStackTrace();
//		} // 删除数据库
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		try (Transaction tx = graphDb.beginTx()) {
			Node node1 = graphDb.createNode();
			node1.setProperty("name", "歌手 1");

			Node node2 = graphDb.createNode();
			node2.setProperty("name", "专辑 1");
			node1.createRelationshipTo(node2, RelationshipTypes.PUBLISH);

			Node node3 = graphDb.createNode();
			node3.setProperty("name", "歌曲 1");
			node2.createRelationshipTo(node3, RelationshipTypes.CONTAIN);

			tx.success();
		} finally {
			graphDb.shutdown();
		}
	}

	/**
	 * 使用索引
	 * 
	 * @author 裴东辉
	 * @since 2014-8-4 上午9:53:44
	 */
	public void useIndex() {
//		try {
//			FileUtils.deleteRecursively(new File(DB_PATH));
//		} catch (IOException e) {
//			e.printStackTrace();
//		} // 删除数据库
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		try (Transaction tx = graphDb.beginTx()) {
			Index<Node> index = graphDb.index().forNodes("nodes");
			Node node1 = graphDb.createNode();
			node1.setProperty("name", "donghui");
			node1.setProperty("gender", "boy");
			index.add(node1, "name", "donghui");

			Object result = index.get("name", "donghui").getSingle().getProperty("gender");
			info(result); // 输出 "boy"

			tx.success();
		} finally {
			graphDb.shutdown();
		}
	}

	/**
	 * 使用节点和关系和索引
	 * 
	 * @author 裴东辉
	 * @since 2014-8-4 上午9:53:49
	 */
	public void useNodeAndRelationshipAndIndex() {
		// try {
		// FileUtils.deleteRecursively(new File(DB_PATH));
		// } catch (IOException e) {
		// e.printStackTrace();
		// } // 删除数据库
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		try (Transaction tx = graphDb.beginTx()) {
			Index<Node> index = graphDb.index().forNodes("nodes");

			Node node1 = graphDb.createNode();
			node1.setProperty("name", "歌手 1");
			index.add(node1, "name", "歌手 1");

			// 专辑1里面的信息
			Node node1_1 = graphDb.createNode();
			node1_1.setProperty("name", "歌手 1的专辑 1");
			Relationship relationship1_1 = node1.createRelationshipTo(node1_1,
					RelationshipTypes.PUBLISH);
			relationship1_1.setProperty("publishyear", "2012");
			index.add(node1_1, "name", "歌手 1的专辑 1");

			Node node1_1_1 = graphDb.createNode();
			node1_1_1.setProperty("name", "歌手 1的专辑 1的歌曲 1");
			Relationship relationship1_1_1 = node1_1.createRelationshipTo(node1_1_1,
					RelationshipTypes.CONTAIN);
			relationship1_1_1.setProperty("time", "2012-10-10");
			index.add(node1_1_1, "name", "歌手 1的专辑 1的歌曲 1");

			Node node1_1_2 = graphDb.createNode();
			node1_1_2.setProperty("name", "歌手 1的专辑 1的歌曲 2");
			Relationship relationship1_1_2 = node1_1.createRelationshipTo(node1_1_2,
					RelationshipTypes.CONTAIN);
			relationship1_1_2.setProperty("time", "2012-12-12");
			index.add(node1_1_2, "name", "歌手 1的专辑 1的歌曲 2");

			// 专辑2里面的信息
			Node node1_2 = graphDb.createNode();
			node1_2.setProperty("name", "歌手 1的专辑 2");
			Relationship relationship1_2 = node1.createRelationshipTo(node1_2,
					RelationshipTypes.PUBLISH);
			relationship1_2.setProperty("publishyear", "2014");
			index.add(node1_2, "name", "歌手 1的专辑 2");

			Node node1_2_1 = graphDb.createNode();
			node1_2_1.setProperty("name", "歌手 1的专辑 2的歌曲 1");
			Relationship relationship1_2_1 = node1_2.createRelationshipTo(node1_2_1,
					RelationshipTypes.CONTAIN);
			relationship1_2_1.setProperty("time", "2014-10-10");
			index.add(node1_2_1, "name", "歌手 1的专辑 2的歌曲 1");

			Node node1_2_2 = graphDb.createNode();
			node1_2_2.setProperty("name", "歌手 1的专辑 2的歌曲 2");
			Relationship relationship1_2_2 = node1_2.createRelationshipTo(node1_2_2,
					RelationshipTypes.CONTAIN);
			relationship1_2_2.setProperty("time", "2014-10-10");
			index.add(node1_2_2, "name", "歌手 1的专辑 2的歌曲 2");

			// 对以上关系的描述
			// 1、歌手 1有两个专辑，每个专辑里面有两手歌曲，现在要查找歌手 1所有的歌曲
			Node singger = index.get("name", "歌手 1").getSingle();
			// 2、歌手1的所有出版的专辑
			// 关于version-2.0后的问题，还是官方API比较给力：http://docs.neo4j.org/chunked/stable/tutorial-traversal-java-api.html
			TraversalDescription singgertd = graphDb.traversalDescription().depthFirst()
					.relationships(RelationshipTypes.PUBLISH, Direction.OUTGOING)
					.evaluator(Evaluators.excludeStartPosition());
			// 节点之间的联系
			/*
			 * for(Node publish:singgertd.traverse(singger).nodes()){
			 * info(publish.getProperty("name")); //3、对每个专辑，找到这个专辑里面的多有歌曲
			 * TraversalDescription publishtd =graphDb.traversalDescription()
			 * .depthFirst()//深度优先遍历 .relationships(RelationshipTypes.CONTAIN,
			 * Direction.OUTGOING)
			 * .evaluator(Evaluators.excludeStartPosition()); for(Node
			 * music:publishtd.traverse(publish).nodes()){ //4、专辑里面的每一首歌曲
			 * info(music.getProperty("name")); } }
			 */

			// 关系之间的联系
			for (Relationship publishreationship : singgertd.traverse(singger).relationships()) {
				// 关系属性
				// info(publishreationship+"-"+publishreationship.getProperty("publishyear"));
				// Node startNode=publishreationship.getStartNode();//Node[0]-歌手
				// 1
				// info(startNode+"-"+startNode.getProperty("name"));
				Node endNode = publishreationship.getEndNode();// Node[1]-歌手
																// 1的专辑 1
				// info(endNode+"-"+endNode.getProperty("name"));
				// endNode下面的关系
				Node node = null;
				for (Relationship rs : endNode.getRelationships(Direction.OUTGOING)) {
					// 关系属性getRelationships(Direction dir) Returns all OUTGOING
					// or INCOMING relationships from this node.
					// 获取到endNode出发的，就是从
					// endNode.createRelationshipTo(node1_2_2,
					// RelationshipTypes.CONTAIN); 中将node1_2_2找到
					node = rs.getEndNode();// Node[1]-歌手 1的专辑 1
					info(node + "-" + node.getProperty("name"));
				}
			}

			tx.success();
		} finally {
			graphDb.shutdown();
		}
	}

	public static void main(String[] args) {
		// new Neo4j_test1().useNodeAndRelationship();
		// new Neo4j_test1().useIndex();
		// new Neo4j_test1().useNodeAndRelationshipAndIndex();

		t6();
	}

	public void info(Object obj) {
		System.out.println(obj.toString());
	}

	// public static String DB_PATH = "http://127.0.0.1:7474";
	private static ThreadLocal<GraphDatabaseService> tl = new ThreadLocal<GraphDatabaseService>();

	public static GraphDatabaseService getConnect() {
		GraphDatabaseService graphDb;
		synchronized (tl) {
			if (tl.get() == null) {
				graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
				registerShutdownHook(graphDb);
				tl.set(graphDb);
			} else {
				graphDb = tl.get();
			}
		}
		return graphDb;
	}

	static void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				graphDb.shutdown();
			}
		});
	}

	static void t1() {
		GraphDatabaseService db = getConnect();
		Transaction tx = db.beginTx();

		try {
			Node n1 = db.createNode();
			n1.setProperty("name", "n1");
			n1.setProperty("value", "v1");

			Node n2 = db.createNode();
			n2.setProperty("name", "n2");
			n2.setProperty("value", "v2");
			n2.setProperty("desc", "desc2");

			RelationshipType rt = DynamicRelationshipType.withName("relat1");
			Relationship rela = n1.createRelationshipTo(n2, rt); // RelTypes.KNOWS
			rela.setProperty("name", "rela1");

			tx.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.failure();
		} finally {
			tx.close();
		}
	}

	static void t2() {
		GraphDatabaseService db = getConnect();
		Transaction tx = db.beginTx();

		try {
			// NodesFunction nn = new NodesFunction(nn);
			Iterable<Node> nodes = db.getAllNodes();
			for (Node n : nodes) {
				System.out.println(n.getProperty("name"));
			}

			tx.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.failure();
		} finally {
			tx.close();
		}
	}

	static void t5() {
		GraphDatabaseService db = getConnect();
		Transaction tx = db.beginTx();
		try {
			ExecutionEngine engine = new ExecutionEngine(db);
			String query = "START n=node(*) RETURN n,n.name";
			ExecutionResult rs = engine.execute(query);
			ResourceIterator<Node> riter = rs.columnAs("n");
			while (riter.hasNext()) {
				Node n = riter.next();
				// n.setProperty("desc", "desc222222");
				// Iterable<String>ps = n.getPropertyKeys();
				Iterator<String> piter = n.getPropertyKeys().iterator();
				while (piter.hasNext()) {
					String key = piter.next();
					System.out.println(key + ":" + n.getProperty(key));
				}
				// n.setProperty("desc", "desc21");
			}
			tx.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.failure();
		}
		tx.close();
		db.shutdown();
	}

	static void t6() {
		GraphDatabaseService db = getConnect();
		Transaction tx = db.beginTx();

		try {
			String sql = "START n=node(1) SET n.desc='desc0000' ";
			ExecutionEngine engine = new ExecutionEngine(db);
			engine.execute(sql);

			tx.success();
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.failure();
		}
	}
}