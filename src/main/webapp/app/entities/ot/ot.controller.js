(function() {
    'use strict';

    angular
        .module('ewidencjaApp')
        .controller('OtController', OtController);

    OtController.$inject = ['Ot'];

    function OtController(Ot) {

        var vm = this;

        vm.ots = [];

        loadAll();

        function loadAll() {
            Ot.query(function(result) {
                vm.ots = result;
                vm.searchQuery = null;
            });
        }
    }
})();
