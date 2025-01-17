import os
import random
import shutil


# crop_part1_path = 'crop_part1'

# # Paths to the map1 and map2 directories
# map1_path = os.path.join(crop_part1_path, 'female')
# map2_path = os.path.join(crop_part1_path, 'male')

# # Paths for the test directories
# map1_test_path = os.path.join(crop_part1_path, 'female_test')
# map2_test_path = os.path.join(crop_part1_path, 'male_test')

# # Create test directories if they don't exist
# os.makedirs(map1_test_path, exist_ok=True)
# os.makedirs(map2_test_path, exist_ok=True)

def move_files_to_test(source_path, test_path, test_percentage=0.02):
    """
    Moves a percentage of files from the source path to the test path.
    """
    # Get a list of all files in the source directory
    files = [f for f in os.listdir(source_path) if os.path.isfile(os.path.join(source_path, f))]
    
    # Calculate the number of files to move (5%)
    num_files_to_move = int(len(files) * test_percentage)
    
    # Randomly select the files to move
    files_to_move = random.sample(files, num_files_to_move)
    
    for file_name in files_to_move:
        src_file = os.path.join(source_path, file_name)
        dst_file = os.path.join(test_path, file_name)
        
        # Move the file to the test directory
        shutil.move(src_file, dst_file)
        print(f"Moved {file_name} to {test_path}")

# move_files_to_test(map1_path, map1_test_path)


# move_files_to_test(map2_path, map2_test_path)

def move_all_items_to_current_folder(current_folder):
    # Iterate through all items in the current folder
    for item in os.listdir(current_folder):
        item_path = os.path.join(current_folder, item)

        # If the item is a folder, move its contents
        if os.path.isdir(item_path):
            for sub_item in os.listdir(item_path):
                sub_item_path = os.path.join(item_path, sub_item)
                try:
                    # Move the file or folder to the current directory
                    shutil.move(sub_item_path, current_folder)
                except Exception as e:
                    print(f"Error moving {sub_item}: {e}")

            # Remove the now-empty folder
            try:
                os.rmdir(item_path)
            except Exception as e:
                print(f"Error removing folder {item_path}: {e}")

# # Usage
# current_directory = "crop_part1/"  # Replace with your folder path
# move_all_items_to_current_folder(current_directory)


def sort_and_balance_files(source_dir):
    # Define folder paths
    lower_folder = os.path.join(source_dir, 'lower_than_21')
    upper_folder = os.path.join(source_dir, 'above_21')

    # Create folders if they don't exist
    os.makedirs(lower_folder, exist_ok=True)
    os.makedirs(upper_folder, exist_ok=True)

    # Sort files into folders
    for filename in os.listdir(source_dir):
        if os.path.isfile(os.path.join(source_dir, filename)):
            try:
                # Extract the number before the underscore
                number = int(filename.split('_')[0])
                if number < 21:
                    shutil.move(os.path.join(source_dir, filename), lower_folder)
                else:
                    shutil.move(os.path.join(source_dir, filename), upper_folder)
            except (ValueError, IndexError):
                # Skip files that do not match the naming pattern
                continue

    # Count files in each folder
    lower_files = os.listdir(lower_folder)
    upper_files = os.listdir(upper_folder)

    lower_count = len(lower_files)
    upper_count = len(upper_files)

    print(f"Files in '{lower_folder}': {lower_count}")
    print(f"Files in '{upper_folder}': {upper_count}")

    # Balance the folders by returning excess files to the base directory
    if lower_count > upper_count:
        excess_files = random.sample(lower_files, lower_count - upper_count)
        for file in excess_files:
            shutil.move(os.path.join(lower_folder, file), source_dir)  # Move back to source directory
    elif upper_count > lower_count:
        excess_files = random.sample(upper_files, upper_count - lower_count)
        for file in excess_files:
            shutil.move(os.path.join(upper_folder, file), source_dir)  # Move back to source directory

    print(f"Folders balanced. Remaining files in each folder: {min(lower_count, upper_count)}")


source_directory = "UTKFace/"  # Replace with your source directory path
sort_and_balance_files(source_directory)


