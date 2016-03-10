/**
 * Created by Dt on 2016-03-03.
 */

$(function () {

    $("#replyArea").hide();
    $("#collect").tooltip({
        title: "收藏",
        trigger: 'hover',
        placement: 'bottom'
    });
    $("#reply").tooltip({
        title: "快捷回复",
        trigger: 'hover',
        placement: 'bottom'
    });


    $("#collect").click(function () {
        $("#collect").toggleClass("selected");
        if ($("#collect").hasClass("selected")) {
            $("#collect").tooltip({
                title: "取消收藏",
                trigger: 'hover',
                placement: 'bottom'
            });
        }
    });

    $("#cancel").click(function () {
        $("#replyArea").hide();
        //$(".area").val("");
    });

    $("#reply").click(function () {
        $("#replyArea").toggle();
    });

    $("#send").click(function(){
        var msg = {};
        msg.toUserName = "you";
        msg.fromUserName = "me";
        msg.msgType = "text";
        msg.content = $("#area").val();

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