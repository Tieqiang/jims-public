$(function () {

})
function getUrlParameter(name){
    name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
    var regexS = "[\\?&]"+name+"=([^&#]*)";
    var regex = new RegExp( regexS );
    var results = regex.exec(window.parent.location.href );
    if( results == null )    return "";  else {
        return results[1];
    }
}
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
var openId = GetQueryString("openId");
var deptId = GetQueryString("deptId");
//var deptId = GetQueryString("price");
var app = angular.module("myApp", []);
app.controller('tableCtrl', function ($scope, $http) {
      $scope.judgeIsEnabledRegist = function (rid, enabledCount, price,name,title,timeDesc,patName,deptName) {
         if (enabledCount > 0) {
//                var openId = $("#openId").val();
                  //选择其他患者
//                window.location.href="/views/his/public/app-select-patinfo.html?openId="+openId+"&price=" + price + "&clinicForRegistId=" + rid ;
//               //根据默认患者挂号
//             window.location.href="/views/his/public/app-pay.html?openId="+openId+"&price=" + price + "&clinicForRegistId=" + rid ;
             window.location.href="/api/wx-service/app-pay?openId="+openId+"&price=" + price + "&clinicForRegistId=" + rid
         } else {
            alert("此号已满，不能再挂号！");
        }
    }
     $http.get("/api/clinic-for-regist/find-by-dept-id?deptId="+deptId+"&openId="+openId)
        .success(function (data) {
            if (data.length > 0) {
                alert(data.length);
                 $("#text1").attr("text", data[0].deptName + ":" + data.length + "人");
             }
            $scope.names = data;
         });
});
