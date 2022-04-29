/**
 * @author Gonzalo Montero y Carlos Kalaska
 */

package edu.uc3m.galaga;

import edu.uc3m.game.GameBoardGUI;

public class Torpedo extends Sprite {
	private int used;

	public Torpedo(GameBoardGUI gui, int id, String image) {
		super(gui, id, image);//Invocando a la clase Sprite(Herencia)
		gui.gb_setSpriteVisible(id, false);
	}

	public int getUsed() {
		return used;
	}
	public void setUsed(int used) {
		this.used = used;
	}
}