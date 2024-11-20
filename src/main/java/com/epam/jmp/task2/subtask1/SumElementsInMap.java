package com.epam.jmp.task2.subtask1;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SumElementsInMap implements Runnable{

    public static final String ANSI_RED = "\033[1;31m";
    public static final String ANSI_RESET = "\u001B[0m";

    private Map<Integer, Integer> map;

    public SumElementsInMap(Map<Integer, Integer> map) {
        this.map = map;
    }

    public void run() {
        System.out.println("[SumElementsInMap] Thread is started");
        try {
            AtomicInteger sum = new AtomicInteger();
            while (true) {
                try {
                    map.forEach((key, value) -> sum.addAndGet(value));
                    System.out.println("[SumElementsThread] Sum : " + sum);
                } catch (ConcurrentModificationException e) {
                    System.out.println(ANSI_RED + "ConcurrentModificationException caught." + ANSI_RESET);
                    Thread.currentThread().interrupt();
                }

                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[SumElementsThread] Thread is interrupted");
        }
    }


}
