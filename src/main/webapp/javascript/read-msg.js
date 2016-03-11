/**
 * Created by Dt on 2016-03-03.
 */

$(function () {
    //var req = {};
    //req.toUserName = "lily";
    //req.fromUserName = "tom";
    //req.msgType = "text";
    //req.content = "hello";
    //
    //jQuery.ajax({
    //    'type': 'POST',
    //    'url': "/api/req-msg/add",
    //    'contentType': 'application/json',
    //    'data': JSON.stringify(req),
    //    'dataType': 'json',
    //    'success': function (data) {
    //        console.log(data);
    //    },
    //    'error': function (data) {
    //        console.log("fail");
    //    }
    //});

    var initTable = $.get("/api/req-msg/list-all", function (data) {
        var html = '';
        if(data){
            $.each(data, function (index, item) {
                html += "<table class=\"table\"><tbody><tr><td class=\"col-sm-2\"><img src=\"../assert/dist/img/user1-128x128.jpg\"></td>";
                html += "<td class=\"col-sm-8\">\<table class=\"table\">\<tbody>\<tr>\<td class=\"text-left\">"+item.fromUserName;
                html += "</td></tr><tr><td class=\"text-left\"><p>"+item.content+"</p></td></tr>";
                html += "<tr class=\"replyArea\" id=\"replyArea" + index + "\" index=" + index + ">\<td colspan=\"4\">\<textarea index=" + index + " id=\"area" + index + "\" class=\"form-control\" rows=\"3\"></textarea>";
                html += "<button class=\"btn send\" id=\"send" + index + "\" index="+index+">发送</button>&nbsp;&nbsp;<button class=\"btn cancel\" id=\"cancel" + index + "\" index=" + index + ">收起</button>";
                html += "</td>\</tr></tbody></table></td><td class=\"col-sm-1\">11:20";
                html += "</td><td class=\"col-sm-1\">";
                html += "<button class=\"glyphicon glyphicon-star collect\" id=\"collect" + index + "\" index=" + index + "></button>&nbsp;&nbsp;<button class=\"glyphicon glyphicon-share-alt reply\" id=\"reply" + index + "\" index=" + index + "></button>";
                html+="</td></tr></tbody></table>";
            });
        }

        $("#reqMsg").html(html);
    });

    initTable.done(function(){
        $(".replyArea").hide();
        $(".collect").tooltip({
            title: "收藏",
            trigger: 'hover',
            placement: 'bottom'
        });
        $(".reply").tooltip({
            title: "快捷回复",
            trigger: 'hover',
            placement: 'bottom'
        });


        $(".collect").click(function () {
            $(this).toggleClass("selected");

        });

        $(".cancel").click(function () {
            var index=$(this).attr("index");
            $(this).parents("#replyArea" + index).hide();
        });

        $(".reply").click(function () {
            var index = $(this).attr("index");
            $("#replyArea" + index).toggle();
        });

        $(".send").click(function () {
            var index = $(this).attr("index");
            console.log("index:" + index);
            var msg = {};
            msg.toUserName = "you";
            msg.fromUserName = "me";
            msg.msgType = "text";
            msg.content = $("#area"+index).val();

            jQuery.ajax({
                'type': 'POST',
                'url': "/api/resp-msg/reply",
                'contentType': 'application/json',
                'data': JSON.stringify(msg),
                'dataType': 'json',
                'success': function (data) {
                    console.log(data);
                },
                'error': function (data) {
                    console.log("fail");
                }
            });
        });
    });

});