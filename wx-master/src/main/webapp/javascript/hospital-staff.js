/**
 * Created by wei on 2016/4/26.
 */
var app = angular.module("myApp", []);
var hospitalStaff = {};
app.controller('tableCtrl', function ($scope, $http) {
    $scope.getQueryString = function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)return unescape(r[2]);
        return null;
    };
    $scope.openId = $scope.getQueryString("openId");
    $scope.click = function (personId) {

        $http.get("/api/hospital-staff-service/find-by-personId?personId=" + personId+"&openId="+$scope.openId)
            .success(function (data) {

                if (data.success) {
                    $("#alert1").attr("style", "display:block");
                    $('#no1').click(function () {
                        $('#alert1').hide();
                    });
                    hospitalStaff.id = data[0].id;
                    hospitalStaff.name = data[0].name;
                    hospitalStaff.personId = data[0].personId;
                    hospitalStaff.openId = $scope.getQueryString("openId");
                 }else{
                    alert(data.message);
                }
             });
    }
    $('#ok').click(function () {
        $http.post("/api/hospital-staff-service/save", hospitalStaff)
            .success(function (data) {
                $('#alert1').hide();
                $("#app").attr("style", "display:none;");
                $("#msg").attr("style", "display:block;");
             });
    });
});