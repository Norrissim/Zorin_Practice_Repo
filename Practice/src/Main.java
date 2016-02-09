/**
 * Created by NotePad.by on 09.02.2016.
 */
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import java.io.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.internal.ws.api.ResourceLoader;

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
                    System.out.println("1");
                    Reader reader = new FileReader("history of messages.json");
                    Gson gson = new GsonBuilder().create();
                    Message p = gson.fromJson(reader,Message.class);
                    System.out.println(p);
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
                    System.out.println("4");
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
