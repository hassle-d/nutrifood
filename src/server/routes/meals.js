/**
 * Created by Yilmaz Emrah on 30/10/2015.
 */

var express = require('express');
var router = express.Router();
var mealController = require('../controllers/meals');

router.route('/meals')
    .post(mealController.postMeals);

module.exports = router;