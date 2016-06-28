/**
 * Created by admin on 2016/6/24.
 */
$(function(){
     var title = UE.getEditor('title');
     var author = UE.getEditor('author');
     var content=UE.getEditor('content');
     var digest=UE.getEditor('digest');

    /**
     * 保存图文消息
     */
    $("#save").on("click",function(){
        var title = UE.getEditor('title');
        var author = UE.getEditor('author');
        var content=UE.getEditor('content');
        var digest=UE.getEditor('digest');

        var sourceImageFont={};
        sourceImageFont.title = title.getContent();
        alert(title.getContent());
         sourceImageFont.author = author.getContent();
        sourceImageFont.digest=digest.getContent();
        sourceImageFont.thumbMediaId=$("#mediaId").val();
        sourceImageFont.contentSourceUrl=$("#contentSourceUrl").val();
        var content=content.getContent();//byte[]
        if(sourceImageFont.thumbMediaId==null || sourceImageFont.thumbMediaId==""){
            $.messager.alert("系统提示","请选择图片","error");
            return;
        }
        if(sourceImageFont.digest==null || sourceImageFont.digest==""){
            sourceImageFont.digest=content.substring("0","47");
        }
        $.postJSON("/api/source/save-image-font?content="+content,sourceImageFont, function (data) {
            $.messager.alert("系统提示", "保存成功", "info");
            $('#imageList').datagrid('unselectAll');
        }, function (data, status) {
            $.messager.alert("系统提示",data.errorMessage, "error");
        })

    });
    /**
     * 加入图片操作
     */
    $("#add").on("click",function(){
        if($("#mediaId").val()!=null&&$("#mediaId").val()!=""){
            $.messager.alert("系统提示","已经存在封面了","error");
            return;
        }
        var selectData=$("#imageList").datagrid("getSelections");
        if(selectData.length!=1){
            $.messager.alert("系统提示","请选择要添加的图片","error");
            return ;
        }
        $("#viewImage").append(selectData[0].image);
        $("#viewImage").show();
        $("#mediaId").val(selectData[0].mediaId);
    })
    /**
     * 移除所选封面
     */
    $('#remove').on("click",function(){
        $("#viewImage").hide();
        $("#viewImage").html("");
        $("#mediaId").val("");
    })

    var loadImage=$.get("/api/source/load-image", function (data) {
        $("#imageList").datagrid('loadData', data);
    });

    $("#imageList").datagrid({
        idField: "id",
        title: '图片库',
        singleSelect:true,
        footer:'#foot',
        fit: true,
        columns: [
            [
                {
                    title: '编号',
                    field: 'id',
                    hidden: true
                },
                {
                    title: 'mediaId',
                    field: 'mediaId',
                    hidden: true
                },
                {
                    title: 'imageLocalUrl',
                    field: 'imageLocalUrl',
                    hidden: true
                },
                {
                    title: 'image',
                    field: 'image'

                }
             ]
        ]
    });




})

