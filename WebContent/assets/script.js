function setActive(page){
	$("#"+page).parent().children().removeClass("is-active");
	$("#"+page).addClass("is-active");
	
}