(function() {
    'use strict';

    angular
        .module('ewidencjaApp')
        .controller('PtDialogController', PtDialogController);

    PtDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pt', 'Asset', 'Employee'];

    function PtDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pt, Asset, Employee) {
        var vm = this;

        vm.pt = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.assets = Asset.query();
        vm.employees = Employee.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pt.id !== null) {
                Pt.update(vm.pt, onSaveSuccess, onSaveError);
            } else {
                Pt.save(vm.pt, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ewidencjaApp:ptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
