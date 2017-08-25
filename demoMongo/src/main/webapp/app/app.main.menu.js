(function () {
    "use strict";
    /**
     * @summary
     * Left panel (main) menu items
     *
     * @description
     * The menu is divided into different named sections and each section is composed of a number of named menu groups
     * that are presented in an accordion drop-down. The drop-down title bar should contain an icon class name so the
     * minimized menu will have some reference the menu list.
     *
     * Single Item Menu Structure
     *
     * {
     *   title: 'Authentication',
     *   role: 0,
     *   content: [{
     *       name: 'Log In',
     *       iconClass: 'fa fa-lock',
     *       state: 'Home'
     *   }]
     * }
     *
     *
     *
     * @name app.menu
     * @memberof app
     * @ngdoc constant
     */

    angular.module('app')
        .constant(
            'app.menu',
            [{
            	title: 'Administrator',
            	role: [1,2],
                content: [ {
                         name: 'Manage Users',
                         open: false,
                         state: 'manageUser'
                     },
                     {
                         name: 'Manage Customers',
                         open: false,
                         state: 'manageCompany'
                     }
                     
                ]	
            },
                {
            	title: 'Customer User',
            	role: 3,
                content: [{
                    name: 'Manage Users',
                    open: false,
                    state: 'manageUser'
                }
                ]	
            }
            ]);
}());

