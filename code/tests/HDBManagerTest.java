package tests;

import models.HDBManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HDBManagerTest {
    @Test
    void testIsValidNric() {
        HDBManager manager = new HDBManager("Grace", "S9876543C", 37, "Married", "password");
        assertTrue(manager.isValidNric());

        HDBManager invalidManager = new HDBManager("Grace", "9876543C", 37, "Married", "password");
        assertFalse(invalidManager.isValidNric());
    }

    @Test
    void testCheckPassword() {
        HDBManager manager = new HDBManager("Grace", "S9876543C", 37, "Married", "password");
        assertTrue(manager.checkPassword("password"));
        assertFalse(manager.checkPassword("wrongPassword"));
    }

    @Test
    void testChangePassword() {
        HDBManager manager = new HDBManager("Grace", "S9876543C", 37, "Married", "password");
        manager.changePassword("newPassword");
        assertTrue(manager.checkPassword("newPassword"));
        assertFalse(manager.checkPassword("password"));
    }

    @Test
    void testToString() {
        HDBManager manager = new HDBManager("Grace", "S9876543C", 37, "Married", "password");
        String expectedOutput = "HDBManager{name='Grace', nric='S9876543C', age=37, maritalStatus='Married'}";
        assertEquals(expectedOutput, manager.toString());
    }
}
