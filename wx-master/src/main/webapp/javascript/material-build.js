/**
 * Created by heren on 2016/4/18.
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

    /*$scope.images = []; //定义图片实体数组

    $scope.image = new Image;   //图片实体

    $scope.images.push($scope.image);*/

    //文件上传
    $scope.submit = function () {
        //if ($scope.form.file.$valid && $scope.file) {
            $scope.upload($scope.file);
        //}
    };
    // upload on file select or drop
    $scope.upload = function (file) {
        console.log("file:" + file);
        Upload.upload({
            url: '/api/material/upload',    //服务端接收
            data: {file: file}     //上传的同时带的参数
            //file: file
        }).then(function (resp) {
            console.log("resp.data.url:" + resp.data.url);
            //$scope.image.image_media_id = resp.data.url;
        }, function (resp) {
            console.log('Error status: ' + resp.status);
        }, function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
        });
    };

    /**
     * 保存素材小
     */
    $scope.save = function () {
        $http.post("/api/material/save", $scope.images).success(function () {
            console.log("ok");
        })
    };
}]);

/*
//image 的实体类
var Image = function () {
    this.image_media_id = "";
};*/
