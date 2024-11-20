package com.epam.jmp.task2.subtask4;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/*
Create simple object pool with support for multithreaded environment.
No any extra inheritance, polymorphism or generics needed here, just implementation of simple class:
*/
public class Main {

    public static void main(String[] args) {
        int poolSize = 3;
        int numProducers = 5;
        int numConsumers = 5;

        BlockingObjectPool objectPool = new BlockingObjectPool(poolSize);

        Thread[] producers = new Thread[numProducers];
        Thread[] consumers = new Thread[numConsumers];

        for (int i = 0; i < numProducers; i++) {
            producers[i] = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Object obj = new Object();
                        objectPool.take(obj);
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Thread: " + Thread.currentThread().getName() +  ". Producer interrupted");
                    Thread.currentThread().interrupt();
                }
            }, "Producer-" + (i + 1));
        }

        for (int i = 0; i < numConsumers; i++) {
            consumers[i] = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        objectPool.get();
                        Thread.sleep(800);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Thread: " + Thread.currentThread().getName() + ". Consumer interrupted");
                    Thread.currentThread().interrupt();
                }
            }, "Consumer-" + (i + 1));
        }

        ExecutorService executor = Executors.newFixedThreadPool(10);
        Stream.of(producers).forEach(p -> executor.execute(p));
        Stream.of(consumers).forEach(c -> executor.execute(c));
        executor.shutdown();

        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }


    }

}
