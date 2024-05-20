import { Upload } from "@mui/icons-material";
import { IconButton } from "@mui/material";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";

const Register = () => {
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [userName, setUserName] = useState<string>("");
  const [avatar, setAvatar] = useState<File | null>(null);

  const navigate = useNavigate();

  function handleRegister(): void {
    if (email === "" || password === "" || userName === "") {
      return;
    }

    const formData = new FormData();
    formData.append("email", email);
    formData.append("password", password);
    formData.append("username", userName);
    if (avatar !== null) {
      formData.append("file", avatar);
    }

    fetch("http://localhost:3000/users/register", {
      method: "POST",
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Success:", data);
        Cookies.set("token", data.token, { expires: 7, secure: true });
        navigate("/");
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }

  function handleSignInRedirect(): void {
    navigate("/login");
  }

  return (
    <>
      <div className="min-h-screen bg-gray-100 py-6 flex flex-col justify-center sm:py-12">
        <div className="relative py-3 sm:max-w-xl sm:mx-auto">
          <div className="absolute inset-0 bg-gradient-to-r from-cyan-400 to-sky-500 shadow-lg transform -skew-y-6 sm:skew-y-0 sm:-rotate-6 sm:rounded-3xl"></div>
          <div className="relative px-4 py-10 bg-white shadow-lg sm:rounded-3xl sm:p-20">
            <div className="max-w-md mx-auto">
              <div>
                <h1 className="text-2xl font-semibold">Registracija</h1>
              </div>
              <div className="divide-y divide-gray-200">
                <div className="py-8 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7">
                  <div className="relative">
                    <input
                      id="email"
                      name="email"
                      type="text"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      className="peer placeholder-transparent h-10 w-full border-b-2 border-gray-300 text-gray-900 focus:outline-none focus:border-rose-600"
                      placeholder="Email address"
                    />
                    <label className="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">
                      E naslov
                    </label>
                  </div>
                  <div className="relative">
                    <input
                      id="username"
                      name="username"
                      type="text"
                      value={userName}
                      onChange={(e) => setUserName(e.target.value)}
                      className="peer placeholder-transparent h-10 w-full border-b-2 border-gray-300 text-gray-900 focus:outline-none focus:border-rose-600"
                      placeholder="Username"
                    />
                    <label className="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">
                      Uporabniško ime
                    </label>
                  </div>
                  <div className="relative">
                    <input
                      id="password"
                      name="password"
                      type="password"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      className="peer placeholder-transparent h-10 w-full border-b-2 border-gray-300 text-gray-900 focus:outline-none focus:border-rose-600"
                      placeholder="Password"
                    />
                    <label className="absolute left-0 -top-3.5 text-gray-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm">
                      Geslo
                    </label>
                  </div>
                  {avatar === null ? (
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
                            Upload an avatar image
                          </span>
                        </div>
                      </label>
                      <input
                        hidden
                        type="file"
                        name="button2"
                        id="button2"
                        onChange={(e) =>
                          setAvatar(e.target.files ? e.target.files[0] : null)
                        }
                      />
                    </div>
                  ) : (
                    <div className="flex flex-row items-center gap-3">
                      <div>
                        <img
                          className="w-12 h-12 rounded-full"
                          src={URL.createObjectURL(avatar)}
                          alt="avatar"
                        />
                      </div>
                      <div>{avatar.name.substring(0, 10)}</div>
                      <button
                        className="text-red-500"
                        onClick={() => {
                          console.log(avatar);
                          setAvatar(null);
                        }}
                      >
                        Remove
                      </button>
                    </div>
                  )}
                  <div className="relative">
                    <button
                      onClick={() => handleRegister()}
                      className="bg-cyan-500 text-white text-base px-4 rounded-md py-1 shadow-lg hover:bg-cyan-600"
                    >
                      Oddaj
                    </button>
                  </div>
                </div>
              </div>
            </div>

            <div className="w-full flex flex-col gap-2 justify-center">
              <div className="text-sm text-gray-400 text-opacity-50">
                Že imaš račun?
              </div>
              <button
                onClick={() => handleSignInRedirect()}
                className="flex items-center flex-col bg-white border border-gray-300 rounded-lg shadow-md px-6 py-2 text-sm font-medium text-gray-800 hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500"
              >
                <div>Prijava</div>
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Register;
