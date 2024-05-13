var mongoose = require('mongoose');
var Schema   = mongoose.Schema;

var reviewSchema = new Schema({
	'eventId' : {
	 	type: Schema.Types.ObjectId,
	 	ref: 'event'
	},
	'userId' : {
	 	type: Schema.Types.ObjectId,
	 	ref: 'user'
	},
	'created' : Date,
	'rating' : Number,
	'content' : String
});

module.exports = mongoose.model('review', reviewSchema);
