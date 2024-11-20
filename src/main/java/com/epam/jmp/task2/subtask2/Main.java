package com.epam.jmp.task2.subtask2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
Create three threads:

1st thread is infinitely writing random number to the collection;
2nd thread is printing sum of the numbers in the collection;
3rd is printing square root of sum of squares of all numbers in the collection.

Make these calculations thread-safe using synchronization block. Fix the possible deadlock.
* */
public class Main {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();

        Thread addElements = new Thread(() -> {
            try{
                while(true) {
                    Integer number = random.nextInt(1000);
                    synchronized (list) {
                        list.add(number);
                    }
                    System.out.println("Add " + number);

                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Add thread is interrupted.");
            }
        });

        Thread sumElements = new Thread(() -> {
            try {
                while (true) {
                    synchronized (list) {
                        System.out.println("Sum: " + list.stream().reduce(0, Integer::sum));
                    }

                    Thread.sleep(700);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Sum thread is interrupted.");
            }
        });

        Thread squareElements = new Thread(() -> {
            try {
                while (true) {
                    synchronized (list) {
                        double sumOfSquares = list
                                .stream()
                                .map(i -> Math.pow(i, 2))
                                .reduce(0d, Double::sum);
                        System.out.println("square root of sum of squares of all numbers: " + Math.sqrt(sumOfSquares));
                    }

                    Thread.sleep(800);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Sum thread is interrupted.");
            }
        });

        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(addElements);
        executor.execute(sumElements);
        executor.execute(squareElements);
        executor.shutdown();

        try {
            if (!executor.awaitTermination(3, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }


}



