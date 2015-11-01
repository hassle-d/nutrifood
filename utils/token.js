/*
** Created by HASSLER Damien
*/

function uid (len) {
	var buf = [],
		chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789',
		charlen = chars.length;

	for (var i = 0; i < len; ++i) {
		buf.push(chars[getRandomInt(0, charlen - 1)]);
	}
	return buf.join('');
};

function getRandomInt(min, max) {
	return Math.floor(Math.random() * (max - min + 1)) + min;
}

exports.createToken = function(){
	var token = uid(100);
	return token;
};
