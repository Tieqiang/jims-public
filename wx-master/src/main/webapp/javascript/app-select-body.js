    /**
     * Created by cxy on 2016/5/9.
     */

    /**
     * 接收传递来的参数
     */
    function getUrlParameter(name) {
        name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
        var regexS = "[\\?&]" + name + "=([^&#]*)";
        var regex = new RegExp(regexS);
        var results = regex.exec(window.parent.location.href);
        if (results == null)    return ""; else {
            return results[1];
        }
    }
    var openId=getUrlParameter("openId");
    function symptomList2(id){
        var sexValue = $('#changeSex').attr('v');// 1 女性 0 男性
        window.location.href="/api/wx-service/query-symptom?bodyId="+id+"&openId="+openId+"&sexValue="+sexValue;
    }
    /**
     * 查询身体部位列表
     */
    function changeSymptomList(){
        var sexValue = $('#changeSex').attr('v');// 1 女性 0 男性
//        alert(sexValue);
        window.location.href="/api/wx-service/body-list?sexValue="+sexValue+"&openId="+openId;
     }
