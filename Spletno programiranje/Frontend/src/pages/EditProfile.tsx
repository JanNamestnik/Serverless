import { IconButton } from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { Upload } from "@mui/icons-material";

const EditProfile = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [profileImage, setProfileImage] = useState<File | null>(null);
    const token = Cookies.get("token");
    const inUser = JSON.parse(Cookies.get("user")!);
    useEffect(() => {
        setUsername(inUser.username);
        setEmail(inUser.email);
        fetch("http://localhost:3000/userImages/" + inUser.profileImage, {
            method: "GET",
            headers: {
                Authorization: "Bearer " + token,
            },
        })
            .then((response) => response.blob())
            .then((data) => {
                setProfileImage(new File([data], inUser.profileImage));
            });
    }, []);

    function handleSavingChanges(): void {
        console.log(profileImage);
        const formData = new FormData();
        formData.append("username", username);
        formData.append("email", email);
        if (profileImage) {
            formData.append("profileImage", profileImage);
        }
        fetch("http://localhost:3000/users/profile/update", {
            method: "POST",
            headers: {
                Authorization: "Bearer " + token,
            },
            body: formData,
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(JSON.stringify(data));
                Cookies.set("user", JSON.stringify(data));
                navigate("/profile");
            });
    }

    return (
        <div className=" pt-24 grid grid-col-3 grid-flow-col">
            {" "}
            <div></div>
            <div>
                <div className="bg-white shadow rounded-lg p-6 ">
                    <div className="flex flex-col items-center w-full">
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
                        <div className="grid grid-cols-2 gap-6 pt-6">
                            <div>Username</div>
                            <input
                                value={username}
                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                onChange={(e) => setUsername(e.target.value)}
                            />

                            <div className="text-gray-700">Email</div>
                            <input
                                value={email}
                                className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </div>

                        <div className="mt-6 flex flex-wrap gap-4 justify-center">
                            <button
                                onClick={() => handleSavingChanges()}
                                className="bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded"
                            >
                                Shrani spremembe
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div></div>
        </div>
    );
};

export default EditProfile;