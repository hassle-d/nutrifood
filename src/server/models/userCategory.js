/*
** Created by HASSLER Damien
*/

var mongoose = require('mongoose');

var UserCategorySchema = new mongoose.Schema({
	userId: {
		type: Number,
		required: true,
	},
	type: {
		type: String,
		required: true
		default: 'user' // Can be user, professional, admin
	}
});

module.exports = mongoose.model('UserCategory', UservCategorySchema);