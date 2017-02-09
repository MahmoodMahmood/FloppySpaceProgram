/*
 * Mahmood Yahya
 * December 24th, 2014
 * This class is the main class. It starts up the gamescreens and loads up the assests
 */
package FloppySpaceProgram;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import FSPHelpers.AssetLoader;
import screens.GameScreen;

public class FSPGame extends Game {

	@Override
	public void create() {
		Gdx.app.log("FSPGame", "Created");
		AssetLoader.load();//Load all assets using assetLoader class
		setScreen(new GameScreen());//Set the current screen to Gamescreen
	}

	@Override
	public void dispose() {
		AssetLoader.dispose();//Dispose of all the assets in asset loader
		super.dispose();//Dispose of the game class		
	}
}
