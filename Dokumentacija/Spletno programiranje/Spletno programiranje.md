<a name="readme-top"></a>

<div align="center">
  <h1 align="center">Spletno programiranje</h1>

  <p align="center">
    Aplikacija za ogled, prijavo in objavo dogodkov v oklici Maribora.
    <br />
    <a href="https://github.com/JanNamestnik/Serverless/tree/main">Projekt</a>
    ·
    <a href="https://github.com/JanNamestnik/Serverless/tree/devel/Spletno%20programiranje/Backend">Backend</a>
    ·
    <a href="https://github.com/JanNamestnik/Serverless/tree/devel/Spletno%20programiranje/Frontend">Frontend</a>
    <br />
    <a href="https://github.com/JanNamestnik/Serverless/tree/devel/Dokumentacija"><strong>Vsa dokumentacija»</strong></a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Kazalo vsebine</summary>
  <ol>
    <li>
      <a href="#o-projektu">O projektu</a>
      <ul>
        <li><a href="#glavne-funkcionalnosti-aplikacije">Glavne funkcionalnosti aplikacije</a></li>
        <li><a href="#uporabljena-oprema">Uporabljena oprema</a></li>
      </ul>
    </li>
    <li>
      <a href="#osnovna-postavitev">Osnovna postavitev</a>
      <ul>
        <li><a href="#predpogoji-uporabe">Predpogoji uporabe</a></li>
        <li><a href="#prenos">Prenos projektne kode</a></li>
        <li><a href="#zagon-zalednega-dela">Zagon zalednega dela</a></li>
        <li><a href="#zagon-clenega-dela">Zagon členega dela</a></li>
        <li><a href="#preverjanje-delovanja">Preverjanje delovanja</a></li>
        <li><a href="#osnovna-uporaba">Osnovna uporaba</a></li>
      </ul>
    </li>
    <li>
      <a href="#opis-projekta">Opis projekta</a>
      <ul>
        <li><a href="#vzpostavitev-podatkovne-baze">Vzpostavitev podatkovne baze</a></li>
        <li><a href="#backend">Backend</a></li>
        <li><a href="#frontend">Frontend</a></li>
      </ul>
    </li>
    <li><a href="#kontakt">Kontakt</a></li>
    <li><a href="#viri">Viri</a></li>
  </ol>
</details>

<!-- O projektu -->
<h2 id="o-projektu">1. O projektu</h2>
Dobrodošli v dokumentacijo aplikacije za dogodke! Naša aplikacija omogoča enostavno organizacijo, upravljanje in sledenje dogodkom vseh velikosti. Ne glede na to, ali načrtujete manjše srečanje ali veliko konferenco, vam bo naša platforma pomagala doseči vaše cilje.

<h3 id="glavne-funkcionalnosti-aplikacije">1.1 Glavne funkcionalnosti aplikacije:</h3>

- Prikaz dogodkov, ki se dogajajo v okolici Maribora
- Dodajanje in urejanje že obstoječih dogodkov
- Animiran prikaz dogodkov na mapi
- Prijava in registracija uporabnikov

<br />
<h2 id="uporabljena-oprema">1.2 Uporabljena oprema</h2>
<h3 id=""> 1.2.1 Front end oprema</h3>

- Framework : [![REACT][REACT]][REACT-url]
- Orodje za optimizirano grajenje React kode : [![VITE][VITE]][VITE-url]
- Programski jezik : [![TypeScript][TypeScript]][TypeScript-url]
- Orodje za stiliranje z CSS-om : [![TAILWIND][TAILWIND]][TAILWIND-url]
- Knjižnica za ustvarjanje pomikajočih se kartic : [![SWIPERJS][SWIPERJS]][SWIPERJS-url]
- Knjižnica za ikone : [![MUI][MUI]][MUI-url]
- Knjižnica za mapo (open street maps) : [![LEAFLET][LEAFLET]][LEAFLET-url]
- Knjižnica za delo z grafi : [![D3JS][D3JS]][D3js-url]
- <h3> 1.2.2 Backend oprema </h3>

- Programski jezik : [![JavaScript][JavaScript]][JavaScript-url]
- Framework : [![Express][Express]][Express-url]

<h3> 1.2.3 Splošna oprema </h3>

- IDE : [![VSCode][VSCode]][VSCode-url]

<h2 id="osnovna postavitev" >2. Osnovna postavitev</h2>

