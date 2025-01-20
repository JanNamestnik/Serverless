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

    private Sound diamondSound;

    private Sound bombSound;

    private Sound notificationSound;

    private Sound winSound;

    private Skin skin_alternative;

    private Skin skin;

    private TextureRegion scratchCoin;
    private TextureRegion bombTexture;
    private TextureRegion diamondTexture;

    private SelectBox<String> bombSelectBox;

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

        TextureAtlas gameplayAtlas = assetManager.getGameplayAtlas();
        mouseClick = assetManager.getMouseClickSound(AssetDescriptors.MOUSE_CLICK);
        diamondSound = assetManager.getDiamondSound(AssetDescriptors.DIAMOND_SOUND);
        bombSound = assetManager.getBombSound(AssetDescriptors.BOMB_SOUND);
        notificationSound = assetManager.getNotificationSound(AssetDescriptors.NOTIFICATION_SOUND);
        winSound = assetManager.getWinSound(AssetDescriptors.WIN_SOUND);

        scratchCoin = gameplayAtlas.findRegion(RegionNames.SCRATCH_COIN);
        bombTexture = gameplayAtlas.findRegion(RegionNames.BOMB);
        diamondTexture = gameplayAtlas.findRegion(RegionNames.DIAMOND);

        skin_alternative = assetManager.getAlternativeSkin(AssetDescriptors.UI_SKIN_ALTERNATIVE);
        skin = assetManager.getSkin(AssetDescriptors.UI_SKIN);

        totalDiamonds = 10; // Set fixed number of diamonds to win
        foundDiamonds = 0;

        Table gameTable = createUI((int) Math.sqrt(GameManager.getInstance().getGridSize()), calculateNumberOfMines(GameManager.getInstance().getDiscount() + "%"));
        stage.addActor(gameTable);

        Table hudTable = createHud();
        stage.addActor(hudTable);

        Table settingsTable = gameplaySettings();
        settingsTable.setPosition(20, GameConfig.HEIGHT - settingsTable.getHeight() - 20);
        stage.addActor(settingsTable);

        Table rulesTable = rulesHud();
        rulesTable.setPosition(GameConfig.WIDTH - rulesTable.getWidth() - 20, GameConfig.HEIGHT - rulesTable.getHeight() - 20);
        stage.addActor(rulesTable);

        if (!GameManager.getInstance().areRulesShown()) {
            playNotificationSound();
            showWelcomeDialog(""); // Pass an empty string or appropriate discount value
            GameManager.getInstance().setRulesShown(true);
        }

        Gdx.input.setInputProcessor(stage);
    }
    private void showWelcomeDialog(String discount) {
        GameManager.getInstance().addCollectedDiscount(discount);

        // Create a LabelStyle for the dialog title
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = assetManager.get(AssetDescriptors.UI_FONT_INTRO);

        // Create the title label
        Label titleLabel = new Label("Deal hunter", titleStyle);

        // Create the content table
        Table contentTable = new Table();
        contentTable.add(titleLabel).center().padBottom(5).row();
        contentTable.add(new Label("Rules: \n\n - Try to find all the diamonds on the board to win \n\n - Finding a mine means you lose your discount \n \n - Bigger discount = more mines", skin)).center();

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
        dialog.setSize(800, 300);
        dialog.setPosition((GameConfig.WIDTH - dialog.getWidth()) / 2, (GameConfig.HEIGHT - dialog.getHeight()) / 2);
    }

    private void showBombsDoubledDialog() {
        // Create a LabelStyle for the dialog title
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = assetManager.get(AssetDescriptors.UI_FONT_INTRO);

        // Create the title label
        Label titleLabel = new Label("Warning", titleStyle);

        // Create the content table
        Table contentTable = new Table();
        contentTable.add(titleLabel).center().padBottom(5).row();
        contentTable.add(new Label("The number of bomb's is now DOUBLED! ", skin)).center();

        // Create and show the dialog
        Dialog dialog = new Dialog("", skin) {
            @Override
            protected void result(Object object) {

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
        totalDiamonds = 10; // Set fixed number of diamonds to win

        stage.clear(); // Clear the stage to remove the old board

        Table gameTable = createUI((int) Math.sqrt(GameManager.getInstance().getGridSize()), calculateNumberOfMines(GameManager.getInstance().getDiscount() + "%"));
        stage.addActor(gameTable);

        Table hudTable = createHud();
        stage.addActor(hudTable);

        Table settingsTable = gameplaySettings();
        settingsTable.setPosition(20, GameConfig.HEIGHT - settingsTable.getHeight() - 20);
        stage.addActor(settingsTable);

        Table rulesTable = rulesHud();
        rulesTable.setPosition(GameConfig.WIDTH - rulesTable.getWidth() - 20, GameConfig.HEIGHT - rulesTable.getHeight() - 20);
        stage.addActor(rulesTable);
    }

    private void showWinDialog(String discount) {
        if (discount == null || discount.isEmpty()) {
            return; // Do not show dialog for empty discount types
        }
        GameManager.getInstance().addCollectedDiscount(discount);

        // Create a LabelStyle for the dialog title
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = assetManager.get(AssetDescriptors.UI_FONT_INTRO);

        // Create the title label
        Label titleLabel = new Label("Congrats!", titleStyle);

        // Create the content table
        Table contentTable = new Table();
        contentTable.add(titleLabel).center().padBottom(20).row();
        contentTable.add(new Label("You just won yourself a " + discount + " discount!", skin)).center();

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
        int baseMines;
        switch (discount) {
            case "5%":
                baseMines = 1;
                break;
            case "10%":
                baseMines = 2;
                break;
            case "25%":
                baseMines = 3;
                break;
            case "50%":
                baseMines = 4;
                break;
            case "100%":
                baseMines = 5;
                break;
            default:
                baseMines = 0;
        }

        if (GameManager.getInstance().getGridSize() == 36) {
            baseMines *= 2;
        }

        System.out.println("Number of mines: " + baseMines); // Add this line to print the number of mines

        return baseMines;
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

        System.out.println("Mine positions: " + minePositions); // Add this line to print the mine positions

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
                        image.addAction(Actions.sequence(
                            Actions.fadeOut(0.3f), // Fade out over 0.3 seconds
                            Actions.run(() -> {
                                hiddenImage.setPosition(image.getX(), image.getY()); // Ensure position matches
                                hiddenImage.setVisible(true);
                                hiddenImage.addAction(Actions.fadeIn(0.3f)); // Fade in over 0.3 seconds
                                image.remove();

                                // Check if the hidden image is a diamond or a bomb
                                if ("diamond".equals(hiddenImage.getName())) {
                                    playDiamondSound();
                                    foundDiamonds++;
                                    if (foundDiamonds == totalDiamonds) {
                                        showWinDialog(bombSelectBox.getSelected());
                                        playWinSound();
                                    }
                                } else if ("bomb".equals(hiddenImage.getName())) {
                                    playBombSound();
                                    showLoseDialog();
                                }
                            })
                        ));
                    }
                });
                boardTable.add(image).size(64).pad(5); // Adjust size and padding as needed
            }
            boardTable.row();
        }

        table.add(boardTable).center().padRight(20);
        return table;
    }
    // Create a table with buttons for the HUD
    private Table createHud() {
        Table hudTable = new Table();
        hudTable.setFillParent(true);

        // Ustvarite nov slog za gumb z uporabo HUD_FONT
        TextButton.TextButtonStyle endGameButtonStyle = new TextButton.TextButtonStyle();
        endGameButtonStyle.font = assetManager.get(AssetDescriptors.HUD_FONT);

        // Gumb za vrnitev na MenuScreen
        TextButton endGameButton = new TextButton("End Game", endGameButtonStyle);
        endGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playSelectSound();
                game.setScreen(new MenuScreen(game)); // Vrni se na MenuScreen
            }
        });

        // Celotna tabela
        hudTable.bottom().right();
        hudTable.padBottom(100).padRight(100);

        hudTable.add(endGameButton).pad(5).row(); // Dodaj padding in nov vrstico

        return hudTable;
    }
    private Table rulesHud() {
        Table rulesTable = new Table();

        Label.LabelStyle mainTitle = new Label.LabelStyle();
        mainTitle.font = assetManager.get(AssetDescriptors.HUD_FONT);
        mainTitle.font.getData().setScale(1f);
        Label titleLabel = new Label("Risks:", mainTitle);

        Label.LabelStyle percentageLabelStyle = new Label.LabelStyle();
        percentageLabelStyle.font = assetManager.get(AssetDescriptors.UI_FONT);

        Label.LabelStyle mineLabelStyle = new Label.LabelStyle();
        mineLabelStyle.font = skin.getFont("font"); // Replace "font" with the actual font name in your skin

        // Create labels for each discount percentage and the corresponding number of mines
        Label fivePercentLabel = new Label("5%: ", percentageLabelStyle);
        Label fivePercentMines = new Label("1 mine", mineLabelStyle);

        Label tenPercentLabel = new Label("10%: ", percentageLabelStyle);
        Label tenPercentMines = new Label("2 mines", mineLabelStyle);

        Label twentyFivePercentLabel = new Label("25%: ", percentageLabelStyle);
        Label twentyFivePercentMines = new Label("3 mines", mineLabelStyle);

        Label fiftyPercentLabel = new Label("50%: ", percentageLabelStyle);
        Label fiftyPercentMines = new Label("4 mines", mineLabelStyle);

        Label hundredPercentLabel = new Label("100%: ", percentageLabelStyle);
        Label hundredPercentMines = new Label("5 mines", mineLabelStyle);

        // Add the labels to the table with proper alignment
        rulesTable.add(titleLabel).center().padBottom(20).padRight(10).colspan(2).row();
        rulesTable.add(fivePercentLabel).left().padBottom(5).padRight(10);
        rulesTable.add(fivePercentMines).left().padBottom(5).row();
        rulesTable.add(tenPercentLabel).left().padBottom(5).padRight(10);
        rulesTable.add(tenPercentMines).left().padBottom(5).row();
        rulesTable.add(twentyFivePercentLabel).left().padBottom(5).padRight(10);
        rulesTable.add(twentyFivePercentMines).left().padBottom(5).row();
        rulesTable.add(fiftyPercentLabel).left().padBottom(5).padRight(10);
        rulesTable.add(fiftyPercentMines).left().padBottom(5).row();
        rulesTable.add(hundredPercentLabel).left().padBottom(5).padRight(10);
        rulesTable.add(hundredPercentMines).left().padBottom(5).row();

        rulesTable.top().right().padTop(60).padRight(40);

        return rulesTable;
    }
    private Table gameplaySettings() {
        Table settingsTable = new Table();

        Label.LabelStyle mainTitle = new Label.LabelStyle();
        mainTitle.font = assetManager.get(AssetDescriptors.HUD_FONT);
        mainTitle.font.getData().setScale(1f);
        Label titleLabel = new Label("Settings:", mainTitle);

        Label.LabelStyle bombNumberLabelStyle = new Label.LabelStyle();
        bombNumberLabelStyle.font = assetManager.get(AssetDescriptors.UI_FONT);
        bombNumberLabelStyle.font.getData().setScale(0.7f);
        Label bombNumberLabel = new Label("Discount:", bombNumberLabelStyle);

        bombSelectBox = new SelectBox<>(skin_alternative); // Initialize bombSelectBox
        bombSelectBox.setItems("5%", "10%", "25%", "50%", "100%");

        Label.LabelStyle tileNumberStyle = new Label.LabelStyle();
        tileNumberStyle.font = assetManager.get(AssetDescriptors.UI_FONT);
        tileNumberStyle.font.getData().setScale(0.8f);
        Label tileNumberLabel = new Label("Grid size:", tileNumberStyle);

        SelectBox<String> tileSelectBox = new SelectBox<>(skin_alternative);
        tileSelectBox.setItems("25 tiles", "36 tiles"); // Removed "49 tiles" option

        // Load saved grid size and set it in the SelectBox
        int savedGridSize = GameManager.getInstance().getGridSize();
        tileSelectBox.setSelected(savedGridSize + " tiles");

        // Load saved discount and set it in the SelectBox
        int savedDiscount = GameManager.getInstance().getDiscount();
        bombSelectBox.setSelected(savedDiscount + "%");

        tileSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playSelectSound();
                String selected = tileSelectBox.getSelected();
                int gridSize = (int) Math.sqrt(Integer.parseInt(selected.split(" ")[0]));
                GameManager.getInstance().setGridSize(gridSize * gridSize); // Save the selected grid size
                updateBoard(gridSize, calculateNumberOfMines(bombSelectBox.getSelected()));

                // Show the bomb doubled dialog if the grid size is 6x6
                if (gridSize == 6) {
                    showBombsDoubledDialog();
                    playNotificationSound();
                }
            }
        });

        bombSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playSelectSound();
                String selected = bombSelectBox.getSelected();
                int discount = Integer.parseInt(selected.replace("%", ""));
                GameManager.getInstance().setDiscount(discount); // Save the selected discount
                updateBoard((int) Math.sqrt(GameManager.getInstance().getGridSize()), calculateNumberOfMines(selected));
            }
        });

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

        Table rulesTable = rulesHud();
        rulesTable.setPosition(GameConfig.WIDTH - rulesTable.getWidth() - 20, GameConfig.HEIGHT - rulesTable.getHeight() - 20);
        stage.addActor(rulesTable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.5f, 0.7f, 0.9f, 1);

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
            diamondSound.play(0.3f);
        }
    }

    private void playBombSound() {
        if (GameManager.getInstance().isSoundEffectsEnabled()) {
            bombSound.play(0.3f);
        }
    }

    private void playNotificationSound() {
        if (GameManager.getInstance().isSoundEffectsEnabled()) {
            notificationSound.play(0.3f);
        }
    }

    private void playWinSound() {
        if (GameManager.getInstance().isSoundEffectsEnabled()) {
            winSound.play(0.3f);
        }
    }
}
