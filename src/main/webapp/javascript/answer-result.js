var app = angular.module("answerResultList", []);
app.controller('controllerList', function ($scope, $http) {
    $http.get("/api/answer-result/list-all")
        .success(function (data) {
            console.log(data);
            $scope.names = data;
        });
    $scope.addAnswerResult= function () {
        $http.post("/api/answer-result/add", $scope.answerResult)
            .success(function (data) {
                $http.get("/api/answer-result/list-all")
                    .success(function (data) {
                        console.log(data);
                        $scope.names = data;
                    });
            });
    };
    $scope.addAnswerResult();
});


