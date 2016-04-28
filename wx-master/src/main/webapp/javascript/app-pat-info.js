/**
 * Created by cxy on 2016/4/27.
 */
$(function(){
    $.postJSON("/api/pat-info/view?patId="+patId,function (data) {
        $("#name").html(data.name);
        $("#idCard").html(data.idcard);
        $("#phone").html(data.cellphone);
    }, function (data, status) {
    })
//    $.ajax({
//        type:"POST",
//        url:"/api/pat-info/view?patId="+patId,
//        cache:false,
//        dataType:"json",
//        success:function(data){
//            $("#name").html(data.name);
//            $("#idCard").html(data.idcard);
//            $("#phone").html(data.cellphone);
//        }
//    });
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

var patId=getUrlParameter("patId");



