package com.interview.practice;

import java.util.LinkedList;

public class ProducerConsumerThread{

    public static void main(String[] args) throws InterruptedException{
        final PC pc = new PC();

        Thread T1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread T2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start the threads t1 and T2

        T1.start();
        T2.start();

        T1.join();
        T2.join();
    }
}


class PC {
    LinkedList<Integer> list = new LinkedList<>();
    int capacity = 2;

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (this) {
                if (list.size() == capacity) {
                    wait();
                }
                System.out.println("The producer produced :" + value);
                list.add(value++);

                notify();

                Thread.sleep(1000);
            }
        }
    }


    public void consume() throws InterruptedException {

        while (true) {
            synchronized (this) {
                if (list.isEmpty())
                    wait();
                int value = list.removeFirst();
                System.out.println("Consumer Consumed :" + value);
                notify();
                Thread.sleep(1000);
            }
        }

    }
}