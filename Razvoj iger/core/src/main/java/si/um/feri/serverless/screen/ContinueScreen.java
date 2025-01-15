// ContinueScreen.java
package si.um.feri.serverless.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import si.um.feri.serverless.DiscountGame;
import si.um.feri.serverless.GameWrapper;
import si.um.feri.serverless.assets.AssetDescriptors;
import si.um.feri.serverless.assets.AssetManager;
import si.um.feri.serverless.assets.RegionNames;
import si.um.feri.serverless.config.GameConfig;

public class ContinueScreen extends ScreenAdapter {

    private final DiscountGame game;
    private final AssetManager assetManager;
    private Viewport viewport;
    private Stage stage;
    private Skin skin;
    private Texture backgroundIntro;
    private TextureAtlas gameplayAtlas;
    private ParticleEffect particleEffect;
    private Image leafClover;

    public ContinueScreen(DiscountGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        skin = assetManager.getSkin(AssetDescriptors.UI_SKIN);
        backgroundIntro = new Texture(Gdx.files.internal("assets/backgroundIntro.png"));
        gameplayAtlas = assetManager.getGameplayAtlas();

        // Load the particle effect
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("assets/sparkle.p"), Gdx.files.internal("assets"));
        particleEffect.scaleEffect(0.3f); // Scale the particle effect to make it smaller
        particleEffect.start();

        Table table = createUi();
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    private Table createUi() {
        Table table = new Table(skin);
        table.defaults().pad(10);
        table.setFillParent(true);

        // Load the custom font
        BitmapFont customFont = assetManager.get(AssetDescriptors.UI_FONT);
        BitmapFont customFontTitle = assetManager.get(AssetDescriptors.UI_FONT_INTRO);

        // Create a LabelStyle with the custom font
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = customFontTitle;

        leafClover = new Image(gameplayAtlas.findRegion(RegionNames.LEAF_CLOVER));

        // Define the animation action with rotation
        leafClover.addAction(Actions.forever(
            Actions.parallel(
                Actions.sequence(
                    Actions.moveBy(50, 50, 1), // Move to top-right
                    Actions.moveBy(-50, 50, 1), // Move to top-left
                    Actions.moveBy(-50, -50, 1), // Move to bottom-left
                    Actions.moveBy(50, -50, 1) // Move to bottom-right
                ),
                Actions.rotateBy(360, 4) // Rotate 360 degrees over 4 seconds
            )
        ));

        Label titleLabel = new Label("Welcome to our game!", titleStyle);

        TextButton.TextButtonStyle startButtonStyle = new TextButton.TextButtonStyle();
        startButtonStyle.font = customFont;

        TextButton startButton = new TextButton("Start the Game", startButtonStyle);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new IntroScreen(game));
            }
        });

        startButton.addAction(Actions.forever(
            Actions.sequence(
                Actions.moveBy(0, 10, 0.5f),
                Actions.moveBy(0, -10, 0.5f)
            )
        ));

        TextButton.TextButtonStyle mapButtonStyle = new TextButton.TextButtonStyle();
        mapButtonStyle.font = customFont;

        TextButton backButton = new TextButton("Back to Map", mapButtonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch back to Map
                GameWrapper wrapper = (GameWrapper) Gdx.app.getApplicationListener();
                wrapper.switchToApplicationAdapter();
            }
        });

        Label descriptionLabel = new Label(
            "Our game gives user's a chance to shine.\n\n" +
                "Have fun and save money!", // New line using \n
            skin, "default"
        );
        descriptionLabel.setWrap(true); // Enable text wrapping if too wide

        // Add title, buttons, and description to the table
        table.add(titleLabel).colspan(2).center().padBottom(10).row();
        table.add(descriptionLabel).colspan(2).center().width(400).padBottom(20).row();
        table.add(startButton).padRight(20);
        table.add(backButton).row();
        table.add(leafClover).center().padBottom(20).row();

        return table;
    }

    @Override
    public void render(float delta) {
        // Clear screen with black color
        ScreenUtils.clear(0, 0, 0, 1);

        viewport.apply();

        SpriteBatch batch = game.getBatch();
        batch.begin();
        batch.draw(backgroundIntro, 0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);

        // Update the particle effect's position to follow the leafClover
        particleEffect.setPosition(
            leafClover.getX() + leafClover.getWidth() / 2 - 30,
            leafClover.getY() + leafClover.getHeight() / 2 - 25
        );
        particleEffect.draw(batch, delta);

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
