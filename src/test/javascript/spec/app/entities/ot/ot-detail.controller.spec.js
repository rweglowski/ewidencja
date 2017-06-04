'use strict';

describe('Controller Tests', function() {

    describe('Ot Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockOt, MockEmployee, MockAsset;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockOt = jasmine.createSpy('MockOt');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockAsset = jasmine.createSpy('MockAsset');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Ot': MockOt,
                'Employee': MockEmployee,
                'Asset': MockAsset
            };
            createController = function() {
                $injector.get('$controller')("OtDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ewidencjaApp:otUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
