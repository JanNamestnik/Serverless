from ultralytics import YOLO
from PIL import Image
import numpy as np

def detect_humans_yolo(image_path):
    # Load the pre-trained YOLOv8 model
    model = YOLO("yolov8n.pt")  # Use a pre-trained YOLO model

    # Run inference on the image
    results = model(image_path)

    # Filter for human detections (class 0 is 'person' in COCO dataset)
    humans = [box for box in results[0].boxes.data if int(box[5]) == 0]  # COCO class 0 = 'person'

    # Save the annotated image
    output_path = "output_yolo.jpg"
    annotated_image = results[0].plot()  # Annotate the image (returns a numpy array)
    annotated_image = Image.fromarray(annotated_image)  # Convert numpy array to PIL Image
    annotated_image.save(output_path)  # Save the annotated image

    return output_path, humans

# Example usage
output_image, humans = detect_humans_yolo("groupFoto.jpg")
print(f"Detected {len(humans)} humans. Output saved as {output_image}.")
