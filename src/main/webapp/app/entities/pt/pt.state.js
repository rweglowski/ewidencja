(function() {
    'use strict';

    angular
        .module('ewidencjaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pt', {
            parent: 'entity',
            url: '/pt',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Pts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pt/pts.html',
                    controller: 'PtController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('pt-detail', {
            parent: 'pt',
            url: '/pt/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Pt'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pt/pt-detail.html',
                    controller: 'PtDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Pt', function($stateParams, Pt) {
                    return Pt.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pt',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pt-detail.edit', {
            parent: 'pt-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pt/pt-dialog.html',
                    controller: 'PtDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pt', function(Pt) {
                            return Pt.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pt.new', {
            parent: 'pt',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pt/pt-dialog.html',
                    controller: 'PtDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date: null,
                                name: null,
                                ptName: null,
                                path: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pt', null, { reload: 'pt' });
                }, function() {
                    $state.go('pt');
                });
            }]
        })
        .state('pt.edit', {
            parent: 'pt',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pt/pt-dialog.html',
                    controller: 'PtDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pt', function(Pt) {
                            return Pt.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pt', null, { reload: 'pt' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pt.delete', {
            parent: 'pt',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pt/pt-delete-dialog.html',
                    controller: 'PtDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pt', function(Pt) {
                            return Pt.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pt', null, { reload: 'pt' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
