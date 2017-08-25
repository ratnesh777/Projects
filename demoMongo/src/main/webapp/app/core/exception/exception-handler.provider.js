// Include in index.html so that app level exceptions are handled.
// Exclude from testRunner.html which should run exactly what it wants to run
(function () {
    'use strict';
    
    angular
        .module('exception')
        .provider('exceptionHandler', exceptionHandlerProvider)
        .config(configHandler);
    
    /**
     * @description Configure by setting an optional string value for appErrorPrefix.
     * Accessible via config.appErrorPrefix (via config value).
     * @memberof exception
     * @prop {object} config.appErrorPrefix All errors will contain this prefix if set
     */
    function exceptionHandlerProvider () {
        /* jshint validthis:true */
        this.config = {
            appErrorPrefix: undefined
        };
        
        this.configure = function (appErrorPrefix) {
            this.config.appErrorPrefix = appErrorPrefix;
        };
        
        this.$get = function () {
            return {config: this.config};
        };
    }
    
    /**
     * @description Configures the exception handling
     * @ngdoc config
     * @name configHandler
     * @memberof exception
     * @param  {Object} $provide
     */
    configHandler.$inject = ['$provide'];
    function configHandler ($provide) {
        $provide.decorator('$exceptionHandler', extendExceptionHandler);
    }
    
    /**
     * @description Extend the $exceptionHandler service to also display a toast.
     * Could add the error to a service's collection,
     * add errors to $rootScope, log errors to remote web server,
     * or log locally. Or throw hard.
     *
     *
     * @example
     *     throw { message: 'error message we added' };
     * @memberof exception
     * @name extendExceptionHandler
     * @param  {Object} $delegate
     * @param  {Object} exceptionHandler
     * @param  {Object} $injector
     * @return {Function} the decorated $exceptionHandler service
     */
    extendExceptionHandler.$inject = ['$delegate', 'exceptionHandler', '$injector'];
    function extendExceptionHandler ($delegate, exceptionHandler, $injector) {
        return function (exception, cause) {
            var appErrorPrefix = exceptionHandler.config.appErrorPrefix || '';
            var errorData = {exception: exception, cause: cause};
            var msg = exception.message;
            if (msg === undefined) {
                msg = JSON.stringify(exception);
            }
            msg = appErrorPrefix + msg;
            $delegate(exception, cause);
            var logger = $injector.get('loggerService');
            //logger.error(msg, errorData, exceptionHandler.config.appTitle);
        };
    }
})();
