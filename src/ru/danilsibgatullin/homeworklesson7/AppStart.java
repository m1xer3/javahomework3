package ru.danilsibgatullin.homeworklesson7;

/*
1. Создать класс, который может выполнять «тесты», в качестве тестов выступают классы с наборами методов с аннотациями @Test.
Для этого у него должен быть статический метод start(), которому в качестве параметра передается или объект типа Class,
или имя класса. Из «класса-теста» вначале должен быть запущен метод с аннотацией @BeforeSuite, если такой имеется, далее
запущены методы с аннотациями @Test, а по завершению всех тестов – метод с аннотацией @AfterSuite. К каждому тесту
необходимо также добавить приоритеты (int числа от 1 до 10), в соответствии с которыми будет выбираться порядок их
выполнения, если приоритет одинаковый, то порядок не имеет значения. Методы с аннотациями @BeforeSuite и @AfterSuite
должны присутствовать в единственном экземпляре, иначе необходимо бросить RuntimeException при запуске «тестирования».
Это домашнее задание никак не связано с темой тестирования через JUnit и использованием этой библиотеки, то есть проект
пишется с нуля.
 */


import ru.danilsibgatullin.homeworklesson7.services.AppTest;
import ru.danilsibgatullin.homeworklesson7.services.ClassWithMethods;
import ru.danilsibgatullin.homeworklesson7.services.ClassWithMethods2;

import java.lang.reflect.InvocationTargetException;

public class AppStart {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        ClassWithMethods cl = new ClassWithMethods();
        //AppTest.start(cl.getClass());
        //AppTest.start(new ClassWithMethods2().getClass());
        AppTest.start("ru.danilsibgatullin.homeworklesson7.services.ClassWithMethods");

    }
}