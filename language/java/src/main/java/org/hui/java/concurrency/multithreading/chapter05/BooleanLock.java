package org.hui.java.concurrency.multithreading.chapter05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.currentThread;

public class BooleanLock implements Lock {
    private Thread currentThread;
    private boolean locked = false;
    private List<Thread> blockedList = new ArrayList<>();
    @Override
    public void lock() throws InterruptedException {
        synchronized (this) {
            while (locked) {
                try {
                    if (!blockedList.contains(currentThread())) {
                        blockedList.add(currentThread());
                    }
                    this.wait();
                } catch (InterruptedException e) {
                    // 如果当前线程在 wait 时被中断，则从 blockedList 中将其删除，避免内存泄漏
                    blockedList.remove(currentThread());
                    // 继续抛出中断异常
                    throw e;
                }
            }
            blockedList.remove(currentThread());
            this.locked = true;
            this.currentThread = currentThread();
        }
    }

    @Override
    public void lock(long mills) throws InterruptedException, TimeoutException {
        synchronized (this) {
            if (mills <= 0) {
                this.lock();
            } else {
                long remainingMills = mills;
                long endMills = currentTimeMillis() + remainingMills;
                while (locked) {
                    if (remainingMills <= 0) {
                        throw new TimeoutException("can not get the lock during " + mills + " ms.");
                    }
                    if (!blockedList.contains(currentThread())) {
                        blockedList.add(currentThread);
                        this.wait();
                        remainingMills = endMills - currentTimeMillis();
                    }
                    blockedList.remove(currentThread());
                    this.locked = true;
                    this.currentThread = currentThread();
                }
            }
        }
    }

    @Override
    public void unlock() {
        synchronized (this) {
            if (currentThread == currentThread()) {
                this.locked = false;
                System.out.println(currentThread().getName() + " will notifyAll.");
                this.notifyAll();
            }
        }
    }

    @Override
    public List<Thread> getBlockedThreads() {
        return Collections.unmodifiableList(blockedList);
    }
}
