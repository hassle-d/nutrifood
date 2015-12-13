/**
 * Created by Yilmaz Emrah on 30/10/2015.
 */

var fs = require('fs');
var Meal = require('../models/meals');

exports.postMeals = function(req, res) {
    var meal = new Meal({
        author: req.body.author,
        date: new Date(),
        name: req.body.name.toLowerCase(),
        description: req.body.description,
        video: req.body.video,
        instruction: req.body.instruction,
        difficulty: req.body.difficulty,
        cooktime: req.body.cooktime,
        category: req.body.category.toLowerCase(),
        ingredients: req.body.ingredients,
        nutritionfact: req.body.nutritionfact
    });

    meal.save(function(err, meal){
        if (err)
            res.send(err);
        else {
            if (req.file) {
                console.log(req.file);
            }            
            res.json({message: 'Meal added'});
        }
    });
};

exports.getImage = function(req, res) {
    var data = fs.readFileSync('./uploads/22bc5c3fad165b6b13fd28e87c4e7f8a');
    res.contentType('image/png');
    res.send(data);
};

exports.setImage = function(req, res) {
    console.log(req.file);
    res.end();
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
    Meal.findById(req.params.id, function(err, meal){
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