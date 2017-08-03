ipcApp.service("apiResponse",function($http,$log,$q){
	return{
		makeApiCall: makeApiCall
	};
	
	function makeApiCall(request) {
		return request.then( handleSuccess, handleError );
	}
	
	function handleError( response ) {
         // The API response from the server should be returned in a
         // nomralized format. However, if the request was not handled by the
         // server (or what not handles properly - ex. server error), then returning "An unknown error occurred" response
         
        if ( !angular.isObject(response.data) && response.status == 0  ) {
              console.log("An unknown error occurred");
             return( $q.reject( "An unknown error occurred." ) );
         }
         // Otherwise, return a response with error message.
            console.log("Not a 200 Error response  ");
            return($q.reject( response ));
     }
    
     //transform the successful response, unwrapping the application data
     // from the API response payload.
     function handleSuccess( response ) {
         console.log(" API called status :: " +response.status);
         return( response.data );
     }
	
});