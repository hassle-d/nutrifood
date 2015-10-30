/*
** Created by HASSLER Damien
*/

var express = require('express');
var router = express.Router();
var passport = require('passport');
var userController = require('../controllers/users');
var authController = require('../controllers/auth');

router.route('/users')
	.post(userController.postUsers)
	.get(userController.getUsers);

router.route('/test')
	.get(authController.isAuthenticated, userController.test);

module.exports = router;