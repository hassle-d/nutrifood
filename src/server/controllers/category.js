/*
** Created by HASSLER Damien
*/

var Category = require('../models/category');

exports.postCategory = function(req, res) {
	if ((!req.body.name) || req.body.name.length == 0)
		{ error = true; res.status(400).json({name: 'ValidationError', message: 'Missing category name'}); }
	else {
		var category = new Category({ name: req.body.name });
		category.save(function(err, cat) {
			if (err)
				res.status(500).send();
			else
				res.status(201).send("OK");
		});
	}
};

exports.getCategory = function(req, res) {
	Category.find(function(err, cat) {
		if (err)
			res.send(err);
		else
			res.json(cat);
	});
};

exports.getCategoryById = function(req, res) {
	Category.findById(req.params.id, function(err, cat) {
		if (err)
			res.send(err);
		else
			res.json(cat);
	});
};

exports.deleteCategoryById = function(req, res) {
	Category.findByIdAndRemove(req.params.id, function(err, count) {
		if (err)
			res.send(err);
		else
			res.json(count);
	});
};
