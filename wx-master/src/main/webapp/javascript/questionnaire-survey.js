
/**
 * Created by wei on 2016/4/6.
 */

var app = angular.module("myApp", []);
var checks=[];
var names=[];
var quest={};


var checksObj=[];
var namesObj=[];
var obj=[];
//定义每页展示5题
var numb=5;
//定义总页数默认为1
var pags=1;
//定义页码默认为1
var pageNum=1;
//获取url中的路径
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

//若该微信号未绑定患者，则点答题时弹窗提示先绑定患者。
if(GetQueryString("patId")=="" || GetQueryString("patId")==null){
    $("#app").attr("style","display:none;");
    $("#nullPatId").attr("style","display:block;");
}else {

    app.controller('tableCtrl', function ($scope, $http) {

        $http.get("/api/questionnaire-model/list-all")
            .success(function (data) {
                console.log(data);

                $scope.name = data;

            });
        $scope.click = function (id) {

            console.log(id);
            $http.get("/api/questionnaire-model/find-by-id?id=" + id)
                .success(function (data) {
                    console.log("--------------------");
                    console.log(id);
                    console.log(data);
                    $("#subject").attr("style", "display:none;");
                    $("#qq").attr("style", "display:block;");
                    $("#submit").attr("style", "display:block;");
                    $("#modelId").html(data.title);
                    $("#memo").html(data.memo);
                    $("#question").html(data.totalNumbers);
                    quest = data.questionnaireVsSubjectVo;
                    //获得总页数向上取整
                    pags = Math.ceil(quest.length / numb);

                    if (quest.length > 0) {
                        for (var i = 0; i < quest.length && i < 5; i++) {
                            if (quest[i].questionType == "1") {
                                quest[i].questionType = "多选";
                                checksObj.push(quest[i]);
                                obj.push(quest[i]);
                            } else {
                                quest[i].questionType = "单选";
                                namesObj.push(quest[i]);
                                obj.push(quest[i]);
                            }
                        }
                        checks = checksObj;
                        names = namesObj;
                    }
                    //显示第一页
                    if (quest.length <= 5) {
                        $scope.pageChecks = checks;
                        $scope.pageNames = names;
                        $("#next").attr("style", "display:none;");
                        $("#submit").attr("style", "display:block;");
                    } else {
                        $("#next").attr("style", "display:block;");
                        $("#submit").attr("style", "display:none;");

                        $scope.pageChecks = checks;
                        $scope.pageNames = names;
                    }


                })


            $scope.answerSheetVo = {};
            $scope.answerSheetVo.openId = GetQueryString("openId");
            $scope.answerSheetVo.patId = GetQueryString("patId");
            ;
            $scope.answerSheetVo.questionnaireId = id;
            $scope.answerSheetVo.answerResults = [];

            $scope.result = function (subjectId, content) {
                $scope.flag = false;
                for (var i = 0; i < $scope.answerSheetVo.answerResults.length; i++) {
                    if ($scope.answerSheetVo.answerResults[i].subjectId == subjectId) {
                        $scope.answerSheetVo.answerResults[i].answer = content;
                        $scope.flag = true;
                    }
                }
                ;
                if (!$scope.flag) {
                    var obje = {};
                    obje.subjectId = subjectId;
                    obje.answer = content;
                    $scope.answerSheetVo.answerResults.push(obje);
                }

            }

            //下一页按钮
            $scope.nextPages = function () {

                var choose = new Array();
                for (var i = 0; i < $scope.answerSheetVo.answerResults.length; i++) {
                    choose[choose.length] = $scope.answerSheetVo.answerResults[i].subjectId;
                }
                for (var i = 0; i < obj.length; i++) {
                    var a = obj[i].id;
                    $("input[name='" + a + "']:checked").each(function () {
                        choose[choose.length] = a
                    });
                }

                var checkall = [];
                for (var i = obj.length - 5; i < obj.length; i++) {
                    checkall[i] = false;
                    for (var j = 0; j < choose.length; j++) {
                        if ((choose[j]) == (obj[i].id)) {
                            checkall[i] = true;
                        }
                    }
                }
                var chk = true;
                for (var i = 0; i < checkall.length; i++) {
                    if (checkall[i] == false) {
                        chk = false;
                    }
                }


                //判断漏答
                if (chk == true) {
                    //将多选的答案封装好
                    for (var i = (pageNum - 1) * 5; i < (pageNum * 5); i++) {
                        console.log(pageNum);
                        if (obj[i].questionType == "多选") {
                            var a = obj[i].id;
                            $("input[name='" + a + "']:checked").each(function () {
                                var objcheck = {};
                                objcheck.subjectId = a;
                                objcheck.answer = $(this).val();
                                $scope.answerSheetVo.answerResults.push(objcheck);
                            });
                        }
                    }
                    console.log($scope.answerSheetVo);

                    //准备跳页的数据
                    pageNum++; //当前页码
                    var checksPageObj = [];
                    var namesPageObj = [];

                    //判断当前页是否是最后一页
                    if (pageNum < pags) {

                        //装好要展示的 $scope.pageChecks、$scope.pageNames
                        for (var i = (pageNum - 1) * 5; i < quest.length && i < 5 * pageNum; i++) {
                            if (quest[i].questionType == "1") {
                                quest[i].questionType = "多选";
                                checksPageObj.push(quest[i]);
                                obj.push(quest[i]);
                            } else {
                                quest[i].questionType = "单选";
                                namesPageObj.push(quest[i]);
                                obj.push(quest[i]);
                            }
                        }
                        checks = checksPageObj;
                        names = namesPageObj;
                        //console.log(checksObj);
                        //console.log(namesObj);
                        $scope.pageChecks = checks;
                        $scope.pageNames = names;
                    } else {
                        for (var i = (pageNum - 1) * 5; i < quest.length && i < 5 * pageNum; i++) {
                            if (quest[i].questionType == "1") {
                                quest[i].questionType = "多选";
                                checksPageObj.push(quest[i]);
                                obj.push(quest[i]);
                            } else {
                                quest[i].questionType = "单选";
                                namesPageObj.push(quest[i]);
                                obj.push(quest[i]);
                            }
                        }
                        checks = checksPageObj;
                        names = namesPageObj;
                        console.log(checks);
                        console.log(names);
                        $scope.pageChecks = checks;
                        $scope.pageNames = names;
                        $("#next").attr("style", "display:none;");
                        $("#submit").attr("style", "display:block;");
                    }

                } else {
                    $("#alert").attr("style", "display:block;");
                    $('#no2').click(function () {
                        $('#dialog1').hide();
                        $("#alert").attr("style", "display:none;");
                    });
                }

            };


            $('#showDialog1').click(function () {
                $('#dialog1').show();
            });

            $('#no1').click(function () {
                $('#dialog1').hide();
            });
            $('#ok').click(function () {

                var choose = new Array();
                for (var i = 0; i < $scope.answerSheetVo.answerResults.length; i++) {
                    choose[choose.length] = $scope.answerSheetVo.answerResults[i].subjectId;
                }
                for (var i = 0; i < obj.length; i++) {
                    var a = obj[i].id;
                    $("input[name='" + a + "']:checked").each(function () {
                        choose[choose.length] = a
                    });
                }
                //console.log(choose);
                var checkall = [];
                for (var i = (pageNum - 1) * 5; i < obj.length; i++) {
                    checkall[i] = false;
                    for (var j = 0; j < choose.length; j++) {
                        if ((choose[j]) == (obj[i].id)) {
                            checkall[i] = true;
                        }
                    }
                }
                var chk = true;
                for (var i = 0; i < checkall.length; i++) {
                    if (checkall[i] == false) {
                        chk = false;
                    }
                }

                if (chk == true) {
                    for (var i = 0; i < checks.length; i++) {
                        var a = checks[i].id;
                        $("input[name='" + a + "']:checked").each(function () {
                            var objcheck = {};
                            objcheck.subjectId = a;
                            objcheck.answer = $(this).val();
                            $scope.answerSheetVo.answerResults.push(objcheck);
                        });
                    }
                    console.log($scope.answerSheetVo)
                    $http.post("/api/answer-sheet/add", $scope.answerSheetVo)
                        .success(function (data) {
                            $('#dialog1').hide();
//                            alert("ok");
                            $("#app").attr("style", "display:none;");
                            $("#msg").attr("style", "display:block;");
                        });
                } else {
                    $("#alert").attr("style", "display:block;");
                    $('#no2').click(function () {
                        $('#dialog1').hide();
                        $("#alert").attr("style", "display:none;");
                    });
                }
            });

        }

    });
}