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
            //var url = '';
            //if ($(".radios:checked").val() == 0) {
            //    url='/api/buy-exp-plan/list-low?storageCode=' + parent.config.storageCode;
            //}
            ////全部
            //if ($(".radios:checked").val() == 1) {
            //    url='/api/buy-exp-plan/list-all?storageCode=' + parent.config.storageCode;
            //}
            //$("#left").datagrid("load",url);
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
                        console.log(data);
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
        //,
        //onClickRow: function (index, row) {
        //    stopEdit();
        //
        //    $(this).datagrid('beginEdit', index);
        //    editIndex = index;
        //}
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
        $.get("/api/wx-service/check", function (data) {
            $("#left").datagrid('loadData', data);
        });

        alert("sync data");

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
            var groupJson = {"group": {"id": groupId}};
            $.messager.confirm("系统提示", "删除标签后，该标签下的所有用户将失去该标签属性。是否确定删除？", function (r) {
                if (r) {
                    //var rowIndex = $("#left").datagrid('getRowIndex', row);
                    //$("#left").datagrid('deleteRow', rowIndex);
                    var post = {data: JSON.stringify(groupJson)};//JSON.stringify(json)把json转化成字符串

                    $.post("/api/app-user-group/del", post, function (data) {
                        $.messager.alert("系统提示", "删除标签成功", "info");
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
            var groupJson
            if(groupId){
                groupJson = {"group": {"id":groupId,"name": name}};
                var post = {data: JSON.stringify(groupJson)};//JSON.stringify(json)把json转化成字符串

                $.post("/api/app-user-group/modify", post, function (data) {
                    $('#dlg').dialog('close');
                    $.messager.alert("系统提示", "修改标签成功", "info");
                }, function (data, status) {
                });
            }else{
                groupJson = {"group": {"name": name}};
                var post = {data: JSON.stringify(groupJson)};//JSON.stringify(json)把json转化成字符串

                $.post("/api/app-user-group/add", post, function (data) {
                    $('#dlg').dialog('close');
                    $.messager.alert("系统提示", "新建标签成功", "info");
                }, function (data, status) {
                });
            }

            console.log(groupJson);
            resetGroup();
            loadGroup();
        }

    });
    //打标签按钮功能
    $("#moveBtn").on('click', function () {
        var rows= $("#right").datagrid("getSelections");
        var openIds;

        if (rows.length==0) {
            $.messager.alert("系统警告", "请选择打标签的行", "error");
        } else {

            $.each(rows, function (index, item) {
                openIds +=item.openId+";";
            });
            openIds = openIds.substr(0,openIds.length-1);
            console.log(openIds);
            $('#dlg-move').dialog('open').dialog('center').dialog('setTitle', '打标签');
            //$.get("/api//?=" + rows, function (data) {
            //    if (data.length > 0) {
            //        $.messager.alert("提示", "移动成功！", "info");
            //    } else {
            //        $.messager.alert("提示", "FAIL！", "info");
            //    }
            //})
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

            $('#dlg-tip').dialog('open').dialog('center').dialog('setTitle', '添加备注名');
            //var user = {};
            //user.openId = rows.openId;
            //$.post("/api/app-user/update-tip", post, function (data) {
            //    $('#dlg').dialog('close');
            //    $.messager.alert("系统提示", "新建标签成功", "info");
            //}, function (data, status) {
            //});
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
            //var remarkJson = {"openId": openId, "remark": remark};
            //
            //var post = {data: JSON.stringify(remarkJson)};//JSON.stringify(json)把json转化成字符串

            $.post("/api/app-user/update-tip", user, function (data) {
                $('#dlg-tip').dialog('close');
                $.messager.alert("系统提示", "添加备注名成功", "info");

            }, function (data, status) {
            });


            console.log(groupJson);

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
                    //var post = {data: JSON.stringify(groupJson)};//JSON.stringify(json)把json转化成字符串
                    //
                    //$.post("/api/app-user-group/del", post, function (data) {
                    //    $.messager.alert("系统提示", "删除标签成功", "info");
                    //}, function (data, status) {
                    //});
                } else {
                    resetGroup();
                }
            });
        }
    });


    var checkValidate = function (rows) {
        //for (var i = 0; i < rows.length; i++) {
        //    //删除空行
        //    if (rows[i].expCode == undefined || rows[i].expName == undefined || rows[i].firmId == undefined || rows[i].units == undefined) {
        //        $.messager.alert("系统提示", "第" + (i + 1) + "行为空请删除", 'error');
        //        $("#right").datagrid('selectRow', i);
        //        return false;
        //    }
        //    if (rows[i].wantNumber == undefined || rows[i].wantNumber <= 0) {
        //        $.messager.alert("系统提示", "第" + (i + 1) + "行:计划数量不能小于0 请重新填写", 'error');
        //        $("#right").datagrid('selectRow', i);
        //        //$("#right").datagrid('beginEdit', i);
        //        return false;
        //    }
        //    //if (rows[i].planNumber == undefined || rows[i].planNumber <= 0) {
        //    //    $.messager.alert("系统提示", "第" + (i + 1) + "行:计划金额不能小于0 请重新填写", 'error');
        //    //    $("#right").datagrid('selectRow', i);
        //    //    //$("#right").datagrid('beginEdit', i);
        //    //    return false;
        //    //}
        //    if (rows[i].exportquantityRef == undefined || rows[i].exportquantityRef <= 0) {
        //        $.messager.alert("系统提示", "第" + (i + 1) + "行:消耗量不能小于0 请重新填写", 'error');
        //        $("#right").datagrid('selectRow', i);
        //        //$("#right").datagrid('beginEdit', i);
        //        return false;
        //    }
        //    //if (rows[i].retailPrice == undefined || rows[i].retailPrice <= 0) {
        //    //    $.messager.alert("系统提示", "第" + (i + 1) + "行:零售价不能小于0 请重新填写", 'error');
        //    //    $("#right").datagrid('selectRow', i);
        //    //    //$("#right").datagrid('beginEdit', i);
        //    //    return false;
        //    //}
        //}
        //return true;
    }

});
