package com.example.hilibrary.processor;

import com.example.my_annotation.MyRunTimeAnnotation;

import java.lang.reflect.Constructor;

public class AnnotationProcessor {

    public static void init(Object object) throws IllegalAccessException {
        if (!(object instanceof User)) {
            throw new IllegalAccessException("异常");
        }
        //
        Constructor[] constructors = object.getClass().getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            MyRunTimeAnnotation useFill = (MyRunTimeAnnotation) constructor.getAnnotation(MyRunTimeAnnotation.class);
            int age = useFill.age();
            int id = useFill.id();
            String name = useFill.name();

            User use = (User) object;
            use.setAge(age);
            use.setId(id);
            use.setName(name);
        }

        //object.getClass().getDeclaredFields()
    }
}
