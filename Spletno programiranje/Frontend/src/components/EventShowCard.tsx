import React from "react";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";

interface EventShowCardProps {
  eventHere: MyEvent;
}

const EventShowCard = ({ eventHere }: EventShowCardProps) => {
  const token = Cookies.get("token");
  const navigate = useNavigate();
  function handleEventDelete(_id: string): void {
    fetch("http://localhost:3000/events/" + _id, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    }).then((response) => {
      if (response.ok) {
        console.log("Event deleted");
      }
      navigate(0);
    });
  }
  function handleDetailsRedirect(_id: string): void {
    navigate("/event/" + _id);
  }
  return (
    <div className="max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
      <img
        className="rounded-t-lg w-96 h-64"
        src={"http://localhost:3000" + eventHere?.eventImage}
        alt="slika dogodka"
      />

      <div className="p-5">
        <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
          {eventHere?.name}
        </h5>

        <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">
          {eventHere?.description?.substring(0, 80) + "..."}
        </p>
        <button
          onClick={() => handleDetailsRedirect(eventHere?._id)}
          className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          Read more
        </button>
        <button
          className="bg-blue-500 text-white px-4 py-2 rounded-lg text-lg"
          onClick={() => handleEventDelete(eventHere._id)}
        >
          Delete Event
        </button>
      </div>
    </div>
  );
};

export default EventShowCard;
