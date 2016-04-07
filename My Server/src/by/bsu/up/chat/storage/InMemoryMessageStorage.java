package by.bsu.up.chat.storage;

/**
 * Created by User on 07/04/2016.
 */

import by.bsu.up.chat.common.models.Message;
import by.bsu.up.chat.logging.Logger;
import by.bsu.up.chat.logging.impl.Log;

import javax.json.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InMemoryMessageStorage implements MessageStorage {

    private static final String DEFAULT_PERSISTENCE_FILE = "messages.srg";

    private static final Logger logger = Log.create(InMemoryMessageStorage.class);

    private List<Message> messages = new ArrayList<>();

    public InMemoryMessageStorage() {
        loadMessages();
    }

    @Override
    public synchronized List<Message> getPortion(Portion portion) {
        int from = portion.getFromIndex();
        if (from < 0) {
            throw new IllegalArgumentException(String.format("Portion from index %d can not be less then 0", from));
        }
        int to = portion.getToIndex();
        if (to != -1 && to < portion.getFromIndex()) {
            throw new IllegalArgumentException(String.format("Porting last index %d can not be less then start index %d", to, from));
        }
        to = Math.max(to, messages.size());
        return messages.subList(from, to);
    }

    @Override
    public void addMessage(Message message) {
        loadMessages();
        messages.add(message);
        saveMessages(messages);

    }

    @Override
    public boolean updateMessage(Message m) {
        loadMessages();
        for (Message message : messages) {
            if (message.getId().equals(m.getId())) {
                message.setText(m.getText());
                message.setIsEdit("was edited");

                saveMessages(messages);

                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized boolean removeMessage(String messageId) {
        loadMessages();
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getId().equals(messageId)) {
                messages.remove(i);
                saveMessages(messages);
                return true;
            }
        }
        return false;
    }

    @Override
    public void loadMessages() {

        JsonArray messageHistoryJson = getJsonArrayFromFile();
        if (!messageHistoryJson.isEmpty()) {
            JsonArray array = messageHistoryJson.getJsonArray(0);
            messages.clear();
            for (int i = 0; i < array.size(); i++) {
                JsonObject tmpObject = array.getJsonObject(i);
                Message tempMessage = new Message(tmpObject);
                messages.add(tempMessage);
            }
        }
    }


    public static JsonArray getJsonArrayFromFile() {
        try {
            List<String> list = Files.readAllLines(Paths.get("D:\\Study\\Programming\\My files\\Practice\\MessageHistory.json"));
            String JSONData = list.toString();
            JsonReader forRead = Json.createReader(new StringReader(JSONData));
            JsonArray forArray = forRead.readArray();
            forRead.close();
            return forArray;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveMessages(List<Message> messages) {
        try {
            if (!messages.isEmpty()) {
                FileWriter fileOutput = new FileWriter("MessageHistory.json");
                JsonWriter writeHistory = Json.createWriter(fileOutput);
                JsonArrayBuilder jsonHistory = Json.createArrayBuilder();
                for (Message i : messages) {
                    jsonHistory.add(toJsonObjectFromMessage(i));
                }
                JsonArray arr = jsonHistory.build();
                writeHistory.writeArray(arr);
                fileOutput.close();
                writeHistory.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static JsonObject toJsonObjectFromMessage(Message aHistory) {
        return Json.createObjectBuilder().add("id", aHistory.getId())
                .add("author", aHistory.getAuthor())
                .add("timestamp", aHistory.getTimestamp())
                .add("message", aHistory.getText())
                .add("isEdit", aHistory.getIsEdit()).build();

    }

    @Override
    public int size() {
        return messages.size();
    }

}

