package app;

import app.domain.User;
import app.domain.UserStatus;
import app.service.UserReportService;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        List<User> users = List.of(
                new User("u1", "Ilham", UserStatus.ACTIVE, Instant.now()),
                new User("u2", "Alice", UserStatus.INACTIVE, Instant.now().minusSeconds(3600)),
                new User("u3", "Bob", UserStatus.BANNED, Instant.now().minusSeconds(7200))
        );

        UserReportService service = new UserReportService();

        List<User> currentActiveUsers = service.getActiveUsers(users);
        Map<UserStatus, List<User>> groupedUserByStatus = service.groupByStatus(users);
        Optional<User> lastRecentLogin = service.findMostRecentlyActiveUser(List.of());

        System.out.println("currentActiveUsers " + currentActiveUsers);
        System.out.println("groupedUserByStatus " + groupedUserByStatus);
        System.out.println("lastRecentLogin " + lastRecentLogin);
    }
}
