/*
**  Damien Hassler
*/

var mongoose = require('mongoose');

var MealCommentSchema = new mongoose.Schema({
    meal: {
    	type: String,
        required: true
    },
    author: {
        type: String,
        required: true
    },
    date: {
    	type: Date,
        required: true
    },
    comment: {
        type: String,
        required: true
    }
});

module.exports = mongoose.model('MealComment', MealCommentSchema);