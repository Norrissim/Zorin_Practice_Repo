import javax.json.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        final String LOG_FILE_NAME = "logfile.txt";
        ArrayList<Message> history = new ArrayList<>();
        BufferedReader optionReader = new BufferedReader(new InputStreamReader(System.in));
        String choose = "";
        while (!choose.equals("7")) {
            showMyMenu();
            choose = optionReader.readLine();
            switch (choose) {
                case "1": {
                    loadingFromFile(LOG_FILE_NAME, history);
                    break;
                }
                case "2": {
                    savingInFile(LOG_FILE_NAME, history);
                    break;
                }
                case "3": {
                    addNewMessage(LOG_FILE_NAME, history);
                    break;
                }
                case "4": {
                    showHistory(LOG_FILE_NAME, history);
                    break;
                }
                case "5": {
                    deleteMessage(LOG_FILE_NAME, history);
                    break;
                }
                case "6": {
                    showSearchMenu();
                    String choose1 = optionReader.readLine();
                    switch (choose1) {
                        case "1": {
                            searchByAuthor(LOG_FILE_NAME, history);
                            break;
                        }
                        case "2": {
                            SearchByKeyWord(LOG_FILE_NAME, history);
                            break;
                        }
                        case "3": {
                            searchByExpression(LOG_FILE_NAME, history);
                            break;
                        }
                        case "4": {
                            searchByTime(LOG_FILE_NAME, history);
                            break;
                        }
                        default: {
                            LogWorker.update(LOG_FILE_NAME, "Wrong choice");
                            LogWorker.update(LOG_FILE_NAME, "--------------------");
                            System.out.println("Wrong choice!");
                            break;
                        }
                    }
                    break;
                }
                case "7": {
                    finishProgram(LOG_FILE_NAME);
                    break;
                }
                default: {
                    System.out.println("Wrong choice!");
                    break;
                }
            }
        }
    }

    public static void finishProgram(String LOG_FILE_NAME) throws FileNotFoundException {
        LogWorker.update(LOG_FILE_NAME, "Exit :");
        LogWorker.update(LOG_FILE_NAME, "program is completed successfully");
        LogWorker.update(LOG_FILE_NAME, "--------------------");
        System.out.println("program is completed successfully");
    }

    public static void searchByTime(String LOG_FILE_NAME, ArrayList<Message> history) throws FileNotFoundException {
        LogWorker.update(LOG_FILE_NAME, "Searching by time period :");
        int count = 0;
        System.out.println("Enter start-time of period in format: dd/mm/yyyy hh:mm:ss");
        Scanner sc = new Scanner(new InputStreamReader(System.in));
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = format.parse(sc.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Enter end-time of period in format: dd/mm/yyyy hh:mm:ss");
        try {
            endTime = format.parse(sc.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean per = false;
        for (Message iterator : history) {
            if (startTime != null && endTime != null && iterator.getTimestamp().after(startTime) && iterator.getTimestamp().before(endTime)) {
                count++;
                System.out.println(iterator.toString());
                per = true;
            }
        }
        LogWorker.update(LOG_FILE_NAME, count + " messages are found");
        LogWorker.update(LOG_FILE_NAME, "--------------------");
        if (!per) {
            System.out.println("This period of time hasn't messages");
        }
    }

    public static void searchByExpression(String LOG_FILE_NAME, ArrayList<Message> history) throws IOException {
        LogWorker.update(LOG_FILE_NAME, "Searching by regular expression :");
        int count = 0;
        System.out.println("Enter regular expression");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String expression = reader.readLine();
        boolean find = false;
        for (Message it : history) {
            Pattern p = Pattern.compile(expression);
            Matcher m = p.matcher(it.getMessage());
            if (m.find()) {
                count++;
                find = true;
                System.out.println(it.toString());
            }
        }
        LogWorker.update(LOG_FILE_NAME, count + " messages are found");
        LogWorker.update(LOG_FILE_NAME, "--------------------");
        if (!find) {
            System.out.println("History hasn't messages with this regular expression");
        }
    }

    public static void SearchByKeyWord(String LOG_FILE_NAME, ArrayList<Message> history) throws IOException {
        LogWorker.update(LOG_FILE_NAME, "Searching by key word :");
        int count = 0;
        System.out.println("Enter key word");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String keyWord = reader.readLine();
        boolean find = false;
        for (Message it : history) {
            if (it.getMessage().contains(keyWord)) {
                count++;
                System.out.println(it.toString());
                find = true;
            }
        }
        LogWorker.update(LOG_FILE_NAME, count + " messages are found");
        LogWorker.update(LOG_FILE_NAME, "--------------------");
        if (!find) {
            System.out.println("History hasn't messages with this word");
        }
    }

    public static void searchByAuthor(String LOG_FILE_NAME, ArrayList<Message> history) throws IOException {
        LogWorker.update(LOG_FILE_NAME, "Searching by author :");
        int count = 0;
        System.out.println("Enter author name");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name = reader.readLine();
        boolean find = false;
        for (Message it : history) {
            if (it.getAuthor().equals(name)) {
                count++;
                System.out.println(it.toString());
                find = true;
            }
        }
        LogWorker.update(LOG_FILE_NAME, count + " messages are found");
        LogWorker.update(LOG_FILE_NAME, "--------------------");
        if (!find) {
            System.out.println("This author does not write messages");
        }
    }

    public static void showSearchMenu() {
        System.out.println("1. by author");
        System.out.println("2. by key word");
        System.out.println("3. by regular expression");
        System.out.println("4. by time period");
    }

    public static void deleteMessage(String LOG_FILE_NAME, ArrayList<Message> history) throws IOException {
        LogWorker.update(LOG_FILE_NAME, "Delete the message from the history :");
        System.out.println("Enter id for deleting :");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int id = Integer.valueOf(reader.readLine());
        boolean del = false;
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i).getId() == id) {
                history.remove(i);
                LogWorker.update(LOG_FILE_NAME, "Message is deleted.");
                LogWorker.update(LOG_FILE_NAME, "--------------------");
                System.out.println("Message is deleted successfully");
                del = true;
            }
        }
        if (!del) {
            System.out.println("Message isn't found");
            LogWorker.update(LOG_FILE_NAME, "Message isn't found.");
            LogWorker.update(LOG_FILE_NAME, "--------------------");
        }
    }

    public static void showHistory(String LOG_FILE_NAME, ArrayList<Message> history) throws FileNotFoundException {
        LogWorker.update(LOG_FILE_NAME, "Show the history :");
        if (history.size() > 0) {
            LogWorker.update(LOG_FILE_NAME, history.size() + " messages are shown");
            LogWorker.update(LOG_FILE_NAME, "--------------------");
            for (Message it : history) {
                System.out.println(it.toString());
            }
        } else {
            LogWorker.update(LOG_FILE_NAME, "0 messages are shown");
            LogWorker.update(LOG_FILE_NAME, "history is empty.");
            LogWorker.update(LOG_FILE_NAME, "--------------------");
            System.out.println("Your history is empty");
        }
    }

    public static void addNewMessage(String LOG_FILE_NAME, ArrayList<Message> history) throws IOException {
        LogWorker.update(LOG_FILE_NAME, "Add new massage :");
        System.out.println("Write:");
        System.out.println("1. Your name");
        System.out.println("2. Your message");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String name = reader.readLine();
        String mes = reader.readLine();
        int id;
        if (!history.isEmpty()) {
            id = history.get(history.size() - 1).getId() + 1;
        } else {
            id = 0;
        }
        Date date = new Date();
        Message tempMessage = new Message(id, name, date, mes);
        history.add(tempMessage);
        LogWorker.update(LOG_FILE_NAME, "Message is added.");
        LogWorker.update(LOG_FILE_NAME, "--------------------");
        System.out.println("Message is added.");
    }

    public static void savingInFile(String LOG_FILE_NAME, ArrayList<Message> history) throws IOException {
        LogWorker.update(LOG_FILE_NAME, "Save massage history in file :");
        if (!history.isEmpty()) {
            FileWriter fileWriter = new FileWriter("history of messages.json");
            JsonWriter jsonWriter = Json.createWriter(fileWriter);
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (Message aHistory : history) {
                arrayBuilder.add(Json.createObjectBuilder().add("id", aHistory.getId())
                        .add("author", aHistory.getAuthor())
                        .add("timestamp", aHistory.getTimestamp().getTime())
                        .add("message", aHistory.getMessage()).build());
            }
            JsonArray jsonArray = arrayBuilder.build();
            jsonWriter.writeArray(jsonArray);
            jsonWriter.close();
            LogWorker.update(LOG_FILE_NAME, history.size() + " messages are saved");
            LogWorker.update(LOG_FILE_NAME, "history is saved successfully ");
            LogWorker.update(LOG_FILE_NAME, "--------------------");
            System.out.println("history is saved successfully");
        } else {
            LogWorker.update(LOG_FILE_NAME, "history is empty. Save it? :");
            System.out.println("Your history is empty. Do you want to save it? (yes or no)");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String answer = reader.readLine();
            LogWorker.update(LOG_FILE_NAME, answer);

            switch (answer) {
                case "yes": {
                    FileWriter fileWriter = new FileWriter("history of messages.json");
                    fileWriter.close();
                    LogWorker.update(LOG_FILE_NAME, "history is saved successfully ");
                    LogWorker.update(LOG_FILE_NAME, "--------------------");
                    System.out.println("history is saved successfully");
                    break;
                }
                case "no": {
                    LogWorker.update(LOG_FILE_NAME, "--------------------");
                    break;
                }
                default: {
                    LogWorker.update(LOG_FILE_NAME, "Wrong choice!");
                    LogWorker.update(LOG_FILE_NAME, "--------------------");
                    System.out.println("Wrong choice!");
                    break;
                }
            }
        }
    }

    public static void loadingFromFile(String LOG_FILE_NAME, ArrayList<Message> history) throws IOException {
        LogWorker.update(LOG_FILE_NAME, "Load message history from file :");
        String personJSONData = Files.readAllLines(Paths.get("history of messages.json")).toString();
        JsonReader reader = Json.createReader(new StringReader(personJSONData));
        JsonArray personArray = reader.readArray();
        if (personArray.size() == 0) {
            LogWorker.update(LOG_FILE_NAME, "Your history is empty \n");
            LogWorker.update(LOG_FILE_NAME, "--------------------");
            System.out.println("Your history is empty");
            return;
        }
        if (!history.isEmpty()) {
            history.clear();
        }
        JsonArray arr = personArray.getJsonArray(0);
        reader.close();

        for (int i = 0; i < arr.size(); i++) {
            JsonObject tempObj = arr.getJsonObject(i);
            Date tempTime = new Date(tempObj.getJsonNumber("timestamp").longValue());
            Message tempMes = new Message(tempObj.getInt("id"), tempObj.getString("author"),
                    tempTime, tempObj.getString("message"));
            history.add(tempMes);
        }
        LogWorker.update(LOG_FILE_NAME, history.size() + " messages are loaded");
        LogWorker.update(LOG_FILE_NAME, "history is loaded successfully ");
        LogWorker.update(LOG_FILE_NAME, "--------------------");
        System.out.println("history is loaded successfully");
    }

    public static void showMyMenu() {
        System.out.println("Choose the option :");
        System.out.println("1. Load message history from file");
        System.out.println("2. Save massage history in file");
        System.out.println("3. Add new massage");
        System.out.println("4. Show the history");
        System.out.println("5. Delete the message from the history");
        System.out.println("6. Searching in the history");
        System.out.println("7. Exit");
    }
}
