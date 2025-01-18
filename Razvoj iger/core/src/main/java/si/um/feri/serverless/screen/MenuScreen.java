package si.um.feri.serverless.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import si.um.feri.serverless.DiscountGame;
import si.um.feri.serverless.GameWrapper;
import si.um.feri.serverless.assets.AssetDescriptors;
import si.um.feri.serverless.assets.AssetManager;
import si.um.feri.serverless.assets.RegionNames;
import si.um.feri.serverless.common.FallingCoin;
import si.um.feri.serverless.common.FallingCoinPool;
import si.um.feri.serverless.common.GameManager;
import si.um.feri.serverless.config.GameConfig;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen extends ScreenAdapter {

    private final DiscountGame game;
    private final AssetManager assetManager;
    private Viewport viewport;
    private Stage stage;
    private Skin skin;
    private Skin skin_alternative;
    Table menuTable;
    private Texture backgroundTexture;
    private Array<FallingCoin> coins;
    private Pool<FallingCoin> coinPool;
    private Sound menuPick;

    private Music menuMusic;

    private TextureAtlas gameplayAtlas;

    private static final int MAX_COINS = 4;

    public MenuScreen(DiscountGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        skin = assetManager.getSkin(AssetDescriptors.UI_SKIN);
        skin_alternative = assetManager.getSkin(AssetDescriptors.UI_SKIN_ALTERNATIVE);
        gameplayAtlas = assetManager.getGameplayAtlas();

        menuPick = assetManager.getPickSound(AssetDescriptors.MENU_PICK);

        menuMusic = assetManager.getMenuMusic(AssetDescriptors.MENU_MUSIC);

        backgroundTexture = new Texture(Gdx.files.internal("assets/backgroundMenu.png"));

        TextureRegion coinTexture = assetManager.getGameplayAtlas().findRegion(RegionNames.COIN);
        coinPool = new FallingCoinPool(coinTexture);

        coins = new Array<>();

        Gdx.input.setInputProcessor(stage);

        spawnCoins();

        if(GameManager.getInstance().isMusicEnabled()) {
            menuMusic.setVolume(0.1f);
            menuMusic.setLooping(true);
            menuMusic.play();
        }

        menuTable = createUi();
        stage.addActor(menuTable);
    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);

        game.getBatch().begin();
        game.getBatch().draw(backgroundTexture, 0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
        game.getBatch().end();

        // Poskrbite, da je menuTable vedno na vrhu
        menuTable.setZIndex(stage.getActors().size - 1);

        stage.act(delta);
        stage.draw();

        for (FallingCoin coin : coins) {
            if (coin.getY() + coin.getHeight() < 0) {
                coinPool.free(coin);
                coins.removeValue(coin, true);
                FallingCoin newCoin = coinPool.obtain();
                coins.add(newCoin);
                stage.addActor(newCoin);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        menuMusic.stop();
        stage.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void spawnCoins() {
        stage.addAction(Actions.forever(Actions.sequence(
            Actions.delay(2f), // Prilagodi zamik po potrebi
            Actions.run(new Runnable() {
                @Override
                public void run() {
                    if (coins.size < MAX_COINS) {
                        FallingCoin coin = coinPool.obtain();
                        coins.add(coin);
                        stage.addActor(coin);
                    }
                }
            })
        )));
    }

    private Table createUi() {
        Table table = new Table(skin);
        table.defaults().pad(20);

        // Create title
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = assetManager.get(AssetDescriptors.CONTINUE_INTRO_TITLE_FONT);
        titleStyle.font.getData().setScale(1.3f); // Increase font size

        Label titleLabel = new Label("Deal hunter", titleStyle);

        table.add(titleLabel).padBottom(30).colspan(1).center().row();

        // Create buttons
        TextButton.TextButtonStyle buttonStyle = skin.get(TextButton.TextButtonStyle.class);
        buttonStyle.font.getData().setScale(1.1f); // Increase font size for buttons

        TextButton playButton = new TextButton("Play", buttonStyle);
        // In MenuScreen.java
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.getInstance().setRulesShown(false); // Reset the rulesShown flag
                game.setScreen(new GameScreen(game));
            }
        });

        TextButton collectionButton = new TextButton("Collection", buttonStyle);
        collectionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playButtonSound();
                game.setScreen(new CollectionScreen(game));
            }
        });

        TextButton settingsButton = new TextButton("Settings", buttonStyle);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playButtonSound();
                game.setScreen(new SettingsScreen(game));
            }
        });

        TextButton backButton = new TextButton("Back to map", buttonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playButtonSound();
                GameWrapper gameWrapper = (GameWrapper) Gdx.app.getApplicationListener();
                gameWrapper.switchToApplicationAdapter();
            }
        });

        TextButton quitButton = new TextButton("Quit", buttonStyle);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playButtonSound();
                Gdx.app.exit();
            }
        });

        Table buttonTable = new Table();
        buttonTable.defaults().padLeft(30).padRight(30);

        TextureRegion menuBackgroundRegion = gameplayAtlas.findRegion(RegionNames.HUD_MENU);
        TextureRegionDrawable menuBackgroundDrawable = new TextureRegionDrawable(menuBackgroundRegion);
        menuBackgroundDrawable.setMinWidth(menuBackgroundRegion.getRegionWidth() * 1.3f); // Scale width
        menuBackgroundDrawable.setMinHeight(menuBackgroundRegion.getRegionHeight() * 1.3f); // Scale height
        buttonTable.setBackground(menuBackgroundDrawable);

        buttonTable.add(playButton).padBottom(25).expandX().fill().row();
        buttonTable.add(collectionButton).padBottom(25).fillX().row();
        buttonTable.add(settingsButton).padBottom(25).fillX().row();
        buttonTable.add(backButton).padBottom(25).fillX().row();
        buttonTable.add(quitButton).fillX();

        buttonTable.center();

        table.add(buttonTable);
        table.center();
        table.setFillParent(true);
        table.padTop(-60);
        table.pack();

        return table;
    }
    private void playButtonSound() {
        if (GameManager.getInstance().isSoundEffectsEnabled()) {
            menuPick.play(0.3f); // Nastavite glasnost, če je potrebno
        }
    }

}
