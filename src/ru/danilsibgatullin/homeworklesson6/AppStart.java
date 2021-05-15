package ru.danilsibgatullin.homeworklesson6;

import ru.danilsibgatullin.homeworklesson6.services.ArrayAction;

public class AppStart {
    public static void main(String[] args) {

        Integer[] arr = { 1,2,4,4,2,3,4,1,7};
        Integer[] arr1 = { 1,1,1,4,4,4,2};
        ArrayAction action = new ArrayAction();
        Integer[] arrOut = action.returnNumbersAfterConst(arr);
        for (Integer integer : arrOut) {
            System.out.print(integer+" ");
        }
        System.out.println(action.arrHaveOnlyOneAndFour(arr1));
    }
}
