package ru.danilsibgatullin.homeworklesson1.exercise3;

import ru.danilsibgatullin.homeworklesson1.exercise3.models.Apple;
import ru.danilsibgatullin.homeworklesson1.exercise3.models.Box;
import ru.danilsibgatullin.homeworklesson1.exercise3.models.Orange;

public class AppStart {
    public static void main(String[] args) {
        Box<Apple> box1 =new Box();
        Box<Apple> box2 =new Box();
        Box<Orange> box3 =new Box();

        box1.addFruit(new Apple(2.1f));
        box1.addFruit(new Apple(3f));
        box1.addFruit(new Apple(4f));

        box3.addFruit(new Orange(2f));
        box3.addFruit(new Orange(3f));

        //вес до перемещения
        System.out.println(box1.getWeight());
        System.out.println(box2.getWeight());

        System.out.println(box1.compare(box3));
        box1.procedFruitToNewBox(box2);

        //вес после перемещения
        System.out.println(box1.getWeight());
        System.out.println(box2.getWeight());


    }
}
