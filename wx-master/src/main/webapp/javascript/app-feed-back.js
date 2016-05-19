/**
 * created by chenxy on 2016-04-17
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
var openId=getUrlParameter('openId');
$(function () {
    //就诊反馈
    $("#regist").on('click',function(){
        //1 查询是否有就诊记录
        $.ajax({
            type:"GET",
            url:"/api/rcpt-master/find-by-open-id?openId="+openId,
            dataType:"JSON",
            cache:false,
            success:function(data){
//                alert(data);

                if(data.length>0){//有就诊记录
                     window.location.href="/api/wx-service/find-master?openId="+openId;
                }else{
                    alert("您目前没有就诊记录！");
                }
            }
        });
     });
    //医院反馈
    $("#hospital").on('click',function(){
        window.location.href="/api/wx-service/find-content?openId="+openId+"&feedTargetId=123123123";
    });


    $("#doct").on('click',function(){
         $.ajax({
            type:"GET",
            url:"/api/rcpt-master/find-by-open-id?openId="+openId+"&doctFlag=1",
            dataType:"JSON",
            cache:false,
            success:function(data){
//                alert(data);
                if(data.length>0){//有医生记录
                    window.location.href="/api/wx-service/find-master?openId="+openId+"&doctFlag=1";
                }else{
                    alert("您目前没有就诊记录！");
                }
            }
        });
    });
 })






