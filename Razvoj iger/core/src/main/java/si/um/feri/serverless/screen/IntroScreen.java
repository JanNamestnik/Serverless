package si.um.feri.serverless.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import si.um.feri.serverless.DiscountGame;
import si.um.feri.serverless.assets.AssetManager;
import si.um.feri.serverless.config.GameConfig;

public class IntroScreen extends ScreenAdapter {

    private final DiscountGame game;

    private final AssetManager assetManager;

    private Viewport viewport;

    private Stage stage;

    private TextureAtlas gameplayAtlas;


    public IntroScreen(DiscountGame game) {
    this.game = game;
    this.assetManager = game.getAssetManager();
   }

   @Override
   public void show() {
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        gameplayAtlas = assetManager.getGameplayAtlas();

   }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 0f);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
