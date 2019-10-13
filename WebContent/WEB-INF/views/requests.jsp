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
<div class="container" style="width: 95%">
	<div class="modal">
		<div class="modal-background"></div>
		<div class="modal-card">
			<header class="modal-card-head">
				<p class="modal-card-title">Update Issue</p>
				<button class="delete model-close" aria-label="close"></button>
			</header>
			<section class="modal-card-body">
				<div class="field">
					<div class="control">
						<input class="input is-large" type="text" placeholder="UserId"
							name="userid" id="userid" disabled="disabled">
					</div>
				</div>
				<div class="field">
					<div class="control">
						<input class="input is-large" type="text" placeholder="Pincode"
							name="pincode" id="pincode">
					</div>
				</div>

				<div class="field">
					<div class="control">
						<input class="input is-large" type="text" placeholder="Latitude"
							name="lat" id="lat">
					</div>
				</div>

				<div class="field">
					<div class="control">
						<input class="input is-large" type="text" placeholder="Longitude"
							name="longi" id="longi">
					</div>
				</div>

				<div class="field">
					<div class="control">
						<input class="input is-large" type="text" placeholder="Tags"
							name="tags" id="tags">
					</div>
				</div>

				<div class="field">
					<div class="control">
						<input class="input is-large" type="text" placeholder="Message"
							name="message" id="message" disabled="disabled">
					</div>
				</div>


				<div class="field">
					<div class="control">
						<input class="input is-large" type="text"
							placeholder="Assigned To" name="assigned" id="assigned">
					</div>
				</div>


				<div class="field">
					<div class="control">
						<input class="input is-large" type="text" placeholder="datetime"
							name="datetime" id="datetime" disabled="disabled">
					</div>
				</div>


			</section>
			<footer class="modal-card-foot">
				<button class="button is-success update">Save changes</button>
				<button class="button model-close">Cancel</button>
			</footer>
		</div>
	</div>



	<h4 class="title is-4">By Latitude Longitude | Closer to you</h4>
	<table class="table is-bordered  is-striped is-fullwidth">
		<thead>
			<tr>
				<th>UserId</th>
				<th>Pincode</th>
				<th>Latitude</th>
				<th>Longitude</th>
				<th>Tags</th>
				<th>Message</th>
				<th>Assigned To</th>
				<th>Date</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody id="tbody">

		</tbody>
	</table>
	<hr>
	<h4 class="title is-4">By Pincode</h4>
	<table class="table is-bordered  is-striped is-fullwidth">
		<thead>
			<tr>
				<th>UserId</th>
				<th>Pincode</th>
				<th>Latitude</th>
				<th>Longitude</th>
				<th>Tags</th>
				<th>Message</th>
				<th>Assigned To</th>
				<th>Date</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody id="tbody2">

		</tbody>
	</table>
	
	<hr>
	<h4 class="title is-4">ALL</h4>
	<table class="table is-bordered  is-striped is-fullwidth">
		<thead>
			<tr>
				<th>UserId</th>
				<th>Pincode</th>
				<th>Latitude</th>
				<th>Longitude</th>
				<th>Tags</th>
				<th>Message</th>
				<th>Assigned To</th>
				<th>Date</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody id="tbody3">

		</tbody>
	</table>
