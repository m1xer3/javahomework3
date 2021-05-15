package ru.danilsibgatullin.homeworklesson2.services;

public class PrintChar {

    private char lastChar;

    public char getLastChar() {
        return lastChar;
    }

    public void setLastChar(char lastChar) {
        this.lastChar = lastChar;
    }

    public synchronized void printChar(char c) {
        System.out.println(c);
    }
}