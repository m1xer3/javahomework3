package ru.danilsibgatullin.homeworklesson1.exercise1_2.services;

import java.util.ArrayList;


public class ArrayProcessing<T> {

    public <T> void changeElementsPositionInArray(T [] arr,int a,int b) throws ArrayIndexOutOfBoundsException {
        if (a < 0||a>(arr.length-1)||b < 0||b > arr.length-1){
            throw new ArrayIndexOutOfBoundsException();
        }
        T tmp=arr[a];
        arr[a]=arr[b];
        arr[b]=tmp;
    }

    public  ArrayList<T> convertToArrayList (T [] arr){
        ArrayList<T> outList = new ArrayList<>();
        for (T t:arr) {
            outList.add(t);
        }
        return outList;
    }


}
