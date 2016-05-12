$(function () {
    $("#uploadBtn").on('click',function(){
//        alert(1);
        var fileToUpload=document.getElementById("fileToUpload");
        var suffer=fileToUpload.value.substring(fileToUpload.value.indexOf(".")+1);
        if(suffer!="jpg"&&suffer!="png"&&suffer!="gif"&&suffer!="jpeg"&&suffer!="bmp"&&suffer!="swf"){
            $.messager.alert("系统提示", "请选择正确格式的图片","error");
        }else{
            ajaxUpload();
        }
    });
    /**
     * ajax 上传
     * */
    var ajaxUpload=function ajaxUpload(){
        $.ajaxFileUpload({
            url : '/img-upload-servlet',
            secureuri : false,
            fileElementId : 'fileToUpload',
            dataType : 'json',
//            data : {username : $("#username").val()},
            success: function(data, status) {
                $('#uploadSpan').append("<span><font color='red'> 上传成功 ✔</font></span>");
//                 $('#headUrl').val("");
//                alert(data.picUrl);
                $('#hospitalImg').val(data.picUrl);
                $("#viewImg").attr("src",data.picUrl);
                //                 alert($('#headUrl').attr('value'));
            },
            error : function(data, status, e) {
                $.messager.alert('系统提示','上传出错','error');
            }
        })
    }
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
    var re = new RegExp(strRegex);

    var show = function () {
        $.get("/api/hospital-info/get", function (data) {
            if (data != null) {
                $("#hospitalId").val(data.hospitalId);
                $("#hospitalName").textbox('setValue', data.hospitalName);
                $("#appId").textbox('setValue', data.appId);
                $("#infoUrl").textbox('setValue', data.infoUrl);
                $("#appSecret").textbox('setValue', data.appSecret);
                $("#openName").textbox('setValue', data.openName);
                $("#appToken").textbox('setValue', data.appToken);
                $("#metchId").textbox('setValue', data.metchId);
                $("#key").textbox('setValue', data.key);
//                $("#tranContent").val(data.tranContent);
                var oEditor = CKEDITOR.instances.tranContent;
                oEditor.setData(data.tranContent);
            }
        })
    }
    show();
    $("#noBtn").on('click', function () {
        show();
    })
    $("#saveBtn").on('click', function () {
        var hospitalInfo = {};
        hospitalInfo.hospitalId = $("#hospitalId").val();
        hospitalInfo.appId = $("#appId").val();
        hospitalInfo.infoUrl = $("#infoUrl").val();
        hospitalInfo.appSecret = $("#appSecret").val();
        hospitalInfo.openName = $("#openName").val();
        hospitalInfo.appToken = $("#appToken").val();
        hospitalInfo.metchId = $("#metchId").val();
        hospitalInfo.key = $("#key").val();
        hospitalInfo.hospitalImg=$("#hospitalImg").val();
        alert(hospitalInfo.hospitalId);
        var tranContent = $("#tranContent").val();
        var oEditor = CKEDITOR.instances.tranContent;
        var tranContent1 = oEditor.getData();
        var tranContent=stripscript(tranContent1);
        if (re.test(hospitalInfo.infoUrl)) {
            $.postJSON("/api/hospital-info/merge?tranContent=" + tranContent, hospitalInfo, function (data) {
                $.messager.alert("系统提示", "保存成功", "info");
                show();
            });
        } else {
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


