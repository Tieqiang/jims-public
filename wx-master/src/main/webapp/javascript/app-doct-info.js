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
///**
// * 获取当前日期
// */
//var  getCurrentDate=function(){
//
//}
app.controller('tableCtrl',function ($scope, $http) {
    $http.get("/api/clinic-for-regist/find-by-dept-id?deptId="+v)
        .success(function (data) {
//            alert(data.length);
            if(data.length>0){
//                placeholder
                $("#text1").attr("placeholder",data[0].deptName+":"+data.length+"人");
            }
            $scope.names = data;});
});