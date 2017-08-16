(function() {
    'use strict';

    angular
        .module('exception')
        .factory('exceptionService', exceptionService);
    
    /**
     * @description Exception Handler service
     *
     * @name exceptionService
     * @memberof exception
     * @ngdoc service
     * @param {service} $q
     * @param {service} loggerService
     *
     */
    exceptionService.$inject = ['$q', 'loggerService'];
    function exceptionService($q, loggerService) {
        return {
            catcher: catcher
        };
        /**
         * @description This service implementation uses the loggerService to
         * record or notify of the exception.
         * @name catcher
         * @memberof exception
         * @param {mixed} message
         * @returns {Function} Service
         */
        function catcher(message) {
            return function(e) {
                var thrownDescription;
                var newMessage;
                if (e.data && e.data.description) {
                    thrownDescription = '\n' + e.data.description;
                    newMessage = message + thrownDescription;
                }
                e.data.description = newMessage;
                loggerService.error(newMessage);
                return $q.reject(e);
            };
        }
    }
})();
