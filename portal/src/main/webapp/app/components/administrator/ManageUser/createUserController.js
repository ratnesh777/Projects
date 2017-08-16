(function () {
    angular.module('manageUser').controller('createUserController',
        createUserController);

    createUserController.$inject = ['$state', 'urlConstants', '$rootScope', 'manageUserService', 'manageCompanyService', '$state'];

    function createUserController($state, urlConstants, rootScope, manageUserService, manageCompanyService, $state) {
        var cuc = this;
        cuc.alerts = [];
        cuc.totalUser = 0;
        cuc.user = {};
        cuc.company = {};
        cuc.companies = [];
        cuc.products = [];
        cuc.roles = [
            {
                id: "1",
                name: "Admin"
            },
            {
                id: "4",
                name: "Customer User"
            }
        ];

        cuc.createUser = createUser;
        cuc.roleChanged = roleChanged;
        cuc.closeAlert = closeAlert;

        // cuc.init=init();
        init();

        function populateCompaniesList(response) {
            cuc.companies = response.data.content;
        }

        function init() {
            manageCompanyService.getAllCompanies(-1, -1)
                .then(populateCompaniesList, displayError);
        }


        function displayError(response) {
            if (!angular.isObject(response.data)) {
                showBackendExceptionAlert(response.data);
            } else if (response.data.reason == undefined) {
                showBackendExceptionAlert(response.data.message);
            }
            else {
                showBackendExceptionAlert(response.data.reason);
            }
        }

        function displaySuccess() {
            cuc.alerts.push({
                type: 'success',
                msg: 'User added successfully'
            });
            $state.go('manageUser');
        }

        function showBackendExceptionAlert(str) {
            console.log("errorMessage from backend :: " + str);
            cuc.alerts = [];
             if(str){
             if(str.indexOf(":")>-1){
             str=str.split(":")[1];
             }

             cuc.alerts.push({
             type : 'danger',
             msg : str
             });

             console.log(cuc.alerts);
             }
        }

        function createUser() {
            // cuc.user.company = cuc.company.selected;
            manageUserService.createUser(cuc.user).then(displaySuccess, displayError);
        }

        function roleChanged() {
            console.log(cuc.user.role);
            if (cuc.user.role.id == 1){
                cuc.user.company = cuc.companies[0];
                cuc.user.siteId = "MARS";
            }
        }

        function closeAlert (index) {
            cuc.alerts.splice(index, 1);
        }

    }
}());
