/*
** Created by HASSLER Damien
*/

var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var port = process.env.PORT || 3000;
var passport = require('passport');
var router = express.Router();

var apiVersion = 1;

//-S-- ROUTES FILES
var user = require('./routes/users');
var meal = require('./routes/meals');
//-E-- ROUTES FILES

mongoose.connect('mongodb://localhost:27017/nutrifood');

app.use(bodyParser.urlencoded({
	extended: true
}));

app.use(passport.initialize());
<<<<<<< HEAD
app.use('/api/v' + apiVersion, user, router);
=======
var router = express.Router();
app.use('/api', user, router);
app.use('/api', meal, router);
>>>>>>> b30e6d2c30affe1ffea3978695ea9d5fad17b0c5

app.listen(port);
