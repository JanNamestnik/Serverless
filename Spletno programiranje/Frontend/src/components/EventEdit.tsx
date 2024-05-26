import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Cookies from "js-cookie";
import EventForm from "./EventForm";

const EventEdit = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const token = Cookies.get("token");
  const [profileImage, setProfileImage] = useState<File | null>(null);
  const user = JSON.parse(Cookies.get("user") || "{}");
  const [event, setEvent] = useState<MyEvent>({
    _id: "",
    name: "",
    venue: "",
    address: "",
    startTime: "",
    date_start: new Date(),
    date_end: new Date(),
    description: "",
    contact: "",
    category: "",
    eventImage: "",
    price: 0,
    attendees: [],
    owner: "",
    location: { type: "", coordinates: [0, 0] },
  });

  useEffect(() => {
    fetch("http://localhost:3000/events/" + id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        setEvent(data as MyEvent);
      });
  }, []);

  const handleSubmit = async () => {
    const formDataToSend = new FormData();
    formDataToSend.append("name", event.name);
    formDataToSend.append("venue", event.venue);
    formDataToSend.append("address", event.address);
    formDataToSend.append("startTime", event.startTime);
    formDataToSend.append("date_start", event.date_start.toString());
    formDataToSend.append("date_end", event.date_end.toString());
    formDataToSend.append("description", event.description);
    formDataToSend.append("contact", event.contact);
    formDataToSend.append("category", event.category);
    formDataToSend.append("price", event.price.toString());
    formDataToSend.append(
      "lagtitute",
      event.location.coordinates[0].toString()
    );
    formDataToSend.append(
      "longtitute",
      event.location.coordinates[1].toString()
    );
    formDataToSend.append("owner", user._id);
    if (profileImage) {
      formDataToSend.append("image", profileImage);
    }

    try {
      const response = await fetch("http://localhost:3000/events/", {
        method: "POST",
        headers: {
          Authorization: "Bearer " + token,
        },
        body: formDataToSend,
      });

      const data = await response.json();
      if (response.ok) {
        console.log(data);
        navigate("/my-events");
        // handle success (e.g., display a success message, redirect, etc.)
      } else {
        console.error(data);
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <div className="pt-20 h-full">
      <div className="flex flex-row">
        <div className="w-3/12"></div>
        <div className="p-4 m-4 bg-white  h-full w-full">
          <EventForm
            event={event}
            setEvent={setEvent}
            submit={() => handleSubmit()}
            profileImage={profileImage}
            setProfileImage={setProfileImage}
          />
        </div>
        <div className="w-3/12"></div>
      </div>
    </div>
  );
};

export default EventEdit;
