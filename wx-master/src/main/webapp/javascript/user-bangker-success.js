/**
 * Created by cxy on 2016/4/27.
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
var patId=getUrlParameter("patId");
var openId=getUrlParameter("openId");

var flag=getUrlParameter("flag");
 $(function(){
    if(flag!=null&&flag!=""){
        $("#ckxq").hide();
    }
})
/**
 * 查看详情
 */
function view(){
//    if(){}
   window.location.href="/api/wx-service/get-param?patId="+patId+"&openId="+openId;
}


