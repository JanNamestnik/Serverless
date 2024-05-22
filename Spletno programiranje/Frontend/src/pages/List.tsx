import React, { useEffect, useRef, useState } from "react";
import EventCard from "../components/EventCard";
import { Swiper, SwiperSlide } from "swiper/react";

import Cookies from "js-cookie";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/free-mode";

import { Pagination, Mousewheel, EffectCoverflow } from "swiper/modules";

const List = () => {
  const token = Cookies.get("token");
  const [events, setEvents] = useState<MyEvent[]>([]);
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
    }
  }, [token]);
  return (
    <div className="flex flex-col h-screen w-screen pt-20 bg-primaryBackground">
      <div className=" grid grid-cols-3 grid-row-2 items-center">
        <div className=" items-center text-center text-2xl font-extrabold p-5">
          Najbolj populrano
        </div>
        <div className=" items-center text-center text-2xl font-extrabold p-5">
          Zanimivo Tvojim prijateljem
        </div>
        <div className=" items-center text-center text-2xl font-extrabold p-5">
          Glede na tvoje prejšnnje dogodke
        </div>
        <div className="items-center">
          {" "}
          {events.length > 2 ? <EventCard event={events[1]} /> : <></>}
        </div>
        <div className="items-center">
          {events.length > 2 ? <EventCard event={events[1]} /> : <></>}
        </div>
        <div className="items-center">
          {events.length > 2 ? <EventCard event={events[1]} /> : <></>}
        </div>
      </div>

      <div className=" text-5xl font-extrabold w-screen text-center p-10">
        Vsi Dogodki
      </div>
      <div className="flex flex-row gap-3 items-center justify-center bg-inherit">
        <Swiper
          breakpoints={{
            320: {
              slidesPerView: 3,
              spaceBetween: 15,
            },
            640: {
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
          <SwiperSlide key="0"></SwiperSlide>
          {events.map((event) => {
            return (
              <SwiperSlide key={event._id}>
                <EventCard event={event} />
              </SwiperSlide>
            );
          })}
          <SwiperSlide key="6"></SwiperSlide>
        </Swiper>
      </div>
    </div>
  );
};

export default List;
