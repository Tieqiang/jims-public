/**
 * Created by cxy on 2016/5/10.
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

//所选择症状ids
var ids=getUrlParameter("ids");
var openId=getUrlParameter("openId");
var app = angular.module("myApp", []);
app.controller('tableCtrl', function ($scope, $http) {

     $http.get("/api/intelligent-guide/find-sickness-by-symptom?ids="+ids)
        .success(function (data) {
            $scope.names = data;
     });
});

//
//$(function(){
//    /**
//     * 今天挂号
//     */
//    $("#today").on("click",function(){
////        alert(1);
//            window.location.href="/api/wx-service/find-dept?openId="+openId;
//    });
//    $("#prepare").on("click",function(){
//
//    })
//})

function prepare(){
    window.location.href="/api/wx-service/find-dept-pre?openId="+openId;
}

function today(){
    window.location.href="/api/wx-service/find-dept?openId="+openId;
}
