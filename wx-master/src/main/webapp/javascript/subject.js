$(function () {
    var editIndex;
    var stopEdit = function () {
        if (editIndex || editIndex == 0) {
            $("#dgOption").datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
    }

    $("#dgOption").datagrid({
        singleSelect: true,
        columns: [[{
            title: '编号',
            field: 'id',
            hidden: 'true'
        },{
            title: 'sub编号',
            field: 'subjectId',
            hidden: 'true'
        }, {
            title: '选项',
            field: 'optContent',
            width: "50%",
            editor: 'text'
        },{
            title: '图片路径',
            field: 'image',
            width: "50%",
            editor: 'text'
        }]],
        onClickRow: function (index, row) {
            stopEdit();
            $(this).datagrid('beginEdit', index);
            editIndex = index;
        }
    });


    $("#dgOptionShow").datagrid({
        singleSelect: true,
        columns: [[{
            title: '编号',
            field: 'id',
            hidden: 'true'
        },{
            title: 'sub编号',
            field: 'subjectId',
            hidden: 'true'
        }, {
            title: '选项',
            field: 'optContent',
            width: "50%",
            editor: 'text'
        },{
            title: '图片路径',
            field: 'image',
            width: "50%",
            editor: 'text'
        }]]
    });


    var editIndexDg;
    var stopEditDg = function () {
        if (editIndexDg || editIndexDg == 0) {
            $("#dg").datagrid('endEdit', editIndexDg);
            editIndexDg = undefined;
        }
    }
    $("#dg").datagrid({
        title: '问题管理',
        fit: true,//让#dg数据创铺满父类容器
        footer: '#tb',
        singleSelect: true,
        columns: [[{
            title: '编号',
            field: 'id',
            hidden: 'true'
        }, {
            title: '题目列表',
            field: 'questionContent',
            width: "30%"

        }, {
            title: '题目类型',
            field: 'questionType',
            width: "30%"
        }, {
            title: '答案',
            field: 'preAnswer',
            width: "40%"

        }]],
        onClickRow: function (index, row) {
            stopEditDg();
            $(this).datagrid('beginEdit', index);
            editIndexDg = index;
        }
    });

    var loadDict = function () {
        $.get("/api/subject/list" , function (data) {
            $("#dg").datagrid('loadData', data);
        });
    }

    loadDict();

    var reset = function(){
        $("#questionContent").textbox('setValue',"");
        $("#questionType").textbox('setValue',"");
        $("#preAnswer").textbox('setValue',"");
        $("#subId").textbox('setValue',"");
        $("#img").textbox('setValue',"");
        var data=[];
        $("#dgOption").datagrid("loadData",data);
    }

    $("#searchBtn").on("click", function () {
        loadDict();
    });
    $("#addBtn").on('click', function () {
        reset();
        $('#dlg').dialog('open').dialog('center').dialog('setTitle', '添加问题选项');
    });

    $("#editBtn").on('click', function () {
        var row = $("#dg").datagrid('getSelected');
        if (row) {
            $.get('/api/subject/find-by-id?id='+row.id, function (node) {
                $('#dlg').dialog('open').dialog('center').dialog('setTitle', '修改问题');
                $("#subId").textbox('setValue', node.id);
                $("#questionContent").textbox('setValue', node.questionContent);
                $("#questionType").combobox('setValue', node.questionType);
                $("#preAnswer").textbox('setValue', node.preAnswer);
                $("#img").textbox('setValue', node.img);
                $("#dgOption").datagrid('loadData', node.options);
            })
        } else{
            $.messager.alert('系统提示', "请选择要编辑的行", 'info');
        }
    });
    //增加修改提交
    $("#submitBtn").on('click', function () {

        if (editIndex || editIndex == 0) {
            $("#dgOption").datagrid("endEdit", editIndex);
        }
        if ($("#fm").form('validate')) {
            var menuDict = {};
            menuDict.questionContent = $("#questionContent").textbox('getValue');
            menuDict.questionType = $("#questionType").combobox('getValue');
            menuDict.preAnswer = $("#preAnswer").textbox('getValue');
            menuDict.img = $("#img").textbox('getValue');
            menuDict.id = $("#subId").val();

            var data =$("#dgOption").datagrid("getRows");
            menuDict.subjectOptionses = data;

            console.log(menuDict);

            $.postJSON("/api/subject/save", menuDict, function (data) {
                $('#dlg').dialog('close');
                $.messager.alert("系统提示", "保存成功", "info");
                loadDict();
                reset();
            }, function (data, status) {
            })
        }

    });
    //删除
    $("#delBtn").on('click', function () {
        var row = $("#dg").datagrid('getSelected');
        if (row) {
            var rowIndex = $("#dg").datagrid('getRowIndex', row);
            $("#dg").datagrid('deleteRow', rowIndex);
            if (editIndexDg == rowIndex) {
                editIndexDg = undefined;
            }
        } else {
            $.messager.alert('系统提示', "请选择要删除的行", 'info');
        }
    });
    $("#saveBtn").on('click', function () {
        if (editIndexDg || editIndexDg == 0) {
            $("#dg").datagrid("endEdit", editIndexDg);
        }
        var deleteDate = $("#dg").datagrid("getChanges", "deleted");

        var beanChangeVo = {};

        beanChangeVo.deleted = deleteDate;

        if (beanChangeVo) {
            $.postJSON("/api/subject/delete", beanChangeVo, function (data, status) {

                $.messager.alert("系统提示", "删除成功", "info");
                loadDict();
            }, function (data) {
                $.messager.alert('提示', data.responseJSON.errorMessage, "error");
            });
        }
    });



    $("#findByIdBtn").on('click', function () {
        var row = $("#dg").treegrid('getSelected');

        if (row){
            $.get('/api/subject/find-by-id?id='+row.id, function (node) {
                $('#dlg-detail').dialog('open').dialog('center').dialog('setTitle', '问题详情');
                $("#questionContentShow").textbox('setValue', node.questionContent);
                $("#questionTypeShow").combobox('setValue', node.questionType);
                $("#preAnswerShow").textbox('setValue', node.preAnswer);
                $("#image").textbox('setValue', node.img);
                $("#dgOptionShow").datagrid('loadData', node.options);
            });
        }else{
            $.messager.alert("系统提示", "请选择要查看的行");
            return;
        }
    });



    $("#addOptionBtn").on("click",function(){
        $("#dgOption").datagrid('appendRow', {});
    });

    $("#delOptionBtn").on("click",function(){
        var row = $("#dgOption").datagrid('getSelected');
        if (row) {
            var rowIndex = $("#dgOption").datagrid('getRowIndex', row);
            $("#dgOption").datagrid('deleteRow', rowIndex);
            if (editIndex == rowIndex) {
                editIndex = undefined;
            }
        } else {
            $.messager.alert('系统提示', "请选择要删除的行", 'info');
        }
    });

});