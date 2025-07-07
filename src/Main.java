import game.Game;
import game.GameMode;
import game.GameModeSelector;

/**********************************************************************/
/* */
/* Para jogar:                                                         */
/* */
/* - cima, baixo, esquerda, direita: movimentação do player.        */
/* - control: disparo de projéteis.                                 */
/* - ESC: para sair do jogo.                                        */
/* */

public class Main {

	public static void main(String[] args) {
		GameMode selectedMode = GameModeSelector.selectGameMode();
		new Game(selectedMode).run();
	}
}