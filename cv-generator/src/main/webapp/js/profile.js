var profileApp = angular.module('profileApp', []);

profileApp.controller('ProfileController', ['$scope', '$window', function ($scope, $window) {
                $scope.data = {};
                $scope.info = {};
            if(userData){
                $scope.data = userData['curriculum-vitae'];
                $scope.info = $scope.data['personal-details'];
            }else{
            }
    }]);