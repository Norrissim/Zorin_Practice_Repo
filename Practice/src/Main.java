/**
 * Created by NotePad.by on 09.02.2016.
 */
import javax.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Message> history = new ArrayList<Message>();
        BufferedReader optionReader = new BufferedReader(new InputStreamReader(System.in));
        String choose = "";
        while (!choose.equals("6")) {
            System.out.println("Choose the option :");
            System.out.println("1. Load message history from file");
            System.out.println("2. Save massage history in file");
            System.out.println("3. Add new massage");
            System.out.println("4. Show the history");
            System.out.println("5. Delete the message from the history");
            System.out.println("6. Exit");
            choose = optionReader.readLine();
            switch (choose) {
                case "1": {
                    String personJSONData = Files.readAllLines(Paths.get("history of messages.json")).toString();
                    JsonReader reader = Json.createReader(new StringReader(personJSONData));
                    JsonArray personArray = reader.readArray();
                    JsonArray arr = personArray.getJsonArray(0);
                    reader.close();

                    for(int i = 0; i < arr.size(); i++) {
                        JsonObject tempObj = arr.getJsonObject(i);
                        Time tempTime = new Time(tempObj.getJsonNumber("timestamp").longValue());
                        Message tempMes = new Message(tempObj.getString("id"), tempObj.getString("author"),
                                tempTime , tempObj.getString("message"));
                        history.add(tempMes);
                        Time time = new Time(tempObj.getJsonNumber("timestamp").longValue());
                    }
                    System.out.println("history is loaded successfully");
                    break;
                }
                case "2": {
                    System.out.println("2");
                    break;
                }
                case "3": {
                    System.out.println("3");
                    break;
                }
                case "4": {
                    for(Message it : history) {
                        System.out.println(it.toString());
                    }
                    break;
                }
                case "5": {
                    System.out.println("5");
                    break;
                }
                case "6": {
                    System.out.println("6");
                    break;
                }
            }
        }
    }
}
