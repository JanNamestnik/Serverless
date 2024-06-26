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
router.get("/recommended", verifyToken, eventController.showRecomended);
router.get("/interesting", verifyToken, eventController.showInteresting);
router.get("/popular", verifyToken, eventController.showPopular);

router.get("/listattending", verifyToken, eventController.listAttending);

router.get("/listmyevents", verifyToken, eventController.listMyEvents);

router.get("/listhidden", verifyToken, eventController.listUnFavorites);

/*
 * GET
 */
router.post("/showEvent/attend/:id", eventController.attend);
router.post("/showEvent/leave/:id", eventController.leave);
router.post("/showEvent/hide/:id", eventController.addUnFavorite);
router.post("/showEvent/unhide/:id", eventController.removeUnFavorite);
router.get("/:id", eventController.show);

router.get("/showEvent/:id", eventController.showEvent);

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
router.post("/update/:id", upload.single("image"), eventController.update);

/*
 * DELETE
 */
router.delete("/:id", eventController.remove);

module.exports = router;
