<%@ page language="java" contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!--[if lt IE 7]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="en">
<!--<![endif]-->
<head>
<title>TIKE</title>
<meta charset="utf-8" />
<!-- <link rel="alternate" type="application/rss+xml" href="//enews.patricbrc.org/frontrss" title="PATRIC eNews Feed" /> -->
<!-- Mobile viewport optimized -->
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<link rel="stylesheet" href="//fonts.googleapis.com/css?family=Didact+Gothic" />
<link rel="stylesheet" href="//cdn.sencha.io/ext/gpl/4.2.1/resources/css/ext-all.css">
<link rel="stylesheet" href="${base}/patric/css/main.css" />
<link rel="stylesheet" href="${base}/patric/css/patric.css" />
<!-- PATRIC CSS & JS -->
<script type="text/javascript">(window.NREUM||(NREUM={})).loader_config={xpid:"VgMFVlFACQsFV1hSAQ=="};window.NREUM||(NREUM={}),__nr_require=function(t,e,n){function r(n){if(!e[n]){var o=e[n]={exports:{}};t[n][0].call(o.exports,function(e){var o=t[n][1][e];return r(o?o:e)},o,o.exports)}return e[n].exports}if("function"==typeof __nr_require)return __nr_require;for(var o=0;o<n.length;o++)r(n[o]);return r}({QJf3ax:[function(t,e){function n(t){function e(e,n,o){t&&t(e,n,o),o||(o={});for(var a=i(e),s=a.length,c=o[r]||(o[r]={}),f=0;s>f;f++)a[f].apply(c,n);return c}function o(t,e){s[t]=i(t).concat(e)}function i(t){return s[t]||[]}function a(){return n(e)}var s={};return{on:o,emit:e,create:a,listeners:i,_events:s}}var r="nr@context";e.exports=n()},{}],ee:[function(t,e){e.exports=t("QJf3ax")},{}],3:[function(t){function e(t,e,n,i,s){try{c?c-=1:r("err",[s||new UncaughtException(t,e,n)])}catch(f){try{r("ierr",[f,(new Date).getTime(),!0])}catch(u){}}return"function"==typeof a?a.apply(this,o(arguments)):!1}function UncaughtException(t,e,n){this.message=t||"Uncaught error with no additional information",this.sourceURL=e,this.line=n}function n(t){r("err",[t,(new Date).getTime()])}var r=t("handle"),o=t(4),i=t("ee"),a=window.onerror,s=!1,c=0;t("loader").features.err=!0,window.onerror=e,NREUM.noticeError=n;try{throw new Error}catch(f){"stack"in f&&(t(5),t(3),"addEventListener"in window&&t(1),window.XMLHttpRequest&&XMLHttpRequest.prototype&&XMLHttpRequest.prototype.addEventListener&&t(2),s=!0)}i.on("fn-start",function(){s&&(c+=1)}),i.on("fn-err",function(t,e,r){s&&(this.thrown=!0,n(r))}),i.on("fn-end",function(){s&&!this.thrown&&c>0&&(c-=1)}),i.on("internal-error",function(t){r("ierr",[t,(new Date).getTime(),!0])})},{1:4,2:7,3:5,4:15,5:6,ee:"QJf3ax",handle:"D5DuLP",loader:"G9z0Bl"}],4:[function(t,e){function n(t){i.inPlace(t,["addEventListener","removeEventListener"],"-",r)}function r(t){return t[1]}var o=(t(1),t("ee").create()),i=t(2)(o);if(e.exports=o,n(window),"getPrototypeOf"in Object){for(var a=document;a&&!a.hasOwnProperty("addEventListener");)a=Object.getPrototypeOf(a);a&&n(a);for(var s=XMLHttpRequest.prototype;s&&!s.hasOwnProperty("addEventListener");)s=Object.getPrototypeOf(s);s&&n(s)}else XMLHttpRequest.prototype.hasOwnProperty("addEventListener")&&n(XMLHttpRequest.prototype);o.on("addEventListener-start",function(t){if(t[1]){var e=t[1];"function"==typeof e?this.wrapped=e["nr@wrapped"]?t[1]=e["nr@wrapped"]:e["nr@wrapped"]=t[1]=i(e,"fn-",null,e.name||"anonymous"):"function"==typeof e.handleEvent&&i.inPlace(e,["handleEvent"],"fn-")}}),o.on("removeEventListener-start",function(t){var e=this.wrapped;e&&(t[1]=e)})},{1:15,2:16,ee:"QJf3ax"}],5:[function(t,e){var n=(t(2),t("ee").create()),r=t(1)(n);e.exports=n,r.inPlace(window,["requestAnimationFrame","mozRequestAnimationFrame","webkitRequestAnimationFrame","msRequestAnimationFrame"],"raf-"),n.on("raf-start",function(t){t[0]=r(t[0],"fn-")})},{1:16,2:15,ee:"QJf3ax"}],6:[function(t,e){function n(t,e,n){var r=t[0];"string"==typeof r&&(r=new Function(r)),t[0]=o(r,"fn-",null,n)}var r=(t(2),t("ee").create()),o=t(1)(r);e.exports=r,o.inPlace(window,["setTimeout","setInterval","setImmediate"],"setTimer-"),r.on("setTimer-start",n)},{1:16,2:15,ee:"QJf3ax"}],7:[function(t,e){function n(){c.inPlace(this,d,"fn-")}function r(t,e){c.inPlace(e,["onreadystatechange"],"fn-")}function o(t,e){return e}var i=t("ee").create(),a=t(1),s=t(2),c=s(i),f=s(a),u=window.XMLHttpRequest,d=["onload","onerror","onabort","onloadstart","onloadend","onprogress","ontimeout"];e.exports=i,window.XMLHttpRequest=function(t){var e=new u(t);try{i.emit("new-xhr",[],e),f.inPlace(e,["addEventListener","removeEventListener"],"-",function(t,e){return e}),e.addEventListener("readystatechange",n,!1)}catch(r){try{i.emit("internal-error",r)}catch(o){}}return e},window.XMLHttpRequest.prototype=u.prototype,c.inPlace(XMLHttpRequest.prototype,["open","send"],"-xhr-",o),i.on("send-xhr-start",r),i.on("open-xhr-start",r)},{1:4,2:16,ee:"QJf3ax"}],8:[function(t){function e(t){if("string"==typeof t&&t.length)return t.length;if("object"!=typeof t)return void 0;if("undefined"!=typeof ArrayBuffer&&t instanceof ArrayBuffer&&t.byteLength)return t.byteLength;if("undefined"!=typeof Blob&&t instanceof Blob&&t.size)return t.size;if("undefined"!=typeof FormData&&t instanceof FormData)return void 0;try{return JSON.stringify(t).length}catch(e){return void 0}}function n(t){var n=this.params,r=this.metrics;if(!this.ended){this.ended=!0;for(var i=0;c>i;i++)t.removeEventListener(s[i],this.listener,!1);if(!n.aborted){if(r.duration=(new Date).getTime()-this.startTime,4===t.readyState){n.status=t.status;var a=t.responseType,f="arraybuffer"===a||"blob"===a||"json"===a?t.response:t.responseText,u=e(f);if(u&&(r.rxSize=u),this.sameOrigin){var d=t.getResponseHeader("X-NewRelic-App-Data");d&&(n.cat=d.split(", ").pop())}}else n.status=0;r.cbTime=this.cbTime,o("xhr",[n,r,this.startTime])}}}function r(t,e){var n=i(e),r=t.params;r.host=n.hostname+":"+n.port,r.pathname=n.pathname,t.sameOrigin=n.sameOrigin}if(window.XMLHttpRequest&&XMLHttpRequest.prototype&&XMLHttpRequest.prototype.addEventListener&&!/CriOS/.test(navigator.userAgent)){t("loader").features.xhr=!0;var o=t("handle"),i=t(1),a=t("ee"),s=["load","error","abort","timeout"],c=s.length,f=t(2);t(4),t(3),a.on("new-xhr",function(){this.totalCbs=0,this.called=0,this.cbTime=0,this.end=n,this.ended=!1,this.xhrGuids={}}),a.on("open-xhr-start",function(t){this.params={method:t[0]},r(this,t[1]),this.metrics={}}),a.on("open-xhr-end",function(t,e){"loader_config"in NREUM&&"xpid"in NREUM.loader_config&&this.sameOrigin&&e.setRequestHeader("X-NewRelic-ID",NREUM.loader_config.xpid)}),a.on("send-xhr-start",function(t,n){var r=this.metrics,o=t[0],i=this;if(r&&o){var f=e(o);f&&(r.txSize=f)}this.startTime=(new Date).getTime(),this.listener=function(t){try{"abort"===t.type&&(i.params.aborted=!0),("load"!==t.type||i.called===i.totalCbs&&(i.onloadCalled||"function"!=typeof n.onload))&&i.end(n)}catch(e){try{a.emit("internal-error",e)}catch(r){}}};for(var u=0;c>u;u++)n.addEventListener(s[u],this.listener,!1)}),a.on("xhr-cb-time",function(t,e,n){this.cbTime+=t,e?this.onloadCalled=!0:this.called+=1,this.called!==this.totalCbs||!this.onloadCalled&&"function"==typeof n.onload||this.end(n)}),a.on("xhr-load-added",function(t,e){var n=""+f(t)+!!e;this.xhrGuids&&!this.xhrGuids[n]&&(this.xhrGuids[n]=!0,this.totalCbs+=1)}),a.on("xhr-load-removed",function(t,e){var n=""+f(t)+!!e;this.xhrGuids&&this.xhrGuids[n]&&(delete this.xhrGuids[n],this.totalCbs-=1)}),a.on("addEventListener-end",function(t,e){e instanceof XMLHttpRequest&&"load"===t[0]&&a.emit("xhr-load-added",[t[1],t[2]],e)}),a.on("removeEventListener-end",function(t,e){e instanceof XMLHttpRequest&&"load"===t[0]&&a.emit("xhr-load-removed",[t[1],t[2]],e)}),a.on("fn-start",function(t,e,n){e instanceof XMLHttpRequest&&("onload"===n&&(this.onload=!0),("load"===(t[0]&&t[0].type)||this.onload)&&(this.xhrCbStart=(new Date).getTime()))}),a.on("fn-end",function(t,e){this.xhrCbStart&&a.emit("xhr-cb-time",[(new Date).getTime()-this.xhrCbStart,this.onload,e],e)})}},{1:9,2:12,3:7,4:4,ee:"QJf3ax",handle:"D5DuLP",loader:"G9z0Bl"}],9:[function(t,e){e.exports=function(t){var e=document.createElement("a"),n=window.location,r={};e.href=t,r.port=e.port;var o=e.href.split("://");return!r.port&&o[1]&&(r.port=o[1].split("/")[0].split(":")[1]),r.port&&"0"!==r.port||(r.port="https"===o[0]?"443":"80"),r.hostname=e.hostname||n.hostname,r.pathname=e.pathname,"/"!==r.pathname.charAt(0)&&(r.pathname="/"+r.pathname),r.sameOrigin=!e.hostname||e.hostname===document.domain&&e.port===n.port&&e.protocol===n.protocol,r}},{}],D5DuLP:[function(t,e){function n(t,e,n){return r.listeners(t).length?r.emit(t,e,n):(o[t]||(o[t]=[]),void o[t].push(e))}var r=t("ee").create(),o={};e.exports=n,n.ee=r,r.q=o},{ee:"QJf3ax"}],handle:[function(t,e){e.exports=t("D5DuLP")},{}],12:[function(t,e){function n(t){if(!t||"object"!=typeof t&&"function"!=typeof t)return-1;if(t===window)return 0;if(o.call(t,"__nr"))return t.__nr;try{return Object.defineProperty(t,"__nr",{value:r,writable:!0,enumerable:!1}),r}catch(e){return t.__nr=r,r}finally{r+=1}}var r=1,o=Object.prototype.hasOwnProperty;e.exports=n},{}],loader:[function(t,e){e.exports=t("G9z0Bl")},{}],G9z0Bl:[function(t,e){function n(){var t=p.info=NREUM.info;if(t&&t.agent&&t.licenseKey&&t.applicationID&&c&&c.body){p.proto="https"===d.split(":")[0]||t.sslForHttp?"https://":"http://",a("mark",["onload",i()]);var e=c.createElement("script");e.src=p.proto+t.agent,c.body.appendChild(e)}}function r(){"complete"===c.readyState&&o()}function o(){a("mark",["domContent",i()])}function i(){return(new Date).getTime()}var a=t("handle"),s=window,c=s.document,f="addEventListener",u="attachEvent",d=(""+location).split("?")[0],p=e.exports={offset:i(),origin:d,features:{}};c[f]?(c[f]("DOMContentLoaded",o,!1),s[f]("load",n,!1)):(c[u]("onreadystatechange",r),s[u]("onload",n)),a("mark",["firstbyte",i()])},{handle:"D5DuLP"}],15:[function(t,e){function n(t,e,n){e||(e=0),"undefined"==typeof n&&(n=t?t.length:0);for(var r=-1,o=n-e||0,i=Array(0>o?0:o);++r<o;)i[r]=t[e+r];return i}e.exports=n},{}],16:[function(t,e){function n(t){return!(t&&"function"==typeof t&&t.apply&&!t[i])}var r=t("ee"),o=t(1),i="nr@wrapper";e.exports=function(t){function e(t,e,r,a){function nrWrapper(){var n,i,f,u;try{i=this,n=o(arguments),f=r&&r(n,i)||{}}catch(d){c([d,"",[n,i,a],f])}s(e+"start",[n,i,a],f);try{return u=t.apply(i,n)}catch(p){throw s(e+"err",[n,i,p],f),p}finally{s(e+"end",[n,i,u],f)}}return n(t)?t:(e||(e=""),nrWrapper[i]=!0,nrWrapper)}function a(t,r,o,i){o||(o="");var a,s,c,f="-"===o.charAt(0);for(c=0;c<r.length;c++)s=r[c],a=t[s],n(a)||(t[s]=e(a,f?s+o:o,i,s,t))}function s(e,n,r){try{t.emit(e,n,r)}catch(o){c([o,e,n,r])}}function c(e){try{t.emit("internal-error",e)}catch(n){}}return t||(t=r),e.inPlace=a,e.flag=i,e}},{1:15,ee:"QJf3ax"}]},{},["G9z0Bl",3,8]);</script>
<script	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script	src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<script src="${base}/patric/js/libs/modernizr-1.6.min.js"></script>
<script src="${base}/patric/js/plugins.min.js"></script>
<script src="${base}/patric/js/script.js"></script>
<script src="//cdn.sencha.com/ext/gpl/4.2.1/ext-all.js"></script>
<script src="${base}/patric/js/patric.min.js"></script>
<script src="${base}/portal-core/layouts/patric/modal.min.js"></script>
</head>
<body class="normal ${bodyClass}">
	<div id="login-modal" style="display: none">
		<div id="login-modal-msg" style="display: none; width: 255px; height: 109px">
			<div id="loginIframe" class="login-content"></div>
		</div>
	</div>

	<div class="main-header no-underline-links">
		<div class="masthead">
			<div class="wrapper">
				<a class="logo" title="Translational Integrated Knowledge Environment" href="/">TIKE Home</a>
				<ul class="main-nav no-decoration">
					<li class="first active"><a class="home active" href="/portal/portal/patric/Home">Home</a>
						<div class="submenu">
							<ul>
								<li><a href="/portal/portal/patric/Home">TIKE Home</a></li>
								<li><a href="/portal/portal/patric/TB">TB @ TIKE</a></li>
							</ul>
						</div></li>
					<li class="has-sub"><a href="#">Diseases</a>
						<div class="submenu two-col">
							<header>
								<p class="bold light">Diseases</p>
							</header>
							<ul class="left first">
								<li><a href="/portal/portal/patric/Taxon?cType=taxon&amp;cId=1386">Breast Cancer</a></li>
							</ul>
							<ul class="left"></ul>
							<header class="clear">
								<p class="bold light">Complete Lists of Bacteria:</p>
							</header>
							<ul>
								<li><a href="/portal/portal/patric/Taxon?cType=taxon&amp;cId=2">All Bacteria</a></li>
							</ul>
						</div></li>
					<li class="has-sub"><a href="#">Data</a>
						<div class="submenu">
							<ul>
								<li><a href="AntibioticResistance">Genomic Feature</a></li>
							</ul>
							<header>
								<p class="bold light">Special Collections</p>
							</header>
							<ul>
								<li><a href="//enews.patricbrc.org/patric-collaborations/">GEO</a></li>
							</ul>
							<header>
								<p class="bold light">Download Data</p>
							</header>
							<ul>
								<li><a href="ftp://ftp.patricbrc.org/patric2"
									target="_blank">FTP Server</a></li>
								<li><a href="Downloads?cType=taxon&amp;cId=">Download
										Tool</a></li>
							</ul>
						</div></li>
					<li class="has-sub"><a href="#">Tools</a>
						<div class="submenu">
							<ul>
								<li><a href="/portal/portal/patric/Tools">Complete List
										of All Tools</a></li>
							</ul>
							<header>
								<p class="bold light">Specialized Searches:</p>
							</header>
							<ul>
								<li><a href="/portal/portal/patric/AntibioticResistanceGeneSearch?cType=taxon&amp;cId=&amp;dm=">Geo</a></li>
							</ul>
							<header>
								<p class="bold light">Comparative Analyses:</p>
							</header>
							<ul></ul>
							<header>
								<p class="bold light">Annotation Pipelines:</p>
							</header>
							<ul></ul>
							<header>
								<p class="bold light">Visual Browsers:</p>
							</header>
							<ul></ul>
						</div></li>
					<li class="has-sub"><a href="#">About</a>
						<div class="submenu">
							<header>
								<p class="bold light">Help</p>
							</header>
							<ul>
								<li><a href="Tutorials">Videos and Workflows</a></li>
								<li><a href="//enews.patricbrc.org/faqs/">FAQs</a></li>
								<li><a href="//enews.patricbrc.org/contact-us/">Contact
										Us</a></li>
							</ul>
							<header>
								<p class="bold light">eNews</p>
							</header>
							<ul>
								<li><a href="//enews.patricbrc.org/patric-publications/">Publications</a></li>
								<li><a
									href="//enews.patricbrc.org/category/patric-in-the-news/">News
										&amp; Events</a></li>
								<li><a href="//enews.patricbrc.org/category/data-release/">Data
										Releases &amp; Updates</a></li>
								<li><a href="//enews.patricbrc.org/category/webupdate/">Website
										Updates</a></li>
								<li><a href="//enews.patricbrc.org/category/presentations/">Presentations</a></li>
							</ul>
							<header>
								<p class="bold light">About Us</p>
							</header>
							<ul>
								<li><a href="//enews.patricbrc.org/what-is-patric/">What
										is TIKE?</a></li>
								<li><a href="//enews.patricbrc.org/citing-patric/">Citing
										TIKE</a></li>
								<li><a
									href="//enews.patricbrc.org/scientific-working-group/">Scientific
										Working Group</a></li>
								<li><a href="//enews.patricbrc.org/personnel/">Personnel</a></li>
								<li><a href="//enews.patricbrc.org/collaborators/">Collaborators</a></li>
								<li><a href="//enews.patricbrc.org/related-sites/">Related
										Sites</a></li>
								<li><a href="https://github.com/cidvbi/PATRIC">System
										Architecture</a></li>
							</ul>
						</div></li>
				</ul>
				<div class="login">
					<div id='dashboardnav'>
						<script type="text/javascript">
						//<![CDATA[
						function doLogin() {
							alertModal('login-modal','login-modal-msg', 'http://patricbrc.org/portal/auth/portal/patric/Home');
							return false;
						}
						//]]>
						</script>
						<span class='logged-in smallest'> <a class="button"
							href="javascript:void(0)" onclick="doLogin()">Login</a> Not
							Registered? <a class="arrow-slate-e"
							href="/portal/portal/patric/MyAccount/PATRICUserPortletWindow?_jsfBridgeViewId=%2Fjsf%2Findex.xhtml&amp;action=1">Sign
								Up</a> <br />
						<a
							href="http://enews.patricbrc.org/workspace-faqs/registration-faqs/"
							target="_blank">Learn About Registering</a>
						</span>

					</div>
				</div>
			</div>
		</div>
		<div class="toolbar ondark linkstyle-highlight-c">
			<div class="container">
				<form class="search" action="post" onsubmit="return false;">

					<input placeholder="Search" title="Search" type="text"
						id="global_search_keyword" name="global_search_keyword" /> <input
						type="submit" onclick="search_all_header()" />
				</form>
				<div class="workspace smallest">
					<span class="upper">My workspace:</span> <a class="arrow-white-e"
						href="#"><span id="cart"></span></a>
				</div>
			</div>
		</div>

	</div>
	<!--[if lt IE 9]>
			<div class="msg-box-below-topnav">
				<span>It appears you are using Microsoft Internet Explorer Version 8 or lower, which is incompatible with most of TIKE"s functionality. We recommend using Chrome, Firefox, Safari, or Internet Explorer 9 (or higher) for better performance.</span>
			</div>
		<![endif]-->
	<!--[if gte IE 9]>
			<script type="text/javascript">
				if (document.documentMode < 9) {
					document.write("<div class=\"msg-box-below-topnav\"><span>It appears you are using Microsoft Internet Explorer Version 9 or higher, but \"Document Mode\" is set below the IE9 standard.  To correct, go to Developer Tools (F12) and select \"Internet Explorer 9 standards (Page default)\". We recommend using Chrome, Firefox, or Safari for better performance.</span></div>");
				}
			</script>
		<![endif]-->

    <jsp:include page="${view}.jsp" />   

	<footer
		class="ondark linkstyle-highlight-c smallest no-underline-links main-footer">
		<div class="container">
			<div class="column" style="border: 0px">
				<div class="wrapper">
					<h4 class="close2x normal-weight">TIKE</h4>
					<p class="close">Virginia Bioinformatics Institute</p>
					<p class="close">1015 Life Science Circle</p>
					<p>Blacksburg, VA 24061</p>
					<a class="button" href="//enews.patricbrc.org/contact-us/">Contact
						Us</a>
				</div>
			</div>
			<div class="column">
				<div class="wrapper">
					<h4 class="normal-weight close2x">How to Cite TIKE</h4>
					<p>If you use TIKE web resources to assist in research
						publications or proposals please cite as:</p>
					<a class="button" href="//enews.patricbrc.org/citing-patric/">How
						to cite TIKE</a>
				</div>
			</div>
			<div class="column">
				<div class="wrapper">
					<h4 class="normal-weight close2x">Funding Statement</h4>
					<p>This project has been funded in whole or in part with
						Federal funds from the National Institute of Allergy and
						Infectious Diseases, National Institutes of Health, Department of
						Health and Human Services, under Contract No. HHSN272200900040C,
						awarded to BWS Sobral</p>
				</div>
			</div>
			<div class="column last bold">
				<div class="wrapper">
					<h4 class="normal-weight close">Follow Us</h4>
					<ul class="left no-decoration">
						<li><a class="footer-icons sprite-icon-subscribe" href="//enews.patricbrc.org/subscribe/">Subscribe</a></li>
						<li><a class="footer-icons sprite-icon-twitter" href="//twitter.com/PATRICBRC">Twitter</a></li>
						<li><a class="footer-icons sprite-icon-facebook" href="//www.facebook.com/pages/Pathosystems-Resource-Integration-Center-PATRIC/117100971687823">Facebook</a></li>
					</ul>
					<ul class="left no-decoration">
						<li><a class="footer-icons sprite-icon-enews" href="//enews.patricbrc.org/">eNews</a></li>
						<li><a class="footer-icons sprite-icon-rss"	href="//enews.patricbrc.org/feed/">RSS</a></li>
						<li><a class="footer-icons sprite-icon-youtube" href="//www.youtube.com/user/PATRICBRC">YouTube</a></li>
						<li><a class="footer-icons sprite-icon-share" href="//www.addthis.com/bookmark.php?v=250&amp;username=xa-4c40660c515875dc">Share This</a></li>
					</ul>
				</div>
			</div>
		</div>
	</footer>

	<!-- <script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-11736060-1', 'auto');
  ga('send', 'pageview');

</script> -->

	<script type="text/javascript" src="/enews/php/homepage.js"></script>
	<!-- <script type="text/javascript">window.NREUM||(NREUM={});NREUM.info={"applicationID":"1853906","applicationTime":19,"beacon":"beacon-1.newrelic.com","queueTime":0,"licenseKey":"37a71e0015","transactionName":"NFEAZkJXCEpRURELCg0bKGFgGTF8ch8sLCNMXhFCH14JVFUdDAwBBkxMWENG","agent":"js-agent.newrelic.com\/nr-460.min.js","errorBeacon":"bam.nr-data.net"}</script> -->
</body>
</html>
