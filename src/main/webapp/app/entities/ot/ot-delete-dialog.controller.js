(function() {
    'use strict';

    angular
        .module('ewidencjaApp')
        .controller('OtDeleteController',OtDeleteController);

    OtDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ot'];

    function OtDeleteController($uibModalInstance, entity, Ot) {
        var vm = this;

        vm.ot = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ot.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
