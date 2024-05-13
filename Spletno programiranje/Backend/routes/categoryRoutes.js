var express = require('express');
var router = express.Router();
var categoryController = require('../controllers/categoryController.js');

/*
 * GET
 */
router.get('/', categoryController.list);

/*
 * GET
 */
router.get('/:id', categoryController.show);

/*
 * POST
 */
router.post('/', categoryController.create);

/*
 * PUT
 */
router.put('/:id', categoryController.update);

/*
 * DELETE
 */
router.delete('/:id', categoryController.remove);

module.exports = router;
