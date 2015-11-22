/*
** Created by HASSLER Damien
*/

var express = require('express');
var path 	= require('path');
var router = express.Router();

router.get('/', function(req, res, next) {
  res.writeHead(301, {Location: '/index'});
  res.end();
});

router.get('/index', function(req, res, next) {
  res.render( 'index', { title: 'Index' } );
});

router.get('/test', function(r, q, n) {
	q.sendFile(path.join(__dirname, '../views/test.html'));
});

module.exports = router;
