/**
 * created by chenxy on 2016-04-17
 */
$(function () {
    $("#showTooltips").on('click',function(){});
 })

var clickFun=function() {
    var patInfo = {};
    patInfo.name = $("#name").val();
    patInfo.idCard = $("#idCard").val();
    patInfo.cellphone = $("#cellphone").val();
    alert(patInfo.name);
//    /api/dept-dict/list-all
    $.postJSON("/api/pat-info/save", patInfo, function (data) {
        console.info(patInfo);
    },function(data,status){
    })
}