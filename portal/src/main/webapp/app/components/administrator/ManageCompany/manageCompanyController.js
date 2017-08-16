(function() {
	angular.module('manageCompany').controller('manageCompanyController',
			manageCompanyController);

	manageCompanyController.$inject = [ '$state', 'urlConstants', '$rootScope'];

	function manageCompanyController($state, urlConstants, rootScope) {
	     var mcc = this;
		mcc.init = init;
	}

	
	function init() {
		console.log("Inside manage company");
	}
}());
