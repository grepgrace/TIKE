package edu.wakehealth.test;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.solr.client.solrj.*;
import org.apache.solr.client.solrj.impl.*;
import org.apache.solr.client.solrj.request.*;
import org.apache.solr.client.solrj.response.*;
import org.apache.solr.common.*;
import org.nutz.json.Json;
import org.nutz.lang.Dumps;

public class SolrTest {
	public static void main(String[] args) throws Exception {
		SolrAdd();
	}
	

	public static void SolrAdd() throws SolrServerException, IOException {
		HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8983/solr/");
		// solrServer.setMaxTotalConnections(100);
		// solrServer.setSoTimeout(10000);// socket read timeout
		// solrServer.setConnectionTimeout(5000);

		// To use a local, embedded server instead:
		// SolrServer server = new EmbeddedSolrServer();

		// If you wish to delete all the data from the index, do this
		// server.deleteByQuery( "*:*" );// CAUTION: deletes everything!

		// Construct a document
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "id1", 1.0f);
		doc1.addField("name", "doc1", 1.0f);
		doc1.addField("price", 10);
		// Construct another document. Each document can be independently be
		// added but it is more efficient to do a batch update. Every call
		// to
		// SolrServer is an Http Call (This is not true for
		// EmbeddedSolrServer).
		SolrInputDocument doc2 = new SolrInputDocument();
		doc2.addField("id", "id2", 1.0f);
		doc2.addField("name", "doc2", 1.0f);
		doc2.addField("price", 20);
		// Fields "id","name" and "price" are already included in Solr
		// installation, you must add your new custom fields in SchemaXml.

		// Create a collection of documents
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		docs.add(doc1);
		docs.add(doc2);

		// Add the documents to Solr
		List<SolrInputDocument> docs1 = new ArrayList<SolrInputDocument>();
		for (int i = 0; i < 100; i++) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", "geo id " + i);
			doc.addField("title", "geo title" + i);
			doc.addField("author", "geo author" + i);
			doc.addField("name", "geo name" + i);
			doc.addField("subject", "geo subject" + i);
			docs1.add(doc);
		}
		solrServer.add(docs1);
		// Do a commit
		// solrServer.commit();

		// To immediately commit after adding documents, you could use:
		UpdateRequest req = new UpdateRequest();
		req.setAction(UpdateRequest.ACTION.COMMIT, false, false);
		req.add(docs1);
		//Status: success
//		Response:{
//		  "responseHeader": {
//		    "status": 0,
//		    "QTime": 3
//		  }
//		}
		UpdateResponse rsp = req.process(solrServer);
		System.out.println("Status:" + rsp.getStatus() + " QTime:" + rsp.getQTime());
	}

}