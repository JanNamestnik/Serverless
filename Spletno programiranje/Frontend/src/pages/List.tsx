import { useEffect, useRef, useState } from "react";
import EventCard from "../components/EventCard";
import { Swiper, SwiperSlide } from "swiper/react";

import Cookies from "js-cookie";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/free-mode";

import { Pagination, Mousewheel, EffectCoverflow } from "swiper/modules";

const emptyEvent: MyEvent = {
  _id: "",
  name: "",
  venue: "",
  address: "",
  startTime: "",
  date_start: new Date(),
  date_end: new Date(),
  description: "",
  contact: "",
  category: {
    type: "",
  },
  eventImage: "",
  price: 0,
  attendees: [],
  location: {
    type: "",
    coordinates: [],
  },
};

const List = () => {
  const [user, setUser] = useState<User>(
    JSON.parse(
      Cookies.get("user") ||
        JSON.stringify({
          favorites: [],
          profileImage: "",
          email: "",
          password: "",
          username: "",
        })
    )
  );
  const token = Cookies.get("token");
  const [events, setEvents] = useState<MyEvent[]>([]);
  const [recommendedEvent, setRecommendedEvent] = useState<MyEvent>(
    {} as MyEvent
  );
  const [popularEvent, setPopularEvent] = useState<MyEvent>({} as MyEvent);
  const [interestingEvent, setInterestingEvent] = useState<MyEvent>(
    {} as MyEvent
  );
  const fetched = useRef(false);
  useEffect(() => {
    if (!token) {
      return;
    }

    if (!fetched.current) {
      fetched.current = true;
      fetch("http://localhost:3000/events", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
      })
        .then((response) => response.json())
        .then((data) => {
          console.log(data, events);

          for (const event of data) {
            setEvents((prev) => [...prev, event]);
          }
          console.log("events", events);
        });

      fetch("http://localhost:3000/events/recommended", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
      })
        .then((response) => response.json())
        .then((data) => {
          setRecommendedEvent(data as MyEvent);
        });

      fetch("http://localhost:3000/events/popular", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
      })
        .then((response) => response.json())
        .then((data) => {
          setPopularEvent(data as MyEvent);
        });

      fetch("http://localhost:3000/events/interesting", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
      })
        .then((response) => response.json())
        .then((data) => {
          setInterestingEvent(data as MyEvent);
        });
    }
  }, [token]);
  return (
    <div className="flex flex-col h-screen w-screen pt-20 bg-primaryBackground">
      <div className=" grid lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-1 items-center ">
        <div>
          <div className=" items-center text-center text-2xl font-extrabold p-5">
            Najbolj populrano
          </div>
          <div className="  flex flex-col items-center">
            {popularEvent != ({} as MyEvent) ? (
              <EventCard event={popularEvent} user={user} setUser={setUser} />
            ) : (
              <EventCard event={emptyEvent} user={user} setUser={setUser} />
            )}
          </div>
        </div>
        <div>
          <div className=" items-center text-center text-2xl font-extrabold p-5">
            Zanimivo Tvojim prijateljem
          </div>{" "}
          <div className="items-center flex flex-col">
            {interestingEvent != ({} as MyEvent) ? (
              <EventCard
                event={interestingEvent}
                user={user}
                setUser={setUser}
              />
            ) : (
              <EventCard event={emptyEvent} user={user} setUser={setUser} />
            )}
          </div>
        </div>
        <div>
          <div className=" items-center text-center text-2xl font-extrabold p-5">
            Glede na tvoje prejšnnje dogodke
          </div>

          <div className="items-center flex flex-col">
            {recommendedEvent != ({} as MyEvent) ? (
              <EventCard
                event={recommendedEvent}
                user={user}
                setUser={setUser}
              />
            ) : (
              <EventCard event={emptyEvent} user={user} setUser={setUser} />
            )}
          </div>
        </div>
      </div>

      <div className=" text-5xl font-extrabold w-screen text-center p-10">
        Vsi Dogodki
      </div>
      <div className="flex flex-row gap-3 items-center justify-center bg-inherit">
        <Swiper
          breakpoints={{
            320: {
              slidesPerView: 1,
              spaceBetween: 15,
            },
            700: {
              slidesPerView: 3,
              spaceBetween: 13,
            },
          }}
          modules={[Pagination, Mousewheel, EffectCoverflow]}
          pagination={{
            clickable: true,
          }}
          mousewheel={true}
          effect="coverflow"
          coverflowEffect={{
            rotate: 0,
            stretch: 0,
            depth: 500,
            modifier: 2,
            slideShadows: true,
          }}
          className="max-w-[90%] lg:max-w-[80%] bg-primaryBackground"
        >
          {window.innerWidth > 700 ? <SwiperSlide key="0"></SwiperSlide> : null}

          {events.map((event) => {
            return (
              <SwiperSlide key={event._id}>
                <EventCard event={event} user={user} setUser={setUser} />
              </SwiperSlide>
            );
          })}
          {window.innerWidth > 700 ? <SwiperSlide key="6"></SwiperSlide> : null}
        </Swiper>
      </div>
    </div>
  );
};

export default List;
