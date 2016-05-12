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
//        $('#pic').addClass('wormanDisplay');
//
//        $('#body').removeClass('wormanDisplay');
//        $('#zndz0202').removeClass('wormanDisplay');
//
//        $('#symptomList_'+id).trigger('click');
//        $('#symptomList_'+id).addClass('changezndzzz');
        window.location.href="/api/wx-service/query-symptom?bodyId="+id+"&openId="+openId;
    }
