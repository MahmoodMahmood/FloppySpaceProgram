/*
 * Mahmood Yahya
 * December 24th, 2014
 * This class is in charge of rendering the information in the game to actual game output
 */
package gameworld;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gameobjects.Bird;
import FSPHelpers.AssetLoader;

public class GameRenderer {

	// Declares variables
	private GameWorld myWorld;// Declare an instance of the GameWorld class
	private OrthographicCamera cam;
	private SpriteBatch batcher;
	private int counter = 0;
	private int midX, midY;
	private TextureRegion planet;
	private Texture bg, border;
	private Animation bird;

	// Getter
	public int getCounter() {
		return counter;
	}

	// Setter
	public void setCounter(int counter) {
		this.counter = counter;
	}

	// Game objects
	private ArrayList<Bird> birds = new ArrayList<Bird>();

	/*
	 * @Param GameWorld object, int gameHeight, int midX, int midY	 * 
	 * @Return GameRenderer object 
	 * This method constructs gameRenderer
	 */
	public GameRenderer(GameWorld world, int gameHeight, int midX, int midY) {
		myWorld = world;// Initialize myWorld to a passed in parameter
		this.midX = midX;
		this.midY = midY;
		cam = new OrthographicCamera();
		cam.setToOrtho(true, 136, gameHeight);
		// play = new Button();
		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);

		// Call helper methods to initialize instance variables
		initGameObjects();
		initAssets();
	}

	/*
	 * @Param float runTime 
	 * @Return none
	 * This method draws everything every frame
	 */
	public void render(float runTime) {
		counter++;// Add one to the number of frames passed since last reset
		// Reset color
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batcher.begin();// Starts the batcher
		batcher.enableBlending();
		batcher.draw(bg, 0, 0, midX * 2, midY * 2);

		drawBird(runTime);
		drawPlanet();
		drawScore();

		batcher.end();// End batcher
		Gdx.app.log("GameRenderer", "render");
	}

	/*
	 * @Param none 
	 * @Return none 
	 * This method draws the planet
	 */
	private void drawPlanet() {
		batcher.draw(planet, midX - myWorld.getPlanet().getRadius(), midY
				- myWorld.getPlanet().getRadius(), myWorld.getPlanet()
				.getRadius() * 2, myWorld.getPlanet().getRadius() * 2);

		batcher.draw(border, midX - 48, -2, 96, midY * 2 + 4);

	}

	/*
	 * @Param none
	 * @Return none
	 * This method draws the bird
	 */
	private void drawBird(float runTime) {
		for (int i = 0; i < birds.size(); i++) {

			batcher.draw(bird.getKeyFrame(runTime), birds.get(i)
					.getBoundingCircle().x
					- birds.get(i).getBoundingCircle().radius, birds.get(i)
					.getBoundingCircle().y
					- birds.get(i).getBoundingCircle().radius, birds.get(i)
					.getBoundingCircle().radius, birds.get(i)
					.getBoundingCircle().radius, birds.get(i).getWidth() / 4,
					birds.get(i).getHeight() / 4, 1, 1, birds.get(i)
							.getRotation());
		}
	}

	/*
	 * @Param none 
	 * @Return none
	 * This method draws the current score This method draws the
	 * current score
	 */
	private void drawScore() {
		//Draw the current score
		String score = "" + String.valueOf(myWorld.getScore());
		AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(), midX
				- ((3 * score.length()) / 2), midY);
		batcher.setProjectionMatrix(cam.combined);
		AssetLoader.font.draw(batcher, "" + myWorld.getScore(), midX
				- ((3 * score.length()) / 2) - 1, midY - 1);
		
		//Draws the current
		AssetLoader.shadow.draw(batcher, "" + "Highscore:", (midX * 2) - 40, 5);
		AssetLoader.font.draw(batcher, "" + "Highscore:", (midX * 2) - 40, 6);
		AssetLoader.shadow.draw(batcher, "" + AssetLoader.getHighScore(),
				(midX * 2) - 5, 14);
		AssetLoader.font.draw(batcher, "" + AssetLoader.getHighScore(),
				(midX * 2) - 10, 15);
	}

	/*
	 * @Param none
	 * @Return none
	 * This method initializes the birds arrayList object
	 */
	private void initGameObjects() {
		birds = myWorld.getBirdsArray();
	}

	/*
	 * @Param none
	 * @Return none
	 * This method initializes the texture variables
	 */
	private void initAssets() {
		bg = AssetLoader.bg;
		planet = AssetLoader.planet;
		bird = AssetLoader.birdAnimation;
		border = AssetLoader.border;
	}
}
