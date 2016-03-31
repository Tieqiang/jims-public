$(function (){
    var show = function () {
        $.get("/api/hospital-info/list-all", function (data){
            if(data[0]!=null) {
                $("#id").textbox('setValue', data[0].id);
                $("#hospitalId").textbox('setValue', data[0].hospitalId);
                $("#appId").textbox('setValue', data[0].appId);
                $("#infoUrl").textbox('setValue', data[0].infoUrl);
                $("#tranContent").val(data[0].tranContent);
            }
        })
    }
    show();
    $("#noBtn").on('click',function(){
        show();
    })

    $("#saveBtn").on('click',function(){
        var hospitalInfo = {};
        hospitalInfo.id = $("#id").val();
        hospitalInfo.hospitalId = $("#hospitalId").val();
        hospitalInfo.appId = $("#appId").val();
        hospitalInfo.infoUrl = $("#infoUrl").val();
        var tranContent = $("#tranContent").val();
        $.postJSON("/api/hospital-info/merge?tranContent="+tranContent, hospitalInfo, function (data) {
            $.messager.alert("系统提示", "保存成功", "info");
            show();
        });
    })
})