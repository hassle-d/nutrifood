/*
**  Damien Hassler
*/

var mongoose = require('mongoose');

var MealImageSchema = new mongoose.Schema({
    filename: {
        type: String
    },
    type: {
        type: String
    }
});

module.exports = mongoose.model('MealImage', MealImageSchema);