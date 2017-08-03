//ipcApp.controller('mainController', ['$scope', '$log', function($scope, $log) {

ipcApp.controller('mainController', ['$scope', '$log','$rootScope', function($scope, $log,$rootScope) {
    
    $scope.name = 'Main';
    $scope.title = 'Policy Engine';
}]);
