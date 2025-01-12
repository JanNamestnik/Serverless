// IntroScreen.java
package si.um.feri.serverless.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
import si.um.feri.serverless.config.GameConfig;

public class IntroScreen extends ScreenAdapter {

    private final DiscountGame game;
    private final AssetManager assetManager;
    private Viewport viewport;
    private Stage stage;
    private Skin skin;

    public IntroScreen(DiscountGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        skin = assetManager.getSkin(AssetDescriptors.UI_SKIN);

        Table table = createUi();
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    private Table createUi() {
        Table table = new Table(skin);
        table.defaults().pad(10);
        table.setFillParent(true);

        // Create a LabelStyle with the custom font
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = assetManager.get(AssetDescriptors.UI_FONT);

        // Create a Label with the text "Welcome to our game!" and the custom LabelStyle
        Label titleLabel = new Label("Welcome to our game!", titleStyle);
        table.add(titleLabel).colspan(2).center().padBottom(20).row();

        // Create a custom TextButtonStyle
        TextButton.TextButtonStyle startButtonStyle = new TextButton.TextButtonStyle();
        startButtonStyle.font = skin.getFont("font");
        startButtonStyle.fontColor = Color.GREEN; // Set the desired color

        // Create the "Start the Game" button with the custom style
        TextButton startButton = new TextButton("Start the Game", startButtonStyle);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Logika za začetek igre (če je potrebno)
            }
        });

        // Add bouncing animation to the "Start the Game" button
        startButton.addAction(Actions.forever(
            Actions.sequence(
                Actions.moveBy(0, 10, 0.5f),
                Actions.moveBy(0, -10, 0.5f)
            )
        ));

        Label descriptionLabel = new Label(
            "In DiscountGame, you can play games to earn discounts on events shown on the map.\n\n" +
                "Have fun and save money!", // Skok v novo vrstico z uporabo \n
            skin, "default"
        );
        descriptionLabel.setWrap(true); // Omogoči zavijanje besedila, če je preširoko

        table.add(descriptionLabel).colspan(2).center().width(400).padBottom(20).row();


        // Create a custom TextButtonStyle
        TextButton.TextButtonStyle mapButtonStyle = new TextButton.TextButtonStyle();
        mapButtonStyle.font = skin.getFont("font");
        mapButtonStyle.fontColor = Color.RED; // Set the desired color

        // Create the "Back to Map" button
        TextButton backButton = new TextButton("Back to Map", mapButtonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Preklop nazaj na Map
                GameWrapper wrapper = (GameWrapper) Gdx.app.getApplicationListener();
                wrapper.switchToApplicationAdapter();
            }
        });

        // Add buttons to the table
        table.add(startButton).padRight(20);
        table.add(backButton).row();

        return table;
    }

    @Override
    public void render(float delta) {
        // Clear screen with black color
        ScreenUtils.clear(0, 0, 0, 1);
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
