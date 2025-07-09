public class Main {
    public static void main(String[] args) {
        // Launches the game
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MinesweeperGUI();
        });
    }
}