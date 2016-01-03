/**
 * Created by Damien Hassler
 */

var express = require('express');
var router = express.Router();

var commentController = require('../controllers/comment');

router.route('/comment/:meal')
	.get(commentController.getComment)
	.post(commentController.postComment);

module.exports = router;