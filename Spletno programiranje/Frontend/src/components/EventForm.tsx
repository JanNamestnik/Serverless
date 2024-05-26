import React, { useState } from "react";
import { MapContainer, TileLayer, Marker, useMapEvents } from "react-leaflet";
import "leaflet/dist/leaflet.css";

import { categories } from "../types/Categories";
import { IconButton } from "@mui/material";
import { Upload } from "@mui/icons-material";

interface EventFormProps {
  event: MyEvent;
  setEvent: React.Dispatch<React.SetStateAction<MyEvent>>;
  submit: () => void;
  profileImage: File | null;
  setProfileImage: React.Dispatch<React.SetStateAction<File | null>>;
}

const EventForm = ({
  event,
  setEvent,
  submit,
  profileImage,
  setProfileImage,
}: EventFormProps) => {
  const handleChange = (e: any) => {
    const { name, value } = e.target;
    setEvent((prev) => ({ ...prev, [name]: value }));
  };

  const handleLocationChange = (coords: any) => {
    setEvent((prev) => ({
      ...prev,
      location: { type: "Point", coordinates: coords },
    }));
  };

  function LocationMarker() {
    useMapEvents({
      click(e) {
        handleLocationChange([e.latlng.lat, e.latlng.lng]);
      },
    });

    return event.location.coordinates ? (
      <Marker position={event.location.coordinates as any} />
    ) : null;
  }

  return (
    <div className="container mx-auto p-4 flex flex-col gap-3">
      <div>
        <label className="block text-sm font-medium text-gray-700">Name</label>
        <input
          type="text"
          name="name"
          value={event.name}
          onChange={handleChange}
          className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-1 ps-3"
        />
      </div>
      <div>
        <label className="block text-sm font-medium text-gray-700">Venue</label>
        <input
          type="text"
          name="venue"
          value={event.venue}
          onChange={handleChange}
          className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-1 ps-3"
        />
      </div>
      <div>
        <label className="block text-sm font-medium text-gray-700">
          Address
        </label>
        <input
          type="text"
          name="address"
          value={event.address}
          onChange={handleChange}
          className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-1 ps-3"
        />
      </div>
      <div>
        <label className="block text-sm font-medium text-gray-700">
          Start Time
        </label>
        <input
          type="time"
          name="startTime"
          value={event.startTime}
          onChange={handleChange}
          className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-1 ps-3"
        />
      </div>
      <div>
        <label className="block text-sm font-medium text-gray-700">
          Start Date
        </label>
        <input
          type="date"
          name="date_start"
          value={event.date_start.toString()}
          onChange={handleChange}
          className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-1 ps-3"
        />
      </div>
      <div>
        <label className="block text-sm font-medium text-gray-700">
          End Date
        </label>
        <input
          type="date"
          name="date_end"
          value={event.date_end.toString()}
          onChange={handleChange}
          className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-1 ps-3"
        />
      </div>
      <div>
        <label className="block text-sm font-medium text-gray-700">
          Description
        </label>
        <textarea
          name="description"
          value={event.description}
          onChange={handleChange}
          className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-1 ps-3"
        />
      </div>
      <div>
        <label className="block text-sm font-medium text-gray-700">
          Contact
        </label>
        <input
          type="text"
          name="contact"
          value={event.contact}
          onChange={handleChange}
          className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-1 ps-3"
        />
      </div>
      <div>
        <div>
          <label className="block text-sm font-medium text-gray-700">
            Category
          </label>
          <select
            name="category"
            value={event.category.type}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2"
          >
            <option value="">Select a category</option>
            {categories.map((category) => (
              <option key={category._id.$oid} value={category._id.$oid}>
                {category.name}
              </option>
            ))}
          </select>
        </div>
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700">Price</label>
        <input
          type="number"
          name="price"
          value={event.price}
          onChange={handleChange}
          className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm p-2 ps-3"
        />
      </div>
      <div>
        {profileImage == null ? (
          <div className="relative">
            <label
              title="Click to upload"
              htmlFor="button2"
              className="cursor-pointer flex items-center gap-4 px-6 py-4 before:border-gray-400/60 hover:before:border-gray-300 group before:bg-gray-100 before:absolute before:inset-0 before:rounded-3xl before:border before:border-dashed before:transition-transform before:duration-300 hover:before:scale-105 active:duration-75 active:before:scale-95"
            >
              <div>
                <IconButton>
                  <Upload />
                </IconButton>
              </div>
              <div className="relative">
                <span className="block text-base font-semibold relative text-blue-900 group-hover:text-blue-500">
                  Upload an event image
                </span>
              </div>
            </label>
            <input
              hidden
              type="file"
              name="button2"
              id="button2"
              onChange={(e) =>
                setProfileImage(e.target.files ? e.target.files[0] : null)
              }
            />
          </div>
        ) : (
          <div className="flex flex-row items-center gap-3">
            <div>
              <img
                className=" w-64 h-64 rounded-full"
                src={URL.createObjectURL(profileImage)}
                alt="avatar"
              />
            </div>
            <div>{profileImage.name.substring(0, 10)}</div>
            <button
              className="text-red-500"
              onClick={() => {
                console.log(profileImage);
                setProfileImage(null);
              }}
            >
              Remove
            </button>
          </div>
        )}
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700">
          Location
        </label>
        <MapContainer center={[46.119944, 14.815333]} zoom={8} className="h-64">
          <TileLayer
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          />
          <LocationMarker />
        </MapContainer>
      </div>
      <button
        onClick={() => submit()}
        className="px-4 py-2 bg-blue-500 text-white rounded-md"
      >
        Submit
      </button>
    </div>
  );
};

export default EventForm;
