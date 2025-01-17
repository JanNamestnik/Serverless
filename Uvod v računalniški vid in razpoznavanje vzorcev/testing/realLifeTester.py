import dlib
import cv2
import numpy as np
from tensorflow.keras.preprocessing.image import load_img, img_to_array
from tensorflow.keras.models import load_model

gender_model = load_model('gender_classification_model.h5')
age_model = load_model('age_classification_model.h5')
def extract_faces_dlib(image_path):
    detector = dlib.get_frontal_face_detector()
    
    # Load the image
    image = cv2.imread(image_path)
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    
    # Detect faces
    faces = detector(gray)
    face_images = []
    for face in faces:
        x, y, w, h = face.left(), face.top(), face.width(), face.height()
        face_images.append(image[y:y+h, x:x+w])
    
    return face_images

# Example usage
faces = extract_faces_dlib("groupFoto.jpg") # Replace with the path to your image   
for i, face in enumerate(faces):
    cv2.imwrite(f"face_{i}.jpg", face)


for i in range(0, len(faces)):
    # Load and preprocess the image
    img = load_img(f"face_{i}.jpg", target_size=(224, 224))
    img_array = img_to_array(img) / 255.0
    img_array = np.expand_dims(img_array, axis=0)

    # Make a prediction
    prediction = gender_model.predict(img_array)
    predicted_label_gender = 'Male ' if prediction[0][0] > 0.5 else 'Female'
   

    prediction = age_model.predict(img_array)
    predicted_label_age = 'Under 21' if prediction[0][0] > 0.5 else 'Above 21'
    print(f"Face {i}")
    print(f"Age Prediction: {predicted_label_age}")
    print(f"Gendre Prediction {predicted_label_gender}")

