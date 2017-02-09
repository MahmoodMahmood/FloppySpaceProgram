/*
* Mahmood Yahya
 * December 24th, 2014
 * This class is in charge of controlling the program when the game is being played
 */
package screens;

import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import gameworld.GameRenderer;
import gameworld.GameWorld;
import FSPHelpers.InputHandler;

public class GameScreen implements Screen {

	private GameWorld world;// Declare an instance of Game World
	private GameRenderer renderer;// Declare an instance of Game Renderer
	private InputHandler iH;//Declare an instance of inputHandler
	private float runTime = 0;

	/*
     * @Param none
     * @Return gameScreen object
     * This method constructs a gameScreen
     */
	public GameScreen() {
		Gdx.app.log("GameScreen", "Attached");
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 136;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		int midPointY = (int) (gameHeight / 2);
		int midPointX = (int) (gameWidth / 2);

		world = new GameWorld(midPointX, midPointY); // initialize world
		renderer = new GameRenderer(world, (int) gameHeight, midPointX,
				midPointY); // initialize renderer
		iH = new InputHandler(world, 0);
		Gdx.input.setInputProcessor(iH);
	}

	/*
	 * @Param float delta
	 * @Return none This method updates the whole game
	 * This method renders the game on a high level and also manages the control switch between
	 */
	public void render(float delta) {
		if (iH.checkClicked()) {//Check if the game has been clicked recently
			resetCounter();//If yes then reset counter
		} else if ((renderer.getCounter() * delta > 5) && allMoving()) {
			//If game has not been clicked for over 5 seconds and all the birds are not moving 
			//then create a new bird and reset then renderer counter
			world.addBird();
			renderer.setCounter(0);
			Gdx.app.log("GameScreen ", "bird created");
			iH.setControlled(world.getBirdsArray().size() - 1);
			Gdx.input.setInputProcessor(iH);
		}		
		world.update(delta);
		// Check if a bird needs to be removed due to collision or out of bounds
		// and make a new bird and give control to it
		if (world.getToRemove() >= 0) {
			world.getBirdsArray().set(world.getToRemove(), null);
			world.getBirdsArray().removeAll(Collections.singleton(null));
			world.getBirdsArray().trimToSize();
			world.addBird();
			renderer.setCounter(0);
			Gdx.app.log("GameScreen ", "bird created");
			iH.setControlled(world.getBirdsArray().size() - 1);
			Gdx.input.setInputProcessor(iH);
			world.setToRemove(-1);
		}

		if (world.switchControl) {
			iH.setControlled(world.getBirdsArray().size() - 1);
			Gdx.input.setInputProcessor(iH);
		}
		runTime += delta;
		renderer.render(runTime);
	}

	/*
	 * @Param none
	 * 
	 * @Return boolean all moving This method determines if all the birds in the
	 * game are moving or if one of them is at 0 velocity
	 */
	public boolean allMoving() {
		for (int i = 0; i < world.getBirdsArray().size(); i++) {
			if (!world.getBirdsArray().get(i).isMoving()) {
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	public void resize(int width, int height) {
		Gdx.app.log("GameScreen", "resizing");
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#show()
	 */
	public void show() {
		Gdx.app.log("GameScreen", "show called");
	}

	private void resetCounter() {
		renderer.setCounter(0);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	public void hide() {
		Gdx.app.log("GameScreen", "hide called");
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	public void pause() {
		Gdx.app.log("GameScreen", "pause called");
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	public void resume() {
		Gdx.app.log("GameScreen", "resume called");
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	public void dispose() {
		// Leave blank
	}

}
