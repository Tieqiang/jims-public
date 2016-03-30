/**
 * Created by yg on 2016/3/28.
 */
/**
 * Created by Fyg on 2016/3/23.
 */
/**
 * 门诊号类收费维护
 */
$(function(){
    var editIndex;
    var clinicTypeSettingId;    //号类ID
    var stopEdit = function () {
        if (editIndex || editIndex == 0) {
            $("#sf").datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
    }

    /**
     * 加载数据表格。查询所有号类记录
     */
    var loadClinicType = function (data) {
        $.get("/api/clinic-type-charge/findAllClinicType" , function (data) {
            $("#dg").datagrid('loadData', data);
        });
    };

    loadClinicType();

    //门诊号类数据表格
    $("#dg").datagrid({
        singleSelect: true,
        title:'门诊号类',
        fit:true,
        columns: [[
            {
                title: 'ID',
                field: 'id',
                hidden:true
            },{
                title: '号类名称',
                field: 'clinicType',
                width:'100%'
            }/*,{
                title: '所属医院ID',
                field: 'hospitalId',
                hidden:true
            },{
                title: '所属公众号',
                field: 'appId',
                hidden:true
            }*/]],
            //用户点击一行时触发事件加载此号类下的所有收费列表
            onClickRow: function (rowIndex, rowData) {
                clinicTypeSettingId = rowData.id;   //号类ID赋值
                $.get("/api/clinic-type-charge/findById", {id: rowData.id}, function (data) {
                    $("#sf").datagrid('loadData', data);
                });
            }
    });

    //门诊号类收费列表数据表格
    $("#sf").datagrid({
        singleSelect: true,
        fit:true,
        nowrap:false,
        fitColumns:true,
        title:'门诊号类收费列表',
        columns: [[
            {
                title: 'ID',
                field: 'id',
                hidden:true
            },{
                title: '诊疗项目名称',
                field: 'clinicTypeName',
                width:'20%',
                editor:'text'
            },{
                title: '诊疗项目代码',
                field: 'clinicTypeCode',
                width:'20%',
                editor:'text'
            },{
                title: '收费项目名称',
                field: 'chargeItem',
                width:'20%',
                editor:'text'
            },{
                title: '收费项目代码',
                field: 'priceItem',
                width:'20%',
                editor:'text'
            },{
                title: '费用',
                field: 'price',
                width:'20%',
                editor: {
                    type: 'numberbox',
                    options: {
                        max: 99999.99,
                        size: 8,
                        maxlength: 8,
                        precision: 2
                    }
                }
            },{
                title: '所属号类',
                field: 'clinicTypeId',
                width:'20%',
                hidden:true
            }]],
            //用户点击一行时触发事件编辑收费列表
            onClickRow: function (rowIndex, rowData) {
                stopEdit();
                $(this).datagrid('beginEdit', rowIndex);
                editIndex = rowIndex;
            }
    });

    $("#bottom").datagrid({
        footer: '#ft',
        border: false
    });
    $('#cc').layout('panel', 'south').panel('resize', {height: 'auto'});

    $("#cc").layout({
        fit: true
    });

    /**
     * 增加门诊号类收费
     */
    $("#addBtn").on('click',function(){
        if (null != clinicTypeSettingId) {
            stopEdit();
            $('#sf').datagrid('appendRow', {clinicTypeId: clinicTypeSettingId});
            var rows = $("#sf").datagrid('getRows');
            var addRowIndex = $("#sf").datagrid('getRowIndex', rows[rows.length - 1]);
            editIndex = addRowIndex;
            $("#sf").datagrid('selectRow', editIndex);  //选择新增加的一行
            $("#sf").datagrid('beginEdit', editIndex);  //开始编辑新增加的行数据
        } else {
            $.messager.alert("系统警告", "请选择号类", "error");
        }
    });

    /**
     * 删除门诊号类收费
     */
    $("#delBtn").on('click', function () {
        var row = $("#sf").datagrid('getSelected');	//获取被选中的行，即要删除的那行
        if (row) {
            var rowIndex = $("#sf").datagrid('getRowIndex', row);
            $("#sf").datagrid('deleteRow', rowIndex);
            if (editIndex == rowIndex) {
                editIndex = undefined;
            }
        } else {
            $.messager.alert('系统警告', "请选择要删除的行", 'error');
        }
    });

    /**
     * 修改门诊号类收费
     */
    /*$("#editBtn").on('click', function () {
        var row = $("#sf").datagrid("getSelected");     //指定行
        var index = $("#sf").datagrid("getRowIndex", row);  //指定行索引

        if (index == -1) {
            $.messager.alert("系统警告", "请选择要修改的行！", "error");
            return;
        }

        if (editIndex == undefined) {
            $("#sf").datagrid("beginEdit", index);
            editIndex = index;
        } else {
            $("#sf").datagrid("endEdit", editIndex);
            $("#sf").datagrid("beginEdit", index);
            editIndex = index;
        }
    });*/

    /**
     * 保存改动的内容
     */
    $("#saveBtn").on('click', function () {
        if (editIndex || editIndex == 0) {
            $("#sf").datagrid("endEdit", editIndex);
        }

        var insertData = $("#sf").datagrid("getChanges", "inserted");
        var updateDate = $("#sf").datagrid("getChanges", "updated");
        var deleteDate = $("#sf").datagrid("getChanges", "deleted");

        var beanChangeVo = {};
        beanChangeVo.inserted = insertData;
        beanChangeVo.deleted = deleteDate;
        beanChangeVo.updated = updateDate;
        console.log(beanChangeVo);
        if (beanChangeVo) {
            $.postJSON("/api/clinic-type-charge/merge", beanChangeVo, function (data, status) {
                $.messager.alert("系统提示", "保存成功", "info");
                loadClinicType();
            }, function (data) {
                $.messager.alert('系统警告', data.responseJSON.errorMessage, "error");
            })
        }
    });
});
