(function () {

    angular.module('layout')
           /**
            * @description default settings for the viewPort
            * @name layout.settings
            * @memberof layout
            * @ngdoc constant
            * @prop {Boolean} isFullPage removes headers
            * @prop {Boolean} hideLeftMenu removes left Menu
            * @prop {Boolean} boxed  Offsets page with margins
            * @prop {Boolean} horizontalMenu Gives icon menu across top
            * @prop {Boolean} onCanvas false: hides left panel completely on small screens
            * @prop {Boolean} fixedHeader true: will not scroll off screen
            * @prop {Boolean} fixedSidebar true: does not scroll with page
            * @prop {Boolean} fullScreenMode different than full page - same as f11 on keyboard (removes browser chrome)
            * @prop {Boolean} navCollapsedMin captures state of collapsed menu
            * @prop {Boolean} rightSideToggle captures state of right panel
            * @prop {Boolean} themeID unused
            * @prop {string} navbarHeaderColor 'bg-primary', 'bg-success', 'bg-warning', etc.
            * @prop {string} logo 'bg-primary', 'bg-success', 'bg-warning', etc.
            * @prop {string} logoSrc url - '/images/-logo.png'
            * @prop {string} asideColor 'bg-dark', 'bg-light'
            * @prop {object} color.primary "#248AAF"
            * @prop {object} color.success "#3CBC8D"
            * @prop {object} color.info "#29B7D3"
            * @prop {object} color.purple "#7266ba"
            * @prop {object} color.warning "#FAC552"
            * @prop {object} color.danger "#E9422E"
            *
             */
           .constant('layout.settings',
        {
            isFullPage: false, //
            hideLeftMenu:false,//
            boxed: false, //
            horizontalMenu: false, //
            onCanvas: false, //
            fixedHeader: true, //
            fixedSidebar: true, //
            fullScreenMode: false, //
            navCollapsedMin: false, //
            navbarHeaderColor: 'bg-primary',
            logoSrc: '/demoMongo/app/img/company_logo.png',
            sideNavColor: 'bg-primary',
            color: {
                primary: "#248AAF",
                success: "#3CBC8D",
                info: "#29B7D3",
                purple: "#7266ba",
                warning: "#FAC552",
                danger: "#E9422E"
            }
        });

}());

