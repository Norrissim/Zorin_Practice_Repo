package Classes;

import javax.json.JsonObject;
import java.io.Serializable;

/**
 * Created by User on 10/05/2016.
 */
public class Message implements Serializable {

    private String id;
    private String author;
    private long timestamp;
    private String text;
    private boolean changed;
    private boolean deleted;
    private boolean changing;

    public Message() {
        this.id = "";
        this.author = "";
        this.timestamp = 0;
        this.text = "";
        this.changed = false;
        this.deleted = false;
        this.changing = false;
    }

    public Message(JsonObject temp) {
        this.author = temp.getString("author");
        this.timestamp = temp.getJsonNumber("timestamp").longValue();
        this.text = temp.getString("message");
        this.deleted = temp.getBoolean("deleted");
        this.changed = temp.getBoolean("changed");
        this.changing = temp.getBoolean("changing");
        this.id = temp.getString("id");
    }

    public boolean isChanging() {
        return changing;
    }

    public void setChanging(boolean changing) {
        this.changing = changing;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", timestamp=" + timestamp +
                ", text='" + text + '\'' +
                '}';
    }
}