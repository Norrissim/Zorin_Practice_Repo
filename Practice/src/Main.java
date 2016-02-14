/**
 * Created by NotePad.by on 09.02.2016.
 */
import javax.json.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        String LOG_FILE_NAME = "logfile.txt";
        ArrayList<Message> history = new ArrayList<Message>();
        BufferedReader optionReader = new BufferedReader(new InputStreamReader(System.in));
        boolean loaded = false;
        String choose = "";
        while (!choose.equals("7")) {
            System.out.println("Choose the option :");
            System.out.println("1. Load message history from file");
            System.out.println("2. Save massage history in file");
            System.out.println("3. Add new massage");
            System.out.println("4. Show the history");
            System.out.println("5. Delete the message from the history");
            System.out.println("6. Searching in the history");
            System.out.println("7. Exit");
            choose = optionReader.readLine();
            switch (choose) {
                case "1": {
                    LogWorker.update(LOG_FILE_NAME, "Load message history from file :");
                    String personJSONData = Files.readAllLines(Paths.get("history of messages.json")).toString();
                    JsonReader reader = Json.createReader(new StringReader(personJSONData));
                    JsonArray personArray = reader.readArray();
                    if(personArray.size() == 0)
                    {
                        LogWorker.update(LOG_FILE_NAME, "Your history is empty \n");
                        LogWorker.update(LOG_FILE_NAME, "--------------------");
                        System.out.println("Your history is empty");
                        break;
                    }
                    if(!history.isEmpty())
                    {
                        history.clear();
                    }
                    JsonArray arr = personArray.getJsonArray(0);
                    reader.close();

                    for(int i = 0; i < arr.size(); i++) {
                        JsonObject tempObj = arr.getJsonObject(i);
                        Date tempTime = new Date(tempObj.getJsonNumber("timestamp").longValue());
                        Message tempMes = new Message(tempObj.getInt("id"), tempObj.getString("author"),
                                tempTime , tempObj.getString("message"));
                        history.add(tempMes);
                    }
                    loaded = true;
                    LogWorker.update(LOG_FILE_NAME, history.size() + " messages are loaded");
                    LogWorker.update(LOG_FILE_NAME, "history is loaded successfully ");
                    LogWorker.update(LOG_FILE_NAME, "--------------------");
                    System.out.println("history is loaded successfully");
                    break;
                }
                case "2": {
                    LogWorker.update(LOG_FILE_NAME, "Save massage history in file :");
                    if (!history.isEmpty()){
                        FileWriter fileWriter = new FileWriter("history of messages.json");
                        JsonWriter jsonWriter = Json.createWriter(fileWriter);
                        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                        for (int i = 0;i<history.size();i++)
                        {
                            arrayBuilder.add(Json.createObjectBuilder().add("id",history.get(i).getId())
                                    .add("author",history.get(i).getAuthor())
                                    .add("timestamp",history.get(i).getTimestamp().getTime())
                                    .add("message", history.get(i).getMessage()).build());
                        }
                        JsonArray jsonArray = arrayBuilder.build();
                        jsonWriter.writeArray(jsonArray);
                        jsonWriter.close();
                        LogWorker.update(LOG_FILE_NAME, history.size() + " messages are saved");
                        LogWorker.update(LOG_FILE_NAME, "history is saved successfully ");
                        LogWorker.update(LOG_FILE_NAME, "--------------------");
                        System.out.println("history is saved successfully");
                        break;
                    }
                    else {
                        LogWorker.update(LOG_FILE_NAME, "history is empty. Save it? :");
                        System.out.println("Your history is empty. Do you want to save it? (yes or no)");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        String answer = reader.readLine();
                        LogWorker.update(LOG_FILE_NAME, answer);

                        switch (answer) {
                            case "yes" :{
                                FileWriter fileWriter = new FileWriter("history of messages.json");
                                fileWriter.close();
                                LogWorker.update(LOG_FILE_NAME, "history is saved successfully ");
                                LogWorker.update(LOG_FILE_NAME, "--------------------");
                                System.out.println("history is saved successfully");
                                break;
                            }
                            case "no" :{
                                LogWorker.update(LOG_FILE_NAME, "--------------------");
                                break;
                            }
                            default:
                            {
                                LogWorker.update(LOG_FILE_NAME, "Wrong choice!");
                                LogWorker.update(LOG_FILE_NAME, "--------------------");
                                System.out.println("Wrong choice!");
                                break;
                            }
                        }
                        break;
                    }
                }
                case "3": {
                    LogWorker.update(LOG_FILE_NAME, "Add new massage :");
                    System.out.println("Write:");
                    System.out.println("1. Your name");
                    System.out.println("2. Your message");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String name = reader.readLine();
                    String mes = reader.readLine();
                    int id;
                    if(!history.isEmpty())
                    {
                        id = history.get(history.size() - 1).getId() + 1;
                    }
                    else
                    {
                        id = 0;
                    }
                    Date date = new Date();
                    Message tempMessage = new Message(id, name, date, mes);
                    history.add(tempMessage);
                    LogWorker.update(LOG_FILE_NAME, "Message is added.");
                    LogWorker.update(LOG_FILE_NAME, "--------------------");
                    System.out.println("Message is added.");
                    break;
                }
                case "4": {
                    LogWorker.update(LOG_FILE_NAME, "Show the history :");
                    if(history.size() > 0) {
                        LogWorker.update(LOG_FILE_NAME, history.size() +" messages are shown");
                        LogWorker.update(LOG_FILE_NAME, "--------------------");
                        for (Message it : history) {
                            System.out.println(it.toString());
                        }
                        break;
                    }
                    else
                    {
                        LogWorker.update(LOG_FILE_NAME, "0 messages are shown");
                        LogWorker.update(LOG_FILE_NAME, "history is empty.");
                        LogWorker.update(LOG_FILE_NAME, "--------------------");
                        System.out.println("Your history is empty");
                        break;
                    }
                }
                case "5": {
                    System.out.println("Enter id for deleting :");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    int id = Integer.valueOf(reader.readLine());
                    boolean del = false;
                    for(int i = 0; i < history.size(); i++) {
                        if (history.get(i).getId() == id) {
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
                    System.out.println("1. by author");
                    System.out.println("2. by key word");
                    System.out.println("3. by regular expression");
                    System.out.println("4. by time period");
                    String choose1 = optionReader.readLine();
                    switch (choose1) {
                        case "1": {
                            System.out.println("Enter author name");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            String name = reader.readLine();
                            boolean find = false;
                            for(Message it : history)
                            {
                                if(it.getAuthor().equals(name))
                                {
                                    System.out.println(it.toString());
                                    find = true;
                                }
                            }
                            if(find == false)
                            {
                                System.out.println("This author does not write messages");
                            }
                            break;
                        }
                        case "2": {
                            System.out.println("Enter key word");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            String keyWord = reader.readLine();
                            boolean find = false;
                            for(Message it : history)
                            {
                                if(it.getMessage().contains(keyWord))
                                {
                                    System.out.println(it.toString());
                                    find = true;
                                }
                            }
                            if(find == false)
                            {
                                System.out.println("History hasn't messages with this word");
                            }
                            break;
                        }
                        case "3": {
                            System.out.println("Enter regular expression");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            String expression = reader.readLine();
                            boolean find = false;
                            for(Message it : history)
                            {
                                Pattern p = Pattern.compile(expression);
                                Matcher m = p.matcher(it.getMessage());
                                if(m.find())
                                {
                                    find = true;
                                    System.out.println(it.toString());
                                }
                            }
                            if(find == false)
                            {
                                System.out.println("History hasn't messages with this regular expression");
                            }
                            break;
                        }
                        case "4": {
                            System.out.println("Enter start-time of period in format: dd/mm/yyyy hh:mm:ss");
                            Scanner sc = new Scanner(new InputStreamReader(System.in));
                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                            Date startTime = null;
                            Date endTime = null;
                            try {
                                startTime = format.parse(sc.nextLine());
                            }
                            catch (ParseException e)
                            {
                                e.printStackTrace();
                            }
                            System.out.println("Enter end-time of period in format: dd/mm/yyyy hh:mm:ss");
                            try {
                                endTime = format.parse(sc.nextLine());
                            }
                            catch (ParseException e)
                            {
                                e.printStackTrace();
                            }
                            boolean per = false;

                            for(Message iter : history)
                            {
                                if(iter.getTimestamp().after(startTime) && iter.getTimestamp().before(endTime))
                                {
                                    System.out.println(iter.toString());
                                    per = true;
                                }
                            }
                            if(per == false)
                                System.out.println("This period of time hasn't messages");
                            break;
                        }
                        default: {
                            System.out.println("Wrong choice!");
                            break;
                        }
                    }
                    break;
                }
                case "7": {
                    System.out.println("program is completed successfully");
                    break;
                }
                default: {
                    System.out.println("Wrong choice!");
                    break;
                }
            }
        }
    }
}
