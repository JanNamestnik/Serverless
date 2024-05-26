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
    <div className="pt-20">
      <div className="flex flex-row ">
        <div className="w-3/12"></div>
        <div className="p-4 m-4 bg-white  h-[600px] w-full">
          <div className=" text-4xl font-bold text-start flex flex-row justify-between">
            My events
            <button
              className="bg-blue-500 text-white px-4 py-2 rounded-lg text-lg"
              onClick={() => navigate("/new-event")}
            >
              New event
            </button>
          </div>

          <ul>
            {events.map((event) => (
              <li key={event._id}>
                <h2>{event.name}</h2>
                <p>{event.description}</p>
              </li>
            ))}
          </ul>
          {events.length === 0 && (
            <div className="italic font-extralight p-6">No events</div>
          )}
        </div>
        <div className="w-3/12"></div>
      </div>
    </div>
  );
};

export default MyEventList;
