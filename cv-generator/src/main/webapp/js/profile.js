var profileApp = angular.module('profileApp', []);

profileApp.controller('ProfileController', ['$scope', '$window', function ($scope, $window) {
            $scope.data = userData || {};
    }]);