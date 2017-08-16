(function () {
  
	'strict mode';
    
    angular.module('manageTurret', ['restService'])
    .config(configStates);
    
    configStates.$inject = ['$stateProvider', 'USER_ROLES'];
   
    function configStates ($stateProvider,USER_ROLES) {
    	 
    	var data = {
    			 	authorizedRoles: [USER_ROLES.operationAdmin, USER_ROLES.manufacturingUser,USER_ROLES.customerUser, USER_ROLES.customerUser],
    	            menuState : 'manageTurret'
    	        };
    	 
        $stateProvider.state(
            'manageTurret', {
                url: '/manageTurret',
                templateUrl: '/portal/app/components/administrator/ManageTurret/manageTurret.html',
                controller: 'manageTurretController',
                controllerAs: 'mtc',
                data : data
            }).state(
                    'activateTurret', {
                        url: '/activateTurret',
                        templateUrl: '/portal/app/components/administrator/ManageTurret/activateTurret.html',
                        controller: 'manageTurretController',
                        controllerAs: 'mtc',
                        data : data
           })
    }
    
}());

