/**
 * Created by heren on 2016/4/14.
 */

var openAccountSetApp= angular.module("openAccountSetApp",[]) ;

var openAccountSetCtrl = openAccountSetApp.controller('openAccountSetCtrl',['$scope','$http',function($scope,$http){
    $scope.appInfo = {} ;

    $scope.save = function(){
        $http.post("/api/hospital-info/save",$scope.appInfo).success(function(data){
            alert("保存成功") ;
        })
    }

    $scope.init=function(){
        $http.get("/api/hospital-info/get").success(function(data){
            $scope.appInfo = data ;
        })
    }() ;

}]) ;