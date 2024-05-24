import { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import Cookies from "js-cookie";

const EventDetails = () => {
  const { id } = useParams();
  const [event, setEvent] = useState<MyEvent>({} as MyEvent);
  const fetched = useRef(true);
  const token = Cookies.get("token");
  useEffect(() => {
    if (fetched.current) {
      fetched.current = false;
    } else {
      return;
    }
    console.log(token);
    fetch("http://localhost:3000/events/" + id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        setEvent(data as MyEvent);
      });
  }, []);
  return (
    <div className="pt-20 ">
      <div className="bg-gray-100 flex items-center justify-center min-h-screen">
        <div className="bg-white rounded-lg shadow-lg p-6 max-w-2xl w-full">
          <div className="flex flex-col md:flex-row md:items-center">
            <img
              src={"http://localhost:3000" + event.eventImage}
              alt="Event Image"
              className="w-full md:w-1/3 rounded-lg md:mr-6 mb-4 md:mb-0"
            />
            <div className="flex-1">
              <h1 className="text-3xl font-bold mb-2">{event.name}</h1>
              <p className="text-gray-700 mb-4">
                Venue: <span className="font-semibold">{event.venue}</span>
              </p>
              <p className="text-gray-700 mb-4">
                Address: <span className="font-semibold">{event.address}</span>
              </p>
              <p className="text-gray-700 mb-4">
                Date:{" "}
                <span className="font-semibold">
                  {event.date_start
                    ? new Date(event.date_start.toString()).toDateString()
                    : ""}{" "}
                  -{" "}
                  {event.date_end
                    ? new Date(event.date_end.toString()).toDateString()
                    : ""}
                </span>
              </p>
              <p className="text-gray-700 mb-4">
                Time:{" "}
                <span className="font-semibold">
                  {event.date_start
                    ? new Date(event.date_start.toString()).toLocaleTimeString()
                    : " "}
                  -
                  {event.date_end
                    ? new Date(event.date_end.toString()).toLocaleTimeString()
                    : " "}
                </span>
              </p>
              <p className="text-gray-700 mb-4">
                Description:{" "}
                <span className="font-semibold">{event.description}</span>
              </p>
              <p className="text-gray-700 mb-4">
                Price: <span className="font-semibold">{event.price} €</span>
              </p>
              <p className="text-gray-700 mb-4">
                Contact: <span className="font-semibold">{event.contact}</span>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EventDetails;
