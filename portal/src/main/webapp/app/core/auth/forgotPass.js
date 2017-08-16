/**
 * @ngdoc function
 * @name forgotPassCtrl
 * @description
 * # forgotPassCtrl
 * Controller of the auth
 */
(function () {
    angular.module('auth')
        .controller('forgotPassCtrl', forgotPassCtrl);
    
    forgotPassCtrl.$inject = ['$rootScope', 'AuthService', 'AUTH_EVENTS','$timeout'];
    function forgotPassCtrl($rootScope, AuthService, AUTH_EVENTS,$timeout){
    	
    	var self = this;
    	this.validateForm  = validateForm;
    	this.username='';
    	this.sendMail = sendMail;
    	this.errorMessage='';
    	this.message='';
    	this.notification=false;
    	this.errorNotification=false;
    	this.keypress=keypress;
    	this.clearMessages=clearMessages;
    	
    	
    	function sendMail(){
    		if(validateForm()){
    			AuthService.sendEmail(self.username).then(successHandler,failureHandler);
    		}
    	}
    	
    	function successHandler(response){
    		self.notification = true;
    		self.errorNotification=false;
    	}
    	
    	function failureHandler(response){
    		if(response.status==423){
    			self.notification = false;
    			self.errorNotification=false;
    			self.message = "User is locked! Please contact your Admin";
    		}else if(response.data.reason.match("Invalid User")){
    			self.notification = true;
    			self.errorNotification=false;
    		}else {
    			self.notification = false;
    			self.errorNotification=true;
    		}
    	}
    	
    	function clearMessages(){
    		self.errorMessage = "";	
    		self.message = "";
    		self.notification = false;
    	}
    	
    	function validateForm(){
    		var elm = {};
			if (self.resetForm.username.$error.email || self.resetForm.username.$invalid) {
				self.errorMessage = "Not a valid email";
				elm = document.getElementById("user");
				$timeout(function() {
					angular.element(elm).triggerHandler('click');
			    });	
				return false;
			} else if(self.username===undefined || self.username===""){			
				self.errorMessage = "Please provide email!!";
				elm = document.getElementById("user");
				$timeout(function() {
					angular.element(elm).triggerHandler('click');
			    });	
				return false;
			}
			return true;
		}
    	
    	function keypress(event){
    		var keyCode = event.which || event.keyCode;
			if (keyCode === 13) {
				self.sendMail();
			}
    	}
    	
    	
    }
}());
