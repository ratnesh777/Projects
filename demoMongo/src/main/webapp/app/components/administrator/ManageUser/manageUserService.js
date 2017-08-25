(function () {
    "use strict";
    angular.module('manageUser')
        .service(
            'manageUserService', ['restService', 'urlConstants', function (restService, urlConstants) {
                return {
                    getUsersFromAPI: function (currentPage, selectedPageSize, sortParam, sortDirection) {
                    	 return restService.get(urlConstants.userUrl +  "?page=" + (currentPage - 1) + "&size=" + selectedPageSize + "&sortParam=" + sortParam + "&sortDirection=" + sortDirection);
                     // return restService.get(urlConstants.userUrl + "?page=" + (currentPage - 1) + "&size=" + selectedPageSize + "&sortParam=" + sortParam + "&sortDirection=" + sortDirection );
                    },

                    createUser : function (user) {
                        return restService.add(urlConstants.userUrl, user);
                    }
                }
            }]);
    
}());
