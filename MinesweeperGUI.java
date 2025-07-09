import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MinesweeperGUI {
    // initializing the grid size and amount of mines
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int MINES = 10;

    // creates the java swing frame to hold the game
    private final JFrame frame;

    // Creates an arraylist of tiles to be pressed
    private final JButton[][] buttons = new JButton[ROWS][COLS];

    // Creates an object of `MinesweeperGame` type to initiate the game on the Swing frame
    private MinesweeperGame game;

    // Label at the top panel saying whether the game is on or not
    private final JLabel statusLabel;

    // timer initializer at top panel
    private final JLabel timerLabel;

    // restart button initializer at top panel
    private final JButton restartButton;

    // Creates the top panel
    private final JPanel boardPanel;

    // Creates a swing timer
    private Timer swingTimer;

    // Records the seconds taken since the game started
    private int elapsedSeconds;

    // MinesweeperGUI Constructor method
    public MinesweeperGUI() {
        // Sets up frame and top panel
        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Status + Timer + Restart at top panel
        JPanel top = new JPanel(new BorderLayout());
        statusLabel = new JLabel("🙂 Game in progress 🙂", SwingConstants.CENTER);
        statusLabel.setFont(statusLabel.getFont().deriveFont(16f));
        top.add(statusLabel, BorderLayout.CENTER);

        // Timer setup
        timerLabel = new JLabel("Time: 0s", SwingConstants.LEFT);
        timerLabel.setFont(timerLabel.getFont().deriveFont(14f));
        top.add(timerLabel, BorderLayout.WEST);

        // Creating the restart button
        restartButton = new JButton("Restart");
        restartButton.setFocusPainted(false);
        restartButton.addActionListener(e -> restartGame());
        top.add(restartButton, BorderLayout.EAST);
        frame.add(top, BorderLayout.NORTH);

        //Board panel setup (Actual game of rows and columns tiles)
        boardPanel = new JPanel(new GridLayout(ROWS, COLS));
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                JButton btn = new JButton();
                btn.setFont(new Font("Monospaced", Font.BOLD, 16));
                btn.setPreferredSize(new Dimension(40, 40));
                final int row = r, col = c;

                btn.setPressedIcon(UIManager.getIcon("Button.pressedIcon"));
                btn.setMargin(new Insets(0,0,0,0));
                btn.setFocusPainted(false);

                // Scenarios of pressing tiles
                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (game.isGameOver()) return;

                        if (SwingUtilities.isRightMouseButton(e)) {
                            game.toggleFlag(row, col);
                        } else {
                            game.revealTile(row, col);
                        }

                        updateBoard();
                        if (game.isGameOver()) {
                            endGame(game.isGameWon());
                        }
                    }
                });
                buttons[r][c] = btn;
                boardPanel.add(btn);
            }
        }
        frame.add(boardPanel, BorderLayout.CENTER);

        // Initializes the game and timer
        setupNewGame();

        // Finalize and show the board
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //Starts a new game after restart
    private void setupNewGame() {
        // Stop existing timer if running
        if (swingTimer != null && swingTimer.isRunning()) {
            swingTimer.stop();
        }
        // Reset game logic
        game = new MinesweeperGame(ROWS, COLS, MINES);
        elapsedSeconds = 0;
        timerLabel.setText("Time: 0s");
        statusLabel.setText("🙂 Game in progress 🙂");
        restartButton.setText("🙂");

        // Reset board buttons
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                buttons[r][c].setEnabled(true);
                buttons[r][c].setText("");
                // reattach mouse listeners if needed (they remain)
            }
        }

        // Start timer on first click
        swingTimer = new Timer(1000, e -> {
            elapsedSeconds++;
            timerLabel.setText("Time: " + elapsedSeconds + "s");
        });
        swingTimer.setRepeats(true);
        swingTimer.start();
    }

    // MOST IMPORTANT METHOD OF THE WHOLE GAME:
    // updates the tile when flagged
    // uncovers all neighboring safe tiles if the one pressed was clean
    // Uncovers all mines and ends game if the pressed tile was a mine
    private void updateBoard() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Tile t = game.getTile(r, c);
                JButton b = buttons[r][c];
                if (t.isRevealed()) {
                    b.setEnabled(false);
                    b.setText(t.isMine()
                            ? "💣"
                            : (t.getAdjacentMines() > 0
                            ? String.valueOf(t.getAdjacentMines())
                            : ""));
                } else {
                    b.setEnabled(true);
                    b.setText(t.isFlagged() ? "🚩" : "");
                }
            }
        }
    }

    // end game scenario
    // updates top panel with different emojis and statements to assist the scenario
    private void endGame(boolean won) {
        // Stop timer
        if (swingTimer != null) swingTimer.stop();

        statusLabel.setText(won ? "😎 You Win! 😎" : "💥 Game Over! 💥");
        restartButton.setText(won ? "😎" : "💀");

        // Reveal all mines and disable all
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Tile t = game.getTile(r, c);
                JButton b = buttons[r][c];
                if (t.isMine()) {
                    b.setText("💣");
                }
                b.setEnabled(false);
            }
        }
    }

    // restarts game when the restart button is pressed
    private void restartGame() {
        setupNewGame();
        frame.revalidate();
        frame.repaint();
    }

    // main function called in `Main` class
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MinesweeperGUI::new);
    }

    // returns a multidimensional array of used buttons
    public JButton[][] getButtons() {
        return buttons;
    }
}