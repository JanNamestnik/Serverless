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
