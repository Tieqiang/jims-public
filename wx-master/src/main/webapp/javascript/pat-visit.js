/**
 * Created by wei on 2016/4/24.
 */

var app = angular.module("myApp", []);

app.controller('tableCtrl', function ($scope, $http) {
    $scope.getQueryString = function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)return unescape(r[2]);
        return null;
    };

//    $scope.patientId = $scope.getQueryString("patientId");
    $scope.openId = $scope.getQueryString("openId");

    $http.get("/api/pat-visit/list?openId=" +$scope.openId)
        .success(function (data) {
//            console.log(data);
//            $("#name").html(data[0].nextOfKin);
            $scope.patVisit = data;
        });
    $scope.click = function (patientId, visitId) {
        $("#head").hide();
        $http.get("/api/time/list?patientId=" + patientId + "&visitId=" + visitId)
            .success(function (data) {
                console.log(patientId);
                $("#patVisit").attr("style", "display:none");
                $("#inp").attr("style", "display:block");
                $("#fanhui").attr("style", "display:block");
                $('#fan').click(function () {
                    $("#head").show();
                    $("#patVisit").attr("style", "display:block");
                    $("#inp").attr("style", "display:none");
                });
                $scope.Inp = data;
            });
     }
});