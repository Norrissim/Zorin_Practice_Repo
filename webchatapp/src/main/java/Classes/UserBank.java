package Classes;

import org.json.simple.JSONArray;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserBank {

    private List<User> users = new ArrayList<User>();

    public UserBank() {

    }

    public void loadUsers() {
        try {
            JsonArray forArray = getJson();
            if (!forArray.isEmpty()) {
                JsonArray array = forArray.getJsonArray(0);
                users.clear();
                for (int i = 0; i < array.size(); i++) {
                    JsonObject tmpObject = array.getJsonObject(i);
                    User temp = new User(tmpObject);
                    users.add(temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static JsonArray getJson() throws IOException {
        List<String> list = Files.readAllLines(Paths.get("users.json"));
        String JSONData = list.toString();
        JsonReader forRead = Json.createReader(new StringReader(JSONData));
        JsonArray forArray = forRead.readArray();
        forRead.close();
        return forArray;
    }

    public boolean isExist(String username, String password) {
        for (User account : users) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
