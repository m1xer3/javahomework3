package ru.danilsibgatullin.homeworklesson2;

import ru.danilsibgatullin.homeworklesson2.services.PrintChar;

public class AppStart {

    public static void main(String[] args) {

        PrintChar p =new PrintChar();
        p.setLastChar('C');

        Thread tA = new Thread(() -> {
            synchronized (p){
                for(int i=0;i<5;i++){
                    while (p.getLastChar()!='C'){
                        try {
                            p.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    p.printChar('A');
                    p.setLastChar('A');;
                    p.notifyAll();
                }
            }
        });
        Thread tB = new Thread(() -> {
            synchronized (p){
                for(int i=0;i<5;i++){
                    while (p.getLastChar()!='A'){
                        try {
                            p.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    p.printChar('B');
                    p.setLastChar('B');;
                    p.notifyAll();
                }
            }
        });
        Thread tC = new Thread(() -> {
            synchronized (p){
                for(int i=0;i<5;i++){
                    while (p.getLastChar()!='B'){
                        try {
                            p.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    p.printChar('C');
                    p.setLastChar('C');;
                    p.notifyAll();
                }
            }
        });

        tA.start();
        tB.start();
        tC.start();
    }
}