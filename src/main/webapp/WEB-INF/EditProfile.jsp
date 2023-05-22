<%@ include file="Inclusion.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Account Settings</title>
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link rel="stylesheet" type="text/css"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>

<style>
<%@include file="/WEB-INF/css/styleEditprofile.css"%>

</style>
</head>
<body>
	<c:if test="${ !empty sessionScope.username  }">

	<section class="py-5 my-5">
		<div class="container">
			<h1 class="mb-5">Account Settings</h1>
			<div class="bg-white shadow rounded-lg d-block d-sm-flex">
				<div class="profile-tab-nav border-right">
					<div class="p-4">
						<div class="img-circle text-center mb-3">
							<img src="${image}" alt="Image not found" class="shadow">
							<form action="UploadImage" method="post" enctype="multipart/form-data">
								<input type="file" name="image" id="file" onchange="this.form.submit()" />
								<label class="file" for="file">
								<i class='bx bxs-image-add'></i>
									
								</label>
							</form>
						</div>
        						<h4 class="text-center"> ${ sessionScope.username } </h4>
					</div>
					<div class="nav flex-column nav-pills" id="v-pills-tab"
						role="tablist" aria-orientation="vertical">
						<a class="nav-link<%  if (!("changePassword".equals(session.getAttribute("activeTab")))&&(!("deleteProfile".equals(session.getAttribute("activeTab"))))) { %> active<% } %>" id="account-tab" data-toggle="pill"
							href="#account" role="tab" aria-controls="account"
							aria-selected="true"> <i class="fa fa-home text-center mr-1"></i>
							Account
						</a>
						 <a class="nav-link<% if (("changePassword".equals(session.getAttribute("activeTab")))) { %> active<% } %>" id="password-tab" data-toggle="pill"
							href="#password" role="tab" aria-controls="password"
							aria-selected="false"> <i class="fa fa-key text-center mr-1"></i>
							Password

						</a> 
						<a class="nav-link<% if (("deleteProfile".equals(session.getAttribute("activeTab")))) { %> active<% } %>" id="deleteP-tab" data-toggle="pill" href="#deleteP" role="tab" aria-controls="deleteP" aria-selected="false">
							<i class="fa fa-user text-center mr-1"></i> 
							Delete Account
						</a>

					</div>
				</div>
				<div class="tab-content p-4 p-md-5" id="v-pills-tabContent">
					<!-- <div class="tab-pane fade show active" id="account" role="tabpanel"
						aria-labelledby="account-tab"> -->
						<div class="tab-pane fade<% if (!("changePassword".equals(session.getAttribute("activeTab")))&&(!("deleteProfile".equals(session.getAttribute("activeTab"))))) { %> show active<% } %>" id="account" role="tabpanel">
						
							<h3 class="mb-4">Account Settings</h3>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label>Username</label> <input type="text" name="username"
											class="form-control" value="${username }">
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label>Email</label> <input type="text" name="email"
											class="form-control" value="${email}">
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label>First Name</label> <input type="text" name="firstname"
											class="form-control" value="${firstname}">
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label>Last Name</label> <input type="text" name="lastname"
											class="form-control" value="${lastname}">
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<label>Bio</label>
										<textarea class="form-control" name="bio" rows="4"><c:out
												value="${bio}" /></textarea>
									</div>
								</div>
							</div>
							<div>
								<button class="btn btn-primary">Update</button>
								<button class="btn btn-light" >Cancel</button>
				
							</div>
					</div>
							
<!-- 					<div class="tab-pane fade" id="password" role="tabpanel"
						aria-labelledby="password-tab"> -->
					<div class="tab-pane fade<% if ("changePassword".equals(session.getAttribute("activeTab"))) { session.removeAttribute("activeTab"); %> show active<% } %>" id="password" role="tabpanel" aria-labelledby="password-tab">
						
						<h3 class="mb-4">Password Settings</h3>
						<form action="ChangePassword" method="post">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
								  	<label>Old password</label>
								  	<input type="password" name="oldPassword" class="form-control">
								</div>
								<h6>${verifchangePassword.erreurs['oldPassword']}</h6>
							</div>
							
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									
								  	<label>New password</label>
								  	<input type="password" name="newPassword" class="form-control">
								</div>
								<h6>${verifchangePassword.erreurs['errorPaswordLength']}</h6>
							</div>
							<div class="col-md-6">
								<div class="form-group">

								  	<label>Confirm new password</label>
								  	<input type="password" name="ConfirmNewPassword" class="form-control"></div>
							  	<h6 >${verifchangePassword.erreurs['passwordDifferent']}</h6>
								  	
							</div>
						</div>
						<div>
							<button type="submit" class="btn btn-primary">change</button>
							<button class="btn btn-light">Cancel</button>
						</div>
						</form>
					</div>

					<div class="tab-pane fade<% if ("deleteProfile".equals(session.getAttribute("activeTab"))) { session.removeAttribute("activeTab"); %> show active<% } %>" id="deleteP" role="tabpanel" aria-labelledby="deleteP-tab">
					<form action="DeleteProfile" method="post">
						<h3 class="mb-4">Delete profile</h3>

							<div class="col-md-6">
								<div class="form-group">

								  	<label>password</label>
								  	<input type="password" class="form-control" name="pswd">
								</div>
							</div>
							<button class="btn btn-primary" type="submit">Delete profile</button>
						  <h6 class="text-center"> ${statutDeleteProfile}</h6>
							</form>
					</div>
				</div>
			</div>
		</div>
	</section>

		 </c:if>	

	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>