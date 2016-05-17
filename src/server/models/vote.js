/*
** Created by HASSLER Damien
*/

var mongoose = require('mongoose');

var VoteSchema = new mongoose.Schema({
	meal: {
		type: String,
		required: true
	},
	note: {
		type: Number,
		required: true
	},
	user: {
		type: String,
		required: true
	}
});

module.exports = mongoose.model('Vote', VoteSchema);
