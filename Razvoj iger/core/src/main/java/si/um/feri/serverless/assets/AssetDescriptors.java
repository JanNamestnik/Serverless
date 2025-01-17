package si.um.feri.serverless.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> UI_FONT =
        new AssetDescriptor<>(AssetPaths.UI_FONT, BitmapFont.class);

    public static final AssetDescriptor<BitmapFont> UI_FONT_INTRO =
        new AssetDescriptor<>(AssetPaths.UI_FONT_INTRO, BitmapFont.class);

    public static final AssetDescriptor<BitmapFont> CONTINUE_FONT =
        new AssetDescriptor<>(AssetPaths.CONTINUE_FONT, BitmapFont.class);

    public static final AssetDescriptor<BitmapFont> CONTINUE_INTRO_TITLE_FONT =
        new AssetDescriptor<>(AssetPaths.CONTINUE_INTRO_TITLE_FONT, BitmapFont.class);

    public static final AssetDescriptor<BitmapFont> MAP_FONT =
        new AssetDescriptor<>(AssetPaths.MAP_FONT, BitmapFont.class);
    public static final AssetDescriptor<TextureAtlas> GAMEPLAY_ATLAS =
        new AssetDescriptor<>(AssetPaths.GAMEPLAY_ATLAS, TextureAtlas.class);
    public static final AssetDescriptor<Skin> UI_SKIN =
        new AssetDescriptor<Skin>(AssetPaths.UI_SKIN, Skin.class);
    public static final AssetDescriptor<Skin> UI_SKIN_ALTERNATIVE =
        new AssetDescriptor<Skin>(AssetPaths.UI_SKIN_ALTERNATIVE, Skin.class);
    public static final AssetDescriptor<Sound> DIAMOND_SOUND =
        new AssetDescriptor<>(AssetPaths.DIAMOND_SOUND, Sound.class);

    public static final AssetDescriptor<Sound> MENU_PICK =
        new AssetDescriptor<>(AssetPaths.MENU_PICK, Sound.class);

    public static final AssetDescriptor<Music> MENU_MUSIC =
        new AssetDescriptor<>(AssetPaths.MENU_MUSIC, Music.class);

    public static final AssetDescriptor<Sound> MOUSE_CLICK =
        new AssetDescriptor<>(AssetPaths.MOUSE_CLICK, Sound.class);

    public static final AssetDescriptor<Sound> BOMB_SOUND =
        new AssetDescriptor<>(AssetPaths.BOMB_SOUND, Sound.class);

}
