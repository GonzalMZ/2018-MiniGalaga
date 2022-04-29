/**
 * @author Gonzalo Montero y Carlos Kalaska
 */

package edu.uc3m.galaga;

import edu.uc3m.galaga.Enemy;
import edu.uc3m.galaga.Player;
import edu.uc3m.galaga.Torpedo;
import edu.uc3m.game.GameBoardGUI;

public class Minigalaga {

	public static void main(String[] args) throws InterruptedException {

		// TABLERO
		GameBoardGUI gui = new GameBoardGUI(17, 22); // tamaño del tablero (17,22)
		gui.setVisible(true);
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 22; j++) {
				gui.gb_setSquareColor(i, j, 0, 0, 0); // color del tablero (negro)
				gui.gb_setGridColor(0, 0, 0); // color de la cuadricula (negro)
			}
		}

		// PARTE DERECHA DEL JUEGO
		gui.gb_setPortraitPlayer("galagaLogo.jpg"); // imagen que aparece en la parte derecha
		gui.gb_setTextPointsUp("Puntos"); // aqui elegimos que caracteristicas del jugador se van a reflejar en el
											// "marcador"
		gui.gb_setTextPointsDown("Velocidad torpedos");
		gui.gb_setTextAbility1("Disparos");
		gui.gb_setTextAbility2("Aciertos");
		gui.gb_setTextPlayerName("Galaga");
		gui.gb_setValueHealthMax(3); // vida maxima
		gui.gb_setValueAbility1(0); // ponemos el contador de disparos a 0

		// INICIAMOS EL JUGADOR Y LO NOMBRAMOS
		Player player = new Player(0, "player.png");// creamos el objeto jugador
		gui.gb_addSprite(0, player.getImage(), true); // añadimos el sprite jugador
		gui.gb_setSpriteVisible(0, true); // hacemos que sea visible
		player.setHealth(3);

		gui.gb_addSprite(1, player.getImage(), true); // realizamos lo mismo con las naves que representan las vidas
														// del jugador
		gui.gb_setSpriteVisible(1, true);

		gui.gb_addSprite(2, player.getImage(), true);
		gui.gb_setSpriteVisible(2, true);

		// INICIAMOS LOS ENEMIGOS
		Enemy[] goei = new Enemy[10]; // creamos un array de 10 enemigos rojos
		for (int i = 0; i < goei.length; i++) {

			goei[i] = new Enemy(gui, i + 3, "enemy2G1.png"); // iniciamos cada enemigo
			goei[i].setHealth(0); // le damos vida al enemigo
			if (i < 5) // definimos las posiciones iniciales desde las cuales van a empezar a hacer la
						// coreografia
				goei[i].move(5, i * 10 + 225);
			else
				goei[i].move(165, (i - 5) * 10 + 225);

		}

		Enemy[] zako = new Enemy[20]; // repetimos lo anterior con un array de enemigos amarillos; en este caso
										// son 20
		for (int i = 0; i < zako.length; i++) {
			zako[i] = new Enemy(gui, i + 20, "enemy3G1.png");
			zako[i].setHealth(0);
			zako[i].move(30, i * 10 + 225);
		}

		Enemy[] comandante = new Enemy[10]; // y lo repetimos tambien para los enemigos verdes
		for (int i = 0; i < comandante.length; i++) {
			comandante[i] = new Enemy(gui, i + 50, "enemy1G1.png");
			comandante[i].setHealth(1); // en este caso tienen 1 vida mas que los otros enemigos, por lo que le
										// asignamos 2 vidas
			if (i < 5)
				comandante[i].move(15, i * 10 + 225);
			else
				comandante[i].move(155, (i - 5) * 10 + 225);
		}

		// INICIAMOS LOS TORPEDOS

		Torpedo[] torpedos = new Torpedo[500]; // iniciamos los torpedos; hemos decidido hacer un array de 500 torpedos
		for (int i = 0; i < torpedos.length; i++) {
			torpedos[i] = new Torpedo(gui, i + 100, "torpedo100.png");
			torpedos[i].setUsed(1); // le asignamos 1 uso, es decir, si chocan con un enemigo gastan ese uso
		}
		// INICIAMOS LOS TORPEDOS ENEMIGOS

		Torpedo torpedosEnemigos[] = new Torpedo[300]; // realizamos lo mismo con los torpedos enemigos, lo unico que
														// cambia es la imagen asignada
		for (int i = 0; i < torpedosEnemigos.length; i++) {
			torpedosEnemigos[i] = new Torpedo(gui, i + 600, "torpedo200.png");
			torpedosEnemigos[i].setUsed(1);
		}
		// DECIDIMOS PARA EL NIVEL QUÉ Y CUÁNTOS ENEMIGOS VAN A APARECER
		for (int i = 0; i < goei.length; i++) {
			switch (i) { // en este caso, esto son los enemigos que en este nivel no van a verse, por lo
							// que los hacemos invisibles y no les damos vida
			case 1:
			case 4:
			case 6:
			case 9:

				gui.gb_setSpriteVisible(i + 3, false);
				goei[i].setHealth(-1);
				break;
			}
		}
		for (int i = 0; i < zako.length; i++) { // realizamos lo mismo con los enemigos amarillos
			switch (i) {
			case 0:
			case 9:
			case 10:
			case 11:
			case 18:
			case 19:
				gui.gb_setSpriteVisible(i + 20, false);
				zako[i].setHealth(-1);
				break;
			}
		}
		for (int i = 0; i < comandante.length; i++) { // y con los verdes
			switch (i) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 6:
			case 7:
			case 8:
			case 9:
				gui.gb_setSpriteVisible(i + 50, false);
				comandante[i].setHealth(-1);
				break;
			}
		}

		int x = 85; // la x inicial del jugador
		int yComandante = 20, yZako = 40, yZako2 = 50, yGoei = 30; // las distintas y
																	// iniciales de los
																	// enemigos
		int contador = 100, contador2 = 0; // contadores utilizados para distintas operaciones
		int contador3[] = new int[40];
		for (int i = 0; i < 40; i++) {
			contador3[i] = 0;
		}
		boolean contar[] = new boolean[40];
		for (int i = 0; i < 40; i++) {
			contar[i] = false;
		}
		int enemigoAleatorio = (int) (Math.random() * 11), enemigoAleatorio2 = (int) (Math.random() * 21);
		int move = 1, puntos = 0, aciertos = 0, nivel = 1, muerte = 0;
		int azar, azar2, azar3;
		int contadorTiempo = 0, contadorDisparosenemigos = 0, moverse = 0, muertesverde = 0, tiempo = 0;
		boolean muerto = false; // booleans para controlar distintos parametros
		boolean perdido = false;
		boolean terminado = false;
		boolean coreografiaterminada = false;

		while (gui.isVisible()) { // hasta que !isVisible, el juego seguira en funcionamiento

			while (puntos <= 2900 && !perdido && !terminado) { // estodelimita el nivel 1, los puntos dan paso al nivel
																// 2, y si mueres tambien termina el nivel

				gui.gb_setValueLevel(nivel); // damos valores a los marcadores anteriormente mencionados
				gui.gb_setValuePointsUp(puntos);
				gui.gb_setValueAbility2(aciertos);
				gui.gb_setValueHealthCurrent(player.getHealth());
				moverse++;

				// MOVIMIENTO DE LA NAVE

				switch (gui.gb_getLastAction()) { // este switch, segun cual haya sido la ultima accion, mueve el
													// jugador hacia un lado o hacia otro, y tambien hace que el jugador
													// dispare
				case "left":
					if (x == 7) // el limite izquierdo
						x = 7;
					else
						x -= 3;
					break;
				case "right":
					if (x == 163) // el limite derecho
						x = 163;
					else
						x += 3;
					break;
				case "space": // si se pulsa el espacio el torpedo se vuelve visible
					gui.gb_setSpriteVisible(contador, true);
					torpedos[contador - 100].move(x, 193);
					contador++;
					gui.gb_setValueAbility1(contador - 100); // contabiliza los disparos
					break;

				}

				gui.gb_moveSpriteCoord(0, x, 200);// Coordenadas de la nave

				gui.gb_moveSpriteCoord(1, 6, 214);// Coordenadas de las naves que indican las vidas restantes
				gui.gb_moveSpriteCoord(2, 17, 214);

				// COREOGRAFIA
				for (int i = 0; i < 40; i++) { // estas son las distintas coreografias de los enemigos antes de comenzar
												// el nivel
					if (i < 5) {
						if (moverse > 100 && moverse < 160 + i * 10) {
							goei[i].choreography(1);
							gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
						} else if (moverse > 160 + i * 10 && moverse < 320 + i * 10) {
							goei[i].choreography(5);
							gui.gb_setSpriteImage(i + 3, "enemy204.png");
						} else if (moverse > 320 + i * 10 && moverse < 420 + i * 10) {
							goei[i].choreography(15);
							gui.gb_setSpriteImage(i + 3, "enemy214.png");
						} else if (moverse > 420 + i * 10 && moverse < 430 + i * 10) {
							goei[i].choreography(5);
							gui.gb_setSpriteImage(i + 3, "enemy204.png");
						} else if (moverse > 430 + i * 10 && moverse < 440 + i * 10) {
							goei[i].choreography(6);
							gui.gb_setSpriteImage(i + 3, "enemy205.png");
						} else if (moverse > 440 + i * 10 && moverse < 450 + i * 10) {
							goei[i].choreography(7);
							gui.gb_setSpriteImage(i + 3, "enemy206.png");
						} else if (moverse > 450 + i * 10 && moverse < 460 + i * 10) {
							goei[i].choreography(8);
							gui.gb_setSpriteImage(i + 3, "enemy207.png");
						} else if (moverse > 460 + i * 10 && moverse < 470 + i * 10) {
							goei[i].choreography(9);
							gui.gb_setSpriteImage(i + 3, "enemy208.png");
						} else if (moverse > 470 + i * 10 && moverse < 480 + i * 10) {
							goei[i].choreography(10);
							gui.gb_setSpriteImage(i + 3, "enemy209.png");
						} else if (moverse > 480 + i * 10 && moverse < 490 + i * 10) {
							goei[i].choreography(11);
							gui.gb_setSpriteImage(i + 3, "enemy210.png");
						} else if (moverse > 490 + i * 10 && moverse < 500 + i * 10) {
							goei[i].choreography(12);
							gui.gb_setSpriteImage(i + 3, "enemy211.png");
						} else if (moverse > 500 + i * 10 && moverse < 510 + i * 10) {
							goei[i].choreography(13);
							gui.gb_setSpriteImage(i + 3, "enemy212.png");
						} else if (moverse > 510 + i * 10 && moverse < 520 + i * 10) {
							goei[i].choreography(14);
							gui.gb_setSpriteImage(i + 3, "enemy213.png");
						} else if (moverse > 520 + i * 10 && moverse < 530 + i * 10) {
							goei[i].choreography(15);
							gui.gb_setSpriteImage(i + 3, "enemy214.png");
						} else if (moverse > 530 + i * 10 && moverse < 540 + i * 10) {
							goei[i].choreography(16);
							gui.gb_setSpriteImage(i + 3, "enemy215.png");
						} else if (moverse > 540 + i * 10 && moverse < 620 + i * 10) {
							goei[i].choreography(1);
							gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
						} else if (moverse > 620 + i * 10 && moverse < 720) {
							goei[i].choreography(5);
							gui.gb_setSpriteImage(i + 3, "enemy204.png");
						} else if (moverse > 735 && moverse < 740) {
							gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
						}

					} else if (i < 10) {
						if (moverse > 100 && moverse < 160 + (i - 5) * 10) {
							goei[i].choreography(1);
							gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
						} else if (moverse > 145 + (i - 5) * 10 && moverse < 320 + (i - 5) * 10) {
							goei[i].choreography(13);
							gui.gb_setSpriteImage(i + 3, "enemy212.png");
						} else if (moverse > 320 + (i - 5) * 10 && moverse < 420 + (i - 5) * 10) {
							goei[i].choreography(3);
							gui.gb_setSpriteImage(i + 3, "enemy202.png");
						} else if (moverse > 420 + (i - 5) * 10 && moverse < 430 + (i - 5) * 10) {
							goei[i].choreography(13);
							gui.gb_setSpriteImage(i + 3, "enemy212.png");
						} else if (moverse > 430 + (i - 5) * 10 && moverse < 440 + (i - 5) * 10) {
							goei[i].choreography(12);
							gui.gb_setSpriteImage(i + 3, "enemy211.png");
						} else if (moverse > 440 + (i - 5) * 10 && moverse < 450 + (i - 5) * 10) {
							goei[i].choreography(11);
							gui.gb_setSpriteImage(i + 3, "enemy210.png");
						} else if (moverse > 450 + (i - 5) * 10 && moverse < 460 + (i - 5) * 10) {
							goei[i].choreography(10);
							gui.gb_setSpriteImage(i + 3, "enemy209.png");
						} else if (moverse > 460 + (i - 5) * 10 && moverse < 470 + (i - 5) * 10) {
							goei[i].choreography(9);
							gui.gb_setSpriteImage(i + 3, "enemy208.png");
						} else if (moverse > 470 + (i - 5) * 10 && moverse < 480 + (i - 5) * 10) {
							goei[i].choreography(8);
							gui.gb_setSpriteImage(i + 3, "enemy207.png");
						} else if (moverse > 480 + (i - 5) * 10 && moverse < 490 + (i - 5) * 10) {
							goei[i].choreography(7);
							gui.gb_setSpriteImage(i + 3, "enemy206.png");
						} else if (moverse > 490 + (i - 5) * 10 && moverse < 500 + (i - 5) * 10) {
							goei[i].choreography(6);
							gui.gb_setSpriteImage(i + 3, "enemy205.png");
						} else if (moverse > 500 + (i - 5) * 10 && moverse < 510 + (i - 5) * 10) {
							goei[i].choreography(5);
							gui.gb_setSpriteImage(i + 3, "enemy204.png");
						} else if (moverse > 510 + (i - 5) * 10 && moverse < 520 + (i - 5) * 10) {
							goei[i].choreography(4);
							gui.gb_setSpriteImage(i + 3, "enemy203.png");
						} else if (moverse > 520 + (i - 5) * 10 && moverse < 530 + (i - 5) * 10) {
							goei[i].choreography(3);
							gui.gb_setSpriteImage(i + 3, "enemy202.png");
						} else if (moverse > 530 + (i - 5) * 10 && moverse < 540 + (i - 5) * 10) {
							goei[i].choreography(2);
							gui.gb_setSpriteImage(i + 3, "enemy201.png");
						} else if (moverse > 540 + (i - 5) * 10 && moverse < 620 + (i - 5) * 10) {
							goei[i].choreography(1);
							gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
						} else if (moverse > 620 + (i - 5) * 10 && moverse < 720) {
							goei[i].choreography(13);
							gui.gb_setSpriteImage(i + 3, "enemy212.png");
						} else if (moverse > 725 && moverse < 735) {
							coreografiaterminada = true; // permite que el nivel comience y que los torpedos puedan
															// matar
							gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
						}

					} else if (i < 30) {
						if (moverse > 10 && moverse < 115 + (i - 10) * 10) {
							zako[i - 10].choreography(1);
							gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
						} else if (moverse > 115 + (i - 10) * 10 && moverse < 169 + (i - 10) * 10) {
							zako[i - 10].choreography(5);
							gui.gb_setSpriteImage(i + 10, "enemy304.png");
						} else if (moverse > 169 + (i - 10) * 10 && moverse < 175 + (i - 10) * 10) {
							zako[i - 10].choreography(1);
							gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
						} else if (moverse > 175 + (i - 10) * 10 && moverse < 181 + (i - 10) * 10) {
							zako[i - 10].choreography(2);
							gui.gb_setSpriteImage(i + 10, "enemy301.png");
						} else if (moverse > 181 + (i - 10) * 10 && moverse < 187 + (i - 10) * 10) {
							zako[i - 10].choreography(3);
							gui.gb_setSpriteImage(i + 10, "enemy302.png");
						} else if (moverse > 187 + (i - 10) * 10 && moverse < 193 + (i - 10) * 10) {
							zako[i - 10].choreography(4);
							gui.gb_setSpriteImage(i + 10, "enemy303.png");
						} else if (moverse > 193 + (i - 10) * 10 && moverse < 199 + (i - 10) * 10) {
							zako[i - 10].choreography(5);
							gui.gb_setSpriteImage(i + 10, "enemy304.png");
						} else if (moverse > 199 + (i - 10) * 10 && moverse < 205 + (i - 10) * 10) {
							zako[i - 10].choreography(6);
							gui.gb_setSpriteImage(i + 10, "enemy305.png");
						} else if (moverse > 205 + (i - 10) * 10 && moverse < 211 + (i - 10) * 10) {
							zako[i - 10].choreography(7);
							gui.gb_setSpriteImage(i + 10, "enemy306.png");
						} else if (moverse > 211 + (i - 10) * 10 && moverse < 217 + (i - 10) * 10) {
							zako[i - 10].choreography(8);
							gui.gb_setSpriteImage(i + 10, "enemy307.png");
						} else if (moverse > 217 + (i - 10) * 10 && moverse < 223 + (i - 10) * 10) {
							zako[i - 10].choreography(9);
							gui.gb_setSpriteImage(i + 10, "enemy308.png");
						} else if (moverse > 223 + (i - 10) * 10 && moverse < 229 + (i - 10) * 10) {
							zako[i - 10].choreography(10);
							gui.gb_setSpriteImage(i + 10, "enemy309.png");
						} else if (moverse > 229 + (i - 10) * 10 && moverse < 235 + (i - 10) * 10) {
							zako[i - 10].choreography(11);
							gui.gb_setSpriteImage(i + 10, "enemy310.png");
						} else if (moverse > 235 + (i - 10) * 10 && moverse < 241 + (i - 10) * 10) {
							zako[i - 10].choreography(12);
							gui.gb_setSpriteImage(i + 10, "enemy311.png");
						} else if (moverse > 241 + (i - 10) * 10 && moverse < 247 + (i - 10) * 10) {
							zako[i - 10].choreography(13);
							gui.gb_setSpriteImage(i + 10, "enemy312.png");
						} else if (moverse > 247 + (i - 10) * 10 && moverse < 253 + (i - 10) * 10) {
							zako[i - 10].choreography(14);
							gui.gb_setSpriteImage(i + 10, "enemy313.png");
						} else if (moverse > 253 + (i - 10) * 10 && moverse < 259 + (i - 10) * 10) {
							zako[i - 10].choreography(15);
							gui.gb_setSpriteImage(i + 10, "enemy314.png");
						} else if (moverse > 259 + (i - 10) * 10 && moverse < 265 + (i - 10) * 10) {
							zako[i - 10].choreography(16);
							gui.gb_setSpriteImage(i + 10, "enemy315.png");
						} else if (moverse > 265 + (i - 10) * 10 && moverse < 309 + (i - 10) * 10) {
							if (i < 20) {
								zako[i - 10].choreography(2);
								gui.gb_setSpriteImage(i + 10, "enemy301.png");
							} else {
								zako[i - 10].choreography(16);
								gui.gb_setSpriteImage(i + 10, "enemy315.png");
							}
						} else if (moverse > 309 + (i - 10) * 10 && moverse < 353 + (i - 10) * 10) {
							if (i < 20) {
								zako[i - 10].choreography(8);
								gui.gb_setSpriteImage(i + 10, "enemy307.png");
							} else {
								zako[i - 10].choreography(10);
								gui.gb_setSpriteImage(i + 10, "enemy309.png");
							}

						} else if (moverse > 353 + (i - 10) * 10 && moverse < 399 + (i - 10) * 10) {
							if (i < 20) {
								zako[i - 10].choreography(13);
								gui.gb_setSpriteImage(i + 10, "enemy312.png");
							} else {
								zako[i - 10].choreography(5);
								gui.gb_setSpriteImage(i + 10, "enemy304.png");
							}

						} else if (moverse > 399 + (i - 10) * 10 && moverse < 449 + (i - 10) * 10) {
							if (i < 20) {
								zako[i - 10].choreography(3);
								gui.gb_setSpriteImage(i + 10, "enemy302.png");
							} else {
								zako[i - 10].choreography(15);
								gui.gb_setSpriteImage(i + 10, "enemy314.png");
							}
						} else if (moverse > 449 + (i - 10) * 10 && moverse < 484 + (i - 10) * 10 && i < 20) {
							zako[i - 10].choreography(1);
							gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
						} else if (moverse > 449 + (i - 10) * 10 && moverse < 472 + (i - 10) * 10 && i < 30) {
							zako[i - 10].choreography(1);
							gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
						} else if (moverse > 484 + (i - 10) * 10 && moverse < 573 && i < 20) {
							zako[i - 10].choreography(13);
							gui.gb_setSpriteImage(i + 10, "enemy312.png");
						} else if (moverse > 573 && moverse < 578 && i < 20)
							gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
						else if (moverse > 472 + (i - 10) * 10 && moverse < 666 && i > 19 && i < 30) {
							zako[i - 10].choreography(5);
							gui.gb_setSpriteImage(i + 10, "enemy304.png");
						} else if (moverse > 666 && moverse < 670 && i > 19 && i < 30)
							gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
					} else {
						if (i < 35) {
							if (moverse > 150 && moverse < -100 + i * 10) {
								comandante[i - 30].choreography(1);
								gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
							} else if (moverse > -100 + i * 10 && moverse < -90 + i * 10) {
								comandante[i - 30].choreography(2);
								gui.gb_setSpriteImage(i + 20, "enemy101.png");
							} else if (moverse > -90 + i * 10 && moverse < -80 + i * 10) {
								comandante[i - 30].choreography(3);
								gui.gb_setSpriteImage(i + 20, "enemy102.png");
							} else if (moverse > -80 + i * 10 && moverse < -70 + i * 10) {
								comandante[i - 30].choreography(4);
								gui.gb_setSpriteImage(i + 20, "enemy103.png");
							} else if (moverse > -70 + i * 10 && moverse < i * 10) {
								comandante[i - 30].choreography(5);
								gui.gb_setSpriteImage(i + 20, "enemy104.png");
							} else if (moverse > i * 10 && moverse < 10 + i * 10) {
								comandante[i - 30].choreography(4);
								gui.gb_setSpriteImage(i + 20, "enemy103.png");
							} else if (moverse > 10 + i * 10 && moverse < 20 + i * 10) {
								comandante[i - 30].choreography(3);
								gui.gb_setSpriteImage(i + 20, "enemy102.png");
							} else if (moverse > 20 + i * 10 && moverse < 30 + i * 10) {
								comandante[i - 30].choreography(2);
								gui.gb_setSpriteImage(i + 20, "enemy101.png");
							} else if (moverse > 30 + i * 10 && moverse < 40 + i * 10) {
								comandante[i - 30].choreography(1);
								gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
							} else if (moverse > 40 + i * 10 && moverse < 50 + i * 10) {
								comandante[i - 30].choreography(16);
								gui.gb_setSpriteImage(i + 20, "enemy115.png");
							} else if (moverse > 50 + i * 10 && moverse < 60 + i * 10) {
								comandante[i - 30].choreography(15);
								gui.gb_setSpriteImage(i + 20, "enemy114.png");
							} else if (moverse > 60 + i * 10 && moverse < 70 + i * 10) {
								comandante[i - 30].choreography(14);
								gui.gb_setSpriteImage(i + 20, "enemy113.png");
							} else if (moverse > 70 + i * 10 && moverse < 140 + i * 10) {
								comandante[i - 30].choreography(13);
								gui.gb_setSpriteImage(i + 20, "enemy112.png");
							} else if (moverse > 140 + i * 10 && moverse < 150 + i * 10) {
								comandante[i - 30].choreography(14);
								gui.gb_setSpriteImage(i + 20, "enemy113.png");
							} else if (moverse > 150 + i * 10 && moverse < 160 + i * 10) {
								comandante[i - 30].choreography(15);
								gui.gb_setSpriteImage(i + 20, "enemy114.png");
							} else if (moverse > 160 + i * 10 && moverse < 170 + i * 10) {
								comandante[i - 30].choreography(16);
								gui.gb_setSpriteImage(i + 20, "enemy115.png");
							} else if (moverse > 170 + i * 10 && moverse < 176 + i * 10) {
								comandante[i - 30].choreography(1);
								gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
							} else if (moverse > 178 + i * 10 && moverse < 243 + i * 10) {
								comandante[i - 30].choreography(5);
								gui.gb_setSpriteImage(i + 20, "enemy104.png");
							} else if (moverse > 243 + i * 10 && moverse < (243 + i * 10) + 3)
								gui.gb_setSpriteImage(i + 20, "enemy1G0.png");

						} else {
							if (moverse > 150 && moverse < -100 + (i - 5) * 10) {
								comandante[i - 30].choreography(1);
								gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
							} else if (moverse > -100 + (i - 5) * 10 && moverse < -90 + (i - 5) * 10) {
								comandante[i - 30].choreography(16);
								gui.gb_setSpriteImage(i + 20, "enemy115.png");
							} else if (moverse > -90 + (i - 5) * 10 && moverse < -80 + (i - 5) * 10) {
								comandante[i - 30].choreography(15);
								gui.gb_setSpriteImage(i + 20, "enemy114.png");
							} else if (moverse > -80 + (i - 5) * 10 && moverse < -70 + (i - 5) * 10) {
								comandante[i - 30].choreography(14);
								gui.gb_setSpriteImage(i + 20, "enemy113.png");
							} else if (moverse > -70 + (i - 5) * 10 && moverse < (i - 5) * 10) {
								comandante[i - 30].choreography(13);
								gui.gb_setSpriteImage(i + 20, "enemy112.png");
							} else if (moverse > (i - 5) * 10 && moverse < 10 + (i - 5) * 10) {
								comandante[i - 30].choreography(14);
								gui.gb_setSpriteImage(i + 20, "enemy113.png");
							} else if (moverse > 10 + (i - 5) * 10 && moverse < 20 + (i - 5) * 10) {
								comandante[i - 30].choreography(15);
								gui.gb_setSpriteImage(i + 20, "enemy114.png");
							} else if (moverse > 20 + (i - 5) * 10 && moverse < 30 + (i - 5) * 10) {
								comandante[i - 30].choreography(16);
								gui.gb_setSpriteImage(i + 20, "enemy115.png");
							} else if (moverse > 30 + (i - 5) * 10 && moverse < 40 + (i - 5) * 10) {
								comandante[i - 30].choreography(1);
								gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
							} else if (moverse > 40 + (i - 5) * 10 && moverse < 50 + (i - 5) * 10) {
								comandante[i - 30].choreography(2);
								gui.gb_setSpriteImage(i + 20, "enemy101.png");
							} else if (moverse > 50 + (i - 5) * 10 && moverse < 60 + (i - 5) * 10) {
								comandante[i - 30].choreography(3);
								gui.gb_setSpriteImage(i + 20, "enemy102.png");
							} else if (moverse > 60 + (i - 5) * 10 && moverse < 70 + (i - 5) * 10) {
								comandante[i - 30].choreography(4);
								gui.gb_setSpriteImage(i + 20, "enemy103.png");
							} else if (moverse > 70 + (i - 5) * 10 && moverse < 140 + (i - 5) * 10) {
								comandante[i - 30].choreography(5);
								gui.gb_setSpriteImage(i + 20, "enemy104.png");
							} else if (moverse > 140 + (i - 5) * 10 && moverse < 150 + (i - 5) * 10) {
								comandante[i - 30].choreography(4);
								gui.gb_setSpriteImage(i + 20, "enemy103.png");
							} else if (moverse > 150 + (i - 5) * 10 && moverse < 160 + (i - 5) * 10) {
								comandante[i - 30].choreography(3);
								gui.gb_setSpriteImage(i + 20, "enemy102.png");
							} else if (moverse > 160 + (i - 5) * 10 && moverse < 170 + (i - 5) * 10) {
								comandante[i - 30].choreography(2);
								gui.gb_setSpriteImage(i + 20, "enemy101.png");
							} else if (moverse > 170 + (i - 5) * 10 && moverse < 176 + (i - 5) * 10) {
								comandante[i - 30].choreography(1);
								gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
							} else if (moverse > 178 + (i - 5) * 10 && moverse < 243 + (i - 5) * 10) {
								comandante[i - 30].choreography(13);
								gui.gb_setSpriteImage(i + 20, "enemy112.png");
							} else if (moverse > 243 + (i - 5) * 10 && moverse < (243 + (i - 5) * 10) + 3)
								gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
						}
					}
				}
				moverse++;

				// MOVIMIENTO TORPEDOS CUANDO SE DISPARAN

				for (int j = 100; j < contador; j++) { // el movimiento vertical de los torpedos
					torpedos[j - 100].move(torpedos[j - 100].getX(), (torpedos[j - 100].getY() - 4));
				}
				if (coreografiaterminada) { // si la coreografia ha terminado, comienza el nivel

					// MOVIMIENTO ENEMIGOS (DE UN LADO A OTRO Y BAJANDO CAFA VEZ QUE SE TOPAN CON UN
					// LIMITE)

					for (int i = 0; i < goei.length; i++) {
						if (goei[7].getX() == ((i + 3) + 1) * 22) { // si el enemigo mencionado llega al limite, cambia
																	// de sentido
							move = -1;
							yGoei++; // cada vez que llegan al limite, las "y" aumenta
							yZako++;
							yZako2++; // hay dos "y" para los zako porque ocupan dos filas distintas
							yComandante++;

						} else if (goei[4].getX() == (i + 3) + 45) { // igual pero el movimiento se realiza hacia el
																		// otro lado
							move = 1;
							yGoei++;
							yZako++;
							yZako2++;
							yComandante++;
						}
					}

					for (int i = 0; i < goei.length; i++) { // movimiento del enemigo rojo
						if (i != enemigoAleatorio)
							goei[i].move((goei[i].getX() + move), yGoei);
						else if (i == enemigoAleatorio) {
							goei[enemigoAleatorio].move((goei[enemigoAleatorio].getX() + move),
									(goei[enemigoAleatorio].getY() + 1)); // "ataque" del enemigo rojo
							if (contador2 % 18 == 0)
								gui.gb_setSpriteImage(i + 3, "enemy200.png");
							else if (contador2 % 9 == 0)
								gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
							if (goei[enemigoAleatorio].getY() > 220) {
								gui.gb_setSpriteImage(enemigoAleatorio + 3, "enemy2G1.png");
								enemigoAleatorio = (int) (Math.random() * 10);
							}
							if (goei[enemigoAleatorio].getX() < (x + 5) && goei[enemigoAleatorio].getX() > (x - 5)
									&& goei[enemigoAleatorio].getY() > 195 && goei[enemigoAleatorio].getY() < 205
									&& goei[enemigoAleatorio].getHealth() == 0) {
								player.setHealth(0);
							}
						}

					}
					for (int i = 0; i < zako.length; i++) { // movimiento del enemigo amarillo
						if (i != enemigoAleatorio2) {
							if (i < 10)
								zako[i].move((zako[i].getX() + move), yZako);
							else
								zako[i].move((zako[i].getX() + move), yZako2);
						} else if (i == enemigoAleatorio2) {
							zako[enemigoAleatorio2].move((zako[enemigoAleatorio2].getX() + move),
									(zako[enemigoAleatorio2].getY() + 2)); // "ataque" del enemigo amarillo
							if (zako[enemigoAleatorio2].getY() > 220)
								enemigoAleatorio2 = (int) (Math.random() * 20);

							if (zako[enemigoAleatorio2].getX() < (x + 5) && zako[enemigoAleatorio2].getX() > (x - 5)
									&& zako[enemigoAleatorio2].getY() > 195 && zako[enemigoAleatorio2].getY() < 205
									&& zako[enemigoAleatorio2].getHealth() == 0) {
								player.setHealth(0);
							}
						}
					}

					for (int i = 0; i < comandante.length; i++) { // movimiento del enemigo verde
						comandante[i].move((comandante[i].getX() + move), yComandante);
					}

					// DESAPARICION DE LOS ENEMIGOS CUANDO EXPLOTAN

					for (int j = 100; j < contador; j++) { // si las coordenadas del torpedo y las del se encuentran
															// entre el rango delimitado:
						for (int i = 3; i < goei.length + 3; i++) {
							if (torpedos[j - 100].getX() < goei[i - 3].getX() + 5
									&& torpedos[j - 100].getX() > goei[i - 3].getX() - 5
									&& torpedos[j - 100].getY() < goei[i - 3].getY() + 5
									&& torpedos[j - 100].getY() > goei[i - 3].getY() - 5) {
								if ((torpedos[j - 100].getUsed() == 1) && goei[i - 3].getHealth() == 0) {
									contar[i - 3] = true; // este boolean permite hacer la explosion
									gui.gb_setSpriteVisible(torpedos[j - 100].getId(), false); // el enemigo desaparece
									torpedos[j - 100].setUsed(torpedos[j - 100].getUsed() - 1); // el torpedo ya ha sido
																								// usado y desaparece
									goei[i - 3].setHealth(goei[i - 3].getHealth() - 1); // el enemigo pierde la vida
									puntos = puntos + 250;
									aciertos++;
									gui.gb_println(
											"El enemigo " + (nivel * 1000 + i - 3) + " ha muerto. Ganas 250 puntos"); // se
																														// imprime
																														// en
																														// consola
								}
							}
						}
						for (int i = 20; i < zako.length + 20; i++) { // hacemos lo mismo con zako
							if (i < 30) {
								if (torpedos[j - 100].getX() < zako[i - 20].getX() + 5
										&& torpedos[j - 100].getX() > zako[i - 20].getX() - 5
										&& torpedos[j - 100].getY() < zako[i - 20].getY() + 5
										&& torpedos[j - 100].getY() > zako[i - 20].getY() - 5) {
									if ((torpedos[j - 100].getUsed() == 1) && zako[i - 20].getHealth() == 0) {
										contar[i - 10] = true;
										gui.gb_setSpriteVisible(torpedos[j - 100].getId(), false);
										torpedos[j - 100].setUsed(torpedos[j - 100].getUsed() - 1);
										zako[i - 20].setHealth(zako[i - 13].getHealth() - 1);
										puntos = puntos + 100;
										aciertos++;
										gui.gb_println(
												"El enemigo " + (nivel * 1000 + i) + " ha muerto. Ganas 100 puntos");
									}
								}

							} else {
								if (torpedos[j - 100].getX() < zako[i - 20].getX() + 5 // lo mismo pero con los zako de
																						// segunda fila
										&& torpedos[j - 100].getX() > zako[i - 20].getX() - 5
										&& torpedos[j - 100].getY() < zako[i - 20].getY() + 5
										&& torpedos[j - 100].getY() > zako[i - 20].getY() - 5) {
									if ((torpedos[j - 100].getUsed() == 1) && zako[i - 20].getHealth() == 0) {
										contar[i - 10] = true;
										gui.gb_setSpriteVisible(torpedos[j - 100].getId(), false);
										torpedos[j - 100].setUsed(torpedos[j - 100].getUsed() - 1);
										zako[i - 20].setHealth(zako[i - 20].getHealth() - 1);
										puntos = puntos + 100;
										aciertos++;
										gui.gb_println(
												"El enemigo " + (nivel * 1000 + i) + " ha muerto. Ganas 100 puntos");
									}
								}
							}

						}
						for (int i = 50; i < comandante.length + 50; i++) { // y con comandante
							if (torpedos[j - 100].getX() < comandante[i - 50].getX() + 5
									&& torpedos[j - 100].getX() > comandante[i - 50].getX() - 5
									&& torpedos[j - 100].getY() < yComandante + 5
									&& torpedos[j - 100].getY() > yComandante - 5) {
								if (comandante[i - 50].getHealth() < 1) {
									if ((torpedos[j - 100].getUsed() == 1) && comandante[i - 50].getHealth() == 0) {// si
																													// solo
																													// le
																													// queda
																													// 1
																													// vida
										contar[i - 20] = true;
										gui.gb_setSpriteVisible(j, false);
										torpedos[j - 100].setUsed(torpedos[j - 100].getUsed() - 1);
										comandante[i - 50].setHealth(comandante[i - 50].getHealth() - 1);
										aciertos++;
										muertesverde++;
										gui.gb_println(
												"El enemigo " + (nivel * 1000 + i - 20) + " ha muerto. Te quedan "
														+ (2 - muertesverde) + " jefes por derrotar");
									}
								}
								if ((torpedos[j - 100].getUsed() == 1) && comandante[i - 50].getHealth() == 1) { // cambia
																													// la
																													// imagen
																													// ya
																													// que
																													// tienen
																													// dos
																													// vidas
									gui.gb_setSpriteImage(i, "enemy9G0.png");
									gui.gb_setSpriteVisible(torpedos[j - 100].getId(), false);
									torpedos[j - 100].setUsed(torpedos[j - 100].getUsed() - 1);
									comandante[i - 50].setHealth(comandante[i - 50].getHealth() - 1);
									aciertos++;
									;
								}

							}
						}
					}
					// IMAGEN DE LA EXPLOSION Y DESAPARICION CUANDO UN TORPEDO ALCANZA A UN ENEMIGO
					for (int i = 3; i < goei.length + 3; i++) {
						if (contar[i - 3]) { // cambiamos las imagenes del jugador por las imagenes de la explosion, y
												// finalmente lo volvemos invisible
							contador3[i - 3]++;
							if (contador3[i - 3] < 3)
								gui.gb_setSpriteImage(i, "explosion20.png");
							else if (contador3[i - 3] < 6)
								gui.gb_setSpriteImage(i, "explosion21.png");
							else if (contador3[i - 3] < 9)
								gui.gb_setSpriteImage(i, "explosion22.png");
							else if (contador3[i - 3] < 12)
								gui.gb_setSpriteImage(i, "explosion23.png");
							else if (contador3[i - 3] < 15)
								gui.gb_setSpriteImage(i, "explosion24.png");
							else
								gui.gb_setSpriteVisible(i, false);
						}
					}
					for (int i = 20; i < zako.length + 20; i++) {
						if (contar[i - 10]) {
							contador3[i - 10]++;
							if (contador3[i - 10] < 3)
								gui.gb_setSpriteImage(i, "explosion20.png");
							else if (contador3[i - 10] < 6)
								gui.gb_setSpriteImage(i, "explosion21.png");
							else if (contador3[i - 10] < 9)
								gui.gb_setSpriteImage(i, "explosion22.png");
							else if (contador3[i - 10] < 12)
								gui.gb_setSpriteImage(i, "explosion23.png");
							else if (contador3[i - 10] < 15)
								gui.gb_setSpriteImage(i, "explosion24.png");
							else
								gui.gb_setSpriteVisible(i, false);
						}
					}
					for (int i = 50; i < comandante.length + 50; i++) {
						if (contar[i - 20]) {
							contador3[i - 20]++;
							if (contador3[i - 20] < 3)
								gui.gb_setSpriteImage(i, "explosion20.png");
							else if (contador3[i - 20] < 6)
								gui.gb_setSpriteImage(i, "explosion21.png");
							else if (contador3[i - 20] < 9)
								gui.gb_setSpriteImage(i, "explosion22.png");
							else if (contador3[i - 20] < 12)
								gui.gb_setSpriteImage(i, "explosion23.png");
							else if (contador3[i - 20] < 15)
								gui.gb_setSpriteImage(i, "explosion24.png");
							else
								gui.gb_setSpriteVisible(i, false);
						}
					}

					// ALETEO ENEMIGOS
					for (int i = 20; i < zako.length + 20; i++) { // segun el contador, la imagen elegida es
																	// una u otra , y da la impresion de que
																	// esta aleteando
						if (contador2 % 20 == 0 && zako[i - 20].getHealth() == 0)
							gui.gb_setSpriteImage(i, "enemy3G0.png");
						else if (contador2 % 10 == 0 && zako[i - 20].getHealth() == 0)
							gui.gb_setSpriteImage(i, "enemy3G1.png");
					}
					for (int i = 50; i < comandante.length + 50; i++) {
						if (comandante[i - 50].getHealth() == 1) {
							if (contador2 % 24 == 0)
								gui.gb_setSpriteImage(i, "enemy1G0.png");
							else if (contador2 % 12 == 0)
								gui.gb_setSpriteImage(i, "enemy1G1.png");
						} else if (comandante[i - 50].getHealth() == 0) {
							if (contador2 % 24 == 0)
								gui.gb_setSpriteImage(i, "enemy9G0.png");
							else if (contador2 % 12 == 0)
								gui.gb_setSpriteImage(i, "enemy9G1.png");
						}
					}
					contador2++;

					// APARICION DE LOS TORPEDOS ENEMIGOS
					if (contadorTiempo % 56 == 0) { // hacemos que aparezcan torpedos enemigos en un enemigo al
													// azar
						azar = (int) ((Math.random() * 10));
						if (goei[azar].getHealth() == 0) { // solo si tiene vida puede disparar
							gui.gb_setSpriteVisible(contadorDisparosenemigos + 600, true);
							torpedosEnemigos[contadorDisparosenemigos].move(goei[azar].getX(), goei[azar].getY());
							contadorDisparosenemigos++;
						}
					}

					if (contadorTiempo % 105 == 0) { // los zako disparan menos veces
						azar2 = (int) ((Math.random() * 20));
						if (zako[azar2].getHealth() == 0) {
							gui.gb_setSpriteVisible(contadorDisparosenemigos + 600, true);
							torpedosEnemigos[contadorDisparosenemigos].move(zako[azar2].getX(), zako[azar2].getY());
							contadorDisparosenemigos++;
						}
					}
					if (contadorTiempo % 20 == 0) { // los comandante tienen mas probabilidades de disparar
						azar3 = (int) ((Math.random() * 10));
						if (comandante[azar3].getHealth() == 0 || comandante[azar3].getHealth() == 1) {
							gui.gb_setSpriteVisible(contadorDisparosenemigos + 600, true);
							torpedosEnemigos[contadorDisparosenemigos].move(comandante[azar3].getX(),
									zako[azar3].getY());
							contadorDisparosenemigos++;
						}
					}

					contadorTiempo++;
					// CHOQUE DE LOS TORPEDOS ENEMIGOS CON EL JUGADOR
					for (int i = 0; i < contadorDisparosenemigos; i++) { // es la misma dinamica que con los torpedos y
																			// los enemigos
						torpedosEnemigos[i].move(torpedosEnemigos[i].getX(), torpedosEnemigos[i].getY() + 2);
						if (torpedosEnemigos[i].getX() < (x + 5) && torpedosEnemigos[i].getX() > (x - 5)
								&& torpedosEnemigos[i].getY() < 205 && torpedosEnemigos[i].getY() > 195) {
							if (player.getHealth() == 3 && torpedosEnemigos[i].getUsed() == 1) { // si le quedan 2 vidas
								muerto = true; // para que realice la animacion de la explosion
								player.setHealth(player.getHealth() - 1);
								torpedosEnemigos[i].setUsed(torpedosEnemigos[i].getUsed() - 1); // para que desaparezca
																								// el torpedo enemigo
								gui.gb_setSpriteVisible(i + 600, false);
								gui.gb_setSpriteVisible(2, false); // desaparece una nave del jugador
								gui.gb_showMessageDialog("El jugador ha muerto. Has perdido una vida");
							} else if (player.getHealth() == 2 && torpedosEnemigos[i].getUsed() == 1) { // si le queda 1
																										// vida
								muerto = true;
								player.setHealth(player.getHealth() - 1);
								torpedosEnemigos[i].setUsed(torpedosEnemigos[i].getUsed() - 1);
								gui.gb_setSpriteVisible(i + 600, false);
								gui.gb_setSpriteVisible(1, false);
								gui.gb_showMessageDialog("El jugador ha muerto. Has perdido una vida");
							} else if (player.getHealth() == 1 && torpedosEnemigos[i].getUsed() == 1) { // si ya no le
																										// quedan
								// mas vidas y le dan
								player.setHealth(player.getHealth() - 1);
								gui.gb_setSpriteVisible(i + 600, false);
							}
						}
					}
					contadorTiempo++;

					// MUERTE DEL JUGADOR
					for (int i = 0; i < 40; i++) {
						if (i < 10) {
							if (x < goei[i].getX() + 5 && x > goei[i].getX() - 5 && goei[i].getY() < 196
									&& goei[i].getY() > 204)
								player.setHealth(0);

						} else if (i < 20) {
							if (x < zako[i - 10].getX() + 5 && x > zako[i - 10].getX() - 5 && yZako < 204
									&& yZako > 196)
								player.setHealth(0);
							;

						} else if (i < 30) {
							if (x < zako[i - 20].getX() + 5 && x > zako[i - 20].getX() - 5 && yZako2 < 204
									&& yZako2 > 196)
								player.setHealth(0);
							;

						} else if (i < 40) {
							if (x < comandante[i - 30].getX() + 5 && x > comandante[i - 30].getX() - 5
									&& yComandante < 204 && yComandante > 196)
								player.setHealth(0);
							;
						}
					}

					// SI AL JUGADOR NO LE QUEDAN MAS VIDAS
					if (player.getHealth() == 0 || muerto) { // realiza la animacion de la explosion
						muerte++;
						if (muerte < 3) {
							gui.gb_setSpriteImage(0, "explosion11.png");
							gui.gb_setPortraitPlayer("hit.png"); // en la foto de la derecha aparecen unas garras
						} else if (muerte < 6)
							gui.gb_setSpriteImage(0, "explosion12.png");
						else if (muerte < 9)
							gui.gb_setSpriteImage(0, "explosion13.png");
						else if (muerte < 12)
							gui.gb_setSpriteImage(0, "explosion11.png");
						else if (muerte > 12) {
							gui.gb_setSpriteVisible(0, false);
							if (player.getHealth() != 0 && muerte > 18) { // si todavia le quedan vidas
								gui.gb_setSpriteImage(0, "player.png");
								gui.gb_setSpriteVisible(0, true);
								gui.gb_setPortraitPlayer("galagaLogo.jpg");
								muerto = false;
								muerte = 0;
							}
							if (player.getHealth() == 0 && !perdido) { // si ya no le quedan vidas(vidaactual==0)
								gui.gb_showMessageDialog("Ha perdido las vidas que le quedaban. Partida finalizada");
								perdido = true;

							}
						}

					}
					if (puntos == 2900 && muertesverde == 2) // damos un tiempo antes de que empiece el siguiente nivel
						tiempo++;
					if (tiempo == 70)
						terminado = true;
				}
				Thread.sleep(1200 / 60); // frames por segundo en los que se ejecuta este while
			}

			// SEGUNDO NIVEL
			if (puntos < 5800 && puntos >= 2900 && muertesverde == 2 && tiempo >= 70) {
				nivel = 2;
				// DECIDIMOS PARA EL NIVEL QUÉ Y CUÁNTOS ENEMIGOS VAN A APARECER
				for (int i = 0; i < goei.length; i++) {
					if (i < 5)
						goei[i].move(5, i * 10 + 225);
					else
						goei[i].move(165, (i - 5) * 10 + 225);

					gui.gb_setSpriteVisible(i + 3, true);
					goei[i].setHealth(0);
				}
				for (int i = 0; i < zako.length; i++) {
					if (i < 10)
						zako[i].move(30, i * 10 + 225);
					else
						zako[i].move(30, i * 10 + 225);

					gui.gb_setSpriteVisible(i + 20, true);
					zako[i].setHealth(0);

				}
				for (int i = 0; i < comandante.length; i++) {
					if (i < 5)
						comandante[i].move(15, i * 10 + 225);
					else
						comandante[i].move(155, (i - 5) * 10 + 225);

					switch (i) {
					case 2:
					case 3:
					case 6:
					case 7:
						gui.gb_setSpriteVisible(i + 50, true);
						comandante[i].setHealth(1);
						break;
					}
				}
				yComandante = 20; // volvemos a dar los valores iniciales a la mayoria de las variables para que
									// el funcionamiento del siguiente nivel sea igual al del nivel anterior
				yZako = 40;
				yZako2 = 50;
				yGoei = 30;
				contador2 = 0;
				terminado = false;

				for (int i = 0; i < 40; i++) {
					contador3[i] = 0;
				}

				for (int i = 0; i < 40; i++) {
					contar[i] = false;
				}
				move = 1;
				muerte = 0;
				muerto = true;
				azar = 0;
				contadorTiempo = 0;
				contadorDisparosenemigos = 0;
				tiempo = 0;
				moverse = 0;
				coreografiaterminada = false;

				while (puntos >= 2900 && puntos <= 7400 && muertesverde <= 6 && !terminado && !perdido) {
					if (puntos == 7400 && muertesverde == 6) // el tiempo antes de que comience el siguiente nivel
						tiempo++;
					if (tiempo == 70)
						terminado = true;

					gui.gb_setValueLevel(nivel);
					gui.gb_setValuePointsUp(puntos);
					gui.gb_setValueAbility2(aciertos);
					gui.gb_setValueHealthCurrent(player.getHealth());

					// MOVIMIENTO DE LA NAVE

					switch (gui.gb_getLastAction()) {
					case "left":
						if (x == 7)
							x = 7;
						else
							x -= 3;
						break;
					case "right":
						if (x == 163)
							x = 163;
						else
							x += 3;
						break;
					case "space":
						gui.gb_setSpriteVisible(contador, true);
						torpedos[contador - 100].move(x, 193);
						contador++;
						gui.gb_setValueAbility1(contador - 100);
						break;

					}

					gui.gb_moveSpriteCoord(0, x, 200);// Coordenadas de la nave

					gui.gb_moveSpriteCoord(1, 6, 214);// Coordenadas de las naves que indican las vidas restantes
					gui.gb_moveSpriteCoord(2, 17, 214);
					// COREOGRAFIA
					for (int i = 0; i < 40; i++) {
						if (i < 5) {
							if (moverse > 100 && moverse < 160 + i * 10) {
								goei[i].choreography(1);
								gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
							} else if (moverse > 160 + i * 10 && moverse < 320 + i * 10) {
								goei[i].choreography(5);
								gui.gb_setSpriteImage(i + 3, "enemy204.png");
							} else if (moverse > 320 + i * 10 && moverse < 420 + i * 10) {
								goei[i].choreography(15);
								gui.gb_setSpriteImage(i + 3, "enemy214.png");
							} else if (moverse > 420 + i * 10 && moverse < 430 + i * 10) {
								goei[i].choreography(5);
								gui.gb_setSpriteImage(i + 3, "enemy204.png");
							} else if (moverse > 430 + i * 10 && moverse < 440 + i * 10) {
								goei[i].choreography(6);
								gui.gb_setSpriteImage(i + 3, "enemy205.png");
							} else if (moverse > 440 + i * 10 && moverse < 450 + i * 10) {
								goei[i].choreography(7);
								gui.gb_setSpriteImage(i + 3, "enemy206.png");
							} else if (moverse > 450 + i * 10 && moverse < 460 + i * 10) {
								goei[i].choreography(8);
								gui.gb_setSpriteImage(i + 3, "enemy207.png");
							} else if (moverse > 460 + i * 10 && moverse < 470 + i * 10) {
								goei[i].choreography(9);
								gui.gb_setSpriteImage(i + 3, "enemy208.png");
							} else if (moverse > 470 + i * 10 && moverse < 480 + i * 10) {
								goei[i].choreography(10);
								gui.gb_setSpriteImage(i + 3, "enemy209.png");
							} else if (moverse > 480 + i * 10 && moverse < 490 + i * 10) {
								goei[i].choreography(11);
								gui.gb_setSpriteImage(i + 3, "enemy210.png");
							} else if (moverse > 490 + i * 10 && moverse < 500 + i * 10) {
								goei[i].choreography(12);
								gui.gb_setSpriteImage(i + 3, "enemy211.png");
							} else if (moverse > 500 + i * 10 && moverse < 510 + i * 10) {
								goei[i].choreography(13);
								gui.gb_setSpriteImage(i + 3, "enemy212.png");
							} else if (moverse > 510 + i * 10 && moverse < 520 + i * 10) {
								goei[i].choreography(14);
								gui.gb_setSpriteImage(i + 3, "enemy213.png");
							} else if (moverse > 520 + i * 10 && moverse < 530 + i * 10) {
								goei[i].choreography(15);
								gui.gb_setSpriteImage(i + 3, "enemy214.png");
							} else if (moverse > 530 + i * 10 && moverse < 540 + i * 10) {
								goei[i].choreography(16);
								gui.gb_setSpriteImage(i + 3, "enemy215.png");
							} else if (moverse > 540 + i * 10 && moverse < 618 + i * 10) {
								goei[i].choreography(1);
								gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
							} else if (moverse > 618 + i * 10 && moverse < 712) {
								goei[i].choreography(5);
								gui.gb_setSpriteImage(i + 3, "enemy204.png");
							} else if (moverse > 735 && moverse < 740) {
								gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
							}

						} else if (i < 10) {
							if (moverse > 100 && moverse < 160 + (i - 5) * 10) {
								goei[i].choreography(1);
								gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
							} else if (moverse > 145 + (i - 5) * 10 && moverse < 320 + (i - 5) * 10) {
								goei[i].choreography(13);
								gui.gb_setSpriteImage(i + 3, "enemy212.png");
							} else if (moverse > 320 + (i - 5) * 10 && moverse < 420 + (i - 5) * 10) {
								goei[i].choreography(3);
								gui.gb_setSpriteImage(i + 3, "enemy202.png");
							} else if (moverse > 420 + (i - 5) * 10 && moverse < 430 + (i - 5) * 10) {
								goei[i].choreography(13);
								gui.gb_setSpriteImage(i + 3, "enemy212.png");
							} else if (moverse > 430 + (i - 5) * 10 && moverse < 440 + (i - 5) * 10) {
								goei[i].choreography(12);
								gui.gb_setSpriteImage(i + 3, "enemy211.png");
							} else if (moverse > 440 + (i - 5) * 10 && moverse < 450 + (i - 5) * 10) {
								goei[i].choreography(11);
								gui.gb_setSpriteImage(i + 3, "enemy210.png");
							} else if (moverse > 450 + (i - 5) * 10 && moverse < 460 + (i - 5) * 10) {
								goei[i].choreography(10);
								gui.gb_setSpriteImage(i + 3, "enemy209.png");
							} else if (moverse > 460 + (i - 5) * 10 && moverse < 470 + (i - 5) * 10) {
								goei[i].choreography(9);
								gui.gb_setSpriteImage(i + 3, "enemy208.png");
							} else if (moverse > 470 + (i - 5) * 10 && moverse < 480 + (i - 5) * 10) {
								goei[i].choreography(8);
								gui.gb_setSpriteImage(i + 3, "enemy207.png");
							} else if (moverse > 480 + (i - 5) * 10 && moverse < 490 + (i - 5) * 10) {
								goei[i].choreography(7);
								gui.gb_setSpriteImage(i + 3, "enemy206.png");
							} else if (moverse > 490 + (i - 5) * 10 && moverse < 500 + (i - 5) * 10) {
								goei[i].choreography(6);
								gui.gb_setSpriteImage(i + 3, "enemy205.png");
							} else if (moverse > 500 + (i - 5) * 10 && moverse < 510 + (i - 5) * 10) {
								goei[i].choreography(5);
								gui.gb_setSpriteImage(i + 3, "enemy204.png");
							} else if (moverse > 510 + (i - 5) * 10 && moverse < 520 + (i - 5) * 10) {
								goei[i].choreography(4);
								gui.gb_setSpriteImage(i + 3, "enemy203.png");
							} else if (moverse > 520 + (i - 5) * 10 && moverse < 530 + (i - 5) * 10) {
								goei[i].choreography(3);
								gui.gb_setSpriteImage(i + 3, "enemy202.png");
							} else if (moverse > 530 + (i - 5) * 10 && moverse < 540 + (i - 5) * 10) {
								goei[i].choreography(2);
								gui.gb_setSpriteImage(i + 3, "enemy201.png");
							} else if (moverse > 540 + (i - 5) * 10 && moverse < 618 + (i - 5) * 10) {
								goei[i].choreography(1);
								gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
							} else if (moverse > 618 + (i - 5) * 10 && moverse < 712) {
								goei[i].choreography(13);
								gui.gb_setSpriteImage(i + 3, "enemy212.png");
							} else if (moverse > 725 && moverse < 735) {
								coreografiaterminada = true; // permite que el nivel comience y que los torpedos puedan
																// matar
								gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
							}

						} else if (i < 30) {
							if (moverse > 10 && moverse < 115 + (i - 10) * 10) {
								zako[i - 10].choreography(1);
								gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
							} else if (moverse > 115 + (i - 10) * 10 && moverse < 169 + (i - 10) * 10) {
								zako[i - 10].choreography(5);
								gui.gb_setSpriteImage(i + 10, "enemy304.png");
							} else if (moverse > 169 + (i - 10) * 10 && moverse < 175 + (i - 10) * 10) {
								zako[i - 10].choreography(1);
								gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
							} else if (moverse > 175 + (i - 10) * 10 && moverse < 181 + (i - 10) * 10) {
								zako[i - 10].choreography(2);
								gui.gb_setSpriteImage(i + 10, "enemy301.png");
							} else if (moverse > 181 + (i - 10) * 10 && moverse < 187 + (i - 10) * 10) {
								zako[i - 10].choreography(3);
								gui.gb_setSpriteImage(i + 10, "enemy302.png");
							} else if (moverse > 187 + (i - 10) * 10 && moverse < 193 + (i - 10) * 10) {
								zako[i - 10].choreography(4);
								gui.gb_setSpriteImage(i + 10, "enemy303.png");
							} else if (moverse > 193 + (i - 10) * 10 && moverse < 199 + (i - 10) * 10) {
								zako[i - 10].choreography(5);
								gui.gb_setSpriteImage(i + 10, "enemy304.png");
							} else if (moverse > 199 + (i - 10) * 10 && moverse < 205 + (i - 10) * 10) {
								zako[i - 10].choreography(6);
								gui.gb_setSpriteImage(i + 10, "enemy305.png");
							} else if (moverse > 205 + (i - 10) * 10 && moverse < 211 + (i - 10) * 10) {
								zako[i - 10].choreography(7);
								gui.gb_setSpriteImage(i + 10, "enemy306.png");
							} else if (moverse > 211 + (i - 10) * 10 && moverse < 217 + (i - 10) * 10) {
								zako[i - 10].choreography(8);
								gui.gb_setSpriteImage(i + 10, "enemy307.png");
							} else if (moverse > 217 + (i - 10) * 10 && moverse < 223 + (i - 10) * 10) {
								zako[i - 10].choreography(9);
								gui.gb_setSpriteImage(i + 10, "enemy308.png");
							} else if (moverse > 223 + (i - 10) * 10 && moverse < 229 + (i - 10) * 10) {
								zako[i - 10].choreography(10);
								gui.gb_setSpriteImage(i + 10, "enemy309.png");
							} else if (moverse > 229 + (i - 10) * 10 && moverse < 235 + (i - 10) * 10) {
								zako[i - 10].choreography(11);
								gui.gb_setSpriteImage(i + 10, "enemy310.png");
							} else if (moverse > 235 + (i - 10) * 10 && moverse < 241 + (i - 10) * 10) {
								zako[i - 10].choreography(12);
								gui.gb_setSpriteImage(i + 10, "enemy311.png");
							} else if (moverse > 241 + (i - 10) * 10 && moverse < 247 + (i - 10) * 10) {
								zako[i - 10].choreography(13);
								gui.gb_setSpriteImage(i + 10, "enemy312.png");
							} else if (moverse > 247 + (i - 10) * 10 && moverse < 253 + (i - 10) * 10) {
								zako[i - 10].choreography(14);
								gui.gb_setSpriteImage(i + 10, "enemy313.png");
							} else if (moverse > 253 + (i - 10) * 10 && moverse < 259 + (i - 10) * 10) {
								zako[i - 10].choreography(15);
								gui.gb_setSpriteImage(i + 10, "enemy314.png");
							} else if (moverse > 259 + (i - 10) * 10 && moverse < 265 + (i - 10) * 10) {
								zako[i - 10].choreography(16);
								gui.gb_setSpriteImage(i + 10, "enemy315.png");
							} else if (moverse > 265 + (i - 10) * 10 && moverse < 309 + (i - 10) * 10) {
								if (i < 20) {
									zako[i - 10].choreography(2);
									gui.gb_setSpriteImage(i + 10, "enemy301.png");
								} else {
									zako[i - 10].choreography(16);
									gui.gb_setSpriteImage(i + 10, "enemy315.png");
								}
							} else if (moverse > 309 + (i - 10) * 10 && moverse < 353 + (i - 10) * 10) {
								if (i < 20) {
									zako[i - 10].choreography(8);
									gui.gb_setSpriteImage(i + 10, "enemy307.png");
								} else {
									zako[i - 10].choreography(10);
									gui.gb_setSpriteImage(i + 10, "enemy309.png");
								}

							} else if (moverse > 353 + (i - 10) * 10 && moverse < 399 + (i - 10) * 10) {
								if (i < 20) {
									zako[i - 10].choreography(13);
									gui.gb_setSpriteImage(i + 10, "enemy312.png");
								} else {
									zako[i - 10].choreography(5);
									gui.gb_setSpriteImage(i + 10, "enemy304.png");
								}

							} else if (moverse > 399 + (i - 10) * 10 && moverse < 449 + (i - 10) * 10) {
								if (i < 20) {
									zako[i - 10].choreography(3);
									gui.gb_setSpriteImage(i + 10, "enemy302.png");
								} else {
									zako[i - 10].choreography(15);
									gui.gb_setSpriteImage(i + 10, "enemy314.png");
								}
							} else if (moverse > 449 + (i - 10) * 10 && moverse < 484 + (i - 10) * 10 && i < 20) {
								zako[i - 10].choreography(1);
								gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
							} else if (moverse > 449 + (i - 10) * 10 && moverse < 472 + (i - 10) * 10 && i < 30) {
								zako[i - 10].choreography(1);
								gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
							} else if (moverse > 484 + (i - 10) * 10 && moverse < 585 && i < 20) {
								zako[i - 10].choreography(13);
								gui.gb_setSpriteImage(i + 10, "enemy312.png");
							} else if (moverse > 585 && moverse < 600 && i < 20) {
								gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
								zako[i - 10].move((i - 10) * 10 + 40, 40);

							} else if (moverse > 472 + (i - 10) * 10 && moverse < 662 && i > 19 && i < 30) {
								zako[i - 10].choreography(5);
								gui.gb_setSpriteImage(i + 10, "enemy304.png");
							} else if (moverse > 662 && moverse < 665 && i > 19 && i < 30) {
								zako[i - 10].move((i - 20) * 10 + 40, 50);
								gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
							}
						} else {
							if (i < 35) {
								if (moverse > 150 && moverse < -100 + i * 10) {
									comandante[i - 30].choreography(1);
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
								} else if (moverse > -100 + i * 10 && moverse < -90 + i * 10) {
									comandante[i - 30].choreography(2);
									gui.gb_setSpriteImage(i + 20, "enemy101.png");
								} else if (moverse > -90 + i * 10 && moverse < -80 + i * 10) {
									comandante[i - 30].choreography(3);
									gui.gb_setSpriteImage(i + 20, "enemy102.png");
								} else if (moverse > -80 + i * 10 && moverse < -70 + i * 10) {
									comandante[i - 30].choreography(4);
									gui.gb_setSpriteImage(i + 20, "enemy103.png");
								} else if (moverse > -70 + i * 10 && moverse < i * 10) {
									comandante[i - 30].choreography(5);
									gui.gb_setSpriteImage(i + 20, "enemy104.png");
								} else if (moverse > i * 10 && moverse < 10 + i * 10) {
									comandante[i - 30].choreography(4);
									gui.gb_setSpriteImage(i + 20, "enemy103.png");
								} else if (moverse > 10 + i * 10 && moverse < 20 + i * 10) {
									comandante[i - 30].choreography(3);
									gui.gb_setSpriteImage(i + 20, "enemy102.png");
								} else if (moverse > 20 + i * 10 && moverse < 30 + i * 10) {
									comandante[i - 30].choreography(2);
									gui.gb_setSpriteImage(i + 20, "enemy101.png");
								} else if (moverse > 30 + i * 10 && moverse < 40 + i * 10) {
									comandante[i - 30].choreography(1);
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
								} else if (moverse > 40 + i * 10 && moverse < 50 + i * 10) {
									comandante[i - 30].choreography(16);
									gui.gb_setSpriteImage(i + 20, "enemy115.png");
								} else if (moverse > 50 + i * 10 && moverse < 60 + i * 10) {
									comandante[i - 30].choreography(15);
									gui.gb_setSpriteImage(i + 20, "enemy114.png");
								} else if (moverse > 60 + i * 10 && moverse < 70 + i * 10) {
									comandante[i - 30].choreography(14);
									gui.gb_setSpriteImage(i + 20, "enemy113.png");
								} else if (moverse > 70 + i * 10 && moverse < 140 + i * 10) {
									comandante[i - 30].choreography(13);
									gui.gb_setSpriteImage(i + 20, "enemy112.png");
								} else if (moverse > 140 + i * 10 && moverse < 150 + i * 10) {
									comandante[i - 30].choreography(14);
									gui.gb_setSpriteImage(i + 20, "enemy113.png");
								} else if (moverse > 150 + i * 10 && moverse < 160 + i * 10) {
									comandante[i - 30].choreography(15);
									gui.gb_setSpriteImage(i + 20, "enemy114.png");
								} else if (moverse > 160 + i * 10 && moverse < 170 + i * 10) {
									comandante[i - 30].choreography(16);
									gui.gb_setSpriteImage(i + 20, "enemy115.png");
								} else if (moverse > 170 + i * 10 && moverse < 210 + i * 10) {
									comandante[i - 30].choreography(1);
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
								} else if (moverse > 210 + i * 10 && moverse < 586) {
									comandante[i - 30].choreography(5);
									gui.gb_setSpriteImage(i + 20, "enemy104.png");
								} else if (moverse > 586 && moverse < 590)
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");

							} else {
								if (moverse > 150 && moverse < -100 + (i - 5) * 10) {
									comandante[i - 30].choreography(1);
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
								} else if (moverse > -100 + (i - 5) * 10 && moverse < -90 + (i - 5) * 10) {
									comandante[i - 30].choreography(16);
									gui.gb_setSpriteImage(i + 20, "enemy115.png");
								} else if (moverse > -90 + (i - 5) * 10 && moverse < -80 + (i - 5) * 10) {
									comandante[i - 30].choreography(15);
									gui.gb_setSpriteImage(i + 20, "enemy114.png");
								} else if (moverse > -80 + (i - 5) * 10 && moverse < -70 + (i - 5) * 10) {
									comandante[i - 30].choreography(14);
									gui.gb_setSpriteImage(i + 20, "enemy113.png");
								} else if (moverse > -70 + (i - 5) * 10 && moverse < (i - 5) * 10) {
									comandante[i - 30].choreography(13);
									gui.gb_setSpriteImage(i + 20, "enemy112.png");
								} else if (moverse > (i - 5) * 10 && moverse < 10 + (i - 5) * 10) {
									comandante[i - 30].choreography(14);
									gui.gb_setSpriteImage(i + 20, "enemy113.png");
								} else if (moverse > 10 + (i - 5) * 10 && moverse < 20 + (i - 5) * 10) {
									comandante[i - 30].choreography(15);
									gui.gb_setSpriteImage(i + 20, "enemy114.png");
								} else if (moverse > 20 + (i - 5) * 10 && moverse < 30 + (i - 5) * 10) {
									comandante[i - 30].choreography(16);
									gui.gb_setSpriteImage(i + 20, "enemy115.png");
								} else if (moverse > 30 + (i - 5) * 10 && moverse < 40 + (i - 5) * 10) {
									comandante[i - 30].choreography(1);
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
								} else if (moverse > 40 + (i - 5) * 10 && moverse < 50 + (i - 5) * 10) {
									comandante[i - 30].choreography(2);
									gui.gb_setSpriteImage(i + 20, "enemy101.png");
								} else if (moverse > 50 + (i - 5) * 10 && moverse < 60 + (i - 5) * 10) {
									comandante[i - 30].choreography(3);
									gui.gb_setSpriteImage(i + 20, "enemy102.png");
								} else if (moverse > 60 + (i - 5) * 10 && moverse < 70 + (i - 5) * 10) {
									comandante[i - 30].choreography(4);
									gui.gb_setSpriteImage(i + 20, "enemy103.png");
								} else if (moverse > 70 + (i - 5) * 10 && moverse < 140 + (i - 5) * 10) {
									comandante[i - 30].choreography(5);
									gui.gb_setSpriteImage(i + 20, "enemy104.png");
								} else if (moverse > 140 + (i - 5) * 10 && moverse < 150 + (i - 5) * 10) {
									comandante[i - 30].choreography(4);
									gui.gb_setSpriteImage(i + 20, "enemy103.png");
								} else if (moverse > 150 + (i - 5) * 10 && moverse < 160 + (i - 5) * 10) {
									comandante[i - 30].choreography(3);
									gui.gb_setSpriteImage(i + 20, "enemy102.png");
								} else if (moverse > 160 + (i - 5) * 10 && moverse < 170 + (i - 5) * 10) {
									comandante[i - 30].choreography(2);
									gui.gb_setSpriteImage(i + 20, "enemy101.png");
								} else if (moverse > 170 + (i - 5) * 10 && moverse < 210 + (i - 5) * 10) {
									comandante[i - 30].choreography(1);
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
								} else if (moverse > 210 + (i - 5) * 10 && moverse < 578) {
									comandante[i - 30].choreography(13);
									gui.gb_setSpriteImage(i + 20, "enemy112.png");
								} else if (moverse > 578 && moverse < 580)
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
							}
						}
					}
					moverse = moverse + 2;

					// MOVIMIENTO TORPEDOS

					for (int j = 100; j < contador; j++) {
						torpedos[j - 100].move(torpedos[j - 100].getX(), (torpedos[j - 100].getY() - 4));
					}
					if (coreografiaterminada) {

						// MOVIMIENTO ENEMIGOS
						for (int i = 3; i < goei.length + 3; i++) {
							if (goei[7].getX() == (i + 1) * 22) { // si el enemigo llega al limite, cambia de sentido
								move = -1;
								yGoei++; // cada vez que llegan al limite, las "y" aumentan
								yZako++;
								yZako2++; // hay dos "y" distintas para zako ya que ocupan dos filas
								yComandante++;

							} else if (goei[4].getX() == i + 45) { // igual pero el movimiento se realiza hacia el
																	// otro lado
								move = 1;
								yGoei++;
								yZako++;
								yZako2++;
								yComandante++;
							}
						}

						for (int i = 0; i < goei.length; i++) { // movimiento del enemigo rojo
							if (i != enemigoAleatorio)
								goei[i].move((goei[i].getX() + move), yGoei);
							else if (i == enemigoAleatorio) {
								goei[enemigoAleatorio].move((goei[enemigoAleatorio].getX() + move),
										(goei[enemigoAleatorio].getY() + 1)); // "ataque" del enemigo rojo
								if (contador2 % 18 == 0)
									gui.gb_setSpriteImage(i + 3, "enemy200.png");
								else if (contador2 % 9 == 0)
									gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
								if (goei[enemigoAleatorio].getY() > 220) {
									gui.gb_setSpriteImage(enemigoAleatorio + 3, "enemy2G1.png");
									enemigoAleatorio = (int) (Math.random() * 10);
								}
								if (goei[enemigoAleatorio].getX() < (x + 5) && goei[enemigoAleatorio].getX() > (x - 5)
										&& goei[enemigoAleatorio].getY() > 195 && goei[enemigoAleatorio].getY() < 205
										&& goei[enemigoAleatorio].getHealth() == 0) {
									player.setHealth(0);
								}
							}

						}
						for (int i = 0; i < zako.length; i++) { // movimiento del enemigo amarillo
							if (i != enemigoAleatorio2) {
								if (i < 10)
									zako[i].move((zako[i].getX() + move), yZako);
								else
									zako[i].move((zako[i].getX() + move), yZako2);
							} else if (i == enemigoAleatorio2) {
								zako[enemigoAleatorio2].move((zako[enemigoAleatorio2].getX() + move),
										(zako[enemigoAleatorio2].getY() + 2)); // "ataque" del enemigo amarillo
								if (zako[enemigoAleatorio2].getY() > 220)
									enemigoAleatorio2 = (int) (Math.random() * 20);

								if (zako[enemigoAleatorio2].getX() < (x + 5) && zako[enemigoAleatorio2].getX() > (x - 5)
										&& zako[enemigoAleatorio2].getY() > 195 && zako[enemigoAleatorio2].getY() < 205
										&& zako[enemigoAleatorio2].getHealth() == 0) {
									player.setHealth(0);
								}
							}
						}

						for (int i = 50; i < comandante.length + 50; i++) {
							comandante[i - 50].move((comandante[i - 50].getX() + move), yComandante);
						}

						// DESAPARICION DE LOS ENEMIGOS CUANDO EXPLOTAN

						for (int j = 100; j < contador; j++) {
							for (int i = 3; i < goei.length + 3; i++) {
								if (torpedos[j - 100].getX() < goei[i - 3].getX() + 5
										&& torpedos[j - 100].getX() > goei[i - 3].getX() - 5
										&& torpedos[j - 100].getY() < goei[i - 3].getY() + 5
										&& torpedos[j - 100].getY() > goei[i - 3].getY() - 5) {
									if ((torpedos[j - 100].getUsed() == 1) && goei[i - 3].getHealth() == 0) {
										contar[i - 3] = true; // este boolean permite hacer la explosion
										gui.gb_setSpriteVisible(torpedos[j - 100].getId(), false); // el enemigo
																									// desaparece
										torpedos[j - 100].setUsed(torpedos[j - 100].getUsed() - 1); // el torpedo ya ha
																									// sido
																									// usado y
																									// desaparece
										goei[i - 3].setHealth(goei[i - 3].getHealth() - 1); // el enemigo pierde la vida
										puntos = puntos + 250;
										aciertos++;
										gui.gb_println("El enemigo " + (nivel * 1000 + i - 3)
												+ " ha muerto. Ganas 250 puntos"); // se
																					// imprime
																					// en
																					// consola
									}
								}
							}
							for (int i = 20; i < zako.length + 20; i++) { // hacemos lo mismo con zako
								if (i < 30) {
									if (torpedos[j - 100].getX() < zako[i - 20].getX() + 5
											&& torpedos[j - 100].getX() > zako[i - 20].getX() - 5
											&& torpedos[j - 100].getY() < zako[i - 20].getY() + 5
											&& torpedos[j - 100].getY() > zako[i - 20].getY() - 5) {
										if ((torpedos[j - 100].getUsed() == 1) && zako[i - 20].getHealth() == 0) {
											contar[i - 10] = true;
											gui.gb_setSpriteVisible(torpedos[j - 100].getId(), false);
											torpedos[j - 100].setUsed(torpedos[j - 100].getUsed() - 1);
											zako[i - 20].setHealth(zako[i - 13].getHealth() - 1);
											puntos = puntos + 100;
											aciertos++;
											gui.gb_println("El enemigo " + (nivel * 1000 + i)
													+ " ha muerto. Ganas 100 puntos");
										}
									}

								} else {
									if (torpedos[j - 100].getX() < zako[i - 20].getX() + 5 // lo mismo pero con los zako
																							// de
																							// segunda fila
											&& torpedos[j - 100].getX() > zako[i - 20].getX() - 5
											&& torpedos[j - 100].getY() < zako[i - 20].getY() + 5
											&& torpedos[j - 100].getY() > zako[i - 20].getY() - 5) {
										if ((torpedos[j - 100].getUsed() == 1) && zako[i - 20].getHealth() == 0) {
											contar[i - 10] = true;
											gui.gb_setSpriteVisible(torpedos[j - 100].getId(), false);
											torpedos[j - 100].setUsed(torpedos[j - 100].getUsed() - 1);
											zako[i - 20].setHealth(zako[i - 20].getHealth() - 1);
											puntos = puntos + 100;
											aciertos++;
											gui.gb_println("El enemigo " + (nivel * 1000 + i)
													+ " ha muerto. Ganas 100 puntos");
										}
									}
								}

							}
							for (int i = 50; i < comandante.length + 50; i++) {
								if (torpedos[j - 100].getX() < comandante[i - 50].getX() + 5
										&& torpedos[j - 100].getX() > comandante[i - 50].getX() - 5
										&& torpedos[j - 100].getY() < yComandante + 5
										&& torpedos[j - 100].getY() > yComandante - 5) {
									if (comandante[i - 50].getHealth() < 1) {
										if ((torpedos[j - 100].getUsed() == 1) && comandante[i - 50].getHealth() == 0) {
											contar[i - 20] = true;
											gui.gb_setSpriteVisible(j, false);
											torpedos[j - 100].setUsed(torpedos[j - 100].getUsed() - 1);
											comandante[i - 50].setHealth(comandante[i - 50].getHealth() - 1);
											aciertos++;
											muertesverde++;
											gui.gb_println(
													"El enemigo " + (nivel * 1000 + i - 20) + " ha muerto. Te quedan "
															+ (6 - muertesverde) + " jefes por derrotar");
										}
									}
									if ((torpedos[j - 100].getUsed() == 1) && comandante[i - 50].getHealth() == 1) {
										gui.gb_setSpriteImage(i, "enemy9G0.png");
										gui.gb_setSpriteVisible(torpedos[j - 100].getId(), false);
										torpedos[j - 100].setUsed(torpedos[j - 100].getUsed() - 1);
										comandante[i - 50].setHealth(comandante[i - 50].getHealth() - 1);
										aciertos++;
									}

								}
							}
						}
						// IMAGEN DE LA EXPLOSION Y DESAPARICION CUANDO UN TORPEDO ALCANZA A UN ENEMIGO
						for (int i = 3; i < goei.length + 3; i++) {
							if (contar[i - 3]) {
								contador3[i - 3]++;
								if (contador3[i - 3] < 3)
									gui.gb_setSpriteImage(i, "explosion20.png");
								else if (contador3[i - 3] < 6)
									gui.gb_setSpriteImage(i, "explosion21.png");
								else if (contador3[i - 3] < 9)
									gui.gb_setSpriteImage(i, "explosion22.png");
								else if (contador3[i - 3] < 12)
									gui.gb_setSpriteImage(i, "explosion23.png");
								else if (contador3[i - 3] < 15)
									gui.gb_setSpriteImage(i, "explosion24.png");
								else
									gui.gb_setSpriteVisible(i, false);
							}
						}
						for (int i = 20; i < zako.length + 20; i++) {
							if (contar[i - 10]) {
								contador3[i - 10]++;
								if (contador3[i - 10] < 3)
									gui.gb_setSpriteImage(i, "explosion20.png");
								else if (contador3[i - 10] < 6)
									gui.gb_setSpriteImage(i, "explosion21.png");
								else if (contador3[i - 10] < 9)
									gui.gb_setSpriteImage(i, "explosion22.png");
								else if (contador3[i - 10] < 12)
									gui.gb_setSpriteImage(i, "explosion23.png");
								else if (contador3[i - 10] < 15)
									gui.gb_setSpriteImage(i, "explosion24.png");
								else
									gui.gb_setSpriteVisible(i, false);
							}
						}
						for (int i = 50; i < comandante.length + 50; i++) {
							if (contar[i - 20]) {
								contador3[i - 20]++;
								if (contador3[i - 20] < 3)
									gui.gb_setSpriteImage(i, "explosion20.png");
								else if (contador3[i - 20] < 6)
									gui.gb_setSpriteImage(i, "explosion21.png");
								else if (contador3[i - 20] < 9)
									gui.gb_setSpriteImage(i, "explosion22.png");
								else if (contador3[i - 20] < 12)
									gui.gb_setSpriteImage(i, "explosion23.png");
								else if (contador3[i - 20] < 15)
									gui.gb_setSpriteImage(i, "explosion24.png");
								else
									gui.gb_setSpriteVisible(i, false);
							}
						}

						// ALETEO ENEMIGOS
						for (int i = 20; i < zako.length + 20; i++) {
							if (contador2 % 20 == 0 && zako[i - 20].getHealth() == 0)
								gui.gb_setSpriteImage(i, "enemy3G0.png");
							else if (contador2 % 10 == 0)
								gui.gb_setSpriteImage(i, "enemy3G1.png");
						}
						for (int i = 50; i < comandante.length + 50; i++) {
							if (comandante[i - 50].getHealth() == 1) {
								if (contador2 % 24 == 0)
									gui.gb_setSpriteImage(i, "enemy1G0.png");
								else if (contador2 % 12 == 0)
									gui.gb_setSpriteImage(i, "enemy1G1.png");
							} else if (comandante[i - 50].getHealth() == 0) {
								if (contador2 % 24 == 0)
									gui.gb_setSpriteImage(i, "enemy9G0.png");
								else if (contador2 % 12 == 0)
									gui.gb_setSpriteImage(i, "enemy9G1.png");
							}
						}
						contador2++;

						// APARICION DE LOS TORPEDOS ENEMIGOS
						if (contadorTiempo % 56 == 0) {// bajamos el tiempo entre torpedos en comparacion con el nivel
														// anterior
							azar = (int) ((Math.random() * 10));
							if (goei[azar].getHealth() == 0) {
								gui.gb_setSpriteVisible(contadorDisparosenemigos + 600, true);
								torpedosEnemigos[contadorDisparosenemigos].move(goei[azar].getX(), goei[azar].getY());

								contadorDisparosenemigos++;
							}
						}

						if (contadorTiempo % 105 == 0) {
							azar2 = (int) ((Math.random() * 20));
							if (zako[azar2].getHealth() == 0) {
								gui.gb_setSpriteVisible(contadorDisparosenemigos + 600, true);
								torpedosEnemigos[contadorDisparosenemigos].move(zako[azar2].getX(), zako[azar2].getY());

								contadorDisparosenemigos++;
							}
						}
						if (contadorTiempo % 20 == 0) {
							azar3 = (int) ((Math.random() * 10));
							if (comandante[azar3].getHealth() == 0 || comandante[azar3].getHealth() == 1) {
								gui.gb_setSpriteVisible(contadorDisparosenemigos + 600, true);
								torpedosEnemigos[contadorDisparosenemigos].move(comandante[azar3].getX(),
										zako[azar3].getY());
								contadorDisparosenemigos++;
							}
						}
						contadorTiempo++;
						// CHOQUE DE LOS TORPEDOS ENEMIGOS CON EL JUGADOR
						for (int i = 0; i < contadorDisparosenemigos; i++) {
							torpedosEnemigos[i].move(torpedosEnemigos[i].getX(), torpedosEnemigos[i].getY() + 2);
							if (torpedosEnemigos[i].getX() < (x + 5) && torpedosEnemigos[i].getX() > (x - 5)
									&& torpedosEnemigos[i].getY() < 205 && torpedosEnemigos[i].getY() > 195) {
								if (player.getHealth() == 3 && torpedosEnemigos[i].getUsed() == 1) {
									muerto = true;
									player.setHealth(player.getHealth() - 1);
									torpedosEnemigos[i].setUsed(torpedosEnemigos[i].getUsed() - 1);
									gui.gb_setSpriteVisible(i + 600, false);
									gui.gb_setSpriteVisible(2, false);
									gui.gb_showMessageDialog("El jugador ha muerto. Has perdido una vida");
								} else if (player.getHealth() == 2 && torpedosEnemigos[i].getUsed() == 1) {
									muerto = true;
									player.setHealth(player.getHealth() - 1);
									torpedosEnemigos[i].setUsed(torpedosEnemigos[i].getUsed() - 1);
									gui.gb_setSpriteVisible(i + 600, false);
									gui.gb_setSpriteVisible(1, false);
									gui.gb_showMessageDialog("El jugador ha muerto. Has perdido una vida");
								} else if (player.getHealth() == 1 && torpedosEnemigos[i].getUsed() == 1) {
									player.setHealth(player.getHealth() - 1);
									gui.gb_setSpriteVisible(i + 600, false);
								}
							}

						}
						contadorTiempo++;

						// MUERTE DEL JUGADOR
						for (int i = 0; i < 40; i++) {
							if (i < 10) {
								if (x < goei[i].getX() + 5 && x > goei[i].getX() - 5 && goei[i].getY() < 196
										&& yGoei > 204)
									player.setHealth(0);

							} else if (i < 20) {
								if (x < zako[i - 10].getX() + 5 && x > zako[i - 10].getX() - 5 && yZako < 204
										&& yZako > 196)
									player.setHealth(0);

							} else if (i < 30) {
								if (x < zako[i - 20].getX() + 5 && x > zako[i - 20].getX() - 5 && yZako2 < 204
										&& yZako2 > 196)
									player.setHealth(0);

							} else if (i < 40) {
								if (x < comandante[i - 30].getX() + 5 && x > comandante[i - 30].getX() - 5
										&& yComandante < 204 && yComandante > 196)
									player.setHealth(0);
							}
						}
						// SI AL JUGADOR NO LE QUEDAN MAS VIDAS
						if (player.getHealth() == 0 || muerto) {
							muerte++;
							if (muerte < 3) {
								gui.gb_setSpriteImage(0, "explosion11.png");
								gui.gb_setPortraitPlayer("hit.png");
							} else if (muerte < 6)
								gui.gb_setSpriteImage(0, "explosion12.png");
							else if (muerte < 9)
								gui.gb_setSpriteImage(0, "explosion13.png");
							else if (muerte < 12)
								gui.gb_setSpriteImage(0, "explosion11.png");
							else if (muerte > 12) {
								gui.gb_setSpriteVisible(0, false);
								if (player.getHealth() != 0 && muerte > 18) {
									gui.gb_setSpriteImage(0, "player.png");
									gui.gb_setSpriteVisible(0, true);
									gui.gb_setPortraitPlayer("galagaLogo.jpg");
									muerto = false;
									muerte = 0;
								}
								if (player.getHealth() == 0 && !perdido) {
									gui.gb_showMessageDialog(
											"Ha perdido las vidas que le quedaban. Partida finalizada");
									perdido = true;
								}
							}
						}
					}
					Thread.sleep(1200 / 60); // frames por segundo
				}
			}
			// TERCER NIVEL
			if (puntos >= 7400 && muertesverde == 6 && tiempo >= 70) { // si se cumplen la condicion, se avanza al nivel
																		// 3
				nivel = 3;
				// DECIDIMOS PARA EL NIVEL QUÉ Y CUÁNTOS ENEMIGOS VAN A APARECER
				for (int i = 0; i < goei.length; i++) {
					if (i < 5)
						goei[i].move(5, i * 10 + 225);
					else
						goei[i].move(165, (i - 5) * 10 + 225);
					switch (i) {
					case 0:
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
					case 9:
						gui.gb_setSpriteVisible(i + 3, true);
						goei[i].setHealth(0);
						break;
					}
				}
				for (int i = 0; i < zako.length; i++) {
					zako[i].move(30, i * 10 + 225);

					switch (i) {
					case 0:
					case 2:
					case 3:
					case 6:
					case 7:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 16:
					case 17:
					case 18:
					case 19:
						gui.gb_setSpriteVisible(i + 20, true);
						zako[i].setHealth(0);
						break;
					}
				}
				for (int i = 0; i < comandante.length; i++) {
					if (i < 5)
						comandante[i].move(15, i * 10 + 225);
					else
						comandante[i].move(155, (i - 5) * 10 + 225);

					switch (i) {
					case 1:
					case 2:
					case 4:
					case 6:
					case 7:
					case 9:
						gui.gb_setSpriteVisible(i + 50, true);
						comandante[i].setHealth(1);
						break;
					}
				}
				yComandante = 20;
				yZako = 40;
				yZako2 = 50;
				yGoei = 30;
				contador2 = 0;
				moverse = 0;

				for (int i = 0; i < 40; i++) {
					contador3[i] = 0;
				}

				for (int i = 0; i < 40; i++) {
					contar[i] = false;
				}
				move = 1;
				muerte = 0;
				muerto = true;

				azar = 0;
				contadorTiempo = 0;
				contadorDisparosenemigos = 0;
				moverse = 0;
				coreografiaterminada = false;

				while (puntos >= 7400 && !perdido) {

					gui.gb_setValueLevel(nivel);
					gui.gb_setValuePointsUp(puntos);
					gui.gb_setValueAbility2(aciertos);
					gui.gb_setValueHealthCurrent(player.getHealth());

					// MOVIMIENTO DE LA NAVE

					switch (gui.gb_getLastAction()) {
					case "left":
						if (x == 7)
							x = 7;
						else
							x -= 3;
						break;
					case "right":
						if (x == 163)
							x = 163;
						else
							x += 3;
						break;
					case "space":
						gui.gb_setSpriteVisible(contador, true);
						torpedos[contador - 100].move(x, 193);
						contador++;
						gui.gb_setValueAbility1(contador - 100);
						break;
					}
					gui.gb_moveSpriteCoord(0, x, 200);// Coordenadas de la nave

					gui.gb_moveSpriteCoord(1, 6, 214);// Coordenadas de las naves que indican las vidas restantes
					gui.gb_moveSpriteCoord(2, 17, 214);
					// COREOGRAFIA
					for (int i = 0; i < 40; i++) {
						if (i < 5) {
							if (moverse > 100 && moverse < 160 + i * 10) {
								goei[i].choreography(1);
								gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
							} else if (moverse > 160 + i * 10 && moverse < 320 + i * 10) {
								goei[i].choreography(5);
								gui.gb_setSpriteImage(i + 3, "enemy204.png");
							} else if (moverse > 320 + i * 10 && moverse < 420 + i * 10) {
								goei[i].choreography(15);
								gui.gb_setSpriteImage(i + 3, "enemy214.png");
							} else if (moverse > 420 + i * 10 && moverse < 430 + i * 10) {
								goei[i].choreography(5);
								gui.gb_setSpriteImage(i + 3, "enemy204.png");
							} else if (moverse > 430 + i * 10 && moverse < 440 + i * 10) {
								goei[i].choreography(6);
								gui.gb_setSpriteImage(i + 3, "enemy205.png");
							} else if (moverse > 440 + i * 10 && moverse < 450 + i * 10) {
								goei[i].choreography(7);
								gui.gb_setSpriteImage(i + 3, "enemy206.png");
							} else if (moverse > 450 + i * 10 && moverse < 460 + i * 10) {
								goei[i].choreography(8);
								gui.gb_setSpriteImage(i + 3, "enemy207.png");
							} else if (moverse > 460 + i * 10 && moverse < 470 + i * 10) {
								goei[i].choreography(9);
								gui.gb_setSpriteImage(i + 3, "enemy208.png");
							} else if (moverse > 470 + i * 10 && moverse < 480 + i * 10) {
								goei[i].choreography(10);
								gui.gb_setSpriteImage(i + 3, "enemy209.png");
							} else if (moverse > 480 + i * 10 && moverse < 490 + i * 10) {
								goei[i].choreography(11);
								gui.gb_setSpriteImage(i + 3, "enemy210.png");
							} else if (moverse > 490 + i * 10 && moverse < 500 + i * 10) {
								goei[i].choreography(12);
								gui.gb_setSpriteImage(i + 3, "enemy211.png");
							} else if (moverse > 500 + i * 10 && moverse < 510 + i * 10) {
								goei[i].choreography(13);
								gui.gb_setSpriteImage(i + 3, "enemy212.png");
							} else if (moverse > 510 + i * 10 && moverse < 520 + i * 10) {
								goei[i].choreography(14);
								gui.gb_setSpriteImage(i + 3, "enemy213.png");
							} else if (moverse > 520 + i * 10 && moverse < 530 + i * 10) {
								goei[i].choreography(15);
								gui.gb_setSpriteImage(i + 3, "enemy214.png");
							} else if (moverse > 530 + i * 10 && moverse < 540 + i * 10) {
								goei[i].choreography(16);
								gui.gb_setSpriteImage(i + 3, "enemy215.png");
							} else if (moverse > 540 + i * 10 && moverse < 618 + i * 10) {
								goei[i].choreography(1);
								gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
							} else if (moverse > 618 + i * 10 && moverse < 712) {
								goei[i].choreography(5);
								gui.gb_setSpriteImage(i + 3, "enemy204.png");
							} else if (moverse > 735 && moverse < 740) {
								gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
							}

						} else if (i < 10) {
							if (moverse > 100 && moverse < 160 + (i - 5) * 10) {
								goei[i].choreography(1);
								gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
							} else if (moverse > 145 + (i - 5) * 10 && moverse < 320 + (i - 5) * 10) {
								goei[i].choreography(13);
								gui.gb_setSpriteImage(i + 3, "enemy212.png");
							} else if (moverse > 320 + (i - 5) * 10 && moverse < 420 + (i - 5) * 10) {
								goei[i].choreography(3);
								gui.gb_setSpriteImage(i + 3, "enemy202.png");
							} else if (moverse > 420 + (i - 5) * 10 && moverse < 430 + (i - 5) * 10) {
								goei[i].choreography(13);
								gui.gb_setSpriteImage(i + 3, "enemy212.png");
							} else if (moverse > 430 + (i - 5) * 10 && moverse < 440 + (i - 5) * 10) {
								goei[i].choreography(12);
								gui.gb_setSpriteImage(i + 3, "enemy211.png");
							} else if (moverse > 440 + (i - 5) * 10 && moverse < 450 + (i - 5) * 10) {
								goei[i].choreography(11);
								gui.gb_setSpriteImage(i + 3, "enemy210.png");
							} else if (moverse > 450 + (i - 5) * 10 && moverse < 460 + (i - 5) * 10) {
								goei[i].choreography(10);
								gui.gb_setSpriteImage(i + 3, "enemy209.png");
							} else if (moverse > 460 + (i - 5) * 10 && moverse < 470 + (i - 5) * 10) {
								goei[i].choreography(9);
								gui.gb_setSpriteImage(i + 3, "enemy208.png");
							} else if (moverse > 470 + (i - 5) * 10 && moverse < 480 + (i - 5) * 10) {
								goei[i].choreography(8);
								gui.gb_setSpriteImage(i + 3, "enemy207.png");
							} else if (moverse > 480 + (i - 5) * 10 && moverse < 490 + (i - 5) * 10) {
								goei[i].choreography(7);
								gui.gb_setSpriteImage(i + 3, "enemy206.png");
							} else if (moverse > 490 + (i - 5) * 10 && moverse < 500 + (i - 5) * 10) {
								goei[i].choreography(6);
								gui.gb_setSpriteImage(i + 3, "enemy205.png");
							} else if (moverse > 500 + (i - 5) * 10 && moverse < 510 + (i - 5) * 10) {
								goei[i].choreography(5);
								gui.gb_setSpriteImage(i + 3, "enemy204.png");
							} else if (moverse > 510 + (i - 5) * 10 && moverse < 520 + (i - 5) * 10) {
								goei[i].choreography(4);
								gui.gb_setSpriteImage(i + 3, "enemy203.png");
							} else if (moverse > 520 + (i - 5) * 10 && moverse < 530 + (i - 5) * 10) {
								goei[i].choreography(3);
								gui.gb_setSpriteImage(i + 3, "enemy202.png");
							} else if (moverse > 530 + (i - 5) * 10 && moverse < 540 + (i - 5) * 10) {
								goei[i].choreography(2);
								gui.gb_setSpriteImage(i + 3, "enemy201.png");
							} else if (moverse > 540 + (i - 5) * 10 && moverse < 618 + (i - 5) * 10) {
								goei[i].choreography(1);
								gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
							} else if (moverse > 618 + (i - 5) * 10 && moverse < 712) {
								goei[i].choreography(13);
								gui.gb_setSpriteImage(i + 3, "enemy212.png");
							} else if (moverse > 725 && moverse < 735) {
								coreografiaterminada = true; // permite que el nivel comience y que los torpedos puedan
																// matar
								gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
							}

						} else if (i < 30) {
							if (moverse > 10 && moverse < 115 + (i - 10) * 10) {
								zako[i - 10].choreography(1);
								gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
							} else if (moverse > 115 + (i - 10) * 10 && moverse < 169 + (i - 10) * 10) {
								zako[i - 10].choreography(5);
								gui.gb_setSpriteImage(i + 10, "enemy304.png");
							} else if (moverse > 169 + (i - 10) * 10 && moverse < 175 + (i - 10) * 10) {
								zako[i - 10].choreography(1);
								gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
							} else if (moverse > 175 + (i - 10) * 10 && moverse < 181 + (i - 10) * 10) {
								zako[i - 10].choreography(2);
								gui.gb_setSpriteImage(i + 10, "enemy301.png");
							} else if (moverse > 181 + (i - 10) * 10 && moverse < 187 + (i - 10) * 10) {
								zako[i - 10].choreography(3);
								gui.gb_setSpriteImage(i + 10, "enemy302.png");
							} else if (moverse > 187 + (i - 10) * 10 && moverse < 193 + (i - 10) * 10) {
								zako[i - 10].choreography(4);
								gui.gb_setSpriteImage(i + 10, "enemy303.png");
							} else if (moverse > 193 + (i - 10) * 10 && moverse < 199 + (i - 10) * 10) {
								zako[i - 10].choreography(5);
								gui.gb_setSpriteImage(i + 10, "enemy304.png");
							} else if (moverse > 199 + (i - 10) * 10 && moverse < 205 + (i - 10) * 10) {
								zako[i - 10].choreography(6);
								gui.gb_setSpriteImage(i + 10, "enemy305.png");
							} else if (moverse > 205 + (i - 10) * 10 && moverse < 211 + (i - 10) * 10) {
								zako[i - 10].choreography(7);
								gui.gb_setSpriteImage(i + 10, "enemy306.png");
							} else if (moverse > 211 + (i - 10) * 10 && moverse < 217 + (i - 10) * 10) {
								zako[i - 10].choreography(8);
								gui.gb_setSpriteImage(i + 10, "enemy307.png");
							} else if (moverse > 217 + (i - 10) * 10 && moverse < 223 + (i - 10) * 10) {
								zako[i - 10].choreography(9);
								gui.gb_setSpriteImage(i + 10, "enemy308.png");
							} else if (moverse > 223 + (i - 10) * 10 && moverse < 229 + (i - 10) * 10) {
								zako[i - 10].choreography(10);
								gui.gb_setSpriteImage(i + 10, "enemy309.png");
							} else if (moverse > 229 + (i - 10) * 10 && moverse < 235 + (i - 10) * 10) {
								zako[i - 10].choreography(11);
								gui.gb_setSpriteImage(i + 10, "enemy310.png");
							} else if (moverse > 235 + (i - 10) * 10 && moverse < 241 + (i - 10) * 10) {
								zako[i - 10].choreography(12);
								gui.gb_setSpriteImage(i + 10, "enemy311.png");
							} else if (moverse > 241 + (i - 10) * 10 && moverse < 247 + (i - 10) * 10) {
								zako[i - 10].choreography(13);
								gui.gb_setSpriteImage(i + 10, "enemy312.png");
							} else if (moverse > 247 + (i - 10) * 10 && moverse < 253 + (i - 10) * 10) {
								zako[i - 10].choreography(14);
								gui.gb_setSpriteImage(i + 10, "enemy313.png");
							} else if (moverse > 253 + (i - 10) * 10 && moverse < 259 + (i - 10) * 10) {
								zako[i - 10].choreography(15);
								gui.gb_setSpriteImage(i + 10, "enemy314.png");
							} else if (moverse > 259 + (i - 10) * 10 && moverse < 265 + (i - 10) * 10) {
								zako[i - 10].choreography(16);
								gui.gb_setSpriteImage(i + 10, "enemy315.png");
							} else if (moverse > 265 + (i - 10) * 10 && moverse < 309 + (i - 10) * 10) {
								if (i < 20) {
									zako[i - 10].choreography(2);
									gui.gb_setSpriteImage(i + 10, "enemy301.png");
								} else {
									zako[i - 10].choreography(16);
									gui.gb_setSpriteImage(i + 10, "enemy315.png");
								}
							} else if (moverse > 309 + (i - 10) * 10 && moverse < 353 + (i - 10) * 10) {
								if (i < 20) {
									zako[i - 10].choreography(8);
									gui.gb_setSpriteImage(i + 10, "enemy307.png");
								} else {
									zako[i - 10].choreography(10);
									gui.gb_setSpriteImage(i + 10, "enemy309.png");
								}

							} else if (moverse > 353 + (i - 10) * 10 && moverse < 399 + (i - 10) * 10) {
								if (i < 20) {
									zako[i - 10].choreography(13);
									gui.gb_setSpriteImage(i + 10, "enemy312.png");
								} else {
									zako[i - 10].choreography(5);
									gui.gb_setSpriteImage(i + 10, "enemy304.png");
								}

							} else if (moverse > 399 + (i - 10) * 10 && moverse < 449 + (i - 10) * 10) {
								if (i < 20) {
									zako[i - 10].choreography(3);
									gui.gb_setSpriteImage(i + 10, "enemy302.png");
								} else {
									zako[i - 10].choreography(15);
									gui.gb_setSpriteImage(i + 10, "enemy314.png");
								}
							} else if (moverse > 449 + (i - 10) * 10 && moverse < 484 + (i - 10) * 10 && i < 20) {
								zako[i - 10].choreography(1);
								gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
							} else if (moverse > 449 + (i - 10) * 10 && moverse < 472 + (i - 10) * 10 && i < 30) {
								zako[i - 10].choreography(1);
								gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
							} else if (moverse > 484 + (i - 10) * 10 && moverse < 585 && i < 20) {
								zako[i - 10].choreography(13);
								gui.gb_setSpriteImage(i + 10, "enemy312.png");
							} else if (moverse > 585 && moverse < 600 && i < 20) {
								gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
								zako[i - 10].move((i - 10) * 10 + 40, 40);
							} else if (moverse > 472 + (i - 10) * 10 && moverse < 662 && i > 19 && i < 30) {
								zako[i - 10].choreography(5);
								gui.gb_setSpriteImage(i + 10, "enemy304.png");
							} else if (moverse > 662 && moverse < 665 && i > 19 && i < 30) {
								gui.gb_setSpriteImage(i + 10, "enemy3G0.png");
								zako[i - 10].move((i - 20) * 10 + 40, 50);
							}
						} else {
							if (i < 35) {
								if (moverse > 150 && moverse < -100 + i * 10) {
									comandante[i - 30].choreography(1);
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
								} else if (moverse > -100 + i * 10 && moverse < -90 + i * 10) {
									comandante[i - 30].choreography(2);
									gui.gb_setSpriteImage(i + 20, "enemy101.png");
								} else if (moverse > -90 + i * 10 && moverse < -80 + i * 10) {
									comandante[i - 30].choreography(3);
									gui.gb_setSpriteImage(i + 20, "enemy102.png");
								} else if (moverse > -80 + i * 10 && moverse < -70 + i * 10) {
									comandante[i - 30].choreography(4);
									gui.gb_setSpriteImage(i + 20, "enemy103.png");
								} else if (moverse > -70 + i * 10 && moverse < i * 10) {
									comandante[i - 30].choreography(5);
									gui.gb_setSpriteImage(i + 20, "enemy104.png");
								} else if (moverse > i * 10 && moverse < 10 + i * 10) {
									comandante[i - 30].choreography(4);
									gui.gb_setSpriteImage(i + 20, "enemy103.png");
								} else if (moverse > 10 + i * 10 && moverse < 20 + i * 10) {
									comandante[i - 30].choreography(3);
									gui.gb_setSpriteImage(i + 20, "enemy102.png");
								} else if (moverse > 20 + i * 10 && moverse < 30 + i * 10) {
									comandante[i - 30].choreography(2);
									gui.gb_setSpriteImage(i + 20, "enemy101.png");
								} else if (moverse > 30 + i * 10 && moverse < 40 + i * 10) {
									comandante[i - 30].choreography(1);
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
								} else if (moverse > 40 + i * 10 && moverse < 50 + i * 10) {
									comandante[i - 30].choreography(16);
									gui.gb_setSpriteImage(i + 20, "enemy115.png");
								} else if (moverse > 50 + i * 10 && moverse < 60 + i * 10) {
									comandante[i - 30].choreography(15);
									gui.gb_setSpriteImage(i + 20, "enemy114.png");
								} else if (moverse > 60 + i * 10 && moverse < 70 + i * 10) {
									comandante[i - 30].choreography(14);
									gui.gb_setSpriteImage(i + 20, "enemy113.png");
								} else if (moverse > 70 + i * 10 && moverse < 140 + i * 10) {
									comandante[i - 30].choreography(13);
									gui.gb_setSpriteImage(i + 20, "enemy112.png");
								} else if (moverse > 140 + i * 10 && moverse < 150 + i * 10) {
									comandante[i - 30].choreography(14);
									gui.gb_setSpriteImage(i + 20, "enemy113.png");
								} else if (moverse > 150 + i * 10 && moverse < 160 + i * 10) {
									comandante[i - 30].choreography(15);
									gui.gb_setSpriteImage(i + 20, "enemy114.png");
								} else if (moverse > 160 + i * 10 && moverse < 170 + i * 10) {
									comandante[i - 30].choreography(16);
									gui.gb_setSpriteImage(i + 20, "enemy115.png");
								} else if (moverse > 170 + i * 10 && moverse < 210 + i * 10) {
									comandante[i - 30].choreography(1);
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
								} else if (moverse > 210 + i * 10 && moverse < 576) {
									comandante[i - 30].choreography(5);
									gui.gb_setSpriteImage(i + 20, "enemy104.png");
								} else if (moverse > 576 && moverse < 580)
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");

							} else {
								if (moverse > 150 && moverse < -100 + (i - 5) * 10) {
									comandante[i - 30].choreography(1);
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
								} else if (moverse > -100 + (i - 5) * 10 && moverse < -90 + (i - 5) * 10) {
									comandante[i - 30].choreography(16);
									gui.gb_setSpriteImage(i + 20, "enemy115.png");
								} else if (moverse > -90 + (i - 5) * 10 && moverse < -80 + (i - 5) * 10) {
									comandante[i - 30].choreography(15);
									gui.gb_setSpriteImage(i + 20, "enemy114.png");
								} else if (moverse > -80 + (i - 5) * 10 && moverse < -70 + (i - 5) * 10) {
									comandante[i - 30].choreography(14);
									gui.gb_setSpriteImage(i + 20, "enemy113.png");
								} else if (moverse > -70 + (i - 5) * 10 && moverse < (i - 5) * 10) {
									comandante[i - 30].choreography(13);
									gui.gb_setSpriteImage(i + 20, "enemy112.png");
								} else if (moverse > (i - 5) * 10 && moverse < 10 + (i - 5) * 10) {
									comandante[i - 30].choreography(14);
									gui.gb_setSpriteImage(i + 20, "enemy113.png");
								} else if (moverse > 10 + (i - 5) * 10 && moverse < 20 + (i - 5) * 10) {
									comandante[i - 30].choreography(15);
									gui.gb_setSpriteImage(i + 20, "enemy114.png");
								} else if (moverse > 20 + (i - 5) * 10 && moverse < 30 + (i - 5) * 10) {
									comandante[i - 30].choreography(16);
									gui.gb_setSpriteImage(i + 20, "enemy115.png");
								} else if (moverse > 30 + (i - 5) * 10 && moverse < 40 + (i - 5) * 10) {
									comandante[i - 30].choreography(1);
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
								} else if (moverse > 40 + (i - 5) * 10 && moverse < 50 + (i - 5) * 10) {
									comandante[i - 30].choreography(2);
									gui.gb_setSpriteImage(i + 20, "enemy101.png");
								} else if (moverse > 50 + (i - 5) * 10 && moverse < 60 + (i - 5) * 10) {
									comandante[i - 30].choreography(3);
									gui.gb_setSpriteImage(i + 20, "enemy102.png");
								} else if (moverse > 60 + (i - 5) * 10 && moverse < 70 + (i - 5) * 10) {
									comandante[i - 30].choreography(4);
									gui.gb_setSpriteImage(i + 20, "enemy103.png");
								} else if (moverse > 70 + (i - 5) * 10 && moverse < 140 + (i - 5) * 10) {
									comandante[i - 30].choreography(5);
									gui.gb_setSpriteImage(i + 20, "enemy104.png");
								} else if (moverse > 140 + (i - 5) * 10 && moverse < 150 + (i - 5) * 10) {
									comandante[i - 30].choreography(4);
									gui.gb_setSpriteImage(i + 20, "enemy103.png");
								} else if (moverse > 150 + (i - 5) * 10 && moverse < 160 + (i - 5) * 10) {
									comandante[i - 30].choreography(3);
									gui.gb_setSpriteImage(i + 20, "enemy102.png");
								} else if (moverse > 160 + (i - 5) * 10 && moverse < 170 + (i - 5) * 10) {
									comandante[i - 30].choreography(2);
									gui.gb_setSpriteImage(i + 20, "enemy101.png");
								} else if (moverse > 170 + (i - 5) * 10 && moverse < 210 + (i - 5) * 10) {
									comandante[i - 30].choreography(1);
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
								} else if (moverse > 210 + (i - 5) * 10 && moverse < 578) {
									comandante[i - 30].choreography(13);
									gui.gb_setSpriteImage(i + 20, "enemy112.png");
								} else if (moverse > 578 && moverse < 580)
									gui.gb_setSpriteImage(i + 20, "enemy1G0.png");
							}
						}
					}
					moverse = moverse + 2;
					// MOVIMIENTO TORPEDOS

					for (int j = 100; j < contador; j++) {
						torpedos[j - 100].move(torpedos[j - 100].getX(), (torpedos[j - 100].getY() - 4));
					}
					if (coreografiaterminada) {

						// MOVIMIENTO ENEMIGOS
						for (int i = 3; i < goei.length + 3; i++) {
							if (goei[7].getX() == (i + 1) * 22) { // si el enemigo llega al limite, cambia de sentido
								move = -1;
								yGoei++; // cada vez que llegan al limite, las "y" aumenta
								yZako++;
								yZako2++; // hay dos "y" distintas para zako porque son dos filas distintas
								yComandante++;
							} else if (goei[4].getX() == i + 45) { // igual pero el movimiento se realiza al otro lado
								move = 1;
								yGoei++;
								yZako++;
								yZako2++;
								yComandante++;
							}
						}

						for (int i = 0; i < goei.length; i++) { // movimiento del enemigo rojo
							if (i != enemigoAleatorio)
								goei[i].move((goei[i].getX() + move), yGoei);
							else if (i == enemigoAleatorio) {
								goei[enemigoAleatorio].move((goei[enemigoAleatorio].getX() + move),
										(goei[enemigoAleatorio].getY() + 1)); // "ataque" del enemigo rojo
								if (contador2 % 18 == 0)
									gui.gb_setSpriteImage(i + 3, "enemy200.png");
								else if (contador2 % 9 == 0)
									gui.gb_setSpriteImage(i + 3, "enemy2G0.png");
								if (goei[enemigoAleatorio].getY() > 220) {
									gui.gb_setSpriteImage(enemigoAleatorio + 3, "enemy2G1.png");
									enemigoAleatorio = (int) (Math.random() * 10);
								}
								if (goei[enemigoAleatorio].getX() < (x + 5) && goei[enemigoAleatorio].getX() > (x - 5)
										&& goei[enemigoAleatorio].getY() > 195 && goei[enemigoAleatorio].getY() < 205
										&& goei[enemigoAleatorio].getHealth() == 0) {
									player.setHealth(0);
								}
							}

						}
						for (int i = 0; i < zako.length; i++) { // movimiento del enemigo amarillo
							if (i != enemigoAleatorio2) {
								if (i < 10)
									zako[i].move((zako[i].getX() + move), yZako);
								else
									zako[i].move((zako[i].getX() + move), yZako2);
							} else if (i == enemigoAleatorio2) {
								zako[enemigoAleatorio2].move((zako[enemigoAleatorio2].getX() + move),
										(zako[enemigoAleatorio2].getY() + 2)); // "ataque" del enemigo amarillo
								if (zako[enemigoAleatorio2].getY() > 220)
									enemigoAleatorio2 = (int) (Math.random() * 20);

								if (zako[enemigoAleatorio2].getX() < (x + 5) && zako[enemigoAleatorio2].getX() > (x - 5)
										&& zako[enemigoAleatorio2].getY() > 195 && zako[enemigoAleatorio2].getY() < 205
										&& zako[enemigoAleatorio2].getHealth() == 0) {
									player.setHealth(0);
								}
							}
						}

						for (int i = 50; i < comandante.length + 50; i++) {
							comandante[i - 50].move((comandante[i - 50].getX() + move), yComandante);
						}

						// DESAPARICION DE LOS ENEMIGOS CUANDO EXPLOTAN

						for (int j = 100; j < contador; j++) {
							for (int i = 3; i < goei.length + 3; i++) {
								if (torpedos[j - 100].getX() < goei[i - 3].getX() + 5
										&& torpedos[j - 100].getX() > goei[i - 3].getX() - 5
										&& torpedos[j - 100].getY() < goei[i - 3].getY() + 5
										&& torpedos[j - 100].getY() > goei[i - 3].getY() - 5) {
									if ((torpedos[j - 100].getUsed() == 1) && goei[i - 3].getHealth() == 0) {
										contar[i - 3] = true; // este boolean permite hacer la explosion
										gui.gb_setSpriteVisible(torpedos[j - 100].getId(), false); // el enemigo
																									// desaparece
										torpedos[j - 100].setUsed(torpedos[j - 100].getUsed() - 1); // el torpedo ya ha
																									// sido
																									// usado y
																									// desaparece
										goei[i - 3].setHealth(goei[i - 3].getHealth() - 1); // el enemigo pierde la vida
										puntos = puntos + 250;
										aciertos++;
										gui.gb_println("El enemigo " + (nivel * 1000 + i - 3)
												+ " ha muerto. Ganas 250 puntos"); // se
																					// imprime
																					// en
																					// consola
									}
								}
							}
							for (int i = 20; i < zako.length + 20; i++) { // hacemos lo mismo con zako
								if (i < 30) {
									if (torpedos[j - 100].getX() < zako[i - 20].getX() + 5
											&& torpedos[j - 100].getX() > zako[i - 20].getX() - 5
											&& torpedos[j - 100].getY() < zako[i - 20].getY() + 5
											&& torpedos[j - 100].getY() > zako[i - 20].getY() - 5) {
										if ((torpedos[j - 100].getUsed() == 1) && zako[i - 20].getHealth() == 0) {
											contar[i - 10] = true;
											gui.gb_setSpriteVisible(torpedos[j - 100].getId(), false);
											torpedos[j - 100].setUsed(torpedos[j - 100].getUsed() - 1);
											zako[i - 20].setHealth(zako[i - 13].getHealth() - 1);
											puntos = puntos + 100;
											aciertos++;
											gui.gb_println("El enemigo " + (nivel * 1000 + i)
													+ " ha muerto. Ganas 100 puntos");
										}
									}

								} else {
									if (torpedos[j - 100].getX() < zako[i - 20].getX() + 5 // lo mismo pero con los zako
																							// de
																							// segunda fila
											&& torpedos[j - 100].getX() > zako[i - 20].getX() - 5
											&& torpedos[j - 100].getY() < zako[i - 20].getY() + 5
											&& torpedos[j - 100].getY() > zako[i - 20].getY() - 5) {
										if ((torpedos[j - 100].getUsed() == 1) && zako[i - 20].getHealth() == 0) {
											contar[i - 10] = true;
											gui.gb_setSpriteVisible(torpedos[j - 100].getId(), false);
											torpedos[j - 100].setUsed(torpedos[j - 100].getUsed() - 1);
											zako[i - 20].setHealth(zako[i - 20].getHealth() - 1);
											puntos = puntos + 100;
											aciertos++;
											gui.gb_println("El enemigo " + (nivel * 1000 + i)
													+ " ha muerto. Ganas 100 puntos");
										}
									}
								}

							}
							for (int i = 50; i < comandante.length + 50; i++) {
								if (torpedos[j - 100].getX() < comandante[i - 50].getX() + 5
										&& torpedos[j - 100].getX() > comandante[i - 50].getX() - 5
										&& torpedos[j - 100].getY() < yComandante + 5
										&& torpedos[j - 100].getY() > yComandante - 5) {
									if (comandante[i - 50].getHealth() < 1) {
										if ((torpedos[j - 100].getUsed() == 1) && comandante[i - 50].getHealth() == 0) {
											contar[i - 20] = true;
											gui.gb_setSpriteVisible(j, false);
											torpedos[j - 100].setUsed(torpedos[j - 100].getUsed() - 1);
											comandante[i - 50].setHealth(comandante[i - 50].getHealth() - 1);
											aciertos++;
											muertesverde++;
											gui.gb_println(
													"El enemigo " + (nivel * 1000 + i - 20) + " ha muerto. Te quedan "
															+ (12 - muertesverde) + " jefes por derrotar");
										}
									}
									if ((torpedos[j - 100].getUsed() == 1) && comandante[i - 50].getHealth() == 1) {
										gui.gb_setSpriteImage(i, "enemy9G0.png");
										gui.gb_setSpriteVisible(torpedos[j - 100].getId(), false);
										torpedos[j - 100].setUsed(torpedos[j - 100].getUsed() - 1);
										comandante[i - 50].setHealth(comandante[i - 50].getHealth() - 1);
										aciertos++;
									}
								}
							}
						}
						// IMAGEN DE LA EXPLOSION Y DESAPARICION CUANDO UN TORPEDO ALCANZA A UN ENEMIGO
						for (int i = 3; i < goei.length + 3; i++) {
							if (contar[i - 3]) {
								contador3[i - 3]++;
								if (contador3[i - 3] < 3)
									gui.gb_setSpriteImage(i, "explosion20.png");
								else if (contador3[i - 3] < 6)
									gui.gb_setSpriteImage(i, "explosion21.png");
								else if (contador3[i - 3] < 9)
									gui.gb_setSpriteImage(i, "explosion22.png");
								else if (contador3[i - 3] < 12)
									gui.gb_setSpriteImage(i, "explosion23.png");
								else if (contador3[i - 3] < 15)
									gui.gb_setSpriteImage(i, "explosion24.png");
								else
									gui.gb_setSpriteVisible(i, false);
							}
						}
						for (int i = 20; i < zako.length + 20; i++) {
							if (contar[i - 10]) {
								contador3[i - 10]++;
								if (contador3[i - 10] < 3)
									gui.gb_setSpriteImage(i, "explosion20.png");
								else if (contador3[i - 10] < 6)
									gui.gb_setSpriteImage(i, "explosion21.png");
								else if (contador3[i - 10] < 9)
									gui.gb_setSpriteImage(i, "explosion22.png");
								else if (contador3[i - 10] < 12)
									gui.gb_setSpriteImage(i, "explosion23.png");
								else if (contador3[i - 10] < 15)
									gui.gb_setSpriteImage(i, "explosion24.png");
								else
									gui.gb_setSpriteVisible(i, false);
							}
						}
						for (int i = 50; i < comandante.length + 50; i++) {
							if (contar[i - 20]) {
								contador3[i - 20]++;
								if (contador3[i - 20] < 3)
									gui.gb_setSpriteImage(i, "explosion20.png");
								else if (contador3[i - 20] < 6)
									gui.gb_setSpriteImage(i, "explosion21.png");
								else if (contador3[i - 20] < 9)
									gui.gb_setSpriteImage(i, "explosion22.png");
								else if (contador3[i - 20] < 12)
									gui.gb_setSpriteImage(i, "explosion23.png");
								else if (contador3[i - 20] < 15)
									gui.gb_setSpriteImage(i, "explosion24.png");
								else
									gui.gb_setSpriteVisible(i, false);
							}
						}
						// ALETEO ENEMIGOS
						for (int i = 20; i < zako.length + 20; i++) {
							if (contador2 % 20 == 0 && zako[i - 20].getHealth() == 0)
								gui.gb_setSpriteImage(i, "enemy3G0.png");
							else if (contador2 % 10 == 0)
								gui.gb_setSpriteImage(i, "enemy3G1.png");
						}
						for (int i = 50; i < comandante.length + 50; i++) {
							if (comandante[i - 50].getHealth() == 1) {
								if (contador2 % 24 == 0)
									gui.gb_setSpriteImage(i, "enemy1G0.png");
								else if (contador2 % 12 == 0)
									gui.gb_setSpriteImage(i, "enemy1G1.png");
							} else if (comandante[i - 50].getHealth() == 0) {
								if (contador2 % 24 == 0)
									gui.gb_setSpriteImage(i, "enemy9G0.png");
								else if (contador2 % 12 == 0)
									gui.gb_setSpriteImage(i, "enemy9G1.png");
							}
						}
						contador2++;

						// APARICION DE LOS TORPEDOS ENEMIGOS
						if (contadorTiempo % 56 == 0) {
							azar = (int) ((Math.random() * 10));
							if (goei[azar].getHealth() == 0) {
								gui.gb_setSpriteVisible(contadorDisparosenemigos + 600, true);
								torpedosEnemigos[contadorDisparosenemigos].move(goei[azar].getX(), goei[azar].getY());

								contadorDisparosenemigos++;
							}
						}

						if (contadorTiempo % 105 == 0) {
							azar2 = (int) ((Math.random() * 20));
							if (zako[azar2].getHealth() == 0) {
								gui.gb_setSpriteVisible(contadorDisparosenemigos + 600, true);
								torpedosEnemigos[contadorDisparosenemigos].move(zako[azar2].getX(), zako[azar2].getY());

								contadorDisparosenemigos++;
							}
						}
						if (contadorTiempo % 20 == 0) {
							azar3 = (int) ((Math.random() * 10));
							if (comandante[azar3].getHealth() == 0 || comandante[azar3].getHealth() == 1) {
								gui.gb_setSpriteVisible(contadorDisparosenemigos + 600, true);
								torpedosEnemigos[contadorDisparosenemigos].move(comandante[azar3].getX(),
										zako[azar3].getY());
								contadorDisparosenemigos++;
							}
						}
						// CHOQUE DE LOS TORPEDOS ENEMIGOS CON EL JUGADOR
						for (int i = 0; i < contadorDisparosenemigos; i++) {
							torpedosEnemigos[i].move(torpedosEnemigos[i].getX(), torpedosEnemigos[i].getY() + 2);
							if (torpedosEnemigos[i].getX() < (x + 5) && torpedosEnemigos[i].getX() > (x - 5)
									&& torpedosEnemigos[i].getY() < 205 && torpedosEnemigos[i].getY() > 195) {
								if (player.getHealth() == 3 && torpedosEnemigos[i].getUsed() == 1) {
									muerto = true;
									player.setHealth(player.getHealth() - 1);
									torpedosEnemigos[i].setUsed(torpedosEnemigos[i].getUsed() - 1);
									gui.gb_setSpriteVisible(i + 600, false);
									gui.gb_setSpriteVisible(2, false);
									gui.gb_showMessageDialog("El jugador ha muerto. Has perdido una vida");
								} else if (player.getHealth() == 2 && torpedosEnemigos[i].getUsed() == 1) {
									muerto = true;
									player.setHealth(player.getHealth() - 1);
									torpedosEnemigos[i].setUsed(torpedosEnemigos[i].getUsed() - 1);
									gui.gb_setSpriteVisible(i + 600, false);
									gui.gb_setSpriteVisible(1, false);
									gui.gb_showMessageDialog("El jugador ha muerto. Has perdido una vida");
								} else if (player.getHealth() == 1 && torpedosEnemigos[i].getUsed() == 1) {
									player.setHealth(player.getHealth() - 1);
									gui.gb_setSpriteVisible(i + 600, false);
								}
							}

						}
						contadorTiempo++;
						// MUERTE DEL JUGADOR
						for (int i = 0; i < 40; i++) {
							if (i < 10) {
								if (x < goei[i].getX() + 5 && x > goei[i].getX() - 5 && goei[i].getY() < 196
										&& yGoei > 204)
									player.setHealth(0);

							} else if (i < 20) {
								if (x < zako[i - 10].getX() + 5 && x > zako[i - 10].getX() - 5 && yZako < 204
										&& yZako > 196)
									player.setHealth(0);

							} else if (i < 30) {
								if (x < zako[i - 20].getX() + 5 && x > zako[i - 20].getX() - 5 && yZako2 < 204
										&& yZako2 > 196)
									player.setHealth(0);

							} else if (i < 40) {
								if (x < comandante[i - 30].getX() + 5 && x > comandante[i - 30].getX() - 5
										&& yComandante < 204 && yComandante > 196)
									player.setHealth(0);

							}
						}
						// EXPLOSION DEL JUGADOR O TERMINAR EL JUEGO SI AL JUGADOR NO LE QUEDAN MAS
						// VIDAS
						if (player.getHealth() == 0 || muerto) {
							muerte++;
							if (muerte < 3) {
								gui.gb_setSpriteImage(0, "explosion11.png");
								gui.gb_setPortraitPlayer("hit.png");
							} else if (muerte < 6)
								gui.gb_setSpriteImage(0, "explosion12.png");
							else if (muerte < 9)
								gui.gb_setSpriteImage(0, "explosion13.png");
							else if (muerte < 12)
								gui.gb_setSpriteImage(0, "explosion11.png");
							else if (muerte > 12) {
								gui.gb_setSpriteVisible(0, false);
								if (player.getHealth() != 0 && muerte > 18) {
									gui.gb_setSpriteImage(0, "player.png");
									gui.gb_setSpriteVisible(0, true);
									gui.gb_setPortraitPlayer("galagaLogo.jpg");
									muerto = false;
									muerte = 0;
								}
								if (player.getHealth() == 0 && !perdido) {
									gui.gb_showMessageDialog(
											"Ha perdido las vidas que le quedaban. Partida finalizada");
									perdido = true;

								}
							}
						}
						// FIN DEL JUEGO
						if (puntos == 11500 && muertesverde == 12) {
							gui.gb_showMessageDialog("Felicidades, ¡te has pasado el juego crack!");
							puntos = 100000;
						}
					}
					Thread.sleep(1200 / 60); // frames por segundo

				}
			}
		}
	}
}