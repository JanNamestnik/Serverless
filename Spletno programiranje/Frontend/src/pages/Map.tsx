// Map.tsx
import React, { useEffect } from "react";
import { MapContainer, Marker, Popup, TileLayer, useMap } from "react-leaflet";
import Cookies from "js-cookie";
import { useNavigate } from "react-router-dom";
import { ArrowLeft } from "@mui/icons-material";

export default function Map() {
  const navigate = useNavigate();
  const [events, setEvents] = React.useState<MyEvent[]>([]);
  const token = Cookies.get("token");
  useEffect(() => {
    fetch("http://localhost:3000/events", {
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
  }, []);

  function handleDetails(_id: string): void {
    navigate("/event/" + _id);
  }

  return (
    <div className="pt-20">
      <MapContainer
        center={[46.55465, 15.645881]}
        zoom={13}
        scrollWheelZoom={true}
      >
        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        {events.map((event, index) => (
          <Marker
            key={index}
            position={[
              event.location.coordinates[1],
              event.location.coordinates[0],
            ]}
          >
            <Popup>
              <div>
                <h1>{event.name}</h1>
                <p>{event.description}</p>
                <button
                  className=" bg-blue-500 hover:bg-blue-700 text-white font-bold py-1 ps-1 pe-4 rounded-full f"
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
