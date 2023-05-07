package com.xkcoding.helloworld;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class MyData {

}

public class Scratch {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + " lock");

        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public void muUnLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + " un lock");
    }

    public static void main(String[] args) {
        Scratch scratch = new Scratch();

        new Thread(() -> {
            scratch.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            scratch.muUnLock();
        }, "t1").start();

        new Thread(() -> {
            scratch.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            scratch.muUnLock();
        }, "t2").start();
    }
}