Za najlažji začetek uporabe je smisleno , da kolonirate celoten repozetorij na lastno lokalno napravo, najbolje zadnjo vezijo na release veji, ker je na njej zadnja verzija delujoče aplikacije.Za lažji začetek sledite naslednjim korakom:

<h3 id="predpogoji uporabe">Predpogoji uporabe</h3>
Za zagon členega in zalednega dela potrebujmo node.js nameščena na lokalni napravi. Za lažje delo z verzijami node.js priporočam uporabo NVM ( node version maneger).

Več informacij o namestitvi najdemo na : <a>https://www.freecodecamp.org/news/node-version-manager-nvm-install-guide/</a>

Za členi in zadeldni del je potrebna uporaba `node 18.14.0` , ki ga namestimo z ukazom:

```bash
nvm install 18.14.00
```

Preverjanje namestitve:
Po namestitvi preverite, ali je Node.js uspešno nameščen, tako da zaženete naslednja ukaza:

```bash
node -v
npm -v
```

Ti ukazi naj bi prikazali različici Node.js in npm, kar potrjuje uspešno namestitev.
Nato ga še omogočimo uporabo ukaza:

```bash
nvm use 18.14.0
```

Tako bi moral biti nameščena prava različica node strežnika.

<h3 id="Prenos">2.1 Prenos projektne kode</h3>

- Obiščite GitHub repozitorij, kjer je shranjena projektna koda.
- Prenesite repozitorij z uporabo ukaza git clone:

```
git clone https://github.com/JanNamestnik/Serverless.git
```

<h3 id="Zagon">2.2 Zagon zalednega dela</h3>

- Premaknite se v imenik projekta:

```
cd Serverless/Spletno Programiranje/Backend
```

- namestimo vse odvisnosti :

```
npm i
```

- nato zaženemo zaledni del z ukazom

```
npm start
```

- Zdaj bi moral backend biti lokalno dostopen na portu 3000
<h3 id="Zagon">2.3 Zagon členega dela</h3>
- Premaknite se v imenik projekta:

```
cd Serverless/Spletno Programiranje/Backend
```

- namestimo vse odvisnosti :

```
npm i
```

- nato zaženemo zaledni del z ukazom

```
npm run dev
```

- Tako bi moral delovati frontend na localhost na portu 5173

<h3 id="Preverjanje">2.4 Preverjanje delovanja</h3>

- Preverjanje delovanje poteka tako, da v brskalniku odpremo

```
http://localhost:5173/
```

- na kar bi se vam morala prikazati spletna stran

<h3 id="osnovna uporaba"> 2.5 Osnovna uporaba </h3>

Za uporabo naše aplikacije se je potrebno v aplikacijo prijaviti, kar je potrebno storiti, ko se odpravi v aplikacijo.

![alt text](image.png)

Če naš račun še en obsaja, potem pritisnemo na registracijo in se tako registriramo.

![alt text](image1.png)

Po uspešni prijavi bi vam morale biti na voljo vse funkcijonalnosti naše aplikacije.

![alt text](image2.png)

<!-- USAGE EXAMPLES -->
<h2 id="opis-projekta">3. Opis projekta</h2> Za osnovno uporabo aplikacije se je potrebno prijaviti v aplikacijo

<h3 id="Vzpostavitev-podatkovne-baze">3.1 Vzpostavitev podatkovne baze</h3>

1. ustvarili smo račun v Mongo DB cloud, kjer smo ustvarili našo podatkovno bazo
2. Pov

```javascript
var mongoDB =
  "mongodb+srv://admin:hpTmFEirA3PWFYSB@cluster0.jor2wzv.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
mongoose.connect(mongoDB);
mongoose.Promise = global.Promise;
var db = mongoose.connection;
```

<h3 id="Backend">3.2 Backend</h3>

Za ustvaranje zalednega dela smo uporabili Express in Javascript.<br/>
Začeli smo z implementacijo app.js datoteke v kateri se definirajo usmerjanje ( routing ), vse knjižnice in povezava na podatkovno bazo

<h2>app.js</h2>

