/**
 * @author Gonzalo Montero y Carlos Kalaska
 */

package edu.uc3m.galaga;

public class Player {

	private int id;
	private String image;
	private int x;
	private int y;
	private int health;
	
	public Player(int id, String image) {
		this.id=id;
		this.image=image;
	}
	
	public int getId() {
		return id;
	}
	public String getImage() {
		return image;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
}
