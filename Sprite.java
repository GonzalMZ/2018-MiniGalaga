/**
 * @author Gonzalo Montero y Carlos Kalaska
 */

package edu.uc3m.galaga;

import edu.uc3m.game.GameBoardGUI;

public class Sprite {
	
	protected int id;
	protected String image;
	protected int x;
	protected int y;
	protected int health;
	protected GameBoardGUI gui;


	public Sprite(GameBoardGUI gui, int id, String image) {
		this.id=id;
		this.image=image;
		this.gui=gui;
		gui.gb_addSprite(id, image, true);
		gui.gb_setSpriteVisible(id, true);
	}
	public void move(int x, int y) {
		this.x=x;
		this.y=y;
		gui.gb_moveSpriteCoord(id, x, y);
	}
	public void entrada() {
		
	}
	/*public void moveDirection(int direction) {
		move(x+Conf.MOVE_DELTAS[direction][0], y+Conf.MOVE_DELTAS[direction][1]);
	}*/
	public  String toString() {//El abstract sirve para obligar a las clases hijas a llevar el toString
		return id+ " "+ image+" "+x+" "+y;//Para ver en consola el id, la imagen, la "x" y la "y" del sprite
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}

	
}
