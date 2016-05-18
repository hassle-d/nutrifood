var myApp = angular.module('nutrifood', ['ngRoute', 'ngCookies', 'youtube-embed', 'bootstrap.fileField'])

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
                    controller : ''
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
                .when('/meals/bookmark',{
                    templateUrl : 'views/meals.html',
                    controller: 'bookmarkMealsController'
                })
                .when('/meals/search/:id',{
                    templateUrl : 'views/meals.html',
                    controller: 'searchMealsController'
                })
                .when('/meals/category/:id',{
                    templateUrl : 'views/meals.html',
                    controller: 'categoryMealsController'
                })
                .when('/mymeals/:author', {
                    templateUrl: 'views/mymeals.html',
                    controller: 'myMealsController'
                })
                .when('/meal/:id', {
                    templateUrl : 'views/meal.html',
                    controller : 'mealController'
                });
});

myApp.directive('myEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.myEnter);
                });

                event.preventDefault();
            }
        });
    };
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
});

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


myApp.controller('bookmarkMealsController', function($scope, $http, $cookies, $location, authService, mealService){
    var token = authService.isAuthenticated();
    $scope.title = "Boomarked Meals";
    mealService.bookmarkedMeals(token).then(function(dataResponse) {

        var data = dataResponse.data;

        for (var i = 0, j = data.length; i < j; i++) {
            data[i].name = data[i].name.charAt(0).toUpperCase() + data[i].name.slice(1);
        }

        $scope.meals = data;
        console.log($scope.meals);
    });
})

myApp.controller('myMealsController', function($scope, $http, $cookies, $location, authService, mealService){
    var token = authService.isAuthenticated();
    var username = $cookies.get('username');
    console.log(username);
    mealService.ownerMeals(token, username).then(function(dataResponse){
        var data = dataResponse.data;
        $scope.meals = data;
        console.log('lfoloerlferf' + JSON.stringify(dataResponse));
    });
});