```javascript
var createError = require("http-errors");
var express = require("express");
var path = require("path");
var cookieParser = require("cookie-parser");
var logger = require("morgan");

var cors = require("cors");

var mongoose = require("mongoose");
var mongoDB =
  "mongodb+srv://admin:hpTmFEirA3PWFYSB@cluster0.jor2wzv.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
mongoose.connect(mongoDB);
mongoose.Promise = global.Promise;
var db = mongoose.connection;
db.on("error", console.error.bind(console, "MongoDB connection error:"));

var indexRouter = require("./routes/index");
var usersRouter = require("./routes/userRoutes");
var eventRouter = require("./routes/eventRoutes");
var categoryRouter = require("./routes/categoryRoutes");
var reviewRouter = require("./routes/reviewRoutes");

var app = express();
app.use(
  cors({
    origin: "http://localhost:5173", // Allow this origin
    methods: "GET,HEAD,PUT,PATCH,POST,DELETE", // Allow these HTTP methods
    credentials: true, // Allow cookies to be sent
  })
);

// view engine setup
app.set("views", path.join(__dirname, "views"));
app.set("view engine", "hbs");

// session
var session = require("express-session");
var MongoStore = require("connect-mongo");
app.use(
  session({
    secret: "Serverless*2024",
    resave: true,
    saveUninitialized: false,
    store: MongoStore.create({ mongoUrl: mongoDB }),
  })
);

app.use(logger("dev"));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, "public")));

app.use("/", indexRouter);
app.use("/users", usersRouter);
app.use("/events", eventRouter);
app.use("/categories", categoryRouter);
app.use("/reviews", reviewRouter);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
  next(createError(404));
});

// error handler
app.use(function (err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get("env") === "development" ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render("error");
});

module.exports = app;
```

Naslednji korak je definicija modela - entitet, ki se bodo shranjevale v podatkovni bazi, spodaj bo priložen model za dogodek, so pa ostali izdelani podobno ( user , review, category ):

<h2>eventModel.js </h2>

```javascript
var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var eventSchema = new Schema({
  name: String,
  venue: String,
  address: String,
  startTime: String,
  date_start: Date,
  date_end: Date,
  description: String,
  contact: String,
  category: {
    type: Schema.Types.ObjectId,
    ref: "category",
  },
  eventImage: String,
  price: Number,
  attendees: [{ type: Schema.Types.ObjectId, ref: "user" }],
  owner: {
    type: Schema.Types.ObjectId,
    ref: "user",
  },
  location: {
    type: {
      type: String,
      enum: ["Point"],
      required: true,
    },
    coordinates: {
      type: [Number],
      required: true,
    },
  },
});

eventSchema.index({ location: "2dsphere" });

module.exports = mongoose.model("event", eventSchema);
```

Nato implementiramo usmernik ( routing ) za vsako entiteto posebej, spodaj bo ponovno prikazano routing za event ampak je podoben postopek pri ostalih entitetah

<h2>eventRouting.js</h2>

```javascript
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
```

Naslednji korak je implementacija funkcij, na katere vežemo poti, spodaj je prikazana implementacija event kontrolerja, kjer so implementirane funkcije, ki jih kličem v zgornji kodi.

<h2>eventController.js</h2>

```javascript
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
    const minAvgRating = parseInt(req.query.rating, 10); // Get minimum average rating

    // Initialize the filter object
    let filter = {};

    const applyFiltersAndRespond = (filter) => {
      // Check if date range is provided
      if (fromDate && toDate) {
        filter.date_start = {
          $gte: new Date(fromDate),
          $lte: new Date(toDate),
        };
      }

      // Check if price range is provided
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

        // If minAvgRating is provided, filter events based on average rating
        if (minAvgRating) {
          const eventIds = events.map((event) => event._id);

          ReviewsModel.aggregate(
            [
              { $match: { eventId: { $in: eventIds } } },
              { $group: { _id: "$eventId", avgRating: { $avg: "$rating" } } },
              { $match: { avgRating: { $gte: minAvgRating } } },
            ],
            function (err, results) {
              if (err) {
                return res.status(500).json({
                  message: "Error when calculating average ratings.",
                  error: err,
                });
              }

              const filteredEventIds = results.map((result) =>
                result._id.toString()
              );
              const filteredEvents = events.filter((event) =>
                filteredEventIds.includes(event._id.toString())
              );

              res.json(filteredEvents);
            }
          );
        } else {
          // Pass the events data to the view
          res.json(events);
        }
      });
    };

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
        applyFiltersAndRespond(filter);
      });
    } else {
      applyFiltersAndRespond(filter);
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

        // Populate the attendees field before returning the event
        EventModel.populate(
          event,
          { path: "attendees" },
          function (err, populatedEvent) {
            if (err) {
              return res.status(500).json({
                message: "Error when populating attendees",
                error: err,
              });
            }
            return res.json(populatedEvent);
          }
        );
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
      { new: true }
    )
      .populate("attendees")
      .exec(function (err, event) {
        if (err) {
          return res.status(500).json({
            message: "Error when leaving event",
            error: err,
          });
        }

        return res.json(event);
      });
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
      ReviewsModel.find({ eventId: id }).remove().exec();
      return res.status(204).json();
    });
  },
};
```

