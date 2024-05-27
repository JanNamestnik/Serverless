import { useEffect, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Cookies from "js-cookie";
import RatingEdit from "./RatingEdit";
import RatingShow from "./RatingShow";

const EventDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [event, setEvent] = useState<MyEvent>({} as MyEvent);
  const [rating, setRating] = useState<number>(5);
  const [content, setContent] = useState<string>("");
  const [reviews, setReviews] = useState<Review[]>([] as Review[]);
  const user = JSON.parse(Cookies.get("user")!);
  const fetched = useRef(true);
  const token = Cookies.get("token");
  useEffect(() => {
    if (fetched.current) {
      fetched.current = false;
    } else {
      return;
    }
    fetch("http://localhost:3000/reviews/" + id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("fethched reviews", data);
        for (const review of data) {
          setReviews((prev) => [...prev, review]);
        }
      });
    console.log(token);
    fetch("http://localhost:3000/events/" + id, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        setEvent(data as MyEvent);
        console.log(event);
      });
  }, []);
  function handlePostComment(): void {
    //you need to add the rating and content to the body

    fetch("http://localhost:3000/reviews", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
      body: JSON.stringify({
        userId: user.id,
        eventId: id,
        rating: rating,
        content: content,
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        setReviews((prev) => [...prev, data]);
        setContent("");
        setRating(5);
      });
  }
  console.log(event);
  function handleAttendEvent(): void {
    fetch("http://localhost:3000/events/showEvent/attend/" + event._id, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        setEvent(data);
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
        setEvent(data);
      });
  }

  function handleHidding(): void {
    fetch("http://localhost:3000/events/showEvent/hide/" + event._id, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        navigate("/list");
      });
  }

  return (
    <div className="pt-20 ">
      <div className="bg-gray-100 flex flex-col items-center justify-center min-h-screen gap-6">
        <div className="bg-white rounded-lg shadow-lg p-6 max-w-2xl w-full">
          <div className="flex flex-col md:flex-row md:items-center">
            <img
              src={"http://localhost:3000" + event.eventImage}
              alt="Event Image"
              className="w-full md:w-1/3 rounded-lg md:mr-6 mb-4 md:mb-0"
            />
            <div className="flex-1">
              <h1 className="text-3xl font-bold mb-2">{event.name}</h1>
              <p className="text-gray-700 mb-4">
                Venue: <span className="font-semibold">{event.venue}</span>
              </p>
              <p className="text-gray-700 mb-4">
                Address: <span className="font-semibold">{event.address}</span>
              </p>
              <p className="text-gray-700 mb-4">
                Date:{" "}
                <span className="font-semibold">
                  {event.date_start
                    ? new Date(event.date_start.toString()).toDateString()
                    : ""}{" "}
                  -{" "}
                  {event.date_end
                    ? new Date(event.date_end.toString()).toDateString()
                    : ""}
                </span>
              </p>
              <p className="text-gray-700 mb-4">
                Time:{" "}
                <span className="font-semibold">
                  {event.date_start
                    ? new Date(event.date_start.toString()).toLocaleTimeString()
                    : " "}
                  -
                  {event.date_end
                    ? new Date(event.date_end.toString()).toLocaleTimeString()
                    : " "}
                </span>
              </p>
              <p className="text-gray-700 mb-4">
                Description:{" "}
                <span className="font-semibold">{event.description}</span>
              </p>
              <p className="text-gray-700 mb-4">
                Price: <span className="font-semibold">{event.price} €</span>
              </p>
              <p className="text-gray-700 mb-4">
                Contact: <span className="font-semibold">{event.contact}</span>
              </p>

              <div className="flex flex-row gap-4 ">
                {event.attendees?.some((attendy) => attendy._id == user._id) ? (
                  <button
                    className="px-2.5 py-1.5 rounded-md text-white text-sm bg-indigo-500"
                    onClick={() => handleLeaving()}
                  >
                    Leave
                  </button>
                ) : (
                  <button
                    className="px-2.5 py-1.5 rounded-md text-white text-sm bg-indigo-500"
                    onClick={() => handleAttendEvent()}
                  >
                    Attend
                  </button>
                )}

                <button
                  className="px-2.5 py-1.5 rounded-md text-white text-sm bg-indigo-500"
                  onClick={() => handleHidding()}
                >
                  Hide
                </button>
              </div>
            </div>
          </div>
        </div>
        <div className="bg-white rounded-lg shadow-lg p-6 max-w-2xl w-full">
          <div className="flex flex-col md:flex-row md:items-center">
            <div className="flex-1">
              <h1 className="text-3xl font-bold mb-2">Attendees</h1>
              <div className="grid grid-cols-3 p-2">
                {event?.attendees?.map((attendee: any, index) => (
                  <div className="flex items-center mr-4 mb-4" key={index}>
                    <img
                      src={
                        "http://localhost:3000/userImages/" +
                        attendee?.profileImage
                      }
                      alt="profile"
                      className="w-8 h-8 rounded-full"
                    />
                    <p className="text-gray-700 ml-2">{attendee?.username}</p>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>

        <div className="bg-white rounded-lg shadow-lg p-6 max-w-2xl w-full">
          <div className="flex flex-col md:flex-row md:items-center">
            <div className="flex-1">
              <h1 className="text-3xl font-bold mb-2">Reviews</h1>
              <div>
                <RatingEdit rating={rating} setRating={setRating} />
                <div className="w-full px-3 my-2">
                  <textarea
                    className="bg-gray-100 rounded border border-gray-400 leading-normal resize-none w-full h-20 py-2 px-3 font-medium placeholder-gray-700 focus:outline-none focus:bg-white"
                    name="body"
                    placeholder="Type Your Comment"
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                  ></textarea>
                </div>

                <div className="w-full flex justify-end px-3">
                  <button
                    className="px-2.5 py-1.5 rounded-md text-white text-sm bg-indigo-500"
                    onClick={() => handlePostComment()}
                  >
                    Post Comment
                  </button>
                </div>
              </div>
              {reviews.map((review, index) => (
                <div
                  key={index}
                  className="bg-gray-100 rounded-lg shadow-lg p-6 mt-4"
                >
                  <div className="flex items-center justify-between">
                    <div className="flex items-center">
                      <img
                        src={
                          "http://localhost:3000/userImages/" +
                          review?.userId?.profileImage
                        }
                        alt="profile"
                        className="w-8 h-8 rounded-full"
                      />
                      <p className="text-gray-700 ml-2">
                        {review?.userId?.username}
                      </p>
                    </div>
                    <div className="flex items-center">
                      <RatingShow rating={review.rating} />
                    </div>
                  </div>
                  <p className="text-gray-700 mt-4">{review.content}</p>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EventDetails;
