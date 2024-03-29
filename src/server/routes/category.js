/*
** Created by HASSLER Damien
*/

var express = require('express');
var router = express.Router();
var passport = require('passport');
var categoryController = require('../controllers/category');
var authController = require('../controllers/auth');


router.route('/category')
	.post(authController.isValidToken, authController.isAdmin, categoryController.postCategory)
	.get(categoryController.getCategory);

router.route('/category/:id')
	.get(categoryController.getCategoryById)
	.delete(authController.isValidToken, authController.isAdmin, categoryController.deleteCategoryById);

module.exports = router;
