import cst8218.slider.entity.AppUser;

import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppUserTest {

    @Test
    void testSetAndGetUserid_ExpectedPass() {
        // Test setting and getting the user ID
        AppUser user = new AppUser();
        String testUserid = "john_doe";

        // Set the user ID
        user.setUserid(testUserid);

        // Assert that the user ID is correctly set and retrieved
        assertEquals(testUserid, user.getUserid()); // User ID should match the input
    }

    @Test
    void testEquals_ExpectedPass() {
        // Test equality with the same ID
        AppUser user1 = new AppUser();
        user1.setId(1L);

        AppUser user2 = new AppUser();
        user2.setId(1L);

        assertEquals(user1, user2); // Objects with the same ID should be equal
    }

    @Test
    void testSetGroupname_ExpectedPass() {
        // Test setting group name
        AppUser user = new AppUser();
        String groupName = "admin";
        user.setGroupname(groupName);

        assertEquals(groupName, user.getGroupname()); // Group name should be set correctly
    }

    @Test
    void testSetPassword_ExpectedFail() {
        // Test setting a null password (should not hash a null password)
        AppUser user = new AppUser();
        user.setPassword(null);

        assertNotNull(user.getPassword());
    }

    @Test
    void testEquals_ExpectedFail() {
        // Test equality with different IDs
        AppUser user1 = new AppUser();
        user1.setId(1L);

        AppUser user2 = new AppUser();
        user2.setId(2L);

        assertNotEquals(user1, user2); 
    }
}
