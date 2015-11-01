/*
** Created by HASSLER Damien
*/

//-S-- CONFIG CONSTANTS VARS
var dbServer	= 'localhost';
var dbPort		= '27017';
var dbName		= 'nutrifood';
var apiVersion 	= 1;
//-E-- CONFIG CONSTANTS VARS

var express		= require('express');
var session 	= require('express-session');
var bodyParser 	= require('body-parser');
var mongoose 	= require('mongoose');
var passport 	= require('passport');
var port 		= process.env.PORT || 3000;
var app 		= express();
var router 		= express.Router();

//-S-- ROUTES FILES
var user 		= require('./routes/users');
var client 		= require('./routes/client');
var meal 		= require('./routes/meals');
var auth		= require('./routes/auth');
var oauth		= require('./routes/oauth2');
//-E-- ROUTES FILES

mongoose.connect('mongodb://' + dbServer + ':' + dbPort + '/' + dbName);

app.use(bodyParser.urlencoded({
	extended: true
}));

app.use(session({
	secret: 'lama',
	saveUninitialized: true,
	resave: true
}));
app.use(passport.initialize());
app.use(passport.session());
app.use('/api/v' + apiVersion,
	user,
	client,
	meal,
	auth,
	oauth,
	router);

app.listen(port);
