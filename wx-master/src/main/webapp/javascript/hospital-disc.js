/**
 * Created by cxy on 2016/4/11
 */
var app = angular.module('hos-inf', []);
app.controller('info',function($scope,$http){
    $http.get("/api/hospital-info/list-all")
        .success(function (result) {
             $("#aa").html("   "+result[0].tranContent);
             $("#hospitalImg").attr("src",result[0].hospitalImg);
        }
    );
});