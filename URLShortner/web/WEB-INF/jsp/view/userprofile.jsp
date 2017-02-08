<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="model.dto.UserUrl"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 

<%
	@SuppressWarnings("unchecked")
	List<UserUrl> urlMapping = (List<UserUrl>) request.getAttribute("links");
%>
<%
	@SuppressWarnings("unchecked")
	// map contains shorturl, visitcount
	Map<String, Integer> urlCount = (Map<String, Integer>) request.getAttribute("linksCount");
%>


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
	<!--  Navbar -->
	<nav class="navbar navbar-inverse navbar-fixed-top" id="uSh_navbar">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#" id="uSh_brand">URL Shortner</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">

				<ul class="nav navbar-nav navbar-right">
					<li id="uSh_logout"><button method="POST"
							onclick="location.href='<c:url value="/logout"></c:url>'"
							type="button" class="btn btn-md btn-info" id="uSh_login">
							<span class="glyphicon glyphicon-log-out"> Logout</span>
						</button></li>
					<li><button type="button" class="btn btn-md btn-warning">
							<span class="glyphicon glyphicon-user"> ${username}</span>
						</button></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>


	<div aria-labelledby="myModalLabel" class="modal fade" id="copyModal"
		role="dialog" tabindex="-1">
		<div class="modal-dialog" role="document">
			<div class="modal-body">
				<div id="getLongUrlFail">
					<div class="alert alert-info" role="alert">
						<strong>URL Copied to your clipboard !
					</div>
				</div>
			</div>
		</div>
	</div>


	<div class="container">
		<h1>Welcome ${username}</h1>
		<div class="jumbotron" id="uSh_LongtoShjumbotron">
			<div class="row">
				<div class="col-xs-4">
					<h3>Get Short URL</h3>
				</div>
				<div class="col-xs-4"></div>
				<div class="col-xs-4"></div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<form:form method="post" modelAttribute="longToShortForm">
						<div class="input-group">
							<form:input path="longUrl" type="text" class="form-control"
								placeholder="Your Long Url Here" aria-describedby="basic-addon2"
								id="uSh_getLongUrl"></form:input>
							<div class="input-group-btn">
								<button type="submit" id="us_convertlongToShort" type="button"
									class="btn btn-md btn-warning">Get Short URL</button>
							</div>
						</div>
					</form:form>			
				</div>
				<div class="col-xs-3"></div>
			</div>
		</div>
		
		<div class="row">
			<ul class="list-group">
				<%
					if (urlMapping == null) {
				%>
				<h4> Nothing to show</h4>
				<%
					}
				%>
				<%
					if (urlMapping != null) {
				%>
				<h4> Something to show</h4>
				<%
					Iterator it = urlMapping.iterator();
				%>
				<%
					while (it.hasNext()) {
				%>
				<%
					UserUrl entry =  (UserUrl)it.next();
				%>
				<%
					String shortUrl = (String) entry.getShortUrl();
				%>
				<%
					String longUrl = (String) entry.getLongUrl();
				%>
				<li class="list-group-item">
					<h4>
						Main URL:
						<h5>
							<a class="main-url" href="<%=longUrl%>"><%=longUrl%></a>
						</h5>
					</h4>
					<h4>Shortened Url:</h4> 
					<a href="<%=shortUrl%>"><%=shortUrl%></a>
					
					 <span class="badge glyphicon glyphicon-stats">&nbsp;<%=urlCount.get(shortUrl)%></span>
					<div class="btn-toolbar" role="toolbar">
						<button role="group" type="button"
							class="btn btn-sm btn-success uSh_copyUrl"
							onclick="copyToClipboard('<%=shortUrl%>')">
							<span class="glyphicon glyphicon-copy">&nbsp;Copy</span>
						</button>

						<button role="group" type="button"
							class="btn btn-sm btn-danger uSh_Deleteurl"
							onclick="urldelete('<%=shortUrl%>')">
							<span class="glyphicon glyphicon-trash">&nbsp;Delete</span>
						</button>
					</div>

				</li>
				<%
					}
				%>
				<%
					}
				%>

			</ul>
		</div>
		<div class="row">
			<div class="col-sm-4">

				<h3 class="shorten">
					<span class="glyphicon glyphicon-link">&nbsp;Shorten</span>
				</h3>
				<p>Shorten long URLs so it’s ready to be shared everywhere.</p>

			</div>

			<div class="col-sm-4">

				<h3 class="share">
					<span class="glyphicon glyphicon-share">&nbsp;Share</span>
				</h3>
				<p>Share links across all your marketing channels.</p>

			</div>


			<div class="col-sm-4">

				<h3 class="analyze">
					<span class="glyphicon glyphicon-dashboard">&nbsp;Analyze</span>
				</h3>
				<p>Analytics help you know where your clicks are coming from</p>

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
