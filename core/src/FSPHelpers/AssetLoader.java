/*
 *  Tyler Stelmack/ Mahmood Yahya
 *  January 5th, 2014
 *  This class loads the assets in the game
 */
package FSPHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	public static BitmapFont font, shadow;
	public static Texture texture, bg, border;
	public static Animation birdAnimation;
	public static Preferences prefs;
	public static TextureRegion bird, birdDown, birdUp, planet;
	private static int scores[];

	/*
	 * @Param none
	 * @Return none
	 * This methods loads all the assets
	 */
	public static void load() {
		prefs = Gdx.app.getPreferences("FloppySpaceProgram");
		if (!prefs.contains("highScore1")) {
			for (int i = 1; i <= 5; i++) {
				prefs.putInteger("highScore" + i, 0);

			}
		}
		scores = new int[5];
		texture = new Texture(Gdx.files.internal("data/Sprites.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		birdUp = new TextureRegion(texture, 0, 136, 17, 12);
		birdUp.flip(false, true);

		birdDown = new TextureRegion(texture, 0, 148, 17, 12);
		birdDown.flip(false, true);

		bird = new TextureRegion(texture, 0, 160, 17, 12);
		bird.flip(false, true);

		TextureRegion[] birds = {birdDown, bird, birdUp};
		birdAnimation = new Animation(0.06f, birds);
		birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		planet = new TextureRegion(texture, 0, 0, 136, 136);
		planet.flip(false, true);
		
		bg = new Texture(Gdx.files.internal("data/Background.png"));
		border = new Texture(Gdx.files.internal("data/Boundary.png"));

		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.setScale(.1f, -.1f);
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
		shadow.setScale(.1f, -.1f);
	}

	/*
	 * @Param value score 
	 * @Return none 
	 * This method puts
	 */
	public static void setHighScore(int val) {
		if (prefs.getInteger("highscore5") < val) {			
			prefs.putInteger("highscore5", val);
		}
		for (int i = 1; i <= 5; i++) {
			scores[i - 1] = prefs.getInteger("highscore" + i);
		}		
		sortScore(0, scores.length - 1);

		for (int i = 1; i <= 5; i++) {
			prefs.putInteger("highscore" + (i), scores[i - 1]);
		}
		prefs.flush();
	}

	//getter
	public static int getHighScore() {
		return prefs.getInteger("highscore1");
	}
    /*
     * @Param left, right
     * @Return void
     * sorts the scores using recurssion
     */
    private static void sortScore(int left, int right) {
        int l, r, p;//ints
        l = left;//l equals left
        r = right;//r equals right
        p = scores[(left + right) / 2];//create the pivot in the middle of the array
        while (l <= r) {//loop while l is less than or equal to r
            while (scores[l] > p) {//loop while the score at l is larger then pivot
                l++;//increace l
            }
            while (scores[r] < p) {//loop while the score at r is less then the pivot
                r--;//decreace r
            }
            int temp;//create a temporary variable
            if (l <= r) {//if l is less than or equal to r
                temp = scores[l];//temp equals the score at l
                scores[l] = scores[r];//the score at l equals the score at r
                scores[r] = temp;//the score at r equals the temp
                l++;//increace l
                r--;//decrease r
            }
        }
        if (left < l - 1) {//if left is smaller then l-1
            sortScore(left, l - 1);//recall the method with left and l-1
        }
        if (l < right) {//if l is less than right
            sortScore(l, right);//recall the method with l and right
        }
    }

    /*
     * @Param none
     * @Return none
     * This method disposes assets
     */
	public static void dispose() {
		font.dispose();
		shadow.dispose();
		texture.dispose();
		bg.dispose();
		border.dispose();
	}
}