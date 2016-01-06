/**
 * @file        NumXandOMain.java
 * @author      Abhiroop Tandon (20061667)
 * @assignment  NumericalXandO     
 */
package wit.cgd.numxando;

import wit.cgd.numxando.game.Assets;
import wit.cgd.numxando.game.util.AudioManager;
import wit.cgd.numxando.game.util.GamePreferences;
import wit.cgd.numxando.screens.MenuScreen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;


public class NumXandOMain extends Game {

    @Override
    public void create() {
        // Set Libgdx log level
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        // Load assets
        Assets.instance.init(new AssetManager());
        // Start game at menu screen
        setScreen(new MenuScreen(this));
        
     // Load preferences for audio settings and start playing music
        GamePreferences.instance.load();
        AudioManager.instance.play(Assets.instance.music.song01);
    }

}