package homeworklesson6;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.danilsibgatullin.homeworklesson6.services.ArrayAction;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayActionTest {

    public static ArrayAction arrayAction;
    public static Integer[] arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9;

    @BeforeAll
    public static void init(){
        arrayAction =new ArrayAction();
        arr1 = new Integer[]{ 1,2,4,4,2,3,4,1,7};
        arr2 = new Integer[]{ 4,4,4,4,4,4,4,4,4};
        arr3 = new Integer[]{ 1,2,3,4,41,44,444,4444,4,1};
        arr4 = new Integer[]{ 4,2,3,3,2,3,1,1,7};
        arr5 = new Integer[]{1, 1, 1, 4, 4, 4};
        arr6 = new Integer[]{0, 0, 0, 0, 0, 0, 0};
        arr7 = new Integer[]{-1, -2, -3, -4, -4, -5, -6};
        arr8 = new Integer[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        arr9 = new Integer[]{null, null, null, null, null};
    }

    @Test
    public void returnNumbersAfterConstTest() {
        Integer[]result;
        assertNotNull(arrayAction);
        result = arrayAction.returnNumbersAfterConst(arr1);
        assertArrayEquals(new Integer[]{1,7},result);
        result = arrayAction.returnNumbersAfterConst(arr2);
        assertNull(result);
        result = arrayAction.returnNumbersAfterConst(arr3);
        assertArrayEquals(new Integer[]{1},result);
        result = arrayAction.returnNumbersAfterConst(arr4);
        assertArrayEquals(new Integer[]{2,3,3,2,3,1,1,7},result);
        result = arrayAction.returnNumbersAfterConst(arr5);
        assertNull(result);
        assertThrows(RuntimeException.class,() -> arrayAction.returnNumbersAfterConst(arr6));
        assertThrows(RuntimeException.class,() -> arrayAction.returnNumbersAfterConst(arr7));
        assertThrows(RuntimeException.class,() -> arrayAction.returnNumbersAfterConst(arr8));
    }

    @Test
    public void arrHaveOnlyOneAndFourTest(){
        assertFalse(arrayAction.arrHaveOnlyOneAndFour(arr1));
        assertTrue(arrayAction.arrHaveOnlyOneAndFour(arr2));
        assertTrue(arrayAction.arrHaveOnlyOneAndFour(arr5));
        assertThrows(NullPointerException.class,() -> arrayAction.arrHaveOnlyOneAndFour(arr9));
    }

}
