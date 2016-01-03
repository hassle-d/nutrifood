/**
 * Created by Yilmaz Emrah on 30/10/2015.
 */

var fs = require('fs');
var Meal = require('../models/meals');
var Image = require('../models/image');

exports.postMeals = function(req, res) {
    image = null;
    console.log(req.file);
    if (req.file)
        image = req.file.filename;
    var meal = new Meal({
        author: req.body.author.toLowerCase(),
        date: new Date().toISOString(),
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
                var mealImage = new Image({
                    filename: req.file.filename,
                    type: req.file.mimetype,
                });
                if (image){
                    mealImage.save(function(err) {
                        if (err)
                            res.status(500).send(err);
                    });
                }
            }
            res.json({message: 'Meal added'}).status(201);
        }
    });
};

exports.updateMeal = function(req, res) {
    var updateFields = {};
    image = null;

    if (req.file)
        updateFields.image = req.file.filename;
    if (req.body.author)
        updateFields.author = req.body.author;
    if (req.body.name)
        updateFields.name = req.body.name.toLowerCase();
    if (req.body.description)
        updateFields.description = req.body.description;
    if (req.body.video)
        updateFields.video = req.body.video;
    if (req.body.instruction)
        updateFields.instruction = req.body.instruction;
    if (req.body.difficulty)
        updateFields.difficulty = req.body.difficulty;
    if (req.body.cooktime)
        updateFields.cooktime = req.body.cooktime;
    if (req.body.category)
        updateFields.category = req.body.category;
    if (req.body.ingredients)
        updateFields.ingredients = req.body.ingredients;
    if (req.body.nutritionfact)
        updateFields.nutritionfact = req.body.nutritionfact;

    Meal.update({_id: req.params.id}, updateFields, function (err, doc){
        if (req.file)
            image = req.file.filename;
        if (err) { res.json(err); return; }
        else{
        var mealImage = new Image({
            filename: req.file.filename,
            type: req.file.mimetype
        });
        if (image){
            mealImage.save(function(err) {
                if (err)
                    res.status(500).send(err);
            });
        }
        res.json(doc);
        }
    });


};

exports.getImage = function(req, res) {
    var file = req.params.filename;
    console.log(file);
    Image.findOne({'filename': file}, function(err, img) {
        if (err)
            res.send(err);
        else {
            if (img) {
                var data = fs.readFileSync('./uploads/' + file);
                res.contentType(img.type);
                res.send(data);                
            }
            else
                res.status(404).send();
        }
    });
    //fs.unlinkSync('./uploads/e72aca600d53aa37789740f692f72260')
};

exports.getMeals = function(req, res){
    var options = null;
    var opsort = null;
    if (req.query.orderby) {
        var field = req.query.orderby;
        var order = '';
        if (req.query.order == 'desc')
            order = '-';
        opsort = order+field;
        console.log(opsort);
    }
    if (req.query.overview !== undefined)
        options = 'author name difficulty cooktime image category'
    Meal.find({}, options, {sort: opsort}, function(err, meals){
        if (err)
            res.send(err);
        else
            res.json(meals);
    });
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
    var regex = new RegExp(req.params.name.toLowerCase(), 'i');
    Meal.find({'name': regex}, function(err, meal){
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