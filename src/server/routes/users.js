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
	.get(authController.isValidToken, authController.isAdmin, userController.getUsers);

router.route('/users/:id')
	.get(authController.isAuthenticated, userController.getUserById)
	.put(authController.isAuthenticated, userController.updateUser)
	.delete(authController.isAuthenticated, authController.isAdmin, userController.delUserById);

router.route('/users/username/:username')
	.get(authController.isAuthenticated, userController.getUserByUsername);


module.exports = router;
