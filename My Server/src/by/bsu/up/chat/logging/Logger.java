package by.bsu.up.chat.logging;

/**
 * Created by User on 07/04/2016.
 */
public interface Logger {

    /**
     * Prints informational message
     * @param message the message to be printed
     */
    void info(String message);

    /**
     * Prints error message
     * @param message the message to be printed
     * @param e exception which occurred and can be printed for mor details
     */
    void error(String message, Throwable e);

}