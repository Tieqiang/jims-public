/**
 * Created by txb on 2015/10/21.
 */
$(document).ready(function () {
    $("#dg").datagrid({
        title:"配置参数",
        fit:true,
        rownumbers:true,
        singleSelect:true,
        footer:"#tb",
        columns:[[
            {
                title:"应用名",
                field:"appName",
                width:"10%",
                editor:{type:"validatebox"}
            }, {
                title:"使用科室",
                field:"deptCode",
                width:"10%",
                editor:{type:"validatebox"}
            }, {
                title:"使用人员",
                field:"empNo",
                width:"10%",
                editor:{type:"validatebox"}
            }, {
                title:"参数名称",
                field:"parameterName",
                width:"10%",
                editor:{type:"validatebox"}
            }, {
                title:"参数初始值",
                field:"parameterValue",
                width:"10%",
                editor:{type:"validatebox"}
            }, {
                title:"位置",
                field:"position",
                width:"10%",
                editor:{type:"validatebox"}
            }, {
                title:"所属医院",
                field:"hospitalId",
                width:"10%",
                editor:{type:"validatebox"},
                hidden:true
            }
        ]]
    });
    //模态窗口定义
    $("#dialogDiv").dialog({
        shadow:true,
        resizable:false,
        modal:true,
        width:400,
        height:300
    });

    //模态窗口数据初始化
    var deptData = []; //科室列表
    var staffData = [];//人员列表
    var deptPromise = $.get("/api/dept-dict/list?hospitalId=" + parent.config.hospitalId, function (data) {
        $.each(data, function (index, item) {
            var dept = {};
            dept.deptCode = item.deptCode;
            dept.deptName = item.deptName;
            deptData.push(dept);
        });
    });

    deptPromise.done(function () {
        $("#deptCode").combobox({
            data:deptData,
            valueField:"deptCode",
            textField:"deptName"
        });
    });

    var staffPromise = $.get("/api/staff-dict/list-by-hospital?hospitalId=" + parent.config.hospitalId, function (data) {
        $.each(data, function (index,item) {
            var staff = {};
            staff.loginName = item.loginName;
            staff.id = item.id;
            staffData.push(staff);
        })
    });

    staffPromise.done(function () {
        $("#empNo").combobox({
            data:staffData,
            valueField:"loginName",
            textField:"loginName"
        });
    });

    //查询
    $("#searchBtn").on("click", function () {
        var name = $("#name").textbox("getValue");

        $.get("/api/app-configer-parameter/list?hospitalId=" + parent.config.hospitalId+"&name=" + name, function (data) {
            $("#dg").datagrid('loadData', data);
        });
    });

    //添加
    $("#addBtn").on("click", function () {
        $("#dialogDiv").dialog("open").dialog("setTitle","添加");
        $("#hospitalId").val(parent.config.hospitalId);
    });
    //修改
    $("#editBtn").on("click", function () {
        var row = $("#dg").datagrid("getSelected");
        if (row == null){
            $.messager.alert("提示","请选择修改的行","info");
            return ;
        }
        $("#appName").textbox("setValue",row.appName);
        $("#deptCode").combobox("setValue",row.deptCode);
        $("#empNo").combobox("setValue",row.empNo);
        $("#parameterName").textbox("setValue",row.parameterName);
        $("#parameterValue").textbox("setValue",row.parameterValue);
        $("#position").textbox("setValue",row.position);

        $("#id").val(row.id);
        $("#hospitalId").val(row.hospitalId);

        $("#dialogDiv").dialog("open").dialog("setTitle","修改");


    });
    //删除
    $("#delBtn").on("click", function () {
        var row = $("#dg").datagrid("getSelected");
        if(row == null){
            $.messager.alert("提示","请选择要删除的行","info");
            return ;
        }else {
            $.postJSON("/api/app-configer-parameter/delete" ,row, function (data) {
                $.messager.alert("提示","删除成功","info");
                loadList();
            }, function (data) {
                $.messager.alert("提示","删除失败","error");
            })
        }
    });

    //保存
    $("#saveBtn").on("click", function () {
            var parameters = {};
            parameters.appName = $("#appName").val();
            parameters.parameterName = $("#parameterName").val();
            parameters.parameterValue = $("#parameterValue").val();
            parameters.position = $("#position").val();
            if ($("#empNo").combobox("getValue") == ""){
                parameters.empNo = $("#empNo").combobox("setValue","*")
            }else {
                parameters.empNo = $("#empNo").combobox("getValue");
            }
            parameters.deptCode = $("#deptCode").combobox("getValue");
            parameters.empNo = $("#empNo").combobox("getValue");
            parameters.id = $("#id").val();
            parameters.hospitalId = $("#hospitalId").val();
            console.log(parameters);
            console.log(parameters.hospitalId);
            if ($("#fm").form('validate')) {
                $.postJSON("/api/app-configer-parameter/save", parameters, function (data) {
                    $("#dialogDiv").dialog("close");
                    clearData();
                    loadList();
                    $.messager.alert("提示", "保存成功", "info");
                }, function (data) {
                    $.messager.alert("提示", "保存失败", "error");
                })
            }
        }
    );
    var clearData = function () {
        $("#appName").textbox("setValue","");
        $("#deptCode").combobox("setValue","");
        $("#empNo").combobox("setValue","");
        $("#parameterName").textbox("setValue","");
        $("#parameterValue").textbox("setValue","");
        $("#position").textbox("setValue","");

        $("#id").val("");
        $("#hospitalId").val("");

    }
    var loadList = function () {
        $.get("/api/app-configer-parameter/list?hospitalId="+ parent.config.hospitalId, function (data) {
            $("#dg").datagrid("loadData",data);
        });
    };
    loadList();
});