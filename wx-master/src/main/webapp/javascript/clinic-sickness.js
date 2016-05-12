/**
 * created by chenxiaoyang on 2016.03.29
 */
$(function () {
    var flag;

    /**
     * load datagrid data
     */
    var loadDict = function () {
        $.get("/api/intelligent-guide/find-sickness", function (data) {
            $("#t_user").datagrid('loadData', data);
        });
    }


    loadDict();

    var reset = function(){
        $("#docId").textbox("setValue","");
        $("#name").textbox("setValue","");
//        $("#deptId").textbox("setValue","");
    }
     /**
     * init datagrid
     */
    $('#t_user').datagrid({
        idField: 'id',
        title: '疾病维护列表',
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
        }, {
            field: 'deptId',
            title: '外键',
            hidden:'true'
        },{
            field: 'name',
            title: '疾病名称',
            width: "50%"

        }, {
            field: 'deptName',
            title: '推荐科室',
            width: "50%"

        }]]
    });
    /**
     * button of add click
     */
    $("#addBtn").on('click', function () {
//        alert(1);
        reset();
        flag = "add";
        $("#fm").get(0).reset();
        $('#dlg').dialog('open').dialog('center').dialog('setTitle', '添加疾病信息');
        loadbodyPart();
    });
     function loadbodyPart() {
        $('#deptId').combobox({
            url: '../../../api/intelligent-guide/find-dept-dict',
            valueField: 'id',
            textField: 'text',
            width: 150,
            listWidth: 150,
            listHeight: 100
        });
    }
    var setValue = function setValue(val) {
        $('#deptId').combobox('setValue', val);
    }
    var getValue = function getValue() {
        return $('#deptId').combobox('getValue');
    }
    /**
     *  button of submit click
     */
    $("#submitBtn").on('click', function () {
        if ($("#fm").form('validate')) {
            var clinicSickness = {};
            clinicSickness.id = $("#docId").val();
            clinicSickness.name = $("#name").val();
            clinicSickness.deptId =getValue();
        }
        $.postJSON("/api/intelligent-guide/save-sickness", clinicSickness, function (data) {
            $('#dlg').dialog('close');
            $.messager.alert("系统提示", "操作成功！","info");
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
                title: '修改疾病信息'
            });
            $("#dlg").dialog('open');
            $("#docId").val(arr[0].id);
            loadbodyPart();
            setValue(arr[0].deptId);
            $("#name").textbox("setValue",arr[0].name);
         }
    });
    /**
     * button of delete click
     */
    $("#delBtn").on('click', function () {
        var arr = $("#t_user").datagrid("getSelections");
        if (0 == arr.length){
            $.messager.alert("系统警告", "请选择一条记录进行删除！","error");
        } else {
            $.messager.confirm("系统提示", "确认要删除吗?", function (r) {
                if (r) {
                    var ids = '';
                    for (var i = 0; i < arr.length; i++) {
                        ids += arr[i].id + ",";
                    }
                    ids = ids.substring(0, ids.length - 1);
                    $.post('/api/intelligent-guide/delete-sickness?ids=' + ids, function (result) {
                        loadDict();
                        $('#t_user').datagrid('unselectAll');
                        $.messager.alert("系统提示", "操作成功");
                    })
                }
                else {
                    return;
                }
            });
        }
    });
});

