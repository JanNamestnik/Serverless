var EventModel = require("../models/eventModel.js");
var CategoryModel = require("../models/categoryModel.js");
var ReviewsModel = require("../models/reviewModel.js");
var UserModel = require("../models/userModel.js");
const { show } = require("./userController.js");

/**
 * eventController.js
 *
 * @description :: Server-side logic for managing events.
 */
module.exports = {
  add: function (req, res) {
    res.render("event/add");
  },

  listAll: function (req, res) {
    EventModel.find(function (err, events) {
      if (err) {
        return res.status(500).json({
          message: "Error when getting event.",
          error: err,
        });
      }

      res.render("event/list", { events: events });
    });
  },

  showEvent: function (req, res) {
    var id = req.params.id;

    // Find the event with the given ID
    EventModel.findOne({ _id: id }, function (err, event) {
      if (err) {
        return res.status(500).json({
          message: "Error when getting event.",
          error: err,
        });
      }

      if (!event) {
        return res.status(404).json({
          message: "No such event",
        });
      }

      // Find all reviews associated with the current event
      ReviewsModel.find({ eventId: id })
        .populate("userId")
        .exec(function (err, reviews) {
          if (err) {
            return res.status(500).json({
              message: "Error when getting reviews.",
              error: err,
            });
          }

          // Pass the event and reviews data to the view
          res.render("event/showEvent", { event: event, reviews: reviews });
        });
    });
  },

  filterEvents: function (req, res) {
    const categoryName = req.query.category;
    const fromDate = req.query.fromDate;
    const toDate = req.query.toDate;
    const favourite = req.query.favourite === "on"; // Convert the string to boolean
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
            message: "Error when getting category.",
            error: err,
          });
        }

        if (!category) {
          return res.status(404).json({
            message: "Category not found.",
          });
        }

        // Add category filter to the filter object
        filter.category = category._id;

        // Check if date range is provided
        if (fromDate && toDate) {
          // Apply date range filtering logic
          filter.date_start = {
            $gte: new Date(fromDate),
            $lte: new Date(toDate),
          };
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
              message: "Error when getting events.",
              error: err,
            });
          }

          // Filter events based on the favourite parameter
          if (favourite) {
            // Sort events by the number of attendees in descending order
            events.sort((a, b) => b.attendees.length - a.attendees.length);
          }

          // Pass the events data to the view
          res.render("event/list", { events: events });
        });
      });
    } else {
      // No category selected, filter only by date range and/or price range if provided
      if (fromDate && toDate) {
        filter.date_start = {
          $gte: new Date(fromDate),
          $lte: new Date(toDate),
        };
      }

      if (minPrice && maxPrice) {
        filter.price = { $gte: minPrice, $lte: maxPrice };
      }

      // Find all events that match the filter
      EventModel.find(filter, function (err, events) {
        if (err) {
          return res.status(500).json({
            message: "Error when getting events.",
            error: err,
          });
        }

        // Filter events based on the favourite parameter
        if (favourite) {
          // Sort events by the number of attendees in descending order
          events.sort((a, b) => b.attendees.length - a.attendees.length);
        }

        // Pass the events data to the view
        res.render("event/list", { events: events });
      });
    }
  },

  geospatialFilter: function (req, res) {
    const longitude = parseFloat(req.query.longitude);
    const latitude = parseFloat(req.query.latitude);
    const distance = parseFloat(req.query.distance);

    // Perform geospatial query to find events near the specified coordinates
    EventModel.find(
      {
        location: {
          $near: {
            $geometry: {
              type: "Point",
              coordinates: [longitude, latitude],
            },
            $maxDistance: distance,
          },
        },
      },
      function (err, events) {
        if (err) {
          return res.status(500).json({
            message: "Error when getting events.",
            error: err,
          });
        }

        // Pass the events data to the view
        res.render("event/list", { events: events });
      }
    );
  },

  attend: function (req, res) {
    let userId = req.userId;
    let id = req.params.id;

    // Update the event document to add the current user to the attendees array
    EventModel.findByIdAndUpdate(
      id,
      { $addToSet: { attendees: userId }, $inc: { attendeesCount: 1 } },
      { new: true },
      function (err, event) {
        if (err) {
          return res.status(500).json({
            message: "Error when attending event",
            error: err,
          });
        }

        return res.json(event);
      }
    );
  },

  leave: function (req, res) {
    let userId = req.userId;
    let id = req.params.id;

    // Update the event document to remove the current user from the attendees array
    EventModel.findByIdAndUpdate(
      id,
      { $pull: { attendees: userId }, $inc: { attendeesCount: -1 } },
      { new: true },
      function (err, event) {
        if (err) {
          return res.status(500).json({
            message: "Error when leaving event",
            error: err,
          });
        }

        return res.json(event);
      }
    );
  },

  addFavorite: function (req, res) {
    var userId = req.userId;

    var eventId = req.params.id;

    UserModel.findByIdAndUpdate(
      userId,
      { $addToSet: { favorites: eventId } },
      { new: true },
      function (err, user) {
        if (err) {
          return res.status(500).json({
            message: "Error when adding favorite event",
            error: err,
          });
        }
        if (!user) {
          return res.status(200).json({
            message: "No such user",
          });
        }
        return res.json(user);
      }
    );
  },
  addUnFavorite: function (req, res) {
    var userId = req.userId;
    var eventId = req.params.id;

    UserModel.findByIdAndUpdate(
      userId,
      { $addToSet: { unfavorites: eventId } },
      { new: true },
      function (err, user) {
        if (err) {
          return res.status(500).json({
            message: "Error when adding favorite event",
            error: err,
          });
        }
        if (!user) {
          return res.status(200).json({
            message: "No such user",
          });
        }
        return res.json(user);
      }
    );
  },

  removeFavorite: function (req, res) {
    var userId = req.userId;
    var eventId = req.params.id;

    UserModel.findByIdAndUpdate(
      userId,
      { $pull: { favorites: eventId } },
      { new: true },
      function (err, user) {
        if (err) {
          return res.status(500).json({
            message: "Error when removing favorite event",
            error: err,
          });
        }
        if (!user) {
          return res.status(200).json({
            message: "No such user",
          });
        }
        return res.json(user);
      }
    );
  },
  removeUnFavorite: function (req, res) {
    var userId = req.userId;
    var eventId = req.params.id;

    UserModel.findByIdAndUpdate(
      userId,
      { $pull: { unfavorites: eventId } },
      { new: true },
      function (err, user) {
        if (err) {
          return res.status(500).json({
            message: "Error when removing favorite event",
            error: err,
          });
        }
        if (!user) {
          return res.status(200).json({
            message: "No such user",
          });
        }
        return res.json(user);
      }
    );
  },

  //----------------------------------------------------------------------------------------------------------
  /**
   * eventController.list()
   */

  //list all events, but remove ones that are in my unfavorites

  list: function (req, res) {
    const userId = req.userId; // Assuming the userId is set in the request object by middleware

    UserModel.findById(userId, "unfavorites", (err, user) => {
      if (err) {
        return res.status(500).json({
          message: "Error when getting user data.",
          error: err,
        });
      }
      if (!user) {
        return res.status(404).json({
          message: "User not found.",
        });
      }

      const unfavorites = user.unfavorites || [];

      EventModel.find({ _id: { $nin: unfavorites } }, (err, events) => {
        if (err) {
          return res.status(500).json({
            message: "Error when getting events.",
            error: err,
          });
        }

        return res.json(events);
      });
    });
  },

  //list all unfavorites
  listUnFavorites: function (req, res) {
    var userId = req.userId;

    UserModel.findById(userId, function (err, user) {
      if (err) {
        return res.status(500).json({
          message: "Error when getting user.",
          error: err,
        });
      }

      EventModel.find(
        { _id: { $in: user.unfavorites } },
        function (err, events) {
          if (err) {
            return res.status(500).json({
              message: "Error when getting unfavorites.",
              error: err,
            });
          }

          return res.json(events);
        }
      );
    });
  },

  listFavorites: function (req, res) {
    var userId = req.userId;

    UserModel.findById(userId, function (err, user) {
      if (err) {
        return res.status(500).json({
          message: "Error when getting user.",
          error: err,
        });
      }

      EventModel.find({ _id: { $in: user.favorites } }, function (err, events) {
        if (err) {
          return res.status(500).json({
            message: "Error when getting favorites.",
            error: err,
          });
        }

        return res.json(events);
      });
    });
  },
  //return all the events that the user is attending, which you are getting from the token
  listAttending: function (req, res) {
    var userId = req.userId;
    EventModel.find({ attendees: userId }).exec((err, events) => {
      if (err) {
        return res
          .status(500)
          .json({ error: "An error occurred while fetching events." });
      }
      return res.json(events);
    });
  },
  // create a function show popular events, which will return the events with the most attendees and followers
  showPopular: function (req, res) {
    EventModel.find()
      .sort({ attendeesCount: -1 })
      .limit(5)
      .exec((err, events) => {
        if (err) {
          return res
            .status(500)
            .json({ error: "An error occurred while fetching events." });
        }
        return res.json(events[0]);
      });
  },
  // create a function show interesting events, which will return the events with most followers
  showInteresting: function (req, res) {
    const userId = req.userId;

    UserModel.findById(userId, (err, user) => {
      if (err) {
        return res.status(500).json({
          error: "An error occurred while fetching user data.",
        });
      }

      if (!user) {
        return res.status(404).json({
          error: "User not found.",
        });
      }

      EventModel.aggregate([
        { $match: { _id: { $in: user.favorites } } },
        { $group: { _id: "$_id", count: { $sum: 1 } } },
        { $sort: { count: -1 } },
        { $limit: 10 },
        {
          $lookup: {
            from: "events",
            localField: "_id",
            foreignField: "_id",
            as: "event",
          },
        },
        { $unwind: "$event" },
        { $replaceRoot: { newRoot: "$event" } },
      ]).exec((err, results) => {
        if (err) {
          return res.status(500).json({
            error: "An error occurred while fetching interesting events.",
          });
        }
        return res.json(results[0]);
      });
    });
  },
  listMyEvents: function (req, res) {
    var userId = req.userId;

    EventModel.find({ owner: userId }, function (err, events) {
      if (err) {
        return res.status(500).json({
          message: "Error when getting events.",
          error: err,
        });
      }

      return res.json(events);
    });
  },

  // create a function that returns recommended events based on user categorys
  showRecomended: function (req, res) {
    const userId = req.userId; // Assuming the userId is set in the request object by middleware

    UserModel.findById(userId)
      .populate("favorites")
      .exec((err, user) => {
        if (err) {
          return res
            .status(500)
            .json({ error: "An error occurred while fetching user data." });
        }
        if (!user) {
          return res.status(404).json({ error: "User not found." });
        }

        const favoriteCategories = user.favorites.map(
          (event) => event.category
        );
        if (favoriteCategories.length === 0) {
          return res.json([]); // No favorite categories, return empty array
        }

        EventModel.find({ category: { $in: favoriteCategories } })
          .populate("category") // Populate the category field if needed
          .exec((err, events) => {
            if (err) {
              return res.status(500).json({
                error: "An error occurred while fetching recommended events.",
              });
            }
            return res.json(events[0]);
          });
      });
  },
  /**
   * eventController.show()
   */
  show: function (req, res) {
    var id = req.params.id;

    EventModel.findOne({ _id: id })
      .populate("attendees")
      .exec(function (err, event) {
        if (err) {
          return res.status(500).json({
            message: "Error when getting event.",
            error: err,
          });
        }

        if (!event) {
          return res.status(404).json({
            message: "No such event",
          });
        }

        return res.json(event);
      });
  },

  showMyEvents: function (req, res) {
    var userId = req.userId;

    EventModel.find({ owner: userId }, function (err, events) {
      if (err) {
        return res.status(500).json({
          message: "Error when getting events.",
          error: err,
        });
      }

      return res.json(events);
    });
  },

  /**
   * eventController.create()
   */
  create: async function (req, res) {
    let userId = req.userId;
    console.log(req.body.longtitute);
    try {
      // Find the category with the specified name
      const category = await CategoryModel.findOne({ _id: req.body.category });

      // If the category doesn't exist, return an error
      if (!category) {
        return res.status(400).json({ message: "Category not found" });
      }

      // Create the event with the found category and location coordinates
      const event = new EventModel({
        name: req.body.name,
        venue: req.body.venue,
        date_start: req.body.date_start,
        date_end: req.body.date_end,
        address: req.body.address,
        startTime: req.body.startTime ? req.body.startTime : "00:00",
        description: req.body.description,
        contact: req.body.contact,
        location: {
          type: "Point",
          coordinates: [
            parseFloat(req.body.longtitute),
            parseFloat(req.body.lagtitute),
          ],
        },
        category: category._id,
        eventImage: "/images/" + req.file.filename,
        price: req.body.price,
        attendees: [],
        owner: userId,
      });

      // Save the event to the database
      const savedEvent = await event.save();

      // Respond with the created event
      res.status(201).json(savedEvent);
    } catch (error) {
      // Handle errors
      console.error(error);
      res
        .status(500)
        .json({ message: "Error when creating event", error: error });
    }
  },

  /**
   * eventController.update()
   */
  update: function (req, res) {
    var id = req.params.id;

    EventModel.findOne({ _id: id }, function (err, event) {
      if (err) {
        return res.status(500).json({
          message: "Error when getting event",
          error: err,
        });
      }

      if (!event) {
        return res.status(404).json({
          message: "No such event",
        });
      }

      event.name = req.body.name ? req.body.name : event.name;
      event.venue = req.body.venue ? req.body.venue : event.venue;
      event.date_start = req.body.date_start
        ? req.body.date_start
        : event.date_start;
      event.date_end = req.body.date_end ? req.body.date_end : event.date_end;
      event.address = req.body.address ? req.body.address : event.address;
      event.startTime = req.body.startTime
        ? req.body.startTime
        : event.startTime;
      event.description = req.body.description
        ? req.body.description
        : event.description;
      event.contact = req.body.contact ? req.body.contact : event.contact;
      event.category = req.body.category ? req.body.category : event.category;
      event.eventImage = req.body.eventImage
        ? req.body.eventImage
        : event.eventImage;
      event.price = req.body.price ? req.body.price : event.price;
      event.attendees = req.body.attendees
        ? req.body.attendees
        : event.attendees;

      event.save(function (err, event) {
        if (err) {
          return res.status(500).json({
            message: "Error when updating event.",
            error: err,
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
          message: "Error when deleting the event.",
          error: err,
        });
      }

      return res.status(204).json();
    });
  },
};
