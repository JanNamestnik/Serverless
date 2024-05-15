var express = require('express');
var router = express.Router();
var userController = require('../controllers/userController.js');
var multer = require('multer');
var upload = multer({ dest: 'public/userImages/' });

const { verifyToken } = require('../public/javascripts/authenticateJWT');

// Middleware for verifying JWT token on all routes except login and register
router.use((req, res, next) => {
    if (req.path === '/login' || req.path === '/register') {
        // Skip authentication for login and register routes
        return next();
    }
    verifyToken(req, res, (err) => {
        if (err) {
            return res.status(401).json({ message: 'Unauthorized: Invalid token' });
        }
        next();
    });
});

/*
 * GET
 */
router.get('/', userController.list);
router.get('/login', userController.showLogin);
router.get('/register', userController.showRegister);
router.get('/profile', userController.profile);
router.get('/logout', userController.logout);
router.get('/myFavorites', userController.myFavorites);

/*
 * GET
 */
router.get('/:id', userController.show);

/*
 * POST
 */
router.post('/', userController.create);
router.post('/login', userController.login);
router.post('/profile/update', upload.single('profileImage'), userController.updatePicture);

/*
 * PUT
 */
router.put('/:id', userController.update);

/*
 * DELETE
 */
router.delete('/:id', userController.remove);

module.exports = router;
