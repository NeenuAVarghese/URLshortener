
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 

  
<%
	String longUrl = (String) session.getAttribute("longUrl");
	session.removeAttribute("longUrl");
%>

<%
	String loginFailed = (String) session.getAttribute("loginFailed");
	session.removeAttribute("loginFailed");
%>

<%
	String signupFailed = (String) session.getAttribute("signupFailed");
	session.removeAttribute("signupFailed");
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
					<li><button data-toggle="modal" data-target=".login-modal"
							type="button" class="btn btn-md btn-info" id="uSh_login">
							<span class="glyphicon glyphicon-log-in"> Login</span>
						</button></li>
					<li><button data-target=".signup-modal" id="uSh_Signup"
							data-toggle="modal" type="button" class="btn btn-md btn-warning">
							<span class="glyphicon glyphicon-hand-up" action=""> SignUp</span>
						</button></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>


	<div aria-labelledby="myModalLabel" class="modal fade login-modal"
		id="uSh_loginCard" role="dialog" tabindex="-1">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-label="Close" class="close" data-dismiss="modal"
						type="button">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Login</h4>
				</div>
				<div class="modal-body">

				<form:form method="post" modelAttribute="loginForm" action="login">
						<div class="form-group">
							<form:label path="username" class="control-label" for="uSh_LoginCard-loginId">Username</form:label>
							<form:input path="username" class="form-control" id="uSh_LoginCard-loginId" type="text" />
						</div>
						<div class="form-group">
							<form:label path="password" class="control-label" for="uSh_LoginCard-password" >Password</form:label>
							<form:password path="password" class="form-control"
								id="uSh_LoginCard-password"/>
						</div>
						<button type="submit" class="btn btn-primary uSh_LoginAction"
							type="button">Login !</button>
					</form:form>

				</div>
				<div class="modal-footer">
					<div>
						<p id="getLoginStatus" hidden><%=loginFailed%></p>
						<div hidden class="alert alert-warning" role="alert"
							id="pt_loginCard-errorMessage">Invalid Credentials</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div aria-labelledby="myModalLabel" class="modal fade signup-modal"
		id="uSh_signupCard" role="dialog" tabindex="-1">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-label="Close" class="close" data-dismiss="modal"
						type="button">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">I want to join</h4>
				</div>
				<div class="modal-body">
				
					<form:form method="post" modelAttribute="signUpForm" action="signUp">
						<div class="form-group">
							<form:label path="new_username" class="control-label" for="uSh_signupCard-loginId">Username</form:label>
							<form:input path="new_username" class="form-control" id="uSh_signupCard-loginId" type="text" />
						</div>
						<div class="form-group">
							<form:label path="new_password" class="control-label" for="uSh_signupCard-password" >Password</form:label>
							<form:password path="new_password" class="form-control"
								id="uSh_signupCard-password"/>
						</div>
						<button type="submit" class="btn btn-primary uSh_signupAction"
							type="button">Sign Me Up !</button>
					</form:form>
				</div>
				<div class="modal-footer">
					<div>
						<p id="getSignUpStatus" hidden><%=signupFailed%></p>
						<div hidden class="alert alert-warning" role="alert"
							id="uSh_signupCard-errorMessage">User name Already exists !</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<div aria-labelledby="myModalLabel"
		class="modal fade showLongUrl-modal" id="uSh_showlongUrlModal"
		role="dialog" tabindex="-1">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-label="Close" class="close" data-dismiss="modal"
						type="button" id="uSh_showlongUrlModalClose">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Original URL</h4>
				</div>
				<div class="modal-body">
					<div id="getLongUrlSuccess">
						<div class="alert alert-info" role="alert">
							<strong>Heads Up!We have found a match!</strong>
						</div>
						<div class="well well-sm">
							<a href="" id="uSh_setlongVal"></a>
						</div>
					</div>
					<div id="getLongUrlFail">
						<div class="alert alert-danger" role="alert">
							<strong>Oh snap!</strong>Change a few things up and try
							submitting again
						</div>
					</div>
					<div class="modal-footer">
						<div>
							<p id="uSh_signupCard-errorMessage"></p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="jumbotron" id="uSh_jumbotron">
			<div class="row">
				<div class="col-xs-4">
					<h3>Fetch Original URL</h3>
				</div>
				<div class="col-xs-4"></div>
				<div class="col-xs-4"></div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<form:form method="get" action="getLongUrl" modelAttribute="shortToLong">
						<div class="input-group">
							<form:input path="shortUrl" type="text" class="form-control" id="uSh_getShortUrl"
								placeholder="Your Short Url Here"></form:input>
								
							<div class="input-group-btn">
								<button type="submit" id="us_convertShortToLong" type="button"
									class="btn btn-md btn-warning">Get Long URL</button>
							</div>
						</div>
					</form:form>
					<p id="uSh_longUrlVal" hidden><%=longUrl%></p>
				</div>
				<div class="col-xs-3"></div>
			</div>
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
				<p>Analytics help you kn.ow where your clicks are coming from</p>

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

