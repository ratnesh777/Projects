(function () {
    "use strict";
    /**
     * @description
     * Application wide settings
     *
     * @see src/client/app/core/layout/layout.settings for viewport settings
     *
     * @name app.settings
     * @property {string} preferredLanguage en,fr,es,etc
     * @ngdoc constant
     */
    angular.module('app')
        .constant('app.settings',
        {
            preferredLanguage: 'en',
        });

}());

