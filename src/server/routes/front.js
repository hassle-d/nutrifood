/*
** Created by HASSLER Damien
*/

var express = require('express');
var router = express.Router();

router.get('/', function(req, res, next) {
  res.writeHead(301, {Location: '/index'});
  res.end();
});

router.get('/index', function(req, res, next) {
  res.render( 'index', { title: 'Index' } );
});

module.exports = router;
