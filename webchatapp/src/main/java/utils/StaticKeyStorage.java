package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 12/05/2016.
 */
public class StaticKeyStorage {

    private static final Map<String, String > userIdMap = new HashMap<>();
    static {
        userIdMap.put("u$#1-token", "Norrissim");
        userIdMap.put("u$#2-token", "Savo4e4ek");
        userIdMap.put("u$#3-token", "Savo4ek");
    }

    public static String getByUsername(String username) {
        return userIdMap.entrySet().stream()
                .filter(v -> v.getValue().equals(username))
                .map(Map.Entry::getKey)
                .findAny()
                .orElse(null);
    }

    public static String getUserByUid(String uid) {
        return userIdMap.get(uid);
    }
}
