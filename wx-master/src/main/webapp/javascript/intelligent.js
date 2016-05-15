$(document).ready(function(){
	
	$(window.parent.document).find("#myFrame").attr("height",$(document.body).height());
    /**
     * 单击身体部位查询症状
     * @param id
     */

 });


function symptomList(id){
//    alert(id);
	var sex = $('#sex').val();
	var age = $('#age').val();
	$('[id^=symptomList_]').removeClass('changezndzzz');
	$('#symptomList_'+id).addClass('changezndzzz');
 	$contend = $('#symptomList');
	$contend.html('');
	
}


function back(){
	$('#body').addClass('wormanDisplay');
	$('#pic').removeClass('wormanDisplay');
}

function changeSex(){
	var changeSex = $('#changeSex').attr('v');
	if(changeSex==0){
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
}


function questionList(id,haveQuestionNum){
	var symptomName = $('#symptomName_'+id).attr('v');
	$('#zndz0202').addClass('wormanDisplay');
	$('#questionList').removeClass('wormanDisplay');
	$contend = $('#questionList');
	$contend.html('');
	
	$('#possibleDisease').after('<input type="hidden" id="symptomId" name="symptomId" v='+symptomName+' value="'+id+'"/>');
}
function back2(){
	$('#zndz0202').removeClass('wormanDisplay');
	$('#questionList').addClass('wormanDisplay');
	
}

function addDisease(){
	$('#addDisease').addClass('wormanDisplay');
	$('#zndz0202').removeClass('wormanDisplay');
	$('#pic').removeClass('wormanDisplay');
	$('#body').addClass('wormanDisplay');
}

function deleteDisease(id){
	$('#disease_'+id).remove();
	if($('[id^=disease_]').length==0){
		$('#addDiseaseRight').html('');
	}else{
		var questionPptionIdList = "";
		var symptomIdList = "";
		var sex = $('#sex').val();
		var age = $('#age').val();
		$('[id^=disease_]').each(function(){
			var v = $(this).attr('v');
			var q = $(this).attr('q');
			symptomIdList = symptomIdList + v + ",";	
			questionPptionIdList = questionPptionIdList + q;
		});
		$contend = $('#addDiseaseRight');
		$contend.html('');
	}
}

function showSymptom(id){
//	var sex = $('#sex').val();
//	var age = $('#age').val();
	$('#pic').addClass('wormanDisplay');
	
	$('#body').removeClass('wormanDisplay');
	$('#zndz0202').removeClass('wormanDisplay');
	
	$('#symptomList_'+id).trigger('click');
	$('#symptomList_'+id).addClass('changezndzzz');
	
}

