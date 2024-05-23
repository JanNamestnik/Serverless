/*var userSchema = new Schema({
	'username' : String,
	'email' : String,
	'password' : String,
	'profileImage' : String,
	'favorites' : [{type: Schema.Types.ObjectId, ref: 'event'}]
});*/

type User = {
  _id: string;
  username: string;
  email: string;
  password: string;
  profileImage: string;
  favorites: string[];
};
