/**
 * Created by cxy on 2016/4/6.
 */
 var app = angular.module("myApp", []);
 app.controller('tableCtrl', function ($scope, $http) {
    $scope.findDeptInfo = function (deptId) {
        window.location.href = "/api/wx-service/query-dept?deptId=" + deptId;
    }
    $http.get("/api/dept-dict/list-all")
        .success(function (data) {
            $scope.names = data;
        });
 });


