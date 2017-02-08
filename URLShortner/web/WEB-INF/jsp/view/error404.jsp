
<%
	@SuppressWarnings("unchecked")
	String errorMessage = (String) request.getAttribute("errorMessage");
%>

<%@ page errorPage="error404.jsp"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>URL Shortner</title>

<!-- Bootstrap -->
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- Application Specific CSS -->
<link href="resources/stylesheets/urlShortner.css" rel="stylesheet"
	type="text/css">

</head>
<body>
	<div class="container">

		<div class="jumbotron" id="uSh_jumbotron">
			<div class="row">
				<div class="col-xs-3"></div>
				<div class="col-xs-6">
					<h3><%=errorMessage%></h3>
				</div>
				<div class="col-xs-3"></div>
			</div>
			<div class="row">
				<div class="col-xs-3"></div>
				<div class="col-xs-6">
					<h3>
						Use our <a href="http://localhost:8080/URLShortner/home">Home
							Page</a> instead !
					</h3>
				</div>
				<div class="col-xs-3"></div>
			</div>
		</div>
	</div>


	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="resources/bootstrap/js/bootstrap.min.js"></script>
	<script src="resources/js/index.js"></script>
</body>
</html>