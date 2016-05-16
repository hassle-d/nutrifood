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
			username: req.body.username.toLowerCase(),
			password: req.body.password,
			email: req.body.email,
			firstname: req.body.firstname,
			lastname: req.body.lastname,
			description: req.body.description,
			age: req.body.age,
			date: new Date(),
			allergy: req.body.allergy,
			specialFood: req.body.special
		});
		user.save(function(err, user) {
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

exports.getProfil = function(req, res) {
	User.findById(req.user.userId, function(err, user) {
		if (err)
			res.status(500).send(err);
		else
			res.json(user);
	});	
};

exports.updateProfil = function(req, res) {
	if (!req.user.userId) { res.status(400).json({name: 'ValidationError', message: 'MissingFields'}); return;}
	var updateFields = {};
	if (req.body.password)
		updateFields.password = req.body.password;
	if (req.body.email)
		updateFields.email = req.body.email;
	if (req.body.firstname)
		updateFields.firstname = req.body.firstname;
	if (req.body.lastname)
		updateFields.lastname = req.body.lastname;
	if (req.body.description)
		updateFields.description = req.body.description;
	if (req.body.age)
		updateFields.age = req.body.age;
	if (req.body.allergy)
		updateFields.allergy = req.body.allergy;
	if (req.body.special)
		updateFields.special = req.body.special;
	console.log(updateFields);
	User.update({_id: req.user.userId}, updateFields, function (err, doc){
		if (err) { res.json(err); return; }
		res.json(doc);
	});
};

exports.getUsers = function(req, res) {
	User.find(function(err, users) {
		if (err)
			res.status(500).send(err);
		else
			res.json(users);
	}).sort({username:1}).skip(null).limit(1);
};

exports.getUserById = function(req, res) {
	User.findById(req.params.id, function(err, user) {
		if (err)
			res.status(500).send(err);
		else
			res.json(user);
	});
};

exports.getUserByUsername = function(req, res) {
	User.findOne({'username': req.params.username.toLowerCase()}, function(err, user) {
		if (err)
			res.status(500).send(err);
		else
			res.json(user);
	});
};

exports.updateUser = function(req, res) {
	if (!req.params.id) { res.status(400).json({name: 'ValidationError', message: 'MissingFields'}); return;}
	updateFields = {};
	if (req.body.password)
		updateFields.password = req.body.password;
	if (req.body.email)
		updateFields.email = req.body.email;
	if (req.body.firstname)
		updateFields.firstname = req.body.firstname;
	if (req.body.lastname)
		updateFields.lastname = req.body.lastname;
	if (req.body.description)
		updateFields.description = req.body.description;
	if (req.body.age)
		updateFields.age = req.body.age;
	if (req.body.allergy)
		updateFields.allergy = req.body.allergy;
	if (req.body.special)
		updateFields.special = req.body.special;
	if (req.body.privilege && req.user && req.user.privilege == 'admin')
		updateFields.privilege = req.body.privilege;
	console.log(updateFields);
	User.update({_id: req.params.id}, updateFields, function (err, doc){
		if (err) { res.json(err); return; }
		res.json(doc);
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
