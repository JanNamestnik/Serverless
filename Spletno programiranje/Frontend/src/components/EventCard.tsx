import React from "react";

interface EventCardProps {
  event: MyEvent;
}

const EventCard = ({ event }: EventCardProps) => {
  function handleDetailsRedirect(_id: string): void {
    throw new Error("Function not implemented.");
  }

  return (
    <div className="max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
      <img
        className="rounded-t-lg"
        src={"http://localhost:3000" + event.eventImage}
        alt="slika dogodka"
      />

      <div className="p-5">
        <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
          {event.name}
        </h5>

        <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">
          {event.description.substring(0, 80) + "..."}
        </p>
        <button
          onClick={() => handleDetailsRedirect(event._id)}
          className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          Read more
        </button>
      </div>
    </div>
  );
};

export default EventCard;
