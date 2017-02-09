/*
 * Mahmood Yahya, Tyler Stelmack
 * January 5th, 2014
 * This class defines a planet object
 */
package gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import gameobjects.Bird;

public class Planet {
	private int radius, midPointX, midPointY;//Declare int variables
	private Circle boundingCircle;//Declare circle bounding circle for collision
	
	/*
	 * @Param: int midPoint in X and midPoint in Y
	 * @Return: Planet Object
	 * This method constructs a planet
	 */
	public Planet(int midPointX,int midPointY){
		this.midPointX = midPointX;
		this.midPointY = midPointY;
		this.radius = (int) Math.ceil((0.2 * Math.min(midPointX, midPointY)));
		boundingCircle = new Circle(midPointX, midPointY, radius);
	}
	
	/*
	 * @Param: none
	 * @Return: Circle object: bounding Circle
	 * This method returns the bounding circle
	 */
	public Circle getBoundingCircle(){
		return boundingCircle;
	}
	
	/*
	 * @Param: Bird object
	 * @Return: boolean collides
	 * This method checks whether a bird and the planet are colliding
	 */
	public boolean collides(Bird bird) {
		if (midPointX < bird.getX() + bird.getWidth()) {
			return (Intersector.overlaps(bird.getBoundingCircle(), boundingCircle));
		}
		return false;
	}
	
	//Getter
	public int getRadius() {
		return radius;
	}
	
	//Getter
	public int getMidPointX() {
		return midPointX;
	}

	//Setter
	public void setMidPointX(int midPointX) {
		this.midPointX = midPointX;
	}

	//Getter
	public int getMidPointY() {
		return midPointY;
	}

	//Setter
	public void setMidPointY(int midPointY) {
		this.midPointY = midPointY;
	}
}