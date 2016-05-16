/**
 * created by chenxiaoyang on 2016.03.29
 */
$(function () {
    var flag;

    /**
     * load datagrid data
     */
    var loadDict = function () {
        $.get("/api/intelligent-guide/find-symptom", function (data) {
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
        title: '症状维护列表',
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
             field: 'bodyPartId',
             title: '外键',
             hidden:'true'
         },{
            field: 'name',
            title: '症状名称',
            width: "40%"

        }, {
            field: 'bodyPartName',
            title: '身体部位',
            width: "40%"

        },{
             field: 'sex',
             title: '性别',
             width: "20%",
             formatter: function(value,row,index){
                 if(row.sex == -1){
                     return "不区分男女";
                 } if(row.sex =="男"){
                     return "男";
                 } if(row.sex == "女"){
                     return "女";
                 }
             }
          }]]
    });
    /**
     * button of add click
     */
    $("#addBtn").on('click', function () {
        reset();
        flag = "add";
        $("#fm").get(0).reset();
        $('#dlg').dialog('open').dialog('center').dialog('setTitle', '添加症状信息');
        loadbodyPart();
     });
    var loadbodyPart = function () {
        $('#bodyPartId').combobox({
            url: '../../../api/intelligent-guide/find-body-part',
            valueField: 'id',
            textField: 'text',
            width: 150,
            listWidth: 150,
            listHeight: 100
        });
    }
    var setValue = function setValue(val) {
        $('#bodyPartId').combobox('setValue', val);
    }
    var getValue = function getValue() {
        return $('#bodyPartId').combobox('getValue');
    }
     /**
     *  button of submit click
     */
    $("#submitBtn").on('click', function () {
                if ($("#fm").form('validate')) {
                    var clinicSymptom = {};
                    clinicSymptom.id = $("#docId").val();
                    clinicSymptom.name = $("#name").val();
                    clinicSymptom.bodyPartId =getValue();
                    clinicSymptom.sex=$("#sex").val();
                 }
                 $.postJSON("../../../api/intelligent-guide/save-symptom", clinicSymptom, function (data) {
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
                title: '修改症状信息'
            });
             $("#dlg").dialog('open');
             loadbodyPart();
             $("#docId").val(arr[0].id);
             setValue(arr[0].bodyPartId)
             $("#name").textbox("setValue",arr[0].name);
             $("#sex").val(arr[0].sex);
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
                    $.post('/api/intelligent-guide/delete-symptom?ids=' + ids, function (result) {
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

