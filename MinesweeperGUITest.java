import org.junit.jupiter.api.Test;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class MinesweeperGUITest {

    @Test
    void guiInitializesWithoutError() {
        assertDoesNotThrow(() -> {
            SwingUtilities.invokeAndWait(() -> new MinesweeperGUI());
        });
    }

    @Test
    void boardHasCorrectButtonCount() throws Exception {
        final MinesweeperGUI[] guiHolder = new MinesweeperGUI[1];
        SwingUtilities.invokeAndWait(() -> guiHolder[0] = new MinesweeperGUI());
        MinesweeperGUI gui = guiHolder[0];

        // Access the internal button array via reflection:
        JButton[][] buttons = gui.getButtons(); // add a getter for tests
        assertEquals(10, buttons.length);
        assertEquals(10, buttons[0].length);
    }
}