/*
** Created by HASSLER Damien
*/

var express = require('express');
var router = express.Router();
var userController = require('../controllers/users');

router.route('/users')
	.post(userController.postUsers)
	.get(userController.getUsers);

module.exports = router;