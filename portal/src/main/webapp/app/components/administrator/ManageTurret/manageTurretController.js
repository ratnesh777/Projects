(function() {
	angular.module('manageTurret').controller('manageTurretController',
			manageUserController);

	manageUserController.$inject = [ '$state', 'urlConstants', '$rootScope'];

	function manageUserController($state, urlConstants, rootScope) {
	     var muc = this;
		muc.init = init;
	}

	
	function init() {
		console.log("Inside manage turret");
	}
}());
