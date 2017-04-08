(function() {
    'use strict';

    angular
        .module('ewidencjaApp')
        .controller('PtDeleteController',PtDeleteController);

    PtDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pt'];

    function PtDeleteController($uibModalInstance, entity, Pt) {
        var vm = this;

        vm.pt = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pt.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
