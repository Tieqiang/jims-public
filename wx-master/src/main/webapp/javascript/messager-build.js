/**
 * Created by heren on 2016/4/18.
 */

var messageBuildApp = angular.module("messageBuildApp",['summernote','ngFileUpload'])

var messageBuildCtrl = messageBuildApp.controller('messageBuildCtrl',['$scope','$http','Upload',function($scope,$http,Upload){
    //编辑器配置
    $scope.options={
        height:250,
        focus:true,
        lang:'zh-CN',
        toolbar: [
            ['edit',['undo','redo']],
            ['headline', ['style']],
            ['style', ['bold', 'italic', 'underline', 'superscript', 'subscript', 'strikethrough', 'clear']],
            ['fontface', ['fontname']],
            ['textsize', ['fontsize']],
            ['fontclr', ['color']],
            ['alignment', ['ul', 'ol', 'paragraph', 'lineheight']],
            ['height', ['height']],
            //['table', ['table']],
            //['insert', ['link','picture','video','hr']],
            ['view', ['fullscreen', 'codeview']],
            ['help', ['help']]
        ]
    } ;

    //定义图文实体数组
    $scope.articles = [] ;//图文实体数组

    $scope.article = new Article;//图文实体

    $scope.articles.push($scope.article) ;

    //添加图文
    $scope.addArticle = function(){
        $scope.article = new Article() ;
        $scope.articles.push($scope.article) ;
    }

    $scope.currentArticle = function(obj){
        $scope.article=obj ;
    }

    //文件上传
    $scope.submit = function() {
        if ($scope.form.file.$valid && $scope.file) {
            $scope.upload($scope.file);
        }
    };
    // upload on file select or drop
    $scope.upload = function (file) {
        Upload.upload({
            url: '/api/media/upload',
            data: {file: file}
        }).then(function (resp) {
            $scope.article.thumb_media_id=resp.data.url;
            //console.log(resp)
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
    $scope.save=function(){
        $http.post("/api/media/save",$scope.articles).success(function(){
            console.log("ok")
        })
    }



}]) ;

//ariticle 的实体类
var Article = function(){
    this.thumb_media_id = "" ;
    this.author="" ;
    this.title="" ;
    this.content_source_url = "" ;
    this.content="" ;
    this.digest="" ;
    this.show_cover_pic="" ;
} ;

