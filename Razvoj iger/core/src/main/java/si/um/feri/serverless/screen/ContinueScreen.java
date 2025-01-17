package si.um.feri.serverless.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
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
import si.um.feri.serverless.common.GameManager;
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
    private Sound continuePick;

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

        continuePick = assetManager.getPickSound(AssetDescriptors.MENU_PICK);

        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("assets/sparkle.p"), Gdx.files.internal("assets"));
        particleEffect.scaleEffect(0.3f);
        particleEffect.start();

        Table table = createUi();
        stage.addActor(table);

        // Dodaj animacijo naslova
        addTitleAnimation();

        Gdx.input.setInputProcessor(stage);
    }

    private Table createUi() {
        Table table = new Table(skin);
        table.defaults().pad(10);
        table.setFillParent(true);

        // Load the custom font
        BitmapFont customFont = assetManager.get(AssetDescriptors.CONTINUE_FONT);
        BitmapFont customFontTitle = assetManager.get(AssetDescriptors.CONTINUE_INTRO_TITLE_FONT);

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


        TextButton.TextButtonStyle startButtonStyle = new TextButton.TextButtonStyle();
        startButtonStyle.font = customFont;

        TextButton startButton = new TextButton("Start the Game", startButtonStyle);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playButtonSound();
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
                playButtonSound();
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

        table.add(descriptionLabel).colspan(2).center().width(400).padBottom(20).row();
        table.add(startButton).padRight(20);
        table.add(backButton).row();
        table.add(leafClover).center().padBottom(20).row();

        return table;
    }

    private void addTitleAnimation() {
        String title = "Welcome to our game!";
        float startX = GameConfig.WIDTH / 2 - (title.length() * 20) / 2;
        float startY = GameConfig.HEIGHT / 2 + 150; // Postavite naslov višje

        for (int i = 0; i < title.length(); i++) {
            char letter = title.charAt(i);
            Label label = new Label(String.valueOf(letter), new Label.LabelStyle(assetManager.get(AssetDescriptors.CONTINUE_INTRO_TITLE_FONT), null));
            label.setPosition(startX + i * 20, startY);
            label.getColor().a = 0; // Začetno nevidno

            // Dodajte fade-in učinek za vsako črko
            label.addAction(Actions.sequence(
                Actions.delay(i * 0.1f), // Zamuda za vsako naslednjo črko
                Actions.fadeIn(0.5f)
            ));

            stage.addActor(label);
        }
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

    private void playButtonSound() {
        if (GameManager.getInstance().isSoundEffectsEnabled()) {
            continuePick.play(0.3f); // Nastavite glasnost, če je potrebno
        }
    }

}
