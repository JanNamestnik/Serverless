/*var eventSchema = new Schema({
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
*/
type MyEvent = {
  _id: string;
  name: string;
  venue: string;
  address: string;
  startTime: string;
  date_start: Date;
  date_end: Date;
  description: string;
  contact: string;
  category: {
    type: string;
  };
  eventImage: string;
  price: number;
  attendees: string[];
  location: {
    type: string;
    coordinates: number[];
  };
};
