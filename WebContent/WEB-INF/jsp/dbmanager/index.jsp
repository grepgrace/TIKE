<%@ page language="java" contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${base}/js/jqueryplugins/dynatable/jquery.dynatable.css">
<div class="row row-offcanvas row-offcanvas-right">

	<div class="col-xs-12 col-sm-9">
		<table id="my-final-table" class="table table-bordered table-hover table-striped">
			<thead>
				<tr>
<c:forEach var="item" items="${fields}" varStatus="loop"><th class="td${loop.index} ${item.name}">${item.name}</th></c:forEach>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
		<div id="demo"></div>
	</div>

</div>

<script src="${base}/js/jqueryplugins/dynatable/jquery.dynatable.js"></script>
<script >
$('#my-final-table').dynatable({
	dataset: {
	    ajax: true,
	    ajaxUrl: '${base}/JsonAPI/Get${name}',
	    ajaxOnLoad: true,
	    records: []
	  }
});
</script>
