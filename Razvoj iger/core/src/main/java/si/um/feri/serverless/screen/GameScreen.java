package si.um.feri.serverless.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import si.um.feri.serverless.DiscountGame;
import si.um.feri.serverless.assets.AssetDescriptors;
import si.um.feri.serverless.assets.AssetManager;
import si.um.feri.serverless.assets.RegionNames;
import si.um.feri.serverless.common.GameManager;
import si.um.feri.serverless.config.GameConfig;

public class GameScreen extends ScreenAdapter {

    private final DiscountGame game;
    private final AssetManager assetManager;
    private Stage stage;
    private Viewport viewport;
    private Sound mouseClick;
    private Sound menuPick;

    private Skin skin_alternative;

    private Skin skin;
    private TextureAtlas gameplayAtlas;

    private TextureRegion scratchCoin;
    private TextureRegion bombTexture;
    private TextureRegion diamondTexture;

    private TextureRegion hudBackground;

    public GameScreen(DiscountGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        gameplayAtlas = assetManager.getGameplayAtlas();
        mouseClick = assetManager.getMouseClickSound(AssetDescriptors.MOUSE_CLICK);
        menuPick = assetManager.getPickSound(AssetDescriptors.MENU_PICK);

        scratchCoin = gameplayAtlas.findRegion(RegionNames.SCRATCH_COIN);
        bombTexture = gameplayAtlas.findRegion(RegionNames.BOMB);
        diamondTexture = gameplayAtlas.findRegion(RegionNames.DIAMOND);
        hudBackground = gameplayAtlas.findRegion(RegionNames.GAMEPLAY_HUD);

        skin_alternative = assetManager.getAlternativeSkin(AssetDescriptors.UI_SKIN_ALTERNATIVE);
        skin = assetManager.getSkin(AssetDescriptors.UI_SKIN);


        Table gameTable = createUI();
        stage.addActor(gameTable);

        Table hudTable = createHud();
        stage.addActor(hudTable);

        Table settingsTable = gameplaySettings();
        settingsTable.setPosition(20, GameConfig.HEIGHT - settingsTable.getHeight() - 20);
        stage.addActor(settingsTable);

        Gdx.input.setInputProcessor(stage);
    }

    private Table createUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        List<int[]> positions = new ArrayList<>();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                positions.add(new int[]{row, col});
            }
        }
        Collections.shuffle(positions);

        Table boardTable = new Table();
        int[] bombPosition = positions.remove(0);
        int[] diamondPosition = positions.remove(0);

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                final Image image;
                final Image hiddenImage;
                if (row == bombPosition[0] && col == bombPosition[1]) {
                    hiddenImage = new Image(bombTexture);
                } else if (row == diamondPosition[0] && col == diamondPosition[1]) {
                    hiddenImage = new Image(diamondTexture);
                } else {
                    hiddenImage = null;
                }

                image = new Image(scratchCoin);
                image.setSize(64, 64); // Set size to match the coin
                image.setZIndex(1); // Set z-index for coins

                if (hiddenImage != null) {
                    hiddenImage.setVisible(false);
                    hiddenImage.setSize(64, 64); // Set size to match the coin
                    hiddenImage.setPosition(image.getX(), image.getY()); // Set position to match the coin
                    boardTable.addActor(hiddenImage);
                }

                image.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        playSelectSound();
                        image.addAction(Actions.sequence(
                            Actions.fadeOut(0.3f), // Fade out over 0.3 seconds
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    if (hiddenImage != null) {
                                        hiddenImage.setPosition(image.getX(), image.getY()); // Ensure position matches
                                        hiddenImage.setVisible(true);
                                        hiddenImage.addAction(Actions.fadeIn(0.5f)); // Fade in over 0.3 seconds
                                    }
                                    image.remove();
                                }
                            })
                        ));
                    }
                });
                boardTable.add(image).size(64).pad(5); // Adjust size and padding as needed
            }
            boardTable.row();
        }

        table.add(boardTable).center().padLeft(25);
        return table;
    }

    // Create a table with buttons for the HUD
    private Table createHud() {
        Table hudTable = new Table();
        hudTable.setFillParent(true);

        // Gumb za vrnitev na MenuScreen
        TextButton endGameButton = new TextButton("End Game", assetManager.getSkin(AssetDescriptors.UI_SKIN));
        endGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game)); // Vrni se na MenuScreen
            }
        });

        // Celotna tabela
        hudTable.bottom().right();
        hudTable.padBottom(100).padRight(100);

        hudTable.add(endGameButton).pad(5).row(); // Dodaj padding in nov vrstico

        return hudTable;
    }

    private Table gameplaySettings() {
        Table settingsTable = new Table();

        Label.LabelStyle mainTitle = new Label.LabelStyle();
        mainTitle.font = assetManager.get(AssetDescriptors.UI_FONT_INTRO);
        mainTitle.font.getData().setScale(0.5f);
        Label titleLabel = new Label("Configurations", mainTitle);

        Label.LabelStyle bombNumberLabelStyle = new Label.LabelStyle();
        bombNumberLabelStyle.font = assetManager.get(AssetDescriptors.UI_FONT);
        bombNumberLabelStyle.font.getData().setScale(0.5f);
        Label bombNumberLabel = new Label("Bombs:", bombNumberLabelStyle);

        SelectBox<Integer> bombSelectBox = new SelectBox<>(skin_alternative);
        bombSelectBox.setItems(1, 2, 3, 4, 5);

        Label.LabelStyle tileNumberStyle = new Label.LabelStyle();
        tileNumberStyle.font = assetManager.get(AssetDescriptors.UI_FONT);
        tileNumberStyle.font.getData().setScale(0.5f);
        Label tileNumberLabel = new Label("Grid size:", tileNumberStyle);

        SelectBox<Integer> tileSelectBox = new SelectBox<>(skin_alternative);
        tileSelectBox.setItems(25, 36, 49, 64);

        settingsTable.add(titleLabel).center().padBottom(20).colspan(2).row();
        settingsTable.add(bombNumberLabel).left().padBottom(5).padRight(5);
        settingsTable.add(bombSelectBox).left().padBottom(5).row();
        settingsTable.add(tileNumberLabel).left().padBottom(5).padRight(5);
        settingsTable.add(tileSelectBox).left().padBottom(5).row();

        settingsTable.top().left().padTop(70).padLeft(40);

        return settingsTable;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);

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

    private void playSelectSound() {
        if (GameManager.getInstance().isSoundEffectsEnabled()) {
            mouseClick.play(0.3f);
        }
    }
}
