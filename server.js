/*
** Created by HASSLER Damien
*/

var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var port = process.env.PORT || 3000;
var passport = require('passport');

mongoose.connect('mongodb://localhost:27017/nutrifood');

//-S-- ROUTES FILES
var user = require('./routes/users');
var meal = require('./routes/meals');
//-E-- ROUTES FILES

app.use(bodyParser.urlencoded({
	extended: true
}));
app.use(passport.initialize());
var router = express.Router();
app.use('/api', user, router);
app.use('/api', meal, router);

app.listen(port);
console.log('Nutrifood server is running on port ' + port);