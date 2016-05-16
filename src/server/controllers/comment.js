/**
 * Created by Damien Hassler
 */

var Comment = require('../models/comment');

exports.postComment = function(req, res) {
    console.log(req.file);

    var comment = new Comment({
        author: req.body.author.toLowerCase(),
        date: new Date().toISOString(),
        comment: req.body.comment,
        meal: req.params.meal
    });
    comment.save(function(err, comment){
        if (err)
            res.send(err);
        else {
            res.json({message: 'Comment added'}).status(201);
        }
    });
};

exports.getComment = function(req, res) {
    Comment.find({'meal': req.params.meal}, {}, {sort: '-date'}, function(err, comment){
        if (err)
            res.send(err);
        else
            res.json(comment);
    });
};