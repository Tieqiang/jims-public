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
var likeSearch = getUrlParameter("likeSearch");
var deptId = GetQueryString("deptId");
//var deptId = GetQueryString("price");
var app = angular.module("myApp", []);
app.controller('tableCtrl', function ($scope, $http) {
    // 加入我的收藏
    $scope.collect=function(docId,clinicIndexId){
        $http.get("/api/doct-info/user-collect?docId=" + docId + "&openId=" + openId+"&clinicIndexId="+clinicIndexId)
            .success(function (data) {
                if(data.success){
                    alert("收藏成功！");
                    $http.get("/api/clinic-for-regist/find-by-dept-id?deptId=" + deptId + "&openId=" + openId)
                        .success(function (data) {
                            if (data.length > 0) {
//                alert(data.length);
                                $("#text1").html(data[0].deptName + ":" + data.length + "人");
                            }
//                console.info(data);
                            $scope.names = data;
                        });
                 }else{
//                    alert("收藏失败！");  取消收藏
                    $http.get("/api/doct-info/baddon-collection?openId=" + openId + "&doctId=" + docId)
                        .success(function (data) {//appDoctInfoVO
                            if (data != 0) {
                                alert("取消成功！");
//                                $http.get("/api/clinic-for-regist/find-my-collection?&openId=" + openId)
//                                    .success(function (data) {//appDoctInfoVO
//                                        $scope.names = data;
//                                    });
                                $http.get("/api/clinic-for-regist/find-by-dept-id?deptId=" + deptId + "&openId=" + openId)
                                    .success(function (data) {
                                        if (data.length > 0) {
//                alert(data.length);
                                            $("#text1").html(data[0].deptName + ":" + data.length + "人");
                                        }
//                console.info(data);
                                        $scope.names = data;
                                    });
                            }
                        });
                }
            });
    }
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
    if(likeSearch!=null&&likeSearch!=""){//模糊查询
        $http.get("/api/clinic-for-regist/find-by-dept-id-like?likeSearch=" + likeSearch + "&openId=" + openId)
            .success(function (data) {
                $scope.names = data;
            });

    }else{
        $http.get("/api/clinic-for-regist/find-by-dept-id?deptId=" + deptId + "&openId=" + openId)
            .success(function (data) {
                if (data.length > 0) {
//                alert(data.length);
                    $("#text1").html(data[0].deptName + ":" + data.length + "人");
                }
//                console.info(data);
                $scope.names = data;
            });
    }

});
