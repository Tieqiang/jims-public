/**
 * Created by heren on 2015/9/14.
 */
$(function () {

    $("#dept").searchbox({
        searcher: function (value, name) {
            var children;
            var roots = $('#tt').treegrid('getRoots'); //得到tree顶级node
            for (var i = 0; i < roots.length; i++) { //循环顶级 node
                children = $('#tt').treegrid('getChildren', roots[i].target);//获取顶级node下所有子节点
                if (children) { //如果有子节点
                    for (var j = 0; j < children.length; j++) { //循环所有子节点
                        //console.log("num:"+j);
                        //console.log(children[j]);
                        if ($('#tt').treegrid('isLeaf', children[j].target)) { //判断子级是否为叶子节点,即不是父节点
                            if (children[j].deptName.indexOf(value) >= 0) { //判断节点text是否包含搜索文本
                                $('#tt').treegrid('select', children[j].target);//设置此节点为选择状态
                                break;
                            }
                        }
                    }
                } else {
                    if (roots[i].deptName.indexOf(value) >= 0) {
                        //alert("*" + roots[i].deptName);
                        $('#tt').treegrid('select', roots[i].target);//设置此节点为选择状态
                        break;
                    }
                }
            }
        }
    });
    //设置列
    $("#tt").treegrid({
        fit: true,
        idField: "id",
        treeField: "deptCode",
        //toolbar: '#tb',
        footer: '#tb',
        fitColumns: true,
        title: "科室维护",
        columns: [[{
            title: 'id',
            field: 'id',
            hidden: true
        },{
            title: 'imgUrl',
            field: 'imgUrl2',
            hidden: true
        }, {
            title: '科室图标',
            field: 'img',
            width: "10%"
        }, {
            title: '科室名称',
            field: 'deptName',
            width: "10%"
        }, {
            title: '科室别名',
            field: 'deptAlis',
            hidden: true
        }, {
            title: '科室临床属性',
            field: 'deptAttr',
            width: "10%"
        }, {
            title: '门诊住院',
            field: 'deptOutpInp',
            width: "10%",
            formatter: function(value,row,index){
                if (row.deptOutpInp == 1){
                    return '门诊';
                } else {
                    return '住院';
                }
            }
        }, {
            title: '输入码',
            field: 'inputCode',
            width: "10%"
        }, {
            title: '分科属性',
            field: 'deptDevideAttr',
            hidden: true
        }, {
            title: '科室位置',
            field: 'deptLocation',
            width: "10%"
        }, {
            title: '科室类别',
            field: 'deptClass',
            width: "10%",
            formatter: function(value,row,index){
                if(row.deptClass == 1){
                    return "经营科室";
                }else{
                    return "其他";
                }
            }
        }, {
            title: '科室类型',
            field: 'deptType',
            width: "10%",
            formatter: function(value,row,index){
                if (row.deptType == 1){
                    return "医疗技术类科室";
                } else if(row.deptType == 2){
                    return "医疗辅助类科室";
                }else{
                    return "管理类科室";
                }
            }
        }, {
            title: '是否停用',
            field: 'deptStopFlag',
            width: "10%",
            formatter: function(value,row,index){
                if (row.deptStopFlag == 1){
                    return "否";
                } else {
                    return "是";
                }
            }
        }, {
            title: '是否末级科室',
            field: 'endDept',
            hidden: true,
            formatter: function(value,row,index){
                if (row.endDept == 1){
                    return "是";
                } else {
                    return "否";
                }
            }
        }, {
            title: '科室简介',
            field: 'deptInfo',
            width: "10%"
        }]]
    });

    /**
     * 加载科室信息表
     */
    var loadDept = function () {
        var depts = [];
        var treeDepts = [];
        var loadPromise = $.get("/api/dept-dict/list", function (data) {
            //$("#tt").treegrid('loadData',data);
            $.each(data, function (index, item) {
//                console.info(item);
                var obj = {};
                obj.deptCode = item.deptCode;
                obj.id = item.id;
                obj.deptName = item.deptName;
                obj.deptAlis = item.deptAlis;
                obj.deptAttr = item.deptAttr;
                obj.deptOutpInp = item.deptOutpInp;
                obj.inputCode = item.inputCode;
                obj.deptDevideAttr = item.deptDevideAttr;
                obj.deptLocation = item.deptLocation;
                obj.deptStopFlag = item.deptStopFlag;
                obj.parentId = item.parentId;
                obj.deptType = item.deptType;
                obj.deptClass = item.deptClass;
                obj.endDept = item.endDept;
                obj.deptInfo = item.deptInfo;
                obj.img = item.img;
                obj.imgUrl2=item.imgUrl2;
                obj.children = [];
//                obj.
                depts.push(obj);
            });
        });

        loadPromise.done(function () {
            for (var i = 0; i < depts.length; i++) {
                for (var j = 0; j < depts.length; j++) {
                    if (depts[i].id == depts[j].parentId) {
                        depts[i].children.push(depts[j]);
                    }
                }
                if (depts[i].children.length > 0 && !depts[i].parentId) {
                    treeDepts.push(depts[i]);
                }

                if (!depts[i].parentId && depts[i].children <= 0) {
                    treeDepts.push(depts[i])
                }
            }

            $("#tt").treegrid('loadData', treeDepts);
        })
    }

    loadDept();

    $("#searchBtn").on("click", function () {
        var name = $("#name").textbox("getValue");
        var rows = $("#tt").treegrid('getData');
        console.log(rows);
        $.each(rows, function (index, item) {
            console.log(item.deptName);

            if (item.deptName == name) {
                $("#tt").treegrid('select', item.id);
            }
        });
    });

    $("#deptStopFlag").combobox({       //科室是否停用
        panelHeight: 'auto',
        valueField: 'baseCode',
        textField: 'baseName',
        url: "/api/base-dict/list-by-type?baseType=STOP_FLAG",
        mode: 'remote',
        method: 'GET'
    })

    $("#deptOutpInp").combobox({        //门诊或住院
        panelHeight: 'auto',
        valueField: 'baseCode',
        textField: 'baseName',
        url: "/api/base-dict/list-by-type?baseType=DEPT_OUTP_INP_FLAG",
        mode: 'remote',
        method: 'GET'
    });

    $("#endDept").combobox({        //是否末级科室
        panelHeight: 'auto',
        valueField: 'baseCode',
        textField: 'baseName',
        url: "/api/base-dict/list-by-type?baseType=END_DEPT_FLAG",
        mode: 'remote',
        method: 'GET'
    });

    $("#deptType").combobox({       //科室部门类型
        panelHeight: 'auto',
        valueField: 'baseCode',
        textField: 'baseName',
        url: "/api/base-dict/list-by-type?baseType=DEPT_TYPE_FLAG",
        mode: 'remote',
        method: 'GET'
    });

    $("#deptClass").combobox({      //科室部门类别
        panelHeight: 'auto',
        valueField: 'baseCode',
        textField: 'baseName',
        url: "/api/base-dict/list-by-type?baseType=DEPT_CLASS_FLAG",
        mode: 'remote',
        method: 'GET'
    });

    /**
     * 添加科室
     */
    $("#addBtn").on('click', function () {
        clearInput();
        $("#dlg").dialog("open").dialog("setTitle", "添加科室");
    });

    /**
     * 添加子科室
     */
    $("#addChildBtn").on('click', function () {
        clearInput();
        var node = $("#tt").treegrid('getSelected');
        if (!node) {
            $.messager.alert("系统提示", "请先选择上级科室，然后在添加子科室");
            return;
        }
        $("#dlg").dialog("open").dialog("setTitle", "添加科室");
        $("#parentId").textbox('setValue', node.id);

    });

    /**
     * 修改科室信息
     *
     */
    $("#editBtn").on('click', function () {
        var node = $("#tt").treegrid("getSelected");
        if (!node) {
            $.messager.alert("系统提示", "请选择要修改的科室");
            return;
        }
        $("#id").val(node.id);
        $("#deptCode").textbox('setValue', node.deptCode);
        $("#deptName").textbox('setValue', node.deptName);
        $("#deptAlis").textbox('setValue', node.deptAlis);
        $("#deptAttr").textbox('setValue', node.deptAttr);
        $("#deptOutpInp").combobox('setValue', node.deptOutpInp);
        $("#deptDevideAttr").textbox('setValue', node.deptDevideAttr);
        $("#deptLocation").textbox('setValue', node.deptLocation);
        $("#deptStopFlag").combobox('setValue', node.deptStopFlag);
        $("#endDept").combobox('setValue', node.endDept);
        $("#deptType").combobox('setValue', node.deptType);
        $("#deptClass").combobox('setValue', node.deptClass);
        $("#parentId").textbox('setValue', node.parentId);
//        alert(node.imgUrl2);
//        console.info(node);
        $("#headUrl").attr("value",node.imgUrl2);
//        $("#deptInfo").val(node.deptInfo);
        var oEditor = CKEDITOR.instances.deptInfo;
         oEditor.setData(node.deptInfo);
        $("#dlg").dialog('open').dialog('setTitle', "修改科室");
     });

    /**
     * 保存信息
     */
    $("#saveBtn").on('click', function () {
        var deptDict = {};
//        deptDict.hospitalDict = {};
        deptDict.id = $("#id").val();
        deptDict.deptCode = $("#deptCode").textbox('getValue');
        deptDict.deptName = $("#deptName").textbox('getValue');
        deptDict.deptAlis = $("#deptAlis").textbox('getValue');
        deptDict.deptAttr = $("#deptAttr").textbox('getValue');
        deptDict.deptOutpInp = $("#deptOutpInp").combobox('getValue');
        //deptDict.deptDevideAttr = $("#deptDevideAttr").textbox('getValue');
        deptDict.deptLocation = $("#deptLocation").textbox('getValue');
        deptDict.deptStopFlag = $("#deptStopFlag").combobox('getValue');
        deptDict.parentId = $("#parentId").textbox('getValue');
        deptDict.deptClass = $("#deptClass").combobox('getValue');
        deptDict.deptType = $("#deptType").combobox('getValue');
        deptDict.endDept = $("#endDept").combobox('getValue');
        deptDict.imgUrl=$("#headUrl").val();
//        alert($("#headUrl").val());
        var oEditor = CKEDITOR.instances.deptInfo;
        deptDict.deptInfo = oEditor.getData();

        //alert(oEditor.getData());

//        deptDict.hospitalDict.id = parent.config.hospitalId;

        if ($("#fm").form('validate')) {
            $.postJSON("/api/dept-dict/add", deptDict, function (data) {
                $.messager.alert("系统提示", "保存成功");
                loadDept();
                clearInput();
                $("#dlg").dialog('close');
            }, function (data) {
                $.messager.alert("系统提示", "保存失败");
            })
        }

    });

    /**
     * 清除输入框信息
     */
    var clearInput = function () {
        $("#id").val("")

        $("#deptCode").textbox('setValue', "");
        $("#deptName").textbox('setValue', "");
        $("#deptAlis").textbox('setValue', "");
        $("#deptAttr").textbox('setValue', "");
        $("#deptOutpInp").combobox('setValue', "");
        //$("#deptDevideAttr").textbox('setValue',"") ;
        $("#deptLocation").textbox('setValue', "");
        $("#deptStopFlag").combobox('setValue', "");
        $("#parentId").textbox('setValue', "");
//        <img src="" id="viewImg"/>
        $("#viewImg").attr("src","");
        $("#headUrl").val("");
        var oEditor = CKEDITOR.instances.deptInfo;
        oEditor.setData("");
    }
    /**
     * 删除
     */
    $("#delBtn").on('click', function () {
        var node = $("#tt").treegrid("getSelected");
        if (!node) {
            $.messager.alert("系统提示", "请选择要删除的科室");
            return;
        }

        if ($("#tt").treegrid("getChildren", node.id).length > 0) {
            $.messager.alert("系统提示", "请先删除子科室，在删除");
            return;
        }


        $.messager.confirm("系统提示", "确定要删除【" + node.deptName + "】吗？", function (r) {
            if (r) {
                $.delete("/api/dept-dict/del/" + node.id, function (data) {
                    $.messager.alert("系统提示", "删除成功");
                    loadDept();
                })
            }
        });

    });
    $("#uploadBtn").on('click',function(){
        var fileToUpload=document.getElementById("fileToUpload");
        var suffer=fileToUpload.value.substring(fileToUpload.value.indexOf(".")+1);
        if(suffer!="jpg"&&suffer!="png"&&suffer!="gif"&&suffer!="jpeg"&&suffer!="bmp"&&suffer!="swf"){
            $.messager.alert("系统提示", "请选择正确格式的图片","error");
        }else{
            ajaxUpload();
        }
    });
    /**
     * ajax 上传
     * */
    var ajaxUpload=function ajaxUpload(){
        $.ajaxFileUpload({
            url : '/img-upload-servlet',
            secureuri : false,
            fileElementId : 'fileToUpload',
            dataType : 'json',
            data : {username : $("#username").val()},
            success: function(data, status) {
                $('#uploadSpan').append("<span><font color='red'> 上传成功 ✔</font></span>");
//                 $('#headUrl').val("");
//                alert(data.picUrl);
                $('#headUrl').attr("value",data.picUrl);
                $("#viewImg").attr("src",data.picUrl);
                //                 alert($('#headUrl').attr('value'));
            },
            error : function(data, status, e) {
                $.messager.alert('系统提示','上传出错','error');
            }
        })
    }
});