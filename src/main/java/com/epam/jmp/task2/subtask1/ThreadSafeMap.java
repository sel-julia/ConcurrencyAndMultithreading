package com.epam.jmp.task2.subtask1;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;

public class ThreadSafeMap<K, V> extends HashMap<K, V>{

    private final Lock lock = new ReentrantLock();

    public V put(K key, V value) {
        lock.lock();
        V res;
        try {
            res = super.put(key, value);
        } finally {
            lock.unlock();
        }

        return res;
    }

    public void forEach(BiConsumer<? super K, ? super V> action) {
        lock.lock();
        try {
            super.forEach(action);
        } finally {
            lock.unlock();
        }
    }

    public Collection<V> values() {
        lock.lock();
        try{
           return super.values();
        } finally {
            lock.unlock();
        }
    }


}
