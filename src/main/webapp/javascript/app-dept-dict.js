/**
 * Created by wei on 2016/4/6.
 */
$(function() {
    /**
     *
     */


})
function but(){
//    alert(1);
    $("#deptlist").show();
    $("#deptInfo").hide();
    $("#return").hide();
}
var app = angular.module("myApp", []);

app.controller('tableCtrl',function ($scope, $http) {
    $http.get("/api/dept-dict/list-all")
        .success(function (data) {
            $scope.names = data;});

});
var findDeptInfo=function findDeptInfo(deptId){
//    alert(deptId);
             $("#deptlist").hide();
             $("#deptInfo").show();
//             $("#return").show();
             $("#return").attr("style", "display:block;");
             loadDict(deptId);

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

