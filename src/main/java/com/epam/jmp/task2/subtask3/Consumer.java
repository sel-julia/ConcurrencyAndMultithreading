package com.epam.jmp.task2.subtask3;

import java.util.Optional;

public class Consumer implements Runnable {

    private MessageBus messageBus;
    private String topic;

    public Consumer(MessageBus messageBus, String topic) {
       this.messageBus = messageBus;
       this.topic = topic;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Optional<Message> message = messageBus.consume(topic);
                message.ifPresent(m -> System.out.println("Consumer for topic " + topic + " consumed: " + m.getPayload()));
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[CONSUMER] Thread is interrupted");
        }
    }

}
