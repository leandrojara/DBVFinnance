package br.com.leandrojara.dbv_finnance.util;

import java.lang.reflect.Field;
import java.util.Date;

public class Utils {

    private static Class[] primitiveTypes = {Double.class, Integer.class, Date.class, String.class, Long.class};

    public static boolean isPrimitive(Field field) {
        Class clazz = field.getDeclaringClass();
        if (clazz.isPrimitive() || clazz.isEnum()) {
            return true;
        } else {
            for (Class clazz2 : primitiveTypes) {
                if (clazz.equals(clazz2)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String getterName(String fieldName) {
        return "get" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1, fieldName.length());
    }
}
