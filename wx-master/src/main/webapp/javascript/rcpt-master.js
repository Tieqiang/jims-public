var app = angular.module("myApp", []);
var patId = "";
var name = "";
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
    return null;
}
var openId = GetQueryString("openId");
app.controller('tableCtrl', function ($scope, $http) {
    $http.get("/api/rcpt-master/find-by-app-user?openId=" + openId)
        .success(function (data) {
            $("#name").attr("style", "display:block;");
            $("#clinic").attr("style", "display:none;");
            $("#rcpt").attr("style", "display:none;");
            $("#items").attr("style", "display:none;");
            console.info(data);
            $scope.nameList = data;
        });
    /**
     * 单击tr触发
     * @param date
     */
//    $scope.clickTR=function(date){
//
//    }
    $scope.clickTR = function (date,visitNo) {
        //alert(1);
        $http.get("/api/rcpt-master/find-by-patient?patientId=" + patId+"&date="+date+"&visitNo="+visitNo)
            .success(function (data) {
                $('#checkName').html(name);
                $("#name").attr("style", "display:none;");
                $("#clinic").attr("style", "display:none;");
                $("#rcpt").attr("style", "display:block;");
                $("#items").attr("style", "display:none;");
                $scope.outpList = data;
            })
    }
    $scope.clickName = function (id, checkName) {
        $http.get("/api/rcpt-master/find-by-pat-id?patId=" + id)
            .success(function (data) {
                if (data.length > 0) {
                    patId =id;
                    name = checkName;
                    $("#name").attr("style", "display:none;");
                    $("#clinic").attr("style", "display:block;");
                    $("#rcpt").attr("style", "display:none;");
                    $("#items").attr("style", "display:none;");
                    $scope.clinicList = data;
                } else {
                    $("#name").attr("style", "display:none;");
                    $("#clinic").attr("style", "display:none;");
                    $("#rcpt").attr("style", "display:none;");
                    $("#items").attr("style", "display:none;");
                    $("#nullRcpt").attr("style", "display:block;");
                }

            })
    }



    $scope.clickRcpt = function (rcptNo) {
        $http.get("/api/rcpt-master/find-by-rcpt?rcptNo=" + rcptNo)
            .success(function (data) {
                $("#name").attr("style", "display:none;");
                $("#clinic").attr("style", "display:none;");
                $("#rcpt").attr("style", "display:none;");
                $("#items").attr("style", "display:block;");
                $scope.rctpList = data;
            })
    }

    $scope.backPatient = function () {
        $("#name").attr("style", "display:none;");
        $("#clinic").attr("style", "display:none;");
        $("#rcpt").attr("style", "display:block;");
        $("#items").attr("style", "display:none;");
    }

    $scope.backNameList = function () {
        $("#name").attr("style", "display:block;");
        $("#clinic").attr("style", "display:none;");
        $("#rcpt").attr("style", "display:none;");
        $("#items").attr("style", "display:none;");
    }

    $('#nullRcptOk').click(function () {
        $('#nullRcpt').attr("style", "display:none;");
        $("#name").attr("style", "display:block;");
        $("#clinic").attr("style", "display:none;");
        $("#rcpt").attr("style", "display:none;");
        $("#items").attr("style", "display:none;");

    })

})