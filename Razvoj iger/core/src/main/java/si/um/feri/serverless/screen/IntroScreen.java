// IntroScreen.java
package si.um.feri.serverless.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import si.um.feri.serverless.DiscountGame;
import si.um.feri.serverless.assets.AssetDescriptors;
import si.um.feri.serverless.assets.AssetManager;
import si.um.feri.serverless.assets.RegionNames;
import si.um.feri.serverless.config.GameConfig;

public class IntroScreen extends ScreenAdapter {

    private final DiscountGame game;
    private final AssetManager assetManager;
    private Viewport viewport;
    private Stage stage;
    private Image bomb;
    private Image diamond;
    private Image leafClover;
    private ParticleEffect particleEffect;
    private BitmapFont uiFont;

    public IntroScreen(DiscountGame game) {
        this.game = game;
        this.assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        stage = new Stage(viewport, game.getBatch());

        TextureAtlas gameplayAtlas = assetManager.getGameplayAtlas();
        uiFont = assetManager.get(AssetDescriptors.CONTINUE_INTRO_TITLE_FONT); // Load the UI_FONT

        // Load the images
        bomb = new Image(gameplayAtlas.findRegion(RegionNames.BOMB));
        diamond = new Image(gameplayAtlas.findRegion(RegionNames.DIAMOND));
        leafClover = new Image(gameplayAtlas.findRegion(RegionNames.LEAF_CLOVER));

        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("assets/sparkle.p"), Gdx.files.internal("assets"));
        particleEffect.scaleEffect(0.3f);


        bomb.setPosition(-bomb.getWidth(), GameConfig.HEIGHT / 2 - bomb.getHeight() / 2);
        diamond.setPosition(GameConfig.WIDTH, GameConfig.HEIGHT / 2 - diamond.getHeight() / 2);


        bomb.addAction(Actions.sequence(
            Actions.parallel(
                Actions.moveTo(GameConfig.WIDTH / 2 - bomb.getWidth() / 2, GameConfig.HEIGHT / 2 - bomb.getHeight() / 2, 2f),
                Actions.rotateBy(360, 2f)
            ),
            Actions.run(() -> {
                bomb.remove();
                diamond.remove();
                leafClover.setPosition(GameConfig.WIDTH / 2 - leafClover.getWidth() / 2, GameConfig.HEIGHT / 2 - leafClover.getHeight() / 2);
                stage.addActor(leafClover);

                leafClover.addAction(Actions.sequence(
                    Actions.alpha(0),
                    Actions.parallel(
                        Actions.fadeIn(1.5f), // Fade in
                        Actions.scaleTo(1.5f, 1.5f, 1.5f),
                        Actions.rotateBy(360, 1.5f)
                    ),
                    Actions.delay(1f),
                    Actions.run(() -> game.setScreen(new MenuScreen(game)))
                ));

                // Start the particle effect
                particleEffect.setPosition(
                    leafClover.getX() + leafClover.getWidth() / 2 - 30,
                    leafClover.getY() + leafClover.getHeight() / 2 - 25
                );
                particleEffect.start();
            })
        ));

        diamond.addAction(Actions.parallel(
            Actions.moveTo(GameConfig.WIDTH / 2 - diamond.getWidth() / 2, GameConfig.HEIGHT / 2 - diamond.getHeight() / 2, 2f),
            Actions.rotateBy(360, 2f)
        ));

        // Add actors to the stage
        stage.addActor(bomb);
        stage.addActor(diamond);

        addTitleAnimation();

        Gdx.input.setInputProcessor(stage);
    }

    private void addTitleAnimation() {
        String title = "Loading";
        float startX = GameConfig.WIDTH / 2 - (title.length() * 20) / 2;
        float startY = GameConfig.HEIGHT / 2 + 100;

        // Ustvari "Loading" besedilo
        for (int i = 0; i < title.length(); i++) {
            char letter = title.charAt(i);
            Label label = new Label(String.valueOf(letter), new LabelStyle(uiFont, null));
            label.setPosition(startX + i * 20, startY);
            label.getColor().a = 1; // Vidno
            stage.addActor(label);
        }

        // Dodaj pikice, ki fade-in in fade-out zaporedoma
        float dotsStartX = startX + title.length() * 20 + 10;
        for (int i = 0; i < 3; i++) {
            Label dot = new Label(".", new LabelStyle(uiFont, null));
            dot.setPosition(dotsStartX + i * 15, startY);
            dot.getColor().a = 0; // Začni kot nevidno

            // Dodaj fade-in in fade-out učinek
            dot.addAction(Actions.forever(
                Actions.sequence(
                    Actions.delay(i * 0.5f), // Časovna zamuda za vsak dot
                    Actions.fadeIn(0.5f),
                    Actions.fadeOut(0.5f),
                    Actions.delay(0.5f) // Odmor med cikli
                )
            ));

            stage.addActor(dot);
        }
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0 , 0, 0);


        stage.act(delta);
        stage.draw();

        if (leafClover.getStage() != null) {
            particleEffect.setPosition(
                leafClover.getX() + leafClover.getWidth() / 2 - 30,
                leafClover.getY() + leafClover.getHeight() / 2 - 25
            );

            // Update and draw the particle effect
            if (!particleEffect.isComplete()) {
                particleEffect.update(delta);
                game.getBatch().begin();
                particleEffect.draw(game.getBatch());
                game.getBatch().end();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        particleEffect.dispose();
    }
}
