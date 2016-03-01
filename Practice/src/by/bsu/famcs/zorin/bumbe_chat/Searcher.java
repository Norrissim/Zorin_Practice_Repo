package by.bsu.famcs.zorin.bumbe_chat;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Searcher {
    Searcher() {
    }

    public void startSwitchForMenu(String fileName, ArrayList<Message> history) {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
            LogWorker.update(fileName, "Exception: input issues");
        }
    }

    private void showSearchMenu() {
        System.out.println("1. by author");
        System.out.println("2. by key word");
        System.out.println("3. by regular expression");
        System.out.println("4. by time period");
    }

    private void searchByTime(String fileName, ArrayList<Message> history) {
        try {
            LogWorker.update(fileName, "Searching by time period :");
            int count = 0;
            System.out.println("Enter start-time of period in format: dd/mm/yyyy hh:mm:ss");
            Scanner sc = new Scanner(new InputStreamReader(System.in));
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Date startTime = null;
            Date endTime = null;
            startTime = format.parse(sc.nextLine());
            System.out.println("Enter end-time of period in format: dd/mm/yyyy hh:mm:ss");
            endTime = format.parse(sc.nextLine());
            boolean per = false;
            for (Message iterator : history) {
                if (startTime != null && endTime != null && iterator.getTimestamp().after(startTime) && iterator.getTimestamp().before(endTime)) {
                    count++;
                    System.out.println(iterator.toString());
                    per = true;
                }
            }
            LogWorker.update(fileName, count + " messages are found");
            LogWorker.update(fileName, "--------------------");
            if (!per) {
                System.out.println("This period of time hasn't messages");
            }

        } catch (ParseException e) {
            System.out.println("Wrong format of data. Please, next time input data in right format.");
            LogWorker.update(fileName, "Exception: Wrong format of data");
            LogWorker.update(fileName, "--------------------");
        }
    }

    private void searchByExpression(String fileName, ArrayList<Message> history) {
        try {
            LogWorker.update(fileName, "Searching by regular expression :");
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
            LogWorker.update(fileName, count + " messages are found");
            LogWorker.update(fileName, "--------------------");
            if (!find) {
                System.out.println("History hasn't messages with this regular expression");
            }
        } catch (PatternSyntaxException e) {
            System.out.println("Error: regular expression syntax error.");
            LogWorker.update(fileName, "Exception: regular expression syntax error");
        } catch (IOException e) {
            e.printStackTrace();
            LogWorker.update(fileName, "Exception: input issues");
        }
    }

    private void SearchByKeyWord(String fileName, ArrayList<Message> history) {
        try {
            LogWorker.update(fileName, "Searching by key word :");
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
            LogWorker.update(fileName, count + " messages are found");
            LogWorker.update(fileName, "--------------------");
            if (!find) {
                System.out.println("History hasn't messages with this word");
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogWorker.update(fileName, "Exception: input issues");
        }
    }

    private void searchByAuthor(String fileName, ArrayList<Message> history) {
        try {
            LogWorker.update(fileName, "Searching by author :");
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
            LogWorker.update(fileName, count + " messages are found");
            LogWorker.update(fileName, "--------------------");
            if (!find) {
                System.out.println("This author does not write messages");
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogWorker.update(fileName, "Exception: input issues");
        }
    }
}
