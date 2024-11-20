package com.epam.jmp.task2.subtask1;

import org.apache.commons.lang3.time.StopWatch;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 Create HashMap<Integer, Integer>.
 The first thread adds elements into the map, the other go along the given map and sum the values.
 Threads should work before catching ConcurrentModificationException.
 Try to fix the problem with ConcurrentHashMap and Collections.synchronizedMap().
 What has happened after simple Map implementation exchanging?
 How it can be fixed in code? Try to write your custom ThreadSafeMap with synchronization and without.
 Run your samples with different versions of Java (6, 8, and 10, 11) and measure the performance.
 Provide a simple report to your mentor.
 */
public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\033[1;32m";

    public static void main(String[] args) {

        System.out.println("--------------------");
        System.out.println("run app with HashMap");
        run(new HashMap<>(), 1);

        System.out.println("--------------------");
        System.out.println("run app with ConcurrentHashMap");
        run(new ConcurrentHashMap<>(), 1);

        System.out.println("--------------------");
        System.out.println("run app with Collections.synchronizedMap");
        run(Collections.synchronizedMap(new HashMap<>()), 1);

        System.out.println("--------------------");
        System.out.println("run app with ThreadSafeMap");
        run(new ThreadSafeMap<>(), 1);

    }

    public static void run(Map<Integer, Integer> map, long minutes) {
        StopWatch watch = new StopWatch();
        watch.start();
        Thread addElementsThread = new Thread(new AddElementsToMap(map));
        Thread sumElementsThread = new Thread(new SumElementsInMap(map));

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(addElementsThread);
        executor.execute(sumElementsThread);
        executor.shutdown();

        try {
            if (!executor.awaitTermination(minutes, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        watch.stop();
        System.out.println(ANSI_GREEN + "Time Elapsed: " + watch.getTime() + ANSI_RESET);

    }


}