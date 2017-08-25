(function() {
	"use strict";

	angular.module('widgets', [
	                           'restService',
	                           'ui.grid',
	                           'ui.grid.pagination',
	                           'ui.grid.selection',
	                           'ui.grid.edit',
	                           'ui.grid.cellNav',
	                           'ui.grid.rowEdit',
	                           'ui.grid.saveState',
	                           'ui.select',
	                           'ui.grid.resizeColumns'
    ]).controller('WidgetController', WidgetController);

	
	WidgetController.$inject = [];
	function WidgetController() {
		var self = this;
		  self.title = 'Information';
	      self.collapsed = false;
	      self.expanded = false;
	      self.minimized = false;
	      self.tableOpitons = {};
	      self.tableFeatures = '';
	}

}());