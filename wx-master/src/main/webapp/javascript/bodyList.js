$(document).ready(function(){
	var xb = $('#xb').val();
	if(xb=="F"){
		$('#nanren').addClass('wormanDisplay');
		$('#nvren').removeClass('wormanDisplay');
		$('#changeSex').html('<img src="/assert/images/nanbutto.jpg">');
		$('#changeSex').attr('v',1);
		$('#sex')[0].selectedIndex = 1;
	}else{
		$('#nanren').removeClass('wormanDisplay');
		$('#nvren').addClass('wormanDisplay');
		$('#changeSex').html('<img src="/assert/images/nvbutto.jpg">');
		$('#changeSex').attr('v',0);
		$('#sex')[0].selectedIndex = 0;
	}
});