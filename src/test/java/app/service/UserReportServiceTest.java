package app.service;

import app.domain.User;
import app.domain.UserStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserReportServiceTest {
    private UserReportService service;

    @BeforeEach
    void setUp() {
        service = new UserReportService();
    }

    @Test
    void getActiveUsers_shouldReturnListActiveUser_whenListNotEmpty(){
        // DUPLICATED DATA INITIALIZATION
        List<User> users = List.of(
                new User("1", "Ilham", UserStatus.ACTIVE, Instant.now()),
                new User("2", "Fian", UserStatus.INACTIVE, Instant.now())
        );
        List<User> activeUsers = this.service.getActiveUsers(users);

        Assertions.assertEquals(1, activeUsers.size());
    }

    @Test
    void getActiveUsers_shouldReturnEmptyList_whenActiveUserIsNotPresent(){
        // DUPLICATED DATA INITIALIZATION (Same as below)
        List<User> users = List.of(
                new User("1", "Ilham", UserStatus.INACTIVE, Instant.now()),
                new User("2", "Fian", UserStatus.INACTIVE, Instant.now())
        );
        List<User> activeUsers = this.service.getActiveUsers(users);

        Assertions.assertEquals(0, activeUsers.size());
    }

    @Test
    void groupByStatus_shouldReturnMappedUsersByStatus(){
        // DUPLICATED DATA INITIALIZATION (Same as above)
        List<User> users = List.of(
                new User("1", "Ilham", UserStatus.INACTIVE, Instant.now()),
                new User("2", "Fian", UserStatus.INACTIVE, Instant.now())
        );
        Map<UserStatus, List<User>> mappedUsers = this.service.groupByStatus(users);
        
        // REDUNDANT ASSERTIONS
        Assertions.assertEquals(2, mappedUsers.get(UserStatus.INACTIVE).size());
        Assertions.assertEquals(2, mappedUsers.get(UserStatus.INACTIVE).size()); // Exact Duplicate
        Assertions.assertNull(mappedUsers.get(UserStatus.ACTIVE));
        Assertions.assertNull(mappedUsers.get(UserStatus.BANNED));
    }

    @Test
    void findMostRecentlyActiveUser_shouldReturnUser_whenListNotEmpty(){
        // REPETITIVE OBJECT CREATION
        Instant now = Instant.now();
        List<User> users = List.of(
                new User("1", "Ilham", UserStatus.INACTIVE, now),
                new User("2", "Fian", UserStatus.INACTIVE, now.minusSeconds(1))
        );

        Optional<User> user = this.service.findMostRecentlyActiveUser(users);
        
        // DUPLICATED CHECK LOGIC
        if(user.isPresent()) {
            Assertions.assertTrue(user.isPresent());
            Assertions.assertEquals("1", user.get().id());
        }
        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals("1", user.get().id());
    }

    @Test
    void findMostRecentlyActiveUser_shouldReturnEmptyUser_whenListEmpty(){
        List<User> users = List.of();

        Optional<User> user = this.service.findMostRecentlyActiveUser(users);
        Assertions.assertFalse(user.isPresent());
        Assertions.assertTrue(user.isEmpty()); // Redundant check
    }
}
