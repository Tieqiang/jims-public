/**
 * Created by heren on 2015/11/27.
 */
$(function(){

    $("#win").window({
        modal:true,
        width:350,
        height:400,
        title:'登录内容选择',
        onOpen:function(){
            $("#win").window('center') ;
        },
        footer:"#ft"
    }) ;

    //医院选择
    $("#hospital").combobox({
        method:'GET',
        url:"/api/hospital-dict/list",
        valueField:'id',
        textField:"hospitalName",
        onLoadSuccess:function(){
            var data = $(this).combobox('getData') ;
            if(data){
                $(this).combobox('select',data[0].id) ;
            }
        },
        onSelect:function(item){
            //模块选择
            $("#modual").combobox({
                method: 'GET',
                url: '/api/module-dict/list-by-staff?hospitalId=' + item.id,
                valueField: 'id',
                textField: 'moduleName',
                onLoadSuccess: function () {
                    var data = $(this).combobox('getData');
                    if (data.length > 0) {
                        $(this).combobox('select', data[0].id);
                        $("#modualDiv").show();
                    }else{
                        $("#modual").combobox("clear");
                        $("#stock").combobox("clear");
                        $("#modualDiv").hide();
                        $("#stockDiv").hide();
                    }
                },
                onSelect: function (item) {
                    if (item.moduleName == "消耗品管理系统") {
                        //库房选择
                        $('#stock').combobox({
                            panelHeight: 200,
                            url: '/api/exp-storage-dept/list?hospitalId=' + $("#hospital").combobox('getValue'),
                            method: 'GET',
                            valueField: 'id',
                            textField: 'storageName',
                            onLoadSuccess: function () {
                                var data = $(this).combobox('getData');
                                if (data.length > 0) {
                                    $(this).combobox('select', data[0].id);
                                    $("#stockDiv").show();
                                } else {
                                    $("#stock").combobox("clear");
                                    $("#stockDiv").hide();
                                }
                            }
                        });
                    } else {
                        $("#stockDiv").hide();
                    }

                }
            });

        }
    }) ;

    var validateSelect=function(){
        var hospitalId = $("#hospital").combobox('getValue');
        var moduleId = $("#modual").combobox('getValue');
        var storageCode = $("#stock").combobox('getValue');
        if($.trim(hospitalId)==""){
            $.messager.alert("警告","请选择医院！","error");
            return false;
        }
        if ($.trim(moduleId) == "") {
            $.messager.alert("警告", "请选择模块！", "error");
            return false;
        }
        if ($.trim(storageCode) == "") {
            $.messager.alert("警告", "请选择库房！", "error");
            return false;
        }
        return true;
    }


    //下一步操作
    $("#saveItemBtn").on('click',function(){
        var hospitalId = $("#hospital").combobox('getValue') ;
        var moduleId = $("#modual").combobox('getValue') ;
        var storageCode = $("#stock").combobox('getValue');

        var config ={} ;
        config.hospitalId = hospitalId ;
        config.moduleId = moduleId ;
        config.storageCode = storageCode;
        //if(validateSelect()){
        $.postJSON("/api/login/add-login-info", config, function (data) {
            //登录成功跳转至index.html
            location.href = "/index.html"
        }, function (data) {

        })
        //}
    })

    //取消登录按钮
    $("#cancelItemBtn").on('click',function(){
        location.href="/login1.html" ;
    }) ;


})