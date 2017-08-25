/**
 * @description
 * This module is the entry point of the application. It encapsulates all functionality in the
 * 'core' folder and makes the API available to the components. This module also provides
 * the foundation for the header & left panels & the mobile ready responsive layout.
 * @name layout
 * @ngdoc module
 * @param {service} ngTouch
 * @param {service} exception
 * @param {service} ngAnimate
 // jscs:ignore jsDoc
 * @param {service} ui.router
 * @param {service} ui.bootstrap// jscs:ignore jsDoc
 * @param {service} auth
 * @param {service} i18n
 * @param {service} jkuri.slimscroll// jscs:ignore jsDoc
 * @param {service} angular-loading-bar
 * @param {service} components
 * @param {service} widgets
 *
 *
 */
(function () {
    'use strict';
    
    angular.module('layout', [
               'ngAnimate',
               'ui.router',
               'ui.bootstrap',
               'auth' ,
               'components'//,  'widgets'
           ])
           .config(configLayout)
           .run(runNotifyListener)
           .run(runNoStateFound);
    /**
     * @description cfpLodingBarProvider adds a seamless progressbar for all XHR calls
     * html5Mode removes the # from the URLs
     * default fallback route is to a nice 404 error page
     *
     * routes -> layout.routes.js is for the default error routes
     * @name configLayout
     * @ngdoc config
     * @memberof layout
     * @param {service} $urlRouterProvider
     * @param {service} $locationProvider
     * @param {service} routes
     *
     */
    configLayout.$inject = ['$urlRouterProvider', '$locationProvider', '$stateProvider', 'routes'];
    function configLayout ($urlRouterProvider, $locationProvider, $stateProvider, routes) {
    	//console.log("inside configurer html5");
        $locationProvider.html5Mode(true);
        $urlRouterProvider.otherwise('/404');
        // State configuration can either be done centrally - inside layout.routers.js, or can be done on a per
        // view state basis inside the config for each of the view states.
        routes.forEach(function (route) {
            $stateProvider.state(route.name, route.config);
        });
        
    }
    
    /**
     * @description When the application has completed loading, this sets 'appLoaded' to true
     * on $rootScope.
     *
     * This is used specifically for turning off the splash screen, but can be used for any
     * purpose that needs to know about this event.
     * @ngdoc run
     * @memberof layout
     * @name runNotifyListener
     * @param {service} $rootScope
     * @listens $stateChangeSuccess
     *
     */
    function runNotifyListener ($rootScope) {
        // Make sure the splash screen get's turned off
        $rootScope.$on('$stateChangeSuccess', function (ev, to, toParams, from, fromParams) {
            $rootScope.appLoaded = true;
        });
    }
    
    /**
     * @description Must find and report "unfound states" rather than have UI router throw an error
     *
     * This simply tells the developer they need to correct their mistake - the User should never see this if
     * the app is constructed and tested properly
     * @ngdoc run
     * @memberof layout
     * @name runNoStateFound
     * @param {service} $rootScope
     * @param {service} $state
     * @listens $stateNotFound
     * @listens $stateChangeSuccess
     *
     */
    runNoStateFound.$inject = ['$rootScope', '$state'];
    function runNoStateFound ($rootScope, $state) {
        $rootScope.appLoaded = false;
        $rootScope.$on('$stateNotFound',
                       function (event, unfoundState, fromState, fromParams) {
                           event.preventDefault();
                           if (unfoundState.to !== 'noState') {
                               var upper = unfoundState.to.charAt(0).toUpperCase() + unfoundState.to.substr(1);
                               var lower = unfoundState.to.charAt(0).toLowerCase() + unfoundState.to.substr(1);
                               var foundUpper = ($state.get(upper)) ? 1 : '';
                               var foundLower = ($state.get(lower)) ? 1 : '';
                               $state.go('noState', {missingState: unfoundState.to, foundUpper: foundUpper, foundLower: foundLower});
                           }
                       });
    }
    
}());
