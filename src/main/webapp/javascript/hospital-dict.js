/**
 * Created by heren on 2015/9/14.
 */
$(function () {
    //设置列
    $("#tt").treegrid({
        fit: true,
        idField: "id",
        treeField: "hospitalName",
        //toolbar: '#tb',
        footer: '#tb',
        title: '系统医院维护',
        columns: [[{
            title: '医院名称',
            field: 'hospitalName',
            width: '25%'
        }, {
            title: '所在区域',
            field: 'unitCode',
            width: '25%'
        }, {
            title: '详细地址',
            field: 'location',
            width: '25%'
        }, {
            title: '邮政编码',
            field: 'zipCode',
            width: '25%'
        }]]
    });


    /**
     * 加载医院信息表
     */
    var loadHospital = function () {

        var hosps = [];
        var treeHosps = [];
        var loadPromise = $.get("/api/hospital-dict/list", function (data) {
            //$("#tt").treegrid('loadData',data);
            var temp = data;
            $.each(data, function (index, item) {
                var obj = {
                    id: item.id,
                    hospitalName: item.hospitalName,
                    zipCode: item.zipCode,
                    unitCode: item.unitCode,
                    location: item.location,
                    parentHospital: item.parentHospital,
                    children: []
                };

                hosps.push(obj);
            });

            for (var i = 0; i < hosps.length; i++) {
                for (var j = 0; j < hosps.length; j++) {
                    if (hosps[i].id == hosps[j].parentHospital) {
                        hosps[i].children.push(hosps[j]);
                    }
                }
                if (hosps[i].children.length > 0 && !hosps[i].parentHospital) {
                    treeHosps.push(hosps[i]);
                }

                if (!hosps[i].parentHospital && hosps[i].children <= 0) {
                    treeHosps.push(hosps[i])
                }
            }
        });

        loadPromise.done(function () {
            $("#tt").treegrid('loadData', treeHosps);
        })
    }

    loadHospital();

    /**
     * 添加医院
     */
    $("#addBtn").on('click', function () {

        $("#dlg").dialog("open").dialog("setTitle", "添加医院");

    })


    /**
     * 添加分院
     */

    $("#addChildBtn").on('click', function () {

        var node = $("#tt").treegrid('getSelected');
        if (!node) {
            $.messager.alert("系统提示", "请选择总院，然后在添加分院");
            return;
        }
        $("#dlg").dialog("open").dialog("setTitle", "添加分院");
        $("#parentHospital").textbox('setValue', node.id);

    });


    /**
     * 修改医院信息
     *
     */
    $("#editBtn").on('click', function () {
        var node = $("#tt").treegrid("getSelected");
        if (!node) {
            $.messager.alert("系统提示", "请选择要修改的医院");
            return;
        }
        $("#id").val(node.id);
        $("#hospitalName").textbox('setValue', node.hospitalName);
        $("#unitCode").textbox('setValue', node.unitCode);
        $("#location").textbox('setValue', node.location);
        $("#zipCode").textbox('setValue', node.zipCode);
        $("#organizationFullCode").textbox('setValue', node.organizationFullCode);
        $("#parentHospital").textbox('setValue', node.parentHospital);
        $("#dlg").dialog('open').dialog('setTitle', "修改医院信息");
    });

    /**
     * 保存信息
     */
    $("#saveBtn").on('click', function () {
        var hospitalDict = {};
        hospitalDict.id = $("#id").val();
        hospitalDict.hospitalName = $("#hospitalName").textbox('getValue');
        hospitalDict.unitCode = $("#unitCode").textbox('getValue');
        hospitalDict.location = $("#location").textbox('getValue');
        hospitalDict.zipCode = $("#zipCode").textbox('getValue');
        hospitalDict.organizationFullCode = $("#organizationFullCode").textbox('getValue');
        hospitalDict.parentHospital = $("#parentHospital").textbox('getValue');

        var postcode = hospitalDict.zipCode;
        if (postcode != "") {   //验证邮编
            var pattern = /^[0-9]{6}$/;
            flag = pattern.test(postcode);
            if (!flag) {
                alert("邮编非法！！")
                document.getElementById("zipCode").focus();
            }else if ($("#fm").form('validate')) {      //邮编验证通过
                $.postJSON("/api/hospital-dict/add", hospitalDict, function (data) {
                    $.messager.alert("系统提示", "保存成功");
                    loadHospital();
                    $("#id").val("");
                    $("#hospitalName").textbox('setValue', "");
                    $("#unitCode").textbox('setValue', "");
                    $("#location").textbox('setValue', "");
                    $("#zipCode").textbox('setValue', "");
                    $("#organizationFullCode").textbox('setValue', "");
                    $("#parentHospital").textbox('setValue', "");
                    $("#dlg").dialog('close');
                }, function (data) {
                    $.messager.alert("系统提示", "保存失败");
                })
            }
        }
    });

    /**
     * 删除
     */
    $("#delBtn").on('click', function () {
        var node = $("#tt").treegrid("getSelected");
        if (!node) {
            $.messager.alert("系统提示", "请选择要删除的医院");
            return;
        }

        if ($("#tt").treegrid("getChildren", node.id).length > 0) {
            $.messager.alert("系统提示", "请先删除分院，在删除总院");
            return;
        }


        $.messager.confirm("系统提示", "确定要删除【" + node.hospitalName + "】吗？", function (r) {
            if (r) {
                $.delete("/api/hospital-dict/delete/" + node.id, function (data) {
                    $.messager.alert("系统提示", "删除成功");
                    loadHospital();
                })
            }
        })

    });
});