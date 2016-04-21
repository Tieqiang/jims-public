$(function () {

    var editIndex;

    var stopEdit = function () {
        if (editIndex || editIndex == 0) {
            $("#right").datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
    }

    $("#expName").searchbox({
        searcher: function (value, name) {

            var rows = $("#left").datagrid("getRows");
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].nickName == value) {
                    $("#left").datagrid('selectRow', i);
                }
            }
        }
    });

    //左侧列表初始化
    $("#left").datagrid({
        title: '全部分组',
        singleSelect: true,
        fit: true,
        nowrap: false,
        columns: [[{
            title: 'ID',
            field: 'id',
            width: "15%",
            hidden:true
        }, {
            title: 'GROUP_ID',
            field: 'groupId',
            width: "15%",
            hidden: true
        }, {
            title: '分组名称',
            field: 'name',
            width: "100%",
            formatter: function (value, row, index) {
                return row.name + "(" + row.count+")";
            }
        }]],
        onClickRow: function (index, row) {
            $.get("/api/app-user/get-user-by-id?groupId=" + row.groupId, function (data) {
                $("#right").datagrid("loadData", data);
            });
        }
    });

    //右侧列表初始化
    $("#right").datagrid({
        title: '全部用户',
        singleSelect: false,
        fit: true,
        fitColumns: true,
        columns: [[{
            title: '序号',
            field: 'id',
            width: "5%"
        }, {
            title: '昵称',
            field: 'nickName',
            width: "25%"
        }, {
            title: 'OPENID',
            field: 'openId',
            width: "25%"
        }, {
            title: '备注',
            field: 'remark',
            width: "25%"
        }]]
    });

    $("#top").datagrid({
        toolbar: '#ft',
        border: false
    });
    $("#bottom").datagrid({
        footer: '#tb',
        border: false
    });

    $('#dg').layout('panel', 'north').panel('resize', {height: 'auto'});
    $('#dg').layout('panel', 'south').panel('resize', {height: 'auto'});

    $("#dg").layout({
        fit: true
    });

    var loadGroup = function () {
        $.get("/api/app-user-group/list-all", function (data) {
            $("#left").datagrid('loadData', data);
        });
    };

    loadGroup();
    //同步数据按钮功能
    $("#syncDataBtn").on('click', function () {
        stopEdit();
        $.post("/api/app-user-group/syn", function (data) {
            loadGroup();
        });
    });
    var resetGroup = function(){
        $("#groupId").textbox('setValue', "");
        $("#name").textbox('setValue', "");
    };
    //新建标签按钮功能
    $("#addGroupBtn").on('click', function () {
        resetGroup();
        $('#dlg').dialog('open').dialog('center').dialog('setTitle', '新建标签');
    });
    //修改标签按钮功能
    $("#modGroupBtn").on('click', function () {
        var row = $("#left").datagrid("getSelected");
        if(row){
            $("#groupId").textbox('setValue', row.groupId);
            $("#name").textbox('setValue', row.name);
            $('#dlg').dialog('open').dialog('center').dialog('setTitle', '新建标签');
        }else{
            $.messager.alert("系统警告", "请选择修改标签的行！", "error");
        }
    });
    //删除标签按钮功能
    $("#delGroupBtn").on('click', function () {
        var row = $("#left").datagrid("getSelected");
        if (row) {
            var groupId = row.groupId;
            if(groupId=="1"||groupId=='0'||groupId=='2'){
                $.messager.alert("系统提示","系统默认的分组不能删除","error");
                return ;
            }
            $.messager.confirm("系统提示", "删除标签后，该标签下的所有用户将失去该标签属性。是否确定删除？", function (r) {
                if (r) {
                    $.post("/api/app-user-group/del", groupId, function (data) {
                        $.messager.alert("系统提示", "删除标签成功", "info");
                        loadGroup();
                        resetGroup();
                    }, function (data, status) {
                    });
                } else {
                    resetGroup();
                }
            });
        } else {
            $.messager.alert("系统警告", "请选择删除标签的行！", "error");
        }

    });
    //新建标签提交
    $("#addBtn").on('click', function () {
        if (true) {
            var groupId = $("#groupId").textbox('getValue');
            var name = $("#name").textbox('getValue');
            if(groupId){
                $.post("/api/app-user-group/modify?id="+groupId+"&groupName="+name,function (data) {
                    $('#dlg').dialog('close');
                    $.messager.alert("系统提示", "修改标签成功", "info");
                    loadGroup();
                    resetGroup();
                }, function (data, status) {
                });
            }else{
                $.post("/api/app-user-group/add", name, function (data) {
                    $('#dlg').dialog('close');
                    $.messager.alert("系统提示", "新建标签成功", "info");
                    loadGroup();
                    resetGroup();
                }, function (data, status) {
                });
            }
        }

    });
    //打标签按钮功能
    $("#moveBtn").on('click', function () {
        var rows= $("#right").datagrid("getSelections");
        var openIds;

        if (rows.length==0) {
            $.messager.alert("系统警告", "修改分组", "error");
        } else {
            $.each(rows, function (index, item) {
                openIds +=item.openId+";";
            });
            openIds = openIds.substr(0,openIds.length-1);

            $('#dlg-move').dialog('open').dialog('center').dialog('setTitle', '修改分组');

        }
    });
    //修改备注名
    $("#tipBtn").on('click', function () {
        var rows = $("#right").datagrid("getSelections");

        if (rows.length != 1) {
            $.messager.alert("系统警告", "请选择添加备注名的行", "error");
        } else {
            var row = $("#right").datagrid("getSelected");
            console.log(row);
            $("#userId").textbox("setValue",row.id);
            $("#openId").textbox('setValue', row.openId);
            $("#nickName").textbox('setValue',row.nickName);

            $('#dlg-tip').dialog('open').dialog('center').dialog('setTitle', '添加备注名');

        }
    });

    $("#addRemBtn").on('click', function () {
        if (true) {
            var id = $("#userId").textbox('getValue');
            var openId = $("#openId").textbox('getValue');
            var remark = $("#remark").textbox('getValue');
            var user = {};
            user.id = id;
            user.openId = openId;
            user.remark = remark;
            $.postJSON("/api/app-user/update-tip", user, function (data) {
                $('#dlg-tip').dialog('close');
                $.messager.alert("系统提示", "添加备注名成功", "info");
            }, function (data, status) {
            });
        }

    });
    //加入黑名单按钮功能
    $("#blackListBtn").on('click', function () {
        var rows = $("#right").datagrid("getSelections");
        var openIds;

        if (rows.length == 0) {
            $.messager.alert("系统警告", "请选择加入黑名单的行", "error");
        } else {
            $.messager.confirm("系统提示", "加入黑名单后，你将无法接收粉丝发来的消息，且该粉丝无法接收到群发消息。确认加入黑名单？", function (r) {
                if (r) {

                } else {
                    resetGroup();
                }
            });
        }
    });


    var checkValidate = function (rows) {

    }


    //修改分组名称
    $("#dlg-move").window({
        modal:true,
        width:400,
        height:200,
        onBeforeOpen:function(){
            var row = $("#left").datagrid('getSelected');
            if(row){
                $("#currentGroupId").combobox('setValue',row.groupId);
            }
            var user  = $("#right").datagrid('getSelected') ;
            if(user){
                $("#opId").combobox('setValue',user.openId) ;
                console.log("user")
                console.log(user) ;
            }else{
                $.messager.alert('系统提示','请选择要移动的人','info') ;
                return false ;
            }
        }
    });

    //目标分组显示所有的分组
    $("#targetGroupId").combobox({
        textField:'name',
        valueField:'groupId',
        method:'GET',
        url:'/api/app-user-group/list-all'
    });

    $("#currentGroupId").combobox({
        textField:'name',
        valueField:'groupId',
        method:'GET',
        url:'/api/app-user-group/list-all'
    }) ;

    $("#opId").combobox({
        textField:'nickName',
        valueField:"openId",
        method:'GET',
        url:"/api/app-user/list-all"
    })

    $("#moveGroupBtn").on('click',function(){
        var currentGroupId = $("#currentGroupId").combobox('getValue') ;
        var targetGroupId = $("#targetGroupId").combobox('getValue') ;
        var openId = $('#opId').combobox('getValue') ;
        if(targetGroupId==null ||targetGroupId==undefined){
            $.messager.alert("系统提示","目标分组不能为空",'error');
            return ;
        }

        $.post("/api/app-user-group/move-group/"+openId+"/"+targetGroupId+"/"+currentGroupId,function(data){
            $.messager.alert("系统提示","移动用户成功",'info');
            $("#dlg-move").window('close');
            loadGroup();
            resetGroup() ;
        });

    })

});
