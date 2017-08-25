(function () {
    "use strict";
    
    angular.module('layout').directive('headerBar', headerBar);
    
    /**
     * @description Provides the layout for the left side menu.
     * @ngdoc directive
     * @name headerBar
     * @memberof layout
     * @return {object}
     */
    function headerBar () {
        return {
            templateUrl: '/demoMongo/app/core/layout/header/headerBar.html',
            controller: HeaderController,
            controllerAs: 'header',
            scope: {
                settings: '=',
                locked :'=',
            },
            bindToController: true
        };
    }
    /**
     * @description
     * This toggles fullscreen mode
     *
     * @name HeaderController
     * @memberof layout
     * @ngdoc controller
     * @param {service} $scope
     * @param {service} fullScreenMode
     */
    HeaderController.$inject = ['$scope', '$controller']; 
    function HeaderController ($scope, $controller) {
        var self = this;
        self.test = test;
        var authController  = $controller('AuthController');
        self.user = authController.currentUser;
        self.url ='https://www.google.com/';
      /*  $scope.$watch('header.settings.fullScreenMode', function (toggledSetting) {
            fullScreenMode.toggle(toggledSetting);
        });*/
        
        function test(){
        	console.log("test function");
        }
    }
    
}());
