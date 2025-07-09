import java.util.Random;

public class MinesweeperGame {

    // instance variables of number of required mines, rows, and columns
    private final int rows;
    private final int cols;
    private final int totalMines;

    // instance variable of a multidimensional array of tiles
    private final Tile[][] board;

    // Booleans to check the game end scenarios
    private boolean gameOver;
    private boolean gameWon;
    private int tilesRevealed;

    // MinesweeperGame Constructor: includes the game logic
    public MinesweeperGame(int rows, int cols, int totalMines) {
        this.rows = rows;
        this.cols = cols;
        this.totalMines = totalMines;
        this.board = new Tile[rows][cols];
        this.gameOver = false;
        this.gameWon = false;
        this.tilesRevealed = 0;
        initializeBoard();
        placeMines();
        calculateNeighborMines();
    }

    // method to create the board of tiles
    private void initializeBoard() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                board[r][c] = new Tile();
            }
        }
    }

    // method that randomly places mines around the board
    private void placeMines() {
        Random rand = new Random();
        int minesPlaced = 0;

        while (minesPlaced < totalMines) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (!board[r][c].isMine()) {
                board[r][c].setMine(true);
                minesPlaced++;
            }
        }
    }

    // method that checks neighboring tiles to add the amount on the tile when pressed (hint)
    private void calculateNeighborMines() {
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!board[r][c].isMine()) {
                    int count = 0;
                    for (int i = 0; i < 8; i++) {
                        int nr = r + dr[i];
                        int nc = c + dc[i];
                        if (isInBounds(nr, nc) && board[nr][nc].isMine()) {
                            count++;
                        }
                    }
                    board[r][c].setAdjacentMines(count);
                }
            }
        }
    }

    // Logics and scenarios of pressing a tile; mine, clean, or flag.
    public void revealTile(int r, int c) {
        if (!isInBounds(r, c) || board[r][c].isRevealed() || board[r][c].isFlagged() || gameOver) {
            return;
        }

        board[r][c].reveal();
        tilesRevealed++;

        if (board[r][c].isMine()) {
            gameOver = true;
            return;
        }

        // Reveal adjacent tiles if no neighbors
        if (board[r][c].getAdjacentMines() == 0) {
            int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
            for (int i = 0; i < 8; i++) {
                revealTile(r + dr[i], c + dc[i]);
            }
        }

        // Check win condition
        if (tilesRevealed == rows * cols - totalMines) {
            gameOver = true;
            gameWon = true;
        }
    }

    // flagging method
    public void toggleFlag(int row, int col) {
        Tile tile = getTile(row, col);
        if (!tile.isRevealed()) {
            tile.toggleFlag();
        }
    }

    // Makes sure the game is within the needed borderd and not crashing because of placing mines or tile outside the board
    public boolean isInBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    // returns coordinates of tile only if it is within the board borders
    public Tile getTile(int r, int c) {
        if (isInBounds(r, c)) {
            return board[r][c];
        }
        return null;
    }


    // Booleans about game end scenarios and getters
    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}