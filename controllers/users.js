/*
** Created by HASSLER Damien
*/

var User = require('../models/users');

exports.postUsers = function(req, res) {
	var user = new User({
		username: req.body.username,
		password: req.body.password
	});
	user.save(function(err) {
		if (err)
			res.send(err);
		else
			res.json({message: 'User added'});
	});
};

exports.getUsers = function(req, res) {
	User.find(function(err, users) {
		if (err)
			res.send(err);
		else
			res.json(users);
	});
};

exports.getUserById = function(req, res) {
	User.findById(req.params.id, 'id username lastname firstname', function(err, user) {
		if (err)
			res.send(err);
		else
			res.json(user);
	});
};

exports.getUserByUsername = function(req, res) {
	User.findOne({'username': req.params.username}, function(err, user) {
		if (err)
			res.send(err);
		else
			res.json(user);
	});
};

exports.delUserById = function(req, res) {
	User.findByIdAndRemove(req.params.id, function(err, count) {
		if (err)
			res.send(err);
		else
			res.json(count);
	});
};