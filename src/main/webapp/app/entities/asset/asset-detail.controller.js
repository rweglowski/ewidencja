(function() {
    'use strict';

    angular
        .module('ewidencjaApp')
        .controller('AssetDetailController', AssetDetailController);

    AssetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Asset', 'Employee', 'Ot', 'Pt'];

    function AssetDetailController($scope, $rootScope, $stateParams, previousState, entity, Asset, Employee, Ot, Pt) {
        var vm = this;

        vm.asset = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('ewidencjaApp:assetUpdate', function(event, result) {
            vm.asset = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
