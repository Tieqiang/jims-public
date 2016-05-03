/**
 * Created by cxy on 2016/4/6.
 */
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
var app = angular.module("myApp", []);
app.controller('tableCtrl',function ($scope, $http) {
    $scope.findDeptInfo=function (deptId){
//         window.location.href="/views/his/public/app-doct-info.html?deptId="+deptId+"&openId="+openId;
        window.location.href="/api/wx-service/query-string?deptId="+deptId+"&openId="+openId;
     };
    $http.get("/api/dept-dict/list-all")
        .success(function (data) {
//            alert(data.img);
             $scope.names = data;});

});
  var loadDict = function (deptId) {
    $.get("/api/dept-dict/find-by-id?deptId='"+deptId+"'", function (data) {
         $("#deptname").html(data.deptName);
         $("#deptAlis").html(data.deptAlis);
         $("#deptLocation").html(data.deptLocation);
        $("#deptdesc").html("    "+data.deptInfo);
     });
}

