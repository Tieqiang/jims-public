/**
 * created by chenxy on 2016-04-17
 */
$(function () {
//    $("#showTooltips").on('click', function () {
//    });

})
function getUrlParameter(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regexS = "[\\?&]" + name + "=([^&#]*)";
    var regex = new RegExp(regexS);
    var results = regex.exec(window.parent.location.href);
    if (results == null)    return ""; else {
        return results[1];
    }
}
var v = getUrlParameter('param');
//alert("openId="+v);
$("#openId").val(v);
 function clickFun (){

    var name = $("#name").val();
    var idCard = $("#idCard").val();
    var cellphone = $("#cellphone").val();
    var openId = v;
    var returnResult = isCardID(idCard);
    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;//phone
//     alert(cellphone);
//    alert(returnResult);
    if (!returnResult == true) {
        alert(returnResult);
    }
    else if (!myreg.test(cellphone)) {
         alert('请输入有效的手机号码！');
     }
    else if (name.trim() == "" || name.trim() == null) {
        alert("姓名不能为空！");
    } else if (idCard.trim() == "" || idCard.trim() == null) {
        alert("省份证不能为空！");
    } else if (cellphone.trim() == "" || cellphone.trim() == null) {
        alert("手机号不能为空！");
    } else if (openId.trim() == "" || openId.trim() == null) {
        alert("暂时不能绑卡！");
    } else {
        window.location.href = "/api/pat-info/save?name=" + name + "&idCard=" + idCard + "&cellphone=" + cellphone + "&openId=" + openId;
    }
}
function isCardID(sId) {
    var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
    var iSum = 0;
    var info = "";
    if (!/^\d{17}(\d|x)$/i.test(sId)) return "你输入的身份证长度或格式错误";
    sId = sId.replace(/x$/i, "a");
    if (aCity[parseInt(sId.substr(0, 2))] == null) return "你的身份证地区非法";
    sBirthday = sId.substr(6, 4) + "-" + Number(sId.substr(10, 2)) + "-" + Number(sId.substr(12, 2));
    var d = new Date(sBirthday.replace(/-/g, "/"));
    if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate()))return "身份证上的出生日期非法";
    for (var i = 17; i >= 0; i--) iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11);
    if (iSum % 11 != 1) return "你输入的身份证号非法";
     return true;
}

