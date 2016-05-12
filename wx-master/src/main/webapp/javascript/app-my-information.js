/**
 * Created by cxy on 2016/4/27.
 */
 function getUrlParameter(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regexS = "[\\?&]" + name + "=([^&#]*)";
    var regex = new RegExp(regexS);
    var results = regex.exec(window.parent.location.href);
    if (results == null)    return ""; else {
        return results[1];
    }
}
var openId = getUrlParameter("openId");
$(function () {
     $.ajax({
        type: "POST",
        cache: false,
        url: "/api/pat-info/find-info-by-open-id?openId=" + openId,
        dataType: "JSON",
        success: function (data) {//patInfo Json
            $("#id").val(data.id);
            $("#name").html(data.name);
         }
    });
});
 /*
 *//**
 * 绑定员工
 *//**/

function staffBangker() {
    window.location.href = "/views/his/public/hospital-staff.html?openId=" + openId;
}


var app = angular.module("myApp", []);
var nameList = [];
var nameId = "";
app.controller('tableCtrl', function ($scope, $http) {
    $scope.findInfo=function(obj){
        if(obj==null || obj==""){
            obj=$("#id").val();
        }
         window.location.href="/api/wx-service/find-by-id?patId="+obj+"&openId="+openId+"&mid="+$("#id").val();
    }
    $scope.userBangker = function () {
        $http.get("/api/pat-info/find-all?openId=" + openId)
            .success(function (data) {
//                console.log(data);
                if (data.length < 3) {
//                    alert(openId);
                    window.location.href = "/api/wx-service/query-open?openId=" + openId;
                } else{
                    $("#alert").attr("style", "display:block;");
                    $('#no2').click(function () {
                        $('#dialog1').hide();
                        $("#alert").attr("style", "display:none;");
                    });
                }
            })
    }

    $http.get("/api/hospital-staff-service/find-by-open-id?openId="+openId)
        .success(function(data){
            if(data.length>0){
                $("#staffName").html(data[0].name);
                $("#staffWelcome").attr("style", "display:block;");
                $("#staffJoin").attr("style", "display:none;");
            }else{
                $("#staffWelcome").attr("style", "display:none;");
                $("#staffJoin").attr("style", "display:block;");
            }
        })


    $http.get("/api/pat-info/find-all?openId=" + openId)
        .success(function (data) {
            $http.post("/api/pat-info/find-info-by-open-id?openId=" + openId)
                .success(function (node) {
                    nameId = node.id;
//                    console.log(node);
//                    alert(nameId);
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].id != node.id) {
                            var nameAll = {};
                            nameAll.id = data[i].id;
                            nameAll.name = data[i].name;
                            nameAll.cellphone = data[i].cellphone;
                            nameAll.sex = data[i].sex;
                            nameAll.idCard = data[i].idCard;
//                            console.log(nameAll);
                            nameList.push(nameAll);
                        }
                    }
//                    console.log(nameList);
//                    console.log(data);
                    $scope.name = nameList;
                })

        });
});