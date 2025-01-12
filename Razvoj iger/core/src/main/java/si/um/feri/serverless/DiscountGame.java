package si.um.feri.serverless;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import si.um.feri.serverless.assets.AssetManager;
import si.um.feri.serverless.screen.IntroScreen;
import si.um.feri.serverless.screen.MenuScreen;

public class DiscountGame extends Game {

    private SpriteBatch batch;
    private AssetManager assetManager;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        assetManager = new AssetManager();
        assetManager.loadAllAssets();
        assetManager.finishLoading();
        assetManager.getLogger().setLevel(Logger.DEBUG);

        batch = new SpriteBatch();

        // Namesto MenuScreen kot začetni zaslon, začni z IntroScreen (ali pozneje preklopi nanj)
        setScreen(new IntroScreen(this));
    }


    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
