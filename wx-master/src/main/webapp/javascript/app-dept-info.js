/**
 * Created by cxy on 2016/5/2.
 */
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
var deptId=GetQueryString("deptId");
$(function(){
    loadDict(deptId);
})
var loadDict = function (deptId) {
     $.get("/api/dept-dict/find-by-id?deptId=" + deptId, function (data) {
        $("#deptname").html(data.deptName);
        $("#deptAlis").html(data.deptAlis);
        $("#deptLocation").html(data.deptLocation);
        $("#deptdesc").html("   "+data.tranDeptInfo);
    });
}
