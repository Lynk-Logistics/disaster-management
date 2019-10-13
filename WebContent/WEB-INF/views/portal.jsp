<%@page import="com.zeemonsters.disman.auth.SecurityConfig"%>
<%@page import="com.zeemonsters.disman.auth.AppUtils"%>
<%@page import="com.zeemonsters.disman.auth.UserAccount"%>

<jsp:include page="header.jsp"></jsp:include>

<div class="hero-body">
	<div class="container has-text-centered">
		<div class="columns is-vcentered">
			<div class="column green">
				<nav class="level">
					<!-- Left side -->
					<div class="level-left">
						<div class="level-item">
							<p class="subtitle is-8 green" id="title"></p>
						</div>
					</div>


					<!-- Right side -->
					<div class="level-right">
						<p class="level-item" id="date"></p>
						<p class="level-item">
							
						</p>
					</div>
				</nav>
			</div>
		</div>
	</div>
</div>


<nav class="tabs is-boxed is-fullwidth is-large">
	<div class="container">
		<ul class="tabcont">
			<li class="tab is-active"id="tabnav"  onclick="openTab(event,'announce')"><span
				class="icon is-small"><i class="fa fa-bullhorn"></i></span> <span>Announcements</span></li>
			 <li class="tab" id="tabnav" onclick="openTab(event,'issues')"><span
				class="icon is-small"><i class="fa fa-comments"></i></span> <span>Issues</span></li> 
				<%
										UserAccount account = AppUtils.getLoginedUser(session);
									%>
									<%
										if (account != null) {
											if (account.getRoles().get(0).equals(SecurityConfig.ROLE_VOLUNTEER)) {
									%>
									<li class="tab" id="tabnav" onclick="openTab(event,'make')"><span
				class="icon is-small"><i class="fa fa-plus"></i></span> <span>Make announcement</span></li>
									<%}
										}%>
									
		</ul>
	</div>
</nav>



<div class="container section" style="width:100%">
	<div id="announce" class="content-tab">
		
		<div class="box content" id="announcements">
						
						
		</div>
		</div>
									<%
										if (account != null) {
											if (account.getRoles().get(0).equals(SecurityConfig.ROLE_VOLUNTEER)) {
									%>
	<div id="make" class="content-tab" style="display: none">
		<div class="field">
						<div class="control">
							<input class="input is-large" type="text" id="titletext" placeholder="Announcement Title"
								name="title" autofocus="true">
						</div>
					</div>

					<div class="field">
						<div class="control">
							<input class="input is-large" id="tag" name="tag" type="text"
								placeholder="Tag">
						</div>
					</div>
					
					<div class="field">
						<div class="control">
							<input class="input is-large" type="text" id="desc" placeholder="Description"
								name="description">
						</div>
					</div>
					<button class="button is-block is-info is-large is-fullwidth" id="makeann">
						Announce <i class="fa fa-sign-in" aria-hidden="true"></i>
					</button>
	
	</div>
	<%}
										}%>
	<div id="issues" class="content-tab" style="display: none">
		<div class="field">
					<div class="control">
						<input class="input is-large" type="text" placeholder="UserId"
							name="userid" id="userid">
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
						<input class="input is-large" type="text" placeholder="Message"
							name="message" id="message" >
					</div>
				</div>
				
				<button class="button is-block is-info is-large is-fullwidth" id="raise">
						Raise Issue <i class="fa fa-sign-in" aria-hidden="true"></i>
					</button>
	</div> 
</div>
<hr>
<div class="fullwidth">
<h4 class="title is-4">Affected Regions</h4>
<div id="map"></div>
</div>
<script type="text/javascript"
  src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAoE7sNgTzWciNQRWue4naZaukAf1hEt7Q&libraries=visualization&region=tamilnadu">
</script>
<script>
	$(document).ready(function(){
		var heatmapData = [];
		$.ajax({
			type : 'GET',
			url : "${pageContext.request.contextPath}/api/requests",
			xhrFields : {
				withCredentials : true
			},
			success : function(resultData) {
				var data = resultData.all;
				
				for (var i = 0; i < data.length; i++) {
					if(data[i].lat!=""&&data[i].long!=""){
						heatmapData.push({location: new google.maps.LatLng(data[i].lat,data[i].long), weight: 100000});
					}
				}
				var region = new google.maps.LatLng(13.0478223,80.0689243);

				map = new google.maps.Map(document.getElementById('map'), {
				  center: region,
				  zoom: 10,
				  mapTypeId: 'terrain'
				});

				var heatmap = new google.maps.visualization.HeatmapLayer({
				  data: heatmapData
				});
				heatmap.setMap(map);
			}
		});
		
			
	});