implementirana je tudi logika za delo z tokeni :

<h2>authenticateJWT.js</h2>

```javascript
const jwt = require("jsonwebtoken");

// Secret key for JWT
const secretKey = "Serverless*2024"; // Change this to a strong and secure key

// Middleware to generate JWT token
function generateToken(username, password, id) {
  return jwt.sign(
    { username: username, password: password, id: id },
    secretKey,
    {
      expiresIn: "1h",
    }
  ); // Token expires in 1 hour
}

// Middleware to verify JWT token
function verifyToken(req, res, next) {
  const authHeader = req.headers["authorization"];
  const token = authHeader && authHeader.split(" ")[1];

  if (!token) {
    return res.status(401).json({
      message: "Unauthorized: Token not provided",
    });
  }

  jwt.verify(token, secretKey, (err, decoded) => {
    if (err) {
      return res.status(403).json({ message: "Forbidden: Invalid token" });
    }

    req.userId = decoded.id;
    next();
  });
}

module.exports = { generateToken, verifyToken };
const jwt = require("jsonwebtoken");

// Secret key for JWT
const secretKey = "Serverless*2024"; // Change this to a strong and secure key

// Middleware to generate JWT token
function generateToken(username, password, id) {
  return jwt.sign(
    { username: username, password: password, id: id },
    secretKey,
    {
      expiresIn: "1h",
    }
  ); // Token expires in 1 hour
}

// Middleware to verify JWT token
function verifyToken(req, res, next) {
  const authHeader = req.headers["authorization"];
  const token = authHeader && authHeader.split(" ")[1];

  if (!token) {
    return res.status(401).json({
      message: "Unauthorized: Token not provided",
    });
  }

  jwt.verify(token, secretKey, (err, decoded) => {
    if (err) {
      return res.status(403).json({ message: "Forbidden: Invalid token" });
    }

    req.userId = decoded.id;
    next();
  });
}

module.exports = { generateToken, verifyToken };
```

<h2>

<h3 id="Frontend">3.3 Frontend</h3>

Implementacija čelnega dela je strukturno bolj preprosta, glavni program naloži index.html, ki ga react nato render-a. Naslednja potrebna implementacije je usmerjanje routing, kjer za vsako pot definiraš ime in katera stran se bo prikazala.

```javascript
function App() {
  const token = Cookies.get("token");
  console.log("token in app", token);
  return (
    <div className="bg-primaryBackground w-screen  h-full">
      {token ? <Navbar /> : <></>}
      <Routes>
        <Route path="/" element={token ? <List /> : <Navigate to="/login" />} />
        <Route
          path="/login"
          element={token ? <Navigate to="/" /> : <Login />}
        />
        <Route
          path="/register"
          element={token ? <Navigate to="/" /> : <Register />}
        />
        <Route
          path="/profile"
          element={token ? <Profile /> : <Navigate to="/login" />}
        />
        <Route
          path="/map"
          element={token ? <Map /> : <Navigate to="/login" />}
        />
        <Route path="/event/:id" element={<EventDetails />} /> <Route
          path="/my-events"
          element={<MyEventList />}
        />
        <Route path="/new-event" element={<EventAdd />} />
        <Route path="/event/edit/:id" element={<EventEdit />} />
        <Route
          path="/event/:id"
          element={token ? <EventDetails /> : <Navigate to="/login" />}
        />
        <Route path="/editprofile" element={token ? <EditProfile /> : <Navigate to="/login" />} />
      </Routes>
    </div>
  );
}
```

Naslednji korak je ustvarjanje stran. Spodaj bo prikaz ene od strani, ki je bila ustvarjena, da se uporabnik lahko vpiše.

