(function() {
    'use strict';

    angular
        .module('ewidencjaApp')
        .controller('AssetDialogController', AssetDialogController);

    AssetDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Asset', 'Employee', 'Ot', 'Pt'];

    function AssetDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Asset, Employee, Ot, Pt) {
        var vm = this;

        vm.asset = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.employees = Employee.query();
        vm.ots = Ot.query();
        vm.pts = Pt.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.asset.id !== null) {
                Asset.update(vm.asset, onSaveSuccess, onSaveError);
            } else {
                Asset.save(vm.asset, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ewidencjaApp:assetUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.purchaseDate = false;
        vm.datePickerOpenStatus.endDateOfUse = false;
        vm.datePickerOpenStatus.liquidationDate = false;
        vm.datePickerOpenStatus.startDateOfUse = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
