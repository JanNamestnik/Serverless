package si.um.feri.serverless.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import si.um.feri.serverless.DiscountGame;
import si.um.feri.serverless.assets.AssetDescriptors;
import si.um.feri.serverless.assets.AssetManager;
import si.um.feri.serverless.common.GameManager;
import si.um.feri.serverless.config.GameConfig;

import java.util.List;

public class CollectionScreen extends ScreenAdapter {
    private final DiscountGame game;
    private final AssetManager assetManager;
    private Stage stage;
    private Viewport viewport;
    private Texture backgroundTexture;
    private Skin skin;
    private Sound mouseClick;
    private Skin skin_alternative;

    public CollectionScreen(DiscountGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        stage = new Stage(viewport, game.getBatch());
        skin = assetManager.getSkin(AssetDescriptors.UI_SKIN);
        skin_alternative = assetManager.getSkin(AssetDescriptors.UI_SKIN_ALTERNATIVE);
        mouseClick = assetManager.getMouseClickSound(AssetDescriptors.MOUSE_CLICK);

        backgroundTexture = new Texture(Gdx.files.internal("assets/backgroundMenu.png"));

        stage.addActor(createUI());
        Gdx.input.setInputProcessor(stage);
    }

    private Actor createUI() {
        Table table = new Table(skin);
        table.setFillParent(true);

        // Title label style with a different font
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = assetManager.get(AssetDescriptors.UI_FONT_INTRO);

        Label titleLabel = new Label("Collected Discounts", titleStyle);
        table.add(titleLabel).center().padBottom(45).row();

        List<GameManager.Result> discounts = GameManager.getInstance().getCollectedDiscounts();
        for (GameManager.Result result : discounts) {
            Table rowTable = new Table(skin);

            Label.LabelStyle discountTypeStyle = new Label.LabelStyle();
            discountTypeStyle.font = assetManager.get(AssetDescriptors.HUD_FONT);

            Label discountTypeLabel = new Label(result.getDiscountType(), discountTypeStyle);
            Label discountCountLabel = new Label("   : " + result.getCount() + " collected", skin);

            Container<Label> discountCountContainer = new Container<>(discountCountLabel);
            discountCountContainer.width(200);

            rowTable.add(discountTypeLabel).left();
            rowTable.add(discountCountContainer).right();

            table.add(rowTable).left().padLeft(56).center().padBottom(10).row();
        }

        // Add "Back to Menu" button with skin_alternative
        TextButton backButton = new TextButton("Back to Menu", skin_alternative);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playSelectSound();
                game.setScreen(new MenuScreen(game));
            }
        });

        table.add(backButton).center().padTop(20);

        return table;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);

        game.getBatch().begin();
        game.getBatch().draw(backgroundTexture, 0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
        game.getBatch().end();

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
