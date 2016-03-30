/**
 * created by chenxiaoyang on 2016.03.29
 */
$(function () {
    var flag;
    /**
     * load datagrid data
     */
    var loadDict = function () {
//        var s_name = $("#s_name").val();
//        var s_hospitalId = $("#s_hospitalId").val();
//        var doctInfo={};
//        doctInfo.name=s_name;
//        doctInfo.hospitalId=s_hospitalId;
        $.get("/api/doct-info/get-list", function (data) {
            $("#t_user").datagrid('loadData', data);
        });
    }
    loadDict();
    /**
     * init datagrid
     */
    $('#t_user').datagrid({
        idField: 'id',
        title: '医生信息列表',
        footer: '#tb',
//        url: loadDict(),
        fit: true,
        fitColumns: true,
        striped: true,
        //nowrap: false ,
        loadMsg: '数据正在加载,请耐心的等待...',
        rownumbers: true,
//        singleSelect:true ,
        remoteSort: true,
        sortName: 'name',
//        sortOrder: 'desc',
        columns: [
            [
                {
                    field: 'id',
                    width: 50,
                    checkbox: true
//
                },
                {
                    field: 'name',
                    title: '医生姓名',
                    width: 100,
                    sortable: true,
                    align: 'center'
                },
                {
                    field: 'title',
                    title: '医生职称',
                    width: 100,
                    sortable: true,
                    align: 'center'
//                    hidden: true
                },
                {
                    field: 'headUrl',
                    title: '头像地址',
                    align: 'center',
                    sortable: true,
                    width: 100
                },
                {
                    field: 'hospitalId',
                    title: '所在医院编号',
                    align: 'center',
                    sortable: true,
                    width: 100
                },
                {
                    field: 'tranDescription',
                    title: '个人描述',
                    align: 'center',
                    sortable: true,
                    width: 150
                }
            ]
        ],
        pagination: true,
        pageSize: 5,
        pageList: [5, 15, 30, 50]
    });
    /**
     * button of add click
     */
    $("#addBtn").on('click', function () {
        flag = "add";
        $("#fm").get(0).reset();
        $('#dlg').dialog('open').dialog('center').dialog('setTitle', '添加医生信息');
        loadHospital();
    });
    /**
     * load hospitalId for combobox
     */
    var loadHospital = function loadData() {
        $('#cc').combobox({
//            ../../../api/hospital-info/find-hospitalId
            url: "../../../api/hospital-info/find-hospitalId",//loadDataByUrl(),//;
            valueField: 'id',
            textField: 'text'
        });

    }
    var loadDataByUrl = function () {
//        $.postJSON("api/hospital-info/find-hospitalId", function (data) {
//        }, function (data, status) {
//        })
        $.get('api/hospital-info/find-hospitalId', function (r) {

        });
    }
    /**
     * init combobox
     */
    $('#cc').combobox({
        width: 150,
        listWidth: 150,
        listHeight: 100,
        editable: false
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
//            var url = "/api/doct-info/save?id=" + id + "&name=" + name + "&title=" + title + "&hospitalId=" + hospitalId + "&headUrl=" + headUrl + "&description=" + description;
        }
        $.postJSON("/api/doct-info/save?description=" + description, doctInfo, function (data) {
            $('#dlg').dialog('close');
            $.messager.alert("系统提示", "操作成功！");
            loadDict();
            $("#fm").get(0).reset();
        }, function (data, status) {
        })
//        $.get(url, function (data) {
//
//            $('#dlg').dialog('close');
//
//            $.messager.alert("操作成功！");
//            loadDict();
//            $("#fm").get(0).reset();
//        });
    });
    /**
     * button of edit click
     */
    $("#editBtn").on('click', function () {
        flag = "edit";
        var arr = $('#t_user').datagrid('getSelections');

        if (arr.length != 1) {
//            $.messager.show({
//                title: "提示信息",
//                msg: "只能选择一条记录进行修改！"
//            });
            $.messager.alert("系统警告", "只能选择一条记录进行修改！","error");
        }
        else {
            $('#dlg').dialog({
                title: '修改医生信息'
            });
            $("#dlg").dialog('open');
            $("#fm").get(0).reset();

            loadSelectedRowData(arr);
            loadHospital();

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
        if (0 == arr.length)
//            $.messager.show({
//                title: "提示信息！",
//                msg: "!"
//            });
            $.messager.alert("系统警告", "必须选择一条记录进行删除！","error");
        else
            $.messager.confirm("系统提示", "确认要删除吗?", function (r) {
                if (r) {
//                    alert(arr.length);
                    var ids = '';
                    for (var i = 0; i < arr.length; i++) {
                        ids += arr[i].id + ",";
                    }
                    ids = ids.substring(0, ids.length - 1);
//                    alert(ids);
                    $.post('/api/doct-info/delete?ids=' + ids, function (result) {
//                        $('#t_user').datagrid('reload');
                        loadDict();
                        $('#t_user').datagrid('unselectAll');
//                        $.messager.show({
//                            title: '提示信息!',
//                            msg: '操作成功!'
//                        });
                        $.messager.alert("系统提示", "操作成功");
                    })
                }
                else {
                    return;
                }
            });
    });
    /**
     * button of search click
     */
    $("#searchBtn").on('click', function () {
        $('#lay').layout('expand', 'north');
    });
    /**
     * button of searchSubmitbtn
     */
    $("#searchSubmitbtn").on('click', function () {
        var s_name = $("#s_name").val().trim();
        var s_hospitalId = $("#s_hospitalId").val().trim();
        if ("" == s_name && "" == s_hospitalId) {
            loadDict();
        } else {
            $.get("/api/doct-info/query-by-condition?name="+s_name+"&hospitalId="+s_hospitalId, function (data) {
                $("#t_user").datagrid('loadData', data);
            });
        }
    })
    /**
     * button ofclearbtn
     */
    $("#clearbtn").on("click", function () {
        $("#mysearch").get(0).reset();
    });
});





