/*
 * Mahmood Yahya
 * January 2nd, 2015
 * This class codes for the bird object
 */
package gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import gameobjects.Planet;

public class Bird {
	//Declare variables
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 midPoint;
	private Vector2 dist;
	private Vector2 acceleration;
	private int width, height;
	private float d, initialX, initialY;
	private Circle boundingCircle;
	private float rotation; //For handling bird rotation

	/*
	 * @Param float x, float y, int width, int height, int midX, int midY
	 * @Return Bird type object
	 * This method constructs a bird type object
	 */
	public Bird(float x, float y, int width, int height, int midX, int midY) {
		this.width = width;
		this.height = height;
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		dist = new Vector2(0, 0);
		acceleration = new Vector2(0, 0);
		midPoint = new Vector2(midX, midY);
		boundingCircle = new Circle();
		initialX = x;
		initialY = y;
	}

	/*
	 * @Param float delta, Planet object
	 * @Return none
	 * This method updates the physics of the bird once every frame. REAAAAALY FUNKING HARD!
	 */
	public void update(float delta, Planet planet) {
		d = dist(position.x, position.y, midPoint.x, midPoint.y);//Calculate how far away the bird is
		velocity.add(acceleration.cpy().scl(delta));//Scale velocity by acceleration
		position.add(velocity.cpy().scl(delta));//Scale position by velocity
		boundingCircle.set(position.x, position.y, 2f);//Reset the bounding circle to the bird position
		implementRotations();//Rotate bird
		acceleration.setZero();//Reset acceleration to 0
	}
	/*
	 * @Param none
	 * @Return none
	 * This method calculates the acceleration
	 */
	public void applyForce() {
		dist = position.cpy().sub(midPoint);//Get the pseudo distance between midPoint and position
		d = dist(position, midPoint);//Get the real distance
		dist.nor();//Set the pseudo distance to 1 while maintaining all the angles

		float strength = (float) (10000 / Math.pow(d, 2));//This is the gravity formula scaled 10000 times
		strength *= -1;//Flip the strength
		dist.scl(strength);//Scale the distance (1 with correct angles) by strength
		acceleration.add(dist);//Scale the acceleration by the scaled strength
		acceleration.scl(2);//Multiply acceleration by 2
	}
	
	/*
	 * @Param float delta
	 * @Return none
	 * This method changes the rotation of the bird
	 */
	private void implementRotations(){
		d = dist(position, midPoint);
		float dx = position.x - midPoint.x;
		float dy = position.y - midPoint.y;
		rotation = -(float) Math.toDegrees(Math.atan(dx/dy));
		if (dy > 0){
			rotation += 180;
		}
	}

	/*
	 * @Param 2 pairs of x,y coordinates
	 * @Return distance between them
	 * This method calculates the distance between 2 points on a plane
	 */
	private float dist(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	/*
	 * @Param 2 Vector2 variables
	 * @Return distance between them
	 * This method calculates the distance between 2 points on a plane
	 */
	private float dist(Vector2 a, Vector2 b) {
		return (float) Math.sqrt(Math.pow(a.x - b.x, 2)
				+ Math.pow(a.y - b.y, 2));
	}

	/*
	 * @Param none
	 * @Return none
	 * This method accelerates the bird when user clicks
	 */
	public void onClick() {
		d = dist(position, midPoint);//Calculates how far the bird is from the center
		dist = position.cpy().sub(midPoint);
		float strength = 30;//Strength of the field
		velocity.x += (dist.cpy().x) * strength / d;//Run the opposite of the gravity formula on velocity x
		velocity.y += (dist.cpy().y) * strength / d;//Run the opposite of the gravity formula on velocity y

		//Add a little nudge to force the bird into orbit around the planet
		if (position.y >= midPoint.y) {
			velocity.x -= 5;
			
		} else {
			velocity.x += 5;
		}
		if (position.x > midPoint.x) {
			velocity.y += 5;
		} else {
			velocity.y -= 5;
		}
	}

	//getters
	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public Circle getBoundingCircle() {
		return boundingCircle;
	}

	//setter
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	/*
	 * @Param none
	 * @Return boolean inBounds
	 * This method checks if the bird is currently out of bounds
	 */
	public boolean inBounds() {
		d = dist(position.x, position.y, midPoint.x, midPoint.y);
		return d < 45;
	}

	/*
	 * @Param planet
	 * @Return boolean isInside 
	 * This method checks if the bird is inside the
	 * planet and if it is then it resets the position to start position
	 */
	public boolean onSurface(Planet planet) {
		d = dist(position.x, position.y, midPoint.x, midPoint.y);

		if (d < planet.getRadius()) {
			velocity.setZero();
			position = new Vector2(initialX, initialY);
		}
		return d < planet.getRadius();
	}

	/*
	 * @Param none
	 * @Return boolean isMoving 
	 * This method checks if this bird has a velocity of 0
	 */
	public boolean isMoving() {
		return !(velocity.isZero());
	}

	/*
	 * @Param bird 
	 * @Return boolean is colliding 
	 * This method checks if 2 birds are colliding
	 */
	public boolean isColliding(Bird b) {
		return b.getBoundingCircle().overlaps(boundingCircle) && !this.equals(b);
	}
}
