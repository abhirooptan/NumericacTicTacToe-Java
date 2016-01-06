/**
 * @file        DesktopLauncher.java
 * @author      Abhiroop Tandon (20061667)
 * @assignment  NumericalXandO     
 */

package wit.cgd.numxando.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

import wit.cgd.numxando.NumXandOMain;

public class DesktopLauncher {
    /*private static boolean  rebuildAtlas        = true;
    private static boolean  drawDebugOutline    = false;*/

    public static void main(String[] args) {
        /*if (rebuildAtlas) {
            Settings settings = new Settings();
            settings.maxWidth = 1024;
            settings.maxHeight = 1024;
            settings.debug = drawDebugOutline;
            TexturePacker.process(settings, "assets-raw/images", "../android/assets/images/",
                    "xando.atlas");
            TexturePacker.process(settings, "assets-raw/images-ui", "../android/assets/images/",
                    "ui.atlas");
        }*/

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "NumXandO";
        config.width = 800;
        config.height = 480;
        new LwjglApplication(new NumXandOMain(), config);
    }
}