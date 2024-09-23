package com.Minor.OptimalGo.header;

public class Runtime {
    private long startTime;
    private long endTime;

    // Starts the runtime clock
    public void start() {
        startTime = System.nanoTime();
        System.out.println("\033[1;36m[INFO]\033[0m Runtime measurement started...\n");
    }

    // Stops the runtime clock
    public void stop() {
        endTime = System.nanoTime();
        System.out.println("\033[1;32m[INFO]\033[0m Runtime measurement stopped.\n");
    }

    // Returns runtime in milliseconds
    private long getDurationInMillis() {
        return (endTime - startTime) / 1_000_000;
    }

    // Returns runtime in seconds
    private double getDurationInSeconds() {
        return (endTime - startTime) / 1_000_000_000.0;
    }

    // Formats the duration into human-readable time
    private String formatDuration() {
        long millis = getDurationInMillis();

        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        millis %= 1000;

        if (minutes > 0) {
            return String.format("%d min, %d sec, %d ms", minutes, seconds, millis);
        } else if (seconds > 0) {
            return String.format("%d sec, %d ms", seconds, millis);
        } else {
            return millis + " ms";
        }
    }

    // Prints the formatted runtime duration
    public void printDuration() {
        String duration = formatDuration();
        System.out.println("\033[1;35m==============================\033[0m");
        System.out.println("\033[1;33m   Program Execution Time\033[0m");
        System.out.println("\033[1;35m==============================\033[0m");
        System.out.println("\033[1;32m[RESULT]\033[0m Total runtime: \033[1;34m" + duration + "\033[0m");
        System.out.println("\033[1;35m==============================\033[0m");
    }
}
