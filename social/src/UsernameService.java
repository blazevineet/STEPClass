import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UsernameService {

    private final ConcurrentHashMap<String, Integer> usernameMap;
    private final ConcurrentHashMap<String, AtomicInteger> attemptMap;
    private volatile String mostAttemptedUsername;
    private final AtomicInteger maxAttempts;

    public UsernameService() {
        usernameMap = new ConcurrentHashMap<>();
        attemptMap = new ConcurrentHashMap<>();
        maxAttempts = new AtomicInteger(0);
        mostAttemptedUsername = null;
    }

    public boolean checkAvailability(String username) {
        attemptMap.putIfAbsent(username, new AtomicInteger(0));
        int currentCount = attemptMap.get(username).incrementAndGet();

        if (currentCount > maxAttempts.get()) {
            maxAttempts.set(currentCount);
            mostAttemptedUsername = username;
        }

        return !usernameMap.containsKey(username);
    }

    public boolean registerUser(String username, int userId) {
        return usernameMap.putIfAbsent(username, userId) == null;
    }

    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        int i = 1;

        while (suggestions.size() < 3) {
            String newUsername = username + i;
            if (!usernameMap.containsKey(newUsername)) {
                suggestions.add(newUsername);
            }
            i++;
        }

        return suggestions;
    }

    public String getMostAttempted() {
        return mostAttemptedUsername;
    }
}
