package ru.danilsibgatullin.homeworklesson7.services;

import ru.danilsibgatullin.homeworklesson7.annotations.AfterSuite;
import ru.danilsibgatullin.homeworklesson7.annotations.BeforeSuite;
import ru.danilsibgatullin.homeworklesson7.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class AppTest {

    public static void start(Class testClass)throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException{
        Method[] me = testClass.getDeclaredMethods();
        Constructor constr=testClass.getConstructor();
        checkUniqBeforeAndAfter(me);
        headFootActions(me,constr, BeforeSuite.class);
        actionsByPriority(me,constr);
        headFootActions(me,constr,AfterSuite.class);
    }

    public static void start(String className) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        Class testClass = Class.forName(className);
        start(testClass);
    }

    private static void checkUniqBeforeAndAfter(Method[] me){
        int countBefore=0;
        int countAfter=0;
        for (Method method : me) {
            if(method.isAnnotationPresent(BeforeSuite.class)){
                countBefore++;
            }
            if(method.isAnnotationPresent(AfterSuite.class)){
                countAfter++;
            }
        }
        if(countBefore>1||countAfter>1) throw new RuntimeException();
    }

    private static void headFootActions(Method[] me,Constructor constr,Class test) throws RuntimeException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Method beforeMethod = null;
        for (Method method : me) {
            if(method.isAnnotationPresent(test)){
                beforeMethod=method;
            }
        }
        if(beforeMethod!=null){
            beforeMethod.invoke(constr.newInstance(),null);
        }
    }

    private static void  actionsByPriority(Method[] me,Constructor constr) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        SortedMap<Integer, List<Method>> outMethods = new TreeMap<>();
        for (Method method:me) {
            if(method.isAnnotationPresent(Test.class)){
                Annotation annotation =method.getAnnotation(Test.class);
                Test t = (Test)annotation;
                if(outMethods.containsKey(t.value())){
                    outMethods.get(t.value()).add(method);
                    continue;
                }
                List<Method> col =new ArrayList<>() ;
                col.add(method);
                outMethods.put(t.value(),col);
            }
        }
        for (List<Method> methodArr : outMethods.values()) {
            for (Method method : methodArr) {
                method.invoke(constr.newInstance(),null);
            }
        }
    }
}
