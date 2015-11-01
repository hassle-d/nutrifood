/*
** Created by HASSLER Damien
*/

var express = require('express');
var router = express.Router();
var passport = require('passport');
var tokenController = require('../controllers/token');
var authController = require('../controllers/auth');


router.route('/token')
	.get(tokenController.getTokens);

module.exports = router;