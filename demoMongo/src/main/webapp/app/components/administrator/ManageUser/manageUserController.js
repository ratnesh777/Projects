(function() {
	angular.module('manageUser').controller('manageUserController',
			manageUserController);

	manageUserController.$inject = [ '$state', 'urlConstants', '$rootScope','manageUserService'];

	function manageUserController($state, urlConstants, rootScope,manageUserService) {
       var muc = this;
       muc.totalUser = 0;
       muc.users ={};
   		muc.selectedPageSize = 5;
   		muc.currentPage = 1;
   		muc.totalPages = 0;
   		muc.sortDirection = 'asc';
   		muc.sortParam = 'id';
   		muc.makeAllRecordSelection = makeAllRecordSelection;
        muc.clearAllSelection = clearAllSelection;
       init();
        muc.showSearchGlyphicon = true;
       
		function init() {
			
			console.log("Inside manage user");
			manageUserService.getUsersFromAPI(
					muc.currentPage,
					muc.selectedPageSize, muc.sortParam,
					muc.sortDirection).then(
							function(response) {
								muc.users = response.data;
								muc.totalUser = response.data.totalElements;
								muc.totalPages = response.data.totalPages;
							},displayError);
			
		}
		
	
		
		function displayError(response) {
	       if ( !angular.isObject(response.data) ) {
	              showBackendExceptionAlert(response.data);
	         } else if(response.data.reason==undefined){
				showBackendExceptionAlert(response.data.errorMessage);
			}
			else{
				showBackendExceptionAlert(response.data.reason);
			}
		}

		function showBackendExceptionAlert(str) {
			console.log("errorMessage from backend :: "+str);
			//TODO need to implement
			/*muc.alerts = [];
			if(str){
				if(str.indexOf(":")>-1){
					str=str.split(":")[1];
				}
				
				muc.alerts.push({
					type : 'danger',
					msg : str
				});
				
				console.log(muc.alerts);
				}
			 */
		
		}
		
		
		
		
		// for custom table
		
		// View Specific options for UI Grid
		muc.selectedRows = [];
		muc.pageNumber = '';
		muc.showsearchKeyGlyphicon=true;
		muc.setUpUiGrid = setUpUiGrid;
		muc.isAllRecordSelected = false;
	
		muc.tableRefresh = false;
		
		muc.gridOptions = {
			appScopeProvider : muc, // required for Controller As syntax
			enablePagination : true,
			enableColumnResizing: true,
		};
		muc.search = '';
		muc.tableDataUrl = urlConstants.userUrl; // default
		muc.queryParams = "";
		muc.invalidPageMsg = '';
		//muc.usersUIGrid = [];
		muc.allowedPageSize = [ 10, 25, 50, 100 ];
		
		// Create column definition, data model and set data into table
		function setUpUiGrid(preData) {
		//	console.log("Inside setUpUiGrid ::" + preData);
			
			muc.pageNumber = '';
			muc.invalidPageMsg = '';
			muc.usersData = preData; //
			var data = [];
			var allSelectedRecords = [];
			
			var columnDefs = [
			/*		{
						name : 'User Id',
						field : 'id',
						sort: {
					        direction: 'asc',
					        ignoreSort: true,
					        priority: 0
					      },
					    sortDirectionCycle: ['asc', 'desc']
					},*/
					{
						name : 'First Name',
						field : 'firstName',
						sortDirectionCycle: ['asc', 'desc']
					},
					{
						name : 'Last Name',
						field : 'lastName',
						sortDirectionCycle: ['asc', 'desc']
					},
					{
						name : 'Email',
						field : 'email',
						sortDirectionCycle: ['asc', 'desc']
						
					},
					{
						name : 'Role',
						field : 'role.name',
						sortDirectionCycle: ['asc', 'desc']
					},
					{
						name : 'Site Id',
						field : 'siteId',
						sortDirectionCycle: ['asc', 'desc']
					},
					{
						name : 'BackRoom',
						field : '',
						sortDirectionCycle: ['asc', 'desc']
					},
					{
						name : 'Customer',
						field : 'company.name',
						sortDirectionCycle: ['asc', 'desc']
					},
					{
						name : 'Products',
						field : '',
						sortDirectionCycle: ['asc', 'desc']
					},
					{
						name : 'Status',
						field : 'status',
						sortDirectionCycle: ['asc', 'desc']
					}
					
					];
			
			muc.gridOptions.columnDefs=columnDefs;
			
			angular.forEach(muc.usersData, function(item) {
			//	console.log("item" + item.id);
				data.push({
					id : item.id,
					email : item.email,
					role : item.role.name,
					actions : ''
				});
			});
			muc.gridOptions.data = data;
			muc.gridOptions.totalItems = muc.totalUser;	
		}
		function makeAllRecordSelection() {
			muc.isAllRecordSelected = true;	
		}
		
		function clearAllSelection() {
			muc.isAllRecordSelected = false;
			muc.selectedRows = [];
			muc.tableRefresh = true;
		}
		
	}
}());
