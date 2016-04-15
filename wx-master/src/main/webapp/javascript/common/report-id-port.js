/**
 * Created by heren on 2015/9/17.
 */

/**
 * 报表服务器信息维护
 */
$(function(){
    var editIndex;
    var stopEdit = function () {
        if (editIndex || editIndex == 0) {
            $("#dg").datagrid('endEdit', editIndex);
            editIndex = undefined;
        }
    }
    //初始化表单
    var data =[] ;

    var promise =$.get('/api/hospital-dict/list',function(data1){
        data = data1 ;
    }) ;

    promise.done(function(){
        $("#dg").datagrid({
            title:'报表服务器信息维护',
            singleSelect:true,
            fit:true,
            method:'GET',
            footer:'#tb',
            columns:[[{
                title: 'id',
                field: 'id',
                hidden:true
            },{
                title:'医院代码',
                field:'hospitalId',
                width:"20%",
                editor:{type:'textbox',options:{editable:false,disable:true}},
                hidden: true
            },{
                title:'医院名称',
                field:'hospitalName',
                width:"50%",
                editor:{type:"combogrid",options:{
                    idField:'id',
                    textValue:'hospitalName',
                    panelWidth:500,
                    fitColumn:true,
                    data:data,
                    singleSelect:true,
                    method:'GET',
                    mode:'local',
                    columns:[[{
                        field:'id',title:'医院代码',width:250
                    },{
                        field:'hospitalName',title:'医院名称',width:200
                    }]],
                    onClickRow:function(index,row){
                        var ed = $("#dg").datagrid('getEditor',{index:editIndex,field:'hospitalId'}) ;
                        $(ed.target).textbox('setValue',row.id) ;

                        var comboEd = $("#dg").datagrid('getEditor',{index:editIndex,field:'hospitalName'}) ;
                        $(comboEd.target).combogrid('setValue',row.hospitalName) ;
                    },
                    keyHandler: $.extend({}, $.fn.combogrid.defaults.keyHandler,{
                        enter:function(e){

                            var row  = $(this).combogrid('grid').datagrid('getSelected') ;
                            if(row){
                                var ed = $("#dg").datagrid('getEditor',{index:editIndex,field:'hospitalId'}) ;
                                $(ed.target).textbox('setValue',row.id) ;
                                var comboEd = $("#dg").datagrid('getEditor',{index:editIndex,field:'hospitalName'}) ;
                                $(comboEd.target).combogrid('setValue',row.hospitalName) ;
                            }

                            $(this).combogrid('hidePanel') ;
                        }
                    }),
                    filter:function(q,row){

                        if($.startWith(row.inputCode,q)){
                            var dg = $(this).combogrid('grid') ;

                            var index = dg.datagrid('getRowIndex',row) ;
                            dg.datagrid('selectRow',index) ;
                            dg.datagrid('scrollTo',index);
                            return true ;
                        }
                        return false ;

                    }
                }}
            },{
                title:'IP地址',
                field:'ip',
                width:"30%",
                editor:{type:'text'}
            },{
                title:'端口号',
                field:'port',
                width:"20%",
                editor: {type: 'text'}
            }]],
            onClickRow: function (index, row) {
                stopEdit();
                $(this).datagrid('beginEdit', index);
                editIndex = index;
            }
        }) ;
    })
    var loadDict = function () {
        $.get('/api/report-dict/list', function (data) {
            if (data.length > 0) {
                $("#dg").datagrid('loadData', data);
            } else {
                $.messager.alert("系统提示", "暂无数据", "info");
            }
        });
    }
    loadDict();
    $("#searchBtn").on("click", function () {
        loadDict();
    });

    $("#addBtn").on('click',function(){
        $("#dg").datagrid('appendRow',{}) ;
        var rows = $("#dg").datagrid('getRows') ;
        var editRow = rows[rows.length -1 ] ;
        var rowIndex = $("#dg").datagrid("getRowIndex",editRow) ;

        if(editIndex==undefined || editIndex !=rowIndex){
            $("#dg").datagrid("endEdit",editIndex) ;
            $("#dg").datagrid("beginEdit",rowIndex) ;
            editIndex = rowIndex ;
        }

        $("#dg").datagrid('selectRow',editIndex) ;
        var comboEdit = $("#dg").datagrid('getEditor',{index:rowIndex,field:'hospitalName'}) ;
        $(comboEdit.target).focus() ;
        $(comboEdit.target).combogrid('showPanel') ;
    }) ;

    /**
     * 进行保存操作
     */
    $("#saveBtn").on('click',function(){
        if (editIndex || editIndex == 0) {
            $("#dg").datagrid("endEdit", editIndex);
        }

        var insertData = $("#dg").datagrid("getChanges", "inserted");
        var updateDate = $("#dg").datagrid("getChanges", "updated");
        var deleteDate = $("#dg").datagrid("getChanges", "deleted");

        var beanChangeVo = {};
        beanChangeVo.inserted = insertData;
        beanChangeVo.deleted = deleteDate;
        beanChangeVo.updated = updateDate;


        if (beanChangeVo) {
            $.postJSON("/api/report-dict/merge", beanChangeVo, function (data, status) {
                $.messager.alert("系统提示", "保存成功", "info");
                loadDict();
            }, function (data) {
                $.messager.alert('提示', data.responseJSON.errorMessage, "error");
            })
        }
    }) ;


    $("#delBtn").on('click',function(){
        var row = $("#dg").datagrid('getSelected');
        if (row) {
            var rowIndex = $("#dg").datagrid('getRowIndex', row);
            $("#dg").datagrid('deleteRow', rowIndex);
            if (editIndex == rowIndex) {
                editIndex = undefined;
            }
        } else {
            $.messager.alert('系统提示', "请选择要删除的行", 'info');
        }
    }) ;



    $("#editBtn").on('click',function(){
        var row = $("#dg").datagrid("getSelected");
        var index = $("#dg").datagrid("getRowIndex", row);

        if (index == -1) {
            $.messager.alert("提示", "请选择要修改的行！", "info");
            return;
        }

        if (editIndex == undefined) {
            $("#dg").datagrid("beginEdit", index);
            editIndex = index;
        } else {
            $("#dg").datagrid("endEdit", editIndex);
            $("#dg").datagrid("beginEdit", index);
            editIndex = index;
        }
    })
})