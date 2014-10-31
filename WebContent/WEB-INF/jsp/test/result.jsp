<%@ page language="java" contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<div class="row row-offcanvas row-offcanvas-right">
<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar">
	<form class="form-horizontal" method="post">
		<input type="hidden" value="${step}" name="step">
		<fieldset>
			<legend>Result</legend>
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
<div class="col-xs-12 col-sm-9">
	<table class="table table-hover">
        <thead>
         <tr>
            <th>#</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Username</th>
          </tr>
        </thead>
        <tbody>
	<c:forEach var="item" items="${results}" varStatus="loop">
          <tr>
            <td>${loop.index}</td>
            <td>${item}</td>
            <td>Otto</td>
            <td>@mdo</td>
          </tr>
	</c:forEach> 	
        </tbody>
      </table>
</div>
</div>