```javascript
const Login = () => {
  const [username, setUsername] = useState < string > "";
  const [password, setPassword] = useState < string > "";

  const navigate = useNavigate();

  function hadleLogin(): void {
    if (username === "" || password === "") {
      return;
    }

    fetch("http://localhost:3000/users/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: username,
        password: password,
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        Cookies.set("token", data.token);
        Cookies.set("user", JSON.stringify(data.user));

        window.location.reload();
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }

  function handleRegisterRedirect(): void {
    navigate("/register");
  }

  return (
    <>
      <div className="min-h-screen bg-gray-100 py-6 flex flex-col justify-center sm:py-12">
        <div className="relative py-3 sm:max-w-xl sm:mx-auto">
          <div className="absolute inset-0 bg-gradient-to-r from-cyan-400 to-sky-500 shadow-lg transform -skew-y-6 sm:skew-y-0 sm:-rotate-6 sm:rounded-3xl"></div>
          <div className="relative px-4 py-10 bg-white shadow-lg sm:rounded-3xl sm:p-20">
            <div className="max-w-md mx-auto">
              <div>
                <h1 className="text-2xl font-semibold">Prijava</h1>
              </div>
              <div className="divide-y divide-gray-200">
                <div className="py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7">
                  <div className="relative">
                    <input
                      id="username"
                      name="username"
                      type="text"
                      value={username}
                      onChange={(e) => setUsername(e.target.value)}
                      className="peer placeholder-transparent h-10 w-full border-b-2 border-gray-300 text-gray-900 focus:outline-none focus:borer-rose-600"
                      placeholder="Username"
                    />
                    <label className="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">
                      Uporabniško ime
                    </label>
                  </div>
                  <div className="relative">
                    <input
                      id="password"
                      name="password"
                      type="password"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      className="peer placeholder-transparent h-10 w-full border-b-2 border-gray-300 text-gray-900 focus:outline-none focus:borer-rose-600"
                      placeholder="Password"
                    />
                    <label className="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">
                      Geslo
                    </label>
                  </div>
                  <div className="relative">
                    <button
                      onClick={() => hadleLogin()}
                      className="bg-cyan-500 text-white text-base px-4 rounded-md py-1 shadow-lg hover:bg-cyan-600"
                    >
                      Oddaj
                    </button>
                  </div>
                </div>
              </div>
            </div>

            <div className="w-full flex flex-col gap-2 justify-center">
              <div className=" text-sm text-gray-400 text-opacity-50">
                ALi še nimaš računa?
              </div>
              <button
                onClick={() => handleRegisterRedirect()}
                className="flex items-center flex-col  bg-white border border-gray-300 rounded-lg shadow-md px-6 py-2 text-sm font-medium text-gray-800 hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500"
              >
                <div>Registracija</div>
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
```

Če se nek del strani ponavlja ali je stran prevelika sama zase ( če zajema približno več kot 300 vrstic kode ali zelo kompleksno logiko ) se stran razdeli na komponento, da je bolj berljivo in lažje za vzdrževanje. Primer komponente, ki se ponavlja je EventCard, ki ga skozi spletno stran večkrat uporabim.

```javascript
const EventCard = ({ event, user, setUser }: EventCardProps) => {
  const navigate = useNavigate();
  function handleDetailsRedirect(_id: string): void {
    navigate("/event/" + _id);
  }

  const token = Cookies.get("token");
  function handleFollowing(): void {
    fetch("http://localhost:3000/events/showEvent/addFavorite/" + event._id, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);

        if (data == null) return;
        Cookies.set("user", JSON.stringify(data));
        setUser({
          username: data.username,
          email: data.email,
          password: data.password,
          profileImage: data.profileImage,
          favorites: data.favorites,
          _id: data._id,
        });
        console.log("okej ", user);
      });
  }
  function handleUnfollowing(): void {
    fetch(
      "http://localhost:3000/events/showEvent/removeFavorite/" + event._id,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
      }
    )
      .then((response) => response.json())
      .then((data) => {
        console.log(data);

        if (data == null) return;
        Cookies.set("user", JSON.stringify(data));
        setUser({
          username: data.username,
          email: data.email,
          password: data.password,
          profileImage: data.profileImage,
          favorites: data.favorites,
          _id: data._id,
        });
        console.log("okej ", user);
      });
  }

  return (
    <div className="max-w-sm bg-white border border-gray-200  shadow dark:bg-gray-800 dark:border-gray-700 lg:h-[500px]">
      <div className="h-72 bg-white ">
        <img
          className=""
          src={
            event?.eventImage?.substring(0, 8) == "https://"
              ? event?.eventImage
              : "http://localhost:3000" + event?.eventImage
          }
          alt="slika dogodka"
        />
      </div>
      <div className="p-5 flex flex-col h-fit justify-between">
        <div>
          <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
            {event?.name}
          </h5>

          <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">
            {event?.description?.substring(0, 80) + "..."}
          </p>
        </div>
        <div className="flex flex-row gap-1 justify-between h-10">
          <button
            onClick={() => handleDetailsRedirect(event?._id)}
            className=" p-2  text-sm font-medium  text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          >
            Read more
          </button>
          {JSON.parse(Cookies.get("user") || "").favorites?.includes(
            event?._id
          ) ? (
            <button
              className=" text-center bg-red-600 px-2  rounded-lg hover:opacity-80 text-white"
              onClick={() => handleUnfollowing()}
            >
              UnFollow
            </button>
          ) : (
            <button
              className=" bg-green-600 text-center px-2 rounded-lg  hover:opacity-80 text-white"
              onClick={() => handleFollowing()}
            >
              Follow
            </button>
          )}
        </div>
      </div>
    </div>
  );
};
```

