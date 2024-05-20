import { Navigate, Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Home from "./pages/Home";
import Cookies from "js-cookie";
import Navbar from "./components/Navbar";
function App() {
  const token = Cookies.get("token");
  return (
    <>
      {token ? <Navbar /> : <></>}
      <Routes>
        <Route path="/" element={token ? <Home /> : <Navigate to="/login" />} />
        <Route path="/about" element={<div className="App"> yoo </div>} />
        <Route
          path="/login"
          element={token ? <Navigate to="/" /> : <Login />}
        />
        <Route path="/register" element={<Register />} />
        <Route path="/profile" element={<div className="App"> yoo </div>} />
        <Route path="/map" element={<div className="App"> yoo </div>} />
      </Routes>
    </>
  );
}

export default App;
