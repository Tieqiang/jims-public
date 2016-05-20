 /**
 * 接收
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
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
var openId=GetQueryString("openId");//opemnId
var price=getUrlParameter("price");//挂号价格
var prepareId=GetQueryString("prepareId");//订单号
var clinicForRegistId=getUrlParameter("clinicForRegistId");//号表Id
$(function(){
    $.ajax({
        type:"POST",
        cache:false,
        url:"/api/clinic-for-regist/find-doct-regist?openId="+openId+"&clinicForRegistId="+clinicForRegistId,
        dataType:"JSON",
        success:function(data){//appDoctInfoVo;
            $("#headUrl").attr("src",data.headUrl);
            $("#name").text(data.name);
            $("#title").text(data.title);
            $("#deptName").text(data.deptName);
            $("#deptAddr").text(data.deptAddr);
            $("#timeDesc").text(data.timeDesc);
            $("#price").text(price+"￥");//patName
            $("#patName").text(data.patName);//enabledCount
            $("#registTime").text(data.registTime);
            $("#prepareId").html(prepareId);
        }
    });


});

 function myregist(){
      window.location.href="/api/wx-service/regist-list?openId="+openId;
 }





