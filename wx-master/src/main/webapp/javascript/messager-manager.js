/**
 * Created by admin on 2016/6/25.
 */

$(function () {
    var editor;

    $('#startTime').datetimebox({
        showSeconds: true
    });
    $('#endTime').datetimebox({
        showSeconds: true
    });

    $("#message").datagrid({
        idField: "id",
        title: '消息列表',
        footer: '#foot',
        fit: true,
        columns: [
            [
                {
                    title: 'id',
                    field: 'id',
                    hidden: true
                },
                {
                    title: 'toUserName',
                    field: 'toUserName',
                    hidden: true
                },
                {
                    title: '发送者',
                    field: 'fromUserName'
                },
                {
                    title: '发送时间',
                    field: 'createTime'
                },
                {
                    title: '消息类型',
                    field: 'msgType'
                },
                {
                    title: '消息内容',
                    field: 'content'
                }
            ]
        ]
    });

    var loadData = function () {
        var startTime = $("#startTime").datetimebox("getValue");
        var endTime = $("#endTime").datetimebox("getValue");
        if (startTime == null || startTime == "") {
            $.messager, alert("系统提示", "请选择开始时间", "error");
            return;
        }
        if (endTime == null || endTime == "") {
            $.messager, alert("系统提示", "请选择结束时间", "error");
            return;
        }
        $.get("/api/source/load-message-list?startTime=" + startTime + "&endTime=" + endTime, function (data) {
            $("#message").datagrid("loadData", data);
        })
    }
//    loadData();

    $("#searchBtn").on("click", function () {
        loadData();
    })

    $("#replyBtn").on("click", function () {
        var rowDatas = $("#message").datagrid("getSelections");
        if (rowDatas.length != 1) {
            $.messager, alert("系统提示", "请选择一条消息进行回复", "error");
            return;
        }

        editor = UE.getEditor("content");
        $("#replyDlg").dialog("open");
    })
    $("#saveBtn").on("click", function () {
        var fromUserName = rowDatas[0].fromUserName;//openId
        var toUserName = rowDatas[0].toUserName;//公众号
        var content = editor.getContentTxt();
        if (content == null || content == "") {
            $.messager.alert("系统提示", "请填写需要回复的内容", "error");
            return;
        }
        $.postJSON("/api/source/reply-user?openId=" + fromUserName + "&replyContent=" + content + "&toUserName=" + toUserName, function (data) {
            $.messager.alert("系统提示", "回复成功", "info");
        }, function (data, status) {
            $.messager.alert("系统提示", data.errorMessage, "error");
        });
    })


    var sendContent = UE.getEditor('font');
    /**
     * 加载图文消息
     */
    var loadImageFont = function () {
        //todo  加载图文消息
        $.get("/api/source/load-image-font", function (data) {
            $("#data").datagrid("loadData", data);
        });
    }
    /**
     * 加载图片
     */
    var loadImage = function () {
        $.get("/api/source/load-image", function (data) {
            $("#data").datagrid("loadData", data);
        });
    }
    /**
     * 跟换样式
     */
    var loadStyle = function () {
        $("#wenzi").hide();
        $("#data").show();
    }
    $('#selectType').combobox({
        onChange: function (newValue, oldValue) {
            if (newValue == 1) {//图文
                init();
                loadStyle();
                loadImageFont();
            } else if (newValue == 3) {
                //图片
                init();
                loadStyle();
                loadImage();
            } else if (newValue == 2) {//文字

                $("#wenzi").show();
                $("#data").hide();
            }
        }
    });
    var getSelectData = function () {
        var selectData = $("#data").datagrid("getSelections");
        if (selectData.length <= 0) {
            $.messager.alert("系统提示", "请选择你要发送的内容", "error");
            return;
        }
        var sendMessage = {};
        var ids = "";
        for (var i = 0; i < selectData.length; i++) {
            ids += selectData[i].id + ",";
        }
        ids = ids.substring(0, ids.length - 1);
        sendMessage.ids = ids;
        return sendMessage;
    }
    var sendData = function () {
        var sendType = $('#selectType').combobox("getValue");
        if (sendType == 1) {//图文
            var sendMessage = getSelectData();
            ajaxReq(sendType, sendMessage);
        } else if (sendType == 2) {//文字
            var sendContent = UE.getEditor('font');
            var sendContent = sendContent.getContentTxt();
            alert(sendContent);
            var sendMessage = {};
            sendMessage.content = sendContent;
            ajaxReq(sendType, sendMessage);
        } else if (sendType == 3) {//图片
            var sendMessage = getSelectData();
            ajaxReq(sendType, sendMessage);
        }
    }
    /*
     *
     */
    var ajaxReq = function (sendType, sendMessage) {
        $.postJSON("/api/source/send-all?sendType=" + sendType, sendMessage, function (data) {
            $.messager.alert("系统提示", "发送成功", "info");
        }, function (data, status) {
            $.messager.alert("系统提示", "发送失败", "error");
        })
    }
    /**
     * 群发发送操作
     */
    $("#send").on("click", function () {
        sendData();
    });


    /**
     * 图片和图文素材的库
     */


});
