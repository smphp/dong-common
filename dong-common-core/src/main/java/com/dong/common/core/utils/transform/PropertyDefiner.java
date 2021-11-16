package com.dong.common.core.utils.transform;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

public class PropertyDefiner {
    private static final String READ_PERFIX = "get";
    private static final String IS_PERFIX = "is";
    private static final String WRITE_PERFIX = "set";
    private Field field;
    private Class clazz;
    private Class fieldClazz;
    private Method readMethod;
    private Method writeMethod;
    private String fieldName;
    private FieldType fieldType;

    public Class getClazz() {
        return this.clazz;
    }

    public Field getField() {
        return this.field;
    }

    public PropertyDefiner(Field field, Class clazz) {
        this.field = field;
        this.clazz = clazz;
        this.initType();
        this.initMethod();
    }

    private void initType() {
        this.fieldClazz = this.field.getType();
        if (!this.fieldClazz.isPrimitive() && !this.fieldClazz.isEnum() && !String.class.isAssignableFrom(this.fieldClazz)) {
            if (Collection.class.isAssignableFrom(this.fieldClazz)) {
                this.fieldType = FieldType.COLLECTION;
            } else if (this.fieldClazz.isArray()) {
                this.fieldType = FieldType.ARRAY;
            } else if (Map.class.isAssignableFrom(this.fieldClazz)) {
                this.fieldType = FieldType.MAP;
            } else {
                this.fieldType = FieldType.OBJECT;
            }
        } else {
            this.fieldType = FieldType.PRIMITIVE;
        }

    }

    private void initMethod() {
        this.fieldName = capitalize(this.field.getName());

        try {
            if (this.field.getType() == Boolean.TYPE) {
                this.readMethod = this.clazz.getMethod("is" + this.fieldName);
            } else {
                this.readMethod = this.clazz.getMethod("get" + this.fieldName);
            }

            if (this.readMethod == null) {
                throw new BeanCopyException(this.clazz, "filed has not getMethod :" + this.field.getName());
            } else {
                this.writeMethod = this.clazz.getMethod("set" + this.fieldName, this.fieldClazz);
                if (this.writeMethod == null) {
                    throw new BeanCopyException(this.clazz, "filed has not setMethod :" + this.field.getName());
                }
            }
        } catch (SecurityException | NoSuchMethodException var2) {
            throw new BeanCopyException(this.clazz, "findMethod error " + this.fieldName, var2);
        }
    }

    public static String capitalize(String name) {
        return name != null && name.length() != 0 ? name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1) : name;
    }

    public Method getReadMethod() {
        return this.readMethod;
    }

    public Method getWriteMethod() {
        return this.writeMethod;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public Class getFieldClazz() {
        return this.fieldClazz;
    }
}

