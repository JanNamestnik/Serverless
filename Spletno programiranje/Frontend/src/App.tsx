import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";
import { Route, Routes } from "react-router-dom";
import Login from "./pages/Login";

function App() {
  const [count, setCount] = useState(0);

  return (
    <>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/about" element={<div className="App"> yoo </div>} />
        <Route path="/login" element={<div className="App"> yoo </div>} />
        <Route path="/register" element={<div className="App"> yoo </div>} />
        <Route path="/profile" element={<div className="App"> yoo </div>} />
        <Route path="/map" element={<div className="App"> yoo </div>} />
      </Routes>
    </>
  );
}

export default App;
