package ru.danilsibgatullin.homeworklesson5.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;


public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private CyclicBarrier cb;
    private CountDownLatch latchStart;
    private CountDownLatch latchFinish;
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CyclicBarrier cb, CountDownLatch latchStart,CountDownLatch latchFinish) {
        this.latchStart=latchStart;
        this.latchFinish= latchFinish;
        this.race = race;
        this.cb=cb;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 20000));
            System.out.println(this.name + " готов");
            latchStart.countDown();
            cb.await();
            Thread.sleep(100); //даем время вывести надпись "ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!"
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        latchFinish.countDown();
    }
}
