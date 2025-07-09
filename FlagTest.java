import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlagTest {
    private Flag flag;

    @BeforeEach
    public void setUp() {
        flag = new Flag(); // Initialize a new Flag object before each test
    }

    @Test
    public void testInitialState() {
        assertFalse(flag.isFlagged(), "The initial state of the flag should be false.");
    }

    @Test
    public void testToggleFlag() {
        // Toggle the flag to true
        flag.toggle();
        assertTrue(flag.isFlagged(), "The flag should be flagged after toggling once.");

        // Toggle the flag to false
        flag.toggle();
        assertFalse(flag.isFlagged(), "The flag should not be flagged after toggling twice.");
    }

    @Test
    public void testResetFlag() {
        // Toggle the flag to true
        flag.toggle();
        assertTrue(flag.isFlagged(), "The flag should be flagged after toggling once.");

        // Reset the flag
        flag.reset();
        assertFalse(flag.isFlagged(), "The flag should not be flagged after resetting.");
    }
}