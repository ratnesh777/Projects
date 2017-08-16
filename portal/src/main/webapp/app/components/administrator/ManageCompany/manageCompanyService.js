(function () {
    "use strict";
    angular.module('manageCompany')
        .service(
            'manageCompanyService', ['restService', 'urlConstants', function (restService, urlConstants) {
                return {
                    getAllCompanies: function () {
                    	 return restService.get(urlConstants.companyUrl);
                    }
                }
            }]);
    
}());
