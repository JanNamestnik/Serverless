package si.um.feri.serverless.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import si.um.feri.serverless.DiscountGame;

/** Launches the desktop (LWJGL3) application. */
public class DesktopLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Discoint Game");
        config.setWindowedMode(900, 900);
        config.setResizable(false);
        new Lwjgl3Application(new DiscountGame(), config);
    }
}
