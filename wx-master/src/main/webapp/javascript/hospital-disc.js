/**
 * Created by fyg on 2016/4/11.
 */
var app = angular.module('hos-inf', []);
app.controller('info',function($scope,$http){
    $http.get("/api/hospital-info/list-all")
        .success(function (response) {
//            console.log("描述:" + response[0].tranContent);
            $("#aa").html(response[0].tranContent);
        }
    );
});