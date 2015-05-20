var editApp = angular.module('editApp', []);

editApp.controller('DateController', ['$scope', function ($scope) {
        $scope.totaldays = 31;
        $scope.totalmonths = 12;
        $scope.totalyears = 150;

        $scope.days = [];
        for (var i = 0; i < $scope.totaldays; i += 1) {
            var d = (i < 9)? "0"+(i+1) : (i+1);
            $scope.days.push(d);
        }

        $scope.months = [];
        for (var i = 0; i < $scope.totalmonths; i += 1) {
            var m = (i < 9)? "0"+(i+1) : (i+1);
            $scope.months.push(m);
        }

        $scope.years = [];
        var currentYear = new Date().getFullYear();
        for (var i = currentYear; i > currentYear - $scope.totalyears; i--) {
            $scope.years.push(i - 1);
        }
    }]);

editApp.controller('FormController', ['$scope', '$window', '$http', function ($scope, $window, $http) {
        $scope.showErrors = false;
        $scope.submit = function () {
            if ($scope.userForm.$valid) {
                if (!checkDate()) {
                    $window.alert("Come on, only Chuck Norris can be born on "
                            + $scope.data['personal-details']['date-of-birth'].day + "."
                            + $scope.data['personal-details']['date-of-birth'].month + "."
                            + $scope.data['personal-details']['date-of-birth'].year + "!");
                    return;
                }
                $scope.showErrors = false;
                var data = {};
                data["curriculum-vitae"] = $scope.data;

                $http({
                    url: $('#cvForm').attr('action'),
                    method: "POST",
                    responseType: "json",
                    headers: {'Content-Type': 'application/json'},
                    data: data})
                        .success(function (data, status) {
                            $window.alert('You rock!');
                            //$window.alert(data);
                        })
                        .error(function (data, status) {
                            $window.alert('Could not reach the server!\n' + data + status);
                        });
            } else {
                $scope.showErrors = true;
                console.log("Invalid form tried to submit.");
                $window.alert("Please fill in all requided fields. If you want to leave somethig blank, press 'X' on the right to delete the group.");
            }
        };

        var checkDate = function () {
            var d = $scope.data['personal-details']['date-of-birth'].day;
            var m = $scope.data['personal-details']['date-of-birth'].month;
            var y = $scope.data['personal-details']['date-of-birth'].year;

            return m > 0 && m <= 12 && d > 0 && d <= daysInMonth(m, y);
        };

        function daysInMonth(m, y) { // m is 1..12
            switch (m) {
                case 2 :
                    return (y % 4 == 0 && y % 100) || y % 400 == 0 ? 29 : 28;
                case 9 :
                case 4 :
                case 6 :
                case 11 :
                    return 30;
                default :
                    return 31;
            }
        }

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
            if (array.length === 1) {
                array.splice(0, 1);
            } else if (array.length > 1) {
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
            form.education.push({
                note: "",
                'name-of-education': "",
                from: null,
                to: null,
                'name-of-school': ""
            });
        };

        if (userData) {
            $scope.data = userData['curriculum-vitae'];
        } else {
            $scope.createBlankForm();
        }
    }]);