package by.bsu.famcs.zorin.bumbe_chat;

import java.io.*;

public class LogWorker {
    private static void write(String fileName, String text) {
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
            }
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                out.print(text);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String read(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        StringBuilder strBuild = new StringBuilder();
        exists(fileName);
        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    strBuild.append(s);
                    strBuild.append("\n");
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return strBuild.toString();
    }

    private static void exists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }
    }

    public static void update(String nameFile, String newText) {
        try {
            exists(nameFile);
            StringBuilder strBuild = new StringBuilder();
            String oldFile = read(nameFile);
            strBuild.append(oldFile);
            strBuild.append(newText);
            write(nameFile, strBuild.toString());
        }
        catch (FileNotFoundException e){
            File newFile = new File(nameFile);
            try {
                newFile.createNewFile();
                LogWorker.update(nameFile, "Exception: input issues");
                LogWorker.update(nameFile, "--------------------");
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
    }
}
