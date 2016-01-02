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
            })
            .error(function(data) {
                console.log(data);
            });
    };

    $scope.logout = function() {

    }
}]);