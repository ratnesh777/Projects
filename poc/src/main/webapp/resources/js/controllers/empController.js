//ipcApp.controller('empController',['$scope','$location','$http','entityService', function($scope,$location,$http,entityService){

  ipcApp.controller('empController',['$scope','$http','entityService','$state', function($scope,$http,entityService,$state){
    console.log("hello");
      $scope.author="Ratnesh";
     console.log("Inside emp controller");
    
   
      $scope.submit = function(){
    	  $scope.createEmpError='';
          $scope.emp ={
            name: $scope.name,
            sex: $scope.sex,  
        };
      //  console.log($scope.emp);
          
       
    //calling rest service through entityservice.js 
    
      /*  entityService.postEntityData($scope.emp).success(function (response) {
                    $scope.result=response;
                    console.log(" Response :::: id - "+  response.id+ " ,name - "+ response.name);
                    $state.go('entities');
                    })
                    .error(function(data, status) {
                    console.log(status);
                });
        */
     
          //standard way of calling rest service through services.js so that same can be reused across multiple controllers  
                  
               entityService.postEntityData($scope.emp).then(
                            function(response) {
                            	console.log("API CALL :: Success");
                                $scope.result = response;
                            }, function(error){
                               // $scope.error = error ;
                                if(error.data){
                                	console.log("HTTP status :: " + error.status + " error code :: "+  error.data.code + " error message :: "+ error.data.message  );
                                	$scope.createEmpError=error.data.message;  
                                }else{
                                	console.log("HTTP status :: " + error.status + " error :: "+  $scope.error  );
                                	$scope.createEmpError= error;  
                                }
                                	
                            }
                );
      
        }
        }
    ]
);