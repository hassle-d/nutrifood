var myApp = angular.module('nutrifood', ['ngRoute'])

myApp.config(function ($httpProvider, $routeProvider) {
            $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
            $httpProvider.defaults.headers.post['Content-Type'] =  'application/x-www-form-urlencoded';

            $routeProvider
                .when('/', {
                    templateUrl : 'views/home.html',
                    controller : 'homeController'
                })
                .when('/register', {
                    templateUrl : 'views/register.html',
                    controller : 'registerController'
                })
                .when('/category', {
                    templateUrl : 'views/category.html',
                    controller : 'categoryController'
                })
                .when('/recipes', {
                    templateUrl : 'views/recipes.html',
                    controller : 'recipesController'
                })
                .when('/submit-recipe', {
                    templateUrl : 'views/submit-recipes.html',
                    controller : 'submitRecipeController'
                })
                .when('/login', {
                    templateUrl : 'views/login.html',
                    controller : 'loginController'
                })
                .when('/profile', {
                    templateUrl : 'views/profile.html',
                    controller : 'profileController'
                })
                .when('/meal/:id', {
                    templateUrl : 'views/meal.html',
                    controller : 'mealController'
                });
});


serialize = function(obj) {
  var str = [];
  for(var p in obj)
    if (obj.hasOwnProperty(p)) {
      str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
    }
  return str.join("&");
}

var token = "";

myApp.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

myApp.controller('recipeSubmitController', ['$scope', '$http', function($scope, $http){
    $scope.recipes = function() {
        var image = $scope.myFile;

        var fd = new FormData();

        fd.append('image', image);
        fd.append('author', $scope.author);
        fd.append('date', Date.now);
        fd.append('name', $scope.name);
        fd.append('description', $scope.description);
        fd.append('instruction', $scope.instruction);
        fd.append('difficulty', $scope. difficulty);
        fd.append('category', $scope.category);
        fd.append('ingredients', $scope.ingredients);
        fd.append('cooktime', $scope.cooktime);

        $http.post('/api/v1/meals', fd,{
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
            .success(function(){
                console.log('ok');
            })
            .error(function(){
            });
    };
}]);

myApp.service('profileService', function($http) {
    delete $http.defaults.headers.common['X-Requested-With'];
    this.getData = function() {
        return $http({
            method: 'GET',
            url: '/api/v1/user',
            headers: {'Authorization': 'VAlYRtwJ2xhBNgqbBndkGIdvFfDtzGMX1DStQMMb6qkR6Rb5eAgRxTOv5o8ZVtmx1mlS4I7OWAxjpOpPMnbLKaUDkkkk9UyeW8gy'}
        });
    }
});

myApp.controller('profileController', function($scope, $http, profileService) {
    $scope.profile = null;
    profileService.getData().then(function(dataResponse) {

        var data = dataResponse.data;
        data.username = data.username.charAt(0).toUpperCase() + data.username.slice(1);
        $scope.profile = data;
        console.log($scope.profile);
    });

    $scope.update = function(){
        var user = {
            username: $scope.username,
            firstname: $scope.firstname,
            lastname: $scope.lastname,
            description: $scope.description,
            allergy: $scope.allergy,
            religion: $scope.religion
        };

        user = checkValidity(user)

        /* Need ID UTILISATEUR FOR PUT */

        $http.put('/api/v1/users', serialize(user))
            .success(function(user){
                console.log()
                $location.path('/home');
                console.log(user);
            })
            .error(function(user){
                console.log(user)
            });
    };

});

myApp.service('mealService', function($http) {
    delete $http.defaults.headers.common['X-Requested-With'];
    this.getData = function() {
        return $http({
            method: 'GET',
            url: '/api/v1/meals'
           // headers: {'Authorization': 'Token token=' + token}
        });
    }
});

myApp.controller('homeController', function($scope, mealService) {
    $scope.data = null;
    $scope.meals = null;
    mealService.getData().then(function(dataResponse) {

        $scope.data = dataResponse;
        console.log($scope.data);
        $scope.meals = dataResponse.data;
        console.log($scope.meals);
    });
});

myApp.controller('mealController', function($scope, $http, $routeParams) {
    var id = $routeParams.id;
    delete $http.defaults.headers.common['X-Requested-With'];
    $http({
        method: 'GET',
        url: '/api/v1/meals/' + id
    }).then(function(dataResponse) {
        console.log(dataResponse.data);
        $scope.meal = dataResponse.data;
    });
});


myApp.controller('registerController', ['$scope', '$http', '$location', function($scope, $http, $location){
    $scope.register = function() {
        var user = {
            username: $scope.username,
            password: $scope.password,
            email: $scope.email,
            firstname: $scope.firstname,
            lastname: $scope.lastname,
            age: $scope.age,
            description: $scope.description,
            allergy: $scope.allergy,
            religion: $scope.religion
        };

        user = checkValidity(user)

        $http.post('/api/v1/users', serialize(user))
            .success(function(user){
                console.log()
                $location.path('/home');
            console.log(user);
        })
            .error(function(user){
                console.log(user)
            });
    };
}]);


myApp.controller('loginController', ['$scope', '$http', function($scope, $http) {
    $scope.login = function() {
        var datas = {
			username: $scope.username,
        	password: $scope.password
        };
        console.log(datas)
        $http.post('/api/v1/auth/signin', serialize(datas))
            .success(function(data) {
                console.log(data);
                token = data;
                console.log(token);
            })
            .error(function(data) {
                console.log(data);
            });
    };

    $scope.logout = function() {

    }
}]);