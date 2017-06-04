'use strict';

describe('Controller Tests', function() {

    describe('Asset Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAsset, MockEmployee, MockPt, MockOt;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAsset = jasmine.createSpy('MockAsset');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockPt = jasmine.createSpy('MockPt');
            MockOt = jasmine.createSpy('MockOt');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Asset': MockAsset,
                'Employee': MockEmployee,
                'Pt': MockPt,
                'Ot': MockOt
            };
            createController = function() {
                $injector.get('$controller')("AssetDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ewidencjaApp:assetUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
