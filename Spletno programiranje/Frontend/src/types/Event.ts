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

// create a empty event object
const emptyEvent: MyEvent = {
  _id: "",
  name: "",
  venue: "",
  address: "",
  startTime: "",
  date_start: new Date(),
  date_end: new Date(),
  description: "",
  contact: "",
  category: {
    type: "",
  },
  eventImage: "",
  price: 0,
  attendees: [],
  location: {
    type: "",
    coordinates: [],
  },
};
