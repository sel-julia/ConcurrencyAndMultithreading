package com.epam.jmp.task2.subtask3;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class MessageBus {

    private final Queue<Message> queue = new LinkedList<>();

    public synchronized void publish(Message message) {
        queue.add(message);
        notifyAll();
    }

    public synchronized Optional<Message> consume(String topic) {
        for (Iterator<Message> it = queue.iterator(); it.hasNext(); ) {
            Message message = it.next();
            if (message.getTopic().equals(topic)) {
                it.remove();
                return Optional.of(message);
            }
        }
        return Optional.empty();
    }
}
