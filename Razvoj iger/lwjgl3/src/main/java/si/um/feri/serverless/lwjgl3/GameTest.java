// GameTest.java
package si.um.feri.serverless.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import si.um.feri.serverless.DiscountGame;

public class GameTest {
    public static void main(String[] arg) {
        // Set up the configuration for the application window
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Game Screen Test");
        config.setWindowedMode(900, 900);
        config.setResizable(true);

        // Launch the application with the DiscountGame
        new Lwjgl3Application(new DiscountGame(), config);
    }
}
