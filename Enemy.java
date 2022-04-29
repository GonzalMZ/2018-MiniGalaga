/**
 * @author Gonzalo Montero y Carlos Kalaska
 */

package edu.uc3m.galaga;

import edu.uc3m.game.GameBoardGUI;

public class Enemy extends Sprite {

	private int health;
	private int movimiento;
	
	public Enemy(GameBoardGUI gui, int id, String image) {
		super(gui, id, image);//Invocando a la clase Sprite(Herencia)
	}

	public void choreography(int movimiento) {
		this.movimiento = movimiento;
		switch (movimiento) {
		case 1: // DIR_N
            setY(getY()-2);
            
		    break;
		case 2: // DIR_NNE
			setX(getX()+1);
			setY(getY()-4);
			
			break;
		case 3: // DIR_NE
			setX(getX()+2);
			setY(getY()-2);
			
			break;
		case 4: // DIR_ENE
			setX(getX()+4);
			setY(getY()-1);
			
			break;
		case 5: // DIR_E
			setX(getX()+2);
			
			break;
		case 6: // DIR_ESE
			setX(getX()+4);
			setY(getY()+1);
			
			break;
		case 7: // DIR_SE
			setX(getX()+2);
			setY(getY()+2);
						
			break;
		case 8: // DIR_SSE
			setX(getX()+1);
			setY(getY()+4);
			
			break;
		case 9: // DIR_S
			setY(getY()+2);
			
			break;
		case 10: // DIR_SSW
			setX(getX()-1);
			setY(getY()+4);
			
			break;
		case 11: // DIR_SW
			setX(getX()-2);
			setY(getY()+2);
			
			break;
		case 12: // DIR_WSW
			setX(getX()-4);
			setY(getY()+1);
			
			break;
		case 13: // DIR_W
			setX(getX()-2);
			
			break;
		case 14: // DIR_WNW
			setX(getX()-4);
			setY(getY()-1);
			
			break;
		case 15: // DIR_NW
			setX(getX()-2);
			setY(getY()-2);
			
			break;
		case 16: // DIR_NNW
			setX(getX()-1);
			setY(getY()-4);
			
			break;

		}gui.gb_moveSpriteCoord(id, x, y);

	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(int movimiento) {
		this.movimiento = movimiento;
	}
}

