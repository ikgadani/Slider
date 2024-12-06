package JunitTests;

import cst8218.slider.entity.AppUser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link AppUser} entity class.
 * These tests validate the behavior of the AppUser class, including its getters, setters, 
 * equality methods, and password hashing logic.
 * 
 * @author Guntas Singh Chugh
 */
class AppUserTest {

    /**
     * Test setting and getting the user ID.
     * Ensures the user ID is correctly set and retrieved.
     */
    @Test
    void testSetAndGetUserid_ExpectedPass() {
        AppUser user = new AppUser();
        String testUserid = "john_doe";
        user.setUserid(testUserid);
        assertEquals(testUserid, user.getUserid());
    }

    /**
     * Test equality with the same ID.
     * Ensures two objects with the same ID are considered equal.
     */
    @Test
    void testEquals_ExpectedPass() {
        AppUser user1 = new AppUser();
        user1.setId(1L);
        AppUser user2 = new AppUser();
        user2.setId(1L);
        assertEquals(user1, user2);
    }

    /**
     * Test setting the group name.
     * Ensures the group name is correctly set and retrieved.
     */
    @Test
    void testSetGroupname_ExpectedPass() {
        AppUser user = new AppUser();
        String groupName = "admin";
        user.setGroupname(groupName);
        assertEquals(groupName, user.getGroupname());
    }

    /**
     * Test setting a null password.
     * Ensures the password is not null after being set.
     */
    @Test
    void testSetPassword_ExpectedFail() {
        AppUser user = new AppUser();
        user.setPassword(null);
        assertNotNull(user.getPassword());
    }

    /**
     * Test equality with different IDs.
     * Ensures two objects with different IDs are not considered equal.
     */
    @Test
    void testEquals_ExpectedFail() {
        AppUser user1 = new AppUser();
        user1.setId(1L);
        AppUser user2 = new AppUser();
        user2.setId(2L);
        assertNotEquals(user1, user2);
    }
}
