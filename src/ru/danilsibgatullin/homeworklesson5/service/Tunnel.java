package ru.danilsibgatullin.homeworklesson5.service;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private Semaphore s;

    public Tunnel(Semaphore s) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        this.s=s;
    }


    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                s.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep((length / c.getSpeed() * 1000)+10000);  // +10 секунд для наглядности
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                s.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}