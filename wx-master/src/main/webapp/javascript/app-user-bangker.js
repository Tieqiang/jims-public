/**
 * created by chenxy on 2016-04-17
 */
$(function () {
//  /*  wx.config({
//        debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
//        appId: 'wxf54cc9ce69e8c277', // 必填，公众号的唯一标识
//        timestamp:132456 , // 必填，生成签名的时间戳
//        nonceStr: 'adf231das', // 必填，生成签名的随机串
//        signature: '',// 必填，签名，见附录1
//        jsApiList: [] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
//    });*/

    $("#showTooltips").on('click',function(){});

 })
function getUrlParameter(name){
    name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
    var regexS = "[\\?&]"+name+"=([^&#]*)";
    var regex = new RegExp( regexS );
    var results = regex.exec(window.parent.location.href );
    if( results == null )    return "";  else {
        return results[1];
    }
}
//    function GetQueryString(name) {
//        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
//        var r = window.location.search.substr(1).match(reg);
//        if(r!=null)return  unescape(r[2]); return null;
//    }
var v=getUrlParameter('param');
alert("openId="+v);
$("#openId").val(v);
var clickFun=function() {
//    var patInfo = {};
    var name = $("#name").val();
    var idCard = $("#idCard").val();
    var cellphone = $("#cellphone").val();
    var openId=$("#openId").val();
//    patInfo.name = $("#name").val();
//    patInfo.idCard = $("#idCard").val();
//    patInfo.cellphone = $("#cellphone").val();
//   var openId = $("#openId").val();
    window.location.href="/api/pat-info/save?name="+name+"&idCard="+idCard+"&cellphone="+cellphone+"&openId="+openId;
//    $.postJSON("/api/pat-info/save?openId="+openId, patInfo, function (data) {
//        alert("success!");
//    }, function (data, status) {
//    })
}

