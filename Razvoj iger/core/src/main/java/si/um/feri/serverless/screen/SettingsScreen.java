package si.um.feri.serverless.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import si.um.feri.serverless.DiscountGame;
import si.um.feri.serverless.assets.AssetDescriptors;
import si.um.feri.serverless.assets.AssetManager;
import si.um.feri.serverless.common.GameManager;
import si.um.feri.serverless.config.GameConfig;

public class SettingsScreen extends ScreenAdapter {

    private final DiscountGame game;
    private final AssetManager assetManager;

    private Preferences preferences;

    private Viewport viewport;

    private Skin skin_alternative;

    private Stage stage;

    private Sound settingsPick;

    private Sound mouseClick;

    private Texture backgroundTexture;

    public SettingsScreen(DiscountGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
        preferences = Gdx.app.getPreferences("game_settings");
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        settingsPick = assetManager.getPickSound(AssetDescriptors.MENU_PICK);
        mouseClick = assetManager.getMouseClickSound(AssetDescriptors.MOUSE_CLICK);
        skin_alternative = assetManager.getSkin(AssetDescriptors.UI_SKIN_ALTERNATIVE);

        backgroundTexture = new Texture(Gdx.files.internal("assets/backgroundMenu.png"));

        stage.addActor(createUi());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);

        game.getBatch().setProjectionMatrix(viewport.getCamera().combined);
        game.getBatch().begin();
        game.getBatch().draw(backgroundTexture, 0, 0, GameConfig.WIDTH, GameConfig.HEIGHT);
        game.getBatch().end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private Actor createUi() {
        Table table = new Table();
        table.defaults().pad(10);
        table.setFillParent(true);

        // Naslov
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = assetManager.get(AssetDescriptors.UI_FONT_INTRO);
        Label titleLabel = new Label("Settings", titleStyle);
        table.add(titleLabel).colspan(2).center().padBottom(20).row();

        // Naslov: Sound settings
        Label soundSettingsLabel = new Label("Sound settings:", skin_alternative, "default");
        table.add(soundSettingsLabel).colspan(2).left().padBottom(10).row();

        // Možnost: Enable Music
        CheckBox musicCheckBox = new CheckBox("Enable Music", skin_alternative);
        musicCheckBox.setChecked(GameManager.getInstance().isMusicEnabled());

        musicCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playSelectSound();
                boolean isChecked = musicCheckBox.isChecked();
                GameManager.getInstance().setMusicEnabled(isChecked);
            }
        });

        table.add(musicCheckBox).colspan(2).left().padBottom(10).row();

        // Možnost: Enable Sounds
        CheckBox soundCheckBox = new CheckBox("Enable Sounds", skin_alternative);
        soundCheckBox.setChecked(GameManager.getInstance().isSoundEffectsEnabled());

        soundCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playSelectSound();
                boolean isChecked = soundCheckBox.isChecked();
                GameManager.getInstance().setSoundEffectsEnabled(isChecked);
            }
        });

        table.add(soundCheckBox).colspan(2).left().padBottom(20).row();

        // Gumb za vrnitev v meni
        TextButton backButton = new TextButton("Back to Menu", skin_alternative);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playButtonSound();
                game.setScreen(new MenuScreen(game));
            }
        });

        table.add(backButton).colspan(2).center().padTop(10); // Manjši razmik zgoraj

        return table;
    }

    private void playButtonSound() {
        if (GameManager.getInstance().isSoundEffectsEnabled()) {
            settingsPick.play(0.3f);
        }
    }

    private void playSelectSound() {
        if (GameManager.getInstance().isSoundEffectsEnabled()) {
            mouseClick.play(0.3f);
        }
    }
}
