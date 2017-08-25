(function () {
    "use strict";
    
    angular.module('layout').controller('LayoutController', LayoutController);
    /**
     * @description
     * Only thing this does is route to the correct landing page (dashboard) based upon user role
     *
     * One could use this simple controller to change to any default state based upon any number
     * or pre-defined conditions.
     *
     * @name LayoutController
     * @memberof layout
     * @ngdoc controller
     * @param {service} $rootScope
     * @param {service} layoutState
     * @param {constant} AuthService
     * @param {service} AUTH_EVENTS
     * @param {service} USER_ROLES
     * @param {service} $state
     * 
     * @prop {object} settings contains a copy of layout.settings
     * @listens AUTH_EVENTS.authenticate
     */
    
    LayoutController.$inject = ['$rootScope', 'layoutState','AuthService',  'AUTH_EVENTS', '$state','$window','USER_ROLES'];
    function LayoutController ($rootScope,layoutState,AuthService, AUTH_EVENTS, $state,$window, USER_ROLES) {
        var self = this;
        this.settings = layoutState.getAll();
        $rootScope.showLoader=false;
        $rootScope.logoutClicked=false;
     
        $rootScope.$on('$stateChangeSuccess', function (ev, to, toParams, from, fromParams) {
        	if(AuthService.isLocked){
        		$rootScope.previousState = 'login'
        	}else if(!AuthService.isLocked && from!==null){
        		$rootScope.previousState = layoutState.defaultState(); //depending on user roles
        		console.log("$rootScope.previousState :: " + $rootScope.previousState );
        		
        	}
   
        	if(to.name === "Home"){
				$state.go('login');
        	}
        });
       
        $rootScope.$on(
            AUTH_EVENTS.login, function () {
            	console.log("state inside layout :::::"+layoutState.defaultState());
            	$state.go(layoutState.defaultState());
            });
        $rootScope.$on(AUTH_EVENTS.logout , function(){
        	self.settings.hideLeftMenu=false;
        });
        
        $rootScope.$on("loader_show", function () {
        	self.showLoading=true;
            
        });
        $rootScope.$on("loader_hide", function () {
        	 self.showLoading=false;
        });
        $rootScope.$on("connect_error", function () {
        	 if($rootScope.logoutClicked){
        		
        		 $state.go('Not Found');
        	 }else{
        		 $state.go('Error');
        	 }
        	
       }); 
    }
}());
