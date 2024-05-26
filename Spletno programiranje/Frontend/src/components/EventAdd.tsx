import { useState } from "react";
import EventForm from "./EventForm";
import Cookies from "js-cookie";
import { useNavigate } from "react-router-dom";

const EventAdd = () => {
  const navigate = useNavigate();
  const token = Cookies.get("token");
  const [profileImage, setProfileImage] = useState<File | null>(null);

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
    category: { type: "" },
    eventImage: "",
    price: 0,
    attendees: [],
    owner: "",
    location: { type: "", coordinates: [0, 0] },
  });

  function handleSubmit(): void {
    console.log(event);
  }

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

export default EventAdd;
