/*
** Created by HASSLER Damien
*/

var express = require('express');
var path 	= require('path');
var router = express.Router();
var views = path.join(__dirname, '../views/');

router.get('/', function(req, res, next) {
  res.writeHead(301, {Location: '/index'});
  res.end();
});

router.get('/index', function(req, res, next) {
  res.render( 'index', { title: 'Index' } );
});

router.get('/test', function(r, q, n) {
	q.sendFile(views + 'test.html');
});

router.get('/login', function(r, q, n){
  q.sendFile(views + 'login.html')
});

router.get('/home', function(r, q, n) {
  q.sendFile(views + 'home.html')
})

router.get('/register', function(r, q, n){
  q.sendFile(views + 'register.html');
});

router.get('/submit-recipe', function(r,q,n){
  q.sendFile(views + 'recipe_submit.html');
});

router.get('/category', function(r,q,n){
  q.sendFile(views + 'category.html')
});




module.exports = router;
