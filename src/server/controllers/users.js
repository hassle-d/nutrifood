/*
** Created by HASSLER Damien
*/

var User = require('../models/users');

exports.postUsers = function(req, res) {
	var error = false;
	if (req.body.password.length < 8)
		{ error = true; res.status(400).json({name: 'ValidationError', message: 'PasswordTooShort'}); }
	if (!error) {
		var user = new User({
			username: req.body.username,
			password: req.body.password,
			email: req.body.email,
			firstname: req.body.firstname,
			lastname: req.body.lastname,
			description: req.body.description,
			age: req.body.age,
			date: new Date(),
			allergy: req.allergy,
			religion: req.religion
		});
		user.save(function(err) {
			if (err)
				if (err.name && err.name == 'ValidationError')
					res.status(400).json({name: 'ValidationError', message: 'MissingFields'});
				else if (err.code && err.code == 11000)
					res.status(400).json({name: 'ValidationError', message: 'UsernameAlreadyExist'});
				else
					res.status(409).send(err);
			else
				res.status(201).json({message: 'User added'});
		});
	}
};

exports.getUsers = function(req, res) {
	User.find(function(err, users) {
		if (err)
			res.send(err);
		else
			console.log(req.user);
			res.json(users);
	});
};

exports.getUserById = function(req, res) {
	User.findById(req.params.id, function(err, user) {
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

exports.updateUser = function(req, res) {
	if (!req.body.id) { res.status(400).json({name: 'ValidationError', message: 'MissingFields'}); return;}
	User.findById(req.body.id, function(err, user) {
		if (err) { res.send(err); return; }
		if (req.body.email)
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
