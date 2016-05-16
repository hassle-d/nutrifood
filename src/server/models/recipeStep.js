/*
** Created by HASSLER Damien
*/

var mongoose = require('mongoose');

var RecipeStepSchema = new mongoose.Schema({
	mealId: {
		type: String,
		required: true
	},
	contentType: {
		type: String, // Can be video or text
		default: 'text',
		required: true
	},
	content: {
		type: String,
		required: true

	}
});

module.exports = mongoose.model('RecipeStep', RecipeStepSchema);