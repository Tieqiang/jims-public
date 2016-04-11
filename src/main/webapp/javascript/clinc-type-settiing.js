/**
 * Created by wei on 2016/3/27.
 */
$(function () {
    var editIndex;
    var stopEdit = function () {
        if (editIndex || editIndex == 0) {
            $("#dg").datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
    }
    $("#dg").datagrid({
        title: '门诊号类维护',
        fit: true,//让#dg数据创铺满父类容器
        footer: '#tb',
        singleSelect: true,
        columns: [[{
            title: '编号',
            field: 'id',
            hidden: 'true'
        }, {
            title: '号类名称',
            field: 'clinicType',
            width: "30%",
            editor: {
                type: 'textbox',
                options: {
                    required: true
                }
            }
        }, {
            title: '医院编号',
            field: 'hospitalId',
            width: "30%",
            hidden: 'true'
        }, {
            title: '所属公众号',
            field: 'appId',
            width: "40%",
            hidden: 'true'
        }]],
        onClickRow: function (index, row) {
            stopEdit();
            $(this).datagrid('beginEdit', index);
            editIndex = index;
        }
    });

    $("#searchBtn").on("click", function () {
        loadDict();
    });

    $("#addBtn").on('click', function () {
        stopEdit();
        $("#dg").datagrid('appendRow', {"hospitalId":config.hospitalId,"appId":config.appId});
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
            $.messager.alert('系统警告', "请选择要删除的行", 'error');
        }
    });

    $("#editBtn").on('click', function () {
        var row = $("#dg").datagrid("getSelected");
        var index = $("#dg").datagrid("getRowIndex", row);

        if (index == -1) {
            $.messager.alert("系统警告", "请选择要修改的行！", "error");
            return;
        }

        if (editIndex == undefined) {
            $("#dg").datagrid("beginEdit", index);
            editIndex = index;
        } else {
            $("#dg").datagrid("endEdit", editIndex);
            $("#dg").datagrid("beginEdit", index);
            editIndex = index;
        }
    });

    var loadDict = function () {
        //var name = $("#name").textbox("getValue");

        $.get("/api/clinic-type-setting/list?hospitalId="+config.hospitalId , function (data) {
            $("#dg").datagrid('loadData', data);
        });
    }

    loadDict();

    /**
     * 保存修改的内容
     * 基础字典的改变，势必会影响其他的统计查询
     * 基础字典的维护只能在基础数据维护的时候使用。
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
            $.postJSON("/api/clinic-type-setting/merge", beanChangeVo, function (data, status) {
                $.messager.alert("系统提示", "保存成功", "info");
                loadDict();
            }, function (data) {
                $.messager.alert('系统警告', data.responseJSON.errorMessage, "error");
                loadDict();
            })
        }
    });
})