package ru.danilsibgatullin.homeworklesson1.exercise3.models;

import java.util.ArrayList;

public class Box<T> {

    ArrayList<T> fruitBox = new ArrayList<>();

    public ArrayList<T> getFruitBox() {
        return fruitBox;
    }

    public void addFruit (T t) {
        fruitBox.add(t);
    }

    public Float getWeight (){
        Float sum=0f;
        for (T fruit : fruitBox) {
            if (fruit instanceof Fruit){
                Fruit f = (Fruit) fruit;
                sum+=f.getWidth();
            }
        }
        return sum;
    }

    public boolean compare (Box<? extends Fruit> box){
        return this.getWeight()==box.getWeight();
    }

    public  void procedFruitToNewBox (Box<T> newBox){
        for (T fruit : fruitBox) {
            newBox.getFruitBox().add(fruit);
        }
        fruitBox.clear();
    }
}