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
//-E-- ROUTES FILES

mongoose.connect('mongodb://localhost:27017/nutrifood');

app.use(bodyParser.urlencoded({
	extended: true
}));

app.use(passport.initialize());
app.use('/api/v' + apiVersion, user, router);

app.listen(port);
