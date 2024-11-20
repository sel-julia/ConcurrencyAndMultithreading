package com.epam.jmp.task2.subtask3;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Producer implements Runnable {

    private static final List<String> TOPICS = Arrays.asList("Topic1", "Topic2", "Topic3", "Topic4", "Topic5");

    private MessageBus messageBus;

    public Producer(MessageBus messageBus) {
        this.messageBus = messageBus;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            while (true) {
                String topic = TOPICS.get(random.nextInt(4));
                String payload = "Payload " + random.nextInt(5000);
                Message message = new Message(topic, payload);

                messageBus.publish(message);

                System.out.println("Published topic: " + topic + ", payload: " + payload);

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[PRODUCER] Thread is interrupted");
        }

    }

}
