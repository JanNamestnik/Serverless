package si.um.feri.serverless.screen;

import com.badlogic.gdx.ScreenAdapter;

import si.um.feri.serverless.DiscountGame;

public class GameScreen extends ScreenAdapter {

    private final DiscountGame game;

    public GameScreen(DiscountGame game) {
        this.game = game;
    }
}
