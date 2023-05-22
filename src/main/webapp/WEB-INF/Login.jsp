<%@include file="Inclusion.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>
<style>
<%@include file="/WEB-INF/css/styleLogin.css"%>
</style>
</head>
<body style="background-image: url('images/bgd2.jpg');
 background-size: cover; background-repeat: no-repeat;">
<c:if test="${ statut =='failed'  }">
	 <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
      <script type="text/javascript">
      Swal.fire({
		  icon: 'error',
		  title: 'Oops...',
		  text: 'Wrong username or password!',
		})
       </script>
      
    </c:if>
	<div class="wrapper">
		<div class="form-wrapper sign-in">
			<form action="Login" method="post">
				<h2>Login</h2>
				<div class="input-group">
					<input type="text" name="usernameLogin" id="usernameLogin" required> 
					<label for="">Username</label>
				</div>
				<div class="input-group">
					<input type="password" name="passwordLogin" id="passwordLogin" required> 
					<label for="">Password</label>
				</div>
				<div class="remember">
					<label for =""><input type="checkbox">Remember me</label>
				</div>
				<button type="submit" value="Login">Login</button>
				<div class="signUp-link">
				<p>Don't have an account?<a href="Signup" class="signUpBtn-link">Sign Up</a></p>
				</div>
			</form>
		</div>	
	</div>

</body>
</html>