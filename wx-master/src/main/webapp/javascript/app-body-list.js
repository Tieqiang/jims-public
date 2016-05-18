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
var sexValue=getUrlParameter("sexValue");

var openId=getUrlParameter("openId");
var app = angular.module("myApp", []);
app.controller('tableCtrl',function ($scope, $http) {
    $scope.findSysptomInfo=function (bodyId){
//        alert(sexValue);
        window.location.href="/api/wx-service/query-symptom?bodyId="+bodyId+"&openId="+openId+"&sexValue="+sexValue;
    };
    $http.get("/api/intelligent-guide/body-list")
        .success(function (data) {
             $scope.names = data;});
    //模糊查询科室和医生
    $scope.finddeptOrDoctInfo=function($scope,$http){
        var likeSearch=$("#likeSearch").val();
        if(likeSearch==null ||likeSearch==""){
            alert("请输入要查询的科室或者是医生！");
        }else{
//            alert("likeSearch="+likeSearch);
            $http.get("/api/dept-dict/query-like?likeSearch="+likeSearch)
                .success(function (data) {
                    /**
                     *   map.put("list",deptDicts);
                     map.put("what","deptDict");
                     return map;
                     }else{//没有查到科室
                        doctInfos=doctInfoFacade.queryLike(likeSearch);
                        map.put("list",doctInfos);
                        map.put("what","doctInfo");
                     */
                    if(data.list.length>0){//查询到科室或者是医生
                        if("doctInfo"==data.what){//医生

                        }else{//科室
                            $scope.names = data.list;
                        }
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

