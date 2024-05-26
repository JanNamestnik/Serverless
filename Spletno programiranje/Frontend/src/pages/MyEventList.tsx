import { SetStateAction, useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import EventCard from "../components/EventCard";
import EventShowCard from "../components/EventShowCard";

const MyEventList = () => {
  const [events, setEvents] = useState<MyEvent[]>([]);
  const navigate = useNavigate();
  const token = Cookies.get("token");
  const user = JSON.parse(Cookies.get("user") || "{}");
  const fetched = useRef(false);

  useEffect(() => {
    if (token === undefined) {
      navigate("/login");
    }
    if (!fetched.current) {
      fetched.current = true;
      fetch("http://localhost:3000/events/listmyevents", {
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
                <EventShowCard eventHere={event} />
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
