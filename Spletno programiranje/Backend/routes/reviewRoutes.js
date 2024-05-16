var express = require('express');
var router = express.Router();
var reviewController = require('../controllers/reviewController.js');

const { verifyToken } = require('../public/javascripts/authenticateJWT');

/*
 * GET
 */
router.get('/', reviewController.list);

/*
 * GET
 */
router.get('/:id', reviewController.show);

/*
 * POST
 */
router.post('/', verifyToken, reviewController.create);

/*
 * PUT
 */
router.put('/:id', verifyToken, reviewController.update);

/*
 * DELETE
 */
router.delete('/:id', verifyToken, reviewController.remove);

module.exports = router;
