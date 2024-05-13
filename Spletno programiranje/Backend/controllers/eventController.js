var EventModel = require('../models/eventModel.js');
var CategoryModel = require('../models/categoryModel.js');
var ReviewsModel = require('../models/reviewModel.js');
var UserModel = require('../models/userModel.js');
const { show } = require('./userController.js');

/**
 * eventController.js
 *
 * @description :: Server-side logic for managing events.
 */
module.exports = {

    add: function (req, res) {
        res.render('event/add');
    },

    listAll: function (req, res) {
        EventModel.find(function (err, events) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when getting event.',
                    error: err
                });
            }

            res.render('event/list', {events: events});
        });
    },


    showEvent: function (req, res) {
        var id = req.params.id;
    
        // Find the event with the given ID
        EventModel.findOne({ _id: id }, function (err, event) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when getting event.',
                    error: err
                });
            }
    
            if (!event) {
                return res.status(404).json({
                    message: 'No such event'
                });
            }
    
            // Find all reviews associated with the current event
            ReviewsModel.find({ eventId: id })
                .populate('userId')
                .exec(function (err, reviews) {
                    if (err) {
                        return res.status(500).json({
                            message: 'Error when getting reviews.',
                            error: err
                        });
                    }
    
                    // Pass the event and reviews data to the view
                    res.render('event/showEvent', { event: event, reviews: reviews });
                });
        });
    },
    

    filterEvents: function (req, res) {
        const categoryName = req.query.category;
        const fromDate = req.query.fromDate;
        const toDate = req.query.toDate;
        const favourite = req.query.favourite === 'on'; // Convert the string to boolean
        const minPrice = req.query.minPrice;
        const maxPrice = req.query.maxPrice;
        
        // Initialize the filter object
        let filter = {};
        
        // Check if a category is provided
        if (categoryName) {
            // Find the category document based on the provided name
            CategoryModel.findOne({ name: categoryName }, function (err, category) {
                if (err) {
                    return res.status(500).json({
                        message: 'Error when getting category.',
                        error: err
                    });
                }
    
                if (!category) {
                    return res.status(404).json({
                        message: 'Category not found.'
                    });
                }
    
                // Add category filter to the filter object
                filter.category = category._id;
    
                // Check if date range is provided
                if (fromDate && toDate) {
                    // Apply date range filtering logic
                    filter.date = { $gte: new Date(fromDate), $lte: new Date(toDate) };
                }
    
                // Check if price range is provided
                if (minPrice && maxPrice) {
                    // Apply price range filtering logic
                    filter.price = { $gte: minPrice, $lte: maxPrice };
                }
    
                // Find all events that match the filter
                EventModel.find(filter, function (err, events) {
                    if (err) {
                        return res.status(500).json({
                            message: 'Error when getting events.',
                            error: err
                        });
                    }
    
                    // Filter events based on the favourite parameter
                    if (favourite) {
                        // Sort events by the number of attendees in descending order
                        events.sort((a, b) => b.attendees.length - a.attendees.length);
                    }
    
                    // Pass the events data to the view
                    res.render('event/list', { events: events });
                });
            });
        } else {
            // No category selected, filter only by date range and/or price range if provided
            if (fromDate && toDate) {
                filter.date = { $gte: new Date(fromDate), $lte: new Date(toDate) };
            }
    
            if (minPrice && maxPrice) {
                filter.price = { $gte: minPrice, $lte: maxPrice };
            }
    
            // Find all events that match the filter
            EventModel.find(filter, function (err, events) {
                if (err) {
                    return res.status(500).json({
                        message: 'Error when getting events.',
                        error: err
                    });
                }
    
                // Filter events based on the favourite parameter
                if (favourite) {
                    // Sort events by the number of attendees in descending order
                    events.sort((a, b) => b.attendees.length - a.attendees.length);
                }
    
                // Pass the events data to the view
                res.render('event/list', { events: events });
            });
        }
    },
    
    
    
    attend: function (req, res) {
        var userId = req.session.userId; // Get the user ID from the session
        var id = req.params.id;
    
        // Update the event document to add the current user to the attendees array
        EventModel.findByIdAndUpdate(id, { $addToSet: { attendees: userId }, $inc: { attendeesCount: 1 } }, { new: true }, function (err, event) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when attending event',
                    error: err
                });
            }
    
            return res.json(event);
            //res.redirect('/events/showEvent/' + id);
        });
    },
    

    leave: function (req, res) {
        var userId = req.session.userId; // Get the user ID from the session
        var id = req.params.id;
    
        // Update the event document to remove the current user from the attendees array
        EventModel.findByIdAndUpdate(id, { $pull: { attendees: userId }, $inc: { attendeesCount: -1 } }, { new: true }, function (err, event) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when leaving event',
                    error: err
                });
            }
    
            return res.json(event);
        });
    },
    








    //----------------------------------------------------------------------------------------------------------
    /**
     * eventController.list()
     */
    list: function (req, res) {
        EventModel.find(function (err, events) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when getting event.',
                    error: err
                });
            }

            return res.json(events);
        });
    },

    /**
     * eventController.show()
     */
    show: function (req, res) {
        var id = req.params.id;

        EventModel.findOne({_id: id}, function (err, event) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when getting event.',
                    error: err
                });
            }

            if (!event) {
                return res.status(404).json({
                    message: 'No such event'
                });
            }

            return res.json(event);
        });
    },

    /**
     * eventController.create()
     */
    create: async function (req, res) {
        try {
            // Find the category with the specified name
            const category = await CategoryModel.findOne({ name: req.body.category });

            // If the category doesn't exist, return an error
            if (!category) {
                return res.status(400).json({ message: 'Category not found' });
            }

            // Create the event with the found category
            const event = new EventModel({
                name: req.body.name,
                venue: req.body.venue,
                date: req.body.date,
                address: req.body.address,
                startTime: req.body.startTime,
                endTime: req.body.endTime,
                description: req.body.description,
                contact: req.body.contact,
                latitude: req.body.latitude,
                longitude: req.body.longitude,
                category: category._id,
                eventImage: "/images/" + req.file.filename,
                price: req.body.price,
                attendees: []
            });

            // Save the event to the database
            const savedEvent = await event.save();

            // Respond with the created event
            res.status(201).json(savedEvent);
        } catch (error) {
            // Handle errors
            console.error(error);
            res.status(500).json({ message: 'Error when creating event', error: error });
        }
    },

    /**
     * eventController.update()
     */
    update: function (req, res) {
        var id = req.params.id;

        EventModel.findOne({_id: id}, function (err, event) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when getting event',
                    error: err
                });
            }

            if (!event) {
                return res.status(404).json({
                    message: 'No such event'
                });
            }

            event.name = req.body.name ? req.body.name : event.name;
			event.venue = req.body.venue ? req.body.venue : event.venue;
			event.date = req.body.date ? req.body.date : event.date;
			event.address = req.body.address ? req.body.address : event.address;
			event.startTime = req.body.startTime ? req.body.startTime : event.startTime;
			event.endTime = req.body.endTime ? req.body.endTime : event.endTime;
			event.description = req.body.description ? req.body.description : event.description;
			event.contact = req.body.contact ? req.body.contact : event.contact;
			event.latitude = req.body.latitude ? req.body.latitude : event.latitude;
			event.longitude = req.body.longitude ? req.body.longitude : event.longitude;
			event.category = req.body.category ? req.body.category : event.category;
            event.eventImage = req.body.eventImage ? req.body.eventImage : event.eventImage;
            event.price = req.body.price ? req.body.price : event.price;
            event.attendees = req.body.attendees ? req.body.attendees : event.attendees;
			
            event.save(function (err, event) {
                if (err) {
                    return res.status(500).json({
                        message: 'Error when updating event.',
                        error: err
                    });
                }

                return res.json(event);
            });
        });
    },

    /**
     * eventController.remove()
     */
    remove: function (req, res) {
        var id = req.params.id;

        EventModel.findByIdAndRemove(id, function (err, event) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when deleting the event.',
                    error: err
                });
            }

            return res.status(204).json();
        });
    }
};
