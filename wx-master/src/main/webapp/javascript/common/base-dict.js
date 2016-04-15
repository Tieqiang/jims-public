/**
 * Created by heren on 2015/10/9.
 */
$(function(){

    var editIndex ;
    var stopEdit = function(){
        if(editIndex||editIndex==0){
            $("#baseDictDg").datagrid('endEdit',editIndex) ;
            editIndex = undefined ;
        }
    }
    $("#baseDictDg").datagrid({
        title:'基础字典维护',
        fit:true,
        //rownumbers:true,
        footer:"#tb",
        url:'/api/base-dict/list-by-type',
        method:'GET',
        singleSelect:true,
        columns:[[{
            title:'id',
            field:'id',
            hidden:true
        },{
            title:'键值',
            field:'baseCode',
            width:"20%",
            editor:'text'
        },{
            title:'键名',
            field:'baseName',
            width:'30%',
            editor: {type: 'textbox', options: {required: true}}
        },{
            title:'字典名称',
            field:'baseType',
            width:'30%',
            editor: {type: 'textbox', options: {required: true}}
        },{
            title:'输入码',
            field:'inputCode',
            width:'20%'
        }]],
        onClickRow:function(index,row){
            stopEdit() ;
            $(this).datagrid('beginEdit',index) ;
            editIndex = index ;
        }

    });


    /**
     * 添加字典
     */
    $("#addBaseDictBtn").on('click',function(){

        stopEdit() ;

        $("#baseDictDg").datagrid('appendRow',{}) ;
        var rows = $("#baseDictDg").datagrid('getRows') ;
        var addRowIndex = $("#baseDictDg").datagrid('getRowIndex',rows[rows.length -1]) ;
        editIndex =addRowIndex ;
        $("#baseDictDg").datagrid('selectRow',editIndex) ;
        $("#baseDictDg").datagrid('beginEdit',editIndex) ;
    }) ;


    /**
     * 追加键值
     */
    $("#appendBaseDictBtn").on('click',function(){
        stopEdit() ;
        var rows =$("#baseDictDg").datagrid('getRows') ;
        var lastRow ;
        if(rows.length>0){
            lastRow = rows[rows.length - 1] ;
        }

        if(lastRow){
            $("#baseDictDg").datagrid('appendRow',{id:'',baseCode:'',baseName:'',baseType:lastRow.baseType}) ;
            var newRows = $("#baseDictDg").datagrid('getRows') ;
            var newRowIndex = $("#baseDictDg").datagrid('getRowIndex',newRows[newRows.length -1 ]) ;

            editIndex = newRowIndex ;
            $("#baseDictDg").datagrid('selectRow',editIndex) ;
            $("#baseDictDg").datagrid('beginEdit',editIndex) ;
        }else{
            $("#baseDictDg").datagrid('appendRow',{}) ;
            var newRows = $("#baseDictDg").datagrid('getRows') ;
            var newRowIndex = $("#baseDictDg").datagrid('getRowIndex',newRows[newRows.length -1 ]) ;

            editIndex = newRowIndex ;
            $("#baseDictDg").datagrid('selectRow',editIndex) ;
            $("#baseDictDg").datagrid('beginEdit',editIndex) ;
        }
    }) ;

    /**
     * 删除字典操作
     */
    $("#delBaseDictBtn").on('click',function(){

        var row = $("#baseDictDg").datagrid('getSelected') ;
        if(row){
            var rowIndex = $("#baseDictDg").datagrid('getRowIndex',row) ;
            $("#baseDictDg").datagrid('deleteRow',rowIndex) ;
            if(editIndex ==rowIndex){
                editIndex = undefined ;
            }
        }else{
            $.messager.alert('系统提示',"请选择要删除的行",'info') ;
        }
    }) ;

    /**
     * 查询操作
     */
    /*$("#searchBtn").on('click',function(){
        var dictType = $("#baseName").textbox('getValue');

        $("#baseDictDg").datagrid(
            'load',{
                baseType:dictType
            }
        ) ;
    }) ;*/

    /**
     * 加载基础信息表
     */
    var loadDict = function () {
        $.get("/api/base-dict/list", function (data) {
            $("#baseDictDg").datagrid('loadData', data);
        });
    }

    /**
     * 保存修改内容
     */
    $("#saveBaseDictBtn").on('click',function(){

        if(editIndex||editIndex==0){
            $("#baseDictDg").datagrid('endEdit',editIndex) ;

        }
        var inserted  = $("#baseDictDg").datagrid('getChanges','inserted') ;
        var deleted = $("#baseDictDg").datagrid('getChanges','deleted') ;
        var updated = $("#baseDictDg").datagrid('getChanges','updated') ;

        var baseDictBeanChangeVo = {} ;
        baseDictBeanChangeVo.inserted = inserted ;
        baseDictBeanChangeVo.deleted = deleted ;
        baseDictBeanChangeVo.updated = updated ;

        $.postJSON("/api/base-dict/merge",baseDictBeanChangeVo,function(data,status){
            $.messager.alert("系统提示","保存成功","info");
            loadDict();
        },function(error){
            $.messager.alert("系统提示","保存失败","error");
            loadDict();
        })
    }) ;

})