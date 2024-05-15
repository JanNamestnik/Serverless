const jwt = require('jsonwebtoken');

// Secret key for JWT
const secretKey = 'mykey'; // Change this to a strong and secure key

// Middleware to generate JWT token
function generateToken(username, password) {
    return jwt.sign({ username: username, password: password }, secretKey, { expiresIn: '1h' }); // Token expires in 1 hour
}

// Middleware to verify JWT token
function verifyToken(req, res, next) {
    const token = req.cookies.token;
    try{
        if (!token) {
            return res.status(401).json({ message: 'Unauthorized: Token not provided' });
        }
    
        jwt.verify(token, secretKey, (err, decoded) => {
            req.userId = decoded.id;
            next();
        });
        
    } catch (err){
        res.clearCookie('token');
        return res.redirect('/users/login');
    }
}

module.exports = { generateToken, verifyToken };
