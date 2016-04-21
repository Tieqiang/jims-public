$(function () {

    var editIndex;
    var modelId;

    //定义右侧多个操作的增删改数据
    var stopEdit = function () {
        if (editIndex || editIndex == 0) {
            $("#dg").datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
    }

    //右侧答题记录
    $("#dg").datagrid({
        title: '号别',
        fit: true,
        singleSelect: true,
        columns: [[{
            title: '编号',
            field: 'id',
            width: "30%"
        },{
            title: '微信号',
            field: 'openId',
            width: "15%"
        },{
            title: '身份证号',
            field: 'patId',
            width: "25%"
        },{
            title: '答题时间',
            field: 'createTime',
            width: "30%"
            }
        ]],
        onClickRow: function (index, row) {
            stopEdit();
            $(this).datagrid('beginEdit', index);
            editIndex = index;
        }
    });

    //左侧问卷列表
    $("#typeList").datagrid({
        title: '问卷列表',
        width:'250px',
        url: "/api/questionnaire-model/list-all",
        mode: 'remote',
        method: 'GET',
        singleSelect: true,
        columns: [[
            {
                title: 'ID',
                field: 'id',
                hidden:true
            },{
                title: '问卷名称',
                field: 'title',
                width:'100%'
            }
        ]],
        onClickRow: function (rowIndex, rowData) {
            modelId = rowData.id;
            if (editIndex || editIndex == 0) {
                $("#dg").datagrid("endEdit", editIndex);
            }
            $.get("/api/answer-sheet/find-by-model-id?modelId="+modelId, function (data) {
                $("#dg").datagrid('loadData', data);
                console.log(data);
            });
        }
    });







    //弹出答题详情
    $("#dgShow").treegrid({
        fit: true,
        idField: "subjectId",
        treeField: "subjectName",
        fitColumns: true,
        title: "问卷",
        columns: [[{
            title: 'id',
            field: 'id',
            hidden:true
        },{
            title: '名称',
            field: 'preAnswer',
            width:"60%"
        },{
            title: '科室',
            field: 'subjectId',
            width:"40%"
        }]],
        onClickRow: function (row) {

            if(row.parentFlag!="Y"){
                clinicIndexId = row.id;   //号类ID赋值
            }else{
                clinicIndexId = null;   //号类ID赋值
            }

            addData();

            $.get("/api/answer-sheet/find-by-model-id", {id: row.id}, function (data) {
                $("#dg").datagrid('loadData', data);
            });

        }
    });


    //弹出答题详情
    //$("#dgOptionShow").datagrid({
    //    title: '答题列表',
    //    width:'100%',
    //    singleSelect: true,
    //    columns: [[
    //        {
    //            title: '题目',
    //            field: 'subjectName',
    //            width:'33%'
    //        },{
    //            title: '答题人选择',
    //            field: 'answerContent',
    //            width:'33%'
    //        },{
    //            title: '默认答案',
    //            field: 'preAnswer',
    //            width:'33%'
    //        }
    //    ]]
    //});


    $("#bottom").datagrid({
        footer: '#tb',
        border: false
    });

    $('#cc').layout('panel', 'north').panel('resize', {height: 'auto'});
    $('#cc').layout('panel', 'south').panel('resize', {height: 'auto'});
    $("#cc").layout({
        fit: true
    });

    //详情按钮
    $("#detailBtn").on('click', function () {
        var row = $("#dg").datagrid('getSelected');
        if (row){
            $.get('/api/answer-sheet/find-by-id?id='+row.id, function (node) {
                console.log(node);

                $('#dlg-detail').dialog('open').dialog('center').dialog('setTitle', ' 答题详情');
                console.log(node);
                $("#dgShow").treegrid('loadData', node);
            })
        }else{
            $.messager.alert("系统提示", "请选择要查看的行");
        }
    });
})
