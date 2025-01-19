package si.um.feri.serverless.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class GameManager {
    private static final String RESULTS_FILE = "discounts.json";
    private static final String PREFERENCES_NAME = "game_settings";
    private static final String SOUND_EFFECTS_ENABLED_KEY = "sound_effects_enabled";
    private static final String RULES_SHOWN_KEY = "rules_shown";
    private static final String MUSIC_ENABLED_KEY = "music_enabled";
    private static final String GRID_SIZE_KEY = "grid_size";
    private static final String DISCOUNT_KEY = "discount";
    private static final int DEFAULT_GRID_SIZE = 25;
    private static final int DEFAULT_DISCOUNT = 5;
    private static final GameManager INSTANCE = new GameManager();
    private final Preferences preferences;
    private final Json json;
    private List<Result> collectedDiscounts;

    private boolean rulesShown = false;

    private GameManager() {
        preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        json = new Json();
        loadDiscounts();

    }

    public static GameManager getInstance() {
        return INSTANCE;
    }
    public void addCollectedDiscount(String discount) {
        if (discount == null || discount.isEmpty()) {
            return;
        }
        boolean found = false;
        for (Result result : collectedDiscounts) {
            if (result.getDiscountType().equals(discount)) {
                result.setCount(result.getCount() + 1);
                found = true;
                break;
            }
        }
        if (!found) {
            collectedDiscounts.add(new Result(discount, 1));
        }
        saveDiscounts();
    }

    public List<Result> getCollectedDiscounts() {
        return new ArrayList<>(collectedDiscounts); // Return a copy for safety
    }


    private void saveDiscounts() {
        try {
            File file = Gdx.files.local(RESULTS_FILE).file();
            if (!file.exists()) {
                file.createNewFile();
            }
            try (FileWriter writer = new FileWriter(file)) {
                Gson gson = new Gson();
                gson.toJson(collectedDiscounts, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving discounts: " + e.getMessage());
        }
    }

    private void loadDiscounts() {
        try {
            File file = Gdx.files.local(RESULTS_FILE).file();
            if (file.exists()) {
                try (FileReader reader = new FileReader(file)) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Result>>() {}.getType();
                    collectedDiscounts = gson.fromJson(reader, listType);
                    if (collectedDiscounts == null) {
                        collectedDiscounts = new ArrayList<>();
                    } else {
                        // Remove entries with empty discountType
                        collectedDiscounts.removeIf(result -> result.getDiscountType().isEmpty());
                    }
                }
            } else {
                collectedDiscounts = new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading discounts: " + e.getMessage());
            collectedDiscounts = new ArrayList<>();
        }
    }

    public class Result {
        private String discountType;
        private int count;

        public Result(String discountType, int count) {
            this.discountType = discountType;
            this.count = count;
        }

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public void setMusicEnabled(boolean isEnabled) {
        preferences.putBoolean(MUSIC_ENABLED_KEY, isEnabled);
        preferences.flush();
    }

    public boolean isMusicEnabled() {
        return preferences.getBoolean(MUSIC_ENABLED_KEY, true); // Privzeto omogočeno
    }

    public void setSoundEffectsEnabled(boolean isEnabled) {
        preferences.putBoolean(SOUND_EFFECTS_ENABLED_KEY, isEnabled);
        preferences.flush();
    }

    public boolean isSoundEffectsEnabled() {
        return preferences.getBoolean(SOUND_EFFECTS_ENABLED_KEY, true); // Privzeto omogočeno
    }

    public void setGridSize(int gridSize) {
        preferences.putInteger(GRID_SIZE_KEY, gridSize);
        preferences.flush();
    }

    public int getGridSize() {
        return preferences.getInteger(GRID_SIZE_KEY, DEFAULT_GRID_SIZE);
    }

    public void setDiscount(int discount) {
        preferences.putInteger(DISCOUNT_KEY, discount);
        preferences.flush();
    }

    public int getDiscount() {
        return preferences.getInteger(DISCOUNT_KEY, DEFAULT_DISCOUNT);
    }


    public boolean areRulesShown() {
        return rulesShown;
    }

    public void setRulesShown(boolean rulesShown) {
        this.rulesShown = rulesShown;
    }

}
