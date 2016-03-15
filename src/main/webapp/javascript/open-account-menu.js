/**
 * Created by Dt on 2016-03-03.
 */


var app = angular.module("myApp", []);

app.controller('customersCtrl', function ($scope, $http) {
    $http.get("/api/req-msg/list-all")
        .success(function (data) {  console.log(data);
            $scope.names = data;
        });
});

app.controller('formCtrl', function ($scope, $http) {
    $scope.submit = function () {
        $http.post("/api/req-msg/add", $scope.msg)
            .success(function (data) {
                alert("ok");
            });
    };
    $scope.submit();

});



