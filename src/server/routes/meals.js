/**
 * Created by Yilmaz Emrah on 30/10/2015.
 */

var express = require('express');
var router = express.Router();
var multer = require('multer');
var upload = multer({ dest: '../uploads/'});

var mealController = require('../controllers/meals');


<<<<<<< HEAD
router.route('/meals/image/:filename')
=======
router.route('/meals/image/:id')
>>>>>>> 2d5587e1148c325add40c17e677a73cdbb267dbf
	.get(mealController.getImage);

router.route('/test').get(function (req, res){
		res.writeHead(200, {'Content-Type': 'text/html' });
		res.end("<!DOCTYPE HTML><html><body>" +
			"<form method='post' action='/api/v1/meals/' enctype='multipart/form-data'>" +
			"<input type='file' name='image'/>" +
			"<input type='submit' /></form>" +
			"</body></html>");
	});


router.route('/meals/name/:name')
    .get(mealController.getMealByName);

router.route('/meals/category/:category')
    .get(mealController.getMealByCategory);

router.route('/meals')
    .post(mealController.postMeals)
    .get(mealController.getMeals);

router.route('/meals/:id')
    .get(mealController.getMealById)
    .put(mealController.updateMeal);

module.exports = router;