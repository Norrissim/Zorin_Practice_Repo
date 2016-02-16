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

    public static void update(String nameFile, String newText) throws FileNotFoundException {
        exists(nameFile);
        StringBuilder strBuild = new StringBuilder();
        String oldFile = read(nameFile);
        strBuild.append(oldFile);
        strBuild.append(newText);
        write(nameFile, strBuild.toString());
    }
}
