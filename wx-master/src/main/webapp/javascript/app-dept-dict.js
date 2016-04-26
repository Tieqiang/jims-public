/**
 * Created by cxy on 2016/4/6.
 */
$(function() {
    /**
     *
     */


})
//function but(){
////    alert(1);
//    $("#deptlist").show();
//    $("#deptInfo").hide();
//    $("#return").hide();
//}
var app = angular.module("myApp", []);

app.controller('tableCtrl',function ($scope, $http) {
    $http.get("/api/dept-dict/list-all")
        .success(function (data) {
//            alert(data);
            $scope.names = data;});

});
function getUrlParameter(name){
    name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
    var regexS = "[\\?&]"+name+"=([^&#]*)";
    var regex = new RegExp( regexS );
    var results = regex.exec(window.parent.location.href );
    if( results == null )    return "";  else {
        return results[1];
    }
}
var openId=getUrlParameter("param");
//alert("openId="+openId);
$("#openId").val(openId);
/**
 * 查找科室下面医生的信息
 * @param deptId
 */
var findDeptInfo=function findDeptInfo(deptId){
    var openId=$("#openId").val();
//    alert("openId="+openId);
     window.location.href="/views/his/public/app-doct-info.html?deptId="+deptId+"&openId="+openId;

};

var loadDict = function (deptId) {
    $.get("/api/dept-dict/find-by-id?deptId='"+deptId+"'", function (data) {
           $("#deptname").html(data.deptName);
//        deptAlis  deptLocation  deptdesc
        $("#deptAlis").html(data.deptAlis);
        $("#deptLocation").html(data.deptLocation);
        $("#deptdesc").html("    "+data.deptInfo);
     });
}

