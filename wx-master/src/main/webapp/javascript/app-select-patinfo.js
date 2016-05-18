 /**
 * 接收从app-doct-info.html页面传递来的参数
 * @param name
 * @returns {*}
 */
function getUrlParameter(name){
    name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
    var regexS = "[\\?&]"+name+"=([^&#]*)";
    var regex = new RegExp( regexS );
    var results = regex.exec(window.parent.location.href );
    if( results == null )    return "";  else {
        return results[1];
    }
}
var openId=getUrlParameter("openId");//opemnId
var price=getUrlParameter("price");//挂号价格
var clinicForRegistId=getUrlParameter("clinicForRegistId");//号表Id
  /**
  * 通过openId
  * 异步加载患者的信息
  * @type {*|module}
  */
var app = angular.module("myApp", []);
 app.controller('tableCtrl',function ($scope, $http) {
    $http.get("/api/pat-info/list?openId="+openId)
        .success(function (data) {
             $scope.names = data;});

});
 /**
 * 付款
 * @param deptId
 */
var findPatInfo=function findPatInfo(idCard){
//     var openId=$("#openId").val();
//    alert("price="+price);
//     alert("clinicForRegistId="+clinicForRegistId);
     if(idCard!=null&&idCard!=""){
         var flag=window.confirm("确定要为次绑定患者挂号吗？");
         if(flag){//确定要为次患者挂号
             // 付款页面  price
             window.location.href="/views/his/public/app-pay.html?price="+price+"&idCard="+idCard+"&clinicForRegistId="+clinicForRegistId+"&openId="+openId;
          }
     }else{
         alert("系统出现错误，暂时不能使用！");
     }
 };





//
//var loadDict = function (deptId) {
//    $.get("/api/dept-dict/find-by-id?deptId='"+deptId+"'", function (data) {
//           $("#deptname").html(data.deptName);
////        deptAlis  deptLocation  deptdesc
//        $("#deptAlis").html(data.deptAlis);
//        $("#deptLocation").html(data.deptLocation);
//        $("#deptdesc").html("    "+data.deptInfo);
//     });
//}

