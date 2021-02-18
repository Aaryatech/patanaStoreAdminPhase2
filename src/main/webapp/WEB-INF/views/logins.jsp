<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Dashboard - Admin</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

<!--base css styles-->
<link rel="stylesheet"
	href="resources/assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="resources/assets/font-awesome/css/font-awesome.min.css">

<!--page specific css styles-->

<!--flaty css styles-->
<link rel="stylesheet" href="resources/css/flaty.css">
<link rel="stylesheet" href="resources/css/flaty-responsive.css">

<link rel="shortcut icon" href="resources/img/favicon.png">

</head>
<body class="login-page">

	<!-- BEGIN Main Content -->
	<div class="login-wrapper">
		<!-- BEGIN Login Form -->
		<form id="form-login" action="loginProcess" method="post">
			<h3>Login to your account</h3>
		
		


			<hr />
			<div class="form-group">
				<div class="controls">
					<input type="text" placeholder="Username" class="	form-control"
						path="username" name="username" id="username" />

				</div>
			</div>
			<div class="form-group">
				<div class="controls">
					<input type="password" placeholder="Password" class="form-control"
						path="userpassword" name="password" id="userpassword" />
				</div>
			</div>
			<div class="form-group">
				<div class="controls">
					<label class="checkbox"> <input type="checkbox"
						value="remember" /> Remember me
					</label>
				</div>
			</div>
			<div class="form-group">
				<div class="controls">
					<button type="submit" class="btn btn-primary form-control">Sign
						In</button>
				</div>
				
				<c:if test="${not empty loginResponseMessage}">
   <!-- here would be a message with a result of processing -->
    <div> ${loginResponseMessage} </div>
        	
</c:if>
				
				
			</div>
			<hr />
			<p class="clearfix">
				<a href="#" class="goto-forgot pull-left">Forgot Password?</a> <a
					href="#" class="goto-register pull-right">Sign up now</a>
			</p>
		</form>
		<!-- END Login Form -->

		<!-- BEGIN Forgot Password Form -->
		<form id="form-forgot" action="index.html" method="get"
			style="display: none">
			<h3>Get back your password</h3>
			<hr />
			<div class="form-group">
				<div class="controls">
					<input type="text" placeholder="Email" class="form-control" />
				</div>
			</div>
			<div class="form-group">
				<div class="controls">
					<button type="submit" class="btn btn-primary form-control">Recover</button>
				</div>
			</div>
			<hr />
			<p class="clearfix">
				<a href="#" class="goto-login pull-left">Back to login form</a>
			</p>
		</form>
		<!-- END Forgot Password Form -->

		<!-- BEGIN Register Form -->
		<form id="form-register" action="index.html" method="get"
			style="display: none">
			<h3>Sign up</h3>
			<hr />
			<div class="form-group">
				<div class="controls">
					<input type="text" placeholder="Email" class="form-control" />
				</div>
			</div>
			<div class="form-group">
				<div class="controls">
					<input type="text" placeholder="Username" class="form-control" />
				</div>
			</div>
			<div class="form-group">
				<div class="controls">
					<input type="password" placeholder="Password" class="form-control" />
				</div>
			</div>
			<div class="form-group">
				<div class="controls">
					<input type="password" placeholder="Repeat Password"
						class="form-control" />
				</div>
			</div>
			<div class="form-group">
				<div class="controls">
					<label class="checkbox"> <input type="checkbox"
						value="remember" /> I accept the <a href="#">user aggrement</a>
					</label>
				</div>
			</div>
			<div class="form-group">
				<div class="controls">
					<button type="submit" class="btn btn-primary form-control">Sign
						up</button>
				</div>
			</div>
			<hr />
			<p class="clearfix">
				<a href="#" class="goto-login pull-left"> Back to login form</a>
			</p>
		</form>
		<!-- END Register Form -->
	</div>
	<!-- END Main Content -->

	<!--basic scripts-->
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
	<script>window.jQuery || document.write('<script src="resources/assets/jquery/jquery-2.0.3.min.js"><\/script>')</script>
	<script src="resources/assets/bootstrap/js/bootstrap.min.js"></script>

	<script type="text/javascript">
            function goToForm(form)
            {
                $('.login-wrapper > form:visible').fadeOut(500, function(){
                    $('#form-' + form).fadeIn(500);
                });
            }
            $(function() {
                $('.goto-login').click(function(){
                    goToForm('login');
                });
                $('.goto-forgot').click(function(){
                    goToForm('forgot');
                });
                $('.goto-register').click(function(){
                    goToForm('register');
                });
            });
        </script>
</body>
</html>
