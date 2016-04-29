$(function () {
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

                console.log(data);
                $("#hospitalName").textbox('setValue', data.hospitalName);
                $("#appId").textbox('setValue', data.appId);
                $("#infoUrl").textbox('setValue', data.infoUrl);
                $("#appSecret").textbox('setValue', data.appSecret);
                $("#openName").textbox('setValue', data.openName);
                $("#appToken").textbox('setValue', data.appToken);
                $("#metchId").textbox('setValue', data.metchId);
                $("#key").textbox('setValue', data.key);
                $("#tranContent").val(data.tranContent);

            }
        })
    }
    show();
    $("#noBtn").on('click', function () {
        show();
    })

    $("#saveBtn").on('click', function () {
        var hospitalInfo = {};
        hospitalInfo.hospitalName = $("#hospitalName").val();
        hospitalInfo.appId = $("#appId").val();
        hospitalInfo.infoUrl = $("#infoUrl").val();
        hospitalInfo.appSecret = $("#appSecret").val();
        hospitalInfo.openName = $("#openName").val();
        hospitalInfo.appToken = $("#appToken").val();
        hospitalInfo.metchId = $("#metchId").val();
        hospitalInfo.key = $("#key").val();
        var tranContent = $("#tranContent").val();


        console.log(hospitalInfo.infoUrl);

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