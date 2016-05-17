/*
** Created by HASSLER Damien
*/

var mongoose = require('mongoose');

var BookmarkSchema = new mongoose.Schema({
	meal: {
		type: String,
		required: true
	},
	user: {
		type: String,
		required: true
	}
});

module.exports = mongoose.model('Bookmark', BookmarkSchema);
