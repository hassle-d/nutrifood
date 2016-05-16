/*
** Created by HASSLER Damien
*/

var express = require('express');
var router = express.Router();
var passport = require('passport');
var userController = require('../controllers/users');
var authController = require('../controllers/auth');

router.route('/user')
	.get(authController.isValidToken, userController.getProfil)
	.put(authController.isValidToken, userController.updateProfil)

router.route('/users')
	.post(userController.postUsers)
	.get(authController.isValidToken, userController.getUsers);

router.route('/users/:id')
	.get(authController.isValidToken, userController.getUserById)
	.put(authController.isValidToken, userController.updateUser)
	.delete(authController.isValidToken, authController.isAdmin, userController.delUserById);

router.route('/users/username/:username')
	.get(authController.isValidToken, userController.getUserByUsername);

module.exports = router;
