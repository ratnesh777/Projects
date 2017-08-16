(function () {
    "use stict";
    /**
     * @description Provides authentication services
     * @name auth
     * @ngdoc module
     * @param {service} restService
     * @param {service} ui.router
     * @param {service} grecaptcha
     *
     */
    angular.module('auth', ['restService', 'ui.router'/*, 'grecaptcha'*/])
           .config(configInterceptor)
//           .config(configRecaptcha)
           /**
            * @memberof auth
            * @private
            */
           .constant('RECAPTCHA_SITE_KEY', 'REPLACE WITH KEY')
           /**
            * @memberof auth
            * @ngdoc constant
            * @name AUTH_EVENTS
            * @prop {object} AUTH_EVENTS.authenticate Sends type (in, out) to indicate direction of authentication
            * @prop {object} AUTH_EVENTS.failed
            * @prop {object} AUTH_EVENTS.notAuthorized
            * @prop {object} AUTH_EVENTS.notAuthenticated
            */
           .constant('AUTH_EVENTS', {
               login: 'auth-login',
               logout: 'auth-logout',
               failed: 'auth-failed',
               notAuthorized: 'auth-not-authorized',
               notAuthenticated: 'auth-not-authenticated',
               accountLocked: 'auth-account-locked',
               authenticated:'auth-authorized',
               checkAuthentication : 'check-authentication'
           })
           .factory('AuthInterceptor', AuthInterceptor);
    /**
     * @description Injects AuthInterceptor into HTTP request stream
     * @name configInterceptor
     * @ngdoc config
     * @memberof auth
     * @param {service} $httpProvider
     */
    configInterceptor.$inject = ['$httpProvider'];
    function configInterceptor ($httpProvider) {
        $httpProvider.defaults.useXDomain = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];
        $httpProvider.interceptors.push(
            [
                '$injector',
                function ($injector) {
                    return $injector.get('AuthInterceptor');
                }
            ]);
    }
    
    /**
     * @description Injects site key into gRecaptcha service
     * @name configRecaptcha
     * @ngdoc config
     * @memberof auth
     * @param {service} grecaptchaProvider
     * @param {constant} RECAPTCHA_SITE_KEY
     */
/*
    configRecaptcha.$inject = ['grecaptchaProvider', 'RECAPTCHA_SITE_KEY'];
    function configRecaptcha (grecaptchaProvider, RECAPTCHA_SITE_KEY) {
        grecaptchaProvider.setParameters(
            {
                sitekey: RECAPTCHA_SITE_KEY
            });
        
    }
*/
    
    /**
     * @description Injects site key into gRecaptcha service
     * @name AuthInterceptor
     * @ngdoc service
     * @memberof auth
     * @param {service} $rootScope
     * @param {service} $q
     * @param {constant} AUTH_EVENTS
     */
    AuthInterceptor.$inject = ['$rootScope', '$q', 'AUTH_EVENTS'];
    function AuthInterceptor ($rootScope, $q, AUTH_EVENTS) {
        return {
            responseError: function (response) {
                $rootScope.$broadcast(
                    {
                        401: AUTH_EVENTS.notAuthenticated,
                        403: AUTH_EVENTS.notAuthorized,
                        422: AUTH_EVENTS.notAuthenticated,
                        423: AUTH_EVENTS.accountLocked
                    }[response.status], response);
                return $q.reject(response);
            }
        };
    }
}());

