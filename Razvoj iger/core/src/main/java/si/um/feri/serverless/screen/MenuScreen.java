package si.um.feri.serverless.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

        backgroundTexture = new Texture(Gdx.files.internal("assets/backgroundMenu.png"));

        TextureRegion coinTexture = assetManager.getGameplayAtlas().findRegion(RegionNames.COIN);
        coinPool = new FallingCoinPool(coinTexture);

        coins = new Array<>();

        Gdx.input.setInputProcessor(stage);

        spawnCoins();

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
        titleStyle.font = assetManager.get(AssetDescriptors.UI_FONT_INTRO);
        titleStyle.font.getData().setScale(1.5f); // Povečajte velikost pisave

        Label titleLabel = new Label("Deal hunter", titleStyle);

        table.add(titleLabel).padBottom(30).colspan(1).center().row();

        // Create buttons
        TextButton.TextButtonStyle buttonStyle = skin.get(TextButton.TextButtonStyle.class);
        buttonStyle.font.getData().setScale(1.5f); // Povečajte velikost pisave za gumbe

        TextButton playButton = new TextButton("Play", buttonStyle);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        TextButton collectionButton = new TextButton("Collection", buttonStyle);
        collectionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CollectionScreen(game));
            }
        });

        TextButton settingsButton = new TextButton("Settings", buttonStyle);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game));
            }
        });

        TextButton backButton = new TextButton("Back to map", buttonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameWrapper gameWrapper = (GameWrapper) Gdx.app.getApplicationListener();
                gameWrapper.switchToApplicationAdapter();
            }
        });

        TextButton quitButton = new TextButton("Quit", buttonStyle);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Table buttonTable = new Table();
        buttonTable.defaults().padLeft(30).padRight(30);

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
}
