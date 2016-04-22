$(function () {
    var editIndex;
    var modelId;
    //定义右侧多个操作的增删改数据
    var stopEdit = function () {
        if (editIndex || editIndex == 0) {
            $("#dg").datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
    };
    //右侧答题记录
    $("#dg").datagrid({
        title: '号别',
        fit: true,
        singleSelect: true,
        columns: [[{
            title: '编号',
            field: 'id',
            width: "30%"
        }, {
            title: '调查问卷Id',
            field: 'questionnaireId',
            hidden: 'true'
        }, {
            title: '微信号',
            field: 'openId',
            width: "15%"
        }, {
            title: '身份证号',
            field: 'patId',
            width: "25%"
        }, {
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
        width: '250px',
        url: "/api/questionnaire-model/list-all",
        mode: 'remote',
        method: 'GET',
        singleSelect: true,
        columns: [[
            {
                title: 'ID',
                field: 'id',
                hidden: true
            }, {
                title: '问卷名称',
                field: 'title',
                width: '100%'
            }
        ]],
        onClickRow: function (rowIndex, rowData) {
            modelId = rowData.id;
            if (editIndex || editIndex == 0) {
                $("#dg").datagrid("endEdit", editIndex);
            }
            $.get("/api/answer-sheet/find-by-model-id?modelId=" + modelId, function (data) {
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
    $("#tree").treegrid({
        idField: "id",
        treeField: "questionContent",
        fitColumns: true,
        title: "问卷题目列表",
        columns: [[{
            title: 'id',
            field: 'id',
            hidden: true
        }, {
            title: '名称',
            field: 'questionContent',
            width: "60%"
        }, {
            title: '图案',
            field: 'picture',
            width: "40%"
        }]]
    });
    $("#detailBtn").on('click', function () {
        var treeDepts = [];
        var row = $("#dg").datagrid('getSelected');
        if (row) {
            $.get('/api/answer-sheet/find-by-id?id=' + row.id, function (select) {
                console.log(select);
                $.get('/api/questionnaire-model/find-by-id?id=' + row.questionnaireId, function (node) {
                    $.each(node.questionnaireVsSubjectVo, function (index, item) {
                        var obj = {};
                        obj.id = item.id;
                        obj.questionContent = item.questionContent;
                        if (null != item.picture) {
                            obj.picture = "<img src='" + item.picture + "'style='width:50px;' >";
                        } else {
                            obj.picture = null;
                        }
                        obj.state = "open";
                        var children = [];
                        for (var j = 0; j < item.subjectOptionses.length; j++) {
                            var opt = item.subjectOptionses[j];
                            var child = {};
                            child.id = opt.id;
                            var firstText = "○ ";
                            for (var x = 0; x < select.length; x++) {
                                if (select[x].answer == opt.id) {
                                    firstText = "● ";
                                }
                            }
                            opt.optContent = firstText + opt.optContent;
                            child.questionContent = opt.optContent;


                            if (null != opt.image) {
                                child.picture = "<img src='" + opt.image + "'style='width:50px;' >";
                            } else {
                                child.picture = null;
                            }
                            children.push(child);
                        }

                        obj.children = children;
                        treeDepts.push(obj);
                    });
                    console.log(treeDepts);
                    $('#dlg-detail').dialog('open').dialog('center').dialog('setTitle', ' 答题详情');
                    $("#tree").treegrid('loadData', treeDepts);
                })
            })

        } else {
            $.messager.alert("系统提示", "请选择要查看的行");
        }
    })

});
