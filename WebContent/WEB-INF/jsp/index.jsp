<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.css">
	<!-- <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css"> -->

    <!-- Custom styles for this template -->
    <link href="bootstrap/css/sticky-footer-navbar.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="assets/js/ie-emulation-modes-warning.js"></script>

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="assets/js/ie10-viewport-bug-workaround.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<body>
<div class="navbar navbar-fixed-top navbar-inverse" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Project name</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li>
          </ul>
        </div><!-- /.nav-collapse -->
      </div><!-- /.container -->
    </div><!-- /.navbar -->
    
	<div class="container">
		<div class="row row-offcanvas row-offcanvas-right">

			<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar" role="navigation">
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
			</div>
			<div class="col-xs-12 col-sm-9">
				<form class="form-horizontal" role="form" method="post">
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

		<!-- <div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
			<ul class="list-group">
				<li class="list-group-item active">
					<h4 class="list-group-item-heading">Step 1</h4>
              		<p class="list-group-item-text">Select DataBase</p></li>
				<li class="list-group-item">
					<h4 class="list-group-item-heading">Step 2</h4>
              		<p class="list-group-item-text">Select DataBase</p></li>
				<li class="list-group-item">
					<h4 class="list-group-item-heading">Step 3</h4>
              		<p class="list-group-item-text">Select DataBase</p></li>
				<li class="list-group-item">
					<h4 class="list-group-item-heading">Step 4</h4>
              		<p class="list-group-item-text">Select DataBase</p></li>
			</ul>			
			</div>
		  	<div class="col-sm-9 col-md-10 main">
		  		<form class="well form-search">
		        <label>Title:</label>
		        <input type="text" placeholder="please input title" class="span6">
		        <span class="help-inline">相关提示信息</span>
		        <p class="help-block">块状帮助文字...</p>
		        <label class="checkbox">
		          <input type="checkbox">Selected
		        </label>
		        <button class="btn btn-primary" type="submit">Submit</button>
		      </form>
			</div>
		</div>
	</div> -->
	
    </div>
    
	<div class="footer">
      <div class="container">
        <p class="text-muted">© Company 2014.</p>
      </div>
    </div>

    
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<!-- <script src="bootstrap/js/bootstrap.js"></script> -->
</body>
</html>
