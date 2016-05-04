/**
 * Created by fyg on 2016/4/18.
 */

var materialApp = angular.module("materialApp", [ 'ngFileUpload'])

var messageBuildCtrl = materialApp.controller('materialControl', ['$scope', '$http', 'Upload', function ($scope, $http, Upload) {
    $scope.myImg = false;   //图片类型素材初始化设置为显示
    $scope.myVoice = true;  //语音类型素材初始化设置为隐藏
    $scope.myVideo = true;  //视频类型素材初始化设置为隐藏
    $scope.imgToggle = function () {        //点击图片素材按钮，语音和视频两种素材隐藏
        $scope.myImg = false;
        $scope.myVoice = true;
        $scope.myVideo = true;
    };
    $scope.voiceToggle = function () {      //点击语音素材按钮，图片和视频两种素材隐藏
        $scope.myImg = true;
        $scope.myVoice = false;
        $scope.myVideo = true;
    };
    $scope.videoToggle = function () {      //点击视频素材按钮，图片和语音素材隐藏
        $scope.myImg = true;
        $scope.myVoice = true;
        $scope.myVideo = false;
    };

    $scope.type = "image";
    $scope.imgItems = new Array();
    $http.get('/api/material/list?type='+ $scope.type).success(function(resp){
        //console.log($scope.imgItems);
        angular.forEach(resp.item,function(data,index){
            var fullUrl = data.url;
            fullUrl=fullUrl.split("?")[0];
            resp.item[index].url = fullUrl;
            //console.log("fullUrl:" + fullUrl);
            /*imgItems = resp.item;
            console.log("resp.item[index].url:" + resp.item[index].url);
            imgItems = resp.item[index];
            imgItems = [data.length];
            console.log("data.url:" + data.url);
            imgItems[index] = '<img src="' + data.url + '">';
            $("#i").html(imgItems[index]);
            imgItems[index] = '"' + data.url + '"';
            console.log("imgItems[index]:" + imgItems[index]);*/
        });
        $scope.imgItems = resp.item;
        //console.log("$scope.imgItems:" + $scope.imgItems);
    });

    $scope.media_id = "";
    //文件上传
    $scope.upload = function (file) {
        console.log("file:" + file);
        Upload.upload({
            url: '/api/material/upload-img',    //服务端接收
            data: {file: file}     //上传的同时带的参数
        }).then(function (resp) {
            console.log("resp.data.media_id:" + resp.data.media_id);
        }, function (resp) {
            console.log('Error status: ' + resp.status);
        }, function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
        });
    };
}]);