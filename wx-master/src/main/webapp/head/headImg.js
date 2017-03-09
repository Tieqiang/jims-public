/**
 * Created by admin on 2017/2/27.
 */
$(function(){
    $("#preReg").on('click',function(){
        location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf9532549ca01fa1f&redirect_uri=http://1k6v451587.iask.in/api/wx-service/find-dept-pre&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
    })
    $("#zhiNeng").on('click',function(){
        location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf9532549ca01fa1f&redirect_uri=http://1k6v451587.iask.in/api/wx-service/find-body&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
    })
})

