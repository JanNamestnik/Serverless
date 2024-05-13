var EventModel = require('../models/eventModel.js');

/**
 * eventController.js
 *
 * @description :: Server-side logic for managing events.
 */
module.exports = {

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
    create: function (req, res) {
        var event = new EventModel({
			name : req.body.name,
			venue : req.body.venue,
			date : req.body.date,
			address : req.body.address,
			startTime : req.body.startTime,
			endTime : req.body.endTime,
			description : req.body.description,
			contact : req.body.contact,
			latitude : req.body.latitude,
			longitude : req.body.longitude,
			category : req.body.category
        });

        event.save(function (err, event) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when creating event',
                    error: err
                });
            }

            return res.status(201).json(event);
        });
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
