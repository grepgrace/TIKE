package com.tike.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.*;

import com.tike.dao.Neo4jDao;
import com.tike.model.geo.MetaMap;
import com.tike.utils.Tools;

@IocBean
@InjectName
public class Index extends BaseController {

	@At("/")
	@GET
	public View indexGet(HttpServletRequest req) {
		return setView(req, "index");
	}

	@At("/")
	@POST
	public View indexPOST(HttpServletRequest req) {
		return setView(req, "index");
	}


	// /post?global_search_keyword=aaa
	@At("/GenomicFeatureWindow")
	@POST
	@Ok("raw:html")
	public String indexPost(HttpServletRequest req, String keyword, String action) {
		req.getSession().setAttribute("keyword", keyword);
		return "GenomicFeatureWindow";
	}

	// /GlobalSearch?cType=taxon&cId=&dm=&pk=857264218
	@At("/GlobalSearch")
	@GET
	public View globalSearchGet(HttpServletRequest request, String keyword) {
		bodyClass = "fluid";
		String _keyword = keyword;
		if (_keyword == null)
			_keyword = (String) request.getSession().getAttribute("keyword");

		if (_keyword != null && !"".equals(_keyword)) {
			String filepath = request.getSession().getServletContext().getRealPath("/")
					+ "tmpKeyword/" + request.getSession().getId() + ".txt";
			if (!_keyword.endsWith("\r\n"))
				_keyword = _keyword + "\r\n";
			try {
				Tools.writeFile(filepath, _keyword);
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<MetaMap> metaMapList = Tools.getMimeMaps(new File(filepath));
			List<Node> list = new ArrayList<>();
			for (MetaMap metaMap : metaMapList) {
				Node metaNode = neo4jDao.getNode(Neo4jDao.labelMetaMap, "UMLSConceptUniqu",
						metaMap.getUMLSConceptUniqu());
				if (metaNode != null)
					list.addAll(neo4jDao.getRelaNodes(metaNode,
						new RelationshipType[] { Neo4jDao.RelTypes.Has }));
			}
			request.setAttribute("geoList", list);
		}

		request.setAttribute("keyword", _keyword);
		return setView(request, "GlobalSearch");
	}

	// /ExperimentListWindow?action=b&cacheability=PAGE
	@At("/ExperimentListWindow")
	@POST
	@Ok("raw:js")
	public String ExperimentListWindow(HttpServletRequest req) {
		return "ExperimentListWindow";
	}

	// /getGlobalSearchCounts.jsp?_dc=1415200863154&keyword=cancer&spellcheck=true
	@At("/getGlobalSearchCounts")
	@GET
	@Ok("raw:js")
	public String getGlobalSearchCounts(HttpServletRequest request, String keyword) {
//		SolrInterface solr = new SolrInterface();
//		String spellcheck = request.getParameter("spellcheck");
		JSONObject result = new JSONObject();
//
//		if (Boolean.parseBoolean(spellcheck)) {
//			JSONObject a = solr.getSpellCheckerResult(keyword);
//			result.put("suggestion", a.get("suggestion"));
//		}
//
		JSONArray data = new JSONArray();
//		solr.setCurrentInstance("GenomicFeature");
//		JSONObject obj = solr.getSummaryforGlobalSearch(keyword);
//		data.add(obj);
//		solr.setCurrentInstance("GenomeFinder");
//		obj = solr.getSummaryforGlobalSearch(keyword);
//		data.add(obj);
//		solr.setCurrentInstance("GlobalTaxonomy");
//		obj = solr.getSummaryforGlobalSearch(keyword);
//		data.add(obj);
//		solr.setCurrentInstance("GENEXP_Experiment");
//		obj = solr.getSummaryforGlobalSearch(keyword);
//		data.add(obj);
//
		result.put("data", data);
		request.setAttribute("result", result);

		request.setAttribute("bodyClass", "fluid");
		return result.toString();
	}

}
