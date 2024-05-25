import React, { useEffect, useRef, useState } from "react";
import Cookies from "js-cookie";
import { Avatar } from "@mui/material";
import { useNavigate } from "react-router-dom";

const Profile = () => {
  const navigate = useNavigate();
  const fetched = useRef(false);
  const token = Cookies.get("token");
  const [attendingEvents, setAttendingEvents] = useState<MyEvent[]>([]);
  useEffect(() => {
    if (!fetched.current) {
      fetched.current = true;
    } else {
      return;
    }
    fetch("http://localhost:3000/events/listattending", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        for (const event of data) {
          setAttendingEvents((prev) => [...prev, event]);
        }
      });
  }, []);

  const user =
    JSON.parse(Cookies.get("user")!) || ({ profileImage: "asdf" } as any);

  return (
    <div className=" pt-24 grid grid-col-3 grid-flow-col">
      {" "}
      <div></div>
      <div>
        <div className="bg-white shadow rounded-lg p-6">
          <div className="flex flex-col items-center">
            {user.profileImage === "" ? (
              <div className="w-12 h-12 rounded-full m-3">
                <Avatar />
              </div>
            ) : (
              <img
                className=" w-40 h-40 rounded-full m-3"
                alt={user.profileImage || "asdf"}
                src={`http://localhost:3000/userImages/${
                  user.profileImage || ""
                }`}
              ></img>
            )}
            <h1 className="text-xl font-bold">{user.username}</h1>
            <p className="text-gray-700">{user.email}</p>
            <div className="mt-6 flex flex-wrap gap-4 justify-center">
              <a
                href="#"
                className="bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded"
              >
                Uredi profil - to implement
              </a>
              <a
                href="#"
                className="bg-gray-300 hover:bg-gray-400 text-gray-700 py-2 px-4 rounded"
              >
                Spremeni geslo - to implement
              </a>
            </div>
          </div>

          <div className="flex flex-col p-20">
            <div className="text-3xl font-bold ps-5">Attending events:</div>
            <div className="grid grid-cols-1 p-2 gap-3 ">
              {attendingEvents.map((event, index) => (
                <div
                  className="bg-white shadow rounded-lg p-6 flex flex-row gap-4 justify-between"
                  key={index}
                >
                  <div className="flex flex-row gap-2 items-center">
                    <img
                      className="rounded-full h-12 w-12"
                      src={"http://localhost:3000" + event.eventImage}
                      alt="slika dogodka"
                    />
                    <h5 className="text-2xl font-bold tracking-tight text-gray-900">
                      {event.name}
                    </h5>
                  </div>
                  <button
                    className="bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded"
                    onClick={() => navigate("/details/" + event._id)}
                  >
                    Read More
                  </button>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
      <div></div>
    </div>
  );
};

export default Profile;
