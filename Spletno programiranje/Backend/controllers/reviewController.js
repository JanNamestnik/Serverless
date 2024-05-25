var ReviewModel = require("../models/reviewModel.js");

/**
 * reviewController.js
 *
 * @description :: Server-side logic for managing reviews.
 */
module.exports = {
  /**
   * reviewController.list()
   */
  list: function (req, res) {
    ReviewModel.find(function (err, reviews) {
      if (err) {
        return res.status(500).json({
          message: "Error when getting review.",
          error: err,
        });
      }

      return res.json(reviews);
    });
  },

  /**
   * reviewController.show()
   */
  show: function (req, res) {
    var id = req.params.id;

    ReviewModel.findOne({ _id: id }, function (err, review) {
      if (err) {
        return res.status(500).json({
          message: "Error when getting review.",
          error: err,
        });
      }

      if (!review) {
        return res.status(404).json({
          message: "No such review",
        });
      }

      return res.json(review);
    });
  },

  /**
   * reviewController.create()
   */
  create: function (req, res) {
    let userId = req.userId;

    var review = new ReviewModel({
      eventId: req.body.eventId,
      userId: userId,
      created: new Date(),
      rating: req.body.rating,
      content: req.body.content,
    });

    review.save(function (err, review) {
      if (err) {
        return res.status(500).json({
          message: "Error when creating review",
          error: err,
        });
      }

      return res.status(201).json(review);
    });
  },

  /**
   * reviewController.update()
   */
  update: function (req, res) {
    var id = req.params.id;

    ReviewModel.findOne({ _id: id }, function (err, review) {
      if (err) {
        return res.status(500).json({
          message: "Error when getting review",
          error: err,
        });
      }

      if (!review) {
        return res.status(404).json({
          message: "No such review",
        });
      }

      review.eventId = req.body.eventId ? req.body.eventId : review.eventId;
      review.userId = req.body.userId ? req.body.userId : review.userId;
      review.created = req.body.created ? req.body.created : review.created;
      review.rating = req.body.rating ? req.body.rating : review.rating;
      review.content = req.body.content ? req.body.content : review.content;

      review.save(function (err, review) {
        if (err) {
          return res.status(500).json({
            message: "Error when updating review.",
            error: err,
          });
        }

        return res.json(review);
      });
    });
  },

  /**
   * reviewController.remove()
   */
  remove: function (req, res) {
    var id = req.params.id;

    ReviewModel.findByIdAndRemove(id, function (err, review) {
      if (err) {
        return res.status(500).json({
          message: "Error when deleting the review.",
          error: err,
        });
      }

      return res.status(204).json();
    });
  },
};
