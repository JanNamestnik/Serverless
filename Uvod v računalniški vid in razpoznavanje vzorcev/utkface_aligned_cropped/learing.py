from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.applications import EfficientNetB0
from tensorflow.keras.models import Model
from tensorflow.keras.layers import Dense, GlobalAveragePooling2D

# Konfiguracija za slike
img_size = (224, 224)
batch_size = 32

# Data augmentation for training
train_datagen = ImageDataGenerator(
    rescale=1./255,  # Normalizacija
    rotation_range=20,  # Naključna rotacija
    width_shift_range=0.2,  # Premik v širini
    height_shift_range=0.2,  # Premik v višini
    shear_range=0.2,  # Naključno škarjenje
    zoom_range=0.2,  # Naključna povečava
    horizontal_flip=True,  # Naključni horizontalni flip
    fill_mode='nearest',  # Kako zapolniti prazne prostore po transformaciji
    validation_split=0.2  # 80% za učenje, 20% za validacijo
)

# Validation data without augmentation
val_datagen = ImageDataGenerator(
    rescale=1./255,  # Normalizacija
    validation_split=0.2
)

train_data = train_datagen.flow_from_directory(
    'UTKFace/learn',  # Pot do mape s slikami
    target_size=img_size,
    batch_size=batch_size,
    class_mode='binary',  # Binary classification
    subset='training'
)

val_data = val_datagen.flow_from_directory(
    'UTKFace/learn',
    target_size=img_size,
    batch_size=batch_size,
    class_mode='binary',
    subset='validation'
)

# Using EfficientNetB1 as base model
base_model = EfficientNetB0(weights='imagenet', include_top=False, input_shape=(224, 224, 3))

# Dodajanje prilagojenih slojev
x = base_model.output
x = GlobalAveragePooling2D()(x)
predictions = Dense(1, activation='sigmoid')(x)  # Binary output

model = Model(inputs=base_model.input, outputs=predictions)

# Zamrzni začetne sloje modela (nevronske mreže ne bo spreminjala teh uteži)
for layer in base_model.layers:
    layer.trainable = False

# Kompilacija modela
model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

# Treniranje modela
model.fit(
    train_data,
    validation_data=val_data,
    epochs=10,  # Število epochov
    steps_per_epoch=train_data.samples // batch_size,
    validation_steps=val_data.samples // batch_size
)

# Shranjevanje modela
model.save('age_classification_model_effnetb0.h5')