package si.um.feri.serverless;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

public class GameWrapper implements ApplicationListener {

    private ApplicationListener currentListener;
    private final ApplicationAdapter initialAdapter;
    private final Game game;

    public GameWrapper(ApplicationAdapter initialAdapter, Game game) {
        this.initialAdapter = initialAdapter;
        this.game = game;
        this.currentListener = initialAdapter;
    }

    @Override
    public void create() {
        currentListener.create();
    }

    @Override
    public void resize(int width, int height) {
        currentListener.resize(width, height);
    }

    @Override
    public void render() {
        currentListener.render();
    }

    @Override
    public void pause() {
        currentListener.pause();
    }

    @Override
    public void resume() {
        currentListener.resume();
    }

    @Override
    public void dispose() {
        currentListener.dispose();
    }

    public void switchToGame() {
        currentListener.dispose();
        currentListener = game;
        currentListener.create();
    }

    public void switchToApplicationAdapter() {
        currentListener.dispose(); // Sprosti trenutni Game
        currentListener = initialAdapter; // Nastavi Map kot trenutni listener
        currentListener.create(); // Inicializacija Map
    }
    public Game getGame() {
        return game;
    }
}


