import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MinesweeperGameTest {
    private MinesweeperGame game;

    @BeforeEach
    void setUp() {
        game = new MinesweeperGame(5, 5, 4);
    }

    @Test
    void initialize_HasCorrectDimensions() {
        assertEquals(5, game.getRows());
        assertEquals(5, game.getCols());
    }

    @Test
    void initialize_HasCorrectMineCount() {
        int mineCount = 0;
        for (int r = 0; r < game.getRows(); r++) {
            for (int c = 0; c < game.getCols(); c++) {
                if (game.getTile(r, c).isMine()) mineCount++;
            }
        }
        assertEquals(4, mineCount, "Board should contain exactly 4 mines");
    }

    @Test
    void revealTile_RevealsSingleTile() {
        // find a non-mine tile
        outer:
        for (int r = 0; r < game.getRows(); r++) {
            for (int c = 0; c < game.getCols(); c++) {
                if (!game.getTile(r, c).isMine()) {
                    game.revealTile(r, c);
                    assertTrue(game.getTile(r, c).isRevealed());
                    break outer;
                }
            }
        }
    }

    @Test
    void toggleFlag_MarksAndUnmarks() {
        game.toggleFlag(2, 3);
        assertTrue(game.getTile(2, 3).isFlagged());
        game.toggleFlag(2, 3);
        assertFalse(game.getTile(2, 3).isFlagged());
    }

    @Test
    void floodFill_RevealsRegion() {
        // Create a small board without mines for flood-fill
        MinesweeperGame g2 = new MinesweeperGame(3, 3, 0);
        g2.revealTile(1, 1);
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                assertTrue(g2.getTile(r, c).isRevealed(), "All tiles should be revealed in an empty board");
            }
        }
    }

    @Test
    void winCondition_DetectedWhenAllSafeRevealed() {
        MinesweeperGame g2 = new MinesweeperGame(3, 3, 1);
        // reveal all non-mine tiles
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (!g2.getTile(r, c).isMine()) {
                    g2.revealTile(r, c);
                }
            }
        }
        assertTrue(g2.isGameOver());
        assertTrue(g2.isGameWon());
    }
}