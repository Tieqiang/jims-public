/**
 * Created by admin on 2016/5/12.
 */
function getUrlParameter(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regexS = "[\\?&]" + name + "=([^&#]*)";
    var regex = new RegExp(regexS);
    var results = regex.exec(window.parent.location.href);
    if (results == null)    return ""; else {
        return results[1];
    }
}
var openId=getUrlParameter("openId");
var feedTargetId=getUrlParameter("feedTargetId");
var app = angular.module("myApp", []);

app.controller('tableCtrl', function ($scope, $http) {
    $http.get("/api/feed-back/find-content-by-name?feedTargetId="+feedTargetId)
        .success(function (data) {
            $("#question").html("--"+data.questionContent+"--");
            $scope.options = data.options;
        });
});
$(function(){

    $("#submit").on("click",function(){
        var obj=$("input[type='radio']:checked");
//        var value=obj.value();
        var value=obj.val();
//        alert(value+"value"+val+"val");//反馈内容
        if(value==null|| value==""){
            alert("请选择您的意见！");
        }else{
            window.location.href="/api/feed-back/submit-result?openId="+openId+"&feedTargetId="+feedTargetId+"&feedcBackResult="+value;
        }
    })
 })





