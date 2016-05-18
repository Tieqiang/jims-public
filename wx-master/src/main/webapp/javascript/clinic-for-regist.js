/**
 * Created by cxy on 2016/3/30.
 */
$(function () {
    $('#clinicType').combobox({
        width: 150,
        listWidth: 150,
        listHeight: 100,
        editable: false
    });
    loadRegistData();
    $("#regist").datagrid({
        idField: "id",
        title: '号表管理',
        fit: true,
        footer: '#foot',
        columns: [
            [
                {
                    title: '编号',
                    field: 'id',
                    hidden: true
                 },
//                {
//                    title: 'ok',
//                    field: 'clinicTypeId',
//                    hidden: true
//                },
                {
                    title: '号别名称',
                    field: 'clinicType',
                    align: "center",
                    width: "10%"

                },
                {
                    title: '号表日期',
                    field: 'clinicDate',
                    align: "center",
                    width: "20%"

                },
                {
                    title: '限号数',
                    field: 'registrationLimits',
                    align: "center",
                    width: "7%"
                },
                {
                    title: '限约号数',
                    field: 'appointmentLimits',
                    align: "center",
                    width: "7%"

                },
                {
                    title: '当前号',
                    field: 'currentNo',
                    align: 'center',
                    width: "7%"
                },
                {
                    title: '当日已挂号',
                    field: 'registrationNum',
                    align: "center",
                    width: "10%"
//                    formatter: function (value, record, index) {
//                        if (value == "" || value == null) {
//                            return 0;
//                        }
//                    }
                },
                {
                    title: '相关费用',
                    field: 'registPrice',
                    width: "10%",
                    align: 'center'

                },
                {
                    title: '时间描述',
                    field: 'timeDesc',
                    width: "30%",
                    align: 'center'

                }
            ]
        ]
    });
    $("#searchBtn").on('click', function () {
        loadRegistData();
    });
    $("#addBtn").on('click', function () {
        $("#registForm").get(0).reset();
        $('#regist_dlg').dialog('open').dialog('center').dialog('setTitle', '添加指定日期号表约束');
        initDate($("#dd"));
        loadClinicTypeData($("#clinicType"), null);
    });
    $("#btn_ok").on('click', function () {
        if ($("#registSelectForm").form('validate')) {
            var clinicForRegist = {};
            clinicForRegist.id = $("#subId").val();
            clinicForRegist.registrationLimits = $("#registrationLimits").val();
            clinicForRegist.appointmentLimits = $("#appointmentLimits").val();
            $.postJSON("/api/clinic-for-regist/save?clinicTypeId=" + getValue() + "&date=" + $("#dd").datebox("getValue"), clinicForRegist, function (data) {
                $('#registSelect').dialog('close');
                $.messager.alert("系统提示", "保存成功", "info");
                loadRegistData();
                $("#registForm").get(0).reset();
                $('#regist').datagrid('unselectAll');
            }, function (data, status) {
            })
        }
    });
    $("#editBtn").on('click', function () {
        var arr = $('#regist').datagrid('getSelections');
        if (arr.length != 1) {
            $.messager.alert("系统提示", "只能选择一行记录进行修改", "error");
        } else {
            $("#tableInfo1").dialog("open").dialog('center').dialog('setTitle', '修改号表');
             initDate($("#date2"));
            $("#date2").datebox("setValue", arr[0].clinicDate);
            loadClinicTypeData($("#clinicLabel1"), null);
            setSettingValue(arr[0].clinicTypeId, $("#clinicLabel1"));
//            alert();
            $("#bid").textbox("setValue", arr[0].id);
//            editBtn
            $("#i_currentRegistCount1").textbox("setValue", arr[0].registrationNum);
            $("#i_currentRegistNo1").textbox("setValue", (arr[0].currentNo));
            $("#i_registrationLimits1").textbox("setValue", arr[0].registrationLimits);
            $("#i_appointmentLimits1").textbox("setValue", arr[0].appointmentLimits==null?0:arr[0].appointmentLimits);
            $("#i_description1").textbox("setValue", arr[0].timeDesc);
            $("#registPrice").attr("value",arr[0].registPrice);
//            loadRegistData();
        }
    });
     $("#btn_ok2").on("click", function () {
//         alert(1);
        var clinicForRegist = {};
        clinicForRegist.id = $("#bid").val();
        var date = $("#date2").datebox("getValue");
        clinicForRegist.timeDesc = $("#i_description1").val();
        clinicForRegist.registrationLimits = $("#i_registrationLimits1").textbox("getValue");
        clinicForRegist.appointmentLimits = $("#i_appointmentLimits1").textbox("getValue");
        clinicForRegist.currentNo = $("#i_currentRegistNo1").textbox("getValue");
        clinicForRegist.registPrice = $("#registPrice").val();
//         alert($("#registPrice").val());
        clinicForRegist.registrationNum = $("#i_currentRegistCount1").textbox("getValue");
        var clinicIndexId = getclinicLabel1();
        $.postJSON("/api/clinic-for-regist/update?clinicIndexId=" + clinicIndexId + "&date=" + date, clinicForRegist, function (data) {
            $('#tableInfo1').dialog('close');
            $.messager.alert("系统提示", "修改成功", "info");
            loadRegistData();
            $("#tableInfoForm1").get(0).reset();
        }, function (data, status) {
        })

    });
     $("#searchInfo").on('click', function () {
        loadRegistData();
    })
     $("#registBtn").on('click', function () {
        $("#registSelectForm").get(0).reset();
        $('#registSelect').dialog('open').dialog('center').dialog('setTitle', '号表生成选项');
        loadClinicSettingTypeData($("#clinicSettingType"));//选择号类
        $('#clinicSettingType').combobox({
            onSelect: function () {
                loadClinicTypeData($("#clinicLabel"), getSettingValue($('#clinicSettingType')));
            }
        });
    })
     $("#btn_ok1").on('click', function () {
             var date = $("#date").datebox("getValue");
             var date1 = $("#date1").datebox("getValue");
             var clinicIndexId = getclinicLabel();
             var desc = $("#desc").val();
             var id = $("#id").val();
//         alert(date);
         if(date=="" || date1=="" || clinicIndexId==""){
             $.messager.alert("系统提示","请选择必填项","error");
         }else{
             regist(date,clinicIndexId,date1,desc,id);
         }
      })
     $("#delBtn").on('click', function () {
        var arr = $('#regist').datagrid('getSelections');
        if (arr.length <= 0) {
            $.messager.alert("系统提示", "至少选择一行记录进行删除", "error");
        } else {

            $.messager.confirm('系统提示', '确认删除?', function (r) {
                if (r) {
                    var ids = '';
                    for (var i = 0; i < arr.length; i++) {
                        ids += arr[i].id + ',';
                    }
                    ids = ids.substring(0, ids.length - 1);

                    $.postJSON('/api/clinic-for-regist/delete?ids=' + ids, function (data) {
//                        console.info(data);
                        loadRegistData();
                        $('#regist').datagrid('unselectAll');
                        $.messager.alert("系统提示", "删除成功", "info");
                    }, function (data, status) {
                        if (status == "success") {
                            loadRegistData();
                            $('#regist').datagrid('unselectAll');
                            $.messager.alert("系统提示", "删除成功", "info");
                            loadRegistData();
                        }
                    });
                } else {
                    return;
                }
            });
        }
    });
     $("#findInfo").on('click', function () {
        var arr = $('#regist').datagrid('getSelections');
        if (arr.length!=1) {
            $.messager.alert("系统提示", "只能选择一条记录进行查看", "error");
        } else {
             getInfo(arr);
        }
     })
     $("#findBtn").on("click", function () {
        loadRegistData();
    })
     $("#clearbtn").on('click', function () {
        $("#mysearch").get(0).reset();
     })
 });
