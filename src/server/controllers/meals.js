/**
 * Created by Yilmaz Emrah on 30/10/2015.
 */

var Meal = require('../models/meals');

exports.postMeals = function(req, res) {
    var meal = new Meal({
        author: req.body.author,
        date: new Date(),
        name: req.body.name.toLowerCase(),
        description: req.body.description,
        instruction: req.body.instruction,
        difficulty: req.body.difficulty,
        cooktime: req.body.cooktime,
        category: req.body.category.toLowerCase(),
        ingredients: req.body.ingredients,
        nutritionfact: req.body.nutritionfact
    });

    meal.save(function(err){
        if (err)
            res.send(err);
        else
            res.json({message: 'Meal added'});
    });
};

exports.getMeals = function(req, res) {
    Meal.find(function(err, meals){
        if (err)
            res.send(err);
        else
            res.json(meals);
    })
};

exports.getMealById = function(req, res) {
    Meal.findById(req.params.id, 'id author name description recipices ingredients votes', function(err, meal){
        if (err)
            res.send(err);
        else
            res.json(meal);
    });
};

exports.getMealByName = function(req, res) {
    Meal.findOne({'name': req.params.name.toLowerCase()}, function(err, meal){
        if (err)
            res.send(err);
        else
            res.json(meal);
    });
};

exports.getMealByCategory = function(req, res) {
    Meal.find({'category': req.params.category.toLowerCase()}, function(err, meal){
        if (err)
            res.send(err);
        else
            res.json(meal);
    });
};