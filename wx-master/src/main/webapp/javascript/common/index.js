/**
 * Created by heren on 2015/9/11.
 */
var config = new common() ;


window.addTab = function (title, href) {
    //如果路径为空，则直接返回
    if(!href){
        return ;
    }
    var tabs = $("#mainContent").tabs('tabs');
    if(tabs.length>10){
        $.messager.alert("系统提示","打开的Tab页面太多，请观不需要的，重新在打开",'info') ;
        return ;
    }
    if($("#mainContent").tabs('exists',title)){
        $("#mainContent").tabs('select',title);
    }else{
        var content = undefined ;
        if($.startWith(href,'http')){
            if(href.indexOf("reportlet")){
                href = href+"&dept_id="+config.acctDeptId ;
                console.log(href) ;
            }
            content= '<iframe scrolling="auto" frameborder="0"  src="'+href+'" style="width:100%;height:100%;"></iframe>' ;
        }else{
            content= '<iframe scrolling="auto" frameborder="0"  src="views'+href+'.html" style="width:100%;height:100%;"></iframe>'
        }

        $("#mainContent").tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}

window.updateTab = function (title, href) {
    var content = undefined;
    if ($.startWith(href, 'http')) {
        if(href.indexOf("reportlet")){
            href = href+"&dept_id="+config.acctDeptId ;
        }
        content = '<iframe scrolling="auto" frameborder="0"  src="' + href + '" style="width:100%;height:100%;"></iframe>';
    } else {
        content = '<iframe scrolling="auto" frameborder="0"  src="views' + href + '.html" style="width:100%;height:100%;"></iframe>'
    }
    if ($("#mainContent").tabs('exists', title)) {
        $("#mainContent").tabs('select', title);
        var tab = $("#mainContent").tabs('getSelected');
        $("#mainContent").tabs('update', {
            tab: tab,
            options: {
                title: title,
                content: content,
                closable: true
            }
        });
    }
};
$(function(){
    $("#closeDlgNew").on('click', function () {
        $('#dlgNew').dialog('close');
    })
    $("#closeDlg").on('click',function(){
        $('#dlg').dialog('close');
    })
    $("#reLogin").on('click',function(){
        location.href="/login.html"
    })

    $("#dlgNew").dialog({
        title: '修改密码',
        width: 300,
        height: 250,
        closed: true,
        buttons: '#dlg-buttons',
        modal: true
    })
    $("#dlg").dialog({
        title: '修改密码',
        width: 300,
        height: 200,
        closed: true,
        buttons: '#dlgNew-buttons',
        modal: true
    })
    $("#cc").layout() ;
    $("#mainContent").tabs({
        onContextMenu:function(e, title,index){
            e.preventDefault();
            if(index>0){
                $('#mm').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                }).data("tabTitle", title);
            }
        }
    })

    $("#mm").menu({
        onClick : function (item) {
            closeTab(item.id);
        }
    }) ;
    var onlyOpenTitle = "主页";
    function closeTab(action) {
        var alltabs = $('#mainContent').tabs('tabs');
        var currentTab = $('#mainContent').tabs('getSelected');
        var allTabtitle = [];
        $.each(alltabs, function (i, n) {
            allTabtitle.push($(n).panel('options').title);
        })


        switch (action) {

            case "close":
                var currtab_title = currentTab.panel('options').title;
                $('#mainContent').tabs('close', currtab_title);
                break;
            case "closeall":
                $.each(allTabtitle, function (i, n) {
                    if (n != onlyOpenTitle) {
                        $('#mainContent').tabs('close', n);
                    }
                });
                break;
            case "closeother":
                var currtab_title = currentTab.panel('options').title;
                $.each(allTabtitle, function (i, n) {
                    if (n != currtab_title && n != onlyOpenTitle) {
                        $('#mainContent').tabs('close', n);
                    }
                });
                break;
            case "closeright":
                var tabIndex = $('#mainContent').tabs('getTabIndex', currentTab);

                if (tabIndex == alltabs.length - 1) {
                    $.messager.alert("提示","该页已经是最右页！","info");
                    return false;
                }
                $.each(allTabtitle, function (i, n) {
                    if (i > tabIndex) {
                        if (n != onlyOpenTitle) {
                            $('#mainContent').tabs('close', n);
                        }
                    }
                });

                break;
            case "closeleft":
                var tabIndex = $('#mainContent').tabs('getTabIndex', currentTab);
                if (tabIndex == 1) {
                    $.messager.alert("提示", "该页已经是最左页！", "info");
                    return false;
                }
                $.each(allTabtitle, function (i, n) {
                    if (i < tabIndex) {
                        if (n != onlyOpenTitle) {
                            $('#mainContent').tabs('close', n);
                        }
                    }
                });

                break;
            case "exit":
                $('#mm').menu('hide');
                break;
        }
    }


    var promise = $.get('/api/login/get-login-info',function(data){
        config =data ;
    })

    promise.done(function(){
        var menus=[] ;//菜单列表
        var menuTreeData = [] ;//菜单树的列表
        //var url = "api/menu/list-login-module?moduleId="+config.moduleId+"&loginName="+config.loginName;
        var lisAll = "api/menu/list" ;
        $(".site_title").append(config.hospitalName+"-"+config.moduleName) ;

        //var url = "api/menu/list-login-module?moduleId=402886f350a6bd4f0150a6c0c4830001&loginName=000WL2"

        var menuPromise = $.get(lisAll,function(data){
            $.each(data,function(index,item){
                var menu ={} ;
                menu.attributes={} ;
                menu.id = item.id ;
                menu.text = item.menuName ;
                menu.state = "open" ;
                menu.attributes.url = item.href ;
                menu.attributes.parentId = item.parentId ;
                menu.attributes.index = item.position;
                menu.children=[] ;
                menus.push(menu) ;
            })

            for(var i = 0 ;i<menus.length;i++){
                for(var j = 0 ;j<menus.length;j++){
                    if(menus[i].id ==menus[j].attributes.parentId){
                        menus[i].children.push(menus[j]) ;
                        menus[i].state='closed' ;
                    }
                }

                if(menus[i].children.length>0 && !menus[i].attributes.parentId){
                    menuTreeData.push(menus[i]) ;
                }

                if(!menus[i].attributes.parentId&&menus[i].children.length<=0){
                    menuTreeData.push(menus[i]) ;
                }
            }
            if(menuTreeData.length){
                menuTreeData[0].state='open' ;
            }
            menus.sort(function(a,b){
                return a.attributes.index- b.attributes.index;
            });
        }) ;

        menuTreeData.sort(function (a, b) {
            return a[0].attributes.index - b[0].attributes.index;
        });
        //$("#menuTree").accordion({
        //    fit:true
        //}) ;
        $("#uName").html(config.staffName) ;
        if(config.moduleName=="微信公众平台系统"){
            $("#storageName").html(config.storageName + "-");
        }
        //初始化菜单
        menuPromise.done(function(){

            $("#menuTree").tree({
                fit:true,
                lines:true,
                onClick:function(node){
                    if(node.attributes.url){
                        addTab(node.text,node.attributes.url) ;
                    }
                }
            }) ;

            $("#menuTree").tree('loadData',menuTreeData);
            //$("#menuTree").tree('collapseAll')//默认折叠所有的选项
            var load = {};
            if(menuTreeData){
                var  promiseLoad = $.get("/api/module-dict/list-tabs", function (data) {
                    load=data;
                    return load;
                });
                promiseLoad.done(function(){
                    if(load.length>0){
                        parent.addTab(load[0].moduleName, load[0].moduleLoad);
                    }

                });
            }
        }) ;


    })
    $("#changePassWord").on('click', function () {
        $("#password").textbox('clear');
        $("#dlg").dialog('open').dialog('setTitle', '修改密码');
    })
    $("#saveBtnNew").on('click', function () {
        var password = psdEdit($("#password").textbox('getValue'));
        if ($("#fm").form('validate')) {
            $.get("/api/staff-dict/edit-pwd?hospitalId=" + parent.config.hospitalId + "&loginId=" + parent.config.loginId, function (data) {
                $("#dlg").dialog('close');
                if (password == data.password) {
                    $("#dlgNew").dialog('open').dialog('setTitle', '修改密码');
                    $("#new_password").textbox('clear');
                    $("#confirm_password").textbox('clear');
                } else {
                    $.messager.alert("系统提示", "密码错误请重新输入", 'error');
                }
            })
        }else{
            $.messager.alert("系统提示", "密码不能为空", 'info');
        }
    })
    $("#saveBtn").on('click',function(){
        if ($("#fm1").form('validate')) {
            var password1 = psdEdit($("#new_password").textbox('getValue'));
            var password2 = psdEdit($("#confirm_password").textbox('getValue'));
            if(password1==password2){
                var staffDict={}
                staffDict.id= parent.config.loginId;
                staffDict.hospitalId = parent.config.hospitalId;
                staffDict.password = password1;
                $.postJSON("/api/staff-dict/edit-pwd-save", staffDict,function (data) {
                    $.messager.alert("系统提示", "修改密码成功", 'success');
                    $("#dlgNew").dialog("close");
                },function(data){
                    $.messager.alert("系统提示", "修改密码失败", 'error');
                })
                //$("#dlgNew").dialog('close')
            }else{
                $.messager.alert("系统提示", "两次输入的密码不一致，请重新输入", 'error');
            }
        }else{
            $.messager.alert("系统提示", "密码不能为空", 'info');
        }
    })
    var psdEdit = function(data){
        switch (data.length){
            case 1:
                data = "00000"+data;
                break;
            case 2:
                data = "0000"+data;
                break;
            case 3:
                data = "000"+data;
                break;
            case 4:
                data = "00"+data;
                break;
            case 5:
                data = "0"+data;
                break;
        }
    return data;
    }


}) ;


//config.defaultSupplier = "供应室"
//config.hospitalId = "4028862d4fcf2590014fcf9aef480016" ;
//config.hospitalName = "双滦区人民医院" ;
//config.storage = "五金库";
//config.storageCode='1503';
//config.loginName = '123';
//config.loginId = '11'
////config.moduleId = '402886f350a6bd4f0150a6c0c47c0000' ;
//config.moduleId = '402886f350a6bd4f0150a6c0c47c0000' ;
//config.moduleName = "消耗品管理系统"
//config.exportClass = "'发放出库','批量出库','退账入库'";
//config.reportServerIp = '192.168.6.68';
//config.reportServerPort = '8080';
//config.reportServerName = 'webReport';
//config.reportServerResousePath = 'ReportServer?reportlet=';
//config.defaultReportPath = "http://" + config.reportServerIp + ":" + config.reportServerPort + "/" + config.reportServerName + "/" + config.reportServerResousePath;