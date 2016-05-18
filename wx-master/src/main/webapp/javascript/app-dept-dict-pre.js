/**
 * Created by cxy on 2016/4/6.
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
var openId=getUrlParameter("param");
var app = angular.module("myApp", []);
app.controller('tableCtrl',function ($scope, $http) {
    $scope.findDeptInfo=function (deptId){
//         window.location.href="/views/his/public/app-doct-info.html?deptId="+deptId+"&openId="+openId;
        window.location.href="/api/wx-service/query-string-pre?deptId="+deptId+"&openId="+openId;
     };
    $http.get("/api/dept-dict/list-all")
        .success(function (data) {
//            alert(data.img);
             $scope.names = data;});
    //模糊查询科室和医生
     $scope.likeSearch=function(){
        var likeSearch=$("#likeSearch").val();
        if(likeSearch==null ||likeSearch==""){
             alert("请输入要查询的科室或者是医生！");
         }else{
//            alert("likeSearch="+likeSearch);
            $http.get("/api/dept-dict/query-like?likeSearch="+likeSearch)
                .success(function (data) {
                    if(data.length>0){
                        $scope.names = data;
                    }else{
//                        alert("没有相关结果！");
                          window.location.href="/api/wx-service/query-doct-like?likeSearch="+likeSearch+"&openId="+openId+"&flag=pre";
                    }
                  }//success
            );//function
        }
     }
 });
  var loadDict = function (deptId) {
    $.get("/api/dept-dict/find-by-id?deptId='"+deptId+"'", function (data) {
         $("#deptname").html(data.deptName);
         $("#deptAlis").html(data.deptAlis);
         $("#deptLocation").html(data.deptLocation);
         $("#deptdesc").html("    "+data.deptInfo);
     });
}

function findMyCollection(){
    window.location.href="/api/wx-service/find-my-collection?openId="+openId;
}

