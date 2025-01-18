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
        assetManager.load(AssetDescriptors.MAP_FONT);
        assetManager.load(AssetDescriptors.UI_FONT_INTRO);
        assetManager.load(AssetDescriptors.CONTINUE_FONT);
        assetManager.load(AssetDescriptors.CONTINUE_INTRO_TITLE_FONT);
        assetManager.load(AssetPaths.UI_SKIN, Skin.class);
        assetManager.load(AssetPaths.UI_SKIN_ALTERNATIVE, Skin.class);
        assetManager.load(AssetPaths.DIAMOND_SOUND, Sound.class);
        assetManager.load(AssetPaths.MENU_PICK, Sound.class);
        assetManager.load(AssetPaths.WIN_SOUND, Sound.class);
        assetManager.load(AssetPaths.MENU_MUSIC, Music.class);
        assetManager.load(AssetPaths.MOUSE_CLICK, Sound.class);
        assetManager.load(AssetPaths.NOTIFICATION_SOUND, Sound.class);
        assetManager.load(AssetPaths.BOMB_SOUND, Sound.class);
    }

    public TextureAtlas getGameplayAtlas() {
        return assetManager.get(AssetPaths.GAMEPLAY_ATLAS);
    }

    public BitmapFont get(AssetDescriptor<BitmapFont> uiFont) {
        return assetManager.get(uiFont);
    }

    public BitmapFont getMapFont(AssetDescriptor<BitmapFont> mapFont) {
        return assetManager.get(mapFont);
    }

    public BitmapFont getIntroFont(AssetDescriptor<BitmapFont> uiFontIntro) {
        return assetManager.get(uiFontIntro);
    }


    public BitmapFont getContinueFont(AssetDescriptor<BitmapFont> continueFont) {
        return assetManager.get(continueFont);
    }

    public BitmapFont getContinueIntroTitleFont(AssetDescriptor<BitmapFont> continueIntroTitleFont) {
        return assetManager.get(continueIntroTitleFont);
    }
    public Skin getSkin(AssetDescriptor<Skin> uiSkin) {
        return assetManager.get(uiSkin);
    }

    public Skin getAlternativeSkin(AssetDescriptor<Skin> uiSkinAlternative) {
        return assetManager.get(uiSkinAlternative);
    }
    public Sound getDiamondSound(AssetDescriptor<Sound> diamondSound) {
        return assetManager.get(AssetPaths.DIAMOND_SOUND);
    }

    public Sound getWinSound(AssetDescriptor<Sound> winSound) {
        return assetManager.get(AssetPaths.WIN_SOUND);
    }


    public Logger getLogger() {
        return assetManager.getLogger();
    }

    public Sound getPickSound(AssetDescriptor<Sound> menuPick) {
        return assetManager.get(menuPick);
    }
    public Sound getNotificationSound(AssetDescriptor<Sound> notificationSound) {
        return assetManager.get(notificationSound);
    }

    public Sound getMouseClickSound(AssetDescriptor<Sound> mouseClick) {
        return assetManager.get(mouseClick);
    }

    public Music getMenuMusic(AssetDescriptor<Music> menuMusic) {
        return assetManager.get(menuMusic);
    }

    public Sound getBombSound(AssetDescriptor<Sound> bombSound) {
        return assetManager.get(AssetPaths.BOMB_SOUND);
    }

    public void dispose() {
        assetManager.dispose();
    }

    public void finishLoading() {
        assetManager.finishLoading();
    }
}
