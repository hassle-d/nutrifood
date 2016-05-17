/**
 * Created by Damien Hassler
 */

var express = require('express');
var router = express.Router();

var bookmarkController = require('../controllers/bookmark');
var authController = require('../controllers/auth');

router.route('/bookmark')
	.get(authController.isValidToken, bookmarkController.getBookmark);


router.route('/bookmark/:meal')
	.post(authController.isValidToken, bookmarkController.addBookmark);

module.exports = router;