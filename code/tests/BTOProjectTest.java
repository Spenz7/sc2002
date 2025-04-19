package tests;

import models.BTOProject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BTOProjectTest {
    @Test
    void testDecrementUnits() {
        BTOProject project = new BTOProject("Project A", "Neighborhood A", "3-room", 10, 300000.0, true);

        assertTrue(project.decrementUnits()); // Successfully decrements
        assertEquals(9, project.getUnitCount()); // Checks the updated unit count

        // Decrement until no units are left
        for (int i = 0; i < 9; i++) {
            project.decrementUnits();
        }

        assertEquals(0, project.getUnitCount()); // Unit count should be 0
        assertFalse(project.decrementUnits()); // No units left, should return false
    }

    @Test
    void testVisibilityToggle() {
        BTOProject project = new BTOProject("Project B", "Neighborhood B", "4-room", 20, 350000.0, true);

        // Toggle visibility off
        project.setVisibility(false);
        assertFalse(project.isVisible());

        // Toggle visibility on
        project.setVisibility(true);
        assertTrue(project.isVisible());
    }

    @Test
    void testToString() {
        BTOProject project = new BTOProject("Project C", "Neighborhood C", "5-room", 5, 500000.0, true);

        String expectedOutput = "BTOProject{name='Project C', neighborhood='Neighborhood C', flatType='5-room', unitCount=5, price=500000.0, visibility=true}";
        assertEquals(expectedOutput, project.toString());
    }
}
