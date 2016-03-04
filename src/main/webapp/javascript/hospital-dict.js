/**
 * Created by Dt on 2016-03-03.
 */

$(function () {
    $("#btnSubmit").click(function(){
        var hospitalDict = {};
        var hospitalInfo = {};
        hospitalDict.hospitalName = $("#hospitalName").val();
        hospitalDict.unitCode = $("#unitCode").val();
        hospitalDict.location = $("#location").val();
        hospitalDict.zipCode = $("#zipCode").val();
        hospitalDict.parentHospital = $("#parentHospital").val();

        jQuery.ajax({
            'type': 'POST',
            'url': "/api/hospital-dict/add",
            'contentType': 'application/json',
            'data': JSON.stringify(hospital),
            'dataType': 'json',
            'success': function(data){
                hospitalInfo.hospitalId = data.id;
                console.log(data);
            },
            'error': function(data){
                console.log("shibai");
            }
        });

    });

});