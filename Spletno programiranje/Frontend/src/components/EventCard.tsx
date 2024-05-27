import React, { useState } from "react";

import Cookies from "js-cookie";
import { useNavigate } from "react-router-dom";

interface EventCardProps {
  event: MyEvent;
  user: User;
  setUser: React.Dispatch<React.SetStateAction<User>>;
}

const EventCard = ({ event, user, setUser }: EventCardProps) => {
  const navigate = useNavigate();
  function handleDetailsRedirect(_id: string): void {
    navigate("/event/" + _id);
  }

  const token = Cookies.get("token");
  function handleFollowing(): void {
    fetch("http://localhost:3000/events/showEvent/addFavorite/" + event._id, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);

        if (data == null) return;
        Cookies.set("user", JSON.stringify(data));
        setUser({
          username: data.username,
          email: data.email,
          password: data.password,
          profileImage: data.profileImage,
          favorites: data.favorites,
          _id: data._id,
        });
        console.log("okej ", user);
      });
  }
  function handleUnfollowing(): void {
    fetch(
      "http://localhost:3000/events/showEvent/removeFavorite/" + event._id,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
      }
    )
      .then((response) => response.json())
      .then((data) => {
        console.log(data);

        if (data == null) return;
        Cookies.set("user", JSON.stringify(data));
        setUser({
          username: data.username,
          email: data.email,
          password: data.password,
          profileImage: data.profileImage,
          favorites: data.favorites,
          _id: data._id,
        });
        console.log("okej ", user);
      });
  }

  function handleLeaving(): void {
    fetch("http://localhost:3000/events/showEvent/leave/" + event._id, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
      });
  }

  function handleAttending(): void {
    fetch("http://localhost:3000/events/showEvent/attend/" + event._id, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);

        if (data == null) return;
        Cookies.set("user", JSON.stringify(data));
        setUser({
          username: data.username,
          email: data.email,
          password: data.password,
          profileImage: data.profileImage,
          favorites: data.favorites,
          _id: data._id,
        });
        console.log("okej ", user);
      });
  }

  function handleHidding(_id: string): void {
    fetch("http://localhost:3000/events/showEvent/hide/" + _id, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        setUser(data);
      });
  }

  return (
    <div className="max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700">
      <img
        className="rounded-t-lg  max-h-72 "
        src={"http://localhost:3000" + event?.eventImage}
        alt="slika dogodka"
      />

      <div className="p-5 flex flex-col">
        <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
          {event?.name}
        </h5>

        <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">
          {event?.description?.substring(0, 80) + "..."}
        </p>

        <div className="flex flex-row gap-1">
          <button
            onClick={() => handleDetailsRedirect(event?._id)}
            className=" items-center px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
          >
            Read more
          </button>
          {JSON.parse(Cookies.get("user") || "").favorites?.includes(
            event?._id
          ) ? (
            <button
              className=" bg-red-600 p-2 rounded-lg m-2 hover:opacity-80 text-white"
              onClick={() => handleUnfollowing()}
            >
              UnFollow
            </button>
          ) : (
            <button
              className=" bg-green-600 p-2 rounded-lg m-2 hover:opacity-80 text-white"
              onClick={() => handleFollowing()}
            >
              Follow
            </button>
          )}
          {event?.attendees?.includes(
            JSON.parse(Cookies.get("user") || "")._id
          ) ? (
            <button
              className=" bg-red-600 p-2 rounded-lg m-2 hover:opacity-80 text-white"
              onClick={() => handleLeaving()}
            >
              UnAttend
            </button>
          ) : (
            <button
              className=" bg-green-600 p-2 rounded-lg m-2 hover:opacity-80 text-white"
              onClick={() => handleAttending()}
            >
              Attend
            </button>
          )}
          <div>
            <button
              className=" bg-teal-500 text-white  rounded-xl p-2 m-2 hover:opacity-80"
              onClick={() => handleHidding(event._id)}
            >
              Hide
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EventCard;
