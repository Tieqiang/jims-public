/**
 * Created by heren on 2016/4/18.
 */
var messageBuildApp = angular.module("messageBuildApp",['summernote']) .directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs, ngModel) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            element.bind('change', function(event){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
                //附件预览
                scope.file = (event.srcElement || event.target).files[0];
                scope.getFile();
            });
        }
    };
}]).factory('fileReader', ["$q", "$log", function($q, $log){
        var onLoad = function(reader, deferred, scope) {
            return function () {
                scope.$apply(function () {
                    deferred.resolve(reader.result);
                });
            };
        };

        var onError = function (reader, deferred, scope) {
            return function () {
                scope.$apply(function () {
                    deferred.reject(reader.result);
                });
            };
        };

        var getReader = function(deferred, scope) {
            var reader = new FileReader();
            reader.onload = onLoad(reader, deferred, scope);
            reader.onerror = onError(reader, deferred, scope);
            return reader;
        };

        var readAsDataURL = function (file, scope) {
            var deferred = $q.defer();
            var reader = getReader(deferred, scope);
            reader.readAsDataURL(file);
            return deferred.promise;
        };

        return {
            readAsDataUrl: readAsDataURL
        };
    }]);

var messageBuildCtrl = messageBuildApp.controller('messageBuildCtrl',['$scope','$http','fileReader',function($scope,$http,fileReader){
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

    //$scope.articles.push({
    //    title:'zhagnsan',
    //    thumb_media_id:'http://wx.qlogo.cn/mmopen/awUVm8gKFmFyw1SWyyq1Xr0wGz9dEnSE20ibBNaicwcQAKqeO8lzA5LGWIy7dFAa8mbNEZWMDVqFBkcdEx7VRWug/0'
    //})
    $scope.articles.push($scope.article) ;

    //添加图文
    $scope.addArticle = function(){
        $scope.article = new Article() ;
        $scope.articles.push($scope.article) ;
    }

    $scope.currentArticle = function(obj){
        $scope.article=obj ;
    }


    $scope.getFile = function () {
        fileReader.readAsDataUrl($scope.file, $scope)
            .then(function(result) {
                $scope.imageSrc = result;
            });
    };



    $scope.upload = function(){
        //console.log($scope.imageSrc);
        //var postData = {
        //    fileName: $scope.imageSrc
        //};
        //
        //var promise = postMultipart('/api/media/upload', postData);
        $("#myForm").submit();
    }

    function postMultipart(url, data) {
        var fd = new FormData();
        angular.forEach(data, function(val, key) {
            fd.append(key, val);
        });
        var args = {
            method: 'POST',
            url: url,
            data: fd,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        };
        return $http(args);
    }


    $('#file').on('fileuploaded', function(event, data, previewId, index) {
        var form = data.form, files = data.files, extra = data.extra,
            response = data.response, reader = data.reader;
        console.log('File uploaded triggered');
    });

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

