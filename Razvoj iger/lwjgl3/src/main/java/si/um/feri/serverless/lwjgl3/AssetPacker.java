package si.um.feri.serverless.lwjgl3;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {
    private static final boolean DRAW_DEBUG_OUTLINE = false;
    private static final String RAW_ASSETS_PATH = "lwjgl3/assets-raw";
    private static final String ASSETS_PATH = "core/assets";

    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.debug = DRAW_DEBUG_OUTLINE;

        System.out.println("Starting texture packing...");
        System.out.println("Raw assets path: " + RAW_ASSETS_PATH + "/gameplay");
        System.out.println("Output assets path: " + ASSETS_PATH + "/gameplay");

        TexturePacker.process(settings,
            RAW_ASSETS_PATH + "/gameplay",
            ASSETS_PATH + "/gameplay",
            "gameplay"
        );

        System.out.println("Packing complete.");
    }


}
