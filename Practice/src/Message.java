import java.sql.Time;

/**
 * Created by NotePad.by on 09.02.2016.
 */
public class Message {
    private String id;
    private String author;
    private Time timestamp;
    private String message;

    public Message(String id, String author, Time timestamp, String message) {
        this.id = id;
        this.message = message;
        this.author = author;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Time getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Time timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "id : " + id + " | " + timestamp + " | " + author + ": " + message;
    }
}
