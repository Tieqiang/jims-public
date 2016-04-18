/**
 * Created by wei on 2016/4/6.
 */
$(function() {

//    <h3>{{x.questionContent }}</h3>
})
var app = angular.module("myApp", []);
//E:\workspace\jims-public\src\main\webapp\views\his\public\questionnaire-survey.html
app.controller('tableCtrl',function ($scope, $http) {
    $http.get("/api/questionnaire-model/find-by-id?id=4028860053ea82700153eaebc17a0000")
        .success(function (data) {
            $("#modelName").html(data.title);
            console.log(data);
            $scope.names = data;
        });


    $scope.answerSheetVo = {} ;

    $scope.answerSheetVo.openId = "112312" ;
    $scope.answerSheetVo.patId = "123123" ;
    $scope.answerSheetVo.questionnaireId = '4028860053ea82700153eaebc17a0000' ;
    $scope.answerSheetVo.answerResults=[] ;

    $scope.result = function(subjectId,content){

        $scope.flag = false ;

        for (var i = 0 ;i<$scope.answerSheetVo.answerResults.length;i++){
            if($scope.answerSheetVo.answerResults[i].subjectId==subjectId){
                $scope.answerSheetVo.answerResults[i].answer = content ;
                $scope.flag = true ;
            }
        }

        if(!$scope.flag){
            var obje = {} ;
            obje.subjectId = subjectId ;
            obje.answer = content ;
            $scope.answerSheetVo.answerResults.push(obje) ;
        }

        //console.log($scope.answerSheet) ;
    }

    $('#showDialog1').click(function() {
        $('#dialog1').show();
    });

    $('#quxioa').click(function() {
        alert('你点击了取消!!!');
        $('#dialog1').hide();
    });

    $('#okBtn').click(function() {

        console.log($scope.answerSheetVo);
        $http.post("/api/answer-sheet/add", $scope.answerSheetVo)
            .success(function (data) {
            $('#dialog1').hide();
            $("#showDialog1").attr("style", "display:none;");
            $("#checkbox").attr("style", "display:none;");
            $("#checkbox1").attr("style", "display:none;");
            $("#msg").attr("style", "display:block;");
        });
        //}, function (data) {
        //    $.messager.alert('提示', data.responseJSON.errorMessage, "error");
        //});
    });

});
