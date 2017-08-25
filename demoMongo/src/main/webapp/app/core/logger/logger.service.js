(function() {
    'use strict';

    angular
        .module('logger')
        .factory('loggerService', loggerService);
    
    /**
     * @description Provides BootStrap colored toast messages
     * @name loggerService
     * @ngdoc service
     * @memberof logger
     * @param {object} $log
     * @param {object} toastr
     */
    loggerService.$inject = ['$log', 'toastr'];
    function loggerService($log, toastr) {
        var service = {
            showToasts: true,

            error   : error,
            info    : info,
            success : success,
            warning : warning,

            // straight to console; bypass toastr
            log     : $log.log
        };

        return service;
        /////////////////////
        /**
         * @description Displays RED toast
         * @param {string} message
         * @param {mixed} data
         * @param {string} title
         */
        function error(message, data, title) {
            toastr.error(message, title);
            $log.error('Error: ' + message, data);
        }
        /**
         * @description Displays BLUE toast
         * @param {string} message
         * @param {mixed} data
         * @param {string} title
         */
        function info(message, data, title) {
            toastr.info(message, title);
            $log.info('Info: ' + message, data);
        }
        /**
         * @description Displays GREEN toast
         * @param {string} message
         * @param {mixed} data
         * @param {string} title
         */
        function success(message, data, title) {
            toastr.success(message, title);
            $log.info('Success: ' + message, data);
        }
        /**
         * @description Displays AMBER toast
         * @param {string} message
         * @param {mixed} data
         * @param {string} title
         */
        function warning(message, data, title) {
            toastr.warning(message, title);
            $log.warn('Warning: ' + message, data);
        }
    }
}());
