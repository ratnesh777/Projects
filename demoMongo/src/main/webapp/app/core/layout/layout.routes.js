(function () {
    'strict mode';

    /**
     * @description These routes constitute the default states the application can be in without any other routes or states.
     *
     * This covers 401, 403, 404, 500, and UI Router "no state found" errors
     *
     * BEST PRACTICE:
     * Each new state configuration for each view and sub view (state and sub-state) should be configured
     * within the config method for the module containing the view code. This isolates the state and view from
     * the core of the application (this config).  So it can be removed and replaced without messing with
     * the core config. Just sayin'
     *
     * @name routes
     * @memberof layout
     * @ngdoc constant
     *
     */
    angular.module('layout')
           .constant('routes',
                     [{
                         name: 'Home', 
                         config: {
                             url: '/'
                         }
                     },{
                         name: 'Not Found',
                         config: {
                             url: '/404',
                             views : {
                        		 'lockedView':{
                        			 templateUrl: '/demoMongo/app/core/errors/404.html'
                        		 }
                        	 }
                         }
                     }, {
                         name: 'Error', 
                         config: {
                             url: '/500',
                             templateUrl: '/demoMongo/app/core/errors/500.html'
                         }
                     }, {
                         name: 'Not Authorized', 
                         config: {
                             url: '/401',
                             templateUrl: '/demoMongo/app/core/errors/401.html',
                             controller: notAuthorizedController
                         }
                     }, {
                         name: 'Not Logged In',
                         config: {
                             url: '/403',
                        	 views : {
                        		 'lockedView':{
                        			 templateUrl: '/demoMongo/app/core/errors/403.html'
                        		 }
                        	 }
                         }
                     }, {
                         name: 'noState',
                         config: {
                             url: '/noState/:missingState/:foundUpper/:foundLower',
                             templateUrl: '/demoMongo/app/core/errors/noState.html',
                             controller: noStateController
                         }
                     },{
                         name: 'login', 
                         config: {
                        	 url: '/login',
                        	 views : {
                        		 'lockedView':{
                                     templateUrl: '/demoMongo/app/core/auth/login.html',
                                     controller :'AuthController',
                                     controllerAs : 'auth'
                        		 }
                        	 }
                         }
                     },{
                         name: 'forgotPass', config: {
                        	 url: '/forgotPass',
                        	 views : {
                        		 'lockedView':{
                                     templateUrl: '/demoMongo/app/core/auth/forgot-password.html',
                                     controller :'forgotPassCtrl',
                                     controllerAs : 'fpc'
                        		 }
                        	 }
                         }
                     }, {
                         name: 'resetPass', config: {
                        	 url: '/resetPass?userId',
                        	 views : {
                        		 'lockedView':{
                                     templateUrl: '/demoMongo/app/core/auth/resetPass.html',
                                     controller :'resetPassCtrl',
                                     controllerAs : 'rpc'
                        		 }
                        	 }
                         }
                     }
                     ]);
    /**
     * @description Prevents unauthorized access
     * @name notAuthorizedController
     * @memberof layout
     * @ngdoc controller
     * @param {service} $scope
     * @param {service} AuthService
     */
    notAuthorizedController.$inject = ['$scope', 'AuthService'];
    function notAuthorizedController ($scope, AuthService) {
        $scope.loggedIn = (!AuthService.currentUser) ? false : true;
    }
    
    /**
     * @description Notifies developer that they haven't created the route/state yet
     * @name noStateController
     * @ngdoc controller
     * @memberof layout
     * @param {service} $stateParams
     * @param {service} $scope
     */
    noStateController.$inject = ['$stateParams', '$scope'];
    function noStateController ($stateParams, $scope) {
        $scope.missingState = $stateParams.missingState;
        $scope.foundUpper = $stateParams.foundUpper;
        $scope.foundLower = $stateParams.foundLower;
    }

}());

