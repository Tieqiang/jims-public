/**
 * Created by fyg on 2016/4/11.
 */
var app = angular.module('hos-inf', []);
app.controller('info',function($scope,$http){
    $http.get("/api/hospital-info/list-all")
        .success(function (response) {
            console.log(response);
            $scope.HospitalInfo = response;
        }
    );
});