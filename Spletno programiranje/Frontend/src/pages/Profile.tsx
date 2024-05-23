import React from "react";
import Cookies from "js-cookie";
import { Avatar } from "@mui/material";

const Profile = () => {
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

          <div className="flex flex-col">
            <span className="text-gray-700 uppercase font-bold tracking-wider mb-2">
              Skills
            </span>
            <ul>
              <li className="mb-2">JavaScript</li>
              <li className="mb-2">React</li>
              <li className="mb-2">Node.js</li>
              <li className="mb-2">HTML/CSS</li>
              <li className="mb-2">Tailwind Css</li>
            </ul>
          </div>
        </div>
      </div>{" "}
      <div></div>
    </div>
  );
};

export default Profile;
