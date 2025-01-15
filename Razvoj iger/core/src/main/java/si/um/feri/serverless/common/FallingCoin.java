package si.um.feri.serverless.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;

public class FallingCoin extends Actor {
    private TextureRegion coinTexture;
    private float speed;

    public FallingCoin(TextureRegion coinTexture, float speed) {
        this.coinTexture = coinTexture;
        this.speed = speed;
        resetPosition();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setY(getY() - speed * delta); // Premik navzdol
        if (getY() + getHeight() < 0) {
            resetPosition(); // Ponastavi pozicijo, če kovanec izgine iz zaslona
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(coinTexture, getX(), getY(), getWidth(), getHeight());
    }

    private void resetPosition() {
        setX((float) (Math.random() * (Gdx.graphics.getWidth() - getWidth()))); // Naključna X pozicija
        setY(Gdx.graphics.getHeight()); // Ponastavi na vrh zaslona
        setSize(50, 50); // Velikost kovanca
    }
}
