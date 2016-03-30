$(function () {
    var editIndex;
    var clinicTypeSettingId;
    var stopEdit = function () {
        if (editIndex || editIndex == 0) {
            $("#dg").datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
    }
    $("#dg").datagrid({
        title: '号别',
        fit: true,
        singleSelect: true,
        columns: [[{
            title: '编号',
            field: 'id',
            hidden:'true'
        }, {
            title: '号别名称',
            field: 'clinicLabel',
            width: "30%",
            editor: 'text'
        }, {
            title: '所属科室',
            field: 'clinicDept',
            width: "30%",
            editor: 'text'
        }, {
            title: '所属医生',
            field: 'doctorId',
            width: "40%",
            editor: {
                type: 'combogrid', options:{
                    panelWidth: 350,
                    idField: 'id',
                    textField: 'name',
                    loadMsg: '数据正在加载',
                    url: "/api/doct-info/get-list",
                    mode: 'listDoc',
                    method: 'GET',
                    columns: [[
                        {field: 'id', title: '编码', width: 145, align: 'center'},
                        {field: 'name', title: '姓名', width: 145, align: 'center'},
                    ]],
                    pagination: false,
                    fitColumns: true,
                    rowNumber: true,
                    autoRowHeight: false
                }

            }
        }, {
            title: '所属号类',
            field: 'clinicTypeId',
            hidden: 'true'
        }]],
        onClickRow: function (index, row) {
            stopEdit();
            $(this).datagrid('beginEdit', index);
            editIndex = index;
        }
    });

    //门诊号类数据表格
    $("#typeList").datagrid({
        url: "/api/clinic-type-setting/list",
        mode: 'typeListAll',
        method: 'GET',
        singleSelect: true,
        columns: [[
            {
                title: 'ID',
                field: 'id',
                hidden:true
            },{
                title: '号类名称',
                field: 'clinicType',
                width:'100%'
            }]],

        onClickRow: function (rowIndex, rowData) {
            clinicTypeSettingId = rowData.id;
            $.get("/api/clinic-index/findByTypeId", {typeId: rowData.id}, function (data) {
                $("#dg").datagrid('loadData', data);
            });
        }
    });

    $("#bottom").datagrid({
        footer: '#tb',
        border: false
    });

    $('#cc').layout('panel', 'north').panel('resize', {height: 'auto'});
    $('#cc').layout('panel', 'south').panel('resize', {height: 'auto'});

    $("#cc").layout({
        fit: true
    });

    var loadGroup = function () {
        $.get("/api/app-user-group/list-all", function (data) {
            $("#left").datagrid('loadData', data);
        });
    };
    $("#addBtn").on('click', function () {
        if(null!=clinicTypeSettingId){
            $("#dg").datagrid('appendRow', {clinicTypeId:clinicTypeSettingId});
            var rows = $("#dg").datagrid('getRows');
            var addRowIndex = $("#dg").datagrid('getRowIndex', rows[rows.length - 1]);
            editIndex = addRowIndex;
            $("#dg").datagrid('selectRow', editIndex);
            $("#dg").datagrid('beginEdit', editIndex);
        }else{
            $.messager.alert("系统警告","请选择号类","error");
        }
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
        //var name = $("#name").textbox("getValue");

        $.get("/api/clinic-index/list-all" , function (data) {
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
            $.postJSON("/api/clinic-index/merge", beanChangeVo, function (data, status) {
                $.messager.alert("系统提示", "保存成功", "info");
                loadDict();
            }, function (data) {
                $.messager.alert('提示', data.responseJSON.errorMessage, "error");
            })
        }
    });

})