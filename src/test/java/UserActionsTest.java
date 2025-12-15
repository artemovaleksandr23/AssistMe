import com.otache.AssistMe.Models.User.Admin;
import com.otache.AssistMe.Models.User.Assistant;
import com.otache.AssistMe.Models.User.User;
import com.otache.AssistMe.Services.AdminActions;
import com.otache.AssistMe.Services.AssistantActions;
import com.otache.AssistMe.Services.RegularUserActions;
import com.otache.AssistMe.Services.UserActions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserActionsTest {

    @Test
    void testAdminAccess() {
        UserActions admin = new AdminActions();
        assertDoesNotThrow(admin::accessAdminPanel);
    }

    @Test
    void testRegularUserAccessDenied() {
        UserActions user = new RegularUserActions();
        Exception exception = assertThrows(UnsupportedOperationException.class,
                user::accessAdminPanel
        );
        assertEquals("No access", exception.getMessage());
    }

    @Test
    void testAssistantAccess() {
        UserActions assistant = new AssistantActions();
        assertDoesNotThrow(assistant::accessAdminPanel);
    }

    @Test
    void adminDelegationTest() {
        User admin = new Admin(1, "Alice", "1234", "alice@mail.com");

        admin.accessAdminPanel();
        admin.viewActions();
        admin.viewMessages();

        admin.setActions(new AssistantActions());
        admin.accessAdminPanel();
    }

    @Test
    void assistantDelegationTest() {
        User assistant = new Assistant(2, "Bob", "5678", "bob@mail.com");
        assistant.accessAdminPanel();
    }
}
