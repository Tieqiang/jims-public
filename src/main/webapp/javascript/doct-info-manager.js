/**
 * created by chenxiaoyang on 2016.03.29
 */
$(function () {
    //头衔长度验证
    $.extend($.fn.validatebox.defaults.rules, {
        maxLength: {
            validator: function(value, param){
                return value.length <= param[0];
            },
            message: '最多可以输入7位!!'
        }
    });

    var flag;

    /**
     * load datagrid data
     */
    var loadDict = function () {
        $.get("/api/doct-info/get-list", function (data) {
            $("#t_user").datagrid('loadData', data);
        });
    }
    loadDict();

    var reset = function(){
        $("#id").textbox("setValue","");
        $("#name").textbox("setValue","");
        $("#title").textbox("setValue","");
        $("#headUrl").textbox("setValue","");
        $("#tranContent").val("")
    }

    /**
     * init datagrid
     */
    $('#t_user').datagrid({
        idField: 'id',
        title: '医生信息列表',
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
            field: 'hospitalId',
            title: '所在医院编号',
            width: "20%"
        }, {
            field: 'name',
            title: '医生姓名',
            width: "20%"

        }, {
            field: 'title',
            title: '头衔',
            width: "20%"

        }, {
            field: 'headUrl',
            title: '头像地址',
            width: "20%"
        }, {
            field: 'tranDescription',
            title: '个人描述',
            width: "20%"
        }]]
    });
    /**
     * button of add click
     */
    $("#addBtn").on('click', function () {
        //reset();
        flag = "add";
        $("#fm").get(0).reset();
        $('#dlg').dialog('open').dialog('center').dialog('setTitle', '添加医生信息');

    });

    /**
     * init combobox
     */
    $("#cc").combobox({
        width: 160,
        listWidth: 150,
        listHeight: 100,
        url:'/api/hospital-dict/list',
        method:"GET",
        mode: 'remote',
        valueField:'id',
        textField:'hospitalName'
        //editable: false
    });
    /**
     * getValue from comboboox
     * @returns {*|jQuery}
     */
    var getValue = function getValue() {
        var val = $('#cc').combobox('getValue');
        return val;
    }
    /**
     * for combobox setvalue
     * @param val
     */
    var setValue = function setValue(val) {
        $('#cc').combobox('setValue', val);
    }
    /**
     *  button of submit click
     */
    $("#submitBtn").on('click', function () {
        if ($("#fm").form('validate')) {
            var doctInfo = {};
            doctInfo.id = $("#docId").val();
            doctInfo.name = $("#name").val();
            doctInfo.title = $("#title").val();
            doctInfo.hospitalId = getValue();
            doctInfo.headUrl = $("#headUrl").val();
            var description = $("#description").val();
        }
        $.postJSON("/api/doct-info/save?description=" + description, doctInfo, function (data) {
            $('#dlg').dialog('close');
            $.messager.alert("系统提示", "操作成功！");
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
                title: '修改医生信息'
            });
            $("#dlg").dialog('open');
            $("#fm").get(0).reset();

            loadSelectedRowData(arr);


            setValue(arr[0].hospitalId)
        }
    });

    /**
     * load selectedRowData
     */
    var loadSelectedRowData = function (arr) {
        $('#fm').form('load', {
            id: arr[0].id,
            name: arr[0].name,
            title: arr[0].title,

            headUrl: arr[0].headUrl,
            description: arr[0].tranDescription
        });
    }

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
                    $.post('/api/doct-info/delete?ids=' + ids, function (result) {
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

    /**
     * 查询
     */
    //$("#searchBtn").on('click', function () {
    //    $('#lay').layout('expand', 'north');
    //});
    /**
     * button of searchSubmitbtn
     */
    //$("#searchSubmitbtn").on('click', function () {
    //    var s_name = $("#s_name").val().trim();
    //    var s_hospitalId = $("#s_hospitalId").val().trim();
    //    if ("" == s_name && "" == s_hospitalId) {
    //        loadDict();
    //    } else {
    //        $.get("/api/doct-info/query-by-condition?name="+s_name+"&hospitalId="+s_hospitalId, function (data) {
    //            $("#t_user").datagrid('loadData', data);
    //        });
    //    }
    //})
    /**
     * button ofclearbtn
     */

});
