import { Celebration } from "@mui/icons-material";
import { Avatar, Icon } from "@mui/material";

import Cookies from "js-cookie";
import { useNavigate } from "react-router-dom";

const Navbar = () => {
  const navigate = useNavigate();
  const user = Cookies.get("user") || " a ";

  const avatarImage = JSON.parse(user);
  function handleLogout(): void {
    Cookies.remove("user");
    Cookies.remove("token");

    window.location.reload();
  }

  function handleProfileRedirect(): void {
    navigate("/profile");
  }

  return (
    <div>
      <nav className="bg-white dark:bg-gray-900 fixed w-full z-20 px-2 top-0 start-0 border-b border-gray-200 dark:border-gray-600">
        <div className="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto ">
          <span className="self-center text-2xl font-semibold whitespace-nowrap dark:text-white flex flex-row gap-3 ">
            <div>
              <Icon>
                <Celebration />
              </Icon>
            </div>
            <div>Festi</div>
          </span>

          <div className="flex md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse items-center">
            <div>
              <button onClick={() => handleProfileRedirect()}>
                {avatarImage.profileImage === "" ? (
                  <div className="w-12 h-12 rounded-full m-3">
                    <Avatar />
                  </div>
                ) : (
                  <img
                    className="w-12 h-12 rounded-full m-3"
                    alt={avatarImage.profileImage || "asdf"}
                    src={`http://localhost:3000/userImages/${
                      avatarImage.profileImage || ""
                    }`}
                  ></img>
                )}
              </button>
            </div>
            <button
              onClick={() => handleLogout()}
              type="button"
              className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 h- py-2 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            >
              Logout
            </button>
            <button
              data-collapse-toggle="navbar-sticky"
              type="button"
              className="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600"
              aria-controls="navbar-sticky"
              aria-expanded="false"
            >
              <span className="sr-only">Open main menu</span>
              <svg
                className="w-5 h-5"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 17 14"
              >
                <path
                  stroke="currentColor"
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth="2"
                  d="M1 1h15M1 7h15M1 13h15"
                />
              </svg>
            </button>
          </div>
          <div
            className="items-center justify-between hidden w-full md:flex md:w-auto md:order-1"
            id="navbar-sticky"
          >
            <ul className="flex flex-col p-4 md:p-0 mt-4 font-medium border border-gray-100 rounded-lg bg-gray-50 md:space-x-8 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700">
              <li>
                <button
                  onClick={() => navigate("/")}
                  className="block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 md:dark:hover:text-blue-500 dark:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
                  aria-current="page"
                >
                  Seznam dogodkov
                </button>
              </li>

              <li>
                <button
                  onClick={() => navigate("/my-events")}
                  className="block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 md:dark:hover:text-blue-500 dark:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
                >
                  Moji Dogodki
                </button>
              </li>
              <li>
                <button
                  onClick={() => navigate("/map")}
                  className="block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0 md:dark:hover:text-blue-500 dark:text-white dark:hover:bg-gray-700 dark:hover:text-white md:dark:hover:bg-transparent dark:border-gray-700"
                >
                  Zemljevid
                </button>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </div>
  );
};

export default Navbar;
