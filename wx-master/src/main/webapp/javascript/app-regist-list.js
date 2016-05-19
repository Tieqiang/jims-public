/**
 * Created by admin on 2016/5/13.
 */
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return  unescape(r[2]);
    return null;
}
var openId = GetQueryString("openId");
var app = angular.module("myApp", []);
app.controller('tableCtrl', function ($scope, $http) {
     $http.get("/api/clinic-master/find-my-regist?openId=" + openId)
        .success(function (data) {//map ("today")  map("history)
             $scope.today = data.today;
//             console.info( $scope.today);
            $scope.history = data.history;
//             console.info($scope.history);
        });
});

function selectTag(showContent,selfObj){
    // 标签
    var tag = document.getElementById("tags").getElementsByTagName("li");
    var taglength = tag.length;
    for(i=0; i<taglength; i++){
        tag[i].className = "";
    }
    selfObj.parentNode.className = "selectTag";
    // 标签内容
    for(i=0; j=document.getElementById("tagContent"+i); i++){
        j.style.display = "none";
    }
    document.getElementById(showContent).style.display = "block";
}