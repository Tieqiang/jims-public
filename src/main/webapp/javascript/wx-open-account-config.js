/**
 * Created by fyg on 2016/3/31.
 */
$(function(){
    var editIndex;
    var stopEdit = function () {
        if (editIndex || editIndex == 0) {
            $("#dg").datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
    }

    $("#dg").datagrid({
        title:'公众号列表',
        fit:true,
        footer: '#ft',
        singleSelect: true,
        columns: [[{
            title: 'ID',
            field: 'id',
            hidden:true
        },{
            title: '医院',
            field: 'hospitalId',
            width: '20%',
            editor: {
                type: 'combogrid', options:{
                    panelWidth: 214,
                    idField: 'id',
                    textField: 'hospitalName',
                    loadMsg: '数据正在加载',
                    url: "/api/hospital-dict/list",
                    mode: 'listDoc',
                    method: 'GET',
                    columns: [[
                        {field: 'hospitalName', title: '名称', width: 80, align: 'center'},
                    ]],
                    pagination: false,
                    fitColumns: true,
                    rowNumber: true,
                    autoRowHeight: false
                }
            }/*,
            formatter: function(value,row,index){
                console.log(row);
                *//*if (row.deptOutpInp == 1){
                    return "门诊";
                } else {
                    return "住院";
                }*//*
            }*/
        },{
            title: '公众号ID',
            field: 'appId',
            width: '12%',
            editor: 'text'
        },{
            title: '公众号名称',
            field: 'openName',
            width:'15%',
            editor: 'text'
        },{
            title: '公众号唯一码',
            field: 'appSecret',
            width: '15%',
            editor: 'text'
        },{
            title: 'js域名',
            field: 'jsRout',
            width: '15%',
            editor: 'text'
        },{
            title: '公众号接口地址',
            field: 'url',
            width: '15%',
            editor: 'text'
        },{
            title: '访问凭证',
            field: 'tooken',
            width: '10%',
            editor:'text'
        }]],
        onClickRow: function (index, row) {      //用户点击一行时触发事件编辑公众号信息
            stopEdit();
            $(this).datagrid('beginEdit', index);
            editIndex = index;
        }
    });

    $("#addBtn").on('click', function () {
        stopEdit();
        $("#dg").datagrid('appendRow', {});
        var rows = $("#dg").datagrid('getRows');
        var addRowIndex = $("#dg").datagrid('getRowIndex', rows[rows.length - 1]);
        editIndex = addRowIndex;
        $("#dg").datagrid('selectRow', editIndex);
        $("#dg").datagrid('beginEdit', editIndex);
    });

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

    var loadDict = function () {
        $.get("/api/wx-open-account-config/list", function (data) {
            $("#dg").datagrid('loadData', data);
        });
    }

    loadDict();


    /**
     * 保存修改的内容
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
            $.postJSON("/api/wx-open-account-config/merge", beanChangeVo, function (data, status) {
                $.messager.alert("系统提示", "保存成功", "info");
                loadDict();
            }, function (data) {
                $.messager.alert('提示', data.responseJSON.errorMessage, "error");
            })
        }
    });
});
