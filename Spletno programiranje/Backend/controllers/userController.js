var UserModel = require('../models/userModel.js');

/**
 * userController.js
 *
 * @description :: Server-side logic for managing users.
 */
module.exports = {
    // -----------------------------------------------------------------------------------------------------
    showLogin: function (req, res) {
        res.render('user/login');
    },

    showRegister: function (req, res) {
        res.render('user/register');
    },

    login: function (req, res, next) {
        UserModel.authenticate(req.body.username, req.body.password, function(error, user){
            if(error || !user){
                var err = new Error('Wrong username or password');
                err.status = 401;
                return next(err);
            } else {
                req.session.userId = user._id;
                //return res.redirect('profile');
                return res.status(201).json(user);
            }
        });
    },

    profile: function (req, res, next) {
        UserModel.findById(req.session.userId)
        .exec(function(error, user){
            if(error){
                return next(error);
            } else {
                if(user === null){
                    var err = new Error('Not authorized! Go back!');
                    err.status = 401;
                    return next(err);
                } else {
                    //return res.render('user/profile', {username: user.username, email: user.email, profileImage: user.profileImage});
                    res.json(user); 
                }
            }
        });
    },
    

    logout: function (req, res, next) {
        if(req.session){
            req.session.destroy(function(err){
                if(err){
                    return next(err);
                } else {
                    return res.redirect('/');
                }
            });
        }
    },


    updatePicture: function (req, res, next) {
        var userId = req.session.userId;
        var profileImage = req.file;
    
        if (!profileImage) {
            return res.status(400).json({
                message: 'No profile image provided'
            });
        }
    
        var imagePath = '/userImages/' + profileImage.filename;
    
        UserModel.findByIdAndUpdate(userId, { profileImage: imagePath }, { new: true }, function (err, user) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when updating profile picture',
                    error: err
                });
            }
            res.redirect('/users/profile');
        });
    },


    myFavorites: function (req, res, next) {
        var userId = req.session.userId; // Get the user ID from the session

        // Find the user document based on the user ID
        UserModel.findById(userId)
            .populate('favorites') // Populate the favorites field to get the details of favorite events
            .exec(function (err, user) {
                if (err) {
                    return res.status(500).json({
                        message: 'Error when finding user',
                        error: err
                    });
                }

                if (!user) {
                    return res.status(404).json({
                        message: 'User not found'
                    });
                }

                // Extract favorite events from the user document
                var favoriteEvents = user.favorites;

                // Respond with the favorite events
                res.json(favoriteEvents);
            });
    },

    
    // -----------------------------------------------------------------------------------------------------
    /**
     * userController.list()
     */
    list: function (req, res) {
        UserModel.find(function (err, users) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when getting user.',
                    error: err
                });
            }

            return res.json(users);
        });
    },

    /**
     * userController.show()
     */
    show: function (req, res) {
        var id = req.params.id;

        UserModel.findOne({_id: id}, function (err, user) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when getting user.',
                    error: err
                });
            }

            if (!user) {
                return res.status(404).json({
                    message: 'No such user'
                });
            }

            return res.json(user);
        });
    },

    /**
     * userController.create()
     */
    create: function (req, res) {
        var user = new UserModel({
			username : req.body.username,
			email : req.body.email,
			password : req.body.password,
			profileImage : req.body.profileImage,
            favorites : []
        });

        user.save(function (err, user) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when creating user',
                    error: err
                });
            }

            return res.status(201).json(user);
        });
    },

    /**
     * userController.update()
     */
    update: function (req, res) {
        var id = req.params.id;

        UserModel.findOne({_id: id}, function (err, user) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when getting user',
                    error: err
                });
            }

            if (!user) {
                return res.status(404).json({
                    message: 'No such user'
                });
            }

            user.username = req.body.username ? req.body.username : user.username;
			user.email = req.body.email ? req.body.email : user.email;
			user.password = req.body.password ? req.body.password : user.password;
			user.profileImage = req.body.profileImage ? req.body.profileImage : user.profileImage;
            user.favorites = req.body.favorites ? req.body.favorites : user.favorites;
			
            user.save(function (err, user) {
                if (err) {
                    return res.status(500).json({
                        message: 'Error when updating user.',
                        error: err
                    });
                }

                return res.json(user);
            });
        });
    },

    /**
     * userController.remove()
     */
    remove: function (req, res) {
        var id = req.params.id;

        UserModel.findByIdAndRemove(id, function (err, user) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when deleting the user.',
                    error: err
                });
            }

            return res.status(204).json();
        });
    }
};
