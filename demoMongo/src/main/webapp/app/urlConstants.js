(function () {
    "use strict";
    
    angular.module('app')
        .service("urlConstants", urlConstants);
    
    urlConstants.$inject = ['$location'];
    function urlConstants ($location) {
        var base = $location.protocol() + "://" + $location.host() + ":" + $location.port();
        var contextVal = base + "/demoMongo";
        return {
        	contextVal: contextVal,
            login: contextVal + '/api/login/authenticate',
            logout: contextVal + '/api//logout',
            userUrl: contextVal + "/api/user",
            companyUrl: contextVal + "/api/company",
            resetPass : contextVal+"/api/login/resetPassword",
            sendEmail: contextVal + '/api/login/sendEmail',
        };
    }
    
}());
