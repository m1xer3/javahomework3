package ru.danilsibgatullin.homeworklesson5;

import ru.danilsibgatullin.homeworklesson5.service.Car;
import ru.danilsibgatullin.homeworklesson5.service.Race;
import ru.danilsibgatullin.homeworklesson5.service.Road;
import ru.danilsibgatullin.homeworklesson5.service.Tunnel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class AppStart {
    public static final int CARS_COUNT = 5;
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latchStart = new CountDownLatch(CARS_COUNT);
        CountDownLatch latchFinish = new CountDownLatch(CARS_COUNT);
        CyclicBarrier cb = new CyclicBarrier(CARS_COUNT);
        Semaphore s = new Semaphore(CARS_COUNT/2);
        Lock lock = new ReentrantLock();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(s), new Road(40));
        //Race race = new Race(new Tunnel(s)); //проверка условия на возможность проехать в тоннель только половина участников
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10),cb,latchStart,latchFinish);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        latchStart.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        latchFinish.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
