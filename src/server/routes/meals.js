/**
 * Created by Yilmaz Emrah on 30/10/2015.
 */

var express = require('express');
var router = express.Router();
var mealController = require('../controllers/meals');

router.route('/meals')
    .post(mealController.postMeals)
    .get(mealController.getMeals);

router.route('/meals/:id')
    .get(mealController.getMealById);

router.route('/meals/name/:name')
    .get(mealController.getMealByName);

router.route('/meals/category/:category')
    .get(mealController.getMealByCategory);

module.exports = router;