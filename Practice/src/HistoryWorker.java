import javax.json.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

public class HistoryWorker {
    Searcher searcher;
    final static String LOG_FILE_NAME = "logfile.txt";
    ArrayList<Message> history;

    public HistoryWorker() {
        history = new ArrayList<>();
        searcher = new Searcher();
        startWhileMainMenu();
    }

    public void startWhileMainMenu() {
        try {
            BufferedReader optionReader = new BufferedReader(new InputStreamReader(System.in));
            String choose = "";
            while (!choose.equals("7")) {
                showMyMenu();
                choose = optionReader.readLine();
                switch (choose) {
                    case "1": {
                        loadingFromFile(history);
                        break;
                    }
                    case "2": {
                        savingInFile(history);
                        break;
                    }
                    case "3": {
                        addNewMessage(history);
                        break;
                    }
                    case "4": {
                        showHistory(history);
                        break;
                    }
                    case "5": {
                        deleteMessage(history);
                        break;
                    }
                    case "6": {
                        searcher.startSwitchForMenu(LOG_FILE_NAME, history);
                        break;
                    }
                    case "7": {
                        finishProgram();
                        break;
                    }
                    default: {
                        System.out.println("Wrong choice!");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                LogWorker.update(LOG_FILE_NAME, "Exception: input problems");
            } catch (FileNotFoundException e1) {
                File newFile = new File(LOG_FILE_NAME);
                try {
                    newFile.createNewFile();
                    LogWorker.update(LOG_FILE_NAME, "Exception: input/output problems");
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public void finishProgram() {
        try {
            LogWorker.update(LOG_FILE_NAME, "Exit :");
            LogWorker.update(LOG_FILE_NAME, "program is completed successfully");
            LogWorker.update(LOG_FILE_NAME, "--------------------");
            System.out.println("program is completed successfully");
        } catch (FileNotFoundException e) {
            File newFile = new File(LOG_FILE_NAME);
            try {
                newFile.createNewFile();
                LogWorker.update(LOG_FILE_NAME, "Exit :");
                LogWorker.update(LOG_FILE_NAME, "program is completed successfully");
                LogWorker.update(LOG_FILE_NAME, "--------------------");
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    public void deleteMessage(ArrayList<Message> history) {
        try {
            LogWorker.update(LOG_FILE_NAME, "Delete the message from the history :");
            System.out.println("Enter id for deleting :");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            int id = Integer.valueOf(reader.readLine());
            boolean del = false;
            for (int i = 0; i < history.size(); i++) {
                if (history.get(i).getId() == id) {
                    history.remove(i);
                    del = true;
                    LogWorker.update(LOG_FILE_NAME, "Message is deleted.");
                    LogWorker.update(LOG_FILE_NAME, "--------------------");
                    System.out.println("Message is deleted successfully");
                }
            }
            if (!del) {
                LogWorker.update(LOG_FILE_NAME, "Message isn't found.");
                LogWorker.update(LOG_FILE_NAME, "--------------------");
                System.out.println("Message isn't found");
            }
        } catch (FileNotFoundException e) {
            File newFile = new File(LOG_FILE_NAME);
            try {
                newFile.createNewFile();
                System.out.println("Message can't be deleted, because wasn't found log file. Now this file is created. Please, try again");
                LogWorker.update(LOG_FILE_NAME, "Message isn't deleted, because wasn't found log file.");
                LogWorker.update(LOG_FILE_NAME, "--------------------");
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                LogWorker.update(LOG_FILE_NAME, "Exception: input issues");
            } catch (FileNotFoundException e1) {
                File newFile = new File(LOG_FILE_NAME);
                try {
                    newFile.createNewFile();
                    LogWorker.update(LOG_FILE_NAME, "Exception: input issues");
                    LogWorker.update(LOG_FILE_NAME, "--------------------");
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public void showHistory(ArrayList<Message> history) {
        try {
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
        } catch (FileNotFoundException e) {
            File newFile = new File(LOG_FILE_NAME);
            try {
                newFile.createNewFile();
                System.out.println("History can't be shown, because wasn't found log file. Now this file is created. Please, try again");
                LogWorker.update(LOG_FILE_NAME, "History isn't shown, because wasn't found log file.");
                LogWorker.update(LOG_FILE_NAME, "--------------------");
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }

    public void addNewMessage(ArrayList<Message> history) {
        try {
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
        } catch (FileNotFoundException e) {
            File newFile = new File(LOG_FILE_NAME);
            try {
                newFile.createNewFile();
                System.out.println("Message can't be added, because wasn't found log file. Now this file is created. Please, try again");
                LogWorker.update(LOG_FILE_NAME, "Message isn't added, because wasn't found log file.");
                LogWorker.update(LOG_FILE_NAME, "--------------------");
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                LogWorker.update(LOG_FILE_NAME, "Exception: input issues");
            } catch (FileNotFoundException e1) {
                File newFile = new File(LOG_FILE_NAME);
                try {
                    newFile.createNewFile();
                    LogWorker.update(LOG_FILE_NAME, "Exception: input issues");
                    LogWorker.update(LOG_FILE_NAME, "--------------------");
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public void savingInFile(ArrayList<Message> history) {
        try {
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
        } catch (FileNotFoundException e) {
            File newFile = new File(LOG_FILE_NAME);
            try {
                newFile.createNewFile();
                System.out.println("History can't be saved, because wasn't found log file. Now this file is created. Please, try again");
                LogWorker.update(LOG_FILE_NAME, "History isn't saved, because wasn't found log file.");
                LogWorker.update(LOG_FILE_NAME, "--------------------");
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                LogWorker.update(LOG_FILE_NAME, "Exception: input issues");
            } catch (FileNotFoundException e1) {
                File newFile = new File(LOG_FILE_NAME);
                try {
                    newFile.createNewFile();
                    LogWorker.update(LOG_FILE_NAME, "Exception: input issues");
                    LogWorker.update(LOG_FILE_NAME, "--------------------");
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public void loadingFromFile(ArrayList<Message> history) {
        try {
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
        } catch (FileNotFoundException e) {
            File newFile = new File(LOG_FILE_NAME);
            try {
                newFile.createNewFile();
                System.out.println("History can't be loaded, because wasn't found log file. Now this file is created. Please, try again");
                LogWorker.update(LOG_FILE_NAME, "History isn't loaded, because wasn't found log file.");
                LogWorker.update(LOG_FILE_NAME, "--------------------");
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                LogWorker.update(LOG_FILE_NAME, "Exception: input issues");
            } catch (FileNotFoundException e1) {
                File newFile = new File(LOG_FILE_NAME);
                try {
                    newFile.createNewFile();
                    LogWorker.update(LOG_FILE_NAME, "Exception: input issues");
                    LogWorker.update(LOG_FILE_NAME, "--------------------");
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public void showMyMenu() {
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
