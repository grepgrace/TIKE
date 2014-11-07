<%@ page language="java" contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List" %>
<%@ page import="org.neo4j.graphdb.Node" %>
<% 
    List<Node> list =(List<Node>) request.getAttribute("geoList");
%>
<div class="container tabs-above">
    <!-- start regionTop -->
    <div id='regionTop'></div>
    <!-- end of regionTop -->
</div>
<div class="container">
    <section role="main" class="main">
        <!-- start regionA -->
        <div id="fluid_page_wrapper">
            <h3 class="section-title normal-case close2x"><span class="wrap">TIKE Search</span></h3>
            <div style="display:none">
                <form method="post" action="#" id="fTableForm">
                    <input type="hidden" value="GlobalSearch" name="tablesource" id="tablesource">
                    <input type="hidden" value="" name="fileformat" id="fileformat">
                    <input type="hidden" value="" name="fastaaction" id="fastaaction">
                    <input type="hidden" value="" name="fastatype" id="fastatype">
                    <input type="hidden" value="" name="fastascope" id="fastascope">
                    <input type="hidden" value="" name="fids" id="fids">
                    <input type="hidden" value="" name="cat" id="cat">
                    <input type="hidden" value="" id="download_keyword" name="download_keyword">
                    <input type="hidden" value="cancer" id="keyword" name="keyword">
                </form>
            </div>
            <div style="display:none;" id="copy-button"></div>
            <div class="mw" id="mw" style="height: 1050px;">
                <div>
                    <div id="med"><span style="font-size: 16px;padding: 0px 0px 0px 12px;font-weight: bold;">Show Results in:</span><span style="font-size:16px;padding-left: 80px;">Showing results for </span><b style="font-size:15px;"><i>${keyword}</i></b><br></div>
                </div>
                <div style="color: rgb(153, 153, 153); font-size: 13px; top: 23px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; padding-left: 230px; padding-top: 6px; padding-bottom: 6px; visibility: hidden; height: 0px;" id="searching_span"><img alt="Loading Icon" src="/patric/images/loading.gif"> Loading...</div>
                <div id="rcnt" style="position:relative; padding-top:3px;">
                    <div style="" id="leftnavc">
                        <div style="position:absolute;top:1px;width:132px" id="leftnav">
                            <div id="ms">
                                <ul>
                                    <li style="background-image: url('/patric/images/patric_search_group_icon2.png');background-repeat: no-repeat;padding: 0px 0px 0px 25px; background-position: left;" id="li__" class="mitem msel first">Summary</li>
                                    <li style="background-image: url('/patric/images/workspace_feature_group_20x20.png');background-repeat: no-repeat;padding: 0px 0px 0px 25px; background-position: left;" id="li__0" class="mitem"><a href="javascript:loadSearchResults(0);" class="kl">GEO (<%=list.size()%>)</a></li>
                                    <!-- <li style="background-image: url('/patric/images/workspace_genome_group_20x20.png');background-repeat: no-repeat;padding: 0px 0px 0px 25px;background-position: left;" id="li__1" class="mitem"><a href="javascript:loadSearchResults(1);" class="kl">Genomes (53)</a></li>
                                        <li style="background-image: url('/patric/images/workspace_taxa_20x20.png');background-repeat: no-repeat;padding: 0px 0px 0px 25px;background-position: left;" id="li__2" class="mitem">Taxa (0)</li>
                                        <li style="background-image: url('/patric/images/workspace_taxa_20x20.png');background-repeat: no-repeat;padding: 0px 0px 0px 25px;background-position: left;" id="li__3" class="mitem last"><a href="javascript:loadSearchResults(3);" class="kl">Experiments (10)</a></li> -->
                                </ul>
                                <div class="lnsep"></div>
                                <div id="GenericSelector" style="display: none;"></div>
                            </div>
                        </div>
                    </div>
                    <div style="visibility: visible;" id="center_col">
                        <div style="height: 1000px; overflow: auto; visibility: visible;" id="summary_div">
                            <div style="background-color: whiteSmoke;" class="x-grid-cell-inner" id="summary_div_feature">
                                <div style="line-height: 28px;">
                                    <span style="font-size: 16px; font-weight: bold;" id="summary_div_feature_span_1">GEO</span>
                                    <span style="font-weight: bold;" id="summary_div_feature_span_2">
                                    <a href="javascript:loadSearchResults('0')" style="font-size: 16px; text-decoration: none;">(<%=list.size()%>)</a>
                                    </span>
                                </div>
                                <%
                                    for (int i = 0; i < Math.min(list.size(), 3); i++){
                                          Node geoNode=list.get(i);
                                    %>
                                <div class="clear"></div>
                                <div style="float: left"><img style="float: left; padding: 5px 6px 2px 2px;" src="/patric/images/global_feature_patric.png"></div>
                                <div style="line-height: 1.5; white-space: normal !important; float: left; width: 700px;">
                                    <span style="font-size: 14px;"><a href="http://www.ncbi.nlm.nih.gov/geo/query/acc.cgi?acc=<%= geoNode.getProperty("Accession") %>"><%= geoNode.getProperty("title") %></a></span><br>
                                    <span style="color: #C60;">Type:<%= geoNode.getProperty("Accession") %></span> <br>
                                    <%= geoNode.getProperty("summary") %>
                                </div>
                                <%--
                                    <div class="clear"></div>
                                    <div style="float: left">
                                    	<img style="float: left; padding: 5px 6px 2px 2px;"
                                    		src="/patric/images/global_feature_patric.png">
                                    </div>
                                    <div
                                    	style="line-height: 1.5; white-space: normal !important; float: left; width: 700px;">
                                    	<span style="font-size: 14px;"><a
                                    		href="Feature?cType=feature&amp;cId=23794064">Breast <em
                                    			class="global_em">cancer</em> type 1 susceptibility protein
                                    			homolog
                                    	</a></span><br>
                                    	<span style="color: #C60;">Synechococcus sp. CC9311</span> <br>
                                    	CDS<span style="font-size: 14px;"> | </span>VBISynSp88089_1589<span
                                    		style="font-size: 14px;"> | </span>sync_1519<br>REFSEQ
                                    	PRODUCT: Breast <em class="global_em">cancer</em> type 1
                                    	susceptibility protein
                                    </div>
                                    <div class="clear"></div>
                                    <div style="float: left">
                                    	<img style="float: left; padding: 5px 6px 2px 2px;"
                                    		src="/patric/images/global_feature_patric.png">
                                    </div>
                                    <div style="line-height: 1.5; white-space: normal !important; float: left; width: 700px;">
                                    	<span style="font-size: 14px;"><a
                                    		href="Feature?cType=feature&amp;cId=58822412">Fibronectin/fibrinogen-binding
                                    			protein</a></span><span style="font-size: 14px;"> | </span>yloA<br>
                                    	<span style="color: #C60;">Paenibacillus polymyxa</span> <br>
                                    	CDS<span style="font-size: 14px;"> | </span>VBIPaePol118909_3288<span
                                    		style="font-size: 14px;"> | </span>PPM_3142<br>REFSEQ
                                    	PRODUCT: serologically defined colon <em class="global_em">cancer</em>
                                    	antigen 1-like protein
                                    </div> --%>
                                <%} %>
                            </div>
                            <%--
                               <div style="background-color: whiteSmoke;"class="x-grid-cell-inner" id="summary_div_genome">
								    <div style="line-height: 28px;">
								        <span style="font-size: 16px; font-weight: bold;" id="summary_div_genome_span_1">Genomes</span><span style="font-weight: bold;" id="summary_div_genome_span_2">
								        <a href="javascript:loadSearchResults('1')" style="font-size: 16px; text-decoration: none;">(53)</a>
								        </span>
								    </div>
								    <div class="clear"></div>
								    <div style="float: left;">
								        <img style="padding: 8px 3px 0px 3px;" src="/patric/images/global_genome.png">
								    </div>
								    <div style="line-height: 1.5; white-space: normal !important; float: left; width: 700px;">
								        <a href="Genome?cType=genome&amp;cId=237915" style="font-size: 14px;">Helicobacter pylori ELS37</a><br>
								        <span style="color: #C60;">1 Chromosome<span style="font-size: 14px;"> | </span>1 Plasmid
								        </span><br>SEQUENCED: 2012-03-23 (Washington University Med.
								        School)<br>HOST: Human, Homo sapiens<br>ISOLATION
								        SOURCE: gastric <em class="global_em">cancer</em> patient from
								        El Salvador rural area<span style="font-size: 14px;"> |
								        </span>ISOLATION COMMENTS: isolate from gastric <em class="global_em">cancer</em>
								        patient from El Salvador rural area<br>COMMENT: Clinical
								        Helicobacter pylori isolate from gastric <em class="global_em">cancer</em>
								        patient from El Salvador rural area.
								    </div>
								    <div class="clear"></div>
								    <div style="float: left;">
								        <img style="padding: 8px 3px 0px 3px;" src="/patric/images/global_genome.png">
								    </div>
								    <div style="line-height: 1.5; white-space: normal !important; float: left; width: 700px;">
								        <a href="Genome?cType=genome&amp;cId=173672" style="font-size: 14px;">Helicobacter pylori PeCan4</a><br>
								        <span style="color: #C60;">1 Chromosome<span style="font-size: 14px;"> | </span>1 Plasmid
								        </span><br>SEQUENCED: 2010-10-01 (Genome Sequencing Center (GSC)
								        at Washington University (WashU) School of Medicine)<br>HOST:
								        Human, Homo sapiens<br>ISOLATION SOURCE: Peruvian gastric
								        <em class="global_em">cancer</em> patient<span style="font-size: 14px;"> | </span>ISOLATION COMMENTS:
								        isolated from a Peruvian gastric <em class="global_em">cancer</em>
								        patient<br>COMMENT: Helicobacter pylori PeCan4.This strain
								        was isolated from a Peruvian gastric <em class="global_em">cancer</em>
								        patient.
								    </div>
								    <div class="clear"></div>
								    <div style="float: left;">
								        <img style="padding: 8px 3px 0px 3px;" src="/patric/images/global_genome.png">
								    </div>
								    <div style="line-height: 1.5; white-space: normal !important; float: left; width: 700px;">
								        <a href="Genome?cType=genome&amp;cId=285170" style="font-size: 14px;">Fusobacterium nucleatum CC53</a><br>
								        <span style="color: #C60;">379 Contigs</span><br>SEQUENCED:
								        2013-03-18 (BC <em class="global_em">Cancer</em> Agency)<br>HOST:
								        Human, Homo sapiens<br>COMMENT: "A strain of Fusobacterium
								        nucleatum, which we named CC53, was cultured from a colon <em class="global_em">cancer</em> tumor surgical section and its
								        genome sequenced."
								    </div>
								</div>
								<div class="x-grid-cell-inner" id="summary_div_taxa">
								    <div style="line-height: 28px;">
								        <span style="font-size: 16px; font-weight: bold;" id="summary_div_taxa_span_1">Taxonomy</span>
								        <span style="font-weight: bold;" id="summary_div_taxa_span_2"></span>
								    </div>
								    <div class="clear"></div>
								    <div class="clear"></div>
								    <div class="clear"></div>
								</div>
								<div class="x-grid-cell-inner" id="summary_div_experiment">
								    <div style="line-height: 28px;">
								        <span style="font-size: 16px; font-weight: bold;" id="summary_div_experiment_span_1">Experiments</span>
								        <span style="font-weight: bold;" id="summary_div_experiment_span_2">
								        <a href="javascript:loadSearchResults('3')" style="font-size: 16px; text-decoration: none;">(10)</a>
								        </span>
								    </div>
								    <div class="clear"></div>
								    <div style="line-height: 1.8; white-space: normal !important;">
								        <div>
								            <img style="float: left; padding: 3px 3px 0px 3px;" src="/patric/images/global_experiment.png"><a href="SingleExperiment?cType=taxon&amp;cId=2&amp;eid=421862">High-throughput
								            screening for Salmonella avirulent mutants that retain targeting of solid tumors </a><br>
								            <span style="color: #C60;">Salmonella enterica</span><br>
								            Accession : GSE19609
								        </div>
								    </div>
								    <div class="clear"></div>
								    <div style="line-height: 1.8; white-space: normal !important;">
								        <div>
								            <img style="float: left; padding: 3px 3px 0px 3px;"
								                src="/patric/images/global_experiment.png"><a
								                href="SingleExperiment?cType=taxon&amp;cId=2&amp;eid=659101">Comparison
								            of the CcpA and CovR Transcriptomes in Group A Streptococcus</a><br>
								            <span style="color: #C60;">Streptococcus pyogenes</span><br>
								            Accession : GSE20212
								        </div>
								    </div>
								    <div class="clear"></div>
								    <div style="line-height: 1.8; white-space: normal !important;">
								        <div>
								            <img style="float: left; padding: 3px 3px 0px 3px;"
								                src="/patric/images/global_experiment.png"><a
								                href="SingleExperiment?cType=taxon&amp;cId=2&amp;eid=755796">Dissecting
								            the transcriptional response to DNA damage in Helicobacter
								            pylori.</a><br>
								            <span style="color: #C60;">Helicobacter pylori</span><br>
								            Accession : GSE19334
								        </div>
								    </div>
								</div> --%>
                        </div>
                        <div style="visibility: hidden; height: 0px;" id="PATRICGrid"></div>
                    </div>
                </div>
            </div>
            <script src="/patric-common/js/ZeroClipboard.js" type="text/javascript"></script>
            <script src="/patric-common/js/grid/copybutton.js" type="text/javascript"></script>
            <script src="/patric-common/js/grid/checkcolumn.js" type="text/javascript"></script>
            <script src="/patric-common/js/grid/pagingbar.js" type="text/javascript"></script>
            <script src="/patric-common/js/grid/toolbar.js" type="text/javascript"></script>
            <script src="/patric-common/js/grid/gridoptions.js" type="text/javascript"></script>
            <script src="/patric-common/js/grid/PATRICSelectionModel.js" type="text/javascript"></script>
            <script src="/patric-common/js/grid/PATRICGrid.js" type="text/javascript"></script>
            <script src="/patric-common/js/grid/table_checkboxes.js" type="text/javascript"></script>
            <script src="/patric-common/js/createtree.js" type="text/javascript"></script> 
            <script src="/patric-common/js/parameters.js" type="text/javascript"></script>
            <script src="/patric/js/vbi/AddToWorkspace.min.js" type="text/javascript"></script>
            <script src="/patric-common/js/grid/loadgrid.js" type="text/javascript"></script>
            <script src="/patric-searches-and-tools/js/globalsearch.js" type="text/javascript"></script>
            <script src="/patric-searches-and-tools/js/FacetTree.js" type="text/javascript"></script>
            <script src="/patric-searches-and-tools/js/solrKeyword.js" type="text/javascript"></script>
            <script src="/patric-searches-and-tools/js/json2.js" type="text/javascript"></script>
            <script type="text/javascript">
                //&lt;![CDATA[
                var $Page;
                
                Ext.onReady(function() {
                	
                	var pageProperties = {
                		name: ["GEO"/*,  "GOSearch", "ECSearch", "GlobalProteinFamilies", "GlobalPathwaySearch", "Genome", "GlobalTaxonomy", "GENEXP_Experiment"*/],
                		text: ["GEO"/*,  "by GO Terms", "by EC Numbers", "by Protein Families", "by Pathways",  "Genomes", "Taxa", "Experiments" */],
                		resultcount: [0, /*0, 0, 0, 0,*/ 0, 0, 0],
                		need: ["GEO", /*"go", "ec", "figfam", "pathway",*/ "0", "taxonomy", "0"],
                		divID: ["GEO", /*"feature_by_go", "feature_by_ec", "feature_by_protein", "feature_by_pathway",*/ "genome", "taxa", "experiment"],
                		summaryHeaderText: ["GEO", /*"Features by Go Terms", "Features by EC Numbers", "Features by Protein Families", "Features by Pathways",*/ "Genomes", "Taxonomy", "Experiments"],
                		summaryPostText :["GEO", /*"features", "features", "features", "features",*/ "genomes", "taxas", "experiments"],
                		summary_data: [[], [], [], []],
                		renderFunction: [renderListFeature, /*renderListGO, renderListEC, renderListFigFam, renderListPathway,*/ renderListGenome, renderListTaxonomy, renderListExperiment],
                		alternativeKW: null,
                		tree: null,
                		treeDS: null,
                		items:3,
                		cart: true,
                		cartType:'cart',
                		plugin:true,
                		plugintype:"checkbox",
                		scm:[],
                		hideToolbar:true,
                		extraParams:getExtraParams,
                		callBackFn:CallBack,
                		remoteSort:false,
                		hash:{
                			aP: [1, 1, 1, 1],
                			cat: "summary",
                			key: "-855383396",
                			spellcheck: true
                		},
                		reconfigure: true,
                		model:["Geo", /*"GO", "EC", "Figfam", "Pathway",*/ "Genome", "Taxonomy", "Experiment"],
                		fids: [],
                		gridType: "",
                		current_hash: window.location.hash?window.location.hash.substring(1):"",
                		url: ['/portal/portal/patric/GlobalSearch/GlobalSearchWindow?action=b&amp;cacheability=PAGE'],
                		loaderFunction: function(){SetLoadParameters();arrangeCSS();loadTable();},
                		stateId: ['globalfeaturelist', 'globalgenomelist', 'globaltaxalist'],
                		pagingBarMsg: ['Displaying features {0} - {1} of {2}', 'Displaying genomes {0} - {1} of {2}', 'Displaying taxonomies {0} - {1} of {2}', 'Displaying experiments {0} - {1} of {2}']
                	};
                	SetPageProperties(pageProperties);
                	SetLoadParameters();
                	SetIntervalOrAPI();
                	getSummaryandCreateLayout();
                	overrideButtonActions();
                });
                //]]&gt;
            </script>
        </div>
        <!-- end of regionA -->
    </section>
</div>