/**
 * created by chenxiaoyang on 2016.03.29
 */
$(function () {
    var flag;

    /**
     * load datagrid data
     */
    var loadDict = function () {
        $.get("/api/feed-back/feed-back-result", function (data) {
            $("#t_user").datagrid('loadData', data);
        });
    }


    loadDict();



    /**
     * init datagrid
     */
    $('#t_user').datagrid({
        idField: 'id',
        title: '反馈结果维护列表',
        footer: '#tb',
        fit: true,
        fitColumns: true,
        singleSelect : false,
        striped: true,
        loadMsg: '数据正在加载,请耐心的等待...',
        remoteSort: true,
        columns: [[{
            field: 'id',
            title: '编号',
            hidden:'true'
        } ,{
            field: 'patName',
            title: '患者姓名',
            width: "20%"

        }, {
            field: 'feedTime',
            title: '反馈时间',
            width: "30%"

        },{
            field: 'feedBackTargetName',
            title: '反馈对象',
            width: "20%"

        },{
            field: 'feedBackContent',
            title: '反馈内容',
            width: "30%"

        }]]
    });
     /**
     * button of delete click
     */
    $("#delBtn").on('click', function () {
        var arr = $("#t_user").datagrid("getSelections");
        if (0 == arr.length){
            $.messager.alert("系统警告", "请选择一条记录进行删除！","error");
        } else {
            $.messager.confirm("系统提示", "确认要删除吗?", function (r) {
                if (r) {
                    var ids = '';
                    for (var i = 0; i < arr.length; i++) {
                        ids += arr[i].id + ",";
                    }
                    ids = ids.substring(0, ids.length - 1);
                    $.post('/api/feed-back/delete-result?ids=' + ids, function (result) {
                        loadDict();
                        $('#t_user').datagrid('unselectAll');
                        $.messager.alert("系统提示", "操作成功");
                    })
                }
                else {
                    return;
                }
            });
        }
    });
});

