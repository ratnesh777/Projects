(function () {
    "use strict";
    
    angular.module('auth')
        .controller('AuthController', AuthController);
    
    /**
     * @description
     * AuthController is instantiated inside the directives
     *
     * This controller is used by various authentication directives. It is not meant
     * to be used by any components other than than athentication capabilities.
     *
     * If you need authencation services, use the AuthService service.
     *
     * @name AuthController
     * @memberof auth
     * @listens module auth:AUTH_EVENTS.authenticate
     * @ngdoc controller
     * @param {service} $rootScope
     * @param {service} AuthService
     * @param {constant} AUTH_EVENTS
     * @param {service} loggerService
     *
     * @prop {object} currentUser references AuthService.currentUser
     * @prop {object} credentials populated from form ng-model attributes
     */
    AuthController.$inject = ['$rootScope', 'AuthService','AUTH_EVENTS', '$state'];
    function AuthController ($rootScope, AuthService,AUTH_EVENTS, $state) {
        var self = this;
        self.loginAttemptCount = 0;
        self.messages = {
            200: 'Logged in. Welcome!',
            401: 'Email and password do not match',
            403: 'Not a registered user, please register or contact administrator.',
            422: 'Email and pasword are mandatory fields.',
            423: 'Your account has been locked. To unlock contact administrator.',
            500: 'Email and password do not match'
        };
        self.currentUser = {};
        self.credentials = {};
        self.login = login;
        self.logout = logout;
        self.isLoggedIn = isLoggedIn;
        self.rememberUser=false;
        self.clearPassword='';
        self.previousAttempted={ emailId : ''};
        self.init = init;
        self.msg="";
        self.isOpen = false;
        self.keypress = keypress;
        self.show=false;
        init();
        /**
         * @description Listens for athentication and sets instance current user from AuthService
         * @memberof AuthController
         */
        $rootScope.$on(AUTH_EVENTS.login, attempt);
        $rootScope.$on(AUTH_EVENTS.logout, attempt);
        $rootScope.$on(AUTH_EVENTS.notAuthenticated, attempt);
        $rootScope.$on(AUTH_EVENTS.notAuthorized, attempt);
        $rootScope.$on(AUTH_EVENTS.accountLocked, attempt);
        $rootScope.$on(AUTH_EVENTS.authenticated, attempt);
        
        
        
        function init(){
        	var date = new Date();
        	self.year = date.getFullYear();
        	self.currentUser = AuthService.currentUser;
        	if(self.currentUser.firstName == undefined){
        		self.currentUser.firstName = localStorage.getItem('currentUserFirstName', '');
            	self.currentUser.lastName = localStorage.getItem('currentUserLastName', '');
        	}
        	else{
        		localStorage.setItem('currentUserFirstName', self.currentUser.firstName);
            	localStorage.setItem('currentUserLastName', self.currentUser.lastName);
        	}
        	if(AuthService.rememberedUser && AuthService.rememberedUser.emailId){
        		self.credentials.emailId = AuthService.rememberedUser.emailId;
        		self.credentials.password = "";
       		 	//self.rememberUser = true;
        	}else{
        		//self.rememberUser = false;
        	}
        	console.log("self.rememberUser ::" + self.rememberUser);
 
        }
        
        /**
         * @description Pass through to AuthService
         * @memberof AuthController
         * @param {object} credentials Credentials populated by form ng-model directives
         */
        function login () {
        	AuthService.login(self.credentials);
        }
        
        function keypress($event){
    		var keyCode = $event.which || $event.keyCode;
            if (keyCode === 13) {
               self.login();
    		}

        }
        
        /**
         * @description Pass through to AuthService
         * @memberof AuthController
         */
        function logout () {
            AuthService.logout();
        }
        
        function lockAccount () {
            //AuthService.lock(self.credentials.emailId);
        }
        
        /**
         * @description Pass through to AuthService
         * @memberof AuthController
         * @returns {Boolean}
         */
        function isLoggedIn () {
            return AuthService.isAuthenticated();
        }
        
        function attempt (event, data) {
        	self.isOpen = false;
           self.msg = angular.isObject(data) ? self.messages[data.status] : '';
           console.log("data.status"+data.status);
            switch (data.status) {
                case 200:
                	self.loginAttemptCount = 0;
                	console.log("event.name::"+event.name);
                	if(event.name === AUTH_EVENTS.authenticated){
                		self.currentUser = AuthService.currentUser;
                	}
                    if(event.name === AUTH_EVENTS.logout){
                    	self.init();
                    	$state.go('login');
                    }
                    break;
                case 401:
                	if(self.previousAttempted.emailId ==='' || self.previousAttempted.emailId === self.credentials.emailId){
                		self.loginAttemptCount++;	
                		self.previousAttempted.emailId = self.credentials.emailId;
                	}else{
                		self.loginAttemptCount = 0;
                		self.previousAttempted.emailId = self.credentials.emailId;
                		self.commonErrorText='';
                	}
                	if(self.loginAttemptCount <= 4)
	            		{
	            		self.isOpen = true;
	            		}
                	break;
                case 403:
                case 422:
                case 500:
                	self.loginAttemptCount=0;
                	
                	self.isOpen = true;
                	break;
                case 423:
                    self.loginAttemptCount++;
                    self.commonErrorText = self.messages[data.status];
                   
                    self.isOpen = true;
                    return;
                default:
                    logger.error('Something odd happened. Please try again.');
            }
            if (self.loginAttemptCount > 4) {
                self.lockAccount();
            }
        }
        
    }
    
}());