</div>
<script>
function edit(id){
	var user = "<%=account.getUserName()%>";
	$
	.ajax({
		type : 'GET',
		url : "${pageContext.request.contextPath}/api/requests/"+id,
		xhrFields : {
			withCredentials : true
		},
		success : function(resultData) {
			console.log(resultData);
			
			$("#userid").val(resultData.userid);
			$("#pincode").val(resultData.pincode);
			$("#lat").val(resultData.lat);
			$("#longi").val(resultData.long);
			$("#message").val(resultData.message);
			$("#tags").val(resultData.tags);
			console.log(user);
			console.log(resultData.assigned);
			if(resultData.assigned==undefined||resultData.assigned==""){
				$("#assigned").val(user);	
			}else{
			$("#assigned").val(resultData.assigned);
			}
			$("#datetime").val(resultData.datetime);
			
			 $(".modal").addClass("is-active");
			 $(".update").click(function(){
				 $.ajax({
						type : 'POST',
						url : "${pageContext.request.contextPath}/api/requests/"+id,
						xhrFields : {
							withCredentials : true
						},
						data : {
							user:$("#userid").val(),
							pincode:$("#pincode").val(),
							lat:$("#lat").val(),
							longi:$("#longi").val(),
							message:$("#message").val(),
							tags:$("#tags").val(),
							assigned:$("#assigned").val(),
							datetime:$("#datetime").val(),
						},
						success : function(resultData) {
							console.log(resultData);
							
								$(".not").html(resultData.message);
								$(".notification").addClass("is-success");
								$("#notification").fadeIn(500);
								
								$(".modal").removeClass("is-active");
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
			 });
		}
	});
}
	$(document)
			.ready(
					function() {
						
							   
						

							$(".model-close").click(function() {
							   $(".modal").removeClass("is-active");
							});

						setActive("lrequests");
						
						
						
						

						$
								.ajax({
									type : 'GET',
									url : "${pageContext.request.contextPath}/api/requests",
									xhrFields : {
										withCredentials : true
									},
									success : function(resultData) {
										console.log(resultData);
										var latlong = resultData.latlong;
										for (var i = 0; i < latlong.length; i++) {
											var assign = latlong[i].assigned!=undefined?latlong[i].assigned:"-";
											var pin = latlong[i].pincode!=undefined?latlong[i].pincode:"-";
											var lat = latlong[i].lat!=undefined?latlong[i].lat:"-";
											var longi = latlong[i].long!=undefined?latlong[i].long:"-";
											var tags = latlong[i].tags!=undefined?latlong[i].tags:"-";
											var message = latlong[i].message!=undefined?latlong[i].message:"-";
											var userid = latlong[i].userid;
											var datetime = latlong[i].datetime;
											var id =  latlong[i].id;
											if(tags!="-"){
												var tag = tags.split(",");
												var tagx = "";
												for (var j = 0; j < tag.length; j++) {
													tagx+='<span class="tag is-warning">'+tag[j]+'</span>';
												}
												tags = tagx;
											}
											var txt = "<tr><td>"+userid+"</td><td>"+pin+"</td><td>"+lat+"</td><td>"+longi+"</td><td>"+tags+"</td><td>"+message+"</td><td>"+assign+"</td><td>"+datetime+"</td><td><button class='button' onclick=edit('"+id+"')>Update</button></td></tr>";
											$("#tbody").append(txt);
										}
										var pincode = resultData.pin;
										for (var i = 0; i < pincode.length; i++) {
											var assign = pincode[i].assigned!=undefined?pincode[i].assigned:"-";
											var pin = pincode[i].pincode!=undefined?pincode[i].pincode:"-";
											var lat = pincode[i].lat!=undefined?pincode[i].lat:"-";
											var longi = pincode[i].long!=undefined?pincode[i].long:"-";
											var tags = pincode[i].tags!=undefined?pincode[i].tags:"-";
											var message = pincode[i].message!=undefined?pincode[i].message:"-";
											var userid = pincode[i].userid;
											var datetime = pincode[i].datetime;
											var id =  pincode[i].id;
											if(tags!="-"){
												var tag = tags.split(",");
												var tagx = "";
												for (var j = 0; j < tag.length; j++) {
													tagx+='<span class="tag is-warning">'+tag[j]+'</span>';
												}
												tags = tagx;
											}
											
											var txt = "<tr><td>"+userid+"</td><td>"+pin+"</td><td>"+lat+"</td><td>"+longi+"</td><td>"+tags+"</td><td>"+message+"</td><td>"+assign+"</td><td>"+datetime+"</td><td><button class='button' onclick=edit('"+id+"')>Update</button></td></tr>";
											$("#tbody2").append(txt);
										}
										var all = resultData.all;
										
										for (var i = 0; i < all.length; i++) {
											var assign = all[i].assigned!=undefined?all[i].assigned:"-";
											var pin = all[i].pincode!=undefined?all[i].pincode:"-";
											var lat = all[i].lat!=undefined?all[i].lat:"-";
											var longi = all[i].long!=undefined?all[i].long:"-";
											var tags = all[i].tags!=undefined?all[i].tags:"-";
											var message = all[i].message!=undefined?all[i].message:"-";
											var userid = all[i].userid;
											var datetime = all[i].datetime;
											var id =  all[i].id;
											if(tags!="-"){
												var tag = tags.split(",");
												var tagx = "";
												for (var j = 0; j < tag.length; j++) {
													tagx+='<span class="tag is-warning">'+tag[j]+'</span>';
												}
												tags = tagx;
											}
											
											var txt = "<tr><td>"+userid+"</td><td>"+pin+"</td><td>"+lat+"</td><td>"+longi+"</td><td>"+tags+"</td><td>"+message+"</td><td>"+assign+"</td><td>"+datetime+"</td><td><button class='button' onclick=edit('"+id+"')>Update</button></td></tr>";
											
											$("#tbody3").append(txt);
										}
									},
									error : function(resultData) {

									}
								});
						/*  */

						$(".create")
								.click(
										function() {
											var available = $("#available")
													.prop("checked");
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
<%
	}

	}
%>
<div id="notification" class="container">
	<div class="columns">
		<div class="column">
			<div class="notification">
				<button class="delete"></button>
				<span class="not"></span>
			</div>
		</div>
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>