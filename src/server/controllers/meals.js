/**
 * Created by Yilmaz Emrah on 30/10/2015.
 */

var fs = require('fs');
var Meal = require('../models/meals');
var MealImage = require('../models/mealsImage');

exports.postMeals = function(req, res) {
        image = null;
    if (req.file)
        image = req.file.filename;
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
        nutritionfact: req.body.nutritionfact,
        image: image
    });

    meal.save(function(err, meal){
        if (err)
            res.send(err);
        else {
            if (req.file) {
                var mealImage = new MealImage({
                    filename: req.file.filename,
                    type: req.file.mimetype
                });
                mealImage.save(function(err) {
                    if (err)
                        res.send(err);
                    else
                        res.json({message: 'Meal added'});
                });
            }
        }
    });
};

exports.getImage = function(req, res) {
    file = req.params.filename;
    MealImage.findOne({'filename': file}, function(err, img) {
        if (err)
            res.send(err);
        else {
            var data = fs.readFileSync('./uploads/' + file);
            res.contentType();
            res.send(data);
        }
    });
    //fs.unlinkSync('./uploads/e72aca600d53aa37789740f692f72260')
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