<!-- CONTACT -->
<h2 id="kontakt">4. Kontakt</h2>

Ime skupine: Serverless <br/>
Člani skupine: Jan Namestnik, Nejc Cekuta, Metod Golob <br/>
Link do projketa: [Serverless](https://github.com/JanNamestnik/Serverless/tree/main)
<br /><br />

<!-- ACKNOWLEDGMENTS -->
<h2 id="viri">5. Viri</h2>

- [Dokumentacija za CSS](https://developer.mozilla.org/en-US/docs/Web/CSS)
- [Dokumentacija za HTML](https://developer.mozilla.org/en-US/docs/Web/HTML)
- [Dokumentacija za JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript)
- [Dokumentacija za TypeScript](https://www.typescriptlang.org/docs/)
- [Dokumentacija za Handlebars](https://handlebarsjs.com/guide/)
- [Visual Studio Code dokumentacija](https://code.visualstudio.com/docs)

<p align="right">(<a href="#readme-top">nazaj na vrh</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[REACT]: https://img.shields.io/badge/REACT-1572B6?style=for-the-badge&logo=react&logoColor=white
[REACT-url]: https://react.dev/
[SWIPERJS]: https://img.shields.io/badge/SWIPER_JS-1572B6?style=for-the-badge&logo=swiper&logoColor=white
[SWIPERJS-url]: https://swiperjs.com/react
[LEAFLET]: https://img.shields.io/badge/LEAFLET-00e600?style=for-the-badge&logo=leaflet&logoColor=white
[LEAFLET-url]: https://leafletjs.com/reference.html
[MUI]: https://img.shields.io/badge/MUI_ICONS-1572B6?style=for-the-badge&logo=mui&logoColor=white
[MUI-url]: https://mui.com/material-ui/material-icons/
[TAILWIND]: https://img.shields.io/badge/TAILWIND-1572B6?style=for-the-badge&logo=tailwindcss&logoColor=white
[TAILWIND-url]: https://tailwindcss.com/docs/installation
[VITE]: https://img.shields.io/badge/VITE-1572B6?style=for-the-badge&logo=vite&logoColor=white
[VITE-url]: https://vitejs.dev/
[HTML]: https://img.shields.io/badge/HTML-E34F26?style=for-the-badge&logo=html5&logoColor=white
[HTML-url]: https://developer.mozilla.org/en-US/docs/Web/HTML
[JavaScript]: https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black
[JavaScript-url]: https://developer.mozilla.org/en-US/docs/Web/JavaScript
[TypeScript]: https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white
[TypeScript-url]: https://www.typescriptlang.org/
[Handlebars]: https://img.shields.io/badge/Handlebars.js-000000?style=for-the-badge&logo=handlebarsdotjs&logoColor=white
[Handlebars-url]: https://handlebarsjs.com/
[VSCode]: https://img.shields.io/badge/VS%20Code-0078d7?style=for-the-badge&logo=visual-studio-code&logoColor=white
[VSCode-url]: https://code.visualstudio.com/
[JS-cookies-url]: https://www.npmjs.com/package/js-cookie/
[D3JS]: https://img.shields.io/badge/D3js-F9A03C?style=for-the-badge&logo=d3dotjs&logoColor=white
[D3js-url]: https://d3js.org/getting-started
[EXPRESS]: https://img.shields.io/badge/expressjs-000000?style=for-the-badge&logo=express&logoColor=white
[EXPRESS-url]: https://expressjs.com/
