public class Flag {
    // boolean returns the state of the tile being flagged
    private boolean isFlagged;

    // Flag Constructor
    public Flag() {
        this.isFlagged = false;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    // Changes the state of the tile when it is flagged/ unflagged
    public void toggle() {
        isFlagged = !isFlagged;
    }

    // removes the flag
    public void reset() {
        isFlagged = false;
    }
}

