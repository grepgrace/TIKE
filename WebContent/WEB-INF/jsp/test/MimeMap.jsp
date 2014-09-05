<%@ page language="java" contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${base}/js/jqueryplugins/dynatable/jquery.dynatable.css">
<div class="row row-offcanvas row-offcanvas-right">

	<div class="col-xs-12 col-sm-9">
	<form action="" method="post">
		<textarea maxlength="10000" cols="80" rows="12" name="InputText" style=""></textarea><br />
		<input type="submit" value="Submit">
		<div id="demo">${result}</div>
	</form>
	</div>

</div>

<script src="${base}/js/jqueryplugins/dynatable/jquery.dynatable.js"></script>
<script >
$('#my-final-table').dynatable({
	dataset: {
	    ajax: true,
	    ajaxUrl: '${base}/DBManager/GetJson?model=${name}',
	    ajaxOnLoad: true,
	    records: []
	  }
});
</script>
