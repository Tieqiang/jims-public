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
    /**
     * 查看医生信息
     * @param id
     */
//    $scope.info2=function (rid){
//        　window.location.href="/api/wx-service/query-doct-info?id="+rid+"&openId="+openId;
//    }
      $scope.info2=function(rid){//
           window.location.href="/api/wx-service/query-doct-info?id="+rid+"&openId="+openId;
      }
  //    collectNO  取消收藏
    $scope.collectNO = function (doctId, clinicIndexId) {//
        $http.get("/api/doct-info/baddon-collection?openId=" + openId + "&doctId=" + doctId)
            .success(function (data) {//appDoctInfoVO
                if (data != 0) {
                    alert("取消成功！");
                    $http.get("/api/clinic-for-regist/find-my-collection?&openId=" + openId)
                        .success(function (data) {//appDoctInfoVO
                            $scope.names = data;
                        });
                }
            });
    }
    $scope.judgeIsEnabledRegist = function (rid, enabledCount, price, name, title, timeDesc, patName, deptName) {
//       alert("qqq"+rid);
        var clinicForRegistId = rid;
        if (enabledCount > 0) {
//                var openId = $("#openId").val();
            //选择其他患者
//                window.location.href="/views/his/public/app-select-patinfo.html?openId="+openId+"&price=" + price + "&clinicForRegistId=" + rid ;
//               //根据默认患者挂号
//             window.location.href="/views/his/public/app-pay.html?openId="+openId+"&price=" + price + "&clinicForRegistId=" + rid ;
            window.location.href = "/api/wx-service/app-pay?openId=" + openId + "&price=" + price + "&clinicForRegistId=" + rid
        } else {
//            alert("rid="+clinicForRegistId);
            //医生详情 clinicForRegistId
            window.location.href = "/api/wx-service/get-regist-id?clinicForRegistId=" + clinicForRegistId;
        }
    }
    $http.get("/api/clinic-for-regist/find-my-collection?openId=" + openId)
        .success(function (data) {//appDoctInfoVO
            if(data.length>0){
                $("#text1").html("共收藏"+data.length+"名医生");
            }
            $scope.names = data;
        });
});
