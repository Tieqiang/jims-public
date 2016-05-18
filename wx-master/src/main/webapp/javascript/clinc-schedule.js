/**
 * Created by wei on 2016/3/28.
 */

$(function () {
    var editIndex;
    var clinicIndexId;

    //定义右侧多个操作的增删改数据
    var inserted = [];
    var updated = [];
    var deleted = [];

    var stopEdit = function () {
        if (editIndex || editIndex == 0) {
            $("#dg").datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
    }

    //保暂存增删改数据
    var addData = function(){
        if (editIndex || editIndex == 0) {
            $("#dg").datagrid("endEdit", editIndex);
        }
        //保存刷新之前的右侧datagrid里面的增删改数据倒数组里面
        if ($("#dg").datagrid("getChanges").length > 0) {

            var insertData = $("#dg").datagrid("getChanges", "inserted");
            var updateData = $("#dg").datagrid("getChanges", "updated");
            var deleteData = $("#dg").datagrid("getChanges", "deleted");

            if (insertData && insertData.length > 0) {
                for (var i = 0; i < insertData.length; i++) {
                    inserted.push(insertData[i]);
                }
            }
            if (updateData && updateData.length > 0) {
                for (var i = 0; i < updateData.length; i++) {
                    updated.push(updateData[i]);
                }
            }
            if (deleteData && deleteData.length > 0) {
                for (var i = 0; i < deleteData.length; i++) {
                    deleted.push(deleteData[i]);
                }
            }
        }
    }

    $("#dg").datagrid({
        title: '出诊安排维护',
        fit: true,//让#dg数据创铺满父类容器
        singleSelect: true,
        columns: [[{
            title: '编号',
            field: 'id',
            hidden: 'true'
        }, {
            title: '号别编号',
            field: 'clinicIndexId',
            hidden: 'true'
        }, {
            title: '出诊星期',
            field: 'dayOfWeek',
            width: "40%",
            editor: {
                type: 'combobox', options: {
                    panelHeight: 'auto',
                    valueField: 'baseCode',
                    textField: 'baseName',
                    url: "/api/base-dict/list-by-type?baseType=DAY_FLAG",
                    mode: 'remote',
                    method: 'GET'
                }
            }
        }, {
            title: '出诊时间',
            field: 'timeOfDay',
            width: "40%",
            editor: {
                type: 'combobox', options: {
                    panelHeight: 'auto',
                    valueField: 'baseCode',
                    textField: 'baseName',
                    url: "/api/base-dict/list-by-type?baseType=TIME_FLAG",
                    mode: 'remote',
                    method: 'GET'
                }
            }
        }, {
            title: '剩余号数',
            field: 'registrationLimits',
            width: "20%",
            editor: {
                type: 'numberbox',
                options: {
                    max: 40,
                    size: 2,
                    maxlength: 2,
                    minlength: 0
                }
            }
        }]],
        onClickRow: function (index, row) {
            stopEdit();
            $(this).datagrid('beginEdit', index);
            editIndex = index;
        }
    });



    //设置列
    $("#tt").treegrid({
        fit: true,
        idField: "id",
        treeField: "clinicName",
        fitColumns: true,
        title: "门诊号别",
        columns: [[{
            title: 'id',
            field: 'id',
            hidden:true
        },{
            title: '名称',
            field: 'clinicName',
            width:"60%"
        },{
            title: '科室',
            field: 'clinicDept',
            width:"40%"
        }]],
        onClickRow: function (row) {

            if(row.parentFlag!="Y"){
                clinicIndexId = row.id;   //号类ID赋值
            }else{
                clinicIndexId = null;   //号类ID赋值
            }

            addData();
//            alert(1);
            $.get("/api/clinic-schedule/find-by-id", {id: row.id}, function (data) {
                $("#dg").datagrid('loadData', data);
            });

        }
    });

    /**
     * 加载医院信息表
     */
    var loadDept = function () {

        var depts = [];
        var treeDepts = [];
        var loadPromise = $.get("/api/clinic-schedule/list-tree", function (data) {
            $.each(data, function (index, item) {
                var obj = {};
                obj.id = item.id;
                obj.parentFlag = item.parentFlag;
                obj.clinicName=item.clinicName;
                obj.clinicDept=item.clinicDept;
                obj.doctorId=item.doctorId;
                obj.children = item.clinicIndex;

                depts.push(obj);
            });
        });

        loadPromise.done(function () {
            for (var i = 0; i < depts.length; i++) {
                treeDepts.push(depts[i])
            }

            $("#tt").treegrid('loadData', treeDepts);
        })
    }

    loadDept();

    $("#bottom").datagrid({
        footer: '#tb',
        border: false
    });

    $('#cc').layout('panel', 'south').panel('resize', {height: 'auto'});

    $("#cc").layout({
        fit: true
    });

    var loadDict = function () {
        //var name = $("#name").textbox("getValue");
        //提交成功后刷新右侧datagrid
        $.get("/api/clinic-schedule/find-by-id?id="+ clinicIndexId, function (data) {
            $("#dg").datagrid('loadData', data);
        });
        //提交完成后重置增删改数据
        inserted = [];
        updated=[];
        deleted=[];
    }


    $("#addBtn").on('click', function () {
        stopEdit();
        if(null!=clinicIndexId){
            $("#dg").datagrid('appendRow', {clinicIndexId:clinicIndexId});
            var rows = $("#dg").datagrid('getRows');
            var addRowIndex = $("#dg").datagrid('getRowIndex', rows[rows.length - 1]);
            editIndex = addRowIndex;
            $("#dg").datagrid('selectRow', editIndex);
            $("#dg").datagrid('beginEdit', editIndex);
        }else{
            $.messager.alert("系统警告","请选择号别","error");
        }
    });

    $("#delBtn").on('click', function () {
        var row = $("#dg").datagrid('getSelected');
        if (row) {
            var rowIndex = $("#dg").datagrid('getRowIndex', row);
            $("#dg").datagrid('deleteRow', rowIndex);
            if (editIndex == rowIndex) {
                editIndex = undefined;
            }
        } else {
            $.messager.alert('系统警告', "请选择要删除的行", 'error');
        }
    });

    $("#saveBtn").on('click', function () {
        addData();
        //提交右侧刷新过的多个datagrid的增删改数据
        var beanChangeVo = {};

        beanChangeVo.inserted = inserted;// inserted;
        beanChangeVo.deleted = deleted; //deleted;
        beanChangeVo.updated = updated; //updated;

        console.log(beanChangeVo);

        if (beanChangeVo) {
            $.postJSON("/api/clinic-schedule/merge", beanChangeVo, function (data, status) {
                $.messager.alert("系统提示", "保存成功", "info");
                loadDict();
            }, function (data) {
                $.messager.alert('提示', data.responseJSON.errorMessage, "error");
                loadDict();
            })
        }

    });

});