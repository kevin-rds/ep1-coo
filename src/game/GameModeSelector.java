package game;

import javax.swing.JOptionPane;

public class GameModeSelector {

    public static GameMode selectGameMode() {
        Object[] options = { "Modo História (Fases)", "Modo Infinito (Aleatório)" };

        int choice = JOptionPane.showOptionDialog(
                null,
                "Escolha o modo de jogo:",
                "EACH em' UP",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == -1) {
            JOptionPane.showMessageDialog(null, "Nenhuma opção selecionada. Encerrando o jogo.");
            System.exit(0);
        }

        return (choice == 0) ? GameMode.STORY : GameMode.INFINITE;
    }
}
