/*
** Created by HASSLER Damien
*/

var passport = require('passport');
var BasicStrategy = require('passport-http').BasicStrategy;
var BearerStrategy = require('passport-http-bearer').Strategy

var libToken = require('../utils/token');
var User = require('../models/users');
var Client = require('../models/client');
var Token = require('../models/token');


passport.use(new BasicStrategy(
	function(username, password, callback){
		User.findOne({username: username}, function(err, user){
			if (err) { return callback(err); }
			if (!user) { return callback(null, false); }
			user.verifyPassword(password, function(err, isMatch){
				if (err) { return callback(err); }
				if (!isMatch) { return callback(null, false); }
				console.log('Test');
				return callback(null, {userId: user.id, username: user.username, privilege: user.privilege});
			});
		});
	}
));

passport.use('client-basic', new BasicStrategy(
	function(username, password, callback) {
		Client.findOne({ id: username }, function (err, client) {
			if (err) { return callback(err); }
			if (!client || client.secret !== password) { return callback(null, false); }
			return callback(null, client);
		});
	}
));

passport.use(new BearerStrategy(
	function(accessToken, callback) {
		Token.findOne({value: accessToken }, function (err, token) {
			if (err) { return callback(err); }
			if (!token) { return callback(null, false); }
			User.findOne({ _id: token.userId }, function (err, user) {
				if (err) { return callback(err); }
				if (!user) { return callback(null, false); }
				callback(null, user, { scope: '*' });
			});
		});
	}
));

exports.getNewToken = function(req, res, next) {
	if (!req.body.username) { res.status(400).json({message: 'Field username missing'}); return;}
	if (!req.body.password) { res.status(400).json({message: 'Field password missing'}); return;}
	User.findOne({username: req.body.username}, function(err, user) {
		if (err) { res.status(500).json({message: 'Server Error'}); return; }
		if (!user) { res.status(400).json({message: 'Wrong username or/and password'}); return; }
		user.verifyPassword(req.body.password, function(err, isMatch){
				if (err) { res.status(500).json({message: 'Server Error'}); return; }
				if (!isMatch) { res.status(400).json({message: 'Wrong username or/and password'}); return; }
				var token = libToken.createToken();
				var myToken = new Token({
					value: token,
					username: user.username,
					date: new Date()
				});
				myToken.save(function(err) {
					if (err) { res.status(500).json({message: 'Server Error'}); return; }
					res.json({token:token});
				});
		});
	});
};

exports.logout = function(req, res, next) {
	var token = req.get('token');
	if (!token) { res.status(401).json({message: 'Missing token'}); return; }
	Token.findOneAndRemove({value: token}, function(err, count) {
		if (err) {
			res.json(err);
		}
		else {
			res.json(count);
		}
	});

};

exports.isValidToken = function(req, res, next) {
	var token = req.get('Authorization');
	if (!token) { res.status(401).json({message: 'Missing token'}); return; }
	Token.findOne({value: token}, function(err, myToken) {
		if (err) { res.status(500).json({message: 'Server Error'}); return; }
		if (!myToken) { res.status(401).json({message: 'Invalid or expired token'}); return; }
		User.findOne({username: myToken.username}, function(err, user) {
			if (err) { res.status(500).json({message: 'Server Error'}); return; }
			if (!user) { res.status(401).json({message:'Invalid token'}); return; }
			req.user = {userId: user.id, username: user.username, privilege: user.privilege};
			myToken.date = new Date();
			myToken.save(function(err) {
				if (err) { res.status(500).json({message: 'Server Error'}); return; }
				else { next(); }
			});
		});
	});
};

exports.isAdmin = function(req, res, next) {
	if (req.user && req.user.privilege && req.user.privilege == "admin")
		next();
	else
		res.status(401).json({message: 'Need to be admin'});
};

exports.isAuthenticated = passport.authenticate('basic', {session: false});
exports.isClientAuthenticated = passport.authenticate('client-basic', {session: true});
exports.isBearerAuthenticated = passport.authenticate('bearer', { session: false });