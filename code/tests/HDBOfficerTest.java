package tests;

import models.HDBOfficer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HDBOfficerTest {
    @Test
    void testIsValidNric() {
        HDBOfficer officer = new HDBOfficer("Sarah", "T7654321B", 40, "Married", "password");
        assertTrue(officer.isValidNric());

        HDBOfficer invalidOfficer = new HDBOfficer("Sarah", "1234567B", 40, "Married", "password");
        assertFalse(invalidOfficer.isValidNric());
    }

    @Test
    void testCheckPassword() {
        HDBOfficer officer = new HDBOfficer("Sarah", "T7654321B", 40, "Married", "password");
        assertTrue(officer.checkPassword("password"));
        assertFalse(officer.checkPassword("wrongPassword"));
    }

    @Test
    void testChangePassword() {
        HDBOfficer officer = new HDBOfficer("Sarah", "T7654321B", 40, "Married", "password");
        officer.changePassword("newPassword");
        assertTrue(officer.checkPassword("newPassword"));
        assertFalse(officer.checkPassword("password"));
    }

    @Test
    void testToString() {
        HDBOfficer officer = new HDBOfficer("Sarah", "T7654321B", 40, "Married", "password");
        String expectedOutput = "HDBOfficer{name='Sarah', nric='T7654321B', age=40, maritalStatus='Married'}";
        assertEquals(expectedOutput, officer.toString());
    }
}
