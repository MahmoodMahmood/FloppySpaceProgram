/*
 * Mahmood Yahya
 * December 25th, 2014
 * This class takes in and processes input.
 */
package FSPHelpers;

import com.badlogic.gdx.InputProcessor;
import gameobjects.Bird;
import gameworld.GameWorld;

public class InputHandler implements InputProcessor {
	//Declare variables
	private Bird myBird;
	private GameWorld myWorld;
	private int controlled;
	private boolean clicked = false;
	 
	/*
	 * @Param gameWorld object, int i
	 * @Return InputHandler object
	 * This method constructs an input handler
	 */
	public InputHandler(GameWorld myWorld, int i) {
	     // myBird now represents the gameWorld's bird.
	    this.myWorld = myWorld;
	    this.controlled = i;
	    myBird = (Bird) myWorld.getBird(i);
	}

	//Getter
	public GameWorld getMyWorld() {
		return myWorld;
	}
	
	/*
	 * @Param none
	 * @Return boolean
	 * This method checks if this is clicked and if it is it changes it back to false.
	 */
	public boolean checkClicked(){
		if (clicked == true){
			clicked = false;
			return true;
		}
		return false;
	}

	//setter
	public void setMyWorld(GameWorld myWorld) {
		this.myWorld = myWorld;
	}

	//getter
	public int getControlled() {
		return controlled;
	}

	/*
	 * @Param int controlled bird
	 * @Return Bird
	 * This method returns the Bird instance of the bird currents being controlled
	 */
	public void setControlled(int controlled) {
		myBird = (Bird) myWorld.getBird(controlled);
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		clicked = true;
        myBird.onClick();//If any button on the keyboard is pressed then run the onClick method
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		clicked = true;
        myBird.onClick();//If the screen is tapped or the mouse is clicked then run the onClick method
        return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