myApp.controller('recipeSubmitController', function($scope, $http, $cookies, $location, authService){
    var token = authService.isAuthenticated();
    $scope.recipes = function() {
        var image = $scope.uploadFile;

        console.log($cookies.get('username'));
        var fd = new FormData();

        var ingredients  = [];
        for (var i = 0; i < $scope.ingredients.length; i++) {
            ingredients.push(($scope.ingredients[i]).val);
        }

        console.log(ingredients);

        fd.append('image', image);
        fd.append('author', $cookies.get('username'));
        fd.append('date', Date.now);
        fd.append('video', $scope.video);
        fd.append('name', $scope.name);
        fd.append('description', $scope.description);
        fd.append('instruction', $scope.instruction);
        fd.append('nutritionfact', $scope.nutritionfact);
        fd.append('difficulty', $scope. difficulty);
        fd.append('category', $scope.category);
        fd.append('ingredients', JSON.stringify(ingredients));
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
    $scope.addIngredient = function() {
        if ($scope.ingredientInput && $scope.ingredientInput != "") {
            var ingredients = $scope.ingredients;
            if (ingredients == null) {
                ingredients = [];
            }
            ingredients.push({val:$scope.ingredientInput});
            $scope.ingredients = ingredients;
            $scope.ingredientInput = "";
            document.getElementById("test").focus();
        }
    };
    $scope.deleteIngredient = function(id) {
        $scope.ingredients.splice(id, 1);
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

myApp.controller('editMealController', function($scope, $http, $cookies, profileService, authService, $routeParams){
    var token = authService.isAuthenticated();
    $scope.meal = null;
    var id = $routeParams.id;
    delete $http.defaults.headers.common['X-Requested-With'];
    $http({
        method: 'GET',
        url: '/api/v1/meals/' + id,
        headers: {'Authorization': token}
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


    $scope.updateMeal = function(){


        var fd = new FormData();

        var recipe = {
            image: $scope.image,
            author: $cookies.get('username'),
            date: Date.now,
            name: $scope.name,
            description: $scope.description,
            instruction: $scope.instruction,
            difficulty: $scope.difficulty,
            category: $scope.category,
            ingredients: JSON.stringify($scope.ingredients),
            cooktime: $scope.cooktime,
            video: $scope.video
        };

        fd.append('image', $scope.image);
        fd.append('author', "admin");
        fd.append('date', Date.now);
        fd.append('video', $scope.video);
        fd.append('name', $scope.name);
        fd.append('description', $scope.description);
        fd.append('instruction', $scope.instruction);
        fd.append('difficulty', $scope. difficulty);
        fd.append('category', $scope.category);
        fd.append('ingredients',JSON.stringify($scope.ingredients));
        fd.append('cooktime', $scope.cooktime);
        console.log(recipe);

        $http({
                method: 'PUT',
                transformRequest: angular.identity,
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': token },
                url: '/api/v1/meals/' + id,
                data: serialize(recipe)
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
    };
    this.ownerMeals = function(token, author) {
        return $http({
           method: 'GET',
            url: '/api/v1/meals/mymeals/' + author,
            headers: {'Authorization': token}
        });
    };
    this.searchMeals = function(token, name) {
        return $http({
            method: 'GET',
            url: '/api/v1/meals/name/' + name,
            headers: {'Authorization': token}
        });
    };
    this.updateMeal = function(token, name) {

    };
    this.categoryMeals = function(name) {
        return $http({
            method: 'GET',
            url: '/api/v1/meals/category/' + name,
        });
    };
    this.bookmarkedMeals = function(token) {
        return $http({
            method: 'GET',
            url: '/api/v1/bookmark',
            headers: {'Authorization': token}
        });
    };
});

myApp.controller('homeController', function($rootScope, $scope, mealService, authService, $cookies) {
    $scope.meals = null;
    $scope.title = "Recipes Timeline"
    var token = $cookies.get("token");
    $rootScope.token = token;
    $rootScope.usermeal = $cookies.get('username');
    mealService.getData(token).then(function(dataResponse) {

        var data = dataResponse.data;

        for (var i = 0, j = data.length; i < j; i++) {
            data[i].name = data[i].name.charAt(0).toUpperCase() + data[i].name.slice(1);
        }

        $scope.meals = data;
        console.log($scope.meals);
    });
});

myApp.controller('searchMealsController', function($scope, mealService, authService, $routeParams) {
    var token = authService.isAuthenticated();
    var id = $routeParams.id;
    $scope.title = "Search results";

    $scope.meals = null;
    mealService.searchMeals(token, id).then(function(dataResponse) {

        var data = dataResponse.data;

        for (var i = 0, j = data.length; i < j; i++) {
            data[i].name = data[i].name.charAt(0).toUpperCase() + data[i].name.slice(1);
        }

        $scope.meals = data
        console.log($scope.meals);
    });
});

myApp.controller('categoryMealsController', function($scope, mealService, authService, $routeParams) {
    var id = $routeParams.id;
    $scope.title=id.charAt(0).toUpperCase() + id.slice(1) + " food";
    $scope.meals = null;
    mealService.categoryMeals(id).then(function(dataResponse) {

        var data = dataResponse.data;

        for (var i = 0, j = data.length; i < j; i++) {
            data[i].name = data[i].name.charAt(0).toUpperCase() + data[i].name.slice(1);
        }

        $scope.meals = dataResponse.data;
        console.log($scope.meals);
    });
});

myApp.controller('mealController', function($scope, $http, $routeParams, $cookies, $location, authService) {
    var token = authService.isAuthenticated();
    var id = $routeParams.id;
    console.log('ID = ' + id);
    var getComment = function(){
        $http({
            method: 'GET',
            url: '/api/v1/comment/' + id
        }).then(function(dataResponse) {
            console.log(dataResponse.data);
            $scope.comments = dataResponse.data;
            $scope.nbComment = dataResponse.data.length;
        });
    };

    var  getMeal = function(){
        delete $http.defaults.headers.common['X-Requested-With'];
        $http({
            method: 'GET',
            url: '/api/v1/meals/' + id,
            headers: {'Authorization': token}
        }).then(function(dataResponse) {
            console.log(dataResponse.data);
            $scope.meal = dataResponse.data;
            if (dataResponse.data.video)
                $scope.videoUrl = dataResponse.data.video;
        });
    }
    $scope.addVote = function(note) {
        var fd = new FormData();
        fd.append('note', note);
        $http.post('/api/v1/vote/' + id, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined, 'Authorization': token}
        })
        .success(function(){
            console.log('ok');
            getMeal();
        })
        .error(function(){
        });
    };

    $scope.addBookmark = function() {
        $http.post('/api/v1/bookmark/' + id, {}, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined, 'Authorization': token}
        })
        .success(function(){
            console.log('ok');
            $scope.meal.bookmarked = true;
        })
        .error(function(){
        });
    };

    $scope.delBookmark = function() {
        $http.delete('/api/v1/bookmark/' + id, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined, 'Authorization': token}
        })
        .success(function(){
            console.log('ok');
            $scope.meal.bookmarked = false;
        })
        .error(function(){
        });
    };

    getMeal();
    getComment();

    $scope.addComment = function (){

        var fd = new FormData();
        fd.append('author', $cookies.get('username'));
        fd.append('comment', $scope.content);
        $http.post('/api/v1/comment/' + id, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined, 'Authorization': token}
        })
            .success(function(comment){
                console.log(comment);
                getComment();

            })
            .error(function(comment){
                console.log(comment)
            });
    };
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
    $location.path('/');
});
