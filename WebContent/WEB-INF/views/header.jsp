<!DOCTYPE html>
<%@page import="com.zeemonsters.disman.auth.SecurityConfig"%>
<%@page import="com.zeemonsters.disman.auth.AppUtils"%>
<%@page import="com.zeemonsters.disman.auth.UserAccount"%>

<html>
<head>
<title>ZeeMonsters | DisMan</title>
<link rel="stylesheet" href="./assets/bulma.css">
<link rel="stylesheet" href="./assets/style.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/css?family=Open+Sans"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-2.2.4.min.js"
	integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
	crossorigin="anonymous"></script>
<script src="./assets/script.js"></script>
</head>
<body>
	<section class="hero is-default is-bold">
		<div class="hero-head">
			<nav class="navbar">
				<div class="container">
					<div class="navbar-brand">
						<h3 class="title is-4">
							<a class="navbar-item" href="#"> <i class="fa fa-handshake-o"></i>
								DisMan
							</a>
						</h3>
						<span class="navbar-burger burger" data-target="navbarMenu">
							<span></span> <span></span> <span></span>
						</span>
					</div>
					<div id="navbarMenu" class="navbar-menu">
						<div class="navbar-end">
							<div class="tabs is-right">
								<ul>
									<li id="lhome"><a
										href="${pageContext.request.contextPath}">Home</a></li>
									<%
										UserAccount account = AppUtils.getLoginedUser(session);
									%>
									<%
										if (account != null) {
											if (account.getRoles().get(0).equals(SecurityConfig.ROLE_VOLUNTEER)) {
									%>
									<li id="lportals"><a href="${pageContext.request.contextPath}/portals">Portals</a></li>
									<li id="lprofile"><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
									<li id="lrequests"><a href="${pageContext.request.contextPath}/requests">Requests</a></li>
									<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
									
									<%
										}
										} else {
									%>
									<li id="lportals"><a href="${pageContext.request.contextPath}/portals">Portals</a></li>
									<li id="llogin"><a href="${pageContext.request.contextPath}/login">Login</a></li>
									<li id="lsignup"><a href="${pageContext.request.contextPath}/signup">Sign
											Up</a></li>
									<%
										}
									%>


								</ul>
							</div>
						</div>
					</div>
				</div>
			</nav>
		</div>