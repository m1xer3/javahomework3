package ru.danilsibgatullin.homeworklesson1.exercise1_2;

import ru.danilsibgatullin.homeworklesson1.exercise1_2.services.ArrayProcessing;

import java.util.ArrayList;


public class AppStart {
    public static void main(String[] args) {

        String [] arrI = {"one","two","tree","forth"};
        ArrayList<String> outList;

        ArrayProcessing<String> arrProc =new ArrayProcessing<>();

        try {
            arrProc.changeElementsPositionInArray(arrI, 0, 3);
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Не корректно указаны индексы для замены");
        }

        outList=arrProc.convertToArrayList(arrI);

        for (String integer : outList) {
            System.out.println(integer);
        }

    }
}
