package si.um.feri.serverless.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import si.um.feri.serverless.DiscountGame;
import si.um.feri.serverless.assets.AssetManager;
import si.um.feri.serverless.config.GameConfig;

import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen extends ScreenAdapter {

    private final DiscountGame game;

    private AssetManager assetManager;

    private Viewport viewport;

    private Stage stage;


    public MenuScreen(DiscountGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        stage = new Stage(viewport, game.getBatch());

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
    }

    @Override
    public void resize(int width, int height) {
        // Posodobi velikost zaslona
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
