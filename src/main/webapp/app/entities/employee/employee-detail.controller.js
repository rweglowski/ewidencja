(function() {
    'use strict';

    angular
        .module('ewidencjaApp')
        .controller('EmployeeDetailController', EmployeeDetailController);

    EmployeeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Employee', 'Asset', 'Ot', 'Pt'];

    function EmployeeDetailController($scope, $rootScope, $stateParams, previousState, entity, Employee, Asset, Ot, Pt) {
        var vm = this;

        vm.employee = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ewidencjaApp:employeeUpdate', function(event, result) {
            vm.employee = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
