/**
 * Created by Dt on 2016-03-03.
 */

$(function () {
    $("#btnSubmit").click(function(){
       var staff = {};
        staff.loginName = $("#loginName").val();
        staff.name = $("#name").val();

        staff.password = $("#password").val();


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