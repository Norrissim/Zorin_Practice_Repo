/**
 * Created by NotePad.by on 09.02.2016.
 */
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Choose the option :");
        System.out.println("1. Load message history from file");
        System.out.println("2. Save massage history in file");
        System.out.println("3. Add new massage");
        System.out.println("4. Show the history");
        System.out.println("5. Delete the message from the history");
        System.out.println("6. Exit");
        BufferedReader optionReader = new BufferedReader(new InputStreamReader(System.in));
        String choose = optionReader.readLine();
        switch (choose)
        {
            case "1" : {
                System.out.println("1");
                break;
            }
            case "2" : {
                System.out.println("2");
                break;
            }
            case "3" : {
                System.out.println("3");
                break;
            }
            case "4" : {
                System.out.println("4");
                break;
            }
            case "5" : {
                System.out.println("5");
                break;
            }
            case "6" : {
                System.out.println("6");
                break;
            }
        }
    }
}
