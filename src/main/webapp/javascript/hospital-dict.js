/**
 * Created by Dt on 2016-03-03.
 */

$(function () {
    var hospitalVo = {};
    var content = CKEDITOR.replace('content');

    //下拉框检索
    $(".select2").select2();

    $("#btnSubmit").click(function(){
        hospitalVo.hospitalName = $("#hospitalName").val();
        hospitalVo.unitCode = $("#unitCode").val();
        hospitalVo.location = $("#location").val();
        hospitalVo.zipCode = $("#zipCode").val();
        hospitalVo.parentHospital = $("#parentHospital").val();
        hospitalVo.organizationFullCode = $("#organizationFullCode").val();
        hospitalVo.parentHospital =$("#parentHospital").val().toString();

        $("#hospitalDictDiv").css("display","none");
        $("#hospitalInfoDiv").css("display","block");

    });
    $("#btnSubmit1").click(function(){
        hospitalVo.appId = $("#appId").val();
        hospitalVo.infoUrl = $("#infoUrl").val();
        hospitalVo.content = content.document.getBody().getHtml();

        jQuery.ajax({
            'type': 'POST',
            'url': "/api/hospital-dict/add_vo",
            'contentType': 'application/json',
            'data': JSON.stringify(hospitalVo),
            'dataType': 'json',
            'success': function(data){
                console.log(data);
            },
            'error': function(data){
                console.log("shibai");
            }
        });

    });

});