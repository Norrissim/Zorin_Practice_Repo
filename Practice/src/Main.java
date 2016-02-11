/**
 * Created by NotePad.by on 09.02.2016.
 */
import javax.json.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Message> history = new ArrayList<Message>();
        BufferedReader optionReader = new BufferedReader(new InputStreamReader(System.in));
        boolean loaded = false;
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
                    if(personArray.size() == 0)
                    {
                        System.out.println("Your history is empty");
                        break;
                    }
                    /*if(personArray.size() == 1)
                    {
                        JsonObject obj = personArray.getJsonObject(0);
                        Time tempTime = new Time(obj.getJsonNumber("timestamp").longValue());
                        Message tempMes = new Message(obj.getString("id"), obj.getString("author"),
                                tempTime , obj.getString("message"));
                        history.add(tempMes);
                        loaded = true;
                        System.out.println("history is loaded successfully");
                        break;
                    }*/
                    JsonArray arr = personArray.getJsonArray(0);
                    reader.close();

                    for(int i = 0; i < arr.size(); i++) {
                        JsonObject tempObj = arr.getJsonObject(i);
                        Date tempTime = new Date(tempObj.getJsonNumber("timestamp").longValue());
                        Message tempMes = new Message(tempObj.getString("id"), tempObj.getString("author"),
                                tempTime , tempObj.getString("message"));
                        history.add(tempMes);
                    }
                    loaded = true;
                    System.out.println("history is loaded successfully");
                    break;
                }
                case "2": {
                    if(loaded == true) {
                        JsonArray personArray = Json.createArrayBuilder().build();
                        JsonObject personObject[] = new JsonObject[history.size()];
                        for(int i = 0; i < history.size(); i++) {
                             personObject[i]= Json.createObjectBuilder()
                                    .add("id", history.get(i).getId())
                                    .add("author", history.get(i).getAuthor())
                                    .add("timestamp", history.get(i).getTimestamp().getTime())
                                    .add("message", history.get(i).getMessage())
                                    .build();
                            personArray.add(personObject[i]);
                        }
                        FileWriter fileWriter = new FileWriter("history of messages.json");
                        JsonWriter writer = Json.createWriter(fileWriter);
                        writer.writeArray(personArray);
                        writer.close();
                        System.out.println("history is saved successfully");
                        break;
                    }
                    else
                    {
                        System.out.println("first you need to download message history");
                        break;
                    }
                }
                case "3": {
                    System.out.println("Write:");
                    System.out.println("1. Your name");
                    System.out.println("2. Your message");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String name = reader.readLine();
                    String mes = reader.readLine();
                    Date date = new Date();
                    Message tempMessage = new Message("don't work", name, date, mes);
                    history.add(tempMessage);
                    break;
                }
                case "4": {
                    if(history.size() > 0) {
                        for (Message it : history) {
                            System.out.println(it.toString());
                        }
                        break;
                    }
                    else
                    {
                        System.out.println("your history is empty");
                        break;
                    }
                }
                case "5": {
                    System.out.println("Enter id for deleting :");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String id = reader.readLine();
                    boolean del = false;
                    for(int i = 0; i < history.size(); i++) {
                        if (history.get(i).getId().equals(id)) {
                            history.remove(i);
                            System.out.println("Message is deleted successfully");
                            del = true;
                        }
                    }
                    if(del == false)
                    System.out.println("Message isn't found");
                    break;
                }
                case "6": {
                    System.out.println("program is completed successfully");
                    break;
                }
            }
        }
    }
}
