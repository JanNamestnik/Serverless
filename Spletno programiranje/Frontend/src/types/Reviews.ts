/*var reviewSchema = new Schema({
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
});*/
type Review = {
  _id: string;
  eventId: string;
  userId: string;
  created: Date;
  rating: number;
  content: string;
};
