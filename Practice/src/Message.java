import java.sql.Time;

/**
 * Created by NotePad.by on 09.02.2016.
 */
public class Message {
    private String id;
    private String text;
    private String author;
    private Time time;

    public Message(String id, String text, String author, Time time) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
