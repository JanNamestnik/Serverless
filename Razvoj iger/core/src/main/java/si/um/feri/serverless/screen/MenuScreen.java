package si.um.feri.serverless.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import si.um.feri.serverless.DiscountGame;

public class MenuScreen extends ScreenAdapter {

    private final DiscountGame game;

    public MenuScreen(DiscountGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        // Render logika za MenuScreen
        SpriteBatch batch = game.getBatch();
        batch.begin();
        // Dodaj svojo logiko risanja tukaj
        batch.end();
    }

    @Override
    public void dispose() {
        // Počisti vire zaslona
    }
}
