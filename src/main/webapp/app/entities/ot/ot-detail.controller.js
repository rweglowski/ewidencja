(function() {
    'use strict';

    angular
        .module('ewidencjaApp')
        .controller('OtDetailController', OtDetailController);

    OtDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ot', 'Asset', 'Employee'];

    function OtDetailController($scope, $rootScope, $stateParams, previousState, entity, Ot, Asset, Employee) {
        var vm = this;

        vm.ot = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ewidencjaApp:otUpdate', function(event, result) {
            vm.ot = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
