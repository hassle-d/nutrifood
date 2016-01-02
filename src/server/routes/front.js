/*
** Created by HASSLER Damien
*/

var express = require('express');
var path 	= require('path');
var router = express.Router();
var views = path.join(__dirname, '../views/');

router.get('/', function(req, res, next) {
  res.sendFile(views + 'index.html');
});

module.exports = router;
