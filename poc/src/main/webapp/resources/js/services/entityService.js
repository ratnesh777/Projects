 ipcApp.service("entityService",['$http', 'apiResponse', function($http,apiResponse){

        console.log("inside entityService method");
   	return{
	     getEntityData: getEntityData,
        postEntityData: postEntityData 
    };
	
	
     function getEntityData() {
		 var request = $http({
	            method: "get",
	            url: "http://localhost:8080/poc/api/emp"
	        });
		//return request;	 //1st way of calling service and returning response
	      return  apiResponse.makeApiCall(request);  //2nd and standard way by using $q 
     }
 
     
      function postEntityData(requestData) {
		 var request = $http({
	            method: "post",
	            url: "http://localhost:8080/poc/api/emp",
                data : requestData
	        });
		//return request;	 //1st way of calling service and returning response
	      return  apiResponse.makeApiCall(request);  //2nd and standard way by using $q 
     }
     
}]);