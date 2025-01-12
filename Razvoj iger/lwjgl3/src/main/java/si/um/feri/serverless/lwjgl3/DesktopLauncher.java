package si.um.feri.serverless.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import si.um.feri.serverless.DiscountGame;
import si.um.feri.serverless.Map;
import si.um.feri.serverless.GameWrapper;

/** Launches the desktop (LWJGL3) application. */
public class DesktopLauncher {
    public static void main(String[] arg) {
        // Nastavi konfiguracijo za okno aplikacije
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Discount Game");
        config.setWindowedMode(900, 900);
        config.setResizable(true);

        // Inicializiraj GameWrapper z Map in DiscountGame
        Map mapAdapter = new Map();
        DiscountGame discountGame = new DiscountGame();
        GameWrapper gameWrapper = new GameWrapper(mapAdapter, discountGame);

        // Zaženi aplikacijo z GameWrapper
        new Lwjgl3Application(gameWrapper, config);
    }
}
