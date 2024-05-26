import React from "react";
import { useNavigate } from "react-router-dom";

interface EventShowCardProps {
  eventHere: MyEvent;
}

const EventShowCard = ({ eventHere }: EventShowCardProps) => {
  const navigate = useNavigate();
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
          className="bg-blue-500 text-white px-4 py-2 rounded-lg text-lg"
          onClick={() => navigate("/event/edit/" + eventHere._id)}
        >
          Edit Event
        </button>
      </div>
    </div>
  );
};

export default EventShowCard;
