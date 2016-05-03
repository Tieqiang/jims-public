$(function (){
    //链接验证
    var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
        + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
        + "|" // 允许IP和DOMAIN（域名）
        + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
        + "[a-z]{2,6})" // first level domain- .com or .museum
        + "(:[0-9]{1,4})?" // 端口- :80
        + "((/?)|" // a slash isn't required if there is no file name
        + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
    var re=new RegExp(strRegex);

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

        var oEditor = CKEDITOR.instances.tranContent;
        var tranContent1 = oEditor.getData();
        var tranContent=stripscript(tranContent1);
        alert(tranContent);

        if(re.test(hospitalInfo.infoUrl)){
            $.postJSON("/api/hospital-info/merge?tranContent="+tranContent, hospitalInfo, function (data) {
                $.messager.alert("系统提示", "保存成功", "info");
                show();
            });
        }else{
            $.messager.alert("系统提示", "医院链接错误!!", "error");
        }
    })
})

function stripscript(s)
{
    var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】”“]")
    var rs = "";
    for (var i = 0; i < s.length; i++) {
        rs = rs+s.substr(i, 1).replace(pattern, '');
    }
    return rs;
}


