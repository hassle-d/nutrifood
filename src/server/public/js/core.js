var myApp = angular.module('nutrifood', ['ngRoute', 'ngCookies', 'youtube-embed'])

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
                    templateUrl : 'views/recipe_submit.html',
                    controller : 'recipeSubmitController'
                })
                .when('/edit-recipe/:id', {
                    templateUrl : 'views/edit_meal.html',
                    controller : 'editMealController'
                })
                .when('/login', {
                    templateUrl : 'views/login.html',
                    controller : 'loginController'
                })
                .when('/logout', {
                    templateUrl : 'views/logout.html',
                    controller : 'logoutController'
                })
                .when('/profile', {
                    templateUrl : 'views/profile.html',
                    controller : 'profileController'
                })
                .when('/meals',{
                    templateUrl : 'views/meals.html',
                    controller: 'homeController'
                })
                .when('/meals/search/:id',{
                    templateUrl : 'views/meals.html',
                    controller: 'searchMealsController'
                })
                .when('/meal/:id', {
                    templateUrl : 'views/meal.html',
                    controller : 'mealController'
                });
});


myApp.service('authService', function($location, $cookies, $rootScope) {
    this.isAuthenticated = function(e) {
        var token = $cookies.get('token');
        console.log(token);
        if (token == null) {
            $location.path('/login');
            return null;
        }
        else {
            $rootScope.token = token;
            return token;
        }
    }
});

myApp.run(function($rootScope, $location) {
    $rootScope.searchMeals = function () {
        $location.path("/meals/search/" + $rootScope.searchValue);
    }
})

serialize = function(obj) {
  var str = [];
  for(var p in obj)
    if (obj.hasOwnProperty(p)) {
      str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
    }
  return str.join("&");
}

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

myApp.controller('recipeSubmitController', function($scope, $http, $cookies, $location, authService){
    $scope.recipes = function() {
        var token = authService.isAuthenticated();
        var image = $scope.myFile;

        console.log($cookies.get('username'));
        var fd = new FormData();

        fd.append('image', image);
        fd.append('author', $cookies.get('username'));
        fd.append('date', Date.now);
        fd.append('video', $scope.video);
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
                $location.path('/');
            })
            .error(function(){
            });
    };
});

myApp.service('profileService', function($http) {
    delete $http.defaults.headers.common['X-Requested-With'];
    this.getData = function(token) {
        console.log(token);
        return $http({
            method: 'GET',
            url: '/api/v1/user',
            headers: {'Authorization': token}
        });
    }
});

myApp.controller('editMealController', function($scope, $http, profileService, authService, $routeParams){
    var token = authService.isAuthenticated();
    $scope.meal = null;
    var id = $routeParams.id;
    delete $http.defaults.headers.common['X-Requested-With'];
    $http({
        method: 'GET',
        url: '/api/v1/meals/' + id
    }).then(function(dataResponse) {
        console.log(dataResponse.data);

        var data = dataResponse.data;
        $scope.name = data.name;
        $scope.video = data.video;
        $scope.description = data.description;
        $scope.difficulty = data.difficulty;
        $scope.cooktime = data.cooktime;
        $scope.instruction = data.instruction;
        $scope.category = data.category;
        $scope.ingredients = data.ingredients;
        $scope.image = data.image;
    });

    $scope.update = function(){
        var image = $scope.myFile;

        var fd = new FormData();

        fd.append('image', image);
        fd.append('name', $scope.name);
        fd.append('video', $scope.video);
        fd.append('description', $scope.description);
        fd.append('instruction', $scope.instruction);
        fd.append('difficulty', $scope. difficulty);
        fd.append('category', $scope.category);
        fd.append('ingredients', $scope.ingredients);
        fd.append('cooktime', $scope.cooktime);

        $http.put('/api/v1/meals/' + id, fd,{
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
            .success(function(){
                console.log('ok');
            })
            .error(function(){
            });
    };

});


myApp.controller('profileController', function($scope, $http, profileService, authService) {
    var token = authService.isAuthenticated();
    
    $scope.profile = null;

    profileService.getData(token).then(function(dataResponse) {
        var data = dataResponse.data;
        data.username = data.username.charAt(0).toUpperCase() + data.username.slice(1);
        $scope.profile = data;
        $scope.firstname = data.firstname;
        $scope.lastname = data.lastname;
        $scope.description = data.description;
        $scope.allergy = data.allergy;
        $scope.religion = data.religion;
        console.log($scope.profile);
    });

    $scope.update = function(){
        var user = {
            firstname: $scope.firstname,
            lastname: $scope.lastname,
            description: $scope.description,
            allergy: $scope.allergy,
            religion: $scope.religion
        };

        user = checkValidity(user)

        $http({
            method: 'PUT',
            url: '/api/v1/user',
            data: serialize(user),
            headers: {'Authorization': token}
        }).then(function(dataResponse) {
            console.log(dataResponse.data);
        });

    };

});

myApp.service('mealService', function($http) {
    delete $http.defaults.headers.common['X-Requested-With'];
    this.getData = function(token) {
        return $http({
            method: 'GET',
            url: '/api/v1/meals',
            headers: {'Authorization': token}
        });
    }
    this.searchMeals = function(token, name) {
        return $http({
            method: 'GET',
            url: '/api/v1/meals/name/' + name,
            headers: {'Authorization': token}
        });

    }
});

myApp.controller('homeController', function($scope, mealService, authService) {
    var token = authService.isAuthenticated();
    $scope.data = null;
    $scope.meals = null;
    mealService.getData(token).then(function(dataResponse) {

        $scope.data = dataResponse;
        console.log($scope.data);
        $scope.meals = dataResponse.data;
        console.log($scope.meals);
    });
});

myApp.controller('searchMealsController', function($scope, mealService, authService, $routeParams) {
    var token = authService.isAuthenticated();
    var id = $routeParams.id;

    $scope.data = null;
    $scope.meals = null;
    mealService.searchMeals(token, id).then(function(dataResponse) {
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
        if (dataResponse.data.video)
            $scope.videoUrl = dataResponse.data.video;
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
                $location.path('/login');
            console.log(user);
        })
            .error(function(user){
                console.log(user)
            });
    };
}]);


myApp.controller('loginController', function($scope, $http, $location, $cookies) {
    $scope.login = function() {
        var datas = {
            username: $scope.username,
            password: $scope.password
        };
        $http.post('/api/v1/auth/signin', serialize(datas))
            .success(function(data) {
                $cookies.put('token', data.token);
                $cookies.put('username', datas.username);
                console.log($cookies.get('token'));
                $location.path('/');
            })
            .error(function(data) {
                console.log(data);
            });
    };

    $scope.logout = function() {
        console.log("sfsdf");

    }
});

myApp.controller('logoutController', function($rootScope, $location, $cookies) {
    delete $rootScope.token;
    $cookies.remove('token');
    $cookies.remove('username');
    $location.path('/login');
});