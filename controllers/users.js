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

exports.test = function(req, res) {
	res.json({message: 'Hello World!'});
};
