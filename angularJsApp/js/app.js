let SpringApp = angular.module('SpringApp', ['ngRoute']);
let userList = [];

SpringApp.config(function ($routeProvider, $locationProvider, $sceProvider) {
    $sceProvider.enabled(false);
    $routeProvider
        .when('/', {
            templateUrl: 'users.html',
            controller: 'UserListCtrl'
        })
        .when('/user/new', {
            templateUrl: 'user.html',
            controller: 'UserNewCtrl'
        })
        .when('/user/:id/edit', {
            templateUrl: 'user.html',
            controller: 'UserEditCtrl'
        })
        .otherwise({
            redirectTo: '/'
        });
});

SpringApp.controller('UserListCtrl', function ($scope, $rootScope, $routeParams, $http) {

    $http.get("http://localhost:8080/mvc-app/api/v1/user/all")
        .then(resp => {
                userList = resp.data;
                $scope.users = resp.data;
            },
            resp => {
                console.error(response.data.errorMessage);
            });
});

SpringApp.controller('UserNewCtrl', function ($scope, $rootScope, $routeParams, $location, $http) {

    $scope.user = {id: null, login: null, password: null};

    $scope.submit = function (user) {
        console.info(user);

        $http.post("http://localhost:8080/mvc-app/api/v1/user", user)
            .then(resp => {
                    $location.path("/");
                },
                resp => {
                    console.error(response.data.errorMessage);
                });
    }
});

SpringApp.controller('UserEditCtrl', function ($scope, $rootScope, $routeParams, $location, $http) {

    $scope.user = userList.find(usr => usr.id === parseInt($routeParams.id));

    $scope.submit = function (user) {
        console.info(user);

        $http.put("http://localhost:8080/mvc-app/api/v1/user", user)
            .then(resp => {
                    $location.path("/");
                },
                resp => {
                    console.error(response.data.errorMessage);
                });
    }
});