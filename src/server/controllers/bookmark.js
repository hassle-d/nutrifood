/**
 * Created by Damien Hassler
 */

var Bookmark = require('../models/bookmark');
var Meal = require('../models/meals');

exports.getBookmark = function(req, res) {

    Bookmark.find({'user':req.user.username}, function(err, bookmarks) {
        if (err)
            res.json(err);
        else {
            var mealId = [];
            for (var i = bookmarks.length - 1; i >= 0; i--) {
                mealId.push(bookmarks[i].meal);
            };
            Meal.find({'_id':{$in:mealId}}, function(err, meals) {
                if (err)
                    res.json(err);
                else
                    res.json(meals);
            });
        }
    });
};

exports.addBookmark = function(req, res) {

    var bookmark = new Bookmark({
        meal: req.params.meal,
        user: req.user.username
    });
    bookmark.save(function(err, bookmark) {
        if (err)
            res.json(err);
        else
            res.json('bookmark added').status(201);
    });
};