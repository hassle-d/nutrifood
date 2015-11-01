var mongoose = require('mongoose');

var TokenSchema = new mongoose.Schema({
	value: {
		type: String,
		required: true },
	username: {
		type: String,
		required: true
	},
	date: {
		type: Date,
	}
});

module.exports = mongoose.model('Token', TokenSchema);
