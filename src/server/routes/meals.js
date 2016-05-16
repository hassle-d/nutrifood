/**
 * Created by Yilmaz Emrah on 30/10/2015.
 */

var express = require('express');
var router = express.Router();
var multer = require('multer');
var upload = multer({ dest: '../uploads/'});

var mealController = require('../controllers/meals');
var authController = require('../controllers/auth');

router.route('/meals/image/:filename')
	.get(mealController.getImage);

router.route('/test').get(function (req, res){
		res.writeHead(200, {'Content-Type': 'text/html' });
		res.end("<!DOCTYPE HTML><html><body>" +
			"<form method='post' action='/api/v1/meals/' enctype='multipart/form-data'>" +
			"<input type='file' name='image'/>" +
			"<input type='submit' /></form><img src='http://localhost:3000/api/v1/meals/image/496e8ea4b75c8ca27e34acae10038eec'><img src='http://localhost:3000/api/v1/meals/image/db81e8d3e96c2abba5a14c0a6a533fad'>" +
			"</body></html>");
	});


router.route('/meals/name/:name')
    .get(authController.isValidToken, mealController.getMealByName);

router.route('/meals/category/:category')
    .get(authController.isValidToken, mealController.getMealByCategory);

router.route('/meals')
    .post(/*authController.isValidToken, */mealController.postMeals)
    .get(authController.isValidToken, mealController.getMeals);

router.route('/meals/:id')
    .get(authController.isValidToken, mealController.getMealById)
    .put(authController.isValidToken, mealController.updateMeal);

module.exports = router;