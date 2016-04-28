/**
 * Created by cxy on 2016/4/27.
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
    function GetQueryString(name) {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
    }
    var openId = getUrlParameter("openId");
    var openId2=GetQueryString("openId");
   alert(openId,openId2);
//$.ajax({
//    type:"POST",
//    cache:false,
//    url:"/api/pat-info/find-info-by-open-id?openId="+openId,
//    dataType:"JSON",
//    success:function(data){//patInfo Json
//        $("#name").html(data.name);
//        $("#idCard").html(data.idCard);
//        $("#phone").html(data.cellphone);
//    }
//});
app.controller('tableCtrl', function ($scope, $http) {
     $http.get("/api/pat-info/find-info-by-open-id?openId="+openId)
        .success(function (data) {
             $scope.names = data;
         });
});



//$(function(){
//     /**
//     * 加载我的信息
//     * from patInfo
//     */
//     $.ajax({
//        type:"POST",
//        cache:false,
//        url:"/api/pat-info/find-info-by-open-id?openId="+openId,
//        dataType:"JSON",
//        success:function(data){//patInfo Json
//            $("#name").html(data.name);
//            $("#idCard").html(data.idCard);
//            $("#phone").html(data.cellphone);
//        }
//     });
//
//
//
//
//})
/**
 * 绑定患者
 */
function userBangker(){
     window.location.href="/views/his/public/app-user-bangker.html?openId="+openId;
}

/**
 * 绑定员工
 */
function staffBangker(){
    window.location.href="/views/his/public/hospital-staff.html?openId="+openId;
}

