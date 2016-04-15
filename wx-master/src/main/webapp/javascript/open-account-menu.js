var app = angular.module('myApp', ['ngGrid']);

app.service('mainService', function ($http) {

    this.getAllReqMsg = function () {
        return $http.get("/api/req-msg/list-all");
    }
});

app.controller('mainController', function ($scope, mainService) {
    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };

    $scope.totalServerItems = 0;

    $scope.pagingOptions = {
        pageSizes: [2, 10, 20],
        pageSize: 2,
        currentPage: 1
    };
    //$scope.setPagingData = function (data, page, pageSize) {
    //    var pageData = data.slice((page - 1) * pageSize, page * pageSize);
    //    $scope.books = pageData;
    //    $scope.totalServerItems = data.length;
    //    if (!$scope.$$phase) {
    //        $scope.$apply();
    //    }
    //};
    //$scope.getPagedDataAsync = function (pageSize, page, searchText) {
    //    setTimeout(function () {
    //        var data;
    //        if (searchText) {
    //            var ft = searchText.toLowerCase();
    //            $http.get('/api/req-msg/list-all')
    //                .success(function (largeLoad) {
    //                    data = largeLoad.filter(function (item) {
    //                        return JSON.stringify(item).toLowerCase().indexOf(ft);
    //                    });
    //                    $scope.setPagingData(data, page, pageSize);
    //                });
    //            //data = largeLoad.filter(function (item) {
    //            //    return JSON.stringify(item).toLowerCase().indexOf(ft);
    //            //});
    //        }
    //        else {
    //            $http.get('/api/req-msg/list-all')
    //                .success(function (largeLoad) {
    //                    $scope.setPagingData(largeLoad, page, pageSize);
    //                });
    //        }
    //
    //
    //        var pagedData = data.slice((page - 1) * pageSize, page * pageSize);
    //        $scope.myData = pagedData;
    //        $scope.totalServerItems = data.length;
    //        if (!$scope.$$phase) {
    //            $scope.$apply();
    //        }
    //    }, 100);
    //};

    //$scope.$watch('pagingOptions', function () {
    //    self.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
    //}, true);
    //$scope.$watch('filterOptions', function () {
    //    self.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
    //}, true);
    //
    //
    //self.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

    var basicCellTemplate = '<div class="ngCellText" ng-class="col.colIndex()" ng-click="editCell(row.entity, row.getProperty(col.field), col.field)"><span class="ui-disableSelection hover">{{row.getProperty(col.field)}}</span></div>';
    $scope.gridOptions = {
        data:'Data',
        enablePinning: false,
        enablePaging: true,
        showFooter: true,
        enableColumnResize: true,
        enableCellSelection: true,
        enableCellEdit: true,
        enableRowSelection: true,
        mutiSelect: false,
        columnDefs: [
            {
                field: "id",
                displayName: '序号',
                width: 180,
                pinned: true,
                enableCellEdit: true
            },
            {
                field: "fromUserName",
                displayName: '来自',
                width: 200,
                enableCellEdit: true
            },
            {
                field: "toUserName",
                displayName: '发往',
                width: 100,
                enableCellEdit: true
            },
            {
                field: "msgType",
                displayName: '类型',
                width: 120,
                enableCellEdit: true,
                //cellFilter: 'currency:"￥"',
                cellTemplate: basicCellTemplate
            },
            {
                field: "context",
                displayName: '内容',
                width: 200,
                enableCellEdit: false,
                cellTemplate: '<button id="editBtn" type="button" class="btn btn-xs btn-info"  ng-click="updateCell()" >Click a Cell for Edit </button>'
            }] ,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        filterOptions: $scope.filterOptions

    };

    GetAllRecords();
    function GetAllRecords() {
        var promiseGet = mainService.getAllReqMsg();
        promiseGet.then(function (pl) {
                console.log(pl.data);
                $scope.Employees = pl.data, $scope.Data = pl.data;

            },
            function (errorPl) {
                $log.error('Some Error in Getting Records.', errorPl);
            });
    }

    $scope.selectedCell;
    $scope.selectedRow;
    $scope.selectedColumn;

    $scope.editCell = function (row, cell, column) {
        $scope.selectedCell = cell;
        $scope.selectedRow = row;
        $scope.selectedColumn = column;
    };

    $scope.updateCell = function () {
        //   alert("checking");
        $scope.selectedRow[$scope.selectedColumn] = $scope.selectedCell;
    };


    $scope.gridOptions.sortInfo = {
        fields: ['id', 'fromUserName'],
        directions: ['asc'],
        columns: [0, 1]
    };


    $scope.changeGroupBy = function (group1, group2) {
        $scope.gridOptions.$gridScope.configGroups = [];
        $scope.gridOptions.$gridScope.configGroups.push(group1);
        $scope.gridOptions.$gridScope.configGroups.push(group2);
        $scope.gridOptions.groupBy();
    }
    $scope.clearGroupBy = function () {
        $scope.gridOptions.$gridScope.configGroups = [];
        $scope.gridOptions.groupBy();
    }


});

