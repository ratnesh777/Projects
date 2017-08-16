(function () {
    'use strict';
    var configPrefixTitle = {
        appErrorPrefix: '[Application Error] ',
        appTitle: 'IPC [change in logger/toastr.config.js]'
    };
    
    angular.module('exception')
           /**
            * @description defaults for toastr message prefix and header
            * @ngdoc value
            * @memberof exception
            * @prop {object} config.appErrorPrefix [Application Error]
            * @prop {object} config.appTitle IPC [change in logger/toastr.config.js]
            */
           .value('config', configPrefixTitle)
           .config(toastrConfiguration)
           .config(configure);
    /**
     * @description Sets resonable defaults for the toastr messages
     * @ngdoc config
     * @name toastrConfig
     * @memberof exception
     *
     * @example
     *  Settings are: {
         autoDismiss: false,
         containerId: 'toast-container',
         maxOpened: 0,
         newestOnTop: true,
         positionClass: 'toast-bottom-right',
         preventDuplicates: false,
         preventOpenDuplicates: true,
         target: 'body'
         }
     *
     * @param {service} toastrConfig
     */
    toastrConfiguration.$inject = ['toastrConfig'];
    function toastrConfiguration (toastrConfig) {
        angular.extend(toastrConfig, {
            // Set timeOut to 5000 for a 5 second timeout - zero means no auto close
            timeOut: 0,
            // Set extendedTimeOut to 1000 for a 1 second auto close timeout after mouse hover - zero means never auto close
            extendedTimeOut: 0,
            closeButton: true,
            autoDismiss: false,
            containerId: 'toast-container',
            maxOpened: 0,
            newestOnTop: true,
            positionClass: 'toast-top-center',
            preventDuplicates: false,
            preventOpenDuplicates: true,
            target: 'body'
        });
    }
    
    /**
     * @description Enable debugging and exception messages to be shown as toasts
     * @memberof exception
     * @ngdoc config
     * @name configure
     * @param {service} $logProvider
     * @param {service} exceptionHandlerProvider
     */
    configure.$inject = ['$logProvider', 'exceptionHandlerProvider'];
    function configure ($logProvider, exceptionHandlerProvider) {
        if ($logProvider.debugEnabled) {
            $logProvider.debugEnabled(true);
        }
        exceptionHandlerProvider.configure(configPrefixTitle.appErrorPrefix);
    }
    
})();
