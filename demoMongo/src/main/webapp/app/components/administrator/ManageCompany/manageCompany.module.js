(function () {
  
	'strict mode';
    
    angular.module('manageCompany', ['restService'])
    .config(configStates);
    
    configStates.$inject = ['$stateProvider', 'USER_ROLES'];
   
    function configStates ($stateProvider,USER_ROLES) {
    	 
    	var data = {
    			 	authorizedRoles: [USER_ROLES.operationAdmin],
    	            menuState : 'manageCompany'
    	        };
    	 
        $stateProvider.state(
            'manageCompany', {
                url: '/manageCompany',
                templateUrl: '/demoMongo/app/components/administrator/ManageCompany/manageCompany.html',
                controller: 'manageCompanyController',
                controllerAs: 'mcc',
                data : data
            })
    }
    
}());

