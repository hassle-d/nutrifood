/**
 * Created by Yilmaz Emrah on 30/10/2015.
 */

var fs = require('fs');
var Meal = require('../models/meals');
var Image = require('../models/image');
var Vote = require('../models/vote');
var Bookmark = require('../models/bookmark');
var Comment = require('../models/comment');

exports.postMeals = function(req, res) {
    image = null;
    console.log(req.file);
    console.log(req.body.author);
    console.log("INGREDIENTS: " + req.body.ingredients);
    if (req.file)
        image = req.file.filename;
    var meal = new Meal({
        author: req.body.author.toLowerCase(),
        date: new Date().toISOString(),
        name: req.body.name.toLowerCase(),
        description: req.body.description,
        video: req.body.video,
        instruction: req.body.instruction.split('\n'),
        difficulty: req.body.difficulty,
        cooktime: req.body.cooktime,
        category: req.body.category.toLowerCase(),
        ingredients: JSON.parse(req.body.ingredients),
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
    var image = null;

    if (req.file)
        updateFields.image = req.body.image;
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

    console.log(req.body.author);
    Meal.update({_id: req.params.id}, updateFields, function (err, doc){
        console.log(req.params.id);
        if (req.file)
            image = req.file.filename;
        if (err) { res.json(err); return; }
        else{

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
        else {
            Bookmark.findOne({'meal': req.params.id, 'user':req.user.username}, function (err, result) {
                if (err)
                    res.json(err);
                else {
                    if (result)
                        meal.bookmarked = true;
                    res.json(meal);
                }
            })
        }
    });
};

exports.deleteMeal = function(req, res) {
    Meal.findById(req.params.id, function (err, meal) {
        if (err)
            res.status(500).json(err);
        else {
            if (meal == null || meal.author != req.user.username)
                res.status(403).json("you are not allowed to delete this meal");
            else {
                Meal.remove({'_id':req.params.id}, function(err, result) {
                    if (err)
                        res.status(500).json(err);
                    else {
                        Vote.remove({'meal': req.params.id}, function(err, result) {
                            if (err)
                                res.status(500).json(err);
                            else {
                                Bookmark.remove({'meal': req.params.id}, function(err, result) {
                                    if (err)
                                        res.status(500).json(err);
                                    else {
                                        Comment.remove({'meal': req.params.id}, function(err, remove) {
                                            if (err)
                                                res.status(500).json(err);
                                            else {
                                                res.json('meal deleted');
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }
    });
};

exports.getOwnerMeal = function(req, res){
  Meal.find({author : req.params.author}, function(err, meal){
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

