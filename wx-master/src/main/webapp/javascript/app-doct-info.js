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
var v = getUrlParameter('deptId');
//alert(v);
$("#deptId").val(v);
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
alert("openId"+openId);
$("#openId").val(openId);
app.controller('tableCtrl', function ($scope, $http) {
// (x.rid,x.enabledNum,x.price,x.name,x.title,,x.timeDesc,x.enabledNum,x.patName,x.deptName)">

    $scope.judgeIsEnabledRegist = function (rid, enabledCount, price,name,title,timeDesc,patName,deptName) {
//        alert("rid="+rid);
        alert("1111111111111111");
        if (enabledCount > 0) {
            //可以挂号
//            var flag = window.confirm("确定要挂号吗?(此号的价格为:" + price + ")");
//            if (flag) {//要挂号
                var openId = $("#openId").val();
//              alert("openId="+openId+"price="+price);
                /**
                 * 如果确定要挂号，跳转到选择患者页面
                 * @type {string}
                 */
                //选择其他患者
//                window.location.href="/views/his/public/app-select-patinfo.html?openId="+openId+"&price=" + price + "&clinicForRegistId=" + rid ;
               //根据默认患者挂号
            window.location.href="/views/his/public/app-pay.html?price="+price+"&clinicForRegistId="+rid+"&openId="+openId+"&name="+name+"&title="+title+"&enabledCount="+enabledCount+"&patName="+patName+"&timeDesc="+timeDesc+"&deptName="+deptName;
//            }
        } else {
            alert("此号已满，不能再挂号！");
        }
    }
//    alert($("#openId").val());
    $http.get("/api/clinic-for-regist/find-by-dept-id?deptId=" + v+"&openId="+$("#openId").val())
        .success(function (data) {
            if (data.length > 0) {
//                 alert(data[0].rid);
                $("#text1").attr("html", data[0].deptName + ":" + data.length + "人");
             }
            $scope.names = data;
//            console.log($scope.names);
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