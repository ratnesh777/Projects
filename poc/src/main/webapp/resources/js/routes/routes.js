/*ipcApp.config(function ($routeProvider) {
    
    $routeProvider
    
    .when('/', {
        templateUrl: 'pages/main.html',
        controller: 'mainController'
    })
    
    .when('/login', {
        templateUrl: 'pages/login.html',
        controller: 'loginController'
    })
    
     .when('/logout', {
        templateUrl: 'pages/logout.html',
        controller: 'logoutController'
    })
    .when('/menu', {
        templateUrl: 'pages/menu.html',
        controller: 'navController'
    })
    .when('/entities',{
    
        templateUrl : 'pages/entities.html',
        controller : 'entityController'
    })
    .when('/postemp',{
    
        templateUrl : 'pages/createemp.html',
        controller : 'empController'
    })
});*/


ipcApp.config(function ($stateProvider, $urlRouterProvider) {
    
  // When the state is not known, send to the 'main' page
  $urlRouterProvider.otherwise("/main")
    
   $stateProvider
			.state('main', {
				url: "/main",
				templateUrl: "pages/main.html",
                controller : 'mainController'
			})
			.state('login', {
				url: "/login",
				templateUrl: "pages/login.html",
                controller : 'loginController'
			})
			
       .state('logout', {
				url: "/logout",
				templateUrl: "pages/logout.html",
                controller : 'logoutController'
		})
         .state('entities', {
				url: "/entities",
				templateUrl: "pages/entities.html",
                controller : 'entityController'
			})
   
        .state('postemp', {
				url: "/postemp",
				templateUrl: "pages/createEmp.html",
                controller : 'empController'
			})
        .state('menu', {
				url: "/menu",
				templateUrl: "pages/menu.html",
				controller : 'navController'
		})

});