import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Searcher {
    Searcher(String fileName, ArrayList<Message> history) throws IOException {
        BufferedReader optionReader = new BufferedReader(new InputStreamReader(System.in));
        showSearchMenu();
        String choose1 = optionReader.readLine();
        switch (choose1) {
            case "1": {
                searchByAuthor(fileName, history);
                break;
            }
            case "2": {
                SearchByKeyWord(fileName, history);
                break;
            }
            case "3": {
                searchByExpression(fileName, history);
                break;
            }
            case "4": {
                searchByTime(fileName, history);
                break;
            }
            default: {
                LogWorker.update(fileName, "Wrong choice");
                LogWorker.update(fileName, "--------------------");
                System.out.println("Wrong choice!");
                break;
            }
        }
    }

    public void showSearchMenu() {
        System.out.println("1. by author");
        System.out.println("2. by key word");
        System.out.println("3. by regular expression");
        System.out.println("4. by time period");
    }

    public void searchByTime(String LOG_FILE_NAME, ArrayList<Message> history) throws FileNotFoundException {
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

    public void searchByExpression(String LOG_FILE_NAME, ArrayList<Message> history) throws IOException {
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

    public void SearchByKeyWord(String LOG_FILE_NAME, ArrayList<Message> history) throws IOException {
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

    public void searchByAuthor(String LOG_FILE_NAME, ArrayList<Message> history) throws IOException {
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
}
