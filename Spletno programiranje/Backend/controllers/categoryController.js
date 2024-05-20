var CategoryModel = require('../models/categoryModel.js');

/**
 * categoryController.js
 *
 * @description :: Server-side logic for managing categorys.
 */
module.exports = {

    /**
     * categoryController.list()
     */
    list: function (req, res) {
        CategoryModel.find(function (err, categorys) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when getting category.',
                    error: err
                });
            }

            return res.json(categorys);
        });
    },

    /**
     * categoryController.show()
     */
    show: function (req, res) {
        var id = req.params.id;

        CategoryModel.findOne({_id: id}, function (err, category) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when getting category.',
                    error: err
                });
            }

            if (!category) {
                return res.status(404).json({
                    message: 'No such category'
                });
            }

            return res.json(category);
        });
    },

    /**
     * categoryController.create()
     */
    create: function (req, res) {
        var category = new CategoryModel({
			name : req.body.name
        });

        category.save(function (err, category) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when creating category',
                    error: err
                });
            }

            return res.status(201).json(category);
        });
    },

    /**
     * categoryController.update()
     */
    update: function (req, res) {
        var id = req.params.id;

        CategoryModel.findOne({_id: id}, function (err, category) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when getting category',
                    error: err
                });
            }

            if (!category) {
                return res.status(404).json({
                    message: 'No such category'
                });
            }

            category.name = req.body.name ? req.body.name : category.name;
			
            category.save(function (err, category) {
                if (err) {
                    return res.status(500).json({
                        message: 'Error when updating category.',
                        error: err
                    });
                }

                return res.json(category);
            });
        });
    },

    /**
     * categoryController.remove()
     */
    remove: function (req, res) {
        var id = req.params.id;

        CategoryModel.findByIdAndRemove(id, function (err, category) {
            if (err) {
                return res.status(500).json({
                    message: 'Error when deleting the category.',
                    error: err
                });
            }

            return res.status(204).json();
        });
    }
};
