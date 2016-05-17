$(function () {

})
function getUrlParameter(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regexS = "[\\?&]" + name + "=([^&#]*)";
    var regex = new RegExp(regexS);
    var results = regex.exec(window.parent.location.href);
    if (results == null)    return ""; else {
        return results[1];
    }
}
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return  unescape(r[2]);
    return null;
}
var openId = GetQueryString("openId");

var app = angular.module("myApp", []);
app.controller('tableCtrl', function ($scope, $http) {
    $scope.judgeIsEnabledRegist = function (rid, enabledCount, price, name, title, timeDesc, patName, deptName) {
//       alert("qqq"+rid);
        var clinicForRegistId =rid;
        if (enabledCount > 0) {
//                var openId = $("#openId").val();
            //选择其他患者
//                window.location.href="/views/his/public/app-select-patinfo.html?openId="+openId+"&price=" + price + "&clinicForRegistId=" + rid ;
//               //根据默认患者挂号
//             window.location.href="/views/his/public/app-pay.html?openId="+openId+"&price=" + price + "&clinicForRegistId=" + rid ;
            window.location.href = "/api/wx-service/app-pay?openId=" + openId + "&price=" + price + "&clinicForRegistId=" + rid
        }else {
//            alert("rid="+clinicForRegistId);
            //医生详情 clinicForRegistId
            window.location.href="/api/wx-service/get-regist-id?clinicForRegistId="+clinicForRegistId;
        }
    }
      $http.get("/api/clinic-for-regist/find-my-collection?find-my-collection&openId=" + openId)
        .success(function (data) {//appDoctInfoVO
             $scope.names = data;
        });
});
