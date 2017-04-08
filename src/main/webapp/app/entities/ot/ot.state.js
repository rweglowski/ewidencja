(function() {
    'use strict';

    angular
        .module('ewidencjaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ot', {
            parent: 'entity',
            url: '/ot',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Ots'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ot/ots.html',
                    controller: 'OtController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('ot-detail', {
            parent: 'ot',
            url: '/ot/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Ot'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ot/ot-detail.html',
                    controller: 'OtDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Ot', function($stateParams, Ot) {
                    return Ot.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ot',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ot-detail.edit', {
            parent: 'ot-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ot/ot-dialog.html',
                    controller: 'OtDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ot', function(Ot) {
                            return Ot.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ot.new', {
            parent: 'ot',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ot/ot-dialog.html',
                    controller: 'OtDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                place: null,
                                date: null,
                                name: null,
                                path: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ot', null, { reload: 'ot' });
                }, function() {
                    $state.go('ot');
                });
            }]
        })
        .state('ot.edit', {
            parent: 'ot',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ot/ot-dialog.html',
                    controller: 'OtDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ot', function(Ot) {
                            return Ot.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ot', null, { reload: 'ot' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ot.delete', {
            parent: 'ot',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ot/ot-delete-dialog.html',
                    controller: 'OtDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ot', function(Ot) {
                            return Ot.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ot', null, { reload: 'ot' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
