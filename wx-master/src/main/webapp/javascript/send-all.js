/**
 * Created by admin on 2016/6/25.
 */

$(function(){
     var sendContent = UE.getEditor('font');
    /**
     * 加载图文消息
     */
     var loadImageFont=function(){
        //todo  加载图文消息
        $.get("/api/source/load-image-font",function(data){
            $("#data").datagrid("loadData",data);
        });
     }

    /**
     * 加载图片
     */
    var  loadImage=function(){
        $.get("/api/source/load-image",function(data){
            $("#data").datagrid("loadData",data);
        });
    }
    /**
     * 跟换样式
     */
    var loadStyle=function(){
        $("#wenzi").hide();
        $("#data").show();
    }
     $('#selectType').combobox({
         onChange: function (newValue, oldValue) {
             if(newValue==1){//图文
                 init();
                loadStyle();
                loadImageFont();
             }else if(newValue==3){
                 //图片
                 init();
                 loadStyle();
                 loadImage();
             }else if(newValue==2){//文字
                 document.location.reload();
             }
         }
     });
    var getSelectData=function(){
        var selectData=$("#data").datagrid("getSelections");
        if(selectData.length<=0){
            $.messager.alert("系统提示","请选择你要发送的内容","error");
            return;
        }
        var sendMessage={};
        var ids="";
        for(var i=0;i<selectData.length;i++){
            ids+=selectData[i].id+",";
        }
        ids=ids.substring(0,ids.length-1);
        sendMessage.ids=ids;
        return sendMessage;
    }
    var  sendData=function(){
        var sendType=$('#selectType').combobox("getValue");
        if(sendType==1){//图文
            var sendMessage=getSelectData();
            ajaxReq(sendType,sendMessage);
        }else if(sendType==2){//文字
            var sendContent = UE.getEditor('font');
            var sendContent=sendContent.getContentTxt();
//            alert(sendContent);
            var sendMessage={};
            sendMessage.content=sendContent;
            ajaxReq(sendType,sendMessage);
         }else if(sendType==3){//图片
            var sendMessage=getSelectData();
            ajaxReq(sendType,sendMessage);
         }
     }
    /*
    *
     */
    var ajaxReq=function(sendType,sendMessage){
        $.postJSON("/api/source/send-all?sendType="+sendType,sendMessage,function(data){
             $.messager.alert("系统提示","发送成功","info");
        },function(data,status){
            $.messager.alert("系统提示","发送失败","error");
        })
    }
    /**
     * 群发发送操作
     */
    $("#send").on("click",function(){
            sendData();
     });


    /**
     * 图片和图文素材的库
     */
    function init(){
        $("#data").datagrid({
            idField: "id",
            title: '素材库列表',
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
                        field: 'mediaId'
                    },
                    {
                        title: 'image',
                        field: 'image',
                        width:"60%"
                     }
                ]
            ]
        });
    }

 });
