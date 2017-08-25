(function() {
	'use strict';
	angular.module('layout').directive('leftSidebar', leftSidebar);
	/**
	 * @description Provides the layout for the left side menu.
	 * @ngdoc directive
	 * @name leftSidebar
	 * @memberof layout
	 * @return {object}
	 */
	function leftSidebar() {
		return {
			templateUrl : '/demoMongo/app/core/layout/leftsidebar/leftSidebar.html',
			controller : LeftSideBarController,
			controllerAs : 'left',
			bindToController : true
		};
	}

	/**
	 * @description Only thing this does is route to the correct landing page
	 *              (dashboard) based upon user role
	 * 
	 * One could use this simple controller to change to any default state based
	 * upon any number or pre-defined conditions.
	 * 
	 * @name LeftSideBarController
	 * @memberof layout
	 * @ngdoc controller
	 * @param {constant}
	 *            app.menu
	 * @param {service}
	 *            $document
	 * @param {constant}
	 *            layout.settings
	 * 
	 * @prop {object} settings contains a copy of layout.settings
	 * @prop {object} menu contains a copy of the menu structure
	 * @listens AUTH_EVENTS.authenticate
	 */
	LeftSideBarController.$inject = [ 'app.menu', '$document', 'layout.settings', 'AuthService', '$rootScope', '$state','AUTH_EVENTS','USER_ROLES' ];
	function LeftSideBarController(menus, $document, settings, AuthService, $rootScope, $state,AUTH_EVENTS,USER_ROLES) {
		var self = this;
		self.settings = settings;
		self.closeAll = closeAll;
		
		$rootScope.$on(AUTH_EVENTS.login, function(){
			self.closeAll();
		});
		
		
		if($state && $state.current && $state.current.data){
			self.menuState = $state.current.data.menuState;
		}else{
			self.menuState = "";
		}
		
		self.menus = menus;
		
		function closeAll() {
			for (var i = 0; i < self.menus.length; i++) {
				for (var j = 0; j < self.menus[i].content.length; j++) {
					if (self.menus[i].content[j].open) {
						self.menus[i].content[j].open = false;
					}
				}
			}
		}
		
		$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
			if(toState.data && toState.data.menuState){
				self.menuState = toState.data.menuState;
			}else{
				self.menuState = '';
			}
			
		});
		
	}
}());
