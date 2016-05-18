/**
 * Created by Damien Hassler
 */

var express = require('express');
var router = express.Router();

var voteController = require('../controllers/vote');
var authController = require('../controllers/auth');

router.route('/vote/:meal')
	.get(authController.isValidToken, voteController.getVote)
	.post(authController.isValidToken, voteController.postVote);

module.exports = router;