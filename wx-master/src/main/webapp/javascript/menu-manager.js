/**
 * Created by heren on 2015/9/11.
 */

$(function () {

    $("#tt").treegrid({
        idField: 'id',
        treeField: 'menuName',
        title: '系统菜单维护',
        fit: true,
        toolbar: '#tb',
        singleSelect:true,
        columns: [[{
            title: '菜单名称',
            field: 'menuName',
            width: "20%"
        }, {
            title: '路径',
            field: 'href',
            width: "40%"
        }, {
            title: 'id',
            field: 'id',
            width: "20%",
            hidden:true
        }, {
            title: '层级',
            field: 'position',
            width: "10%"
        }]],
        onLoadSuccess:function(row, data){
            //启用拖动
            enableDnd($('#tt'));
        }
    });

    function enableDnd(t) {
        var nodes = t.treegrid('getPanel').find('tr[node-id]');
        nodes.find('span.tree-hit').bind('mousedown.treegrid', function () {
            return false;
        });
        nodes.draggable({
            disabled: false,
            revert: true,
            cursor: 'pointer',
            proxy: function (source) {
                var p = $('<div class="tree-node-proxy tree-dnd-no"></div>').appendTo('body');
                p.html($(source).find('.tree-title').html());
                p.hide();
                return p;
            },
            deltaX: 15,
            deltaY: 15,
            onBeforeDrag: function () {
                $(this).next('tr.treegrid-tr-tree').find('tr[node-id]').droppable({accept: 'no-accept'});
            },
            onStartDrag: function () {
                $(this).draggable('proxy').css({
                    left: -10000,
                    top: -10000
                });
            },
            onDrag: function (e) {
                $(this).draggable('proxy').show();
                this.pageY = e.pageY;
            },
            onStopDrag: function () {
                $(this).next('tr.treegrid-tr-tree').find('tr[node-id]').droppable({accept: 'tr[node-id]'});
            }
        }).droppable({
            accept: 'tr[node-id]',
            onDragOver: function (e, source) {
                var pageY = source.pageY;
                var top = $(this).offset().top;
                var bottom = top + $(this).outerHeight();
                $(source).draggable('proxy').removeClass('tree-dnd-no').addClass('tree-dnd-yes');
                $(this).removeClass('row-append row-top row-bottom');
                if (pageY > top + (bottom - top) / 2) {
                    if (bottom - pageY < 5) {
                        $(this).addClass('row-bottom');
                    } else {
                        $(this).addClass('row-append');
                    }
                } else {
                    if (pageY - top < 5) {
                        $(this).addClass('row-top');
                    } else {
                        $(this).addClass('row-append');
                    }
                }
            },
            onDragLeave: function (e, source) {
                $(source).draggable('proxy').removeClass('tree-dnd-yes').addClass('tree-dnd-no');
                $(this).removeClass('row-append row-top row-bottom');
            },
            onDrop: function (e, source) {

                var src = $('#tt').treegrid('find', $(source).attr('node-id'));
                var des = $('#tt').treegrid('find', $(this).attr('node-id'));
                if(des.parentId==null){
                    $.messager.confirm("系统提示", "确认将当前目录设置成顶级目录吗?", function (r) {
                        if (r) {
                            src.parentId = "";
                            $("#tt").treegrid('remove',src.id);
                            $("#tt").treegrid('insert', {
                                before: des.id,
                                data: makeArray(src)
                            });
                            //$('#tt').treegrid('reload');
                        } else {
                            src.parentId = des.id;
                            $("#tt").treegrid('remove', src.id);
                            $("#tt").treegrid('append', {
                                parent: des.id,
                                data: makeArray(src)
                            });
                            //$('#tt').treegrid('reload');
                        }


                        var menuDict = {};
                        menuDict.menuName = src.menuName;
                        menuDict.href = src.href;
                        menuDict.parentId = src.parentId;
                        menuDict.position = src.position;
                        menuDict.id = src.id;
                        $.postJSON("/api/menu/add", menuDict, function (data) {
                            //loadMenu();
                        }, function (data, status) {
                        })
                    })
                }else if(des.parentId != null){
                    src.parentId = des.id;
                    $("#tt").treegrid('remove', src.id);
                    $("#tt").treegrid('append', {
                        parent: des.id,
                        data: makeArray(src)

                    });
                    //$('#tt').treegrid('reload');
                    var menuDict = {};
                    menuDict.menuName = src.menuName;
                    menuDict.href = src.href;
                    menuDict.parentId = src.parentId;
                    menuDict.position = src.position;
                    menuDict.id = src.id;
                    $.postJSON("/api/menu/add", menuDict, function (data) {
                        //loadMenu();
                    }, function (data, status) {
                    })
                }
            }
        });
    }

    var makeArray = function (array) {
        var ret = [];
        if (array != null) {
            var i = array.length;
// The window, strings (and functions) also have 'length'
            if (i == null || typeof array === "string" || $.isFunction(array) || array.setInterval)
                ret[0] = array;
            else
                while (i)
                    ret[--i] = array[i];
        }
        return ret;
    }
    var loadMenu = function () {
        var menus = [];//菜单列表
        var menuTreeData = [];//菜单树的列表
        var menuPromise = $.get("/api/menu/list", function (data) {
            $.each(data,function(index,item){
                var menu ={} ;
                menu.id = item.id ;
                menu.menuName = item.menuName ;
                menu.href = item.href ;
                menu.position = item.position;
                menu.parentId = item.parentId ;
                menu.children=[] ;
                menus.push(menu) ;
            });

            for(var i = 0 ;i<menus.length;i++){
                for(var j = 0 ;j<menus.length;j++){
                    if(menus[i].id ==menus[j].parentId){
                        menus[i].state='closed'
                        menus[i].children.push(menus[j]) ;
                    }
                }

                if(menus[i].children.length>0 && !menus[i].parentId){
                    menus[i].state='open'
                    menuTreeData.push(menus[i]) ;
                }

                if(!menus[i].parentId&&menus[i].children.length<=0){
                    menuTreeData.push(menus[i]) ;
                }
            }
        });

        menuPromise.done(function () {
            $("#tt").treegrid('loadData',menuTreeData) ;
            $("#tt").treegrid("selectRow", 1);
        });
    }


    loadMenu();


    $("#allMenuClose").on('click', function () {
        var rows= $("#tt").treegrid('getRoots');
        for(var i=0;i<rows.length;i++){
            $("#tt").treegrid('collapseAll', rows[i].id);
        }

    })
    $("#allMenuOpen").on('click', function () {
        var rows = $("#tt").treegrid('getRoots');
        for (var i = 0; i < rows.length; i++) {
            $("#tt").treegrid('expandAll', rows[i].id);
        }
    })
    $("#saveBtn").on('click', function () {
        if ($("#fm").form('validate')) {
            var menuDict = {};
            menuDict.menuName = $("#menuName").textbox('getValue');
            menuDict.href = $("#href").textbox('getValue');
            menuDict.parentId = $("#parentId").textbox('getValue');
            menuDict.position = $("#position").textbox('getValue');
            menuDict.id = $("#id").val();

            $.postJSON("/api/menu/add", menuDict, function (data) {
                $('#dlg').dialog('close');

                if(!menuDict.id){
                    $("#tt").treegrid('append',{
                        parent:menuDict.parentId,
                        data:[data]
                    })
                }else{
                    $("#tt").treegrid('update',{
                        id:menuDict.id,
                        row:data
                    })
                }

            }, function (data, status) {
            })
        }

    })

    $("#addSameLevelBtn").on('click', function () {
        var node = $("#tt").treegrid('getSelected')
        if (!node) {
            $.messager.alert("系统提示", "请选择，所添加菜单的同一级的任意一个菜单");
            return;
        }
        if (node) {
            $('#dlg').dialog('open').dialog('center').dialog('setTitle', '添加同级菜单');
            $('#fm').form('clear');
            $("#parentName").textbox('setValue', node.menuName);
            $("#parentId").textbox('setValue', node._parentId);
            $("#position").textbox('setValue', node.position);
        }
    });

    /**
     * 添加下级菜单
     */
    $("#addNextLevelBtn").on('click', function () {
        var node = $("#tt").treegrid('getSelected')
        if (!node) {
            $.messager.alert("系统提示", "请选择，所添加菜单的同一级的任意一个菜单");
            return;
        }

        if(!node.id){
            $.messager.alert("系统提示",'所选菜单为新添加菜单，请刷新后，添加子菜单','info') ;
            return ;
        }
        if (node) {
            $('#dlg').dialog('open').dialog('center').dialog('setTitle', '添加子菜单');
            $('#fm').form('clear');
            $("#parentName").textbox('setValue', node.menuName);
            $("#parentId").textbox('setValue', node.id);
            $("#position").textbox('setValue', node.position);
        }
    })

    /**
     * 删除某一个菜单
     */
    $("#removeBtn").on('click', function () {
        var row = $("#tt").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("系统提示", "请选择要删除的菜单");
            return;
        }



        var children = $("#tt").treegrid('getChildren', row.id);
        if (children.length > 0) {
            $.messager.alert("系统提示", "请先删除子菜单");
            return;
        } else {
            if(!row.id){
                $.messager.alert("系统提示","对于新添加的菜单请先刷新在进行删除",'error') ;
                return
            }
            $.messager.confirm("系统提示", "确认删除:【" + row.menuName + "】的菜单吗?", function (r) {
                if (r) {
                    $.post('/api/menu/del/' + row.id, function (data) {
                        $.messager.alert("系统提示", "删除成功");
                        $("#tt").treegrid('remove', row.id);
                    });
                }
            })

        }
    })

    /**
     * 修改一个菜单
     */
    $("#updateBtn").on('click', function () {

        var node = $("#tt").treegrid('getSelected');
        if (node == null) {
            $.messager.alert("系统提示", "请选中要修改的菜单");
            return;
        }
        $('#dlg').dialog('open').dialog('center').dialog('setTitle', '修改菜单');
        //$('#fm').form('clear');
        $("#menuName").textbox('setValue', node.menuName);
        $("#href").textbox('setValue', node.href);
        $("#parentName").textbox('setValue', node.menuName);
        $("#parentId").textbox('setValue', node._parentId);
        $("#position").textbox('setValue', node.position);
        $("#id").val(node.id);

    });

})