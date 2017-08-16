/**
* @description Generic data access service
* 
 * @name restService
* @ngdoc module
* 
 */

(function () {
    'use strict';

    angular
        .module('restService', [])
        .factory('restService', restService)
                        .factory('httpInterceptor', function ($q, $rootScope, $log) {
                                var numLoadings = 0;
                
                    return {
                        request: function (config) {
                        	$rootScope.$broadcast('check-authentication');
                        	 numLoadings++;
                        	if( !$rootScope.showLoader){
                            $rootScope.$broadcast("loader_show");
                        	}
                            return config || $q.when(config);
                
                        },
                        response: function (response) {
                                 if ((--numLoadings) === 0) {
                                $rootScope.$broadcast("loader_hide");
                                 }
                                
                            return response || $q.when(response);
                        },
                        responseError: function (response) {
                        	if(response.status == 401 && response.config.url.indexOf("login/authenticate") === -1){
                  			 	 $rootScope.$broadcast("loader_hide");
                             	 $rootScope.$broadcast("session-timeout");
                             	 return $q.reject(response);
                       		}
                        	   if (!(--numLoadings)) {
                                $rootScope.$broadcast("loader_hide");
                                }
                        	if(response.status == -1){
                        		$rootScope.$broadcast("connect_error");
                        	}
                            return $q.reject(response);
                        }
                    };
                   })

       .config(['$httpProvider', function($httpProvider){
                 $httpProvider.interceptors.push('httpInterceptor');
                $httpProvider.defaults.headers.put = {'Content-Type':'application/json'};
                
                // initialize get if not there
            if (!$httpProvider.defaults.headers.get) {
                $httpProvider.defaults.headers.get = {};    
            }    

            // Answer edited to include suggestions from comments
            // because previous version of code introduced browser-related
                                                // errors

            // disable IE ajax request caching
            $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
            // extra
            $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
            $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
                
        }]);
    
    /**
                * @description Provides REST API endpoint access
                * 
                 * @name restService
                * @ngdoc service
                * @memberof restService
                * 
                 * @param {service}
                *            $http
                */
    restService.$inject = ['$http'];
    function restService($http) {
        /* jshint -W040 */
        var self = this;

        return {
            get:get,
            add:add,
            update:update,
            patch: patch,
            restore:restore,
            remove:remove,
            removeAll: removeAll
        };
        /**
                                * @description HTTP GET method
                                * 
                                 * @function get
                                * @memberof restService
                                * 
                                 * @param {string}
                                *            resource URL to resource
                                * @returns {promise} $http promise
                                */
        function get(resource) {
            return $http.get(resource);
        }
        /**
                                * @description HTTP POST method
                                * 
                                 * @function post
                                * @memberof restService
                                * 
                                 * @param {string}
                                *            resource URL to resource
                                * @param {mixed}
                                *            data data to post
                                * @returns {promise} $http promise
                                */
        function add(resource, data) {
            return $http.post(resource, data);
        }
        /**
                                * @description HTTP PUT method
                                * 
                                 * @function put
                                * @memberof restService
                                * 
                                 * @param {string}
                                *            resource URL to resource
                                * @param {int}
                                *            id record to update
                                * @param {mixed}
                                *            data data to update (put)
                                * @returns {promise} $http promise
                                */
        function update(resource, id, data) {
            return $http.put(resource + '/' + id, data);
        }
        
        /**
                                * @description HTTP PUT method
                                * 
                                 * @function put
                                * @memberof restService
                                * 
                                 * @param {string}
                                *            resource URL to resource
                                * @param {mixed}
                                *            data data to update (put)
                                * @returns {promise} $http promise
                                */
        function update(resource,data) {
            return $http.put(resource, data);
        }
       
        function patch(resource, id, data) {
        	if(id !== ""){
        		return $http.patch(resource + '/' + id, data);
        	}else{
        		return $http.patch(resource, data);
        	}
            
        }
       
        function restore(url) {
            return $http.put(url);
        }
        /**
                                * @description HTTP DELETE method
                                * 
                                 * @function remove
                                * @memberof restService
                                * 
                                 * @param {string}
                                *            resource URL to resource
                                * @param {int}
                                *            id record to update
                                * @returns {promise} $http promise
                                */
        function remove(resource, id) {
            return $http.delete(resource + '/' + id);
        }
        
        function removeAll(url){
                return $http.delete(url);
        }
    }
})();
