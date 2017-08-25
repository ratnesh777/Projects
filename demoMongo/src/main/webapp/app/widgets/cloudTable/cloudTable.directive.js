(function () {
    /**
     * To use:
     *
     * <table dataurl="/api/alertsTable"
     * table-features="ui-grid-pagination,ui-grid-selectors"
     * table-options="{data:[]}"></table>
     *
     * Alternatively, if the data is retrieved from within the parent
     * controller, have the parent controller set the gridOptions.data
     * attribute:
     *
     * ctrlPseudonym.gridOptions.data = ...
     */
    angular.module('widgets')   
    	.config(globalTableConfig)
        .directive('cloudTable', cloudTable);
    
    globalTableConfig.$inject = ['$provide'];
    function globalTableConfig ($provide) {
        $provide.decorator(
            'GridOptions', function ($delegate) {
                var gridOptions;
                gridOptions = angular.copy($delegate);
                gridOptions.initialize = function (options) {
                    var initOptions;
                    initOptions = $delegate.initialize(options);
                    initOptions.paginationPageSizes = [10, 25, 50, 100];
                    initOptions.enableRowSelection = true;
                    initOptions.enableSelectAll = true;
                    initOptions.multiSelect = true;
                    initOptions.useExternalSorting = true;
                    initOptions.enableFiltering = false;
                    initOptions.useExternalFiltering = true;
                    initOptions.useExternalPagination = true;
                    initOptions.selectionRowHeaderWidth = 30;
                    initOptions.rowHeight = 40;
                    initOptions.showGridFooter = false;
                    initOptions.enableColumnMenus = false;
                    initOptions.totalItems = 0;
                    initOptions.enableHorizontalScrollbar = 0;
                    initOptions.enablePagination;
                    initOptions.enablePaginationControls;
                    initOptions.paginationTemplate = '/demoMongo/app/widgets/cloudTable/pagination.template.html'
                    return initOptions;
                };
                return gridOptions;
            });
    }
 
    
    
    function cloudTable () {
        return {
            templateUrl: '/demoMongo/app/widgets/cloudTable/table.template.html',
            controller: CloudTableController,
            controllerAs: 'tableCtrl',
            bindToController: true,
            scope: {
            	dataurl: '@',
            	search: '@',
                tableFeatures: '@',
                tableOptions: '=',
                selectedIds: '=',
                queryParams: '@',
                setupTable: '&',
                isAllRecordSelected:'=',
                refresh:'='
            },
            compile: function (elem, attrs) {
                if (attrs.tableFeatures) {
                    var grid = elem[0].firstElementChild;
                    attrs.tableFeatures.split(',').forEach(
                        function (option) {
                            grid.setAttribute(option.trim(), '');
                        });
                }
            }
            };
    }
    
    
    CloudTableController.$inject = ['$scope', 'restService', 'uiGridConstants'];
    function CloudTableController ($scope, restService, uiGridConstants) {
    	
    	console.log(" Inside Cloud TableController ");
    	// Locals
    	var self = this;
    	
        var page = 0;
        var size = 10;
        var sortParam = 'id';
        var sortDirection = 'asc';
        var enforced = true; // This should come in as queryParams
        // Publics
        self.loading = true;
        self.loadAttempted = false;
        // Get the grid options from the
        self.gridOptions = self.tableOptions || {};
        self.selectedRows =[];
        self.gridOptions.onRegisterApi = setUpUiGridListeners;
        self.goToPage = goToPage;
        self.keypress = keypress;
        self.focus = focus;
        self.gridOptions.paginationPageSize = size;
        self.isOpen = false;
        self.isAllRecordSelected = self.isAllRecordSelected || false;
        self.selectedIds =  self.selectedIds || [];
        self.state = {};
        self.tableKey =  self.gridOptions.tableKey || 'id';
        self.gridApi = self.gridApi;
        self.gridOptions.paginationCurrentPage = undefined;
        var rowtpl='<div><div ng-repeat="(colRenderIndex, col) in colContainer.renderedColumns track by col.colDef.name" class="ui-grid-cell" ng-class="{ \'ui-grid-row-header-cell\': col.isRowHeader , \'uiGridSelCheck\': col.isRowHeader,\'selectableRow\': !row.entity.readStatus }" ui-grid-cell></div></div>';
        self.gridOptions.rowTemplate= rowtpl;
        if (self.gridOptions.appScopeProvider.sortParam !== undefined && self.gridOptions.appScopeProvider !== '') {
			sortParam = self.gridOptions.appScopeProvider.sortParam;
		}


        if(self.gridOptions.appScopeProvider.pageNumber === undefined ||self.gridOptions.appScopeProvider.invalidPageMsg === undefined){
        	self.gridOptions.appScopeProvider.invalidPageMsg='';
        	self.gridOptions.appScopeProvider.pageNumber='';
        }
        
        if(angular.isDefined(self.search)){
            $scope.$watch(function(){ return self.search;} ,function(){ 
            	if(self.gridOptions.appScopeProvider.alerts){
            		self.gridOptions.appScopeProvider.alerts =[];
            	}
            	self.grid.options.paginationCurrentPage = 1;
            	getData();
            });
        } else {
        	getData();
        }

		        
        
        $scope.$watch(function() {
        	if(self.grid.selection){
        		return self.grid.selection.selectAll;
        	}else{
        		return false;
        	}
			
		}, function(value) {
			if (value) {
				if (self.selectedRows.length == self.gridOptions.totalItems && self.gridOptions.appScopeProvider.searchKey == '') {
					self.isAllRecordSelected = true;
				}
			} 
		});
        
        if(angular.isDefined(self.refresh)){
        	$scope.$watch(function(){return self.refresh;},function(value){
        		if(value){
        			if(self.grid.selection){
        				self.grid.selection.selectAll = false;
        			}
        			sortParam = self.tableKey;
            		sortDirection = 'asc';
            		self.gridOptions.appScopeProvider.isSortingApplied = true;
            		self.grid.options.paginationCurrentPage = 1;
        			getData();
        		}
        	});
        }
    	
        if(angular.isDefined(self.isAllRecordSelected)){
        	$scope.$watch(function(){return self.isAllRecordSelected;},function(value){
        		if(value){
        			self.gridApi.pagination.seek(1);
        			getData();
        		}
        	});
        }

        // listen to row selections and remote sort,pagination,filter
        function setUpUiGridListeners (gridApi) {
        	
        	//assigning the gridApi to scope
        	self.gridApi = gridApi;
        	self.grid = gridApi.grid;
        	
        	if(gridApi.selection){
        		// react to single row being selected
        		gridApi.selection.on.rowSelectionChanged(
                        null, function (row) {
                        	// PEPAP - get all selected rows
                            self.selectedRows = [row];
                            // PEPAP - filter out so just ids (only name provided in demo)
                            filterIds();
                        });
        		 // react to multiple rows being selected
                gridApi.selection.on.rowSelectionChangedBatch(
                    null, function (rows, event) { 
                    	// PEPAP - get all selected rows
                        self.selectedRows = gridApi.selection.getSelectedRows();
                        // PEPAP - filter out so just ids
                        filterIds();
                        
                    });
                
                gridApi.core.registerRowsProcessor(reselectRows);
        	}
            
           
            // react to a sort change request
            gridApi.core.on.sortChanged(
                    null,
                    function (grid, sortColumns) {
                        if(sortColumns.length > 0) {
                        	sortParam = sortColumns[0].field;
                        	sortDirection = sortColumns[0].sort.direction;                        	
                        	self.gridOptions.appScopeProvider.isSortingApplied = true;
                    	} else {
                    		sortParam = self.tableKey;
                    		sortDirection = 'asc';
                    		self.gridOptions.appScopeProvider.isSortingApplied = false;
                    	}
                        getData();
                    });
            // React to pagination requests
            if (gridApi.pagination) {
                gridApi.pagination.on.paginationChanged(
                        null,
                        function (newPage, pageSize) {
                            size = pageSize;
                            page = newPage;
                            if(self.grid.selection){
                				self.grid.selection.selectAll = false;
                			}
                            if(self.gridOptions.appScopeProvider.alerts  && !self.refresh){
                        		self.gridOptions.appScopeProvider.alerts =[];
                        		self.refresh = false;
                        	}
                            getData();
                        });
            }
            self.gridOptions.isRowSelectable = function(row){
		          return true;
		      };
		    self.gridApi.core.notifyDataChange(uiGridConstants.dataChange.OPTIONS);
        }
        
        
        
        function filterIds() {
            if(!self.selectedRows.length) {
              self.selectedRows = self.gridApi.core.getVisibleRows(self.gridApi.grid);  
            }
            self.selectedRows.forEach(
              function(row) {
                  // PEPAP - if single selection event, row is GridRow object
                  if (row.hasOwnProperty('isSelected')) {
                    if (!row.isSelected) {
                      // PEPAP - turn off Really select ALL
                      self.isAllRecordSelected = false;
                      self.selectedIds.splice(self.selectedIds.indexOf(row.entity[self.tableKey]), 1);
                    } else if (self.selectedIds.indexOf(row.entity[self.tableKey]) === -1) {
                      self.selectedIds.push(row.entity[self.tableKey]);
                    }
                    // PEPAP - otherwise the row is the row entity (data)
                  } else if (self.selectedIds.indexOf(row[self.tableKey]) < 0) {
                    self.selectedIds.push(row[self.tableKey]);
                  }
                });
          }
        
        function reselectRows(rows) {
            for (var i = 0; i < rows.length; i++) {
              if (self.selectedIds.indexOf(rows[i].entity[self.tableKey]) !== -1) {
                self.gridApi.selection.selectRow(rows[i].entity);
              }
            }
            return rows;
          }
        
        function getData(){
            /** dataUrl set as attribute on widget * */
            if (self.dataurl) {
                self.loading = true;
                var dataUrl = self.dataurl || '/api/nothinghere';
                if(self.grid.options.paginationCurrentPage && self.search==''){
                	page = self.grid.options.paginationCurrentPage - 1;
                } 
                else if(page>0){
                	page = page-1;
                }
                if(self.gridOptions.enablePagination){
                	dataUrl += "?page=" + page + "&size=" + size + "&sortParam=" + sortParam + "&sortDirection=" + sortDirection + "&searchKey=" + self.search;
                }
                dataUrl += (self.queryParams)? self.queryParams : '';
                restService.get(dataUrl).then(show,handleFailure).then(endLoading);
            }
        }
        
        function handleFailure(resp){
        	self.gridOptions.data = [];
        }
        
        function goToPage(){
        	if(self.gridOptions.appScopeProvider.alerts){
        		self.gridOptions.appScopeProvider.alerts =[];
        	}
        	if(self.gridOptions.appScopeProvider.pageNumber < 1 || self.gridOptions.appScopeProvider.pageNumber > self.totalPages || self.gridOptions.appScopeProvider.pageNumber === undefined){
        		self.gridOptions.appScopeProvider.invalidPageMsg = "Page Number Invalid";
        		self.isOpen = true;
        	}
        	else {        		
            	self.grid.options.paginationCurrentPage = Number(self.gridOptions.appScopeProvider.pageNumber);
        	}
        	
       }
        function keypress($event){
        	var keyCode = $event.which || $event.keyCode;
            if (keyCode === 13) {
               self.goToPage();
    		}

        }
        function focus(){
        	self.gridOptions.appScopeProvider.invalidPageMsg = '';
        	self.isOpen = false;
        	self.gridOptions.appScopeProvider.pageNumber = '';
        }
        function show (res) {
        	self.gridOptions.appScopeProvider.pageNumber ='';
        	self.gridOptions.rowTemplate= rowtpl;
        	if (self.gridOptions.enablePagination) {
        		self.totalPages = res.data.totalPages;
        	}
        	var preData;
			  if (self.gridOptions.enablePagination) {
				preData = (res.data.data) ? res.data.data : res.data.content;
			} else {
				if (res.data.content) {
					preData = res.data.content;
				} else {
					preData = res.data;
				}
			}
		   if(res.data.totalElements && res.data.totalElements > 0){
			   	if(self.search ==''){
			   		self.gridOptions.totalRecords = res.data.totalElements;	
			   	}
			   	self.gridOptions.totalItems = res.data.totalElements;	
                self.gridOptions.enablePaginationControls = true;
            }else{
            	if(self.search ==''){
            	self.gridOptions.totalRecords = 0;
            	}
               self.gridOptions.totalItems = 0;
               self.gridOptions.enablePaginationControls = false;
            }
		   
            var data = [];
            if(!preData.length){
            	self.gridOptions.data = [];
            }
            
            // Use the external setup or use this internal simple model
            if (self.setupTable) {
                self.setupTable({preData:preData});               
            }
            
            
            // Otherwise, just dump the data into the table
            angular.forEach(preData, 
                function (row) {
                    var newRow = {};
                    angular.forEach(row,
                        function (col, index) {
                            newRow[index] = col;
                        });
                    data.push(newRow);
                });
            self.gridOptions.data = data;
            
            if(self.gridOptions.enablePagination){
            	var reselectAll = false;
                //var firstRow = page  * self.gridOptions.paginationPageSize;
                //var reselectAll = false;
                // turn off selectAll selector by default
                //self.gridApi.grid.selection.selectAll = false;
                //self.gridOptions.data = data.slice(firstRow, firstRow + self.gridOptions.paginationPageSize);
                // PEPAP - prepare rows for selection
                self.gridApi.grid.modifyRows(self.gridOptions.data);
                // Remove duplicates
                var result = [];
                self.selectedIds.forEach(
                  function(item) {
                    if (result.indexOf(item) === -1) {
                      result.push(item);
                    }
                  });
                self.selectedIds = result;
                //PEPAP - see if # of selections = number of rows on page
                // If so, then reselect all rows on page
                if (self.state['page' + self.gridApi.pagination.getPage()]) {
                  reselectAll = self.state['page' + self.gridApi.pagination.getPage()]['selection'].length == self.gridOptions.paginationPageSize;
                }
                // PEPAP - if isAllRecordSelected is true, collect up the selections
                if (self.isAllRecordSelected && self.selectedIds.length !== self.gridOptions.totalRecords || reselectAll ) {
                  self.gridApi.selection.selectAllRows();
                  self.gridApi.pagination.nextPage();
                } else if (reselectAll) {
                  self.gridApi.selection.selectAllRows();
                }
            }
            endLoading();
            self.refresh = false;
        }
        
        function endLoading () {
        	self.gridOptions.appScopeProvider.pageNumber ='';
            self.loading = false;
            self.loadAttempted = true;
        }
      
        
    }
}());

