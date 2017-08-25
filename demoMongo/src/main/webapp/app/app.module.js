(function () {
    'use strict';

    /**
     * @description Bootstrap only
     *
     * @see src/client/app/core/layout/layout.module for application entry point
     *
     * @name app
     * @ngdoc module
     * @param {module} ngSanitize is here for translations services
     * @param {module} auth is here for app wide authentication access
     * @param {module} layout is the workhorse and the
     */
    
    angular.module('app', [
                           'ngSanitize', // clean up html when needed
                           'auth','layout' // The true application workhorse
                       ])
                       .controller('AppController', AppController)
                       .run();
    
    
    
    /**
     * @description Global StateChangeError Handler
     *
     * @exception Custom exception message
     * @listens $stateChangeError
     *
     * @param {service} $rootScope
     * @param {server} $exceptionHandler
     */
    manageStateChange.$inject = ['$rootScope', '$exceptionHandler','AuthService'];
    function manageStateChange ($rootScope, $exceptionHandler,AuthService) {
    	
    	$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
			if((toState.name=='login')){
				AuthService.deleteCookies();
			}
		});

    	$rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
            $exceptionHandler({exception: 'stateChangeError'}, arguments);
        });
    }
    
    /**
     * @description Simply here to provide global access to the AuthService
     *
     * @name AppController
     * @param {service} AuthService
     */
    AppController.$inject = ['AuthService','RoleService'];
    function AppController (AuthService,RoleService) {
        this.auth = AuthService;
        this.role = RoleService;
        //console.log("hello");
    }
                
}());
