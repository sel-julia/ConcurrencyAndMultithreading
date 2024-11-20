package com.epam.jmp.task2.subtask1;

import org.apache.commons.lang3.time.StopWatch;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PerformanceTest {

    public static final Integer OPERATION_COUNT = 10_000_000;
    public static final String ANSI_RED = "\033[1;31m";

    public static final String ANSI_GREEN = "\033[1;32m";
    public static final String ANSI_RESET = "\u001B[0m";


    public static void main(String[] args) throws InterruptedException {
        System.out.println("Test HashMap:");
        run(new HashMap<>());

        System.out.println("Test ConcurrentHashMap:");
        run(new ConcurrentHashMap<>());

        System.out.println("Test Collections.synchronizedMap:");
        run(Collections.synchronizedMap(new HashMap<>()));

        System.out.println("Test custom ThreadSafeMap:");
        run(new ThreadSafeMap<>());
    }

    public static void run(Map<Integer, Integer> map) throws InterruptedException {

        Thread addElements = new Thread(() -> {
            StopWatch watch = new StopWatch();
            watch.start();

            Random random = new Random();
            for (int i = 0; i <= OPERATION_COUNT; i++) {
                Integer key = random.nextInt(1000);
                Integer value = random.nextInt(1000);
                map.put(key, value);
            }

            watch.stop();
            System.out.println(ANSI_GREEN + "[AddElementsToMap] Time Elapsed: " + watch.getTime() + ANSI_RESET);
        }, "Add elements thread");

        Thread sumElements = new Thread(() -> {
            StopWatch watch = new StopWatch();
            watch.start();
            AtomicInteger sum = new AtomicInteger();

            for (int i = 0; i <= OPERATION_COUNT; i++) {
                try {
                    map.forEach((key, value) -> sum.addAndGet(value));
                } catch (ConcurrentModificationException e) {
                    System.out.println(ANSI_RED + "ConcurrentModificationException caught." + ANSI_RESET);
                    Thread.currentThread().interrupt();
                }
            }

            watch.stop();
            System.out.println(ANSI_GREEN + "[SumElementsThread] Time Elapsed: " + watch.getTime() + ANSI_RESET);
        });

        addElements.start();
        addElements.join();

        sumElements.start();
        sumElements.join();

    }
}
