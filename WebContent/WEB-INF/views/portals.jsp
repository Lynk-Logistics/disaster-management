<%@page import="com.zeemonsters.disman.auth.SecurityConfig"%>
<%@page import="com.zeemonsters.disman.auth.AppUtils"%>
<%@page import="com.zeemonsters.disman.auth.UserAccount"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<jsp:include page="header.jsp"></jsp:include>

<div class="container latest">

	<h4 class="title is-4">Portals</h4>
	<div class="row columns" id="portals">
		

	</div>
</div>

<script>
	$(document).ready(function() {
		setActive("lportals");
		$.ajax({
			type : 'GET',
			url : "${pageContext.request.contextPath}/api/portal",
			xhrFields : {
				withCredentials : true
			},
			success : function(resultData) {
				 for (var i = 0; i < resultData.length; i++) {
					 var html = '<div class="column is-one-third"><div class="card large"><div class="card-content"><div class="media"><div class="media-content"><p class="title is-4 no-padding" id="portalName">'+resultData[i].name+'</p><p><span class="title is-6"><a id="url" href="${pageContext.request.contextPath}/portal?url='+resultData[i].url+'">'+resultData[i].url+'</a></span></p><p class="subtitle is-6" id="region">'+resultData[i].region+' '+resultData[i].eventdate+' </p></div></div><div class="content" id="description">'+resultData[i].description+'</div></div></div></div>';
					$("#portals").append(html);
				} 
			}
		});
		

	});
</script>
<jsp:include page="footer.jsp"></jsp:include>