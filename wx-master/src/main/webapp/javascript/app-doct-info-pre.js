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
var deptId = GetQueryString("deptId");
var likeSearch = getUrlParameter("likeSearch");
var app = angular.module("myApp", []);
app.controller('tableCtrl', function ($scope, $http) {
   // 加入我的收藏
    $scope.collect=function(docId,clinicIndexId){
        $http.get("/api/doct-info/user-collect?docId=" + docId + "&openId=" + openId+"&clinicIndexId="+clinicIndexId)
            .success(function (data) {
                if(data.success==true){
//                    alert("收藏成功！");
//                    alert("deptId="+deptId);
                    $http.get("/api/clinic-for-regist/find-by-dept-id-pre-like?likeSearch=" + likeSearch + "&openId=" + openId+"&deptId="+deptId)
                        .success(function (data) {
                            if(data.length>0){
                                $("#text1").html(data[0].deptName + ":" + data.length + "人");
                            }
                            $scope.names = data;
                        });
                 }else{
                     $http.get("/api/doct-info/baddon-collection?openId=" + openId + "&doctId=" + docId)
                        .success(function (data) {//appDoctInfoVO
                            if (data != 0) {
//                                alert("取消成功！");
                                $http.get("/api/clinic-for-regist/find-by-dept-id-pre-like?likeSearch=" + likeSearch + "&openId=" + openId+"&deptId="+deptId)
                                    .success(function (data) {
                                        if(data.length>0){
                                            $("#text1").html(data[0].deptName + ":" + data.length + "人");
                                        }
                                        $scope.names = data;
                                    });
                            }
                        });
                }
            });
    }
    $scope.judgeIsEnabledRegist = function (rid, enabledCount, price, name, title, timeDesc, patName, deptName) {
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
    if(likeSearch!=null&&likeSearch!=""){
//        alert(likeSearch);
        $http.get("/api/clinic-for-regist/find-by-dept-id-pre-like?likeSearch=" + likeSearch + "&openId=" + openId+"&flag=pre")
            .success(function (data) {
                 $scope.names = data;
            });
     }else{
        $http.get("/api/clinic-for-regist/find-by-dept-id-pre?deptId=" + deptId + "&openId=" + openId)
            .success(function (data) {
                if (data.length > 0) {
//                alert(data.length);
                    $("#text1").html(data[0].deptName + ":" + data.length + "人");
                }
                $scope.names = data;
            });
    }

});
