import os
import numpy as np
from tensorflow.keras.preprocessing.image import load_img, img_to_array
from tensorflow.keras.models import load_model

# Load the model
model = load_model('logs/best_model.h5')

# Define the test folder path
test_folder_path = 'female_male/test'  # Replace with the path to your test folder

# Define the categories (subfolder names)
categories = ['female_test', 'male_test']  # Adjust the names as per your folder structure

# Initialize counters
total_images = 0
correct_predictions = 0

for category in categories:
    category_path = os.path.join(test_folder_path, category)
    if not os.path.exists(category_path):
        print(f"Category folder '{category}' does not exist in the test folder.")
        continue

    files = [f for f in os.listdir(category_path) if os.path.isfile(os.path.join(category_path, f))]
    for file_name in files:
        file_path = os.path.join(category_path, file_name)

        try:
            # Load and preprocess the image
            img = load_img(file_path, target_size=(224, 224))
            img_array = img_to_array(img) / 255.0
            img_array = np.expand_dims(img_array, axis=0)

            # Make a prediction
            prediction = model.predict(img_array)
            predicted_label = 'male_test' if prediction[0][0] > 0.5 else 'female_test'

            # Compare with the actual category
            is_correct = predicted_label == category
            correct_predictions += int(is_correct)
            total_images += 1

            # Print the result
            #print(f"File: {file_name} -> Prediction: {predicted_label} | Actual: {category} | Correct: {is_correct}")

        except Exception as e:
            print(f"Error processing file {file_name}: {e}")

# Print the overall accuracy
if total_images > 0:
    accuracy = (correct_predictions / total_images) * 100
    print(f"\nTotal Images Tested: {total_images}")
    print(f"Correct Predictions: {correct_predictions}")
    print(f"Accuracy: {accuracy:.2f}%")
else:
    print("No images were tested.")
