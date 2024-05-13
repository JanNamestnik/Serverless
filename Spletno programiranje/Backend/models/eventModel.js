var mongoose = require('mongoose');
var Schema   = mongoose.Schema;

var eventSchema = new Schema({
	'name' : String,
	'venue' : String,
	'date' : Date,
	'address' : String,
	'startTime' : String,
	'endTime' : String,
	'description' : String,
	'contact' : String,
	'latitude' : Number,
	'longitude' : Number,
	'category' : {
	 	type: Schema.Types.ObjectId,
	 	ref: 'category'
	}
});

module.exports = mongoose.model('event', eventSchema);
