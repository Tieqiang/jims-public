/**
 * created by chenxy on 2016-04-17
 */
$(function () {
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
var v=getUrlParameter('param');
//alert("openId="+v);
$("#openId").val(v);
var clickFun=function() {
//    var patInfo = {};
    var name = $("#name").val();
    var idCard = $("#idCard").val();
    var cellphone = $("#cellphone").val();
    var openId=v;
    if(name.trim()=="" || name.trim()==null){
        alert("姓名不能为空！");
    }else if(idCard.trim()=="" || idCard.trim()==null){
        alert("省份证不能为空！");
    }else if(cellphone.trim()=="" || cellphone.trim()==null){
        alert("手机号不能为空！");
    }else if(openId.trim()==""|| openId.trim()==null ){
        alert("暂时不能绑卡！");
    }else{
         window.location.href="/api/pat-info/save?name="+name+"&idCard="+idCard+"&cellphone="+cellphone+"&openId="+openId;
     }
 }
//
//function checkIsExist(){
//    var idCard=$("#idCard").val();
//    $.ajax({
//        type:"POST",
//        url:"/api/pat-info/check-idCard?idCard="+idCard,
//        cache:false,
//        dataType:"JSON",
//        success:function(result){
//
//        }
//     });
//
//
//}

