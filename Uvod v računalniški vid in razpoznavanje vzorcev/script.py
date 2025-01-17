import os
import random
import shutil

def sort_and_balance_files(source_dir):
    # Define folder paths
    female_folder = os.path.join(source_dir, 'female')
    male_folder = os.path.join(source_dir, 'male')

    # Create folders if they don't exist
    os.makedirs(female_folder, exist_ok=True)
    os.makedirs(male_folder, exist_ok=True)

    # Sort files into folders
    for filename in os.listdir(source_dir):
        if os.path.isfile(os.path.join(source_dir, filename)):
            try:
                # Extract the number before the underscore
                number = int(filename.split('_')[1])
                if number  == 1:
                    shutil.move(os.path.join(source_dir, filename), female_folder)
                else:
                    shutil.move(os.path.join(source_dir, filename), male_folder)
            except (ValueError, IndexError):
                # Skip files that do not match the naming pattern
                continue

    # Count files in each folder
    lower_files = os.listdir(female_folder)
    upper_files = os.listdir(male_folder)

    lower_count = len(lower_files)
    upper_count = len(upper_files)

    print(f"Files in '{female_folder}': {lower_count}")
    print(f"Files in '{male_folder}': {upper_count}")

    # Balance the folders
    if lower_count > upper_count:
        excess_files = random.sample(lower_files, lower_count - upper_count)
        for file in excess_files:
            os.remove(os.path.join(female_folder, file))
    elif upper_count > lower_count:
        excess_files = random.sample(upper_files, upper_count - lower_count)
        for file in excess_files:
            os.remove(os.path.join(male_folder, file))

    print(f"Folders balanced. Remaining files in each folder: {min(lower_count, upper_count)}")

# Usage
source_directory = "crop_part1/"  # Replace with your source directory path
sort_and_balance_files(source_directory)
