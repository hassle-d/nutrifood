/*
** Created by HASSLER Damien
*/

var mongoose = require('mongoose');

var MealVoteSchema = new mongoose.Schema({
	mealId: {
		type: String,
		required: true
	},
	note: {
		type: Number,
		required: true
	}
});

module.exports = mongoose.model('MealVote', MealVoteSchema);
