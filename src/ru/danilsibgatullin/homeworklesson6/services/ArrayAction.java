package ru.danilsibgatullin.homeworklesson6.services;

/*
2. Написать метод, которому
в качестве аргумента передается не пустой одномерный целочисленный массив. Метод должен вернуть новый массив, который
получен путем вытаскивания из исходного массива элементов, идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку,
иначе в методе необходимо выбросить RuntimeException. Написать набор тестов для этого метода (по 3-4 варианта входных данных). Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].

3. Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы,
то метод вернет false; Написать набор тестов для этого метода (по 3-4 варианта входных данных).
 */

public class ArrayAction {

    private static final int CONST=4;
    private int indexLastCONST;

    public Integer[] returnNumbersAfterConst(Integer[] arr) {
        Integer[] arrOut;
        indexLastCONST=-1;
        for(int i=0;i<arr.length;i++){
            if(arr[i].equals(CONST)){
                indexLastCONST=i;
            }
        }
        if(indexLastCONST<0){
            throw new RuntimeException();
        } else if((arr.length-1)==indexLastCONST){
            return null;
        }else {
            arrOut = new Integer[(arr.length-1)-indexLastCONST];
        }
        int outIndex=0;
        for (int i =indexLastCONST+1; i<arr.length;i++){
            arrOut[outIndex]=Integer.valueOf(arr[i]);
            outIndex++;
        }
        return arrOut;
    }

    public boolean arrHaveOnlyOneAndFour(Integer[] arr){
        for (Integer integer : arr) {
            if(!(integer.equals(1)||integer.equals(4))){
                return false;
            }
        }
        return true;
    }
}