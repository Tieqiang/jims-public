/**
 * Created by admin on 2016/6/25.
 */

$(function () {
    var editor;

    $('#startTime').datetimebox({
        showSeconds: true,
        required:true
     });
    $('#endTime').datetimebox({
        showSeconds: true,
        required:true
     });
    $('#startTime').datetimebox("setValue",formatter());
    $('#endTime').datetimebox("setValue",formatter());
    function formatter(){
            var date=new Date();
            var str1=date.getYear()+"-"+date.getMonth()<10?"0"+date.getMonth():date.getMonth()+"-";//
            var str2=date.getDay()<10?"0"+date.getDay():date.getDay()+" "+date.getHours()<10?"0"+date.getHours():date.getHours()+":";
            var str3=date.getMinutes()<10?"0"+date.getMinutes():date.getMinutes()+":"+date.getSeconds()<10?"0"+date.getSeconds():date.getSeconds();
            return str1+str2+str3;
    }
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
                }, {
                    title: 'openId',
                    field: 'openId',
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
                },
                {
                    title: '回复内容',
                    field: 'replyContent'
                },
                {
                    title: '是否回复',
                    field: 'replyFlag',
                    formatter:function(index,value){
//                        alert(value.replyFlag);
                        if(value.replyFlag=="1"){
                            return "已回复";
                        }else{
                            return "未回复";
                        }
                    }
                }
            ]
        ]
    });
    var loadData = function () {
        var startTime = $("#startTime").datetimebox("getValue");
        var endTime = $("#endTime").datetimebox("getValue");
        if (startTime == null || startTime == "") {
            $.messager.alert("系统提示", "请选择开始时间", "error");
            return;
        }
        if (endTime == null || endTime == "") {
            $.messager.alert("系统提示", "请选择结束时间", "error");
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
            $.messager.alert("系统提示", "请选择一条消息进行回复", "error");
            return;
        }
        editor = UE.getEditor("content");
        $("#replyDlg").dialog("open").dialog("setTitle","回复消息");
    })
    $("#saveBtn").on("click", function () {
        var rowDatas = $("#message").datagrid("getSelections");
        var fromUserName = rowDatas[0].openId;//openId
        var toUserName = rowDatas[0].toUserName;//公众号
        var id=rowDatas[0].id;
        var content = editor.getPlainTxt();
        if (content == null || content == "") {
            $.messager.alert("系统提示", "请填写需要回复的内容", "error");
            return;
        }
        $.ajax({
            url:"/api/source/reply-user?replyContet="+content+"&openId="+fromUserName+"&id="+id,
            type:"POST",
            cache:false,
            success:function(data){
                $('#replyDlg').dialog('close');
                loadData();
                $.messager.alert("系统提示","发送成功","info");
            },
            error:function(data){
                $.messager.alert("系统提示","该用户24小时之内未联系过您,您不能对其发送消息！","info");
            }
        })
    })
});
