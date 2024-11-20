package com.epam.jmp.task2.subtask4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingObjectPool {

    private Queue<Object> pool;
    private int size;
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    /**
     * Creates filled pool of passed size
     *
     * @param size of pool
     */
    public BlockingObjectPool(int size) {
        pool = new LinkedList<>();
        this.size = size;
        for (int i = 0; i < size; i++) {
            pool.add(new Object());
        }

        System.out.println("Pool is initialized");
        pool.forEach(System.out::println);
        System.out.println("--------------");
    }

    /**
     * Gets object from pool or blocks if pool is empty
     *
     * @return object from pool
     */
    public Object get() throws InterruptedException {
        Object obj;
        lock.lock();
        try {
            while (pool.isEmpty()) {
                notEmpty.await();
            }
            obj = pool.poll();
            System.out.println("Thread: " + Thread.currentThread().getName() +  ". Consumed: " + obj);
            notFull.signal();
        } finally {
            lock.unlock();
        }
        return obj;
    }

    /**
     * Puts object to pool or blocks if pool is full
     *
     * @param object to be taken back to pool
     */
    public void take(Object object) throws InterruptedException {
        lock.lock();
        try {
            while (pool.size() == size) {
                notFull.await();
            }
            pool.offer(object);
            System.out.println("Thread: " + Thread.currentThread().getName() +  ". Added: " + object);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
}

