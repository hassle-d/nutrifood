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
    video: {
        type: String,
        default: null
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
		default: null
	}

});


module.exports = mongoose.model('Meal', MealSchema);