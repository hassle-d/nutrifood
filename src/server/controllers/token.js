/*
** Created by HASSLER Damien
*/

var Token = require('../models/token');

exports.getTokens = function(req, res) {
	Token.find(function(err, token) {
		if (err)
			res.send(err);
		else
			console.log(req.user);
			res.json(token);
	});
};
