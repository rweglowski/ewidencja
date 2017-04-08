(function() {
    'use strict';

    angular
        .module('ewidencjaApp')
        .controller('OtDialogController', OtDialogController);

    OtDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Ot', 'Asset', 'Employee'];

    function OtDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Ot, Asset, Employee) {
        var vm = this;

        vm.ot = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.assets = Asset.query({filter: 'ot-is-null'});
        $q.all([vm.ot.$promise, vm.assets.$promise]).then(function() {
            if (!vm.ot.asset || !vm.ot.asset.id) {
                return $q.reject();
            }
            return Asset.get({id : vm.ot.asset.id}).$promise;
        }).then(function(asset) {
            vm.assets.push(asset);
        });
        vm.employees = Employee.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ot.id !== null) {
                Ot.update(vm.ot, onSaveSuccess, onSaveError);
            } else {
                Ot.save(vm.ot, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('ewidencjaApp:otUpdate', result);
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
