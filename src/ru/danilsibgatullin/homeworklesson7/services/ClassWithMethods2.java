package ru.danilsibgatullin.homeworklesson7.services;


import ru.danilsibgatullin.homeworklesson7.annotations.BeforeSuite;
import ru.danilsibgatullin.homeworklesson7.annotations.Test;

public class ClassWithMethods2 {
    @Test
    public void test(){
        System.out.println("test in 2 ");
    }

    @BeforeSuite
    public void before(){
        System.out.println("BeforeSuite");
    }
}