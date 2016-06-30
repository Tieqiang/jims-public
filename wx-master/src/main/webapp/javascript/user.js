/**
 * Created by admin on 2016/6/30.
 */

var loadDict = function () {
    $.get("/api/source/source-user", function (data) {
        $("#user").datagrid('loadData', data);
    });
}
 /**
 * init datagrid
 */
var initUser=function() {
     $('#user').datagrid({
         idField: 'id',
         title: '关注用户列表',
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
                     field: 'openid',
                     title: 'openId'
                 },
                 {
                     field: 'nickname',
                     title: '昵称'
                 },
                 {
                     field: 'sex',
                     title: '性别',
                     formatter: function (index, value) {
//                        console.info(value);
                         if (value.sex == "1") {
                             return "男";
                         } else {
                             return "女";
                         }
                     }

                 },
                 {
                     field: 'city',
                     title: '城市'

                 },
                 {
                     field: 'country',
                     title: '国家'
                 }
             ]
         ]
     });
 }
