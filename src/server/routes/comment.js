/**
 * Created by Damien Hassler
 */

var express = require('express');
var router = express.Router();

var commentController = require('../controllers/comment');
var authController = require('../controllers/auth');

router.route('/comment/:meal')
	.get(commentController.getComment)
	.post(authController.isValidToken, commentController.postComment);

module.exports = router;