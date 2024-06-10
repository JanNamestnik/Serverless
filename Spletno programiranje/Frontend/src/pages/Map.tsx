import React, { useEffect, useRef, useState } from "react";
import { MapContainer, Marker, Popup, TileLayer } from "react-leaflet";
import Cookies from "js-cookie";
import { useNavigate } from "react-router-dom";
import { ArrowLeft } from "@mui/icons-material";
import { categories } from "../types/Categories";
import RatingEdit from "../components/RatingEdit";

export default function Map() {
  const navigate = useNavigate();
  const [events, setEvents] = useState<MyEvent[]>([]);
  const [currentDate, setCurrentDate] = useState(new Date());
  const [isAnimating, setIsAnimating] = useState(false);
  const [animationEnded, setAnimationEnded] = useState(true);
  const [filter, setFilter] = useState({
    category: "",
    fromDate: "",
    toDate: "",
    minPrice: "",
    maxPrice: "",
    rating: "",
  });
  const token = Cookies.get("token");
  const markersRef = useRef<L.Marker[]>([]);
  const [rating, setRating] = useState(5);

  useEffect(() => {
    fetchEvents();
  }, [filter, token]);

  const fetchEvents = () => {
    //handleChange({ target: { name: "rating", value: rating } });
    const queryParams = new URLSearchParams(filter);

    fetch(`http://localhost:3000/events/filter?${queryParams}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        setEvents(data);
      });
  };

  useEffect(() => {
    let interval = null;
    const dates = [
      ...new Set(
        events.map((event) => new Date(event.date_start).toDateString())
      ),
    ].map((dateStr) => new Date(dateStr));

    let index = 0;

    if (isAnimating) {
      interval = window.setInterval(() => {
        setCurrentDate(dates[index]);
        index = (index + 1) % dates.length;
      }, 5000);
    }

    return () => {
      if (interval) {
        clearInterval(interval);
      }
    };
  }, [events, isAnimating]);

  const filteredEvents = isAnimating
    ? events.filter(
        (event) =>
          new Date(event.date_start).toDateString() ===
          currentDate.toDateString()
      )
    : events;

  function handleDetails(_id: string): void {
    navigate("/event/" + _id);
  }

  function resetAnimation() {
    if (events.length > 0) {
      setCurrentDate(new Date(events[0].date_start));
      setIsAnimating(true);
    }
  }

  const handleToggleAnimation = () => {
    setIsAnimating(!isAnimating);
  };

  const handleChange = (e: any) => {
    const { name, value } = e.target;
    setFilter((prevFilter) => ({
      ...prevFilter,
      [name]: value,
    }));
  };

  const resetFilter = () => {
    setFilter({
      category: "",
      fromDate: "",
      toDate: "",
      minPrice: "",
      maxPrice: "",
      rating: "",
    });
  };
  return (
    <div className="relative pt-20">
      {/* Filter Card */}
      <div className="absolute top-20 right-5 m-4 p-4 bg-white shadow-lg rounded-lg z-10 w-80">
        <div className="filter-container">
          <div className="p-4 justify-around mx-auto flex flex-col space-y-4">
            <div className=" text-xl">Filters and animation</div>
            <div className="mb-4">
              <select
                name="category"
                value={filter.category}
                onChange={handleChange}
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                <option value="">Select Category</option>
                {categories.map((category) => (
                  <option key={category._id.$oid} value={category.name}>
                    {category.name}
                  </option>
                ))}
              </select>
            </div>
            <div className="mb-4">
              <input
                type="date"
                name="fromDate"
                value={filter.fromDate}
                onChange={handleChange}
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
            <div className="mb-4">
              <input
                type="date"
                name="toDate"
                value={filter.toDate}
                onChange={handleChange}
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
            <div className="mb-4">
              <input
                type="number"
                name="minPrice"
                placeholder="Min Price"
                value={filter.minPrice}
                onChange={handleChange}
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
            <div className="mb-4">
              <input
                type="number"
                name="maxPrice"
                placeholder="Max Price"
                value={filter.maxPrice}
                onChange={handleChange}
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
            <div className="flex flex-row justify-between">
              <div>Rating:</div>{" "}
              <button
                onClick={() =>
                  handleChange({
                    target: { name: "rating", value: rating },
                  }) as any
                }
              >
                <RatingEdit rating={rating} setRating={setRating} />
              </button>
            </div>
            <div className="items-center flex flex-row justify-between gap-3">
              <button
                className="p-2 bg-blue-500 text-white rounded-xl"
                onClick={fetchEvents}
              >
                Apply Filter
              </button>

              <button
                className="p-2 bg-red-500 text-white rounded-xl"
                onClick={resetFilter}
              >
                Clear Filter
              </button>
            </div>
          </div>
        </div>
        <div className="flex flex-col space-y-2 mt-4">
          <button
            className="p-2 bg-green-500 hover:bg-green-700 text-white font-bold rounded"
            onClick={resetAnimation}
          >
            Reset Animation
          </button>
          <button
            className="p-2 bg-red-500 hover:bg-red-700 text-white font-bold rounded"
            onClick={handleToggleAnimation}
          >
            {isAnimating ? "Stop Animation" : "Start Animation"}
          </button>
        </div>
        <div className="mt-4 text-lg font-bold text-center">
          Current Date: {currentDate.toDateString()}
        </div>
      </div>

      {/* Map */}
      <MapContainer
        center={[46.55465, 15.645881]}
        zoom={13}
        scrollWheelZoom={true}
        className="h-screen w-full z-0 absolute"
      >
        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        {filteredEvents.map((event, index) => (
          <Marker
            key={event._id}
            position={[
              event.location.coordinates[1],
              event.location.coordinates[0],
            ]}
            ref={(marker) => {
              marker?.openPopup();
              if (marker) {
                markersRef.current[index] = marker;
                marker.options.event = event;
              }
            }}
          >
            <Popup>
              <div>
                <h1>{event.name}</h1>
                <p>{event.description}</p>
                <button
                  className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-1 ps-1 pe-4 rounded-full"
                  onClick={() => handleDetails(event._id)}
                >
                  <ArrowLeft />
                  Details
                </button>
              </div>
            </Popup>
          </Marker>
        ))}
      </MapContainer>
    </div>
  );
}
