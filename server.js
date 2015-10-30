/*
** Created by HASSLER Damien
*/

var express = require('express');
var app = express();
var mongoose = require('mongoose');
var port = process.env.PORT || 3000;
var router = express.Router();
var passport = require

mongoose.connect('mongodb://localhost:27017/nutrifood');

//-S-- ROUTES FILES
var user = require('./routes/users');
//-E-- ROUTES FILES

app.use('/api', user, router);

app.listen(port);
console.log('Nutrifood server is running on port ' + port);