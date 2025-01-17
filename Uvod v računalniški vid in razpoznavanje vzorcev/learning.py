from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.applications import VGG16
from tensorflow.keras.models import Model
from tensorflow.keras.layers import Dense, GlobalAveragePooling2D
from tensorflow.keras.callbacks import ModelCheckpoint, CSVLogger, EarlyStopping, ReduceLROnPlateau
import os

# Konfiguracija za slike
img_size = (224, 224)
batch_size = 32

# Ustvari mapo za shranjevanje logov in modelov
if not os.path.exists('logs'):
    os.makedirs('logs')

# Data augmentation for training
train_datagen = ImageDataGenerator(
    rescale=1./255,
    rotation_range=15,
    width_shift_range=0.2,
    height_shift_range=0.2,
    shear_range=0.2,
    zoom_range=0.2,
    horizontal_flip=True,
    fill_mode='nearest',
    validation_split=0.3
)

# Validation data without augmentation
val_datagen = ImageDataGenerator(
    rescale=1./255,
    validation_split=0.2
)

train_data = train_datagen.flow_from_directory(
    'female_male/learn',
    target_size=img_size,
    batch_size=batch_size,
    class_mode='binary',
    subset='training'
)

val_data = val_datagen.flow_from_directory(
    'female_male/learn',
    target_size=img_size,
    batch_size=batch_size,
    class_mode='binary',
    subset='validation'
)

# Using VGG16 as base model
base_model = VGG16(weights='imagenet', include_top=False, input_shape=(224, 224, 3))

# Dodajanje prilagojenih slojev
x = base_model.output
x = GlobalAveragePooling2D()(x)
predictions = Dense(1, activation='sigmoid')(x)  # Binary output

model = Model(inputs=base_model.input, outputs=predictions)

# Zamrzni začetne sloje modela
for layer in base_model.layers:
    layer.trainable = False

# Kompilacija modela
model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

# **Dodajanje povratnih klicev (callbacks)**

# 1. Shranjevanje najboljšega modela
checkpoint = ModelCheckpoint(
    'logs/best_model.h5', monitor='val_accuracy', save_best_only=True, mode='max', verbose=1
)

# 2. Logiranje izgub in natančnosti v CSV datoteko
csv_logger = CSVLogger('logs/training_log.csv', append=True)

# 3. Zgodnja ustavitev, če se validacijska natančnost ne izboljšuje
early_stopping = EarlyStopping(
    monitor='val_loss', patience=5, restore_best_weights=True, verbose=1
)

# 4. Dinamično prilagajanje stopnje učenja
reduce_lr = ReduceLROnPlateau(
    monitor='val_loss', factor=0.2, patience=3, min_lr=1e-6, verbose=1
)

callbacks = [checkpoint, csv_logger, early_stopping, reduce_lr]

# Treniranje modela
history = model.fit(
    train_data,
    validation_data=val_data,
    epochs=10,
    steps_per_epoch=train_data.samples // batch_size,
    validation_steps=val_data.samples // batch_size,
    callbacks=callbacks
)

# Shranjevanje končnega modela
model.save('logs/final_model.h5')

# **Vizualizacija rezultatov treninga**
import matplotlib.pyplot as plt

# Prikaz natančnosti
plt.figure(figsize=(12, 4))
plt.plot(history.history['accuracy'], label='Training Accuracy')
plt.plot(history.history['val_accuracy'], label='Validation Accuracy')
plt.title('Model Accuracy')
plt.xlabel('Epochs')
plt.ylabel('Accuracy')
plt.legend()
plt.grid()
plt.show()

# Prikaz izgube
plt.figure(figsize=(12, 4))
plt.plot(history.history['loss'], label='Training Loss')
plt.plot(history.history['val_loss'], label='Validation Loss')
plt.title('Model Loss')
plt.xlabel('Epochs')
plt.ylabel('Loss')
plt.legend()
plt.grid()
plt.show()
