package si.um.feri.serverless.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static final String PREFERENCES_NAME = "game_settings";

    private static final String SOUND_EFFECTS_ENABLED_KEY = "sound_effects_enabled";
    private static final String MUSIC_ENABLED_KEY = "music_enabled";

    private static final GameManager INSTANCE = new GameManager();
    private final Preferences preferences;

    public GameManager() {
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

}
