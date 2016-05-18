/**
 * Created by Damien Hassler
 */

var Vote = require('../models/vote');
var Meal = require('../models/meals');

var getSum = function (votes, note) {
    var theSum = 0;

    for (var i = votes.length - 1; i >= 0; i--) {
        theSum += votes[i].note;
    };
    theSum = ( theSum + parseInt(note) ) / ( votes.length + 1 );

    return theSum;
};

var hasNotVoted = function(votes, username) {
    for (var i = votes.length - 1; i >= 0; i--) {
        if (votes[i].user == username)
            return false;
    };
    return true;
}

exports.postVote = function(req, res) {

    Vote.find({'meal': req.params.meal}, function(err, votes){
        if (err)
            res.json(err);
        else {
            if (hasNotVoted(votes, req.user.username.toLowerCase())) {
                var theSum = getSum(votes, req.body.note);
                console.log(req.body);
                var vote = new Vote({
                    user: req.user.username.toLowerCase(),
                    note: req.body.note,
                    meal: req.params.meal
                });
                vote.save(function(err, vote){
                    if (err)
                        res.json("SAVE" + err);
                    else {
                        Meal.update({_id: req.params.meal}, {vote:theSum}, function (err, doc){
                            if (err)
                                res.json(err);
                            else
                                res.json('vote added').status(201);
                        });
                    }
                });
            }
            else
                res.status(401).json('already voted');
        }
    });

};

exports.getVote = function(req, res) {
    Vote.find({'meal': req.params.meal}, function(err, vote){
        if (err)
            res.json(err);
        else
            res.json(vote);
    });
};