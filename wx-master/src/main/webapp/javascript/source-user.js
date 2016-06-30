$(function () {
    /**
     * load datagrid data
     */
    var  editor;
    var loadDict = function () {
        $.get("/api/source/source-user", function (data) {
            $("#user").datagrid('loadData', data);
        });
    }
    loadDict();


    /**
     * init datagrid
     */
    $('#user').datagrid({
        idField: 'id',
        title: '关注用户列表',
        footer: '#tb',
        fit: true,
        fitColumns: true,
        singleSelect: true,
        striped: true,
        loadMsg: '数据正在加载,请耐心的等待...',
        remoteSort: true,
        columns: [
            [
                {
                    field: 'id',
                    title: '编号',
                    hidden: 'true'
                },
                {
                    field: 'openid',
                    title: 'openId'
                },
                {
                    field: 'nickname',
                    title: '昵称'
                },
                {
                    field: 'sex',
                    title: '性别',
                    formatter:function(index,value){
//                        console.info(value);
                        if(value.sex=="1"){
                            return "男";
                        }else{
                            return "女";
                        }
                    }

                },
                {
                    field: 'city',
                    title: '城市'

                },
                {
                    field: 'country',
                    title: '国家'
                }
            ]
        ]
    });
    /**
     * 发送消息
     */
    $("#send").on("click",function(){
        var selectDatas=$("#user").datagrid("getSelections");
//        alert(selectDatas.length);
        if(selectDatas.length!=1){
            $.messager.alert("系统提示","请选择一个用户发送消息","info");
            return;
        }
        editor=UE.getEditor("sendMessage");
        $('#sendMessageDlg').dialog('open').dialog('setTitle', '输入要发送的内容!');


     })
    /**
     * 确认发送
     */
    $("#submit").on("click",function(){
       var userLength=$("#user").datagrid("getSelections");
       var sendMessage= editor.getPlainTxt();
       var openId=userLength[0].openid;
       $.ajax({
            url:"/api/source/reply-user?replyContet="+sendMessage+"&openId="+openId,
            type:"POST",
            cache:false,
            success:function(data){
                $('#sendMessageDlg').dialog('close');
                $.messager.alert("系统提示","发送成功","info");
            },
            error:function(data){
                 $.messager.alert("系统提示","该用户24小时之内未联系过您,您不能对其发送消息！","info");
            }
        })
    })
});

