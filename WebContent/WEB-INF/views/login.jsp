
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<jsp:include page="header.jsp"></jsp:include>


<div class="hero-body">
	<div class="container has-text-centered">
		<div class="column is-4 is-offset-4">
			<h3 class="title has-text-black">Login</h3>
			<hr class="login-hr">
			<%
				if (request.getAttribute("errorMessage") != null) {
			%>
			<p class="subtitle error">${errorMessage}</p>
			<%
				request.setAttribute("errorMessage", null);
				}
			%>

			<div class="box">
				<figure class="avatar">
					<i class="fa fa-handshake-o fa-4x"></i>
				</figure>
				<form method="POST"
					action="${pageContext.request.contextPath}/login">
					<input type="hidden" name="redirectId" value="${redirectId}" />
					<div class="field">
						<div class="control">
							<input class="input is-large" type="text" placeholder="Username"
								name="username" autofocus="true">
						</div>
					</div>

					<div class="field">
						<div class="control">
							<input class="input is-large" name="password" type="password"
								placeholder="Password">
						</div>
					</div>

					<button class="button is-block is-info is-large is-fullwidth">
						Login <i class="fa fa-sign-in" aria-hidden="true"></i>
					</button>
				</form>
			</div>
			<p class="has-text-grey">
				<a href="${pageContext.request.contextPath}/signup">Sign Up</a>
			</p>
		</div>
	</div>
</div>

<script>
	$(document).ready(function(){
		setActive("llogin");
	});
</script>
<jsp:include page="footer.jsp"></jsp:include>