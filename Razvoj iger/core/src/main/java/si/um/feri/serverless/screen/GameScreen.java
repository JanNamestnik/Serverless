package si.um.feri.serverless.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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

    private Sound diamondSound;

    private Sound bombSound;

    private Skin skin_alternative;

    private Skin skin;
    private TextureAtlas gameplayAtlas;

    private TextureRegion scratchCoin;
    private TextureRegion bombTexture;
    private TextureRegion diamondTexture;

    private TextureRegion hudBackground;

    private int foundDiamonds;
    private int totalDiamonds;

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
        diamondSound = assetManager.getDiamondSound(AssetDescriptors.DIAMOND_SOUND);
        bombSound = assetManager.getBombSound(AssetDescriptors.BOMB_SOUND);

        scratchCoin = gameplayAtlas.findRegion(RegionNames.SCRATCH_COIN);
        bombTexture = gameplayAtlas.findRegion(RegionNames.BOMB);
        diamondTexture = gameplayAtlas.findRegion(RegionNames.DIAMOND);
        hudBackground = gameplayAtlas.findRegion(RegionNames.GAMEPLAY_HUD);

        skin_alternative = assetManager.getAlternativeSkin(AssetDescriptors.UI_SKIN_ALTERNATIVE);
        skin = assetManager.getSkin(AssetDescriptors.UI_SKIN);


        int savedGridSize = (int) Math.sqrt(GameManager.getInstance().getGridSize());
        int discount = GameManager.getInstance().getDiscount();
        String discountString = discount + "%";

        totalDiamonds = savedGridSize * savedGridSize - calculateNumberOfMines(discountString);
        foundDiamonds = 0;

        Table gameTable = createUI(savedGridSize, calculateNumberOfMines(discountString));
        stage.addActor(gameTable);

        Table hudTable = createHud();
        stage.addActor(hudTable);

        Table settingsTable = gameplaySettings();
        settingsTable.setPosition(20, GameConfig.HEIGHT - settingsTable.getHeight() - 20);
        stage.addActor(settingsTable);

        // Show the welcome dialog
        showWelcomeDialog();

        Gdx.input.setInputProcessor(stage);
    }

    private void showWelcomeDialog() {
        // Create a LabelStyle for the dialog title
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = assetManager.get(AssetDescriptors.MAP_FONT);

        // Create the title label
        Label titleLabel = new Label("Deal hunter", titleStyle);

        // Create the content table
        Table contentTable = new Table();
        contentTable.add(titleLabel).center().padBottom(20).row();
        contentTable.add(new Label("Rules: \n\n - Try to find all the diamonds on the board to win \n\n - Finding a mine means u lose your discount \n \n - Bigger discount = more mines  ", skin)).center();

        // Create and show the dialog
        Dialog dialog = new Dialog("", skin) {
            @Override
            protected void result(Object object) {
                // Handle dialog result if needed
            }
        };
        dialog.getContentTable().add(contentTable).center();
        dialog.button("OK", true);
        dialog.show(stage);
        dialog.setSize(800, 300); // Adjust the size as needed
        dialog.setPosition((GameConfig.WIDTH - dialog.getWidth()) / 2, (GameConfig.HEIGHT - dialog.getHeight()) / 2); // Center the dialog
    }

    private void resetGame() {
        foundDiamonds = 0;
        int savedGridSize = (int) Math.sqrt(GameManager.getInstance().getGridSize());
        int discount = GameManager.getInstance().getDiscount();
        String discountString = discount + "%";
        totalDiamonds = savedGridSize * savedGridSize - calculateNumberOfMines(discountString);

        stage.clear(); // Clear the stage to remove the old board

        Table gameTable = createUI(savedGridSize, calculateNumberOfMines(discountString));
        stage.addActor(gameTable);

        Table hudTable = createHud();
        stage.addActor(hudTable);

        Table settingsTable = gameplaySettings();
        settingsTable.setPosition(20, GameConfig.HEIGHT - settingsTable.getHeight() - 20);
        stage.addActor(settingsTable);

        // Show the welcome dialog
        showWelcomeDialog();
    }

    private void showWinDialog() {
        // Create a LabelStyle for the dialog title
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = assetManager.get(AssetDescriptors.UI_FONT_INTRO);

        // Create the title label
        Label titleLabel = new Label("Congrats!", titleStyle);

        // Create the content table
        Table contentTable = new Table();
        contentTable.add(titleLabel).center().padBottom(20).row();
        contentTable.add(new Label("You just won yourself a discount", skin)).center();

        // Create and show the dialog
        Dialog dialog = new Dialog("", skin) {
            @Override
            protected void result(Object object) {
                // Handle dialog result if needed
                resetGame();
            }
        };
        dialog.getContentTable().add(contentTable).center();
        dialog.button("Continue", true);
        dialog.show(stage);
        dialog.setSize(800, 300); // Adjust the size as needed
        dialog.setPosition((GameConfig.WIDTH - dialog.getWidth()) / 2, (GameConfig.HEIGHT - dialog.getHeight()) / 2); // Center the dialog
    }

    private void showLoseDialog() {
        // Create a LabelStyle for the dialog title
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = assetManager.get(AssetDescriptors.UI_FONT_INTRO);

        // Create the title label
        Label titleLabel = new Label("Game Over", titleStyle);

        // Create the content table
        Table contentTable = new Table();
        contentTable.add(titleLabel).center().padBottom(20).row();
        contentTable.add(new Label("You hit a bomb! Better luck next time.", skin)).center();

        // Create and show the dialog
        Dialog dialog = new Dialog("", skin) {
            @Override
            protected void result(Object object) {
                // Handle dialog result if needed
                resetGame();
            }
        };
        dialog.getContentTable().add(contentTable).center();
        dialog.button("OK", true);
        dialog.show(stage);
        dialog.setSize(800, 300); // Adjust the size as needed
        dialog.setPosition((GameConfig.WIDTH - dialog.getWidth()) / 2, (GameConfig.HEIGHT - dialog.getHeight()) / 2); // Center the dialog
    }

    private int calculateNumberOfMines(String discount) {
        switch (discount) {
            case "5%":
                return 1;
            case "10%":
                return 3;
            case "25%":
                return 5;
            case "50%":
                return 10;
            case "100%":
                return 20;
            default:
                return 1;
        }
    }

    private Table createUI(int gridSize, int numberOfMines) {
        Table table = new Table();
        table.setFillParent(true);
        table.center().padTop(50);

        List<int[]> positions = new ArrayList<>();
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                positions.add(new int[]{row, col});
            }
        }
        Collections.shuffle(positions);

        Table boardTable = new Table();
        List<int[]> minePositions = positions.subList(0, numberOfMines);

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                final Image image;
                final Image hiddenImage;
                boolean isMine = false;

                for (int[] pos : minePositions) {
                    if (pos[0] == row && pos[1] == col) {
                        isMine = true;
                        break;
                    }
                }

                if (isMine) {
                    hiddenImage = new Image(bombTexture);
                    hiddenImage.setName("bomb");
                } else {
                    hiddenImage = new Image(diamondTexture);
                    hiddenImage.setName("diamond");
                }

                image = new Image(scratchCoin);
                image.setSize(64, 64); // Set size to match the coin
                image.setZIndex(1); // Set z-index for coins

                hiddenImage.setVisible(false);
                hiddenImage.setSize(64, 64); // Set size to match the coin
                hiddenImage.setPosition(image.getX(), image.getY()); // Set position to match the coin
                boardTable.addActor(hiddenImage);

                image.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        playSelectSound();
                        image.addAction(Actions.sequence(
                            Actions.fadeOut(0.3f), // Fade out over 0.3 seconds
                            Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    hiddenImage.setPosition(image.getX(), image.getY()); // Ensure position matches
                                    hiddenImage.setVisible(true);
                                    hiddenImage.addAction(Actions.fadeIn(0.3f)); // Fade in over 0.3 seconds
                                    image.remove();

                                    // Check if the hidden image is a diamond or a bomb
                                    if ("diamond".equals(hiddenImage.getName())) {
                                        foundDiamonds++;
                                        if (foundDiamonds == totalDiamonds) {
                                            showWinDialog();
                                        }
                                    } else if ("bomb".equals(hiddenImage.getName())) {
                                        showLoseDialog();
                                    }
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
        mainTitle.font.getData().setScale(1f);
        Label titleLabel = new Label("Challenge", mainTitle);

        Label.LabelStyle bombNumberLabelStyle = new Label.LabelStyle();
        bombNumberLabelStyle.font = assetManager.get(AssetDescriptors.UI_FONT);
        bombNumberLabelStyle.font.getData().setScale(0.7f);
        Label bombNumberLabel = new Label("Discount:", bombNumberLabelStyle);

        SelectBox<String> bombSelectBox = new SelectBox<>(skin_alternative);
        bombSelectBox.setItems("5%", "10%", "25%", "50%", "100%");

        Label.LabelStyle tileNumberStyle = new Label.LabelStyle();
        tileNumberStyle.font = assetManager.get(AssetDescriptors.UI_FONT);
        tileNumberStyle.font.getData().setScale(0.8f);
        Label tileNumberLabel = new Label("Grid size:", tileNumberStyle);

        SelectBox<String> tileSelectBox = new SelectBox<>(skin_alternative);
        tileSelectBox.setItems("25 tiles", "36 tiles", "49 tiles");

        // Load saved grid size and set it in the SelectBox
        int savedGridSize = GameManager.getInstance().getGridSize();
        tileSelectBox.setSelected(savedGridSize + " tiles");

        // Load saved discount and set it in the SelectBox
        int savedDiscount = GameManager.getInstance().getDiscount();
        bombSelectBox.setSelected(savedDiscount + "%");

        tileSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = tileSelectBox.getSelected();
                int gridSize = (int) Math.sqrt(Integer.parseInt(selected.split(" ")[0]));
                GameManager.getInstance().setGridSize(gridSize * gridSize); // Save the selected grid size
                updateBoard(gridSize, calculateNumberOfMines(bombSelectBox.getSelected()));
            }
        });

        bombSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selected = bombSelectBox.getSelected();
                int discount = Integer.parseInt(selected.replace("%", ""));
                GameManager.getInstance().setDiscount(discount); // Save the selected discount
                updateBoard((int) Math.sqrt(GameManager.getInstance().getGridSize()), calculateNumberOfMines(selected));
            }
        });

        TextureRegion hudBackgroundRegion = gameplayAtlas.findRegion(RegionNames.HUD_MENU);
        TextureRegionDrawable menuBackgroundDrawable = new TextureRegionDrawable(hudBackgroundRegion);
        menuBackgroundDrawable.setMinWidth(hudBackgroundRegion.getRegionWidth()); // Scale width
        menuBackgroundDrawable.setMinHeight(hudBackgroundRegion.getRegionHeight()); // Scale height
        settingsTable.setBackground(menuBackgroundDrawable);

        settingsTable.add(titleLabel).center().padBottom(20).colspan(2).row();
        settingsTable.add(bombNumberLabel).left().padBottom(5).padRight(5);
        settingsTable.add(bombSelectBox).left().padBottom(5).row();
        settingsTable.add(tileNumberLabel).left().padBottom(5).padRight(5);
        settingsTable.add(tileSelectBox).left().padBottom(5).row();

        settingsTable.top().left().padTop(60).padLeft(40);

        return settingsTable;
    }

    private void updateBoard(int gridSize, int numberOfMines) {
        stage.clear(); // Clear the stage to remove the old board

        Table gameTable = createUI(gridSize, numberOfMines);
        stage.addActor(gameTable);

        Table hudTable = createHud();
        stage.addActor(hudTable);

        Table settingsTable = gameplaySettings();
        settingsTable.setPosition(20, GameConfig.HEIGHT - settingsTable.getHeight() - 20);
        stage.addActor(settingsTable);
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

    private void playDiamondSound() {
        if (GameManager.getInstance().isSoundEffectsEnabled()) {
            mouseClick.play(0.3f);
        }
    }

    private void playBombSound() {
        if (GameManager.getInstance().isSoundEffectsEnabled()) {
            mouseClick.play(0.3f);
        }
    }
}
