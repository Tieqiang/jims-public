/**
 * Created by heren on 2015/9/16.
 */
/***
 * 系统模块维护
 */
$(function () {
    var editRowIndex;
    var menus = [];//菜单数组
    var menuTreeData = [];//菜单树对象
    var staffs = [];//staff数组
    var staffTreeData = [];//staff树对象
    $("#dg").datagrid({
        title: '模块名称维护',
        fit: true,//让#dg数据创铺满父类容器
        footer: '#tb',
        singleSelect: true,
        columns: [[{
            title: '编号',
            field: 'id',
            hidden: 'true'
        },{
            title: '模块名称',
            field: 'moduleName',
            width: "20%",
            editor: {
                type: 'validatebox', options: {
                    //required: true, validType: 'length[0,128]', missingMessage: '请输入64个以内的汉字'
                }
            }
        },{
            title:'模块主页路径',
            field:'moduleLoad',
            width:'20%',
            editor: {
                type: 'textbox'
            }
        },{
            title:'输入码',
            field:'inputCode',
            width:'20%'
        },{
            title:'员工Ids',
            field:'staffIds',
            hidden:true
        },{
            title:'模块权限Ids',
            field:'menuIds',
            editor: {
                type: 'textbox'
            }
        }]]
    });

    $("#searchBtn").on("click", function () {
        var name = $("#name").textbox("getValue");

        $.get("/api/module-dict/list?name=" + name, function (data) {
            $("#dg").datagrid('loadData', data);
        });
    });

    $("#addBtn").on('click', function () {
        console.log(editRowIndex) ;
        if(editRowIndex||editRowIndex==0){
            console.log("wocha")
            $("#dg").datagrid('endEdit',editRowIndex) ;
        }
        $("#dg").datagrid('appendRow', {});
        var rows = $("#dg").datagrid('getRows');
        if(rows.length>0){
            var rowIndex = $("#dg").datagrid('getRowIndex',rows[rows.length - 1]);
            if(rowIndex||rowIndex==0){
                editRowIndex = rowIndex ;
                $("#dg").datagrid("beginEdit",editRowIndex) ;
            }
        }


    });

    $("#delBtn").on('click', function () {
        var row = $("#dg").datagrid('getSelected');
        if (!row) {
            $.messager.alert("系统提醒", "请选择要删除的行", "error");
            return;
        }

        var index = $("#dg").datagrid('getRowIndex', row);

        if (index == editRowIndex) {
            editRowIndex = undefined;
        }
        $("#dg").datagrid('deleteRow', index);

    });

    $("#editBtn").on('click', function () {
        var row = $("#dg").datagrid('getSelected');
        if (!row) {
            $.messager.alert("系统提醒", "请选择要编辑的行", "error");
            return;
        }

        var index = $("#dg").datagrid('getRowIndex', row);

        if (editRowIndex == undefined) {

            $("#dg").datagrid("beginEdit", index);
            editRowIndex = index;
        } else {
            $("#dg").datagrid('endEdit', editRowIndex);
            $("#dg").datagrid('beginEdit', index);
            editRowIndex = index;
        }
    });

    var loadDict = function () {

        $.get("/api/module-dict/list", function (data) {
            $("#dg").datagrid('loadData', data);
        });
    }

    loadDict();


    /**
     * 保存修改的内容
     * 基础字典的改变，势必会影响其他的统计查询
     * 基础字典的维护只能在基础数据维护的时候使用。
     */
    $("#saveBtn").on('click', function () {
        console.log(editRowIndex)
        if (editRowIndex||editRowIndex==0) {
            $("#dg").datagrid('endEdit', editRowIndex);
            editRowIndex = undefined;
        }
        var insertData = $("#dg").datagrid('getChanges', 'inserted');
        var updateData = $("#dg").datagrid('getChanges', 'updated');
        var deleteData = $("#dg").datagrid('getChanges', 'deleted');

        var modulDictBeanChangeVo = {} ;
        modulDictBeanChangeVo.inserted=[] ;
        modulDictBeanChangeVo.deleted=[] ;
        modulDictBeanChangeVo.updated = [] ;

        for(var i = 0 ;i<insertData.length ;i++){
            var modulDict = {} ;
            modulDict.id = insertData[i].id ;
            modulDict.moduleName = insertData[i].moduleName ;
            modulDict.inputCode = insertData[i].inputCode ;
            modulDict.moduleLoad = insertData[i].moduleLoad ;
            modulDict.hospitalId = parent.config.hospitalId ;
            modulDictBeanChangeVo.inserted.push(modulDict) ;
        }

        for(var i = 0 ;i<updateData.length ;i++){
            var modulDict = {} ;
            modulDict.id = updateData[i].id ;
            modulDict.moduleName = updateData[i].moduleName ;
            modulDict.inputCode = updateData[i].inputCode ;
            modulDict.moduleLoad = updateData[i].moduleLoad ;
            modulDict.hospitalId = parent.config.hospitalId ;
            modulDictBeanChangeVo.updated.push(modulDict) ;
        }
        for(var i = 0 ;i<deleteData.length ;i++){
            var modulDict = {} ;
            modulDict.id = deleteData[i].id ;
            modulDict.moduleName = deleteData[i].moduleName ;
            modulDict.inputCode = deleteData[i].inputCode ;
            modulDict.moduleLoad = deleteData[i].moduleLoad ;
            modulDict.hospitalId = parent.config.hospitalId ;
            modulDictBeanChangeVo.deleted.push(modulDict) ;
        }

        $.postJSON("/api/module-dict/save",modulDictBeanChangeVo,function(data){
            $.messager.alert("系统提示","保存成功",'info') ;
        },function(data){
            $.messager.alert("系统提示",data.responseText.errorMessage,'error') ;
        })

    });


    //树定义
    $("#tt").tree({
        cascadeCheck:true,
        checkbox: true
    });

    var loadTreeData = function () {
        var promise = $.get("/api/menu/list", function (data) {
            $.each(data, function (index, item) {
                var menu = {};
                menu.attributes = {};
                menu.id = item.id;
                menu.text = item.menuName;
                menu.state = "open";
                menu.attributes.url = item.href;
                menu.attributes.parentId = item.parentId;
                menu.children = [];
                menus.push(menu);
            })

            for (var i = 0; i < menus.length; i++) {
                for (var j = 0; j < menus.length; j++) {
                    if (menus[i].id == menus[j].attributes.parentId) {
                        menus[i].children.push(menus[j]);
                    }
                }

                if (menus[i].children.length > 0 && !menus[i].attributes.parentId) {
                    menuTreeData.push(menus[i]);
                }

                if (!menus[i].attributes.parentId && menus[i].children.length <= 0) {
                    menuTreeData.push(menus[i]);
                }
            }
        });

        promise.done(function () {
            $("#tt").tree('loadData', menuTreeData);
        })
    }
    //加载菜单树数据
    loadTreeData();

    $("#menuAddBtn").on('click', function () {
        var row=$("#dg").datagrid('getSelected');
        if(!row){
            $.messager.alert('系统提示','请选择模块，然后再分配权限','error');
            return ;
        }
        $("#dlg").dialog('open').dialog('setTitle', '分配权限');
        var menuIds = row.menuIds ;
        if(!menuIds){
            return ;
        }
        var arr = new Array;
        arr = menuIds.split(",");
        for(i=0;i<arr.length;i++){

            var node=  $("#tt").tree('find',arr[i]) ;
            if(node !=null){
                var children = $("#tt").tree('getChildren',node.target);
                if(children.length > 0){
                    continue ;
                }
                $('#tt').tree('check', node.target);//将得到的节点选中
            }

        }
    });

    //全选
    $("#selectAllMenuBtn").on('click', function () {
        clearMenuBtn();
        var nodes =$('#tt').tree('getChecked',"unchecked");
        var flag = nodes.checked ? "uncheck" : "check";
        for(var i=0;i<nodes.length;i++){
            $('#tt').tree(flag, nodes[i].target);//将得到的节点选中
        }
    });
    //全不选
    $("#selectNoMenuBtn").on('click', function () {
        clearMenuBtn();
    });
    //清空选项
    var clearMenuBtn = function(){
        var nodes =$('#tt').tree('getChecked',['checked','indeterminate','unchecked']);
        var flag = nodes.checked ? "check" : "uncheck";
        for(var i=0;i<nodes.length;i++){
            $('#tt').tree(flag, nodes[i].target);//将得到的节点清空
        }
    }
    //保存分配的权限
    $("#saveMenuBtn").on('click',function(){
        var nodes=$("#tt").tree('getChecked',['checked','indeterminate']) ;
        var data = {} ;
        var row=$("#dg").datagrid('getSelected');
        if(!row.id){
            $.messager.alert("系统提示","请先保存模块然后再分配菜单","info") ;
            return ;
        }
        data.moduleId =row.id ;
        data.menuId=[] ;
        $.each(nodes,function(index,item){
            data.menuId.push(item.id) ;
        })

        $.postJSON("/api/module-dict/add-module-menu/"+data.moduleId,data.menuId,function(){
            $.messager.alert('系统提示','菜单分配成功','info');
            $("#dlg").dialog('close') ;
            $.each(nodes,function(index,item){
                $("#tt").tree('uncheck',item.target);
            })
            loadDict() ;
        },function(){
            $.messager.alert('系统提示','菜单分配失败','error');
        })

    });



    $("#checkStaffWin").window({
        title: '设置模块医生',
        width: 700,
        height: 500,
        onOpen: function () {
            $(this).window('center');
        }
    });
    $("#staffName").searchbox({
        searcher: function (value, name) {
            var rows = $("#waitCheckStaff").datagrid("getRows");
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].name == value || rows[i].loginName == value) {
                    $("#waitCheckStaff").datagrid('selectRow', i);
                }
            }
        }
    });
    $("#waitCheckStaff").datagrid({
        title: '待选工作人员',
        fitColumns: true,
        width: 200,
        fit: true,
        toolbar: '#waitTb',
        columns: [[{
            title: '编号',
            field: 'id',
            checkbox: true
        }, {
            title: '登录名',
            field: 'loginName',
            width:'40%'
        }, {
            title: '姓名',
            field: 'name',
            width: '55%'
        }]]

    });
    $("#checkedStaff").datagrid({
        title: '已选工作人员',
        fitColumns: true,
        width: 200,
        fit: true,
        singleSelect: false,
        columns: [[{
            title: '编号',
            field: 'id',
            checkbox: true
        }, {
            title: '登录名',
            field: 'loginName',
            width: '40%'
        }, {
            title: '姓名',
            field: 'name',
            width: '55%'
        }]]
    });

    $("#staffAddBtn").on('click', function () {
        var row = $("#dg").datagrid('getSelected');
        if (!row) {
            $.messager.alert('系统提示', '请选择模块，然后再分配权限', 'error');
            return;
        }
        $("#checkStaffWin").window('open');

        var staffIds = new Array;
        staffIds= row.staffIds.split(",");
        var selectStaffs=[];
        var staffs = [];
        var loadPromise = $.get("/api/staff-dict/list", function (data) {
            var flag = true;
            for(var i =0;i<data.length;i++){
                    for(var j=0;j<staffIds.length;j++){
                        if(staffIds[j]==data[i].id){
                            selectStaffs.push(data[i]);
                            flag=false;
                        }
                    }
                if(flag==true){
                    staffs.push(data[i]);
                }
                flag=true;
            }
        });

        loadPromise.done(function () {
            $("#waitCheckStaff").datagrid('loadData', staffs);
            $("#checkedStaff").datagrid('loadData', selectStaffs);
        })

    });

    //添加全部
    $("#selectAllStaffBtn").on('click', function () {
        var rows = $('#waitCheckStaff').datagrid('getRows');
        $.messager.confirm("提示信息", "确认添加全部待选人员？", function (r) {
            if (r) {
                $.each(rows, function (index, row) {
                    $('#checkedStaff').datagrid('appendRow', row);
                });
                $('#waitCheckStaff').datagrid('loadData', []);
            }
        });

    });
    //添加所选
    $("#addStaffBtn").on('click', function () {
        var rows = $('#waitCheckStaff').datagrid('getSelections');
        if(rows.length==0){
            $.messager.alert("提示","请选择待选工作人员！","info");
        }else{
            $.each(rows, function (index, row) {
                $('#checkedStaff').datagrid('appendRow', row);
                var delIndex = $('#waitCheckStaff').datagrid('getRowIndex', row);
                $('#waitCheckStaff').datagrid('deleteRow', delIndex);
            });
        }
    });
    //删除所选
    $("#delStaffBtn").on('click', function () {
        var rows = $('#checkedStaff').datagrid('getSelections');
        if (rows.length == 0) {
            $.messager.alert("提示", "请选择已选工作人员！", "info");
        } else {
            $.each(rows, function (index, row) {
                $('#waitCheckStaff').datagrid('appendRow', row);
                var delIndex = $('#checkedStaff').datagrid('getRowIndex', row);
                $('#checkedStaff').datagrid('deleteRow', delIndex);
            });
        }
    });
    //删除全部
    $("#selectNoStaffBtn").on('click', function () {
        var rows = $('#checkedStaff').datagrid('getRows');
        $.messager.confirm("提示信息", "确认删除全部已选人员？", function (r) {
            if (r) {
                $.each(rows, function (index, row) {
                    $('#waitCheckStaff').datagrid('appendRow', row);
                });
                $('#checkedStaff').datagrid('loadData', []);
            }
        });
    });

    //保存分配的权限
    $("#saveStaffBtn").on('click', function () {

        var nodes = $("#checkedStaff").datagrid('getRows');

        var data = {};
        var row = $("#dg").datagrid('getSelected');
        if (!row.id) {
            $.messager.alert("系统提示", "请先保存模块然后再分配用户权限", "info");
            return;
        }
        data.moduleId = row.id;
        data.staffId = [];
        $.each(nodes, function (index, item) {
            data.staffId.push(item.id);
        })

        $.postJSON("/api/module-dict/add-module-staff/" + data.moduleId, data.staffId, function () {
            $.messager.alert('系统提示', '分配用户权限成功', 'info');
            $("#checkStaffWin").window('close');
            loadDict();
        }, function () {
            $.messager.alert('系统提示', '分配用户权限失败', 'error');
        })

    });



})