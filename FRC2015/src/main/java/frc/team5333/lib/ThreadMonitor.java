package frc.team5333.lib;

import java.util.ArrayList;
import java.util.List;

/**
 * A debugging tool for keeping track of active threads
 *
 * @author Jaci
 */
public class ThreadMonitor {

    /**
     * Get a list of all the threads currently running
     */
    public static List<Thread> activeThreads() {
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for (Thread thread : Thread.getAllStackTraces().keySet())
            threads.add(thread);

        return threads;
    }

    /**
     * Get a thread with the specified name
     * If no thread is specified (null or empty), it will default to the current running thread
     */
    public static Thread getThread(String thread) {
        if (thread == null || thread.equals(""))
            return Thread.currentThread();

        for (Thread t : activeThreads())
            if (t.getName().equals(thread))
                return t;

        return null;
    }

    /**
     * Get the stacktrace of the thread specified.
     * If no thread is specified (null or empty), it will default to the current running thread
     */
    public static StackTraceElement[] getRawStackTrace(String thread) {
        Thread t = getThread(thread);

        if (t != null)
            return t.getStackTrace();

        return null;
    }

    /**
     * Get the formatted stack trace for the thread specified
     * If no thread is specified (null or empty), it will default to the current running thread
     */
    public static String getFormattedStackTrace(String thread) {
        StringBuilder builder = new StringBuilder();
        builder.append("Stack trace for thread ID: " + thread);
        StackTraceElement[] elements = getRawStackTrace(thread);
        if (elements == null)
            builder.append("\tThread does not exist\n");
        else
            for (StackTraceElement element : elements)
                builder.append("\t" + element + "\n");

        return builder.toString();
    }
}
