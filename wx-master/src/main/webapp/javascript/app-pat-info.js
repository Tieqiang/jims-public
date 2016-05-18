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
var mid=getUrlParameter("mid");//默认用户patId
$(function(){
     var patId=$("#patId").val()
     if(mid==patId){
        $("#moren").hide();
     }
     if(patId==null || patId==""){
        alert("暂时不能查看详情");
      }else{
        $.ajax({
            url:"/api/pat-info/view?patId="+patId,
            type:"POST",
            dataType:"JSON",
            cache:false,
            success:function(data){
//                alert(data.idCard);
                $("#patId").val(data.id);
                $("#name").val(data.name);
                $("#idCard").val(data.idCard);
                $("#phone").val(data.cellphone);
             }
        });
     }
  })
/*
* 修改默认用户
 */
function moren(){
    var patId=$("#patId").val();//patId
    //将appUser 表中的patid 设为这个
    if(openId==null || openId==""){
        alert("暂时不能修改默认用户!");
    }else{
        window.location.href="/api/pat-info/update-pat-id?patId="+patId+"&openId="+openId;
    }
 }
function update(){
//alert(1);
    var patId=$("#patId").val();//patId
    var name = $("#name").val();
    var idCard = $("#idCard").val();
    var cellphone = $("#phone").val();
//    var openId=v;
    if(name.trim()=="" || name.trim()==null){
        alert("姓名不能为空！");
    }else if(idCard.trim()=="" || idCard.trim()==null){
        alert("省份证不能为空！");
    }else if(cellphone.trim()=="" || cellphone.trim()==null){
        alert("手机号不能为空！");
    }else{
        window.location.href="/api/pat-info/save?name="+name+"&idCard="+idCard+"&cellphone="+cellphone+"&patId="+patId;
    }
 }
function del(){
    var patId=$("#patId").val();//patId
    if(patId==mid){
        $("#alert").attr("style", "display:block;");
        $('#no2').click(function () {
            $('#dialog1').hide();
            $("#alert").attr("style", "display:none;");
        });
    }else{
        $.ajax({
            type:"POST",
            url:"/api/pat-info/delete?patId="+patId,
            dataType:"JSON",
            cache:false,
            success:function(data){
                window.location.href="/views/his/public/user-bangker-success.html";
            },
            error:function(data){
                window.location.href="/views/his/public/user-bangker-failed.html";
            }
        });
    }
 }
/**
 * 挂号
 */
function prepare(){
//
    window.location.href="/api/wx-service/find-dept-pre?openId="+openId;
}
function today(){
    window.location.href="/api/wx-service/find-dept?openId="+openId;
}





