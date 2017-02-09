/*
 * Mahmood Yahya
 * December 24th, 2014
 * This class is in charge of the actual gameplay of the game.
 */
package gameworld;

import java.util.ArrayList;
import java.util.Collections;

import FSPHelpers.AssetLoader;

import com.badlogic.gdx.Gdx;

import gameobjects.Bird;
import gameobjects.Planet;

public class GameWorld {

	//Declare variables
	int midPointX, midPointY, birdStartY, toRemove;
	private ArrayList<Bird> birds = new ArrayList<Bird>();
	private Planet planet;
	private int score = -1;
	public boolean switchControl;

	/*
	 * @Param int midPointX, int midPointY
	 * @Return GameWorld object
	 * This method constructors a gameWorld object
	 */
	public GameWorld(int midPointX, int midPointY) {
		// Initialize planet
		planet = new Planet(midPointX, midPointY);
		// Initialize bird
		birdStartY = midPointY - planet.getRadius();
		this.midPointX = midPointX;
		this.midPointY = midPointY;
		birds.add(new Bird(midPointX, birdStartY, (midPointX / 4), (int) Math
				.ceil(midPointY / 3.75), midPointX, midPointY));
	}

	/*
	 * @Param float delta
	 * @Return none
	 * This method constructs a method
	 */			 
	public void update(float delta) {
		Gdx.app.log("GameWorld", "update");
		if (birds.size() > 1) {//Check if any birds are colliding if there is more than one bird on the field
			deleteColliding();
		}
		for (int i = 0; i < birds.size(); i++) {//Do that math and stuff
			if (!(birds.get(i)).onSurface(planet)) {
				(birds.get(i)).applyForce();
				(birds.get(i)).update(delta, planet);
				(birds.get(i)).onSurface(planet);
			}

			if (!birds.get(i).inBounds()) {// Check if the bird is out of bounds
				birds.set(i, null);// Delete the bird
				// Go through a loop to decrease all the birds by 1
				for (int j = i; i < birds.size() - 1; i++) {
					birds.set(j, birds.get(j + 1));
				}
				toRemove = i;
				score--;
				// AssetLoader.dead.play();
			}
		}
	}

	/*
	 * @Param none
	 * @Return none
	 * This method checks if any 2 birds are colliding
	 */
	public void deleteColliding() {
		for (int i = 0; i < birds.size(); i++) {
			for (int j = i; j < birds.size(); j++) {
				if (birds.get(i).isColliding(birds.get(j))) {
					birds.set(i, null);
					birds.set(j, null);
					score -= 2;
					if (i == birds.size() - 1 || j == birds.size() - 1) {
						addBird();
					}
					i = 9999;
					j = 9999;
				}
			}
		}

		//Remove all null birds
		getBirdsArray().removeAll(Collections.singleton(null));
		getBirdsArray().trimToSize();
		switchControl = true;
	}
	//Getter
	public int getScore() {
		return score;
	}

	/*
	 * @Param none
	 * @Return none
	 * This method increments current score
	 */
	public void addScore(int increment) {
		score += increment;
	}

	//getter
	public int getToRemove() {
		return toRemove;
	}

	//setter
	public void setToRemove(int toRemove) {
		this.toRemove = toRemove;
	}
	
	//getter
	public ArrayList<Bird> getBirdsArray() {
		return birds;
	}

	/*
	 * @Param none
	 * @Return none
	 * This method adds a new bird
	 */
	public void addBird() {
		birds.add(new Bird(midPointX, birdStartY, (midPointX / 4), (int) Math
				.ceil(midPointY / 3.75), midPointX, midPointY));
		score++;//increment score
		AssetLoader.setHighScore(score);
	}

	/*
	 * @Param none
	 * @Return none
	 * This method adds a new bird
	 */
	public void addBird(float x, float y, int width, int height, int midX,
			int midY) {
		birds.add(new Bird(x, y, width, height, midX, midY));
		score++;//increment score
		AssetLoader.setHighScore(score);
	}

	//getter
	public Bird getBird(int i) {
		return birds.get(i);
	}

	//getter
	public Planet getPlanet() {
		return planet;
	}
}