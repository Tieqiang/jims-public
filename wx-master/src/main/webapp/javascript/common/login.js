$(function () {
    //config.hospitalName = "双滦区人民医院";
    setCookie("hospitalName", "双滦区人民医院");
    //config.exportClass = "'发放出库','批量出库','退账入库'";
    setCookie("exportClass", "'发放出库','批量出库','退账入库'");
    //config.defaultSupplier = "供应室";
    setCookie("defaultSupplier", "供应室");
    var menus = [];//菜单列表
    var hospitalId = '';
    var staffId='';
    var moduleId = '';
    var validateCode = '';
    //医院数据加载
    $('#hospitalId').combobox({
        width: 200,
        panelHeight: 'auto',
        url:'/api/hospital-dict/list',
        method:'GET',
        valueField: 'id',
        textField: 'hospitalName'
    });

    var changeImg = function () {
        var timestamp = (new Date()).valueOf();
        var url = "/api/login/code?temp=" + timestamp;
        $("#code").attr("src", url);
    }

    $("#code").on('click',function(){
        changeImg();
    })

    $("#moduleDialog").dialog({
        title: '选择模块',
        width: 700,
        height: 300,
        closed: true,
        catch: true,
        modal: true,
        closable: true,
        onClose: function () {
            getStaffMenu();
        }
    });
    $("#stockDialog").dialog({
        title: '选择库房',
        width: 700,
        height: 300,
        closed: true,
        catch: false,
        modal: true,
        closable: false,
        onClose: function () {

        }
    });

    //
    $("#OABtn").on('click', function () {
        moduleId = '';
        $("#moduleDialog").dialog('close');
    });
    //
    $("#RLBtn").on('click', function () {
        moduleId ='';
        $("#moduleDialog").dialog('close');
    });
    //
    $("#ZLBtn").on('click', function () {
        moduleId ='';
        alert("ZL");
        $("#moduleDialog").dialog('close');
    });
    //
    $("#JXBtn").on('click', function () {
        moduleId ='402886f350a6bd4f0150a6c0c4830001';
        //config.moduleId = moduleId;
        setCookie("moduleId", moduleId);
        //config.moduleName = "全成本核算系统";
        setCookie("moduleName", "全成本核算系统");
        $("#moduleDialog").dialog('close');
    });
    //
    $("#WZBtn").on('click', function () {
        moduleId ='402886f350a6bd4f0150a6c0c47c0000';
        //config.moduleId = moduleId;
        setCookie("moduleId", moduleId);
        //config.moduleName = "消耗品管理系统";
        setCookie("moduleName", "消耗品管理系统");
        $("#stockDialog").dialog('open');
        $('#stock').combobox({
            width: 200,
            panelHeight: 'auto',
            url: '/api/exp-storage-dept/list?hospitalId=' + hospitalId,
            method: 'GET',
            valueField: 'storageCode',
            textField: 'storageName',
            onSelect: function (item) {
                //config.storageDict = item;
                setCookie("storageDict", JSON.stringify(item));
                //config.storage = item.storageName;
                setCookie("storage", item.storageName);
                //config.storageCode = item.storageCode;
                setCookie("storageCode", item.storageCode);

                $("#stockDialog").dialog('close');
                $("#moduleDialog").dialog('close');
            }
        });
    });
    //
    $("#HQBtn").on('click', function () {
        moduleId ='';
        alert("HQ");
        $("#moduleDialog").dialog('close');
    });
    //
    $("#YYBtn").on('click', function () {
        moduleId ='';
        alert("YY");
        $("#moduleDialog").dialog('close');
    });







    var getRoleMenu = function(){
        var promise = $.get('/api/login/list-menu-by-staff-role?staffId=' + staffId, function (data) {
            console.log("staff role menu:" + data);
        });
    }

    var getModuleMenu = function () {
        var promise = $.get('/api/login/list-menu-by-module?moduleId=' + moduleId, function (data) {
            console.log("module menu:"+data);
        });
    }
    var getStaffMenu = function(){$.get('/api/login/list-menu-by-staff?staffId=' + staffId+'&moduleId=' + moduleId, function (data) {
            menus = data;
            //config.menus = menus;
            setCookie("menus", JSON.stringify(menus));
        }).done(function(){
            window.location.href = "../../../index.html";
        });

    }

    $("#clearBtn").on('click', function () {
        $('#login').form('clear');
        changeImg();
    });


    $("#loginBtn").on('click', function(){
        $('#login').form('submit', {
            url: '/api/login/login?loginName='+ $("#loginName").textbox("getText")+"&password="+ $("#password").textbox("getText")+"&validateCode="+ $("#validateCode").textbox("getText")+"&hospitalId="+ $("#hospitalId").combobox("getValue"),
            onSubmit: function () {
                // 做某些检查,返回 false 来阻止提交
                return $(this).form('enableValidation').form('validate');
            },
            success: function (data) {
                var map = JSON.parse(data);
                console.log("map"+map);
                if (!map.id) {
                    $.messager.alert("error", map.loginName);
                    changeImg();
                    $('#login').form('clear');
                } else {
                    hospitalId = map.hospitalId;
                    staffId = map.id;

                    //config.hospitalId = hospitalId;
                    setCookie("hospitalId", hospitalId);
                    //config.loginId = staffId;
                    setCookie("loginId", staffId);
                    //config.loginName = map.loginName;
                    setCookie("loginName", map.loginName);
                    //config.staff = map;
                    setCookie("staff", JSON.stringify(map));
                    $("#moduleDialog").dialog('open');
                }
            }
        });
    });


});
