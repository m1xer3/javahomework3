package ru.danilsibgatullin.homeworklesson7.services;

import ru.danilsibgatullin.homeworklesson7.annotations.AfterSuite;
import ru.danilsibgatullin.homeworklesson7.annotations.BeforeSuite;
import ru.danilsibgatullin.homeworklesson7.annotations.Test;

import javax.xml.ws.Action;
import javax.xml.ws.WebServiceRef;


public class ClassWithMethods{

    @AfterSuite
    public void testa(){
        System.out.println("Checked AfterSuite");
    }

   // @AfterSuite
    public void testad(){
        System.out.println("Checked AfterSuite");
    }

    @Test(value = 1)
    public void test1(){
        System.out.println("Checked test1");
    }

    @Test(value = 2)
    public void test2(){
        System.out.println("Checked test2");
    }

    @Action
    @WebServiceRef
    public void test3(){
        System.out.println("Checked test3");
    }

    @Test(value = 4)
    public void test4(){
        System.out.println("Checked test4");
    }

    @Test(value = 10)
    public void test() {
        System.out.println("Checked test");
    }

    @Test(value = 10)
    public void test6() {
        System.out.println("Checked test6");
    }


    @Test(value = 1)
    public void test5(){
        System.out.println("Checked test5");
    }

    @BeforeSuite
    public void testb(){
        System.out.println("Checked BeforeSuite");
    }
}
