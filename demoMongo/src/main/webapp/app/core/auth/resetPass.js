/**
 * @ngdoc function
 * @name auth.controller:resetPassCtrl
 * @description
 * # resetPassCtrl
 * Controller of the auth
 */

(function(){
	angular.module('auth').controller('resetPassCtrl',resetPassCtrl);

	resetPassCtrl.$inject = ['$uibModal','AuthService', '$state', '$stateParams', '$timeout'];

	function resetPassCtrl ($uibModal,AuthService, $state, $stateParams, $timeout) {
			var self = this;  
			self.password="";
			self.confirmPassword="";
			self.passwordToken=$stateParams.userId;
			self.user={};
			self.resetError="";
			self.resetPass=resetPass;
			self.keypress = keypress;
			self.init = init;
			self.openAlertModal = openAlertModal;
			var modalInstance;
			function init() {
				AuthService.checkLogin(self.passwordToken).then(function(resp){
					self.user = resp.data;
				},function(resp){
					$state.go('Not Found');
				});
				
			}
			
			function resetPass(){
				self.user.password = self.password;
				AuthService.resetPassword(self.user).then(function(response){
					self.openAlertModal();
				},function(response){
					self.resetError = "Reset Password Failed";
				});
			}
			function keypress(event){
	    		var keyCode = event.which || event.keyCode;
				if (keyCode === 13) {
					self.resetPass();
				}
	    	}
			function validateForm(){
				   if(self.password != self.confirmPassword){
					   self.resetError = "Password Mismatch";
					   var elm = document.getElementById("confirmPassword");
						$timeout(function() {
							angular.element(elm).triggerHandler('click');
					    });
					   return false;
				   }
				   return true;
			   }
		
			   function openAlertModal() {

					modalInstance = $uibModal.open({
						animation : true,
						templateUrl : 'app/core/auth/resetPasswordModal.html',
						controller : 'resetPassModalCtrl',					
						size : 'rpmc',
						windowClass: 'alert-modal-window',
						backdrop : 'static',
						keyboard : false,
						resolve : {
							newPass : function(){
								return self.confirmPassword;
							},
							username : function(){
								return self.user.username;
							}
							
						}
					});
					
				}
			   
		   }
	
}());


