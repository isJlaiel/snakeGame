<%@ include file="Inclusion.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<!-- Boxicons -->
	<link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
	<!-- My CSS -->
<style>
<%@include file="/WEB-INF/css/styleDashboard.css"%>
</style>
	<title>Profile page</title>
</head>
<body>


	<c:if test="${ !empty sessionScope.username  }">

	<!-- SIDEBAR -->
	<section id="sidebar">
	
		<a href="#" class="brand">
			<i class='bx bxs-smile'></i>

        <span class="text"> ${ sessionScope.username }  !</span>
		
		</a>
<span class="text">Vous êtes ${ sessionScope.username }  !</span>>
   	</a>
		
		<ul class="side-menu">
			<li>
				<a href="EditProfile">

					<i class='bx bxs-cog' ></i>
					<span class="text">Settings</span>
				</a>
			</li>
			<li>
				<a href="Logout" class="logout">
					<i class='bx bxs-log-out-circle' ></i>
					<span class="text">Logout</span>
				</a>
			</li>
						
		</ul>
	</section>
	<!-- SIDEBAR -->



	<!-- CONTENT -->
	<section id="content">
	
		<main>
			<div class="head-title">
				<div class="left">
					<h1>Dashboard</h1>
					<ul class="breadcrumb">
						<li>
							<a href="#">Dashboard</a>
						</li>
						<li><i class='bx bx-chevron-right' ></i></li>
						<li>
							<a class="active" href="#">Home</a>
						</li>
					</ul>
				</div>
				
			</div>

			<ul class="box-info">
				<li>
					<i class='bx bxs-joystick'></i>
					<span class="text">
						<h3>${totalNbParie}</h3>
						<p>Parties</p>
					</span>
				</li>
				
					<li>
					<i class='bx bx bxs-group' ></i>
					<span class="text">
						<h3>${userList.size()}</h3>
						<p>Players</p>
					</span>
				</li>
			</ul>

<div class="table-data">
  <div class="order">
    <div class="head">
      <h3>Ranking</h3>
    </div>
    <table>
      <thead>
        <tr>
          <th>Picture</th>
          <th>Player</th>
          <th>Total score</th>
          <th>total number of games</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${userList}" var="user">
          <tr>
            <td><img src="${user.image}"/></td>
            <td>${user.username}</td>
            <td>${user.score}</td>
            <td>${user.nbPartie}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>

  <div class="order">
    <div class="head">
      <h3>Historique</h3>
    </div>
    <table>
      <thead>
        <tr>
          <th>score</th>
          <th>number of games</th>
          <th>date</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${HistoriqueList}" var="historique">
          <tr>
            <td>${historique.scoreTotal}</td>
            <td>${historique.nbPartie}</td>
            <td>${historique.date}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

		</main>
		<!-- MAIN -->
		 
		
	</section>
	<!-- CONTENT -->
						 </c:if>	
	
<script src="js/script2.js"></script>
</body>
</html>