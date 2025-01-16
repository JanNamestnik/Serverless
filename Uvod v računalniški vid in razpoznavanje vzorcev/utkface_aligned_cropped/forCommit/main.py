from flask import Flask, request, jsonify
from flask_restx import Api, Resource, reqparse
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.image import img_to_array
import numpy as np
import io
from PIL import Image
import dlib
import cv2
import base64

# Initialize Flask app and Flask-RESTX API
app = Flask(__name__)
api = Api(
    app,
    title="Age Prediction API",
    version="1.0",
    description="API to predict age category ('under 21' or 'over 21') from an image",
)

# Load pre-trained models
gender_model = load_model("gender_classification_model.h5")
age_model = load_model("age_classification_model.h5")

IMG_SIZE = (224, 224)  # Replace with your model's expected input size

# Define request parser for file upload
upload_parser = reqparse.RequestParser()
upload_parser.add_argument(
    "file",
    type="werkzeug.datastructures.FileStorage",
    location="files",
    required=True,
    help="Upload an image file for prediction",
)


@api.route("/predict")
class Predict(Resource):
    @api.expect(upload_parser)
    def post(self):
        """
        Predict the age category ('under 21' or 'over 21') from an uploaded image
        and return cropped face images with predictions.
        """
        try:
            uploaded_file = request.files.get("file")  # Access the uploaded file

            if not uploaded_file:
                return {"error": "No file uploaded"}, 400

            # Load and preprocess the image
            image = Image.open(io.BytesIO(uploaded_file.read())).convert("RGB")
            image_array = np.array(image)

            # Extract faces using Dlib
            faces = extract_faces_dlib(image_array)
            if not faces:
                return {"error": "No faces detected in the image"}, 400

            results = []
            for face in faces:
                # Resize the face to match model input
                resized_face = cv2.resize(face, IMG_SIZE)
                face_array = img_to_array(resized_face) / 255.0
                face_array = np.expand_dims(face_array, axis=0)

                # Make predictions
                gender_prediction = gender_model.predict(face_array)
                age_prediction = age_model.predict(face_array)

                predicted_gender = "Male" if gender_prediction[0][0] > 0.5 else "Female"
                predicted_age = "Under 21" if age_prediction[0][0] > 0.5 else "Over 21"

                # Encode the face as a Base64 string
                _, buffer = cv2.imencode(".jpg", face)
                face_base64 = base64.b64encode(buffer).decode("utf-8")

                results.append(
                    {
                        "age": predicted_age,
                        "gender": predicted_gender,
                        "face_image": face_base64,
                    }
                )

            return jsonify({"results": results})

        except Exception as e:
            return {"error": f"Error processing image: {str(e)}"}, 500


def extract_faces_dlib(image):
    detector = dlib.get_frontal_face_detector()
    gray_image = cv2.cvtColor(image, cv2.COLOR_RGB2GRAY)
    faces = detector(gray_image)

    face_images = []
    for face in faces:
        x, y, w, h = face.left(), face.top(), face.width(), face.height()
        cropped_face = image[y : y + h, x : x + w]
        face_images.append(cropped_face)
    return face_images


# Run the Flask app
if __name__ == "__main__":
    app.run(debug=True)
