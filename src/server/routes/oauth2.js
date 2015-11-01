/*
** Created by HASSLER Damien
*/

var express = require('express');
var router = express.Router();
var passport = require('passport');
var authController = require('../controllers/auth');
var clientController = require('../controllers/client');
var oauth2Controller = require('../controllers/oauth2');

router.route('/oauth2/authorize')
	.get(authController.isAuthenticated, oauth2Controller.authorization)
	.post(authController.isAuthenticated, oauth2Controller.decision);

router.route('/oauth2/token')
	.post(authController.isClientAuthenticated, oauth2Controller.token);

module.exports = router;