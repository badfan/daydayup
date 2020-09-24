package com.ff.common.annotations;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import androidx.fragment.app.Fragment;

public class FFWork {
    public static void inject(Object target) {
        inject(target, null);
//        View view = null;
//        try {
//            if (target instanceof Activity) {
//                view = ((Activity) target).getWindow().getDecorView();
//            }
//            Class<?> aClass = Class.forName(target.getClass().getName());
//            Field[] fields = aClass.getDeclaredFields();
//            //通过该方法设置所有的字段都可访问，否则即使是反射，也不能访问private修饰的字段
//            AccessibleObject.setAccessible(fields, true);
//            for (Field field : fields) {
//                boolean annotationPresent = field.isAnnotationPresent(FFView.class);
//                if (annotationPresent) {
//                    FFView annotation;
//                    annotation = field.getAnnotation(FFView.class);
//                    if (annotation != null) {
//                        int id = annotation.value();
//                        if (view != null) {
//                            View viewById = view.findViewById(id);
//                            field.set(target, field.getType().cast(viewById));
//                        }
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void inject(Object target, View view) {
        try {
            if (view == null) {
                if (target instanceof Activity) {
                    view = ((Activity) target).getWindow().getDecorView();
                } else if (target instanceof Fragment) {
                    view = ((Fragment) target).getActivity().getWindow().getDecorView();
                } else {
                    throw new RuntimeException("view is null ,but target is not activity or fragment");
                }
            }

            Class<?> aClass = Class.forName(target.getClass().getName());
            Field[] fields = aClass.getDeclaredFields();
            //通过该方法设置所有的字段都可访问，否则即使是反射，也不能访问private修饰的字段
            AccessibleObject.setAccessible(fields, true);
            for (Field field : fields) {
                boolean annotationPresent = field.isAnnotationPresent(FFView.class);
                if (annotationPresent) {
                    FFView annotation;
                    annotation = field.getAnnotation(FFView.class);
                    if (annotation != null) {
                        int id = annotation.value();
                        if (view != null) {
                            View viewById = view.findViewById(id);
                            field.set(target, field.getType().cast(viewById));
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
