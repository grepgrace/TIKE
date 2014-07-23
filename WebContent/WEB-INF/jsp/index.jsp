<%@ page language="java" contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="${base}/js/jqueryplugins/jstree/themes/default/style.min.css" />
<script src="${base}/js/jqueryplugins/jstree/jstree.min.js"></script>
<script>
$(function () { $('#html1').jstree(); });
$('#jstree_demo_div').on("changed.jstree", function (e, data) {
  console.log(data.selected);
});
$('#jstree_demo_div').jstree({ 'core' : {
    'data' : [
       'Simple root node',
       {
         'text' : 'Root node 2',
         'state' : {
           'opened' : true,
           'selected' : true
         },
         'children' : [
           { 'text' : 'Child 1' },
           'Child 2'
         ]
      }
    ]
} });
</script>
<div class="row row-offcanvas row-offcanvas-right">
	<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" >
		<ul class="list-group">
			<li class="list-group-item <c:if test="${step==1}">active</c:if> <c:if test="${step>1}">Disabled</c:if>">
				<h4 class="list-group-item-heading">Step 1</h4>
				<p class="list-group-item-text">Select DataBase</p>
			</li>
			<li class="list-group-item <c:if test="${step==2}">active</c:if> <c:if test="${step>2}">Disabled</c:if>">
				<h4 class="list-group-item-heading">Step 2</h4>
				<p class="list-group-item-text">Select DataBase</p>
			</li>
			<li class="list-group-item <c:if test="${step==3}">active</c:if> <c:if test="${step>3}">Disabled</c:if>">
				<h4 class="list-group-item-heading">Step 3</h4>
				<p class="list-group-item-text">Select DataBase</p>
			</li>
			<li class="list-group-item <c:if test="${step==4}">active</c:if> <c:if test="${step>4}">Disabled</c:if>">
				<h4 class="list-group-item-heading">Step 4</h4>
				<p class="list-group-item-text">Select DataBase</p>
			</li>
		</ul>
		
		<div id="jstree_demo_div"></div>
		<div id="html1">
  <ul>
    <li>Root node 1
      <ul>
        <li>Child node 1</li>
        <li><a href="#">Child node 2</a></li>
      </ul>
    </li>
  </ul>
</div>

	</div>
	<div class="col-xs-12 col-sm-9">
		<form class="form-horizontal" method="post">
			<input type="hidden" value="${step}" name="step">
			<fieldset>
				<legend>Step ${step}:Select DataBase </legend>
				<div class="form-group">
					<label class="col-sm-2 control-label">Email</label>
					<div class="col-sm-10">
						<p class="form-control-static">email@example.com</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="input01">inputtext</label>
					<div class="col-sm-10">
						<input type="text" class="input-xlarge form-control " id="input01" name="name" value="${name}">
						<p class="help-block">help text</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="optionsCheckbox">checkbox</label>
					<div class="col-sm-10">
						<label class="checkbox"> 
						
						<input type="checkbox" id="optionsCheckbox" value="option1" name="checkbox" <c:if test="${not empty checkbox}">checked="checked"</c:if>>
							selected me
						</label>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="select01">select</label>
					<div class="col-sm-10">
						<select id="select01" class="form-control ">
							<option>select ..</option>
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="multiSelect">mutil select</label>
					<div class="col-sm-10">
						<select multiple="multiple" id="multiSelect" class="form-control ">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="fileInput">file upload</label>
					<div class="col-sm-10">
						<input class="input-file" id="fileInput" type="file">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="textarea">textarea</label>
					<div class="col-sm-10">
						<textarea class="input-xlarge form-control " id="textarea" rows="3"></textarea>
					</div>
				</div>
				<div class="form-group">
				    <div class="col-sm-offset-2 col-sm-10">
				      <button type="submit" name="submit" value="next" class="btn btn-primary"><c:if test="${step<4}">Next</c:if><c:if test="${step>=4}">Search</c:if></button>
				      <c:if test="${step!=1}"><button type="submit" name="submit" value="previous" class="btn btn-default">Previous</button></c:if>
				      <button type="reset" class="btn btn-default" value="Cancel">Reset</button>
				    </div>
				  </div>
			</fieldset>
		</form>
	</div>

</div>
