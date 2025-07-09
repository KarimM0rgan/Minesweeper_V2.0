public class Tile {

    // Checks if the tile is a mine or clean
    private boolean isMine;

    //Checks if the tile was pressed and revealed or not
    private boolean isRevealed;

    // Number of neighboring mines around the tile
    private int adjacentMines;

    // Creates an object of class flag
    private Flag flag;

    // Tile constructor
    public Tile() {
        this.isMine = false;
        this.isRevealed = false;
        this.adjacentMines = 0;
        this.flag = new Flag();
    }

    public boolean isMine() {
        return isMine;
    }

    // Puts a mine at that tile
    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    // method to check the `isRevealed` boolean as true when the tile is uncovered
    public void reveal() {
        isRevealed = true;
    }

    // Returns the number of mines around the pressed tile
    public int getAdjacentMines() {
        return adjacentMines;
    }

    // sets mines around a tile
    public void setAdjacentMines(int count) {
        adjacentMines = count;
    }

    // returns true
    public boolean isFlagged() {
        return flag.isFlagged();
    }

    // flags the tile
    public void toggleFlag() {
        flag.toggle();
    }
}