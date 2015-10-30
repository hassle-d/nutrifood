/*
**  YILMAZ EMRAH
*/

var mongoose = require('mongoose');

var MealSchema = new Schema({
    author: String,
    name: String,
    description: String,
    category: String,
    recipices: [String],
    ingredients: [String],
    votes: Number
});


module.exports = mongoose.model('MealSchema');