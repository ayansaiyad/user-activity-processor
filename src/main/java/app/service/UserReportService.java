package app.service;

import app.domain.User;
import app.domain.UserStatus;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserReportService {

    /**
     * Return only users with ACTIVE status
     */
    public List<User> getActiveUsers(List<User> users) {
        // hint: stream + filter
        return users
                .stream()
                .filter(user -> user.status() == UserStatus.ACTIVE)
                .toList();
    }

    /**
     * Group users by their status
     */
    public Map<UserStatus, List<User>> groupByStatus(List<User> users) {
        // hint: Collectors.groupingBy
        return users
                .stream()
                .collect(Collectors.groupingBy(User::status));
    }

    /**
     * Find the user with the most recent lastLogin
     */
    public Optional<User> findMostRecentlyActiveUser(List<User> users) {
        // hint: max + Comparator
        Comparator<User> byLastLogin = Comparator.comparing(User::lastLogin);

        return users
                .stream()
                .max(byLastLogin);

    }
}
