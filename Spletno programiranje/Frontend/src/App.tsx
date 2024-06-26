import { Navigate, Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";

import Cookies from "js-cookie";
import Navbar from "./components/Navbar";
import Profile from "./pages/Profile";
import List from "./pages/List";
import EventDetails from "./components/EventDetails";
import Map from "./pages/Map";

import MyEventList from "./pages/MyEventList";
import EventAdd from "./components/EventAdd";
import EventEdit from "./components/EventEdit";

import EditProfile from "./pages/EditProfile";

function App() {
  const token = Cookies.get("token");
  console.log("token in app", token);
  return (
    <div className="bg-primaryBackground w-screen  h-full">
      {token ? <Navbar /> : <></>}
      <Routes>
        <Route path="/" element={token ? <List /> : <Navigate to="/login" />} />
        <Route
          path="/login"
          element={token ? <Navigate to="/" /> : <Login />}
        />
        <Route
          path="/register"
          element={token ? <Navigate to="/" /> : <Register />}
        />
        <Route
          path="/profile"
          element={token ? <Profile /> : <Navigate to="/login" />}
        />
        <Route
          path="/map"
          element={token ? <Map /> : <Navigate to="/login" />}
        />
        <Route path="/event/:id" element={<EventDetails />} />{" "}
        <Route path="/my-events" element={<MyEventList />} />
        <Route path="/new-event" element={<EventAdd />} />
        <Route path="/event/edit/:id" element={<EventEdit />} />
        <Route
          path="/event/:id"
          element={token ? <EventDetails /> : <Navigate to="/login" />}
        />
        <Route
          path="/editprofile"
          element={token ? <EditProfile /> : <Navigate to="/login" />}
        />
      </Routes>
    </div>
  );
}

export default App;
