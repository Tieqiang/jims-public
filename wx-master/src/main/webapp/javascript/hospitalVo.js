/**
 * Created by Administrator Dt on 2016-03-08.
 */
$(function () {

    var curHospital = {};

    $.get("api/staff-dict/find-by-info?hospitalId=4028862d4fcf2590014fcf9aef480016&loginName=000LHR&password=123", function (data) {
        $("#name").text(data.name);
        $.get("api/hospital-dict/find-by-hospitalId?hospitalId=4028862d4fcf2590014fcf9aef480016", function (data) {
            $("#hospitalNameSpan").text(data.hospitalName);
            curHospital = data;
        })
    });

    //医院首页管理
    $("#updateUrl").click(function () {
        $("#urlDivOne").css("display","none");
        $("#urlInput").val($("#urlSpan").text());
        $("#urlDivTwo").css("display","block");
    });

    $("#saveUrl").click(function () {
        $("#urlDivTwo").css("display","none");
        $("#urlSpan").text($("#urlInput").val());
        $("#urlDivOne").css("display","block");

    });

    //医院名称管理
    $("#updateHospitalName").click(function () {
        $("#hospitalNameDivOne").css("display","none");
        $("#hospitalNameInput").val($("#hospitalNameSpan").text());
        $("#hospitalNameDivTwo").css("display","block");
    });

    $("#saveHospitalName").click(function () {
        $("#hospitalNameDivTwo").css("display","none");
        $("#hospitalNameDivOne").css("display","block");
        curHospital.hospitalName = $("#hospitalNameInput").val();
        jQuery.ajax({
            'type': 'PUT',
            'url': "/api/hospital-dict/update",
            'contentType': 'application/json',
            'data': JSON.stringify(curHospital),
            'dataType': 'json',
            'success': function (data) {
                console.log(data);
                $("#hospitalNameSpan").text(data.hospitalName);

            },
            'error': function (data) {
                console.log("FAIL"+data);
            }
        });


    });
     //个人邮箱管理
    $("#updateEmail").click(function () {
        $("#emailDivOne").css("display","none");
        $("#emailInput").val($("#emailSpan").text());
        $("#emailDivTwo").css("display","block");
    });

    $("#saveEmail").click(function () {
        $("#emailDivTwo").css("display","none");
        $("#emailSpan").text($("#emailInput").val());
        $("#emailDivOne").css("display","block");
    });
});