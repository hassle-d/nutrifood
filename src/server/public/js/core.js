var myApp = angular.module('nutrifood',[])
        .config(function ($httpProvider) {
            $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
            $httpProvider.defaults.headers.post['Content-Type'] =  'application/x-www-form-urlencoded';
        })

serialize = function(obj) {
  var str = [];
  for(var p in obj)
    if (obj.hasOwnProperty(p)) {
      str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
    }
  return str.join("&");
}

var token = "";

myApp.controller('recipeSubmitController', ['$scope', '$http', function($scope, $http){
    $scope.recipes = function() {

        console.log("lol");

        var recipe = {
            author: $scope.author,
            date: Date.now,
            name: $scope.name,
            description: $scope.description,
            instruction: $scope.instruction,
            difficulty: $scope.difficulty,
            category: $scope.category,
            ingredients: $scope.ingredients,
            cooktime: $scope.cooktime
        };

        console.log(recipe);
        $http.post('/api/v1/meals', serialize(recipe))
            .success(function(recipe){
                console.log(recipe);
            })
            .error(function(recipe){
                console.log(recipe)
            });
    };
}]);

myApp.service('mealService', function($http) {
    delete $http.defaults.headers.common['X-Requested-With'];
    this.getData = function() {
        return $http({
            method: 'GET',
            url: 'http://localhost:3000/api/v1/meals'
           // headers: {'Authorization': 'Token token=' + token}
        });
    }
});

myApp.controller('mealsController', function($scope, mealService) {
    $scope.data = null;
    $scope.meals = null;
    mealService.getData().then(function(dataResponse) {

        $scope.data = dataResponse;
        console.log($scope.data);
        $scope.meals = dataResponse.data;
        console.log($scope.meals);
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