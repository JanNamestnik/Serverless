package si.um.feri.serverless.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;

public class AssetManager {
    private final com.badlogic.gdx.assets.AssetManager assetManager;

    public AssetManager() {
        this.assetManager = new com.badlogic.gdx.assets.AssetManager();
    }

    public void loadAllAssets(){
        assetManager.load(AssetPaths.GAMEPLAY_ATLAS, TextureAtlas.class);
        assetManager.load(AssetDescriptors.UI_FONT);
        assetManager.load(AssetDescriptors.UI_FONT_INTRO);
        assetManager.load(AssetPaths.UI_SKIN, Skin.class);
        assetManager.load(AssetPaths.UI_SKIN_ALTERNATIVE, Skin.class);
        assetManager.load(AssetPaths.DIAMOND_SOUND, Sound.class);
        assetManager.load(AssetPaths.MENU_PICK_SOUND, Sound.class);
        assetManager.load(AssetPaths.BOMB_SOUND, Sound.class);
    }

    public TextureAtlas getGameplayAtlas() {
        return assetManager.get(AssetPaths.GAMEPLAY_ATLAS);
    }

    public BitmapFont get(AssetDescriptor<BitmapFont> uiFont) {
        return assetManager.get(uiFont);
    }

    public BitmapFont getIntroFont(AssetDescriptor<BitmapFont> uiFontIntro) {
        return assetManager.get(uiFontIntro);
    }

    public Skin getSkin(AssetDescriptor<Skin> uiSkin) {
        return assetManager.get(uiSkin);
    }

    public Skin getAlternativeSkin(AssetDescriptor<Skin> uiSkinAlternative) {
        return assetManager.get(uiSkinAlternative);
    }
    public Sound getDiamondSound() {
        return assetManager.get(AssetPaths.DIAMOND_SOUND);
    }

    public Logger getLogger() {
        return assetManager.getLogger();
    }

    public Sound getMenuPickSound() {
        return assetManager.get(AssetPaths.MENU_PICK_SOUND);
    }

    public Sound getBombSound() {
        return assetManager.get(AssetPaths.BOMB_SOUND);
    }

    public void dispose() {
        assetManager.dispose();
    }

    public void finishLoading() {
        assetManager.finishLoading();
    }
}
