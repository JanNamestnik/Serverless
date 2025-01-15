package si.um.feri.serverless.common;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

public class FallingCoinPool extends Pool<FallingCoin> {
    private final TextureRegion coinTexture;

    public FallingCoinPool(TextureRegion coinTexture) {
        this.coinTexture = coinTexture;
    }

    @Override
    protected FallingCoin newObject() {
        return new FallingCoin(coinTexture, 100 + (float) (Math.random() * 100));
    }
}
