/**
 * Created by txb on 2015/10/21.
 */
$(document).ready(function () {
    var editRowIndex;
    $("#dg").datagrid({
        title:"基础配置信息",
        fit:true,
        rownumbers:true,
        singleSelect:true,
        footer:"#tb",
        columns:[[{
            title:"应用名",
            field:"appName",
            width:"10%",
            editor:{type:"validatebox"}
        },{
            title:"参数序号",
            field:"parameterNo",
            width:"10%",
            editor:{type:"validatebox"}
        },{
            title:"参数名称",
            field:"parameterName",
            width:"10%",
            editor:{type:"validatebox"}
        },{
            title:"参数初始值",
            field:"parainitValue",
            width:"10%",
            editor:{type:"validatebox"}
        },{
            title:"参数范围",
            field:"parameterScope",
            width:"10%",
            editor:{type:"validatebox"}
        },{
            title:"说明",
            field:"explanation",
            width:"40%",
            editor:{type:"textarea",options:{
            }}
        }]]
    });
    //模态窗口定义
    $("#dialogDiv").dialog({
        shadow:true,
        resizable:false,
        modal:true,
        width:400,
        height:300
    });
    //查询
    $("#searchBtn").on("click", function () {
        var name = $("#name").textbox("getValue");

        $.get("/api/app-configer-baseinfo/list?name=" + name, function (data) {
            $("#dg").datagrid('loadData', data);
        });
    });
    //添加
    $("#addBtn").on("click", function () {
        $("#dialogDiv").dialog("open").dialog("setTitle","添加");
    });
    //修改
    $("#editBtn").on("click", function () {
       var row = $("#dg").datagrid("getSelected");
        if (row == null){
            $.messager.alert("提示","请选择修改的行","info");
            return ;
        }
        $("#appName").textbox("setValue",row.appName);
        $("#parameterName").textbox("setValue",row.parameterName);
        $("#parainitValue").textbox("setValue",row.parainitValue);
        $("#parameterScope").textbox("setValue",row.parameterScope);
        $("#explanation").val(row.explanation);

        $("#id").val(row.id);
        $("#parameterNo").val(row.parameterNo);

        $("#dialogDiv").dialog("open").dialog("setTitle","修改");


    });
    //删除
    $("#delBtn").on("click", function () {
        var row = $("#dg").datagrid("getSelected");
        if(row == null){
            $.messager.alert("提示","请选择要删除的行","info");
            return ;
        }else {
            $.postJSON("/api/app-configer-baseinfo/delete" ,row, function (data) {
                $.messager.alert("提示","删除成功","info");
                loadList();
            }, function (data) {
                $.messager.alert("提示","删除失败","error");
            })
        }
    });

    //保存
    $("#saveBtn").on("click", function () {
            var baseinfos = {};
            baseinfos.appName = $("#appName").val();
            baseinfos.parameterNo = $("#parameterNo").val();
            baseinfos.parameterName = $("#parameterName").val();
            baseinfos.parainitValue = $("#parainitValue").val();
            baseinfos.parameterScope = $("#parameterScope").val();
            baseinfos.explanation = $("#explanation").val();
            baseinfos.id = $("#id").val();

            if ($("#fm").form('validate')) {
                $.postJSON("/api/app-configer-baseinfo/save", baseinfos, function (data) {
                    $("#dialogDiv").dialog("close");
                    clearData();
                    loadList();
                    $.messager.alert("提示", "保存成功", "info");
                }, function (data) {
                    $.messager.alert("提示", data.responseJSON.errorMessage, "error");
                })
            }
        }
    );
    var clearData = function () {
        $("#appName").textbox("setValue","");
        $("#parameterName").textbox("setValue","");
        $("#parainitValue").textbox("setValue","");
        $("#parameterScope").textbox("setValue","");
        $("#explanation").val("");

        $("#id").val("");
        $("#parameterNo").val("");
    }
    var loadList = function () {
        $.get("/api/app-configer-baseinfo/list", function (data) {
            console.log(data);
            $("#dg").datagrid("loadData",data);
        });
    };
    loadList();
});