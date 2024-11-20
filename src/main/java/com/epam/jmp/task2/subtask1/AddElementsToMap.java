package com.epam.jmp.task2.subtask1;

import java.util.Map;
import java.util.Random;

public class AddElementsToMap implements Runnable {

    private Map<Integer, Integer> map;

    public AddElementsToMap(Map<Integer, Integer> map) {
        this.map = map;
    }

    public void run() {
        System.out.println("[AddElementsToMap] Thread is started");
        try {
            Random random = new Random();
            while (!Thread.currentThread().isInterrupted()) {
                Integer key = random.nextInt(1000);
                Integer value = random.nextInt(1000);
                map.put(key, value);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[AddElementsToMap] Thread is interrupted");
        }
    }

}
