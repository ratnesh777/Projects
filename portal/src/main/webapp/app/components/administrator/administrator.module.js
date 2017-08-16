(function () {
    'use strict';
    
    angular.module(
        'administrator', [ 'auth', 'manageUser','manageCompany'])
        .config(configStates);
    
    configStates.$inject = ['$stateProvider', 'USER_ROLES'];
    function configStates ($stateProvider, USER_ROLES) {
    	
        $stateProvider.state(
        		'AdminDashBoard', {
        						url: '/adminDashBoard',
        						templateUrl: '/portal/app/components/administrator/dashboard.html',
        						controller: AdministrationDashboardController,
        						constrollerAs: 'adc',
        						bindToController: true,
        						  data: {
        			                    authorizedRoles: [USER_ROLES.operationAdmin,USER_ROLES.customerAdmin]
        			                }
        		});
    }
    
    AdministrationDashboardController.$inject = [];
    function AdministrationDashboardController () {
    }
    
       
}());



