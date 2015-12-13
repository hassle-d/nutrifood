/*
**  YILMAZ EMRAH
*/

var mongoose = require('mongoose');

var MealSchema = new mongoose.Schema({
    author: {
    	type: String, 
    	required: true
    },
    date: {
    	type: Date, 
    	default: Date.now, 
    	required: true
    },
    name: {
    	type: String, 
    	required: true
    },
    description: {
    	type: String, 
    	required: true
    },
	difficulty: {
		type: String,
		required: true
	},
	cooktime: {
		type: String,
		required: true
	},
	instruction: {
		type: String,
		required: true
	},
    category: {
    	type: String, 
    	required: true
    },
    ingredients: {
    	type: [String], 
    	required: true
    },
	nutritionfact: {
		type: String,
		default: "none"
	}

});


module.exports = mongoose.model('Meal', MealSchema);