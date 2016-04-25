/**
 * Created by heren on 2015/9/16.
 */
/***
 * 消耗品产品编码描述字典维护
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
        title: '调查问卷列表',
        fit: true,//让#dg数据创铺满父类容器
        footer: '#tb',
        singleSelect: true,
        columns: [[{
            title: '编号',
            field: 'id',
            width: "20%"
        }, {
            title: '问卷名',
            field: 'title',
            width: "20%"
        }, {
            title: '问卷描述',
            field: 'memo',
            width: "20%"
        },{
            title: '创建人',
            field: 'createPerson',
            width: "10%"
        },{
            title: '总题数',
            field: 'totalNumbers',
            width: "10%"
        },
            {
                title: '所属微信号',
                field: 'appId',
                width: "20%"
            },

        ]],
        onClickRow: function (index, row) {
            stopEdit();
            $(this).datagrid('beginEdit', index);
            editIndex = index;
        }
    });

    var editIndexDg;
    var stopEditDg = function () {
        if (editIndexDg || editIndexDg == 0) {
            $("#dgOption").datagrid('endEdit', editIndexDg);
            editIndexDg = undefined;
        }
    }

    $("#dgOption").datagrid({
        singleSelect: true,
        columns: [[{
            title: '编号',
            field: 'id',
            width: "10%"
        },{
            title: '题目',
            field: 'questionContent',
            width: "40%",
            editor: {
                type: 'combogrid', options: {
                    mode: 'remote',
                    url: '/api/subject/list',
                    singleSelect: true,
                    method: 'GET',
                    panelWidth: 400,
                    idField: 'questionContent',
                    textField: 'questionContent',
                    columns: [[{
                        title: '编号',
                        field: 'id',
                        hidden: 'true'
                    }, {
                        title: '题目',
                        field: 'questionContent',
                        width: "40%"
                    }, {
                        title: '题目类型',
                        field: 'questionType',
                        width: "20%"
                    }, {
                        title: '答案',
                        field: 'preAnswer',
                        width: "20%"
                    },{
                        title: '图片',
                        field: 'img',
                        width: '20%'
                    }]],
                    onClickRow: function (index, row) {
                        $('#dgOption').datagrid('updateRow',{
                            index: editIndexDg,
                            row: {
                                id: row.id,
                                questionContent: row.questionContent,
                                questionType:row.questionType
                            }
                        });
                    },
                    keyHandler: $.extend({}, $.fn.combogrid.defaults.keyHandler, {
                        enter: function (e) {
                            $('#dgOption').datagrid('updateRow',{
                                index: editIndexDg,
                                row: {
                                    id: row.id,
                                    questionContent: row.questionContent,
                                    questionType:row.questionType
                                }
                            });
                        }
                    })
                }
            }
        }, {
            title: '题目类型',
            field: 'questionType',
            width: "40%"
        }]],
        onClickRow: function (index, row) {
            stopEditDg();
            $(this).datagrid('beginEdit', index);
            editIndexDg = index;
        }
    });

    $("#dgOptionShow").datagrid({
        singleSelect: true,
        columns: [[{
            title: '编号',
            field: 'id',
            width: "10%"
        },{
            title: '题目',
            field: 'questionContent',
            width: "40%"
        }, {
            title: '题目类型',
            field: 'questionType',
            width: "40%"
        }]]
    });


    $("#addOptionBtn").on("click",function(){
        $("#dgOption").datagrid('appendRow', {});
    });

    $("#delOptionBtn").on("click",function(){
        var row = $("#dgOption").datagrid('getSelected');
        if (row) {
            var rowIndex = $("#dgOption").datagrid('getRowIndex', row);
            $("#dgOption").datagrid('deleteRow', rowIndex);
            if (editIndexDg == rowIndex) {
                editIndexDg = undefined;
            }
        } else {
            $.messager.alert('系统提示', "请选择要删除的行", 'info');
        }
    });
    var reset = function(){
        $("#id").textbox("setValue","");
        $("#title").textbox("setValue","");
        $("#memo").textbox("setValue","");
        $("#createPerson").textbox("setValue","");
        $("#createPerson").textbox("setValue","");
        $("#appId").textbox("setValue","");
        var data=[];
        $("#dgOption").datagrid("loadData",data);
    }

    //新增按钮
    $("#addBtn").on('click', function () {
        reset();
        $('#dlg').dialog('open').dialog('center').dialog('setTitle', '新建调查问卷');
    });

    //修改按钮
    $("#editBtn").on('click', function () {
        var row = $("#dg").datagrid('getSelected');
        if (row) {
            $.get('/api/questionnaire-model/find-by-id?id='+row.id, function (node) {
                console.log(node.id);
                $("#id").textbox('setValue', node.id);
                $("#title").textbox('setValue', node.title);
                $("#memo").textbox('setValue', node.memo);
                $("#totalNumbers").textbox('setValue',node.totalNumbers);
                $("#createPerson").textbox('setValue',node.createPerson);
                $("#appId").textbox('setValue',node.appId);
                $("#dgOption").datagrid('loadData', node.questionnaireVsSubjectVo);
                $('#dlg').dialog('open').dialog('center').dialog('setTitle', '问卷详情');

            })
        } else {
            $.messager.alert('系统提示', "请选择要编辑的行", 'info');
        }
    });

    //弹出框确认按钮，实现新增和修改的提交
    $("#okBtn").on('click', function () {

        var questionnaireModel = {};
        questionnaireModel.id = $("#id").val();
        questionnaireModel.title = $("#title").val();
        questionnaireModel.memo = $("#memo").val();
        questionnaireModel.createPerson = $("#createPerson").val();
        questionnaireModel.appId = $("#appId").val();
        var rows = $("#dgOption").datagrid("getRows");
        var subIds="";
        $.each(rows,function(index,item){
            subIds +=item.id+";";
        })
        if(subIds.length>0){
            subIds = subIds.substr(0,subIds.length-1);
        }

        questionnaireModel.subIds = subIds;
        console.log(questionnaireModel);
        $.postJSON("/api/questionnaire-model/add", questionnaireModel, function (data) {
            $('#dlg').dialog('close');
            $.messager.alert("系统提示", "保存成功", "info");
            loadDict();
            reset();
        });
    })


    //删除
    $("#delBtn").on('click', function () {
        var row = $("#dg").datagrid('getSelected');
        if (row) {
            $.messager.confirm("系统提示", "确认要删除吗?", function (r){
                if(r){
                    var modelId=row.id;
                    var rowIndex = $("#dg").datagrid('getRowIndex', row);
                    $("#dg").datagrid('deleteRow', rowIndex);
                    if (editIndex == rowIndex) {
                        editIndex = undefined;
                    }
                    $.ajax({
                        type:"POST",
                        url:"/api/questionnaire-model/del?modelId="+modelId,
                        cache:false,
                        success:function(data){
                            if(data.isSuccess){
                                $.messager.alert("系统提示",data.msg, "info");
                            }else{
                                $.messager.alert('系统提示', data.msg, "error");
                            }
                        }
                    });
                }else{
                    return
                }
            });
        } else {
            $.messager.alert('系统提示', "请选择要删除的行", 'info');
        }
    });


    //获取model表的全部数据
    var loadDict = function () {
        $.get("/api/questionnaire-model/list-all" , function (data) {
            $("#dg").datagrid('loadData', data);
        });
    }
    loadDict();


    $("#detailBtn").on('click', function () {
        var row = $("#dg").treegrid('getSelected');

        if (row){
            $.get('/api/questionnaire-model/find-by-id?id='+row.id, function (node) {
                $("#idShow").textbox('setValue', node.id);
                $("#titleShow").textbox('setValue', node.title);
                $("#memoShow").textbox('setValue', node.memo);
                $("#totalNumbersShow").textbox('setValue',node.totalNumbers);
                $("#createPersonShow").textbox('setValue',node.createPerson);
                $("#appIdShow").textbox('setValue',node.appId);
                $("#dgOptionShow").datagrid('loadData', node.questionnaireVsSubjectVo);
                $('#dlg-detail').dialog('open').dialog('center').dialog('setTitle', '问卷详情');
            })
        }else{
            $.messager.alert("系统提示", "请选择要查看的行");
            return;
        }
    });

    $("#dlgClose").on('click',function(){
        $('#dlg').dialog('close');
        reset();
    })
})