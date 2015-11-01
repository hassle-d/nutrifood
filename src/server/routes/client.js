/*
** Created by HASSLER Damien
*/

var express = require('express');
var router = express.Router();
var passport = require('passport');
var authController = require('../controllers/auth');
var clientController = require('../controllers/client');

router.route('/clients')
	.post(authController.isAuthenticated, clientController.postClients)
	.get(authController.isAuthenticated, clientController.getClients);

module.exports = router;