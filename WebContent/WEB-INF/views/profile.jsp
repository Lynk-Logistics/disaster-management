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
					<p class="modal-card-title">Update Personal Details</p>
				</header>
				<section class="modal-card-body">

					<div class="field">
						<div class="control">
							<input class="input is-large" id="region" type="text"
								placeholder="Region" name="region" autofocus="true">
						</div>
					</div>


					<div class="field">
						<label class="checkbox"> <input type="checkbox"
							id="available" name="available"> I am available
						</label>
					</div>

					<div class="field">
						<div class="control">
							<input class="input is-large" id="pincode" type="text"
								placeholder="Pincode" name="pincode">
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
					<button class="button is-success create">Update</button>
				</footer>
			</div>
			<div class="column">
				<h1 class="title is-2">DisMan</h1>
				<h2 class="subtitle is-4">Helping when required</h2>
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

	}
%>




<script>
	$(document)
			.ready(
					function() {

						setActive("lprofile");

						$
								.ajax({
									type : 'GET',
									url : "${pageContext.request.contextPath}/api/profile",
									xhrFields : {
										withCredentials : true
									},
									success : function(resultData) {
										console.log(resultData);
										if(resultData.available==="true"){
											$("#available").prop( "checked", true );
										}
								$("#pincode").val(
										resultData.pincode);
								$("#region")
										.val(resultData.region);
									},
									error : function(resultData) {

									}
								});
						/*  */

						$(".create")
								.click(
										function() {
											var available = $("#available")
													.prop( "checked");
											var pincode = $("#pincode").val();
											var region = $("#region").val();

											$
													.ajax({
														type : 'POST',
														url : "${pageContext.request.contextPath}/api/profile",
														xhrFields : {
															withCredentials : true
														},
														data : {
															available : available,
															region : region,
															pincode : pincode
														},
														success : function(
																resultData) {
															console
																	.log(resultData);

															$(".not")
																	.html(
																			resultData.message);
															$(".notification")
																	.addClass(
																			"is-success");
															$("#notification")
																	.fadeIn(500);
															

															$(document)
																	.on(
																			'click',
																			'.notification > button.delete',
																			function() {
																				$(
																						"#notification")
																						.fadeOut(
																								500);
																				return false;
																			});
														},
														error : function(
																resultData) {

															$(".not")
																	.html(
																			resultData.responseJSON.message);
															$(".notification")
																	.addClass(
																			"is-danger");
															$("#notification")
																	.fadeIn(500);
															$(document)
																	.on(
																			'click',
																			'.notification > button.delete',
																			function() {
																				$(
																						"#notification")
																						.fadeOut(
																								500);
																				return false;
																			});

															console
																	.log("Save Complete");
														}
													});
											/*  */
										});

					});
</script>
<jsp:include page="footer.jsp"></jsp:include>