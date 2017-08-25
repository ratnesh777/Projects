(function () {
    "use strict";
    angular.module('auth')
           .run(rolesSetup)
           /**
            * @description Role dictionary
            * @ngdoc constant
            * @name USER_ROLES
            * @memberof auth
            * @prop {object} USER_ROLES.superUser
            * @prop {object} USER_ROLES.operationAdmin
            * @prop {object} USER_ROLES.manufacturingUser
            * @prop {object} USER_ROLES.guest
            */
           .constant('USER_ROLES', {
        	   customerUser: 3,
               /*customerAdmin: 4,
               manufacturingUser: 3,*/
               operationUser: 2,
               operationAdmin: 1
           })
           .service('RoleService', RoleService);
    /**
     * @description On $stateChangeStart run RoleService.checkAuthorization
     * @ngdoc run
     * @memberof auth
     * @name RoleSetup
     * @param {service} $rootScope
     * @param {service} RoleService
     * @listens $stateChangeState
     */
    rolesSetup.$inject = ['$rootScope', 'RoleService'];
    function rolesSetup ($rootScope, RoleService) {
        $rootScope.$on('$stateChangeStart', function (event, next) {
          //  if(next.name!='forgotPass' && next.name!='resetPass'){
            	if (typeof next.data === 'undefined' || typeof next.data.authorizedRoles === 'undefined') { return; }
            	 RoleService.checkAuthorization(next.data.authorizedRoles, event); 
            //}
        });
    }
    
    /**
     * @description Provides mechanism for validating access based upon role dictionary
     * @ngdoc service
     * @name RoleService
     * @memberof auth
     *
     * @param {service} $rootScope
     * @param {service} AuthService
     * @param {service} $state
     *
     * @prop {array} authorizedRoles Holds list of authorized roles for a resource
     */
    RoleService.$inject = ['$rootScope', 'AuthService', '$state'];
    function RoleService ($rootScope, AuthService, $state) {
        var self = this;
        this.authorizedRoles = [];
        this.checkAuthorization = checkAuthorization;
        this.matchRole = matchRole;
        /**
         * @description Main method used to validate if logged in user has correct access permission
         * @memberof RoleService
         * @param {mixed} authorizedRoles List of roles authorized by the $state (in the state config)
         * @param {event} event Need to prevent the default behavior - so event is passed along
         */
        function checkAuthorization (authorizedRoles, event) {
          
        	if (!AuthService.currentUser || !AuthService.currentUser.email) {
        		console.log("Inside checkAuthorization..."+AuthService.currentUser + " & localStorage.getItem('portalCurrentUserRole')::"+ localStorage.getItem('portalCurrentUserRole'));
        		
        		AuthService.currentUser.role = localStorage.getItem('portalCurrentUserRole');//TODO will update after spring-security integration and will fix by calling retrieveLogin() method 
        		AuthService.isLocked = false;
        		setRoles(authorizedRoles);
            	
          /*  	AuthService.retrieveLogin().then(function(resp){
            		if(resp.data && resp.data.emailId && resp.data.role){
            			AuthService.currentUser = resp.data;
            			AuthService.isLocked = false;
            			setRoles(authorizedRoles);
                        checkRole(event);
            		}else{
            			 notAuthorized(event);
            		}
            	},AuthService.logout);*/
            }
        }
        
        /**
         * @description Iterates over list of roles to see if user is assigned to any of them.
         * If a currentUser.role is in the list, the user is authorized
         * @memberof RoleService
         * @param {event} event Then event is passed along
         */
        function checkRole (event) {
            var authorized = false;
            for (var i = 0; i < self.authorizedRoles.length; i++) {
                authorized = (AuthService.currentUser.role === self.authorizedRoles[i]);
                if (authorized) { break; }
            }
            if (!authorized) {
                notAuthorized(event);
            }
        }
        
        /**
         * @description Stops the app from navigating to the next state and notifies
         * the app via broadcast "not authorzied"
         * Immediately redirects to Not Authorized state.
         * @memberof RoleService
         * @param {event} event Then event to preventDefault() on - stops state transition
         */
        function notAuthorized (event) {
            event.preventDefault();
            $rootScope.$broadcast('auth-not-authorized');
            $state.go('Not Authorized');
        }
        
        /**
         *
         * @param {array} authorizedRoles
         * @returns {boolean}
         */
            function matchRole (authorizedRoles) {
            //	console.log("authorizedRoles ::" + authorizedRoles + " AuthService.currentUser.role :: "+AuthService.currentUser.role);
                setRoles(authorizedRoles);
                if (!AuthService.currentUser) { return false; }
                return (self.authorizedRoles.indexOf(Number(AuthService.currentUser.role)) === -1)? false : true;
            }
        
        /**
         * @description Converts a string into an array for internal use
         * @memberof RoleService
         * @param {mixed} roles
         */
        function setRoles (roles) {
            if (!angular.isArray(roles)) {
                roles = [roles];
            }
            self.authorizedRoles = roles;
        }
    }
}());
