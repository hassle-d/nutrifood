/*
** Created by HASSLER Damien
*/

//-S-- CONFIG CONSTANTS VARS
var dbServer	= 'localhost';
var dbPort		= '27017';
var dbName		= 'nutrifood';
var apiVersion 	= 1;
var done		= false;
//-E-- CONFIG CONSTANTS VARS

var express		= require('express');
var path 		= require('path');
var session 	= require('express-session');
var bodyParser 	= require('body-parser');
var mongoose 	= require('mongoose');
var passport 	= require('passport');
var multer		= require('multer');

var port 		= process.env.PORT || 3000;
var app 		= express();
var router 		= express.Router();

//-S-- ROUTES FILES
var frontroutes	= require('./routes/front');
var user 		= require('./routes/users');
var client 		= require('./routes/client');
var meal 		= require('./routes/meals');
var auth		= require('./routes/auth');
var token		= require('./routes/token');
var oauth		= require('./routes/oauth2');
var category	= require('./routes/category');
var comment		= require('./routes/comment');
var vote		= require('./routes/vote');
var bookmark	= require('./routes/bookmark');
//-E-- ROUTES FILES

mongoose.connect('mongodb://' + dbServer + ':' + dbPort + '/' + dbName);

app.set('views', path.join(__dirname, 'views'));

app.use(express.static(path.join(__dirname, 'public')));

app.use(multer({dest:'./uploads/'}).single('image'));

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
	token,
	oauth,
	category,
	comment,
	vote,
	bookmark,
	router);

app.use('/', frontroutes);

app.listen(port);
