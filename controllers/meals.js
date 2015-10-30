/**
 * Created by Yilmaz Emrah on 30/10/2015.
 */

var Meal = require('../models/meals');

exports.postMeals = function(req, res) {
    var meal = new Meal({
        author: req.body.author,
        date: new Date(),
        name: req.body.name,
        description: req.body.description,
        category: req.body.category,
        recipices: req.body.recipices,
        ingredients: req.body.ingredients,
        votes: req.body.votes

    });

    meal.save(function(err){
        if (err)
            res.send(err);
        else
            res.json({message: 'Meal added'});
    });
}