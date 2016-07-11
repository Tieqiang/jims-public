/**
 * Created by admin on 2016/6/24.
 */
$(function(){
    var ue = UE.getEditor('editor1');
    ue.ready(function(){
        //


//        $(this.container).click(function(e){
//            e.stopPropagation();
//        })
    });
//    $('.content').click(function(e){
//        var $target = $(this);
//        var content = $target.html();
//
//        var currentParnet = ue.container.parentNode.parentNode;
//        var currentContent = ue.getContent();
//        $target.html('');
//        $target.append(ue.container.parentNode);
//        ue.reset();
//        setTimeout(function(){
//            ue.setContent(content);
//        },200)
//        $(currentParnet).html(currentContent);
//      })
    /**
     * 可以编辑
     */
    $("#enable").on("click",function(){
        var content = ue.getContent();
        alert(content);
        if(content.indexOf("正文")!=-1){//包含正文则启用编辑器
             ue.setEnabled();
            return;
        }
        $.messager.alert("系统提示","不是正文不允许使用编辑器","error");
     })
    /**
     * 保存图文消息
     */
    $("#save").on("click",function(){
        var sourceImageFont={};
        sourceImageFont.thumbMediaId=$("#mediaId").val();
        sourceImageFont.contentSourceUrl=$("#contentSourceUrl").val();
        var content=ue.getContent();//byte[]
        if(sourceImageFont.thumbMediaId==null || sourceImageFont.thumbMediaId==""){
            $.messager.alert("系统提示","请选择图片","error");
            return;
        }
        sourceImageFont.title=document.getElementById("title").value;
//        alert(title.value);
//        sourceImageFont.title=$("#title").text();
        sourceImageFont.author=document.getElementById("oauth").value;
        sourceImageFont.digest=$("#digest").text();
//        alert($("#oauth").text());
        if(sourceImageFont.digest==null || sourceImageFont.digest==""){
            if(content.length>47)
            sourceImageFont.digest=content.substring("0","47");
            sourceImageFont.digest=content.substring("0",content.length);
        }
        $.postJSON("/api/source/save-image-font?content="+content,sourceImageFont, function (data) {
            $.messager.alert("系统提示", "保存成功", "info");
            $('#imageList').datagrid('unselectAll');
        }, function (data, status) {
            $.messager.alert("系统提示","保存失败", "error");
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
                    field: 'image',
                    width:"100%"

                }
             ]
        ]
    });
    /**
     * 放大按钮
     */
    $("#bigBtn").on("click",function(){
        var selectedDatas=$("#imageList").datagrid("getSelections");
        if(selectedDatas.length!=1){
            $.messager.alert("系统提示","请选择一张图片进行放大","error");
            return;
        }
//        <div id="imageDlg" modal="true" draggable="false" class="easyui-dialog" style="width:100%;height:100%;padding:10px 20px"
//        closed="true"  data-options="modal:true">
//            <div id="imageDiv"></div>
//        </div>
        $("#imageDlg").dialog("open").dialog;
    })



})