</script>



<div id="notification" class="container"><div class="columns"><div class="column"><div class="notification"><button class="delete"></button><span class="not"></span></div></div></div></div>
<script>
//@formatter:off
var portalurl = '<%=request.getParameter("url")%>';
var portalid = -1;
		//@formatter:on
function openTab(evt, tabName) {
	  var i, x, tablinks;
	  x = document.getElementsByClassName("content-tab");
	  for (i = 0; i < x.length; i++) {
	      x[i].style.display = "none";
	  }
	  tablinks = document.getElementsByClassName("tab");
	  for (i = 0; i < x.length; i++) {
	      tablinks[i].className = tablinks[i].className.replace(" is-active", "");
	  }
	  document.getElementById(tabName).style.display = "block";
	  evt.currentTarget.className += " is-active";
	}
	function refreshData(){
		$("#announcements").html('');
		var id = portalid;
		
		$.ajax({
			type : 'GET',
			url : "${pageContext.request.contextPath}/api/announcement/"
					+ id,
			xhrFields : {
				withCredentials : true
			},
			success : function(resultData) {
				
				for (var i = 0; i < resultData.length; i++) {
					var txt = '<article class="post"><h4>'+resultData[i].title+'</h4><div class="media"><div class="media-content"><div class="content">';
					
					txt+='<p>@'+resultData[i].username+' <span>'+resultData[i].eventdate+'</span></p>';
					txt+='<p>'+resultData[i].description+'</p>';
					var tagV = "";
					var tags = resultData[i].tag.split(",");
					for (var j = 0; j < tags.length; j++) {
						tagV+='<span class="tag is-warning">'+tags[j]+'</span>';
					}
					txt+='<p>'+tagV	+'</p></div></div></div></article><hr>';
					$("#announcements").append(txt);
				}
				
				
			}
		});
	}
	$(document).ready(function() {
		
		$("#raise").click(function(){
			
				 $.ajax({
						type : 'POST',
						url : "${pageContext.request.contextPath}/api/requests",
						xhrFields : {
							withCredentials : true
						},
						data : {
							user:$("#userid").val(),
							pincode:$("#pincode").val(),
							lat:$("#lat").val(),
							longi:$("#longi").val(),
							message:$("#message").val(),
						},
						success : function(resultData) {
							console.log(resultData);
							
								$(".not").html(resultData.message);
								$(".notification").addClass("is-success");
								$("#notification").fadeIn(500);
								$(".modal").removeClass("is-active");
								$("#userid").val('');
								$("#pincode").val('');
								$("#lat").val('');
								$("#longi").val('');
								$("#message").val('');
								$("#tags").val('');
								$("#assigned").val('');
								$("#datetime").val('');
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
		
		$("#makeann").click(function(){
			var titl= $("#titletext").val();
			var desc= $("#desc").val();
			var tag= $("#tag").val();
			$.ajax({
				type : 'POST',
				url : "${pageContext.request.contextPath}/api/announcement",
				xhrFields : {
					withCredentials : true
				},
				data : {
					title : titl,
					tag : tag,
					description : desc,
					portal:portalid
				},
				success : function(resultData) {
					
						$(".not").html(resultData.message);
						$(".notification").addClass("is-success");
						$("#notification").fadeIn(500);
						$("#titletext").val('');
						$("#tag").val('');
						$("#desc").val('');
						$(document).on('click', '.notification > button.delete', function() {
						    $("#notification").fadeOut(500);
						    return false;
						});
						refreshData();
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
		
		setActive("lportals");
				$.ajax({
					type : 'GET',
					url : "${pageContext.request.contextPath}/api/portal/"
							+ portalurl,
					xhrFields : {
						withCredentials : true
					},
					success : function(resultData) {
						
						var title = resultData.name + ' - ' + resultData.region
								+ ' <br>' + resultData.description;
						var sub = resultData.eventdate;
						$("#title").html(title);
						$("#date").html(sub);
						portalid = resultData.id;
						refreshData();
						
					}
				});

			});
</script>
<jsp:include page="footer.jsp"></jsp:include>