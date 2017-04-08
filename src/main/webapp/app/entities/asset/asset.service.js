(function() {
    'use strict';
    angular
        .module('ewidencjaApp')
        .factory('Asset', Asset);

    Asset.$inject = ['$resource', 'DateUtils'];

    function Asset ($resource, DateUtils) {
        var resourceUrl =  'api/assets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.purchaseDate = DateUtils.convertLocalDateFromServer(data.purchaseDate);
                        data.endDateOfUse = DateUtils.convertLocalDateFromServer(data.endDateOfUse);
                        data.liquidationDate = DateUtils.convertLocalDateFromServer(data.liquidationDate);
                        data.startDateOfUse = DateUtils.convertLocalDateFromServer(data.startDateOfUse);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.purchaseDate = DateUtils.convertLocalDateToServer(copy.purchaseDate);
                    copy.endDateOfUse = DateUtils.convertLocalDateToServer(copy.endDateOfUse);
                    copy.liquidationDate = DateUtils.convertLocalDateToServer(copy.liquidationDate);
                    copy.startDateOfUse = DateUtils.convertLocalDateToServer(copy.startDateOfUse);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.purchaseDate = DateUtils.convertLocalDateToServer(copy.purchaseDate);
                    copy.endDateOfUse = DateUtils.convertLocalDateToServer(copy.endDateOfUse);
                    copy.liquidationDate = DateUtils.convertLocalDateToServer(copy.liquidationDate);
                    copy.startDateOfUse = DateUtils.convertLocalDateToServer(copy.startDateOfUse);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
