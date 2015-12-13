/*
** Created by HASSLER Damien
*/

var express = require('express');
var router = express.Router();
var userController = require('../controllers/users');
var authController = require('../controllers/auth');

router.route('/auth/signin')
	.post(authController.getNewToken);

router.route('/auth/logout')
	.get(authController.logout);

module.exports = router;
