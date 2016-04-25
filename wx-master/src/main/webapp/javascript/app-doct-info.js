$(function () {

})
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return  unescape(r[2]);
    return null;
}
var v = GetQueryString('deptId');
//alert(v);
$("#deptId").val([v]);
var deptId = $("#deptId").val();
//alert("deptId="+deptId)
var app = angular.module("myApp", []);

function getUrlParameter(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regexS = "[\\?&]" + name + "=([^&#]*)";
    var regex = new RegExp(regexS);
    var results = regex.exec(window.parent.location.href);
    if (results == null)    return ""; else {
        return results[1];
    }
}
var openId = getUrlParameter("openId");
//alert("openId"+openId);
$("#openId").val(openId);
app.controller('tableCtrl', function ($scope, $http) {
    $scope.judgeIsEnabledRegist = function (rid, enabledCount, price) {
//        alert("rid="+rid);
        if (enabledCount > 0) {
            //可以挂号
            var flag = window.confirm("确定要挂号吗?(此号的价格为:" + price + ")");
            if (flag) {//要挂号
                var openId = $("#openId").val();
//              alert("openId="+openId+"price="+price);
                window.location.href = "/api/clinic-for-regist/regist?price=" + price + "&clinicForRegistId=" + rid + "&openId=" + openId;
            }
        } else {
            alert("此号已满，不能再挂号！");
        }
    }
    $http.get("/api/clinic-for-regist/find-by-dept-id?deptId=" + v)
        .success(function (data) {
            if (data.length > 0) {
//                 alert(data[0].rid);
                $("#text1").attr("placeholder", data[0].deptName + ":" + data.length + "人");
            }
            $scope.names = data;
            console.log($scope.names);
        });
});
/**
 * 判断是否可以挂号
 */


//function judgeIsEnabledRegist(rid,enabledCount,price){
//
//    if(enabledCount>0){
//        //可以挂号
//        var flag= window.confirm("此号的价格为:"+price+"确定要挂号吗?");
//        if(flag){//要挂号
//            var openId=$("#openId").val();
//              alert("openId="+openId+"price="+price);
//              window.location.href="/api/clinic-for-regist/regist?price="+price+"&clinicForRegistId="+id+"&openId="+openId;
//        }
//    }else{
//        alert("此号已满，不能再挂号！");
//    }
//
//}