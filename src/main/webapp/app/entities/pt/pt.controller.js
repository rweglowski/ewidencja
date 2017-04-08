(function() {
    'use strict';

    angular
        .module('ewidencjaApp')
        .controller('PtController', PtController);

    PtController.$inject = ['Pt'];

    function PtController(Pt) {

        var vm = this;

        vm.pts = [];

        loadAll();

        function loadAll() {
            Pt.query(function(result) {
                vm.pts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
