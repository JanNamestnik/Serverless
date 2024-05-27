import React, { useEffect, useRef, useState } from "react";
import { MapContainer, Marker, Popup, TileLayer } from "react-leaflet";
import Cookies from "js-cookie";
import { useNavigate } from "react-router-dom";
import { ArrowLeft } from "@mui/icons-material";

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
  });
  const token = Cookies.get("token");
  const markersRef = useRef<L.Marker[]>([]);

  useEffect(() => {
    fetchEvents();
  }, [filter, token]);

  const fetchEvents = () => {
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

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFilter((prevFilter) => ({
      ...prevFilter,
      [name]: value,
    }));
  };
  return (
    <div className="pt-20">
      {/* Filter UI */}
      <div className="filter-container">
        <input
          type="text"
          name="category"
          placeholder="Category"
          value={filter.category}
          onChange={handleChange}
        />
        <input
          type="date"
          name="fromDate"
          value={filter.fromDate}
          onChange={handleChange}
        />
        <input
          type="date"
          name="toDate"
          value={filter.toDate}
          onChange={handleChange}
        />
        <input
          type="number"
          name="minPrice"
          placeholder="Min Price"
          value={filter.minPrice}
          onChange={handleChange}
        />
        <input
          type="number"
          name="maxPrice"
          placeholder="Max Price"
          value={filter.maxPrice}
          onChange={handleChange}
        />
        <button onClick={fetchEvents}>Apply Filter</button>
      </div>
      {/* Map */}
      <MapContainer
        center={[46.55465, 15.645881]}
        zoom={13}
        scrollWheelZoom={true}
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
      <div className="flex space-x-4">
        <button
          className="p-4 m-7 mb-4 bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-full"
          onClick={resetAnimation}
        >
          Reset Animation
        </button>
        <button
          className="p-4 m-7 mb-4 bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-full"
          onClick={handleToggleAnimation}
        >
          {isAnimating ? "Stop Animation" : "Start Animation"}
        </button>
      </div>
      <div className="mb-4 text-lg font-bold">
        Current Date: {currentDate.toDateString()}
      </div>
    </div>
  );
}
