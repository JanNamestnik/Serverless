var EventModel = require('../models/eventModel.js');
var CategoryModel = require('../models/categoryModel.js');

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
                reviews: []
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
            event.reviews = req.body.reviews ? req.body.reviews : event.reviews;
			
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
