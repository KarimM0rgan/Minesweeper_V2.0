import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    void defaultState_IsHidden_NoMine_NoFlag() {
        Tile tile = new Tile();
        assertFalse(tile.isMine(), "New tile should not have a mine by default");
        assertFalse(tile.isRevealed(), "New tile should not be revealed by default");
        assertFalse(tile.isFlagged(), "New tile should not be flagged by default");
        assertEquals(0, tile.getAdjacentMines(), "New tile should have 0 neighboring mines");
    }

    @Test
    void setMine_TogglesMineFlag() {
        Tile tile = new Tile();
        tile.setMine(true);
        assertTrue(tile.isMine());
        tile.setMine(false);
        assertFalse(tile.isMine());
    }

    @Test
    void reveal_MarksRevealed() {
        Tile tile = new Tile();
        tile.reveal();
        assertTrue(tile.isRevealed());
    }

    @Test
    void toggleFlag_FlipsFlagState() {
        Tile tile = new Tile();
        tile.toggleFlag();
        assertTrue(tile.isFlagged());
        tile.toggleFlag();
        assertFalse(tile.isFlagged());
    }

    @Test
    void setNeighborMines_StoresCorrectCount() {
        Tile tile = new Tile();
        tile.setAdjacentMines(3);
        assertEquals(3, tile.getAdjacentMines());
    }
}