<%@include file="Inclusion.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign Up page</title>
<style>
<%@include file="/WEB-INF/css/styleLogin.css"%>
</style>
</head>
<body style="background-image: url('images/bgd2.jpg');
 background-size: cover; background-repeat: no-repeat;">
 
		<div class="wrapper">
			<form method="post" action="">
				<h2>Sign Up</h2>
				<div class="input-group">
					<input type="text"  name="username"  value="<c:out value="${user.username}"/>" required> 
					<label for="username" >Username</label>
				</div>
									<h6>${form.erreurs['usernameErreur']}</h6>
									<h6>${form.erreurs['usernameexists']}</h6>
									
				
				<div class="input-group">
					<input type="email"  name="email"   value="<c:out value="${user.email}"/>" required> 
					<label for="email" >Email</label>
							<span class="erreur">${form.erreurs['emailFormat']}</span>
					
				</div>
				   <h6>${form.erreurs['emailexists']}</h6>
				
				<div class="input-group">
					<input type="password" name="pswd"   required> 
					<label for="pswd">Password</label>    
				</div>
				   <h6>${form.erreurs['errorPaswordLength']}</h6>
					<div class="input-group">
					<input type="password" name="pswd-repeat" required> 
					<label for="pswd-repeat">Repeat-password</label>
					
				</div>
			<h6>${form.erreurs['passwordDifferent']}</h6>
				
				<button type="submit">Sign up</button>
				<div class="signUp-link">
				<p>Already have an account?<a href="Login" class="signInBtn-link">Sign In</a></p>
				</div>
			</form>
		</div>

</body>
</html>