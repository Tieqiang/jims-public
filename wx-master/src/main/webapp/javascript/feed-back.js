/**
 * created by chenxiaoyang on 2016.03.29
 */
$(function () {
    var flag;

    /**
     * load datagrid data
     */
    var loadDict = function () {
        $.get("/api/feed-back/find-all", function (data) {
            $("#t_user").datagrid('loadData', data);
        });
    }


    loadDict();

    var reset = function(){
        $("#docId").textbox("setValue","");
        $("#name").textbox("setValue","");
//        $("#bodyPartId").textbox("setValue","");
    }

    /**
     * init datagrid
     */
    $('#t_user').datagrid({
        idField: 'id',
        title: '就医反馈问题维护列表',
        footer: '#tb',
        fit: true,
        fitColumns: true,
        singleSelect : true,
        striped: true,
        loadMsg: '数据正在加载,请耐心的等待...',
        remoteSort: true,
        columns: [[{
            field: 'id',
            title: '编号',
            hidden:'true'
        },{
            field: 'feedTarget',
            title: '反馈对象',
            width: "20%"

        }, {
            field: 'questionContent',
            title: '问题内容',
            width: "40%"

        },{
            field: 'optionContent',
            title: '选项内容',
            width: "40%"

        }]]
    });

    /**
     *  button of submit click
     *
     *
     *   private String feedTarget;

     private String optionContent;

     private String questionContent;
     */
    $("#submitBtn").on('click', function () {
        if ($("#fm").form('validate')) {
            var feedBackTarget = {};
            feedBackTarget.id = $("#docId").val();
            feedBackTarget.feedTarget = $("#feedTarget").val();
            feedBackTarget.questionContent =$("#questionContent").val();
            feedBackTarget.optionContent =$("#optionContent").val();
        }
        $.postJSON("../../../api/feed-back/save", feedBackTarget, function (data) {
            $('#dlg').dialog('close');
            $.messager.alert("系统提示", "修改成功！","info");
            loadDict();
            $("#fm").get(0).reset();
        }, function (data, status) {
        })

    });
    /**
     * button of edit click
     */
    $("#editBtn").on('click', function () {
        flag = "edit";
        var arr = $('#t_user').datagrid('getSelections');
        if (arr.length != 1) {
            $.messager.alert("系统警告", "请选择一条记录进行修改！","error");
        }
        else {
            $('#dlg').dialog({
                title: '修改症状信息'
            });
            $("#dlg").dialog('open');
            $("#docId").val(arr[0].id);
            $("#feedTarget").textbox("setValue",arr[0].feedTarget);
            $("#questionContent").textbox("setValue",arr[0].questionContent);
            $("#optionContent").textbox("setValue",arr[0].optionContent);
         }
    });
 });

