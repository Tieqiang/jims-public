/**
 * Created by admin on 2016/4/29.
 */
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
var clinicForRegistId=GetQueryString("clinicForRegistId");//号表Id
$(function(){
    $.ajax({
        type:"POST",
        cache:false,
        url:"/api/clinic-for-regist/find-doct-regist?clinicForRegistId="+clinicForRegistId,
        dataType:"JSON",
        success:function(data){//appDoctInfoVo;
            $("#headUrl").attr("src",data.headUrl);
            $("#name").text(data.name);
            $("#title").text(data.title);
            $("#deptName").text(data.deptName);
            $("#scheduleTime").text(data.scheduleTime);
            $("#description").text(data.description);
        }
    });


})