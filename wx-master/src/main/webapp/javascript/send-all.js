/**
 * Created by admin on 2016/6/25.
 */

$(function () {
    var sendContent = UE.getEditor('font');
//    sendContent.setDisabled('fullscreen');
    /**
     * 加载图文消息
     */
    var loadImageFont = function () {
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
                document.location.reload();
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
            var sendContent = sendContent.getPlainTxt();
//            alert(sendContent);
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
     * 删除素材
     */
    $("#delete").on("click", function () {
        var sendType = $("#selectType").combobox("getValue");
        if (sendType == 2) {//文本格式
            $.messager.alert("系统提示", "请选择素材进行删除！", "error");
            return;
        }

        var selectedDatas = $("#data").datagrid("getSelections");
        if (selectedDatas.length != 1) {
            $.messager.alert("系统提示", "请选择一条素材进行删除！", "error");
            return;
        }
        var mediaId = selectedDatas[0].mediaId;
        $.postJSON("/api/source/delete-source?mediaId=" + mediaId + "&sendType=" + sendType, function () {
             if(sendType==1){
                 loadImageFont();
             }else if(sendType==3){
                 loadImage();
             }
            $.messager.alert("系统提示", "删除成功", "info");
        }, function (data, status) {
            $.messager.alert("系统提示", "删除成功", "info");
        })

    })
    /**
     * 预览
     */
    $("#preview").on("click", function () {
        var selectedDatas = $("#data").datagrid("getSelections");
        var selectedType = $("#selectType").combobox("getValue");
        if (selectedType != 2 ? (selectedDatas.length != 1 ? true : false) : false) {
            $.messager.alert("系统提示", "请选择一条记录进行预览!", "error");
            return;
        }
        var mediaId;
//        var content;
//        if (selectedType != 2) {//不是文字消息
//            mediaId = selectedDatas[0].mediaId;
//        } else {
//            content = sendContent.getPlainTxt();
//        }
        $("#userDlg").dialog("open").dialog("setTitle", "请选择预览人员！");
        initUser();
        loadDict();
//        var  selectUsers=$("#user").datagrid("getSelections");
//        if(selectUsers.length!=1){
//            $.messager.alert("系统提示", "只能选择一名用户,进行预览!", "error");
//            return;
//        }
//        var openId=selectUsers[0].openid;
//        $.postJSON("/api/source/preview?selectedType=" + selectedType + "&mediaId=" + mediaId + "&sendContent=" + content+"&openId="+openId, function (data) {
//                $.messager.alert("系统提示", "已发送到指定预览的手机,请注意查收", "info");
//            }, function (data, status) {
//                $.messager.alert("系统提示", "已发送到指定预览的手机,请注意查收", "info");
//            }
//        )

    })
    /**
     * 确定预览
     */
    $("#submit").on("click", function () {
        var selectedDatas = $("#data").datagrid("getSelections");
        var content;
        if (selectedType != 2) {//不是文字消息
            mediaId = selectedDatas[0].mediaId;
        } else {
            content = sendContent.getPlainTxt();
        }
        var selectedType = $("#selectType").combobox("getValue");
        var selectUsers = $("#user").datagrid("getSelections");
        if (selectUsers.length != 1) {
            $.messager.alert("系统提示", "只能选择一名用户,进行预览!", "error");
            return;
        }
        $("#userDlg").dialog("close");
        var openId = selectUsers[0].openid;
        $.postJSON("/api/source/preview?selectedType=" + selectedType + "&mediaId=" + mediaId + "&sendContent=" + content + "&openId=" + openId, function (data) {
                $.messager.alert("系统提示", "已发送到指定预览的手机,请注意查收", "info");
            }, function (data, status) {
                $.messager.alert("系统提示", "已发送到指定预览的手机,请注意查收", "info");
            }
        )
    })
    /**
     * 图片和图文素材的库
     */
    function init() {
        $("#data").datagrid({
            idField: "id",
            title: '素材库列表',
            footer: '#foot',
            singleSelect: true,
            fit: true,
            columns: [
                [
                    {
                        title: '编号',
                        field: 'id',
                        hidden: true
                    },
                    {
                        title: 'mediaId',
                        field: 'mediaId',
                        hidden: true
                    },
                    {
                        title: 'image',
                        field: 'image',
                        width: "100%"
                    }
                ]
            ]
        });
    }

    /**
     * 同步服务器素材
     */
    $("#down").on("click", function () {
        var selectType = $("#selectType").combobox("getValue");
        var url = "";
        if (selectType == 1) {
            url = "/api/source/synch-image-font";
        } else if (selectType == 3) {
            url = "/api/source/synch-image";
        } else {
            $.messager.alert("系统提示", "您选择的是文本,不能进行同步！");
            return;
        }
        $.postJSON(url, function (data) {
            $.messager.alert("系统提示", "数据已同步到本地，请重新加载！", "info");
        }, function (data, status) {
            $.messager.alert("系统提示", "数据已同步到本地，请重新加载！", "info");
        })
    })
});
