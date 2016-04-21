$(function(){

    var editIndex;
    var stopEdit = function () {
        if (editIndex || editIndex == 0) {
            $("#dg").datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
    }

    $("#dg").datagrid({
        title:'AccessTooken管理',
        fit:true,
        footer:'#ft',
        singleSelect:true,
        columns:[[{
            title:'ID',
            field:'id',
            hidden:true
        },{
            title:'微信公众号',
            field:'appId',
            width:'20%',
            editor:{
                type: 'combogrid', options:{
                    panelWidth: '20%',
                    idField: 'appId',
                    textField: 'openName',
                    loadMsg: '数据正在加载',
                    url: "/api/wx-open-account-config/list",
                    mode: 'listApp',
                    method: 'GET',
                    columns: [[
                        {field: 'openName', title: '名称', width: '45%', align: 'center'},
                        {field: 'appId', title: '微信号', width: '45%', align: 'center'},
                    ]],
                    pagination: false,
                    fitColumns: true,
                    autoRowHeight: false
                }
            }
        },{
                title:'AccessTooken',
                field:'accessTooken',
                width:'50%',
                editor:'text'
            },{
                title:'开始时间',
                field:'startTime',
                width:'30%'
        }]],
        onClickRow: function (index, row) {      //用户点击一行时触发事件编辑信息
            stopEdit();
            var a= $(this).datagrid('beginEdit', index);
            //var ed = $('#dg').datagrid('getEditor', {index:index, field:'accessTooken'});
            //$(ed.target).text('setValue', 'abc');
            editIndex = index;
        }
    })

    var loadDict = function () {
        $.get("/api/access-tooken/list-all",function(data){
            $("#dg").datagrid('loadData', data);
        });
    }
    loadDict();

    //新增按钮
    $("#addBtn").on('click', function () {
        stopEdit();
        $("#dg").datagrid('appendRow', {});
        var rows = $("#dg").datagrid('getRows');
        var addRowIndex = $("#dg").datagrid('getRowIndex', rows[rows.length - 1]);
        editIndex = addRowIndex;
        $("#dg").datagrid('selectRow', editIndex);
        $("#dg").datagrid('beginEdit', editIndex);
    });

    //删除按钮
    $("#delBtn").on('click', function () {
        var row = $("#dg").datagrid('getSelected');
        if (row) {
            var rowIndex = $("#dg").datagrid('getRowIndex', row);
            $("#dg").datagrid('deleteRow', rowIndex);
            if (editIndex == rowIndex) {
                editIndex = undefined;
            }
        } else {
            $.messager.alert('系统提示', "请选择要删除的行", 'info');
        }
    });

    //保存增删改
    $("#saveBtn").on('click', function () {
        if (editIndex || editIndex == 0) {
            $("#dg").datagrid("endEdit", editIndex);
        }

        var insertData = $("#dg").datagrid("getChanges", "inserted");
        var updateDate = $("#dg").datagrid("getChanges", "updated");
        console.log(updateDate);
        var deleteDate = $("#dg").datagrid("getChanges", "deleted");

        var beanChangeVo = {};
        beanChangeVo.inserted = insertData;
        beanChangeVo.deleted = deleteDate;
        beanChangeVo.updated = updateDate;

        if (beanChangeVo) {
            $.postJSON("/api/access-tooken/merge", beanChangeVo, function (data, status) {
                $.messager.alert("系统提示", "保存成功", "info");
                loadDict();
            }, function (data) {
                $.messager.alert('提示', data.responseJSON.errorMessage, "error");
            })
        }
    });
})