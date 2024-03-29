/*
** Created by HASSLER Damien
*/

var mongoose = require('mongoose');
var bcrypt = require('bcrypt-nodejs');

var UserSchema = new mongoose.Schema({
	username: {
		type: String,
		unique: true,
		required: true
	},
	password: {
		type: String,
		required: true
	},
	email: {
		type: String,
		required: true
	},
	firstname: {
		type: String,
		required: true
	},
	lastname: {
		type: String,
		required: true
	},
	description: {
		type: String,
		default: null
	},
	age: {
		type: Number, 
		required: true
	},
	date: {
		type: Date,
		default: null, 
		required: true
	},
	userCategory: {
		type: String,
		default: 'user',
	},
	allergy: {
		type: [String],
		default: null
	},
	specialFood: {
		type: [String],
		default: null
	}
});

UserSchema.pre('save', function(callback) {
	var user = this;

	if (!user.isModified('password')) return callback();

	bcrypt.genSalt(5, function(err, salt){
		if (err) return callback(err);
		bcrypt.hash(user.password, salt, null, function(err, hash){
			if (err) return callback(err);
			user.password = hash;
			callback();
		});
	});
});

UserSchema.methods.verifyPassword = function(password, callback) {
	bcrypt.compare(password, this.password, function(err, isMatch){
		if (err) return callback(err);
		callback(null, isMatch);
	});
};

module.exports = mongoose.model('User', UserSchema);