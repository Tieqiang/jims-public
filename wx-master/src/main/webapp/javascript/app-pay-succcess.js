
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
var openId=GetQueryString("openId");//opemnId

function find(){
    window.location.href="/api/wx-service/regist-list?openId="+openId;
}






