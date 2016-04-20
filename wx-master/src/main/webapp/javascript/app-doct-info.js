$(function(){

 })
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
var v=GetQueryString('deptId');
//alert(v);
$("#deptId").val([v]);
var deptId=$("#deptId").val();
//alert("deptId="+deptId)
var app = angular.module("myApp", []);
app.controller('tableCtrl',function ($scope, $http) {
    $http.get("/api/clinic-for-regist/find-by-dept-id?deptId="+v)
        .success(function (data) {
            $scope.names = data;});
});