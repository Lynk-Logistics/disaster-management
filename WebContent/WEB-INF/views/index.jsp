<%@page import="com.zeemonsters.disman.auth.SecurityConfig"%>
<%@page import="com.zeemonsters.disman.auth.AppUtils"%>
<%@page import="com.zeemonsters.disman.auth.UserAccount"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<jsp:include page="header.jsp"></jsp:include>


<%
	UserAccount account = AppUtils.getLoginedUser(session);
%>
<%
	if (account != null) {
		if (account.getRoles().get(0).equals(SecurityConfig.ROLE_VOLUNTEER)) {
%>
<div class="hero-body">
	<div class="container has-text-centered">
		<div class="columns is-vcentered">

			<div class="column">
				<header class="modal-card-head">
					<p class="modal-card-title">Create Portal</p>
				</header>
				<section class="modal-card-body">

					<div class="field">
						<div class="control">
							<input class="input is-large" id="name" type="text"
								placeholder="Portal Name" name="name" autofocus="true">
						</div>
					</div>

					<div class="field">
						<div class="control">
							<input class="input is-large" id="url" name="url" type="text"
								placeholder="URL Text">
						</div>
					</div>

					<div class="field">
						<div class="control">
							<input class="input is-large" id="region" type="text"
								placeholder="Region" name="region">
						</div>
					</div>

					<div class="field">
						<div class="control">
							<input class="input is-large" id="description" name="description"
								type="text" placeholder="Description">
						</div>
					</div>

					<%
						if (request.getAttribute("message") != null) {
					%>
					<p class="subtitle error">${message}</p>
					<%
						request.setAttribute("errorMessage", null);
								}
					%>

				</section>
				<footer class="modal-card-foot">
					<button class="button is-success create">Create</button>
				</footer>
			</div>
			<div class="column">
				<h1 class="title is-2">DisMan</h1>
				<h2 class="subtitle is-4">Chance favours the prepared mind</h2>
				<br>
				<!-- <p class="has-text-centered">
					<a class="button is-medium is-info is-outlined"> Learn more </a>
				</p> -->
			</div>

		</div>
	</div>
</div>
<%
	}
	} else {
%>
<section class="hero is-primary has-text-centered">
	<div class="hero-body">
		<div class="container">
			<h1 class="title">DisMan</h1>
			<h2 class="subtitle">Chance favours the prepared mind</h2>
		</div>
	</div>
</section>

<%
	}
%>



<div class="container latest">
	<h4 class="title is-4">Recent Portals</h4>
	<div class="row columns" id="portals">
		

	</div>
</div>
<div id="notification" class="container"><div class="columns"><div class="column"><div class="notification"><button class="delete"></button><span class="not"></span></div></div></div></div>
<script>
	$(document).ready(function() {
		setActive("lhome");
		$.ajax({
			type : 'GET',
			url : "${pageContext.request.contextPath}/api/portal",
			xhrFields : {
				withCredentials : true
			},
			success : function(resultData) {
				 for (var i = 0; i < resultData.length; i++) {
					var html = '<div class="column is-one-third "><div class="card large"><div class="card-content"><div class="media"><div class="media-content"><p class="title is-4 no-padding" id="portalName">'+resultData[i].name+'</p><p><span class="title is-6"><a id="url" href="${pageContext.request.contextPath}/portal?url='+resultData[i].url+'">'+resultData[i].url+'</a></span></p><p class="subtitle is-6" id="region">'+resultData[i].region+' '+resultData[i].eventdate+' </p></div></div><div class="content" id="description">'+resultData[i].description+'</div></div></div></div>';
					$("#portals").append(html);
				} 
			}
		});
		$(".create").click(function() {
			var name = $("#name").val();
			var url = $("#url").val();
			var region = $("#region").val();
			var description = $("#description").val();
			$.ajax({
				type : 'POST',
				url : "${pageContext.request.contextPath}/api/portal",
				xhrFields : {
					withCredentials : true
				},
				data : {
					name : name,
					url : url,
					region : region,
					description : description
				},
				success : function(resultData) {
					console.log(resultData);
					
						$(".not").html(resultData.message);
						$(".notification").addClass("is-success");
						$("#notification").fadeIn(500);
						$("#name").val('');
						$("#url").val('');
						$("#region").val('');
						$("#description").val('');
						$(document).on('click', '.notification > button.delete', function() {
						    $("#notification").fadeOut(500);
						    return false;
						});
				},error:function(resultData){
					
						$(".not").html(resultData.responseJSON.message);
						$(".notification").addClass("is-danger");
						$("#notification").fadeIn(500);
						$(document).on('click', '.notification > button.delete', function() {
							$("#notification").fadeOut(500);
						    return false;
						});
					
					console.log("Save Complete");
				}
			});
			/*  */
		});

	});
</script>
<jsp:include page="footer.jsp"></jsp:include>