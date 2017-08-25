(function () {
    "use strict";
    angular.module('auth')
           .service('AuthService', AuthService);
    /**
     * @description
     * This service provides authentication capabilities. If you need access to the user object
     * or authentication in your component, use this service.
     * @ngdoc service
     * @name AuthService
     * @memberof auth
     * @param {service} $rootScope
     * @param {service} restService
     * @param {service} $timeout
     * @param {constant} AUTH_EVENTS
     * @param {constant} TIMEOUT_EVENTS
     *
     * @prop {object} guestUser default user with no privledges
     * @prop {object} endpoints centralize these URLS
     * @prop {object} currentUser logged in user
     *
     */
    AuthService.$inject = ['$rootScope', 'restService',  'AUTH_EVENTS', 'urlConstants','$location','$window'];
    function AuthService ($rootScope, restService,  AUTH_EVENTS, urlConstants,$location,$window) {
        var self = this;
		        this.endpoints = {
			login : urlConstants.login,
			logout : urlConstants.logout,
			retrieveLoggedInUser : urlConstants.retrieveLoggedInUser,
			sendEmail : urlConstants.sendEmail,
			resetPass : urlConstants.resetPass
			
		};
        this.currentUser = {};
        this.rememberedUser={};
        this.credentialValue = null;
        this.login = login;
        this.logout = logout;
        this.isLocked = true;
        this.currentUser = {};
        this.deleteCookies = deleteCookies;
        this.isAuthenticated = isAuthenticated;
	    this.sendEmail = sendEmail;
		this.checkAuthentication = checkAuthentication;
        this.retrieveLogin = retrieveLogin;
        console.log("isLocked :: "+ this.isLocked);
        
        if(!self.isLocked){
        	self.retrieveLogin().then(retrievedSuccess,retrievedFailure);
        } else{
        	var storedUser = localStorage.getItem('rememberedUser');
        	if(storedUser){
        		self.rememberedUser = JSON.parse(localStorage.getItem('rememberedUser'));
        	}
    	}
        
/*        $rootScope.$on(AUTH_EVENTS.checkAuthentication, function () {
            self.checkAuthentication();
        });*/  
        
        function checkAuthentication(){
        	if (!self.isLocked && self.currentUser.username !== localStorage.getItem('portalCurrentUser')) {
        		$location.path('/logout').replace();
        		$window.location.reload();
	    	}
        }
      
        function retrievedFailure(){
        	self.locked=true;
        	self.currentUser={};
        }
        
        /**
         * @description Sends credentials to server, records user, alerts application
         * @memberof AuthService
         * @requires restService
         * @param {object} credentials
         */
        function login (credentials, rememberMeFlag) {
        	self.rememberMe = rememberMeFlag;
        	 if(rememberMeFlag){
                 self.credentialValue=angular.copy(credentials);
                 self.credentialValue.password = "";
                 localStorage.setItem('rememberedUser', JSON.stringify(self.credentialValue));
	            }
	           else{
	        	   localStorage.setItem('rememberedUser', '');
	           }
             
			return restService
			.add(self.endpoints.login, credentials)
			.then(recordUser)
			.then(success, removeUser);
			}
        
        
        /**
         * @description Sets currentUser and localStorage property
         * @memberof AuthService
         * @requires localStorage
         * @param {response} res response
         * @returns {response} $http response
         */
        function recordUser (res) {
            self.currentUser = res.data;
            console.log(" self.currentUser.role::"+ self.currentUser.role);
            self.currentUser.status = 'green';
            return res;
        }
        
        /**
         * @description Sets currentUser and localStorage property
         * @memberof AuthService
         * @requires localStorage
         * @event {broadcast} AUTH_EVENTS.authenticate
         * @fires AUTH_EVENTS.authenticate
         * @param {response} res response
         * @returns {response} $http response
         */
        function success (res) {
            console.log(" res.data.role::"+ res.data.role);
            if (angular.isObject(res.data)) {
                self.isLocked = false;
                $rootScope.$broadcast(AUTH_EVENTS.login, res);
                localStorage.setItem('portalCurrentUser', res.data.email);
                localStorage.setItem('portalCurrentUserRole', res.data.role);//TODO will update after spring-security integration
                
            } else {
                self.isLocked = true;
                $rootScope.$broadcast(AUTH_EVENTS.logout, res);
                localStorage.setItem('portalCurrentUser', '');
                localStorage.setItem('portalCurrentUserRole', '');//TODO will update after spring-security integration
                
            }
        }
        

        
        /**
         * @description Unsets currentUser and removes localStorage property
         * @memberof AuthService
         * @requires localStorage
         */
        function removeUser (res) {
        	self.currentUser = {};
        	var storedUser = localStorage.getItem('rememberedUser');
        	if(storedUser){
        		self.rememberedUser = JSON.parse(localStorage.getItem('rememberedUser'));
        	}
        	return res;
        }
        
       
        /**
         * @description Logs user out, removes user, alert application
         * @memberof AuthService
         * @requires restService
         * @returns {promise} $http promise
         */
        function logout () {
            self.isLocked = true;
            $rootScope.logoutClicked=true;
            var storedUser = localStorage.getItem('rememberedUser');
            return restService.get(self.endpoints.logout).then(removeUser).then(success, removeUser);
        }
    
       
        function deleteCookies(){
        	restService
            .get(self.endpoints.logout).then(function(resp){
            	localStorage.setItem('portalCurrentUser', '');
            })
        }
        
        
        /**
         * @description Validates that a currentUser is recorded
         * @memberof AuthService
         * @requires localStorage
         */
        function isAuthenticated () {
            var result =  false;
            if (!self.currentUser) { return false; }
            return self.currentUser.role > 0;
        }
        
         function retrieveLogin(){
        	return restService.get(self.endpoints.retrieveLoggedInUser);
        }
        
         
        function resetPassword(user){
        	return restService.add(self.endpoints.resetPass,user);
        }
        

        function sendEmail (username) {
          return restService
                .add(self.endpoints.sendEmail,username);
        }
        
       
    }
    
}());

