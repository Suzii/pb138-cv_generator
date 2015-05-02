var editApp = angular.module('editApp', []);

editApp.controller('FormController', ['$scope', '$window', '$http', function ($scope, $window, $http) {
        $scope.submit = function () {
            //if ($scope.userForm.$valid) {
            //$http.post($('#cvForm').attr('action'), {data: JSON.stringify($scope.data)}, { })
            
            $http({
                    url: $('#cvForm').attr('action'),
                    method: "POST",
                    responseType: "json",
                    headers: {'Content-Type': 'application/json'},
                    data: $scope.data})
                    .success(function (data, status) {
                        $window.alert('You rock!');
                        $window.alert(data);
                    })
                    .error(function (data, status) {
                        $window.alert('Could not reach the server!');
                    });
            //}
        };

        $scope.createBlankForm = function () {
            $scope.data = {};
            $scope.data['personal-details'] = {};
            $scope.addPhone($scope.data['personal-details']);
            $scope.addEmail($scope.data['personal-details']);
            $scope.addSocial($scope.data['personal-details']);

            $scope.addEducation($scope.data);
            $scope.addEmployment($scope.data);
            $scope.addLanguageSkill($scope.data);
            $scope.addCertificate($scope.data);
            $scope.addComputerSkill($scope.data);
            $scope.addDrivingLicence($scope.data);
            $scope.data.characteristic = "";
            $scope.data.hobbies = "";
        };

        $scope.deleteItem = function (array, index) {
            if (array.length > 1) {
                array.splice(index, 1);
            }
        };

        $scope.addPhone = function (form) {
            if (typeof form.phones === 'undefined') {
                form.phones = [];
            }
            form.phones.push({value: ""});
        };

        $scope.addEmail = function (form) {
            if (typeof form.emails === 'undefined') {
                form.emails = [];
            }
            form.emails.push({value: ""});
        };

        $scope.addSocial = function (form) {
            if (typeof form.social === 'undefined') {
                form.social = [];
            }
            form.social.push({value: ""});
        };

        $scope.addLanguageSkill = function (form) {
            if (typeof form['language-skills'] === 'undefined') {
                form['language-skills'] = [];
            }
            form['language-skills'].push({name: "", level: "", note: ""});
        };

        $scope.addCertificate = function (form) {
            if (typeof form.certificates === 'undefined') {
                form.certificates = [];
            }
            form.certificates.push({name: "", year: "", note: ""});
        };

        $scope.addDrivingLicence = function (form) {
            if (typeof form['driving-licence'] === 'undefined') {
                form['driving-licence'] = [];
            }
            form['driving-licence'].push({name: "", note: ""});
        };

        $scope.addComputerSkill = function (form) {
            if (typeof form['computer-skills'] === 'undefined') {
                form['computer-skills'] = [];
            }
            form['computer-skills'].push({name: "", note: "", level: ""});
        };

        $scope.addEmployment = function (form) {
            if (typeof form.employment === 'undefined') {
                form.employment = [];
            }
            form.employment.push({company: "", position: "", note: "", from: null, to: null});
        };

        $scope.addEducation = function (form) {
            if (typeof form.education === 'undefined') {
                form.education = [];
            }
            form.education.push({value: "", from: null, to: null});
        };

        if (userData) {
            $scope.data = userData;
        } else {
            $scope.createBlankForm();
        }
    }]);