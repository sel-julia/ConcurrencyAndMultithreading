package com.epam.jmp.task2.subtask3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
Task 3 - Where’s Your Bus, Dude?
Cost: 20 points.
Implement message bus using Producer-Consumer pattern.

Implement asynchronous message bus. Do not use queue implementations from java.util.concurrent.
Implement producer, which will generate and post randomly messages to the queue.
Implement consumer, which will consume messages on specific topic and log to the console message payload.
(Optional) Application should create several consumers and producers that run in parallel.
*/
public class Main {

    public static void main(String[] args) {

        MessageBus bus = new MessageBus();

        Thread producer1 = new Thread(new Producer(bus));
        Thread producer2 = new Thread(new Producer(bus));

        Thread consumer1 = new Thread(new Consumer(bus, "Topic1"));
        Thread consumer2 = new Thread(new Consumer(bus, "Topic2"));
        Thread consumer3 = new Thread(new Consumer(bus, "Topic3"));

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(producer1);
        executor.execute(producer2);
        executor.execute(consumer1);
        executor.execute(consumer2);
        executor.execute(consumer3);
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
