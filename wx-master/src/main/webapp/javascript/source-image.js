$(function () {
    /**
     * load datagrid data
     */
    var loadDict = function () {
        $.get("/api/source/list", function (data) {
            $("#image").datagrid('loadData', data);
        });
    }
    loadDict();

    var reset = function () {
        $("#imageId").textbox("setValue", "");
        $("#imageSize").textbox("setValue", "");
        $("#imageName").textbox("setValue", "");
        $("#imageLocalUrl").textbox("setValue", "");
        $("#imageWxUrl").textbox("setValue", "");
    }

    /**
     * init datagrid
     */
    $('#image').datagrid({
        idField: 'id',
        title: '图片素材列表',
        footer: '#tb',
        fit: true,
        fitColumns: true,
        singleSelect: true,
        striped: true,
        loadMsg: '数据正在加载,请耐心的等待...',
        remoteSort: true,
        columns: [
            [
                {
                    field: 'id',
                    title: '编号',
                    hidden: 'true'
                },
                {
                    field: 'imageName',
                    title: '图片名称'
                },
                {
                    field: 'image',
                    title: '图片'
                },
                {
                    field: 'imageLocalUrl',
                    title: '图片本地路径'
                },
                {
                    field: 'imageWxUrl',
                    title: '图片服务器端路径'

                },
                {
                    field: 'imageSize',
                    title: '图片大小'
                }
            ]
        ]
    });
    /**
     * button of add click
     */
    $("#addBtn").on('click', function () {
        $('#dlg').dialog('open').dialog('center').dialog('setTitle', '上传图片素材');
    });
    /**
     *  button of submit click
     */
    $("#submitBtn").on('click', function () {
        if ($("#fm").form('validate')) {
            var sourceImage = {};
            sourceImage.id = $("#imageId").val();
            sourceImage.imageName = $("#imageName").val();
            sourceImage.imageLocalUrl = $("#imageLocalUrl").val();
            sourceImage.imageWxUrl = $("#imageWxUrl").val();
            sourceImage.imageSize = $("#imageSize").val();
        }
        $.postJSON("/api/source/save?update=1", sourceImage, function (data) {
            $('#uploadDlg').dialog('close');
            $.messager.alert("系统提示", "操作成功！", "info");
            loadDict();
            $("#fm").get(0).reset();
        }, function (data, status) {
        })
    });
    /**
     * button of edit click
     */
    $("#editBtn").on('click', function () {
        var arr = $('#image').datagrid('getSelections');
        if (arr.length != 1) {
            $.messager.alert("系统警告", "请选择一条记录进行修改！", "error");
        }
        else {
            $('#uploadDlg').dialog({
                title: '修改图片信息'
            });
            loadSelectedRowData(arr);
        }
    });

    /**
     * load selectedRowData
     */
    var loadSelectedRowData = function (arr) {
        $('#fm').form('load', {
            id: arr[0].id,
            imageName: arr[0].imageName,
            imageSize: arr[0].imageSize,
            imageLocalUrl: arr[0].imageLocalUrl,
            imageWxUrl: arr[0].imageWxUrl
        });

    }
    /**
     * button of delete click
     */
    $("#delBtn").on('click', function () {
        var arr = $("#image").datagrid("getSelections");
        if (0 == arr.length) {
            $.messager.alert("系统警告", "请选择一条记录进行删除！", "error");
        } else {
            $.messager.confirm("系统提示", "确认要删除吗?", function (r) {
                if (r) {
                    var ids = '';
                    for (var i = 0; i < arr.length; i++) {
                        ids += arr[i].id + ",";
                    }
                    ids = ids.substring(0, ids.length - 1);
//                    $.post('/api/doct-info/delete?ids=' + ids, function (result) {
//                        loadDict();
//                        $('#t_user').datagrid('unselectAll');
//                        $.messager.alert("系统提示", "操作成功");
//                    })
                }
                else {
                    return;
                }
            });
        }
    });


    $("#uploadBtn").on('click', function () {
        var fileToUpload = document.getElementById("fileToUpload");
        var suffer = fileToUpload.value.substring(fileToUpload.value.indexOf(".") + 1);
        var fileSize=fileToUpload.files[0].size;
        alert(fileSize);
        if(fileSize>2*1024*1000){
            $.messager.alert("系统提示", "请选择小于2M的图片", "error");
            return;
        }

//        bmp/png/jpeg/jpg/gif
        if (suffer != "jpg" && suffer != "png" && suffer != "gif" && suffer != "jpeg" && suffer != "bmp") {
            $.messager.alert("系统提示", "请选择正确格式的图片", "error");
        } else {
            ajaxUpload();
        }
    });
    /**
     * ajax 上传
     * */




    function ajaxUpload() {

        $.ajaxFileUpload({
            url: '/img-upload-servlet',
            secureuri: false,
            fileElementId: 'fileToUpload',
            dataType: 'json',
            data: {username: $("#username").val()},
            success: function (data1, status) {
                var sourceImage = {};
                sourceImage.imageSize = data1.size;
                sourceImage.imageName = data1.imageName;
                sourceImage.imageLocalUrl = data1.picUrl;
                $.postJSON("/api/source/save?saveName=" + data1.saveName, sourceImage, function (data) {
                    $('#dlg').dialog('close');
                    $.messager.alert("系统提示", "操作成功！", "info");
                    loadDict();
                }, function (data, status) {
                })
            },
            error: function (data, status, e) {
                $.messager.alert('系统提示', '上传出错', 'error');
            }
        })
    }

    /**
     * 放大图片
     */
    $("#addBigBtn").on("click",function(){
        var selectedImages = $("#image").datagrid("getSelections");
        if(selectedImages.length!=1){
            $.messager.alert("系统提示","请选择一张图片进行查看","error");
            return;
        }
        $("#imageDiv").html(selectedImages[0].image);
        $("#imageDlg").dialog("setTitle","大图");
        $("#imageDlg").dialog("open");
    })
});

