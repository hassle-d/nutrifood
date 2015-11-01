/*
**  YILMAZ EMRAH
*/

var mongoose = require('mongoose');

var MealSchema = new mongoose.Schema({
    author: {type: String, required: true},
    date: {type: Date, default: Date.now, required: true},
    name: {type: String, required: true},
    description: {type: String, required: true},
    category: {type: String, required: true},
    recipices: {type: [String], required: true},
    ingredients: {type: [String], required: true},
    votes: {type: Number}
});


module.exports = mongoose.model('Meal', MealSchema);