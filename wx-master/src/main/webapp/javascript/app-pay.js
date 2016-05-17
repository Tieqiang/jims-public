/**
 * created by chenxy on 2016-04-22
 * 微信支付js
  */
/**
 * window.onload()
 */
/**
 * 接收
 * @param name
 * @returns {*}
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
function GetQueryString(name) {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
}
var openId=GetQueryString("openId");//opemnId
var price=getUrlParameter("price");//挂号价格
var clinicForRegistId=getUrlParameter("clinicForRegistId");//号表Id
 $(function(){
      $.ajax({
         type:"POST",
         cache:false,
         url:"/api/clinic-for-regist/find-doct-regist?openId="+openId+"&clinicForRegistId="+clinicForRegistId,
         dataType:"JSON",
         success:function(data){//appDoctInfoVo;
            $("#headUrl").attr("src",data.headUrl);
             $("#name").text(data.name);
             $("#title").text(data.title);
             $("#deptName").text(data.deptName);
             $("#scheduleTime").text(data.scheduleTime);
             $("#timeDesc").text(data.timeDesc);
             $("#price").text(price+"￥");//patName
             $("#patName").text(data.patName);//enabledCount
             $("#enabledCount").text(data.enabledNum);
             $("#idCard").val(data.idCard);
//             alert(data.registTime);
             $("#registTime").text(data.registTime);
          }
      });

       $("#pay").on("click",function(){
              //生成支付js
            $.ajax({
                type:"POST",
                url:"/api/wx-service/pay-js?price="+price+"&openId="+openId,
                cache:false,
                dataType:"JSON",
                success:function(json){
 //                        // 成功返回支付js
//                        alert(json.package);
                        WeixinJSBridge.invoke('getBrandWCPayRequest',json,function(res){
//                            alert("errorMsg"+res.err_msg);
                            if(res.err_msg == 'get_brand_wcpay_request:ok'){
                                 // 支付成功后微信会调用 notify_url
                                //支付成功，后台数据库加入响应挂号操作
                                window.location.href="/api/clinic-for-regist/regist?prepareId="+json.prepareId+"&price="+price+"&clinicForRegistId="+clinicForRegistId+"&openId="+openId;
                            }else{
                                //支付失败
                                window.location.href="/views/his/public/app-pay-failed.html";
                            }
                        });
                 }
            });
     })
 })
 /**
 *
 */
function change(){
    if(openId!=null&&openId!=""){
        window.location.href="/api/wx-service/change?openId="+openId;
    }else{
        alert("暂时不能切换默认用户！");
    }
 }
 //wx.config({
//    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
//    appId: appId, // 必填，公众号的唯一标识
//    timestamp:timeStamp2 , // 必填，生成签名的时间戳
//    nonceStr: nonceStr2, // 必填，生成签名的随机串
//    signature: signature,// 必填，签名，见附录1
//    jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
//});
//wx.ready(function(){
//    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
//    wx.chooseWXPay({
//        timestamp: timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
//        nonceStr: nonceStr, // 支付签名随机串，不长于 32 位
//        package: pk, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
//        signType: signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
//        paySign: paySign, // 支付签名
//        complete: function (res) {
//            // 支付成功后的回调函数
//            location.href=basePath+'/activityorder/activity-order!list.action?activityId='+activityId;
//        }
//    });
//});

