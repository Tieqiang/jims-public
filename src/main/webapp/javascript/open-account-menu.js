/**
 * Created by Dt on 2016-03-03.
 */


var app = angular.module("myApp", []);

app.controller('customersCtrl', ["$scope","$http",function ($scope, $http) {
    $http.get("/api/req-msg/list-all")
        .success(function (data) {  console.log(data);
            $scope.names = data;
        });
}]);

$(".replyArea").hide();
$(".collect").tooltip({
    title: "收藏",
    trigger: 'hover',
    placement: 'bottom'
});
$(".reply").tooltip({
    title: "快捷回复",
    trigger: 'hover',
    placement: 'bottom'
});


$(".collect").click(function () {
    $(this).toggleClass("selected");

});

$(".cancel").click(function () {
    var index = $(this).attr("index");
    $(this).parents("#replyArea" + index).hide();
});

$(".reply").click(function () {
    var index = $(this).attr("index");
    $("#replyArea" + index).toggle();
});


