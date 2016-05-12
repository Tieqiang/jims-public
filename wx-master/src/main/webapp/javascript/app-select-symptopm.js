/**
 * Created by cxy on 2016/5/9.
 */
//var ids="";
function getUrlParameter(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regexS = "[\\?&]" + name + "=([^&#]*)";
    var regex = new RegExp(regexS);
    var results = regex.exec(window.parent.location.href);
    if (results == null)    return ""; else {
        return results[1];
    }
}
var bodyId=getUrlParameter("bodyId");
var openId=getUrlParameter("openId");
var app = angular.module("myApp", []);
app.controller('tableCtrl', function ($scope, $http) {
//     $scope.haveSelected = function (symptomId) {
//          ids=ids+symptomId+",";
////          $("#haveSelected").html("<span style='color:red'>√</span>");
//     }
    $http.get("/api/intelligent-guide/find-body-name?bodyId="+bodyId)
        .success(function (data) {
            alert(data+"    data");
            $("#text1").html(data);
        });
    $http.get("/api/intelligent-guide/find-symptom-by-body?bodyId="+bodyId)
        .success(function (data) {
            $scope.names = data;
     });
});
/**
 *根据所选择疾病的ids 来确定可能得的疾病
 */
function result(){
    var ids="";
    var arr=$("input[type='checkbox']:checked");
    if(arr.length==0){
        alert("请选择至少一症状！");
    }else{
        for(var i=0;i<arr.length;i++){
            console.info(arr[i]);
            ids+=arr[i].value+",";
        }
    }
    if(ids==null || ids==""){
        alert("请选择至少一症状！");
    }else{
        ids=ids.substring(0,ids.length-1);//1，2，
        alert(ids);
//        window.location.href="/api/intelligent-guide/find-sickness-by-symptom?ids="+ids;
         window.location.href = "/api/wx-service/query-sickness?ids=" + ids+"&openId="+openId;
     }

}

