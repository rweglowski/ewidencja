(function() {
    'use strict';

    angular
        .module('ewidencjaApp')
        .controller('PtDetailController', PtDetailController);

    PtDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pt', 'Asset', 'Employee'];

    function PtDetailController($scope, $rootScope, $stateParams, previousState, entity, Pt, Asset, Employee) {
        var vm = this;

        vm.pt = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ewidencjaApp:ptUpdate', function(event, result) {
            vm.pt = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
