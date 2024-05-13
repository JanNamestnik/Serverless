var express = require('express');
var router = express.Router();
var eventController = require('../controllers/eventController.js');
var multer = require('multer');
var upload = multer({dest: 'public/images/'});

function requireLogin(req, res, next){
    if(req.session && req.session.userId){
        return next();
    } else {
        var err = new Error('You must be logged in to view this page.');
        err.status = 401;
        return next(err);
    }
}

/*
 * GET
 */
router.get('/', eventController.list);
router.get('/add', eventController.add);
router.get('/list', eventController.listAll);
router.get('/filter', eventController.filterEvents);



/*
 * GET
 */
router.get('/:id', eventController.show);
router.get('/showEvent/:id', eventController.showEvent);

/*
 * POST
 */
router.post('/', requireLogin, upload.single('image'), eventController.create);

/*
 * PUT
 */
router.put('/:id', eventController.update);

/*
 * DELETE
 */
router.delete('/:id', eventController.remove);

module.exports = router;
