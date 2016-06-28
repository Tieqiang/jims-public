$(function () {
    /**
     * load datagrid data
     */
    var loadDict = function () {
        $.get("/api/source/list-video", function (data) {
            $("#video").datagrid('loadData', data);
        });
    }
    loadDict();
    var reset = function () {
        $("#videoId").textbox("setValue", "");
        $("#title").textbox("setValue", "");
        $("#label").textbox("setValue", "");
        $("#videoLocalUrl").textbox("setValue", "");
        $("#videoWxUrl").textbox("setValue", "");
        $("#description").textbox("setValue", "");
    }

    /**
     * <embed src="URL" widht=播放显示宽度 height=播放显示高度 autostart=true/false loop=true/false></embed>
     * init datagrid
     */
    $('#video').datagrid({
        idField: 'id',
        title: '视频素材列表',
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
                    field: 'title',
                    title: '视频标题'
                },
                {
                    field: 'label',
                    title: '标签'
                },
                {
                    field: 'videoLocalUrl',
                    title: '图片本地路径'
                },
                {
                    field: 'videoWxUrl',
                    title: '图片服务器端路径'

                },
                {
                    field: 'videoSize',
                    title: '视频大小'
                },
                {
                    field: 'video',
                    title: '视频'
                }
            ]
        ]
    });
    /**
     * button of add click
     */
    $("#addBtn").on('click', function () {
        $('#uploadDlg').dialog('open').dialog('center').dialog('setTitle', '上传视频素材');
    });
    /**
     *  button of submit click
     */
    $("#submitBtn").on('click', function () {
        var fileToUpload = document.getElementById("fileToUpload");
        var fileSize =  fileToUpload.files[0].size;//文件的大小，单位为字节B 27246843
        if(fileSize>10*1024*1000){//10M
            $.messager.alert("系统提示", "请选择10M以下的视屏", "error");
            return;
        }
        var suffer = fileToUpload.value.substring(fileToUpload.value.indexOf(".") + 1);
        if (suffer!= "MP4"&&suffer!="mp4") {
            $.messager.alert("系统提示", "请选择正确格式的视屏", "error");
        } else {
            if ($("#fm").form('validate')) {
                var sourceVideo = {};
                sourceVideo.id = $("#imageId").val();
                sourceVideo.title = $("#title").val();
                sourceVideo.videoLocalUrl = $("#videoLocalUrl").val();
                sourceVideo.videoWxUrl = $("#videoWxUrl").val();
                sourceVideo.videoSize = $("#videoSize").val();
//            sourceVideo.description=$("#description").val();
                sourceVideo.label=$("#label").val();
            }
            if($("#imageId").val()==null || $("#imageId").val()==""){//新增
                ajaxUpload(sourceVideo,$("#description").val());
            }else{
                $.postJSON("/api/source/save?update=1", sourceVideo, function (data) {
                    $('#uploadDlg').dialog('close');
                    $.messager.alert("系统提示", "操作成功！", "info");
                    loadDict();
                    $("#fm").get(0).reset();
                }, function (data, status) {
                    $.messager.alert("系统提示",data.errorMessage,"error");
                })
            }
        }
     });
    /**
     * button of edit click
     */
    $("#editBtn").on('click', function () {
        var arr = $('#video').datagrid('getSelections');
        if (arr.length != 1) {
            $.messager.alert("系统警告", "请选择一条记录进行修改！", "error");
        }
        else {
            $('#uploadDlg').dialog({
                title: '修改视频信息'
            });
            $("#uploadDlg").dialog('open');
            loadSelectedRowData(arr);
        }
    });

    /**
     * load selectedRowData
     */
    var loadSelectedRowData = function (arr) {
        $('#fm').form('load', {
            id: arr[0].id,
            title: arr[0].title,
            videoSize: arr[0].videoSize,
            videoLocalUrl: arr[0].videoLocalUrl,
            videoWxUrl: arr[0].videoWxUrl,
            label:arr[0].label,
            description:arr[0].description
         });

    }
    /**
     * button of delete click
     */
    $("#delBtn").on('click', function () {
        var arr = $("#video").datagrid("getSelections");
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

//
//    $("#uploadBtn").on('click', function () {
//
//    });
    /**
     * ajax 上传
     * */



    function ajaxUpload(sourceVideo,videoDescription) {
         $.ajaxFileUpload({
            url: '/img-upload-servlet',
            secureuri: false,
            fileElementId: 'fileToUpload',
            dataType: 'json',
            data: {username: $("#username").val()},
            success: function (data1, status) {
                sourceVideo.videoSize = data1.size;
                sourceVideo.videoLocalUrl = data1.picUrl;
                $.postJSON("/api/source/save-video?saveName=" + data1.saveName+"&videoDescription="+videoDescription, sourceVideo, function (data) {
                    $('#uploadDlg').dialog('close');
                    $.messager.alert("系统提示", "操作成功！", "info");
                    loadDict();
                }, function (data, status) {
                    $.messager.alert("系统提示",data.errorMessage,"error");
                })
            },
            error: function (data, status, e) {
                $.messager.alert('系统提示', '上传出错', 'error');
            }
        })
    }
});

