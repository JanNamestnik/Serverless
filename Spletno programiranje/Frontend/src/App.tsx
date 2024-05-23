import { Navigate, Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Home from "./pages/Home";
import Cookies from "js-cookie";
import Navbar from "./components/Navbar";
import Profile from "./pages/Profile";
import List from "./pages/List";
import EventDetails from "./components/EventDetails";
function App() {
  const token = Cookies.get("token");
  console.log("token in app", token);
  return (
    <div className="bg-primaryBackground w-screen  h-screen">
      {token ? <Navbar /> : <></>}
      <Routes>
        <Route path="/" element={token ? <Home /> : <Navigate to="/login" />} />
        <Route path="/about" element={<div className="App"> yoo </div>} />
        <Route
          path="/login"
          element={token ? <Navigate to="/" /> : <Login />}
        />
        <Route
          path="/register"
          element={token ? <Navigate to="/" /> : <Register />}
        />
        <Route path="/profile" element={<Profile />} />
        <Route path="/map" element={<div className="App"> yoo </div>} />
        <Route
          path="/list"
          element={token ? <List /> : <Navigate to="/login" />}
        />
        <Route path="/event/:id" element={<EventDetails />} />{" "}
      </Routes>
    </div>
  );
}

export default App;
