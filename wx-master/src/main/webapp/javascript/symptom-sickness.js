/**
 * Created by cxy on 2016/5/10.
 */
/**
 * created by chenxiaoyang on 2016.03.29
 */
$(function () {
    var flag;

    /**
     * load datagrid data
     */
    var loadDict = function () {
        $.get("/api/intelligent-guide/find-symptom-vs-sickness", function (data) {
            $("#t_user").datagrid('loadData', data);
        });
    }


    loadDict();

//    var reset = function(){
//        $("#docId").textbox("setValue","");
//        $("#name").textbox("setValue","");
////        $("#deptId").textbox("setValue","");
//    }
    /**
     * init datagrid
     */
    $('#t_user').datagrid({
        idField: 'id',
        title: '症状疾病维护列表',
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
            field: 'symptomId',
            title: '外键',
            hidden:'true'
        }, {
            field: 'sicknessId',
            title: '外键',
            hidden:'true'
        },{
            field: 'symptomName',
            title: '症状名称',
            width: "40%"

        },{
            field: 'sicknessName',
            title: '疾病名称',
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
        flag = "add";
        $("#fm").get(0).reset();
        $('#dlg').dialog('open').dialog('center').dialog('setTitle', '添加症状疾病信息');
        loadbodyPart();
        loadbodyPart1();
    });
    /**
     * load 症状
     */
    function loadbodyPart() {
        $('#symptomId').combobox({
            url: '../../../api/intelligent-guide/load-symptom',
            valueField: 'id',
            textField: 'text',
            width: 150,
            listWidth: 150,
            listHeight: 100
        });
    }
    /**
     * load 疾病
     */
    function loadbodyPart1() {
        $('#sicknessId').combobox({
            url: '../../../api/intelligent-guide/load-sickness',
            valueField: 'id',
            textField: 'text',
            width: 150,
            listWidth: 150,
            listHeight: 100
        });
    }
    function setValue($obj,val) {
        $obj.combobox('setValue', val);
    }
    function getValue($obj) {
        return $obj.combobox('getValue');
    }
    /**
     *  button of submit click
     */
    $("#submitBtn").on('click', function () {
        if ($("#fm").form('validate')) {
            var clinicSickness = {};
            clinicSickness.id = $("#docId").val();
            clinicSickness.symptomId = getValue($("#symptomId"));
            clinicSickness.sicknessId =getValue($("#sicknessId"));
            clinicSickness.sex=$("#sex").val();
        }
        $.postJSON("/api/intelligent-guide/save-symptom-vs-sickness", clinicSickness, function (data) {
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
                title: '修改症状疾病信息'
            });
            $("#dlg").dialog('open');
            $("#docId").val(arr[0].id);
            loadbodyPart();
            loadbodyPart1();
            setValue($("#symptomId"),arr[0].symptomId);
            setValue($("#sicknessId"),arr[0].sicknessId);
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

