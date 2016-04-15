/**
 * Created by heren on 2015/9/22.
 */

$(function(){
    $("#staffName").searchbox({
        searcher: function (value, name) {
            var rows = $("#dg").datagrid("getRows");
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].loginName == psdEdit(value.toUpperCase())) {
                    $("#dg").datagrid('selectRow', i);
                }
            }
        }
    });
    //设置列
    $("#dg").datagrid({
        fit: true,
        singleSelect: true,
        footer: '#tb',
        title: parent.config.hospitalName + "--工作人员字典维护",
        columns: [[ {
            title: '登录名',
            field: 'loginName',
            width: '10%'
        },{
            title:'姓名',
            field:'name',
            width:'10%'
        },{
            title: '密码',
            field: 'password',
            width: '15%',
            hidden:true
        },{
            title: '科室名称',
            field: 'deptDict',
            width: '20%',
            formatter: function(value,row,index){
                if (value.deptName){
                    return value.deptName ;
                } else {
                    return value;
                }
            }
        }, {
            title: '工作',
            field: 'job',
            width: '20%'
        }, {
            title: '职称',
            field: 'title',
            width: '20%'
        }, {
            title: '角色',
            field: 'roleNames',
            width: '30%'
        },{
            title:'角色ID',
            field:'roleIds',
            width:'30%',
            hidden:true
        }]]
    });
    var dept = [] ;//待选科室数组
    var role = [] ;//角色
    /**
     * 加载人员信息表
     */
    var loadStaff = function () {
        var staffs = [];
        var loadPromise = $.get("/api/staff-dict/list-by-hospital?hospitalId="+parent.config.hospitalId, function (data) {
            staffs  = data ;
        });
        loadPromise.done(function () {
            $("#dg").datagrid('loadData',staffs) ;
        })
    }

    loadStaff();
    var deptPromise = $.get("/api/dept-dict/list?hospitalId="+parent.config.hospitalId,function(data){
        dept = data ;
    }) ;

    var rolePromise = $.get("/api/role-dict/list",function(data){
        role = data ;
    }) ;

    $("#deptName").combobox({
        valueField:'id',
        textField:'deptName'
    }) ;

    $("#roleIds").combobox({
        valueField:'id',
        width:160,
        textField:'roleName',
        formatter: function(row){
            var opts = $(this).combobox('options');
            return row[opts.textField];
            },
        multiple:true
    }) ;

    $("#searchBtn").on("click", function () {
        var name = $("#name").textbox("getValue");

        $.get("/api/staff-dict/list?name=" + name, function (data) {
            $("#dg").datagrid('loadData', data);
        });
    });

    $("#addBtn").on('click',function(){
        clearInput() ;
        $("#deptName").combobox('loadData',dept) ;
        $("#roleIds").combobox('loadData',role) ;
        $("#dlg").dialog('open').dialog('setTitle','工作人员维护') ;
    });
    var psdEdit = function (data) {
        switch (data.length) {
            case 1:
                data = "00000" + data;
                break;
            case 2:
                data = "0000" + data;
                break;
            case 3:
                data = "000" + data;
                break;
            case 4:
                data = "00" + data;
                break;
            case 5:
                data = "0" + data;
                break;
        }
        return data;
    }
    /**
     * 清除输入框信息
     */
    var clearInput = function(){
        $("#id").val("");
        $("#deptName").combobox('clear');
        $("#loginName").textbox('clear');
        $("#password").textbox('clear');
        $("#confirm_password").textbox('clear');
        $("#title").textbox('clear');
        $("#job").textbox('clear');
        $("#roleIds").combobox('clear')
        $("#name").textbox('clear') ;
    }
    $("#saveBtn").on('click',function(){
        if($("#fm").form('validate')){
            var staff ={} ;
            staff.id = $("#id").val() ;
            staff.deptDict ={} ;
            staff.deptDict.id= $("#deptName").combobox('getValue') ;
            staff.loginName = $("#loginName").textbox('getValue') ;
            var pwd = staff.password = psdEdit($("#password").textbox('getValue')) ;
            var confirm_pwd= psdEdit($("#confirm_password").textbox('getValue'));
            staff.title= $("#title").textbox('getValue') ;
            staff.job= $("#job").textbox('getValue') ;
            staff.name = $("#name").textbox('getValue') ;
            staff.hospitalId=parent.config.hospitalId;
            staff.idNo = $("#idNo").textbox('getValue');
            staff.ids=[] ;

            var rolesId = $("#roleIds").combobox('getValues') ;
            for(var i = 0 ;i<rolesId.length;i++){
                if(rolesId[i]){
                    staff.ids.push(rolesId[i]) ;
                }
            }
            if(pwd == confirm_pwd){
                if ($("#fm").form('validate')) {
                    $.postJSON("/api/staff-dict/add",staff,function(data){
                        $.messager.alert("系统提示","保存成功",'info') ;
                        loadStaff();
                        clearInput() ;
                        $("#dlg").dialog('close');
                    },function(data){
                        console.log(data);
                        $.messager.alert("系统提示","保存失败:"+data.responseJSON.errorMessage,'error') ;
                    })
                }
            }else{
                $.messager.alert("系统提示","两次输入的密码不一致，请重新输入",'info') ;
            }
        }

    })
    /**
     * 删除
     */
    $("#delBtn").on('click', function () {
        var row = $("#dg").datagrid('getSelected');
        if (!row) {
            $.messager.alert("系统提醒", "请选择要删除的行", "error");
            return;
        }
        var index = $("#dg").datagrid('getRowIndex', row);
        $.messager.confirm("系统提示", "确定要删除【" + row.loginName + "】吗？", function (r) {
            if (r) {
                $.delete("/api/staff-dict/del/" + row.id, function (data) {
                    loadStaff();
                });
            }
        });
    });
    /**
     * 修改员工信息
     *
     */
    $("#editBtn").on('click', function () {
        var row = $("#dg").datagrid("getSelected");
        console.log(row);
        if (!row) {
            $.messager.alert("系统提示", "请选择要修改的员工信息");
            return;
        }
        $("#id").val(row.id);
        $("#loginName").textbox('setValue', row.loginName);
        $("#name").textbox('setValue', row.name);
        $("#password").textbox('setValue', row.password);
        $("#confirm_password").textbox('setValue', row.password);
        $("#deptName").combobox('setValue',row.deptDict.id) ;
        $("#job").textbox('setValue', row.job);
        $("#idNo").textbox('setValue',row.idNo);
        $("#title").textbox('setValue', row.title);
        $("#roleIds").combobox('setValues',row.roleIds) ;
        $("#roleIds").combobox('loadData',role) ;
        $("#deptName").combobox('loadData',dept) ;
        $("#dlg").dialog('open').dialog('setTitle', "修改员工信息");
    });

})