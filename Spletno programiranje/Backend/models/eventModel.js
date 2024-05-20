var mongoose = require('mongoose');
var Schema   = mongoose.Schema;

var eventSchema = new Schema({
	'name' : String,
	'venue' : String,
	'address' : String,
	'startTime' : String,
	'date_start' : Date,
	'date_end' : Date,
	'description' : String,
	'contact' : String,
	'category' : {
	 	type: Schema.Types.ObjectId,
	 	ref: 'category'
	},
	'eventImage' : String,
	'price' : Number,
	'attendees': [{type: Schema.Types.ObjectId, ref: 'user'}],

	'location': {
        type: {
            type: String,
            enum: ['Point'],
			required: true
        },
        coordinates: {
            type: [Number],
			required: true
        }
    },

});

eventSchema.index({ location: '2dsphere' });

module.exports = mongoose.model('event', eventSchema);
