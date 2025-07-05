import game.Game;
import game.GameMode;
import java.util.Scanner;

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
		System.out.println("============== SPACE SHOOTER ==============");
		System.out.println("Escolha o modo de jogo:");
		System.out.println("1: Modo História (Fases)");
		System.out.println("2: Modo Infinito (Aleatório)");
		System.out.print("Sua escolha: ");

		Scanner scanner = new Scanner(System.in);
		int choice = -1;

		while (choice != 1 && choice != 2) {
			try {
				choice = Integer.parseInt(scanner.nextLine());
				if (choice != 1 && choice != 2) {
					System.out.print("Opção inválida. Digite 1 ou 2: ");
				}
			} catch (NumberFormatException e) {
				System.out.print("Por favor, digite um número (1 ou 2): ");
			}
		}

		scanner.close();

		GameMode selectedMode = (choice == 1) ? GameMode.STORY : GameMode.INFINITE;
		new Game(selectedMode).run();
	}
}