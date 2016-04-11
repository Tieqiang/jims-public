/**
 * Created by fyg on 2016/3/23.
 * 支付方式字典表维护
 */
$(function(){
    var editIndex;
    var stopEdit = function () {
        if (editIndex || editIndex == 0) {
            $("#dg").datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
    }
    /**
     * 加载数据表格。查询所有记录
     */
    var loadDict = function () {
        $.get("/api/pay-way-dict/list" , function (data) {
            console.log(data);
            $("#dg").datagrid('loadData', data);
        });
    };

    loadDict();

    $("#dg").datagrid({
        title: '支付方式字典表维护',
        fit: true,
        footer: '#tb',
        singleSelect: true,
        columns: [[
            {title: 'ID',
                field: 'id',
                hidden: true
            },{
                title: '支付方式',
                field: 'payWayName',
                width:'30%',
                editor:{
                    type:'textbox',
                    options: {
                        required: true
                    }
                }
            }]],
        //用户点击一行时触发
        onClickRow: function (rowIndex, rowData) {
            stopEdit();
            $(this).datagrid('beginEdit', rowIndex);    //开始编辑行
            editIndex = rowIndex;
        }
    });

    /**
     * 增加支付方式
     */
    $("#addBtn").on('click',function(){
        stopEdit();
        $('#dg').datagrid('appendRow',{});
        var rows = $("#dg").datagrid('getRows');
        var addRowIndex = $("#dg").datagrid('getRowIndex', rows[rows.length - 1]);
        editIndex = addRowIndex;
        $("#dg").datagrid('selectRow', editIndex);  //选择新增加的一行
        $("#dg").datagrid('beginEdit', editIndex);  //开始编辑新增加的行数据

    });

    /**
     * 删除支付方式
     */
    $("#delBtn").on('click', function () {
        var row = $("#dg").datagrid('getSelected');	//获取被选中的行，即要删除的那行
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

    /**
     * 保存增删改
     */
    $("#saveBtn").on('click', function () {
        if (editIndex || editIndex == 0) {
            $("#dg").datagrid("endEdit", editIndex);
        }

        var insertData = $("#dg").datagrid("getChanges", "inserted");
        var updateDate = $("#dg").datagrid("getChanges", "updated");
        var deleteDate = $("#dg").datagrid("getChanges", "deleted");

        var beanChangeVo = {};
        beanChangeVo.inserted = insertData;
        beanChangeVo.deleted = deleteDate;
        beanChangeVo.updated = updateDate;

        if (beanChangeVo) {
            $.postJSON("/api/pay-way-dict/merge", beanChangeVo, function (data, status) {
                $.messager.alert("系统提示", "保存成功", "info");
                loadDict();
            }, function (data) {
                $.messager.alert('系统警告', data.responseJSON.errorMessage, "error");
                loadDict();
            })
        }
    });
});
