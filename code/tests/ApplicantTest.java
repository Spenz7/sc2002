package tests;

import models.Applicant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicantTest {
    @Test
    void testIsValidNric() {
        Applicant applicant = new Applicant("John", "S1234567A", 35, "Single", "password");
        assertTrue(applicant.isValidNric());

        Applicant invalidApplicant = new Applicant("John", "1234567A", 35, "Single", "password");
        assertFalse(invalidApplicant.isValidNric());
    }

    @Test
    void testCheckPassword() {
        Applicant applicant = new Applicant("John", "S1234567A", 35, "Single", "password");
        assertTrue(applicant.checkPassword("password"));
        assertFalse(applicant.checkPassword("wrongPassword"));
    }

    @Test
    void testChangePassword() {
        Applicant applicant = new Applicant("John", "S1234567A", 35, "Single", "password");
        applicant.changePassword("newPassword");
        assertTrue(applicant.checkPassword("newPassword"));
        assertFalse(applicant.checkPassword("password"));
    }

    @Test
    void testIsEligibleForApplication() {
        Applicant eligibleApplicant = new Applicant("John", "S1234567A", 35, "Single", "password");
        assertTrue(eligibleApplicant.isEligibleForApplication());

        Applicant ineligibleApplicant = new Applicant("Rachel", "T2345678B", 25, "Single", "password");
        assertFalse(ineligibleApplicant.isEligibleForApplication());
    }
}
