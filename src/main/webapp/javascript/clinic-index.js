$(function () {

    var editIndex;
    var clinicTypeSettingId;

    //定义右侧多个操作的增删改数据
    var inserted = [];
    var updated = [];
    var deleted = [];

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
            editor: {
                type: 'combogrid', options:{
                    panelWidth: 350,    //下拉面板宽度
                    idField: 'deptCode',
                    textField: 'deptName',
                    loadMsg: '数据正在加载',
                    url: "/api/dept-dict/list?hospitalId="+config.hospitalId,   //一个URL从远程站点请求数据
                    mode: 'listDoc',
                    method: 'GET',
                    columns: [[
                        {field: 'deptCode', title: '科室代码', width: 145, align: 'center'},
                        {field: 'deptName', title: '科室名称', width: 145, align: 'center'},
                    ]],
                    pagination: false,  //控件底部不显示分页工具栏
                    fitColumns: true,   //自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动。
                    rowNumber: true,
                    autoRowHeight: false
                }
            }
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
        title: '门诊号类',
        width:'250px',
        url: "/api/clinic-type-setting/list?hospitalId="+config.hospitalId,
        mode: 'remote',
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
            }
        ]],
        onClickRow: function (rowIndex, rowData) {
            clinicTypeSettingId = rowData.id;
            if (editIndex || editIndex == 0) {
                $("#dg").datagrid("endEdit", editIndex);
            }

            //保存刷新之前的右侧datagrid里面的增删改数据倒数组里面
            if($("#dg").datagrid("getChanges").length>0){

                var insertData = $("#dg").datagrid("getChanges", "inserted");
                var updateData = $("#dg").datagrid("getChanges", "updated");
                var deleteData = $("#dg").datagrid("getChanges", "deleted");
                console.log(insertData);
                if(insertData && insertData.length >0){
                    for(var i =0;i<insertData.length;i++){

                        inserted.push(insertData[i]);
                    }
                }
                if(updateData && updateData.length >0){
                    for(var i =0;i<updateData.length;i++){
                        updated.push(updateData[i]);
                    }
                }
                if(deleteData && deleteData.length >0){
                    for(var i =0;i<deleteData.length;i++){
                        deleted.push(deleteData[i]);
                    }
                }
            }
            $.get("/api/clinic-index/find-by-type-id", {typeId: rowData.id}, function (data) {
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

    $("#addBtn").on('click', function () {
        stopEdit();
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
         $.get("/api/clinic-index/find-by-type-id?typeId="+ clinicTypeSettingId , function (data) {
             console.log(data)
             $("#dg").datagrid('loadData', data);
        });
        inserted = [];
        updated=[];
        deleted=[];
    }


    /**
     * 保存修改的内容
     */
    $("#saveBtn").on('click', function () {
        if (editIndex || editIndex == 0) {
            $("#dg").datagrid("endEdit", editIndex);
        }

        var insertData = $("#dg").datagrid("getChanges", "inserted");
        var updateData = $("#dg").datagrid("getChanges", "updated");
        var deleteData = $("#dg").datagrid("getChanges", "deleted");

        if(insertData && insertData.length >0){
            for(var i =0;i<insertData.length;i++){
                inserted.push(insertData[i]);
            }
        }
        if(updateData && updateData.length >0){
            for(var i =0;i<updateData.length;i++){
                updated.push(updateData[i]);
            }
        }
        if(deleteData && deleteData.length >0){
            for(var i =0;i<deleteData.length;i++){
                deleted.push(deleteData[i]);
            }
        }
        //提交右侧刷新过的多个datagrid的增删改数据
        var beanChangeVo = {};

        beanChangeVo.inserted =inserted;// inserted;
        beanChangeVo.deleted =deleted; //deleted;
        beanChangeVo.updated =updated; //updated;

        if (beanChangeVo) {
            $.postJSON("/api/clinic-index/merge", beanChangeVo, function (data, status) {
                $.messager.alert("系统提示", "保存成功", "info");
                loadDict();
            }, function (data) {
                $.messager.alert('提示', data.responseJSON.errorMessage, "error");
                loadDict();
            })

        }

    });
})