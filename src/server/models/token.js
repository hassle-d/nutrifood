/*
** Created by HASSLER Damien
*/

var mongoose = require('mongoose');

var TokenSchema = new mongoose.Schema({
	value: {
		type: String,
		unique: true,
		required: true },
	username: {
		type: String,
		required: true
	},
	date: {
		type: Date,
	}
});

module.exports = mongoose.model('Token', TokenSchema);
