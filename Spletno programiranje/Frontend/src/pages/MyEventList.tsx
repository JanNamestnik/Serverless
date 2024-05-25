import { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";

const MyEventList = () => {
  const [events, setEvents] = useState<MyEvent[]>([]);
  const navigate = useNavigate();
  const token = Cookies.get("token");
  const fetched = useRef(false);

  useEffect(() => {
    if (token === undefined) {
      navigate("/login");
    }
    if (!fetched.current) {
      fetched.current = true;
      fetch("http://localhost:5000/events/my", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      })
        .then((response) => response.json())
        .then((data) => {
          setEvents(data);
        });
    }
  }, []);

  return (
    <div>
      <h1>My events</h1>
      <button onClick={() => navigate("/new-event")}>New event</button>
      <ul>
        {events.map((event) => (
          <li key={event._id}>
            <h2>{event.name}</h2>
            <p>{event.description}</p>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default MyEventList;
