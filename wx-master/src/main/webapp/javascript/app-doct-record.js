var app = angular.module("myApp", []);

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
}
var openId = GetQueryString("openId");
app.controller('tableCtrl', function ($scope, $http) {
    $http.get("/api/rcpt-master/find-by-open-id?openId=" + openId+"&doctFlag=1")
        .success(function (data) {
             $scope.nameList = data;
        });
     // 科室名称
     $scope.clickName = function (name) {
//         alert(name)
            window.location.href="/api/wx-service/find-content?feedTargetId=3452341234&openId="+openId;
     }






})