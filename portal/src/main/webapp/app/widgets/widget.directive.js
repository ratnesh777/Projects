(function () {
    angular.module('widgets')
        .directive('widget', widget);
    
    widget.$inject = ['$compile'];
    function widget ($compile) {
        return {
            templateUrl: '/app/widgets/widget.html',
            controller: 'WidgetController',
            controllerAs: 'WidgetCtrl',
            scope: {
                title: '@',
                type: '@',
                dataurl: '@',
                tableFeatures: '@',
                tableOptions: '='
            },
            bindToController: true,
            link: function ($scope, $element, $attrs) {
                var type = $scope.WidgetCtrl.type = $attrs.type || 'ipc-chart';
                var widgetDirectiveString = '<' + type;
                if ($scope.WidgetCtrl.dataurl) {
                    widgetDirectiveString += ' dataurl="' + $scope.WidgetCtrl.dataurl + '"';
                }
                if ($attrs.tableFeatures) {
                    widgetDirectiveString += ' table-features="' + $attrs.tableFeatures + '"';
                }
                if ($attrs.tableOptions) {
                    widgetDirectiveString += ' table-options="' + $attrs.tableOptions + '"';
                }
                widgetDirectiveString += ' expanded="' + $scope.WidgetCtrl.expanded + '">';
                var linkFn = $compile(widgetDirectiveString);
                var classList = 'portlet col-xs-12 col-md-8 col-lg-6';
                if (type === 'cloud-table') {
                    $element[0].querySelector('div').className = classList;
                }
                var el = $element[0].querySelector('widgetPlaceHolder');
                linkFn(
                    $scope, function cloneAttachFn (clone, scope) {
                        angular.element(el).replaceWith(clone);
                    });
            }
        };
    }
    
}());

