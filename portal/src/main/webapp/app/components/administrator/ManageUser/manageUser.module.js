(function () {

	'strict mode';

    angular.module('manageUser', ['restService','ui.grid', 'ui.grid.pagination', 'ui.grid.selection','ui.grid.resizeColumns'])
    .config(configStates);

    configStates.$inject = ['$stateProvider', 'USER_ROLES'];

    function configStates ($stateProvider,USER_ROLES) {

    	var data = {
    			 	authorizedRoles: [USER_ROLES.operationAdmin,USER_ROLES.customerAdmin],
    	            menuState : 'manageUser'
    	        };

        $stateProvider.state(
            'manageUser', {
                url: '/manageUser',
                templateUrl: '/portal/app/components/administrator/ManageUser/manageUser.html',
                controller: 'manageUserController',
                controllerAs: 'muc',
                data : data
            }).state(
            'createUser', {
                url: '/createUser',
                templateUrl: '/portal/app/components/administrator/ManageUser/createUser.html',
                controller: 'createUserController',
                controllerAs: 'cuc',
                data : data
            })

    }

}());

