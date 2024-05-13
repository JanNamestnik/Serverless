var express = require('express');
var router = express.Router();
var reviewController = require('../controllers/reviewController.js');

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
router.post('/', reviewController.create);

/*
 * PUT
 */
router.put('/:id', reviewController.update);

/*
 * DELETE
 */
router.delete('/:id', reviewController.remove);

module.exports = router;
