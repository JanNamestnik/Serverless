package si.um.feri.serverless.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameManager {

    private static final String PREFERENCES_NAME = "game_settings";

    private static final String SOUND_EFFECTS_ENABLED_KEY = "sound_effects_enabled";
    private static final String MUSIC_ENABLED_KEY = "music_enabled";
    private static final String GRID_SIZE_KEY = "grid_size";
    private static final String DISCOUNT_KEY = "discount";
    private static final int DEFAULT_GRID_SIZE = 25;
    private static final int DEFAULT_DISCOUNT = 5;

    private static final GameManager INSTANCE = new GameManager();
    private final Preferences preferences;

    private GameManager() {
        preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
    }

    public static GameManager getInstance() {
        return INSTANCE;
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
}
