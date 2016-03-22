/**
 * Created by wei on 2016/3/16.
 */
var app = angular.module("myApp", []);
app.controller("buttonCtrl",function($scope, $http){


});

app.controller('tableCtrl',function ($scope, $http) {

    $scope.check=function(questionContent){
        console.log($scope.contextSearch);
        $http.get("/api/subject/find-by-questionContent?questionContent="+$scope.contextSearch)
            .success(function (data) {
                $scope.names = data;
            })
    }





    $scope.delMulti=function(){

        $scope.ids='1465565656;3265655676';

        $http.post("/api/subject/del-subjects?ids="+$scope.ids)
            .success(function (data) {

                alert("delete成功！");
            })
    }
    $http.get("/api/subject/list-sub")
        .success(function (data) {  console.log(data);
            $scope.names = data;
        });

          $scope.get= function(id){
                  $http.get("/api/subject/find-by-id?id="+id)
                      .success(function (data) {  console.log(data);
                          console.log(data.options);
                          $scope.name = data;
                          $scope.options = data.options;
                      });
    }

    $scope.delOption=function(id){
        var index=-1;
        angular.forEach($scope.options,function(x,key){
            if(x.id===id){
                index=key;
            }
        });
        if(index !==-1){
            $scope.options.splice(index,1);

        }
    }

    $scope.updateSubject=function(id){

        console.log($scope.name.options);
        console.log($scope.name);
        $http.post("/api/subject/save-sub", $scope.name)
            .success(function (data) {

                alert("update成功！");
            })
    }
});
app.controller('formCtrl', function ($scope, $http) {
    $scope.save = function () {
        //获取到表单是否验证通过
        if ($scope.form.$valid) {
            $http.post("/api/subject/save-sub", $scope.sub)
                .success(function (data) {

                    alert("增加成功！");
                })
        } else {
            alert("表单没有通过验证");
        }
    }




});

