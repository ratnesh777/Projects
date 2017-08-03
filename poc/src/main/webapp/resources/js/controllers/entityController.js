ipcApp.controller('entityController',['$scope','$location','$http','entityService', function($scope,$location,$http,entityService){
  
    $scope.author="Ratnesh";
   $scope.result="default";
     console.log("Inside entity controller");
    
    //simple way of calling rest service directly from controller 
    //Not recomended
       /* $http.get('http://localhost:8080/poc/api')
            .success(function(response){
                $scope.result=response;
                console.log("result :: " + $scope.result);
                })
            .error(function(data,status){
                console.log(data);
            })
        */
  
    
    //calling rest service through services.js 
    
      /*  entityService.getEntityData().success(function (response) {
                    $scope.result=response;
                console.log(" response in controller:: "+  response);
                    })
                    .error(function(data, status) {
                    console.log(status);
                });
    
    */
    
    
     //standard way of calling rest service through services.js so that same can be reused across multiple controllers        
      entityService.getEntityData().then(
                function(response) {
				        $scope.result = response;
						  }, function(error){
                              $scope.error = error ;
                                console.log(" error:: "+  $scope.error.data + " status :: " + error.status);
						}
      		);
    
   
     }
    ]
);
