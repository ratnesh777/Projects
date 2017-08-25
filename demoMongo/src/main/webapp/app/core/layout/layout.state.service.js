(function () {
    angular.module('layout')
        .factory('layoutState', layoutState);
    
    /**
     * @description
     * State here is as in machine-state of a state machine. This service listens to the
     * layout.settings and records any changes.
     *
     * Currently, this only stores settings in localStorage, but should be sent/retrieved to/from the
     * server to be stored on a per user basis. This allows the user to log in elsewhere and restore settings.
     *
     * @ngdoc service
     * @name layoutState
     * @memberof layout
     * @param {constant} layout.settings
     * @param {service} $rootScope
     *
     */
    layoutState.$inject = ['layout.settings', '$rootScope','AuthService','USER_ROLES'];
    function layoutState(defaultSettings, $rootScope,AuthService,USER_ROLES) {
        var self = this;
        var settings = init();
        $rootScope.$watchCollection('layout.settings', save);

        return {
            get: get,
            getAll: getAll,
            set:set,
            save:save,
            defaultState : defaultState
        };
        
        /**
         * @description Returns the default state based on the user role
         * @name defaultState
         * @memberof layoutState
         */
        
        function defaultState(){
        	console.log(" AuthService.currentUser.role ::"+ AuthService.currentUser.role);
        	if ( USER_ROLES.operationAdmin === Number(AuthService.currentUser.role)) {
                return 'manageUser';
            }else if ( USER_ROLES.operationUser === Number(AuthService.currentUser.role)) {
                return 'manageUser';
            }
        	/*else if ( USER_ROLES.manufacturingUser === Number(AuthService.currentUser.role)) {
                return 'manageTurret';
            } else if ( USER_ROLES.customerAdmin === Number(AuthService.currentUser.role)) {
                return 'manageUser';
            } */else if ( USER_ROLES.customerUser === Number(AuthService.currentUser.role)) {
                return 'activateTurret';
            } else{
            	AuthService.isLocked = true;
            	return 'login';
            }
        	
        }
        
        
        /**
         * @description Stores the layout.settings in localStorage in key 'viewState'
         * @name save
         * @memberof layoutState
         * @param {mixed} newState
         */
        function save(newState) {
            if (typeof newState !== 'undefined') {
                try {
                    localStorage.setItem('viewState', JSON.stringify(newState));
                } catch (e) {
                    localStorage.clear();
                }
            }
        }
        /**
         * @description Changes the local version of a setting
         * @name set
         * @memberof layoutState
         * @param {string} key
         * @param {mixed} value
         */
        function set(key, value) {
            settings[key] = value;
        }
        /**
         * @description Gets a value for key from the local version of settings
         * @name get
         * @memberof layoutState
         * @param {string} key
         * @return {mixed}
         */
        function get(key) {
            return this[key];
        }
        /**
         * @description Returns the entire settings collection
         * @name getAll
         * @memberof layoutState
         * @return {object}
         */
        function getAll() {
            return settings;
        }
        /**
         * @description Sets the initial state. This is either pulled from local storage or set
         * from the defaults.
         *
         * This is where one would retrieve these settings from the server if they exist.
         * @name init
         * @memberof layoutState
         * @return {object}
         */
        function init() {
            var restoredState = JSON.parse(localStorage.getItem('viewState'));
            var defaultState = defaultSettings;
            if (restoredState) {
                return restoredState;
            }
            return defaultState;
        }
    }
}());