var loadClinicTypeData = function (obj, param) {
//    alert(param);
    var url = "";
    if (param == "" || param == null) {
        url = "../../../api/clinic-for-regist/find-clinic-index-type";
    } else {
        url = "../../../api/clinic-for-regist/find-clinic-index-type?typeId=" + param;
    }
    obj.combobox({
        url: url,
        valueField: 'id',
        textField: 'text',
        width: 150,
        listWidth: 150,
        listHeight: 100
    });
}
var loadClinicSettingTypeData = function (obj) {
    obj.combobox({
        url: '../../../api/clinic-for-regist/find-clinic-setting-type',
        valueField: 'id',
        textField: 'text',
        width: 150,
        listWidth: 150,
        listHeight: 100
    });
}
var loadchargeTypeData = function (param) {
    $('#chargeType').combobox({
        url: '../../../api/clinic-for-regist/find-charge-type?typeId=' + param,
        valueField: 'id',
        textField: 'text',
        width: 150,
        listWidth: 150,
        listHeight: 100
    });
}
var setValue = function setValue(val) {
    $('#clinicType').combobox('setValue', val);
}
var getValue = function getValue() {
    return $('#clinicType').combobox('getValue');
}
var setclinicLabel = function setValue(val) {
    $('#clinicLabel').combobox('setValue', val);
}
var getclinicLabel = function getValue() {
    return $('#clinicLabel').combobox('getValue');
}
var getclinicLabel1 = function getValue() {
    return $('#clinicLabel1').combobox('getValue');
}
var setSettingValue = function setValue(val, obj) {
    obj.combobox('setValue', val);
}
var getSettingValue = function getValue(obj) {
    return obj.combobox('getValue');
}
var setChargeValue = function setValue(val) {
    $('#chargeType').combobox('setValue', val);
}
var getChargeValue = function getValue() {
    return $('#chargeType').combobox('getValue');
}
 var initDate = function (obj) {
    obj.datetimebox({
        currentText: '今天',
        closeText: '关闭',
        disabled: false,
        required: true,
        editable: false,
        missingMessage: '必填'
    });
}
var initDate1 = function () {
    $("#date1").datetimebox({
        currentText: '今天',
        closeText: '关闭',
        disabled: false,
        required: true,
        editable: false,
        missingMessage: '必填'
    });
}
 var regist = function (date, clinicIndexId, date1, desc, id) {
//    alert(1);
    $.get("/api/clinic-for-regist/regist-table?date=" + date + "&clinicIndexId=" + clinicIndexId + "&date1=" + date1 + "&desc=" + desc + "&id=" + id, function (data) {
//        alert('ok');
        if (data.isRegist) {//生成
            loadRegistData();
            $("#registSelect").dialog("close");
            $.messager.alert("系统提示","号表生成成功", "info");
        } else {
            $.messager.alert("系统提示", "号表生成失败", "error");
        }
    });

}
var loadRegistData = function () {
    var clinicIndexName = $("#clinicIndexName").val();
//    var date=$("#dt").datebox("getValue");
//    alert(date);
    $.get("/api/clinic-for-regist/list-all?likeName=" + clinicIndexName, function (data) {
        $("#regist").datagrid('loadData', data);
    });
}
var getInfo = function (arr) {
//    alert(arr[0].appointmentLimits);
    $.get("/api/clinic-for-regist/find-info?id=" + arr[0].id, function (node) {
        $('#tableInfo').dialog('open').dialog('center').dialog('setTitle', '号表详情');
        $("#i_date").textbox('setValue', node.registTime);
        $("#i_clinicSettingType").textbox('setValue', node.clinicSettingType);
        $("#i_clinicLabel").textbox('setValue', node.clinicLabel);
        $("#i_dept").textbox('setValue', node.clinicDept);
//        i_appointmentLimits
        $("#i_currentRegistCount").textbox('setValue', node.currentRegistCount);
        $("#i_currentRegistNo").textbox('setValue', node.currentRegistNo);
        $("#i_registrationLimits").textbox('setValue', node.registrationLimits)
        $("#xyhs").textbox("setValue",node.appointmentLimits==null?0:node.appointmentLimits);
        $("#i_description").textbox('setValue', node.description);
    });
}
