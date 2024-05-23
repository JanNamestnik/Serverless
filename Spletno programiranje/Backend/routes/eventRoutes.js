var express = require("express");
var router = express.Router();
var eventController = require("../controllers/eventController.js");
var multer = require("multer");
var upload = multer({ dest: "public/images/" });

const { verifyToken } = require("../public/javascripts/authenticateJWT");

// Middleware for verifying JWT token on all routes except list, filter
router.use((req, res, next) => {
  if (
    req.path === "/list" ||
    req.path === "/filter" ||
    req.path === "/geospatialFilter"
  ) {
    // Skip authentication for list routes
    return next();
  }
  verifyToken(req, res, (err) => {
    if (err) {
      return res
        .status(401)
        .json({ message: "Unauthorized: Invalid token" + err.message });
    }
    next();
  });
});

/*
 * GET
 */
router.get("/", eventController.list);
router.get("/add", eventController.add);
router.get("/list", eventController.listAll);
router.get("/filter", eventController.filterEvents);
router.get("/geospatialFilter", eventController.geospatialFilter);

/*
 * GET
 */
router.get("/:id", eventController.show);
router.get("/showEvent/:id", eventController.showEvent);
router.get("/showEvent/attend/:id", eventController.attend);
router.get("/showEvent/leave/:id", eventController.leave);

/*
 * POST
 */
router.post("/", upload.single("image"), eventController.create);
router.post(
  "/showEvent/addFavorite/:id",
  verifyToken,
  eventController.addFavorite
);
router.post(
  "/showEvent/removeFavorite/:id",
  verifyToken,
  eventController.removeFavorite
);

/*
 * PUT
 */
router.put("/:id", eventController.update);

/*
 * DELETE
 */
router.delete("/:id", eventController.remove);

module.exports = router;
